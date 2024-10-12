package com.ebookeria.ecommerce.dto.cart;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

//TODO Check if it is necessary (cart id and cart itemId)
public record UpdateCartItemDTO(
        @NotNull(message = "Cart ID cannot be null")
        @Min(value = 1, message = "Cart ID must be a positive number")
        int cartId,

        @NotNull(message = "Cart item ID cannot be null")
        @Min(value = 1, message = "Cart item ID must be a positive number")
        int cartItemId,

        @NotNull(message = "Quantity cannot be null")
        @Min(value = 1, message = "Quantity must be at least 1")
        int quantity
) {

}
