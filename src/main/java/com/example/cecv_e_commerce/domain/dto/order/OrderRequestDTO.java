package com.example.cecv_e_commerce.domain.dto.order;

import java.util.List;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

public record OrderRequestDTO(@NotNull(
        message = "Order items must not be null") @Valid List<OrderItemRequestCreateDTO> orderItems,

        @NotNull(
                message = "Order shipping must not be null") @Valid OrderShippingRequestCreateDTO orderShipping) {

}

