package com.ebookeria.ecommerce.repository;

import com.ebookeria.ecommerce.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Integer> {
    Role findByName(String name);
}
