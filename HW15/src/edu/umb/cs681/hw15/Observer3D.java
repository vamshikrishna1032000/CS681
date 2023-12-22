package edu.umb.cs681.hw15;

public class Observer3D implements Observer<StockEvent> {

    @Override
    public void update(Observable<StockEvent> sender, StockEvent event) {
        System.out.println("Ticker from 3D Observer:"+event.ticker());
        System.out.println("Quote from 3D Observer:"+event.quote());
    }
}