package com.ebookeria.ecommerce.repository;

import com.ebookeria.ecommerce.entity.Author;
import org.springframework.data.jpa.repository.JpaRepository;


public interface AuthorRepository extends JpaRepository<Author, Integer> {

}
