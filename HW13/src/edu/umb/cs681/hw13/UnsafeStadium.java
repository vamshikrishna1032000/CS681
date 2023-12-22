package edu.umb.cs681.hw13;

public class UnsafeStadium {
	
	private int seatsbooked = 0;
	
	public void bookseat(){
		seatsbooked=seatsbooked+1;
		System.out.println("Thread "+Thread.currentThread().getId() +" Booked one seat, seats booked = "+seatsbooked);
	}
	
	public void cancelBooking(){
		seatsbooked=seatsbooked-seatsbooked;
		System.out.println("Thread "+Thread.currentThread().getId() + " Cancelled one booked seat, seats booked = "+seatsbooked);
	}
	
	public double getnumSeatsbooked() { 
		return this.seatsbooked; 
	}
}