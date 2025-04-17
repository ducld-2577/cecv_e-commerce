package com.example.cecv_e_commerce.domain.dto.cart;

import java.util.List;

public record CartResponseDTO(Long id, Long userId, List<CartItemDTO> items, int numberOfItems) {
}
