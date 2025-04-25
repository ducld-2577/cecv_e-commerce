package com.example.cecv_e_commerce.domain.dto.order;

import java.util.List;

public record OrderRequestDTO(List<OrderItemRequestCreateDTO> orderItems) {
}
