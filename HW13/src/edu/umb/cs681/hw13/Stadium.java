package edu.umb.cs681.hw13;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class Stadium {
	
	private int seatsbooked = 0;
	private ReentrantLock lock = new ReentrantLock();
	
	public void bookseat(){
		lock.lock();
		try{		
			seatsbooked=seatsbooked+1;
			System.out.println("Thread "+Thread.currentThread().getId() +" Booked one seat, seats booked = "+seatsbooked);
		}
		finally{
			lock.unlock();
		}
	}
	
	public void cancelBooking(){
		lock.lock();
		try{
			seatsbooked=seatsbooked-1;
			System.out.println("Thread "+Thread.currentThread().getId() + " Cancelled one booked seat, seats booked = "+seatsbooked);
		}
		finally{
			lock.unlock();
		}
	}
	
	public double getnumSeatsbooked() {
		lock.lock();
		try {
			return this.seatsbooked;
		} finally {
			lock.unlock();
		}
	}
	
	public static void main(String[] args){
		Stadium stadium = new Stadium();
		List<Thread> threads = new ArrayList<>();
		List<BookSeatRunnable> bookingThreads = new ArrayList<>();
		List<CancelBookingRunnable> cancellingThreads = new ArrayList<>();	
		for(int i = 0; i < 20; i++){
			BookSeatRunnable buy = new BookSeatRunnable(stadium);
			CancelBookingRunnable cancel = new CancelBookingRunnable(stadium);
			bookingThreads.add(buy);
		    cancellingThreads.add(cancel);
			threads.add(new Thread(buy));
			threads.add(new Thread(cancel));
		}
		for(Thread t:threads) {
			t.start();
		}
		try {
	        Thread.sleep(5000);
	    } catch (InterruptedException exception) {
	    	exception.printStackTrace();
	    }
		for (BookSeatRunnable t : bookingThreads) {
		    t.setDone();
		}
		for (CancelBookingRunnable t : cancellingThreads) {
		    t.setDone();
		}
		for(Thread t:threads) {
	        t.interrupt();
	    }
		for(Thread t:threads) {
	        try {
	            t.join();
	        } catch (InterruptedException exception) {
	        	exception.printStackTrace();
	        }
	    }	
	}
}