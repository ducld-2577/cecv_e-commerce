package com.example.cecv_e_commerce.domain.dto.order;

import java.util.List;

public record OrderResponseDTO(Integer id, Integer userId, List<OrderItemDTO> orderItems,
        Integer numberOfOrders) {
}
