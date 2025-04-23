package com.example.cecv_e_commerce.service.impl;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import com.example.cecv_e_commerce.domain.dto.order.OrderItemDTO;
import com.example.cecv_e_commerce.domain.dto.order.OrderItemRequestCreateDTO;
import com.example.cecv_e_commerce.domain.dto.order.OrderItemRequestDeleteDTO;
import com.example.cecv_e_commerce.domain.dto.order.OrderItemRequestUpdateDTO;
import com.example.cecv_e_commerce.domain.dto.order.OrderRequestDTO;
import com.example.cecv_e_commerce.domain.dto.order.OrderResponseDTO;
import com.example.cecv_e_commerce.domain.dto.product.ProductDTO;
import com.example.cecv_e_commerce.domain.enums.OrderStatusEnum;
import com.example.cecv_e_commerce.domain.model.Order;
import com.example.cecv_e_commerce.domain.model.OrderItem;
import com.example.cecv_e_commerce.domain.model.Product;
import com.example.cecv_e_commerce.domain.model.User;
import com.example.cecv_e_commerce.exception.BadRequestException;
import com.example.cecv_e_commerce.exception.ResourceNotFoundException;
import com.example.cecv_e_commerce.repository.OrderRepository;
import com.example.cecv_e_commerce.repository.OrderItemRepository;
import com.example.cecv_e_commerce.repository.ProductRepository;
import com.example.cecv_e_commerce.service.OrderService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;

    @Override
    @Transactional
    public OrderResponseDTO createOrder(OrderRequestDTO orderRequestDTO) {
        User user = getCurrentUser();
        Order order = new Order();
        order.setUser(user);

        List<OrderItem> orderItems = orderRequestDTO.orderItems().stream().map(item -> {
            Product product = productRepository.findByIdWithLock(item.getProductId())
                    .orElseThrow(() -> new ResourceNotFoundException("Product not found"));

            if (product.getQuantity() < item.getQuantity()) {
                throw new BadRequestException(
                        "Not enough product quantity in stock for product ID: " + product.getId()
                                + ". Available: " + product.getQuantity() + ", Requested: "
                                + item.getQuantity());
            }

            product.setQuantity(product.getQuantity() - item.getQuantity());
            productRepository.save(product);

            OrderItem orderItem = new OrderItem();
            orderItem.setProduct(product);
            orderItem.setQuantity(item.getQuantity());
            orderItem.setPrice(item.getPrice());
            orderItem.setOrder(order);

            return orderItem;
        }).collect(Collectors.toList());

        order.setOrderItems(orderItems);
        order.setTotal(order.getOrderItems().stream()
                .mapToDouble(item -> item.getPrice() * item.getQuantity()).sum());
        order.setStatus(OrderStatusEnum.PENDING);

        orderRepository.save(order);

        return convertOrderToOrderResponseDTO(order);
    }

    @Override
    @Transactional
    public OrderResponseDTO createOrderItem(OrderItemRequestCreateDTO orderItemRequestCreateDTO) {
        Order order = findOrderByIdAndCheckAccess(orderItemRequestCreateDTO.getOrderId());
        Product product = productRepository.findByIdWithLock(orderItemRequestCreateDTO.getProductId())
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));

        OrderItem existingOrderItem = order.getOrderItems().stream()
                .filter(item -> item.getProduct().getId().equals(product.getId())).findFirst()
                .orElse(null);

        if (existingOrderItem != null) {
            int newQuantity =
                    existingOrderItem.getQuantity() + orderItemRequestCreateDTO.getQuantity();
            validateAndUpdateProductQuantity(product, newQuantity, existingOrderItem.getQuantity());
            existingOrderItem.setQuantity(newQuantity);
            existingOrderItem.setPrice(orderItemRequestCreateDTO.getPrice());
            orderItemRepository.save(existingOrderItem);
        } else {
            validateAndUpdateProductQuantity(product, orderItemRequestCreateDTO.getQuantity(), 0);

            OrderItem orderItem = new OrderItem();
            orderItem.setProduct(product);
            orderItem.setQuantity(orderItemRequestCreateDTO.getQuantity());
            orderItem.setPrice(orderItemRequestCreateDTO.getPrice());
            orderItem.setOrder(order);

            orderItemRepository.save(orderItem);
        }

        updateOrderTotal(order);

        return convertOrderToOrderResponseDTO(order);
    }

    @Override
    @Transactional
    public OrderResponseDTO updateOrderItem(OrderItemRequestUpdateDTO orderItemRequestUpdateDTO) {
        Order order = findOrderByIdAndCheckAccess(orderItemRequestUpdateDTO.getOrderId());
        OrderItem orderItem =
                findOrderItemByIdAndCheckAccess(orderItemRequestUpdateDTO.getOrderItemId());

        Product product = productRepository.findByIdWithLock(orderItem.getProduct().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));

        validateAndUpdateProductQuantity(product, orderItemRequestUpdateDTO.getQuantity(),
                orderItem.getQuantity());

        orderItem.setQuantity(orderItemRequestUpdateDTO.getQuantity());
        orderItem.setPrice(orderItemRequestUpdateDTO.getPrice());
        orderItemRepository.save(orderItem);
        updateOrderTotal(order);

        return convertOrderToOrderResponseDTO(order);
    }

    @Override
    @Transactional
    public OrderResponseDTO deleteOrderItem(OrderItemRequestDeleteDTO orderItemRequestDeleteDTO) {
        Order order = findOrderByIdAndCheckAccess(orderItemRequestDeleteDTO.getOrderId());
        OrderItem orderItem =
                findOrderItemByIdAndCheckAccess(orderItemRequestDeleteDTO.getOrderItemId());

        Product product = productRepository.findByIdWithLock(orderItem.getProduct().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));

        product.setQuantity(product.getQuantity() + orderItem.getQuantity());
        productRepository.save(product);

        order.getOrderItems().remove(orderItem);
        updateOrderTotal(order);

        return convertOrderToOrderResponseDTO(order);
    }

    @Override
    public OrderResponseDTO getOrderById(Integer orderId) {
        Order order = findOrderByIdAndCheckAccess(orderId);
        return convertOrderToOrderResponseDTO(order);
    }

    private User getCurrentUser() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (user == null) {
            throw new ResourceNotFoundException("User not found");
        }

        return user;
    }

    private Order findOrderByIdAndCheckAccess(Integer orderId) {
        User user = getCurrentUser();
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found"));
        if (order.getUser().getId() != user.getId()) {
            throw new ResourceNotFoundException("Order not found");
        }

        return order;
    }

    private OrderItem findOrderItemByIdAndCheckAccess(Integer orderItemId) {
        User user = getCurrentUser();
        OrderItem orderItem = orderItemRepository.findById(orderItemId)
                .orElseThrow(() -> new ResourceNotFoundException("Order item not found"));
        if (orderItem.getOrder().getUser().getId() != user.getId()) {
            throw new ResourceNotFoundException("Order item not found");
        }

        return orderItem;
    }

    private void updateOrderTotal(Order order) {
        double total = order.getOrderItems().stream()
                .mapToDouble(item -> item.getPrice() * item.getQuantity()).sum();
        order.setTotal(total);
        orderRepository.save(order);
    }

    private void validateAndUpdateProductQuantity(Product product, int requestedQuantity,
            int currentOrderItemQuantity) {
        if (product.getQuantity() < (requestedQuantity - currentOrderItemQuantity)) {
            throw new BadRequestException(
                    "Not enough product quantity in stock. Available: " + product.getQuantity());
        }
        product.setQuantity(product.getQuantity() - (requestedQuantity - currentOrderItemQuantity));
        productRepository.save(product);
    }

    private OrderResponseDTO convertOrderToOrderResponseDTO(Order order) {
        List<OrderItemDTO> orderItems = order.getOrderItems().stream()
                .map(item -> new OrderItemDTO(item.getId(), order.getId(),
                        new ProductDTO(item.getProduct().getId(), item.getProduct().getName(),
                                item.getProduct().getDescription(), item.getProduct().getPrice(),
                                item.getProduct().getQuantity()),
                        item.getPrice(), item.getQuantity()))
                .collect(Collectors.toList());

        return new OrderResponseDTO(order.getId(), order.getUser().getId(), orderItems,
                order.getOrderItems().size());
    }
}
