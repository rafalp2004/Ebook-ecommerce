package com.ebookeria.ecommerce.dto.category;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CategoryUpdateDTO(
        @NotNull(message = "ID cannot be null")
        int id,
        @NotBlank(message = "Name for category cannot be blank")
        String name
) {
}
