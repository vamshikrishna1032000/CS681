package edu.umb.cs681.hw15;

public class MultiThreadedTest {

    public static void main(String[] args) {
        StockQuoteObservable stockObservable = new StockQuoteObservable();
        Observer<StockEvent> observer1 = new Observer3D();
        Observer<StockEvent> observer2 = new LineChartObserver();
        Observer<StockEvent> observer3 = new TableObserver();

        stockObservable.addObserver(observer1);
        stockObservable.addObserver(observer2);
        stockObservable.addObserver(observer3);

        int numThreads = 10;
        for (int i = 0; i < numThreads; i++) {
            Thread thread = new Thread(() -> {
                for (int j = 0; j < 5; j++) {
                    String ticker = "AAPL";
                    double quote = Math.random() * 1000;
                    stockObservable.changeQuote(ticker, quote);
                }
            });
            thread.start();
        }


        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        System.out.println("Final Stock Information:");
        stockObservable.getStockMap().forEach((ticker, quote) ->
                System.out.println("Ticker: " + ticker + ", Quote: " + quote));
    }
}
