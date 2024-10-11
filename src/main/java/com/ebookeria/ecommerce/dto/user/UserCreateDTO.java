package com.ebookeria.ecommerce.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record UserCreateDTO(
        @NotNull(message = "First name cannot be null")
        @NotBlank(message = "First name cannot be blank")
        @Size(min=2, max=50, message="First name must be between 2 and 50 characters")
        String firstName,

        @NotNull(message = "Last name cannot be null")
        @NotBlank(message = "Last name cannot be blank")
        @Size(min=2, max=50, message="Last name must be between 2 and 50 characters")
        String lastName,

        @NotNull(message = "Email  cannot be null")
        @Email
        String email,

        @NotNull(message = "Password cannot be null")
        @Size(min=2, max=50, message="Password must be between 2 and 50 characters")
        String password
) {
}
