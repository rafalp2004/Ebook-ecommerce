package com.ebookeria.ecommerce.dto.transaction;

import com.ebookeria.ecommerce.dto.ebook.EbookDTO;
import com.ebookeria.ecommerce.entity.Ebook;

public record TransactionItemDTO(
        String EbookTitle,
        double unitPrice,
        int quantity
) {
}
