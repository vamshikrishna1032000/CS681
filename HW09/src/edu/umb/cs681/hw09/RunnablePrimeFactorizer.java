package edu.umb.cs681.hw09;

public class RunnablePrimeFactorizer extends PrimeFactorizer implements Runnable {
    private long dividend;
    private long from;
    private long to;
    private long divisor;
    private boolean done = false;

    public RunnablePrimeFactorizer(long dividend, long from, long to) {
        super(dividend, from, to);
        if (dividend >= 1) {
            this.dividend = dividend;
        } else {
            throw new IllegalArgumentException("dividend must be greater than or equal to 1");
        }
        if (from >= 2) {
            this.from = from;
        } else {
            throw new IllegalArgumentException("from must be greater than or equal to 2");
        }
        if (to >= from) {
            this.to = to;
        } else {
            throw new IllegalArgumentException("to must be greater than or equal to from");
        }
    }

    public void setDone() {
        done = true;
    }

    public void run() {
        for (long n = from; n <= to; n++) {
            if (done) {
                System.out.println("Stopped generating prime factors.");
                break;
            }
            if (dividend % n == 0) {
                divisor = n;
                System.out.println(divisor);
            }
        }
    }
}
