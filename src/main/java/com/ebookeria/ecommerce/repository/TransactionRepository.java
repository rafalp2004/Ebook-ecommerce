package com.ebookeria.ecommerce.repository;

import com.ebookeria.ecommerce.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Integer> {
    List<Transaction> findTransactionByUserId(int userId);
}
