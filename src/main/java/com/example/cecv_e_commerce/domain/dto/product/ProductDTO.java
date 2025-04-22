package com.example.cecv_e_commerce.domain.dto.product;

import java.math.BigDecimal;

public record ProductDTO(Integer id, String name, String description, BigDecimal price, Integer quantity) {
}
