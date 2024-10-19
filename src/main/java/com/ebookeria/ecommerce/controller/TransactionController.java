package com.ebookeria.ecommerce.controller;

import com.ebookeria.ecommerce.dto.transaction.TransactionResponse;
import com.ebookeria.ecommerce.entity.Transaction;
import com.ebookeria.ecommerce.service.checkout.CheckoutService;
import com.ebookeria.ecommerce.service.transaction.TransactionService;
import com.stripe.exception.StripeException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TransactionController {
    private final TransactionService transactionService;
    private final CheckoutService checkoutService;

    public TransactionController(TransactionService transactionService, CheckoutService checkoutService) {
        this.transactionService = transactionService;
        this.checkoutService = checkoutService;
    }

    //TODO add exception to GlobalHandler
    @PostMapping(path="/transactions")
    public ResponseEntity<String> createTransaction() throws StripeException {
        Transaction transaction = transactionService.createTransaction();

        String paymentUrl = checkoutService.createPayment(transaction);
        return new ResponseEntity<>(paymentUrl,HttpStatus.CREATED);
    }

    @GetMapping(path="/transactions")
    public ResponseEntity<TransactionResponse> getUserTransactions(
            @RequestParam(value = "pageNo", defaultValue = "0", required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize
            ){

       TransactionResponse transactionResponse = transactionService.getUserTransactions(pageNo, pageSize);
        return new ResponseEntity<>(transactionResponse,HttpStatus.OK);
    }


    //TODO add some panel only for admins
    @GetMapping(path="admin/transactions")
    public ResponseEntity<TransactionResponse> getAllTransactions(
            @RequestParam(value = "pageNo", defaultValue = "0", required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize
    ){

        TransactionResponse transactionResponse = transactionService.getAllTransactions(pageNo, pageSize);
        return new ResponseEntity<>(transactionResponse,HttpStatus.OK);
    }

}
