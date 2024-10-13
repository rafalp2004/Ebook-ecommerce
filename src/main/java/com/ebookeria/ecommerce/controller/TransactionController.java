package com.ebookeria.ecommerce.controller;

import com.ebookeria.ecommerce.dto.transaction.TransactionDTO;
import com.ebookeria.ecommerce.service.transaction.TransactionService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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
    public ResponseEntity<List<TransactionDTO>> getUserTransactions(){
        List<TransactionDTO> transactionDTOS = transactionService.getUserTransactions();
        return new ResponseEntity<>(transactionDTOS,HttpStatus.CREATED);
    }
}
