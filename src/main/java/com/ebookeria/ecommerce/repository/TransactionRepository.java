package com.ebookeria.ecommerce.repository;

import com.ebookeria.ecommerce.entity.Transaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction, Integer> {

    Page<Transaction> findTransactionByUserId(int userId, Pageable pageable);
}
