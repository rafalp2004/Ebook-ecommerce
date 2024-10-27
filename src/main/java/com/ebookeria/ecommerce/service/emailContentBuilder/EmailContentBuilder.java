package com.ebookeria.ecommerce.service.emailContentBuilder;


import com.ebookeria.ecommerce.entity.Transaction;

public interface EmailContentBuilder {
    String buildTransactionEmail(Transaction transaction);
}
