package com.ebookeria.ecommerce.dto.transaction;

public record TransactionItemDTO(
        String EbookTitle,
        double unitPrice,
        int quantity
) {
}
