package com.example.cecv_e_commerce.domain.dto.order;

public record OrderShippingDTO(Integer id, String recipientName, String recipientPhone,
        String addressLine1, String addressLine2, String city, String postalCode, String country,
        String shippingMethod, Double shippingFee) {
}
