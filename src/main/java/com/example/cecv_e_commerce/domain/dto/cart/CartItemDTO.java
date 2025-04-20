package com.example.cecv_e_commerce.domain.dto.cart;

import com.example.cecv_e_commerce.domain.dto.product.ProductDTO;

public record CartItemDTO(Integer id, Integer quantity, ProductDTO product) {
}
