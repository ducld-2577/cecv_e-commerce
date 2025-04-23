package com.example.cecv_e_commerce.domain.dto.order;

import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.NotNull;

@Data
@NoArgsConstructor
public class OrderItemRequestDeleteDTO {
    @NotNull(message = "Order ID is required")
    private Integer orderId;

    @NotNull(message = "Order item ID is required")
    private Integer orderItemId;
}
