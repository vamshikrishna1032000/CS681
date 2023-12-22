package edu.umb.cs681.hw17;

public class LineChartObserver implements Observer<StockEvent>{
    @Override
    public void update(Observable<StockEvent> sender, StockEvent event) {
        System.out.println("Ticker from Line Observer: "+event.ticker());
        System.out.println("Quote from Line Observer: "+event.quote());
    }
}