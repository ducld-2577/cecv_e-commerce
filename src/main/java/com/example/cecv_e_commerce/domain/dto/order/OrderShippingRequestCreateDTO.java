package com.example.cecv_e_commerce.domain.dto.order;

import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderShippingRequestCreateDTO {
    @NotNull(message = "Recipient name is required")
    @Size(min = 1, max = 255, message = "Recipient name must be between 1 and 255 characters")
    private String recipientName;

    @NotNull(message = "Recipient phone is required")
    @Pattern(regexp = "\\d{10}", message = "Recipient phone must be 10 digits")
    private String recipientPhone;

    @NotNull(message = "Address line 1 is required")
    @Size(min = 1, max = 255, message = "Address line 1 must be between 1 and 255 characters")
    private String addressLine1;

    @NotNull(message = "Address line 2 is required")
    @Size(min = 1, max = 255, message = "Address line 1 must be between 1 and 255 characters")
    private String addressLine2;

    @NotNull(message = "City is required")
    @Size(min = 1, max = 100, message = "City must be between 1 and 255 characters")
    private String city;

    @NotNull(message = "Postal code is required")
    @Pattern(regexp = "\\d{5}", message = "Postal code must be 5 digits")
    private String postalCode;

    @NotNull(message = "Country is required")
    @Size(min = 1, max = 100, message = "Country must be between 1 and 255 characters")
    private String country;

    @NotNull(message = "Shipping method is required")
    @Size(min = 1, max = 100, message = "Shipping method must be between 1 and 255 characters")
    private String shippingMethod;

    @NotNull(message = "Shipping fee is required")
    private Double shippingFee;
}
