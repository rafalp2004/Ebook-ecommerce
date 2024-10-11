package com.ebookeria.ecommerce.dto.ebook;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;
import java.util.List;

public record EbookCreationDTO(
        @NotNull(message = "Title cannot be null")
        @NotBlank(message = "title cannot be blank")
        String title,

        @NotNull(message = "Description cannot be null")
        @NotBlank(message = "Description cannot be blank")
        String description,

        @NotNull(message = "Published year cannot be null")
        @PastOrPresent(message = "Published year must be in the past or present")
        LocalDate publishedYear,

        @NotNull(message = "Category ID cannot be null")
        @Positive(message = "Category ID must be positive")
        int categoryId,

        @NotNull(message = "Price cannot be null")
        @Positive(message = "Price must be positive")
        double price,

        @NotNull(message = "Authors list cannot be null")
        @Size(min = 1, message = "There must be at least one author")
        List<Integer> authorsId,

        @NotNull(message = "Image URLs list cannot be null")
        @Size(min = 1, message = "There must be at least one image URL")
        List<String> imageUrls,

        @NotNull(message = "Download URL cannot be null")
        String downloadUrl
) { }
