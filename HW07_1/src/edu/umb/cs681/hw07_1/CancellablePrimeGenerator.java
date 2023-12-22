package edu.umb.cs681.hw07_1;

public class CancellablePrimeGenerator extends PrimeGenerator implements Runnable {
    private boolean done = false;

    public CancellablePrimeGenerator(long from, long to) {
        super(from, to);
    }

    public void setDone() {
        done = true;
    }

    public void run() {
        for (long n = from; n <= to; n++) {
            if (done) {
                System.out.println("Stopped generating prime numbers.");
                this.primes.clear();
                break;
            }
            if (isPrime(n)) {
                this.primes.add(n);
            }
        }
    }
}
