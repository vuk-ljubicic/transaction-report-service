package com.n26.transaction.stats.service;

import com.n26.transaction.stats.model.Transaction;
import com.n26.transaction.stats.model.TransactionStats;

public interface TransactionStatsService {
    void insertTransaction(Transaction transaction);
    TransactionStats getLookBackTransactionStats();
    boolean validateTransaction(Transaction transaction);
}
