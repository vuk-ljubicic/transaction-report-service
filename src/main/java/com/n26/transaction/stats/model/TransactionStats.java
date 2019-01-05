package com.n26.transaction.stats.model;

public class TransactionStats {
    private double sum;

    private double avg;

    private double max = Double.MIN_VALUE;

    private double min = Double.MAX_VALUE;

    private long count;

    public double getSum() {
        return sum;
    }

    public synchronized void setSum(double sum) {
        this.sum = sum;
    }

    public double getAvg() {
        return avg;
    }

    public synchronized void setAvg(double avg) {
        this.avg = avg;
    }

    public double getMax() {
        return max;
    }

    public synchronized void setMax(double max) {
        this.max = max;
    }

    public double getMin() {
        return min;
    }

    public synchronized void setMin(double min) {
        this.min = min;
    }

    public long getCount() {
        return count;
    }

    public synchronized void setCount(long count) {
        this.count = count;
    }
}
