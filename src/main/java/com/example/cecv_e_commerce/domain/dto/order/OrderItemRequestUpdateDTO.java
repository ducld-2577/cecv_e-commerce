package com.example.cecv_e_commerce.domain.dto.order;

import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemRequestUpdateDTO {
    @NotNull(message = "Order item ID is required")
    private Integer orderItemId;

    @NotNull(message = "Quantity is required")
    @Min(value = 1, message = "Quantity must be at least 1")
    private Integer quantity;

    @NotNull(message = "Price is required")
    @Min(value = 0, message = "Price must be at least 0")
    private Double price;

    @NotNull(message = "Order ID is required")
    private Integer orderId;
}
