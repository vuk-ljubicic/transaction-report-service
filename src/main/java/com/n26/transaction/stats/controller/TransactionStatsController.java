package com.n26.transaction.stats.controller;

import com.n26.transaction.stats.model.Transaction;
import com.n26.transaction.stats.model.TransactionStats;
import com.n26.transaction.stats.service.TransactionStatsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TransactionStatsController {
    private final TransactionStatsService transactionStatsService;

    @Autowired
    public TransactionStatsController(final TransactionStatsService transactionStatsService) {
        this.transactionStatsService = transactionStatsService;
    }

    @GetMapping("/stats")
    public TransactionStats getTransactionStats() {
        return transactionStatsService.getLookBackTransactionStats();
    }

    @PostMapping("/transaction")
    public ResponseEntity<?> addTransaction(@RequestBody Transaction transaction) {
        try {
            if (transactionStatsService.validateTransaction(transaction)) {
                transactionStatsService.insertTransaction(transaction);
                return new ResponseEntity<>(HttpStatus.ACCEPTED);
            } else {
                return new ResponseEntity<>(HttpStatus.UNPROCESSABLE_ENTITY);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }
}
