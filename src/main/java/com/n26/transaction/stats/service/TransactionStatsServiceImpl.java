package com.n26.transaction.stats.service;

import com.n26.transaction.stats.model.Transaction;
import com.n26.transaction.stats.model.TransactionStats;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.concurrent.ConcurrentNavigableMap;
import java.util.concurrent.ConcurrentSkipListMap;

@Service
public class TransactionStatsServiceImpl implements TransactionStatsService{
    public static final int TRANSACTION_STATS_LOOK_BACK_SECONDS = 60;
    public static final int CACHE_CLEANUP_THREAD_SLEEP_MINUTES = 1;
    private ConcurrentNavigableMap<LocalDateTime, TransactionStats> transactionStatsCache;

    public TransactionStatsServiceImpl() {
        transactionStatsCache = new ConcurrentSkipListMap<>();
    }

    @PostConstruct
    public void initTransactionStatsCacheCleanup(){
        (new Thread(() -> {
            try {
                transactionStatsCache.headMap(getTransactionStatsLookBack()).forEach((k, v) -> {
                    transactionStatsCache.remove(k);
                });
                Thread.sleep(CACHE_CLEANUP_THREAD_SLEEP_MINUTES);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        })).start();
    }

    @Override
    public void insertTransaction(Transaction transaction) {
        LocalDateTime transactionSecond = getTransactionSecond(transaction);
        if (transactionSecond != null) {
            TransactionStats transactionStats = transactionStatsCache.get(transactionSecond);
            if (transactionStats == null) {
                transactionStatsCache.put(transactionSecond, new TransactionStats());
            }
            transactionStats.setSum(transactionStats.getSum() + transaction.getAmount());
            transactionStats.setCount(transactionStats.getCount() + 1);
            transactionStats.setMax(transactionStats.getMax() > transaction.getAmount() ?
                    transactionStats.getMax() : transaction.getAmount());
            transactionStats.setMin(transactionStats.getMin() < transaction.getAmount() ?
                    transactionStats.getMin() : transaction.getAmount());
        }
    }

    @Override
    public TransactionStats getLookBackTransactionStats() {
        TransactionStats transactionStats = new TransactionStats();
        transactionStatsCache.tailMap(getTransactionStatsLookBack()).forEach((k, v) -> {
            transactionStats.setSum(transactionStats.getSum() + v.getSum());
            transactionStats.setCount(transactionStats.getCount() + v.getCount());
            transactionStats.setMax(transactionStats.getMax() > v.getMax() ?
                    transactionStats.getMax() : v.getMax());
            transactionStats.setMin(transactionStats.getMin() < v.getMin() ?
                    transactionStats.getMin() : v.getMin());
        });
        transactionStats.setAvg(transactionStats.getSum() / transactionStats.getCount());
        return transactionStats;
    }

    private LocalDateTime getTransactionSecond(Transaction transaction) {
        LocalDateTime transactionTimestamp = LocalDateTime.ofInstant(Instant.ofEpochMilli(transaction.getTimestamp()),
                ZoneId.systemDefault()).withNano(0);
        if (getTransactionStatsLookBack().isAfter(transactionTimestamp)) {
            return null;
        }
        return transactionTimestamp;

    }

    private LocalDateTime getTransactionStatsLookBack() {
        LocalDateTime transactionStatsLookBack = LocalDateTime.now().withNano(0).
                minusSeconds(TRANSACTION_STATS_LOOK_BACK_SECONDS);
        return transactionStatsLookBack;
    }
}
