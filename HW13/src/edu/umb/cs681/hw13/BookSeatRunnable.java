package edu.umb.cs681.hw13;

public class BookSeatRunnable implements Runnable {
	
	private Stadium stadium;
	private volatile boolean done = false;
	
	public BookSeatRunnable(Stadium stadium) {
		this.stadium=stadium;
	}
	
	public void setDone() {
		done = true;
	}
	
	public void run() {
		if(!done) {
			stadium.bookseat();
		}
		try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
        	System.out.println(e);
    		return;
        }
	}

}