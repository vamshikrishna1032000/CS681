package edu.umb.cs681.hw15;

public class TableObserver implements Observer<StockEvent> {
    @Override
    public void update(Observable<StockEvent> sender, StockEvent event) {
        System.out.println("Ticker from Table Observer: "+event.ticker());
        System.out.println("Quote from Table Observer: "+event.quote());
    }
}