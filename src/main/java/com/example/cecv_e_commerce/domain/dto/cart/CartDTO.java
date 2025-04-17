package com.example.cecv_e_commerce.domain.dto.cart;

import java.util.List;

public record CartDTO(Long id, Long userId, List<CartItemDTO> items) {
}
