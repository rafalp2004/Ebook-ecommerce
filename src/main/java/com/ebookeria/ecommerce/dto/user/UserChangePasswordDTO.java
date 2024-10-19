package com.ebookeria.ecommerce.dto.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UserChangePasswordDTO(
        @NotBlank
        @Size(min = 2, max = 50, message = "First name must be between 2 and 50 characters")
        String password
) {
}
