package com.ebookeria.ecommerce.dto.ebook;

import java.time.LocalDate;
import java.util.List;

public record EbookCreationDTO(
        String title,
        String description,
        LocalDate publishedYear,
        String category,
        double price,
        List<Integer> authorsId,
        List<String> imageUrls,
        String downloadUrl

)
{ }
