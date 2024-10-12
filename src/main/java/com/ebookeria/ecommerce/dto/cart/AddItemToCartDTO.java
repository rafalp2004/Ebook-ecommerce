package com.ebookeria.ecommerce.dto.cart;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record AddItemToCartDTO(
        @NotNull(message = "Cart ID cannot be null")
        @Min(value = 1, message = "Cart ID must be a positive number")
        int cartId,

        @NotNull(message = "Ebook ID cannot be null")
        @Min(value = 1, message = "Ebook ID must be a positive number")
        int ebookId,

        @NotNull(message = "Quantity cannot be null")
        @Min(value = 1, message = "Quantity must be at least 1")
        int quantity
) {
}
