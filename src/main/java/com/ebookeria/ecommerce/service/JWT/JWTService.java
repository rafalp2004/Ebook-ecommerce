package com.ebookeria.ecommerce.service.JWT;

import org.springframework.security.core.userdetails.UserDetails;

public interface JWTService {
    String generateToken(String email);

    String extractEmail(String token);

    boolean validateToken(String token, UserDetails userDetails);
}
