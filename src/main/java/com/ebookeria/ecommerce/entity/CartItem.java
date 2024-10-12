package com.ebookeria.ecommerce.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name="cart_item")
public class CartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private int id;

    @Column(name="quantity")
    private int quantity;

    @Column(name="unit_price")
    private double unit_price;

    @Column(name="added_at")
    private LocalDateTime addedAt;

    @ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinColumn(name="cart_id")
    private Cart cart;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name="ebook_id")
    private Ebook ebook;

}
