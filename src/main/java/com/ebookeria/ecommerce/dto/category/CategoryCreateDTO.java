package com.ebookeria.ecommerce.dto.category;

import jakarta.validation.constraints.NotBlank;

public record CategoryCreateDTO(

        @NotBlank(message = "Name for category cannot be blank")
        String name
) {
}
