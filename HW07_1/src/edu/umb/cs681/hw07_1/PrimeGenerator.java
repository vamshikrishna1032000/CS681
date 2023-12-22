package edu.umb.cs681.hw07_1;

import java.util.LinkedList;

public class PrimeGenerator {
    protected long from, to;
    protected LinkedList<Long> primes = new LinkedList<>();

    public PrimeGenerator(long from, long to) {
        this.from = from;
        this.to = to;
    }



    public LinkedList<Long> getPrimes() {
        return primes;
    }

    protected boolean isEven(long n) {
        return n % 2 == 0;
    }

    protected boolean isPrime(long n) {
        // 1 or lower numbers are not prime.
        if (n <= 1) {
            return false;
        }
        // Even numbers are not prime, except for 2.
        if (n > 2 && isEven(n)) {
            return false;
        }
        // Find a number "i" that can divide "n"
        long i = (long) Math.sqrt(n);
        while (n % i != 0 && i >= 1) {
            i--;
        }
        // If such a number "i" is found, n is not prime. Otherwise, n is prime.
        return i == 1;
    }

    public void generatePrimes() {
        for (long n = from; n <= to; n++) {
            if (isPrime(n)) {
                primes.add(n);
            }
        }
    }

    public long getNextPrime() {
        long n = primes.getLast();
        while (true) {
            n++;
            if (isPrime(n)) {
                return n;
            }
        }
    }
}