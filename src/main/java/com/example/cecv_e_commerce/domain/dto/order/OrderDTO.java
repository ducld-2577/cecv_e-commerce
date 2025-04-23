package com.example.cecv_e_commerce.domain.dto.order;

import java.time.LocalDateTime;
import java.util.List;
import com.example.cecv_e_commerce.domain.dto.user.UserDTO;
import com.example.cecv_e_commerce.domain.enums.OrderStatusEnum;

public record OrderDTO(Integer id, UserDTO user, List<OrderItemDTO> orderItems, Double total,
                OrderStatusEnum status, String cancelReason, LocalDateTime createdAt,
                LocalDateTime updatedAt) {
}
