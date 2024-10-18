package com.ebookeria.ecommerce.service.transaction;

import com.ebookeria.ecommerce.dto.transaction.TransactionResponse;

public interface TransactionService {

    void createTransaction();

    TransactionResponse getUserTransactions(int pageNo,int pageSize);

    TransactionResponse getAllTransactions();
}
