package edu.umb.cs681.hw07_1;

import java.util.concurrent.locks.ReentrantLock;

public class RunnableCancellablePrimeGenerator extends RunnablePrimeGenerator {
    private final ReentrantLock lock = new ReentrantLock();
    private boolean done = false;

    public RunnableCancellablePrimeGenerator(long from, long to) {
        super(from, to);
    }

    public void setDone() {
        lock.lock();
        try {
            done = true;
        } finally {
            lock.unlock();
        }
    }

    @Override
    public void generatePrimes() {
        for (long n = from; n <= to; n++) {
            lock.lock();
            try {
                if (done) {
                    System.out.println("Stopped generating prime numbers.");
                    this.primes.clear();
                    break;
                }
                if (isPrime(n)) {
                    this.primes.add(n);
                }
            } finally {
                lock.unlock();
            }
        }
    }
}
