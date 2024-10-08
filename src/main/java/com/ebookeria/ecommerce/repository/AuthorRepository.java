package com.ebookeria.ecommerce.repository;

import com.ebookeria.ecommerce.entity.Author;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.data.jpa.repository.JpaRepository;


public interface AuthorRepository extends JpaRepository<Author, Integer> {

}
