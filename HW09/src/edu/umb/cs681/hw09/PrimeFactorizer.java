package edu.umb.cs681.hw09;



public class PrimeFactorizer {
    private long dividend;
    private long from;
    private long to;
    private long divisor;


    public PrimeFactorizer(long dividend, long from, long to) {
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
        this.divisor = 2;

    }

    private boolean isPrime(long n) {
        if (n <= 1) {
            return false;
        }
        for (long i = 2; i * i <= n; i++) {
            if (n % i == 0) {
                return false;
            }
        }
        return true;
    }


    public void generatePrimeFactors() {
        long divisor = 2;
        while (dividend != 1 && divisor <= dividend) {
            if (isPrime(divisor) && dividend % divisor == 0) {
                System.out.println(divisor);
                dividend /= divisor;
            } else {
                divisor++;
            }
        }
    }

    public static void main(String[] args) {
        PrimeFactorizer pf = new PrimeFactorizer(36, 2, 7);
        pf.generatePrimeFactors();
    }
}
