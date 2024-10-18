package com.ebookeria.ecommerce.dto.transaction;

import java.time.LocalDateTime;
import java.util.List;

public record TransactionDTO(
        int id,
        String email,
        LocalDateTime localDateTime,
        double totalSales,
        String status,
        List<TransactionItemDTO> transactionItems


) {
}
