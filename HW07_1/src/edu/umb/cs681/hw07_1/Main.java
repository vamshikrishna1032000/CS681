package edu.umb.cs681.hw07_1;


import java.time.LocalDateTime;
import java.util.List;

public class Main {
    public static void main(String[] args) {
    	RunnableCancellablePrimeGenerator PrimeGenerator1 = new RunnableCancellablePrimeGenerator(200, 300);
		RunnableCancellablePrimeGenerator PrimeGenerator2 = new RunnableCancellablePrimeGenerator(170, 220);
		
		Thread t1 = new Thread(()->{
			PrimeGenerator1.generatePrimes();
		});

		Thread t2 = new Thread(()-> {
			PrimeGenerator2.generatePrimes();
		});

		t1.start();
		t2.start();
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		PrimeGenerator1.setDone();
		PrimeGenerator2.setDone();
		
		
		try {
			t1.join();
			t2.join();
		} catch (InterruptedException e) {
			System.out.println(e.getMessage());
		}



		List<Long> primes =PrimeGenerator1.getPrimes();
    		 
        System.out.println("Thread-1");
	     for (Long prime : primes) {
	    	    System.out.print(prime + ", ");
	    	}
	    	System.out.println();
	        System.out.println(primes.size() + " primes are found.");

	    primes =PrimeGenerator2.getPrimes();
	    System.out.println("Thread-2");
	    for (Long prime : primes) {
	   	    System.out.print(prime + ", ");
	   	}
	   	System.out.println();
	       System.out.println(primes.size() + " primes are found.");
	   }
}
