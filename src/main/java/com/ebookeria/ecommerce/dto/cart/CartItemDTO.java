package com.ebookeria.ecommerce.dto.cart;

import java.time.LocalDateTime;

public record CartItemDTO(
        int id,
        int ebookId,
        int quantity,
        double unitPrice,
        LocalDateTime addedAt
) {
}
