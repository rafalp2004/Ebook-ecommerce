package com.ebookeria.ecommerce.dto.transaction;

import com.ebookeria.ecommerce.entity.TransactionItem;
import com.ebookeria.ecommerce.enums.TransactionStatus;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

import java.time.LocalDate;
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
