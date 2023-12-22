package edu.umb.cs681.hw07_1;

import java.util.LinkedList;

public class RunnablePrimeGenerator extends PrimeGenerator implements Runnable {
    public RunnablePrimeGenerator(long from, long to) {
        super(from, to);
    }

    @Override
    public void generatePrimes() {
        super.generatePrimes();
    }

    public void run() {
        generatePrimes();
    }
}
