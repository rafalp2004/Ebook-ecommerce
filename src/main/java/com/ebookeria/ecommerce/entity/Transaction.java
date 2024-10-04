package com.ebookeria.ecommerce.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

import java.util.ArrayList;
import java.util.Date;
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
    private Date transactionDate;

    @Column(name="total_sales")
    private double totalSales;

    @Column(name="status")
    private String status;

    @OneToMany(mappedBy = "transaction")
    private List<TransactionItem> transactionItems;


    public void addTransactionItem(TransactionItem transactionItem) {
        if (transactionItems == null) {
            transactionItems = new ArrayList<>();
        }
        transactionItems.add(transactionItem);
        transactionItem.setTransaction(this);
    }
}
