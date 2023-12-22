package edu.umb.cs681.hw07_2;


import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;


public class RunnableCancellablePrimeFactorizer extends RunnablePrimeFactorizer implements Runnable {
    private long dividend;
    private long from;
    private long to;
    private long divisor;


    private List<Long> primeFactors = new ArrayList<>();
    private ReentrantLock lock = new ReentrantLock();
    private boolean done = false;

    public RunnableCancellablePrimeFactorizer(long dividend, long from, long to) {
        super(dividend, from, to);
    }

    public void setDone() {
        lock.lock();
        try {
            done = true;
        } finally {
            lock.unlock();
        }
    }

    protected boolean isEven(long n) {
        return n % 2 == 0;
    }

    public void generatePrimeFactors() {
        long divisor = this.from;
        while (dividend != 1 && divisor <= this.to) {
            lock.lock();
            try {
                if (done) {
                    System.out.println("Stopped generating prime factors.");
                    this.primeFactors.clear();
                    break;
                }
                if (divisor > 2 && isEven(divisor)) {
                    divisor++;
                    continue;
                }
                if (divisor != 0 && dividend % divisor == 0) {
                    primeFactors.add(divisor);
                    dividend /= divisor;
                } else {
                    if (divisor == 2) {
                        divisor++;
                    } else {
                        divisor += 2;
                    }
                }
            } finally {
                lock.unlock();
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                System.out.println(e.toString());
                continue;
            }
        }
    }

    public static void main(String[] args) {
        RunnableCancellablePrimeFactorizer gen = new RunnableCancellablePrimeFactorizer(10, 2, 100);
        Thread thread = new Thread(gen);
        thread.start();
        gen.generatePrimeFactors();
        gen.setDone();
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private List<Long> getPrimeFactors() {
        return primeFactors;
    }
}
