package com.ebookeria.ecommerce.dto.ebook;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;
import java.util.List;

public record EbookUpdateDTO(
        @NotNull(message = "ID cannot be null")
        Integer id,

        @NotBlank(message = "Title cannot be blank")
        String title,

        String description,

        @PastOrPresent(message = "Published year must be in the past or present")
        LocalDate publishedYear,

        @Positive(message = "Category ID must be positive")
        Integer categoryId,

        @Positive(message = "Price must be positive")
        Double price,

        @Size(min = 1, message = "There must be at least one author")
        List<Integer> authorsId,

        @Size(min = 1, message = "There must be at least one image URL")
        List<String> imageUrls,

        String downloadUrl
) {
}
