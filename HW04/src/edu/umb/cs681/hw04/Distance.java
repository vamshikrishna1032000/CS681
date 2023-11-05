package edu.umb.cs681.hw04;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Distance {
    public static double get(List<Double> p1, List<Double> p2) {
        return Distance.get(p1, p2, new Euclidean());
    }

    public static double get(List<Double> p1, List<Double> p2, DistanceMetric metric) {
        return metric.distance(p1, p2);
    }

    public static List<List<Double>> matrix(List<List<Double>> points) {
        return Distance.matrix(points, new Euclidean());
    };

    public static List<List<Double>> matrix(List<List<Double> > points, DistanceMetric metric) {
        int numOfPoints = points.size();

        return IntStream.range(0, numOfPoints)
                .parallel()
                .mapToObj(i -> calculateDistancesForPoint(points.get(i), points, metric))
                .collect(Collectors.toList());
    }

    private static List<Double> calculateDistancesForPoint(List<Double> current, List<List<Double>> points, DistanceMetric metric) {
        return points.stream()
                .map(peer -> Distance.get(current, peer, metric))
                .collect(Collectors.toList());
    }

    private static List<List<Double>> initDistanceMatrix(int numOfPoints){
        List<List<Double>> distanceMatrix = new ArrayList<>(numOfPoints);
        for(int i=0; i < numOfPoints; i++) {
            Double[] vector = new Double[numOfPoints];
            Arrays.fill(vector, 0.0);
            distanceMatrix.add( Arrays.asList(vector) );
        }
        return distanceMatrix;
    }

    public static void main(String[] args) {
        int numPoints = 1000;
        int numDimensions = 100;
        List<List<Double>> points = generateRandomPoints(numPoints, numDimensions);

        List<List<Double>> distanceMatrix = matrix(points, new Euclidean());
        // Print the first row of the distance matrix for verification
        System.out.println(distanceMatrix.get(0).subList(0, 10));
    }

    public static List<List<Double>> generateRandomPoints(int numPoints, int numDimensions) {
        Random random = new Random();
        List<List<Double>> points = new ArrayList<>();
        for (int i = 0; i < numPoints; i++) {
            List<Double> point = new ArrayList<>();
            for (int j = 0; j < numDimensions; j++) {
                point.add(random.nextDouble());
            }
            points.add(point);
        }
        return points;
    }

}