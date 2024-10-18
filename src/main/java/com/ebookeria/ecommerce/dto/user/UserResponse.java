package com.ebookeria.ecommerce.dto.user;


import java.util.List;

public record UserResponse(
        List<UserDTO> content,
        int pageNo,
        int pageSize,
        int totalElements,
        int totalPages,
        boolean last
) {
}
