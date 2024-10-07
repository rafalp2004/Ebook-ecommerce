package com.ebookeria.ecommerce.repository;

import com.ebookeria.ecommerce.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
}
