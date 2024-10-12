package com.ebookeria.ecommerce.repository;

import com.ebookeria.ecommerce.entity.Cart;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart, Integer> {

}
