package edu.umb.cs681.hw03;



import java.util.LinkedList;
import java.util.Random;

public class CarPriceResultHolder {
    private int numCarExamined;
    private double average;

    public CarPriceResultHolder(int numCarExamined, double average) {
        this.numCarExamined = numCarExamined;
        this.average = average;
    }

    public CarPriceResultHolder() {

    }

    @Override
    public String toString() {
        return "CarPriceResultHolder{" +
                "numCarExamined=" + numCarExamined +
                ", average=" + average +
                '}';
    }

    public static void main(String[] args) {
        LinkedList<Car> cars = new LinkedList<>();
        Random random = new Random();

        for (int i = 0; i < 10; i++) {
            String make = "Brand" + (i + 1);
            String model = "Model" + (i + 1);
            int year = 2010 + random.nextInt(10); // Random year between 2010 and 2019
            int mileage = random.nextInt(100000); // Random mileage
            float price = 10000 + random.nextFloat() * 40000; // Random price between $10,000 and $50,000

            Car car = new Car(make, model, year, mileage, price);
            car.setDenCnt(cars);
            cars.add(car);
        }

        CarPriceResultHolder carPriceResultHolder = cars.stream()
                .map(Car::getPrice)
                .reduce(new CarPriceResultHolder(),
                        (result, price) -> {
                            result.accumulate(price);
                            return result;
                        },
                        (result1, result2) -> {
                            result1.combine(result2);
                            return result1;
                        });
        System.out.println("CarPriceResultHolder: "+ carPriceResultHolder);
        System.out.println("Average Price: " + carPriceResultHolder.getAverage());
    }

    public int getNumCarExamined() {
        return numCarExamined;
    }

    public void setNumCarExamined(int numCarExamined) {
        this.numCarExamined = numCarExamined;
    }

    public double getAverage() {
        return average;
    }

    public void accumulate(double price) {
        this.numCarExamined++;
        this.average = ((this.average * (this.numCarExamined - 1)) + price) / this.numCarExamined;
    }

    public void combine(CarPriceResultHolder other) {
        int totalNumCars = this.numCarExamined + other.numCarExamined;
        if (totalNumCars == 0) {
            return;
        }

        double totalAverage = ((this.average * this.numCarExamined) + (other.average * other.numCarExamined)) / totalNumCars;
        this.numCarExamined = totalNumCars;
        this.average = totalAverage;
    }

    public void setAverage(double average) {
        this.average = average;
    }
}
