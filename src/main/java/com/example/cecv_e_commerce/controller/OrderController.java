package com.example.cecv_e_commerce.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.cecv_e_commerce.service.OrderService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import com.example.cecv_e_commerce.domain.dto.order.OrderItemRequestCreateDTO;
import com.example.cecv_e_commerce.domain.dto.order.OrderItemRequestDeleteDTO;
import com.example.cecv_e_commerce.domain.dto.order.OrderItemRequestUpdateDTO;
import com.example.cecv_e_commerce.domain.dto.order.OrderRequestDTO;
import com.example.cecv_e_commerce.domain.dto.order.OrderResponseDTO;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @PostMapping
    public OrderResponseDTO createOrder(@RequestBody OrderRequestDTO orderRequestDTO) {
        return orderService.createOrder(orderRequestDTO);
    }

    @GetMapping("/{orderId}")
    public OrderResponseDTO getOrderById(@PathVariable Integer orderId) {
        return orderService.getOrderById(orderId);
    }

    @DeleteMapping("/items")
    public OrderResponseDTO deleteOrderItem(
            @RequestBody OrderItemRequestDeleteDTO orderItemRequestDeleteDTO) {
        return orderService.deleteOrderItem(orderItemRequestDeleteDTO);
    }

    @PostMapping("/items")
    public OrderResponseDTO createOrderItem(
            @RequestBody OrderItemRequestCreateDTO orderItemRequestCreateDTO) {
        return orderService.createOrderItem(orderItemRequestCreateDTO);
    }

    @PutMapping("/items")
    public OrderResponseDTO updateOrderItem(
            @RequestBody OrderItemRequestUpdateDTO orderItemRequestUpdateDTO) {
        return orderService.updateOrderItem(orderItemRequestUpdateDTO);
    }
}
