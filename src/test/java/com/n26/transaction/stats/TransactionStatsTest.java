package com.n26.transaction.stats;

import com.n26.transaction.stats.model.Transaction;
import com.n26.transaction.stats.model.TransactionStats;
import com.n26.transaction.stats.service.TransactionStatsServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

@RunWith(JUnit4.class)
public class TransactionStatsTest {
    private static final String TEST_TIME_LITERAL = "2000-01-01 00:00:00";
    private List<Transaction> serviceInput;
    private TransactionStats statsOutput;
    private TransactionStatsServiceImpl transactionStatsService;

    @Before
    public void setUp() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime testTime = LocalDateTime.parse(TEST_TIME_LITERAL, formatter);
        serviceInput = new ArrayList<>();
        serviceInput.add(new Transaction(10, testTime.minusSeconds(80).
                atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()));
        serviceInput.add(new Transaction(20, testTime.minusSeconds(70).
                atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()));
        serviceInput.add(new Transaction(30, testTime.minusSeconds(61).
                atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()));
        serviceInput.add(new Transaction(40, testTime.minusSeconds(60).
                atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()));
        serviceInput.add(new Transaction(50, testTime.minusSeconds(59).
                atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()));
        serviceInput.add(new Transaction(60, testTime.
                atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()));
        serviceInput.add(new Transaction(70, testTime.plusSeconds(1).
                atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()));
        serviceInput.add(new Transaction(80, testTime.plusSeconds(10).
                atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()));
        statsOutput = new TransactionStats(150, 50, 60, 40, 3);
        transactionStatsService = new TransactionStatsServiceImpl();
        transactionStatsService = spy(transactionStatsService);
        when(transactionStatsService.getNow()).thenReturn(testTime);
    }

    @Test
    public void transactionStatsTest() {
        serviceInput.forEach(t -> transactionStatsService.insertTransaction(t));
        assertTrue(statsOutput.equals(transactionStatsService.getLookBackTransactionStats()));

    }
}
