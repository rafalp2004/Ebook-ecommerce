package com.ebookeria.ecommerce.dto.ebook;

import java.util.List;

public record EbookResponse(
        List<EbookDTO> content,
        int pageNo,
        int pageSize,
        int totalElements,
        int totalPages,
        boolean last

) {
}
