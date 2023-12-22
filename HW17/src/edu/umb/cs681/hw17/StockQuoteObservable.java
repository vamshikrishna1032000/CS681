package edu.umb.cs681.hw17;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;

public class StockQuoteObservable extends Observable<StockEvent> {
    private final ReentrantLock lockTQ = new ReentrantLock();
    private Map<String, Double> stock = new HashMap<>();

    public void changeQuote(String ticker, double quote) {
        lockTQ.lock();
        try {
            stock.put(ticker, quote);
        } finally {
            lockTQ.unlock();
        }

        notifyObservers(new StockEvent(ticker, quote));
    }

    public Map<String, Double> getStockMap() {
        lockTQ.lock();
        try {
            return new HashMap<>(stock);
        } finally {
            lockTQ.unlock();
        }
    }
}
