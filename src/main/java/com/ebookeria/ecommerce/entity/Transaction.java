package com.ebookeria.ecommerce.entity;

import com.ebookeria.ecommerce.enums.TransactionStatus;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name="transaction")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
@ToString(exclude = {"user", "transactionItems"})
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private int id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id")
    private User user;

    @Column(name="transaction_date")
    private LocalDateTime transactionDate;

    @Column(name="total_sales")
    private double totalSales;

    @Column(name="status")
    @Enumerated(EnumType.STRING)
    private TransactionStatus status;


    @OneToMany(mappedBy = "transaction",cascade = CascadeType.ALL)
    private List<TransactionItem> transactionItems;


    public void addTransactionItem(TransactionItem transactionItem) {
        if (transactionItems == null) {
            transactionItems = new ArrayList<>();
        }
        transactionItems.add(transactionItem);
        transactionItem.setTransaction(this);
    }
}
