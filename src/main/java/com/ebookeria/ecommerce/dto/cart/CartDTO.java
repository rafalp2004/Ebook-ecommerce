package com.ebookeria.ecommerce.dto.cart;

import java.time.LocalDateTime;
import java.util.List;

public record CartDTO(
         int id,
         LocalDateTime createdAt,
         LocalDateTime lastUpdated,
         List<CartItemDTO> cartItems

) {
}
