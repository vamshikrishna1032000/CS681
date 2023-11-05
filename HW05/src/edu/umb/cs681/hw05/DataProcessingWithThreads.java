package edu.umb.cs681.hw05;

import java.util.List;
import java.util.concurrent.*;

import static edu.umb.cs681.hw05.DataProcessing.*;

public class DataProcessingWithThreads {
    public static void main(String[] args) {
        List<List<String>> data = readCSV();

        // Create a CountDownLatch with a count of 3 for the three worker threads
        CountDownLatch latch = new CountDownLatch(3);

        // Create an ExecutorService with a fixed thread pool size of 3
        ExecutorService executorService = Executors.newFixedThreadPool(3);

        try {
            // Step 1: Filtering Data (Year Built)
            Future<List<List<String>>> filteredDataFuture = executorService.submit(() -> {
                List<List<String>> filteredData = filterData(data);
                latch.countDown(); // Notify that this task is done
                return filteredData;
            });

            // Step 2: Transforming Data (Adding 1 to BackupPower)
            Future<List<List<String>>> transformedDataFuture = executorService.submit(() -> {
                List<List<String>> transformedData = transformData(filteredDataFuture.get());
                latch.countDown(); // Notify that this task is done
                return transformedData;
            });

            // Step 3: Aggregating Data (Average NumStories)
            Future<Double> averageNumStoriesFuture = executorService.submit(() -> {
                double averageNumStories = averageAggregate(filteredDataFuture.get());
                latch.countDown(); // Notify that this task is done
                return averageNumStories;
            });

            // Wait for all worker threads to finish
            System.out.println("Waiting for the threads to finish");
            latch.await();

            // Get results
            List<List<String>> filteredData = filteredDataFuture.get();
            List<List<String>> transformedData = transformedDataFuture.get();
            double averageNumStories = averageNumStoriesFuture.get();

            System.out.println("Filtered Data:");
            printData(filteredData);

            System.out.println("Transformed Data:");
            printData(transformedData);

            System.out.println("Average NumStories: " + averageNumStories);
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        } finally {
            // Shutdown the ExecutorService
            executorService.shutdown();
        }
    }
}
