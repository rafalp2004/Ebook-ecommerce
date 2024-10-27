package com.ebookeria.ecommerce.repository;

import com.ebookeria.ecommerce.entity.Ebook;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface EbookRepository extends JpaRepository<Ebook,Integer> {
    @Query("SELECT e FROM TransactionItem ti JOIN ti.transaction t JOIN ti.ebook e WHERE t.user.id = :userId")
    Page<Ebook> findUsersBoughtEbooksByUserId(@Param("userId") int userId, Pageable pageable);
}
