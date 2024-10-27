package com.ebookeria.ecommerce.service.emailContentBuilder;

import com.ebookeria.ecommerce.entity.Transaction;
import com.ebookeria.ecommerce.entity.TransactionItem;
import org.springframework.stereotype.Service;

@Service
public class EmailContentBuilderImpl implements EmailContentBuilder {

    @Override
    public String buildTransactionEmail(Transaction transaction) {
        String s = "Thank you for purchase. The url for ebook is below";
        StringBuilder sB = new StringBuilder(s);

        for(TransactionItem item : transaction.getTransactionItems()){
            sB.append("\n");
            sB.append(item.getEbook().getTitle()).append(" ").append(item.getEbook().getDownloadUrl());
        }

        return sB.toString();
    }
}
