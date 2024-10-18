package com.ebookeria.ecommerce.controller;

import com.ebookeria.ecommerce.dto.transaction.TransactionResponse;
import com.ebookeria.ecommerce.service.transaction.TransactionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TransactionController {
    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping(path="/transactions")
    public ResponseEntity<Void> createTransaction(){
        transactionService.createTransaction();
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping(path="/transactions")
    public ResponseEntity<TransactionResponse> getUserTransactions(
            @RequestParam(value = "pageNo", defaultValue = "0", required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize
            ){

       TransactionResponse transactionResponse = transactionService.getUserTransactions(pageNo, pageSize);
        return new ResponseEntity<>(transactionResponse,HttpStatus.OK);

    }
}
