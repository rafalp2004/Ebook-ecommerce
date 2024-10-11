package com.ebookeria.ecommerce.dto.author;

import jakarta.validation.constraints.NotNull;

public record AuthorUpdateDTO(
        @NotNull(message = "ID cannot be null")
        int id,
        @NotNull(message = "First name cannot be null")
        String firstName,
        @NotNull(message = "Last name cannot be null")
        String lastName
) {
}
