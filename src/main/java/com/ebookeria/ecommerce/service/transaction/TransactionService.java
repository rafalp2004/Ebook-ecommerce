package com.ebookeria.ecommerce.service.transaction;

import com.ebookeria.ecommerce.dto.transaction.TransactionDTO;
import com.ebookeria.ecommerce.entity.Transaction;

import java.util.List;

public interface TransactionService {

    void createTransaction();

    List<TransactionDTO> getUserTransactions();
}
