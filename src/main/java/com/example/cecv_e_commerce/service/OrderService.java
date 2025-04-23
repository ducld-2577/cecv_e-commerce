package com.example.cecv_e_commerce.service;

import org.springframework.stereotype.Service;
import com.example.cecv_e_commerce.domain.dto.order.OrderItemRequestCreateDTO;
import com.example.cecv_e_commerce.domain.dto.order.OrderItemRequestDeleteDTO;
import com.example.cecv_e_commerce.domain.dto.order.OrderItemRequestUpdateDTO;
import com.example.cecv_e_commerce.domain.dto.order.OrderRequestDTO;
import com.example.cecv_e_commerce.domain.dto.order.OrderResponseDTO;

@Service
public interface OrderService {
    OrderResponseDTO createOrder(OrderRequestDTO orderRequestDTO);

    OrderResponseDTO createOrderItem(OrderItemRequestCreateDTO orderItemRequestCreateDTO);

    OrderResponseDTO updateOrderItem(OrderItemRequestUpdateDTO orderItemRequestUpdateDTO);

    OrderResponseDTO deleteOrderItem(OrderItemRequestDeleteDTO orderItemRequestDeleteDTO);

    OrderResponseDTO getOrderById(Integer orderId);
}
