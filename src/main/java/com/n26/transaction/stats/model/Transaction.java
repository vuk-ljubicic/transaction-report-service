package com.n26.transaction.stats.model;

public class Transaction{

    private double amount;

    private long timestamp;

    public Transaction(){

    }

    public Transaction(double amount, long timestamp){
        this.amount = amount;
        this.timestamp = timestamp;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}
