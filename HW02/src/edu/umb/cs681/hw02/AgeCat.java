package edu.umb.cs681.hw02;

import java.util.Arrays;

public enum AgeCat {
    YOUNG(0, 17), MID(18, 64), OLD(65, Integer.MAX_VALUE);

    private final int minAge;
    private final int maxAge;

    AgeCat(int minAge, int maxAge) {
        this.minAge = minAge;
        this.maxAge = maxAge;
    }

    public int getMinAge() {
        return minAge;
    }

    public int getMaxAge() {
        return maxAge;
    }

    public static AgeCat getAgeCategory(int age) {
        return Arrays.stream(AgeCat.values())
                .filter(category -> age >= category.minAge && age <= category.maxAge)
                .findFirst()
                .orElse(null); // You can return null or a default category for ages outside the defined ranges
    }
}
