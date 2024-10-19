package com.ebookeria.ecommerce.controller;

import com.ebookeria.ecommerce.entity.Transaction;
import com.ebookeria.ecommerce.exception.ResourceNotFoundException;
import com.ebookeria.ecommerce.repository.TransactionRepository;
import com.ebookeria.ecommerce.service.checkout.CheckoutService;
import com.ebookeria.ecommerce.service.transaction.TransactionService;
import com.stripe.exception.StripeException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/checkout")
public class CheckoutController {
    private final CheckoutService checkoutService;
    private final TransactionRepository transactionRepository;

    public CheckoutController(CheckoutService checkoutService, TransactionService transactionService, TransactionRepository transactionRepository) {
        this.checkoutService = checkoutService;
        this.transactionRepository = transactionRepository;

    }


    //Only for testing
//    @PostMapping("/purchase")
//    public String createPayment() throws StripeException {
//        Transaction transaction = transactionRepository.findById(7).orElseThrow(()->new ResourceNotFoundException("asd"));
//
//        return checkoutService.createPayment(transaction);
//    }

}
