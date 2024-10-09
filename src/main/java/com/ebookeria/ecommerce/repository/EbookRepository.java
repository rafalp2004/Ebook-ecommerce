package com.ebookeria.ecommerce.repository;

import com.ebookeria.ecommerce.entity.Ebook;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EbookRepository extends JpaRepository<Ebook,Integer> {

}
