package com.example.cecv_e_commerce.domain.dto.order;

import java.time.LocalDateTime;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderPaymentRequestDTO {
    @NotNull(message = "Payment method is required")
    @Size(max = 100, message = "Payment method must be less than 100 characters")
    private String paymentMethod;

    @NotNull(message = "Payment status is required")
    @Size(max = 50, message = "Payment status must be less than 50 characters")
    private String paymentStatus;

    @NotNull(message = "Payment amount is required")
    @Min(value = 0, message = "Payment amount must be greater than 0")
    private Double paymentAmount;

    @Size(max = 255, message = "Transaction id must be less than 255 characters")
    private String transactionId;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime paidAt;
}
