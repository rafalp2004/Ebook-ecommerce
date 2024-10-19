package com.ebookeria.ecommerce.service.transaction;

import com.ebookeria.ecommerce.dto.transaction.TransactionResponse;
import com.ebookeria.ecommerce.entity.Transaction;

public interface TransactionService {

    Transaction createTransaction();

    TransactionResponse getUserTransactions(int pageNo,int pageSize);

    TransactionResponse getAllTransactions(int pageNo,int pageSize);
}
