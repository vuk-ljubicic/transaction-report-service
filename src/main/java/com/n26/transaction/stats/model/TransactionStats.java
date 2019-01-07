package com.n26.transaction.stats.model;

public class TransactionStats {
    private double sum;

    private double avg;

    private double max = Double.MIN_VALUE;

    private double min = Double.MAX_VALUE;

    private long count;

    public TransactionStats() {

    }

    public TransactionStats(double sum, double avg, double max, double min, long count) {
        this.sum = sum;
        this.avg = avg;
        this.max = max;
        this.min = min;
        this.count = count;
    }

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

    @Override
    public boolean equals(Object obj){
        if(obj == null || !(obj instanceof TransactionStats)){
            return false;
        } else {
            if(this.count == ((TransactionStats) obj).count &&
            this.min == ((TransactionStats) obj).min &&
            this.max == ((TransactionStats) obj).max &&
            this.avg == ((TransactionStats) obj).avg &&
            this.sum == ((TransactionStats) obj).sum){
                return true;
            } else {
                return false;
            }
        }
    }
}
