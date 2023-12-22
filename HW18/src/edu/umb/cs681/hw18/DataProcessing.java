package edu.umb.cs681.hw18;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class DataProcessing {
    public static void main(String[] args) {
        List<List<String>> data = readCSV();

        // 1. Filtering the data based on the yearBuilt field
        List<List<String>> filteredData = filterData(data);
        System.out.println("Filtered Data: ");
        printData(filteredData);

        //2. Transforming: adding 1 to BackupPower
        List<List<String>> transformedData = transformData(data);
        System.out.println("Transformed Data: ");
        printData(transformedData);

        //3. Aggregate: Average(NumStories)
        double averageNumStories = averageAggregate(data);
        System.out.println("Average NumStories: " + averageNumStories);
    }

    static double averageAggregate(List<List<String>> data) {
        return data.stream().parallel()
                .skip(1)
                .filter(row -> !row.get(13).isBlank())
                .mapToDouble(row -> Double.parseDouble(row.get(13)))
                .average().orElse(0.0);
    }

    static List<List<String>> transformData(List<List<String>> data) {
        return data.stream().parallel()
                .skip(1)
                .peek(row -> {
                    int backUpPower = Integer.parseInt(row.get(16));
                    row.set(16, String.valueOf(backUpPower + 1));
                }).toList();
    }

    static List<List<String>> filterData(List<List<String>> data) {
        return data.stream().parallel()
                .skip(1)
                .filter(row -> row.get(12) != null && !row.get(12).isBlank() && Integer.parseInt(row.get(12)) > 1900)
                .toList();
    }

    static List<List<String>> readCSV() {
        Path path = Paths.get("Colleges_and_Universities.csv");
        List<List<String>> matrix = new LinkedList<>();
        try (Stream<String> lines = Files.lines(path)) {
            matrix = lines
                    .map(line -> Stream.of(line.split(","))
                            .collect(Collectors.toList()))
                    .collect(Collectors.toList());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return matrix;
    }

    public static void printData(List<List<String>> data) {
        data.forEach(row -> System.out.println(String.join(",", row)));
    }
}