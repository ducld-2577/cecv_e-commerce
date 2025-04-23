package com.example.cecv_e_commerce.domain.dto.order;

import com.example.cecv_e_commerce.domain.dto.product.ProductDTO;

public record OrderItemDTO(Integer id, Integer orderId, ProductDTO product, Double price,
        Integer quantity) {
}
