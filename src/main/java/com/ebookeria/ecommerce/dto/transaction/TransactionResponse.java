package com.ebookeria.ecommerce.dto.transaction;

import java.util.List;

public record TransactionResponse(
        List<TransactionDTO> content,
        int pageNo,
        int pageSize,
        long totalElements,
        int totalPages,
        boolean last

) {
}
