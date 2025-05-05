package com.example.cecv_e_commerce.domain.dto.order;

import java.time.LocalDateTime;

public record OrderPaymentDTO(Integer id, Integer orderId, String paymentMethod, String paymentStatus,
        Double paymentAmount, String transactionId, LocalDateTime paidAt) {
}
