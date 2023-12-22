package edu.umb.cs681.hw09;

import edu.umb.cs681.hw09.PrimeFactorizer;
import edu.umb.cs681.hw09.RunnableCancellablePrimeFactorizer;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

public class RunnableCancellableInterruptiblePrimeFactorizer extends RunnableCancellablePrimeFactorizer {
    private long dividend;
    private long from;
    private long to;
    private long divisor;
    private PrimeFactorizer gen;

    private List<Long> primeFactors = new ArrayList<>();
    private boolean done = false;
    private final ReentrantLock lock = new ReentrantLock();

    public RunnableCancellableInterruptiblePrimeFactorizer(long dividend, long from, long to) {
        super(dividend, from, to);
    }

    public List<Long> getPrimeFactors() {
        return primeFactors;
    }

    public void setDone() {
        lock.lock();
        try {
            done = true;
        } finally {
            lock.unlock();
        }
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
                if (dividend % divisor == 0) {
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
                System.out.println(e);
            }
        }
    }


    public static void main(String[] args) {
        RunnableCancellableInterruptiblePrimeFactorizer gen =
                new RunnableCancellableInterruptiblePrimeFactorizer(36, 2, 500);

        Thread thread = new Thread(gen);
        thread.start();
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        gen.setDone();
        thread.interrupt();
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        gen.getPrimeFactors().forEach( (Long prime)-> System.out.print(prime + ", ") );
    }
}
