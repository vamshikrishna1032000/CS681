package edu.umb.cs681.hw03;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.Random;

public class Car {
    private final String make;
    private final String model;
    private final int mileage;
    private final int year;
    private final double price;
    private int dom;


    public Car(String make, String model, int year, int mileage, double price) {
        this.make = make;
        this.model = model;
        this.mileage = mileage;
        this.year = year;
        this.price = price;

    }



    public static void separateCarsBySortingPolicy(
            LinkedList<Car> cars, String sortingPolicy, int threshold) {

        // Sort the cars based on the chosen sorting policy
        switch (sortingPolicy) {
            case "price":
                cars.sort(Comparator.comparingDouble(Car::getPrice));
                break;
            case "year":
                cars.sort(Comparator.comparingInt(Car::getYear));
                break;
            case "mileage":
                cars.sort(Comparator.comparingInt(Car::getMileage));
                break;
            case "domination":
                cars.sort((car1, car2) -> Integer.compare(car2.getDominationCount(), car1.getDominationCount()));
                break;
            default:
                System.out.println("Invalid sorting policy");
                return;
        }

        // Separate cars into "HIGH" and "LOW" groups
        LinkedList<Car> highGroup = new LinkedList<>();
        LinkedList<Car> lowGroup = new LinkedList<>();

        for (Car car : cars) {
            if (car.getPrice() >= threshold) {
                highGroup.add(car);
            } else {
                lowGroup.add(car);
            }
        }

        // Calculate average, highest, and lowest values for each group
        double highAvg = highGroup.stream().map(Car::getPrice).reduce((double) 0, Double::sum) / highGroup.size();
        double highMax = highGroup.stream().map(Car::getPrice).max(Double::compareTo).orElse((double) 0);
        double highMin = highGroup.stream().map(Car::getPrice).min(Double::compareTo).orElse((double) 0);

        double lowAvg = lowGroup.stream().map(Car::getPrice).reduce((double) 0, Double::sum) / lowGroup.size();
        double lowMax = lowGroup.stream().map(Car::getPrice).max(Double::compareTo).orElse((double) 0);
        double lowMin = lowGroup.stream().map(Car::getPrice).min(Double::compareTo).orElse((double) 0);

        int highGroupSize = highGroup.size();
        int lowGroupSize = lowGroup.size();

        // Print the results for the chosen sorting policy
        System.out.println("Sorting Policy: " + sortingPolicy);
        System.out.println("Threshold: " + threshold);
        System.out.println("HIGH Group - Average: " + highAvg + ", Max: " + highMax + ", Min: " + highMin + ", Count: " + highGroupSize);
        System.out.println("LOW Group - Average: " + lowAvg + ", Max: " + lowMax + ", Min: " + lowMin + ", Count: " + lowGroupSize);
    }

    public String getMake() {
        return this.make;
    }

    public String getModel() {
        return this.model;
    }

    public int getMileage() {
        return this.mileage;
    }

    public int getYear() {
        return this.year;
    }

    public double getPrice() {
        return this.price;
    }

    public void setDenCnt(LinkedList<Car> udCar) {
        int cnt = 0;
        for (Car ca : udCar) {
            if (!ca.equals(this)) {
                if ((this.getYear() >= ca.getYear()) && (this.getMileage() <= ca.getMileage()) && (this.getPrice() <= ca.getPrice())) {
                    if ((this.getYear() > ca.getYear()) || (this.getMileage() < ca.getMileage()) || (this.getPrice() < ca.getPrice())) {
                        cnt++;
                    }
                }
            }
        }
        this.dom = cnt;
    }

    public int getDominationCount() {
        return dom;
    }

}