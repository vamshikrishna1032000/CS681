package edu.umb.cs681.hw02;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class Person {

    private String firstname;
    private String lastname;
    private LocalDate dob;

    private int age;
    private AgeCat ageCat;
    private LinkedList<Dose> doses;

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public AgeCat getAgeCat() {
        return ageCat;
    }

    public void setAgeCat(AgeCat ageCat) {
        this.ageCat = ageCat;
    }

    public LinkedList<Dose> getDoses() {
        return doses;
    }

    public void setDoses(LinkedList<Dose> doses) {
        this.doses = doses;
    }

    public static Dose generateRandomDose() {
        Random random = new Random();
        String[] vacProductNames = {"Vaccine A", "Vaccine B", "Vaccine C", "Vaccine D"};
        String vacProductName = vacProductNames[random.nextInt(vacProductNames.length)];
        String lotNumber = "Lot-" + (random.nextInt(1000) + 1); // Generate a random lot number
        LocalDate date = LocalDate.now().minusDays(random.nextInt(365)); // Random date within the last year
        String[] vacSites = {"Arm", "Leg", "Hip", "Shoulder"};
        String vacSite = vacSites[random.nextInt(vacSites.length)];

        return new Dose(vacProductName, lotNumber, date, vacSite);
    }

    public static void main(String[] args) {
        // Create a list of 1,000+ randomly generated Person instances
        List<Person> people = new ArrayList<>();
        Random random = new Random();

        for (int i = 0; i < 1000; i++) {
            Person person = new Person();
            person.setAge(random.nextInt(100)); // Random age between 0 and 99
            person.setAgeCat(AgeCat.getAgeCategory(person.getAge()));

            // Simulate vaccinations with a random number of doses
            int numDoses = random.nextInt(4); // Random number of doses between 0 and 3
            List<Dose> doses = new ArrayList<>();

            for (int j = 0; j < numDoses; j++) {
                doses.add(generateRandomDose());
            }
            person.setDoses(new LinkedList<>(doses));

            people.add(person);
        }

        // Calculate the vaccination rate for each age category using groupingBy
        Map<AgeCat, Double> vaccinationRateByAgeCategory = people.stream()
                .collect(Collectors.groupingBy(
                        Person::getAgeCat,
                        Collectors.averagingInt(p -> p.getDoses().size())));

        System.out.println("Vaccination Rate by Age Category: " + vaccinationRateByAgeCategory);

        // Calculate the average number of vaccinations administered in each age category
        Map<AgeCat, Double> averageVaccinationsByAgeCategory = people.stream()
                .collect(Collectors.groupingBy(
                        Person::getAgeCat,
                        Collectors.averagingInt(p -> p.getDoses().size())));

        System.out.println("Average Vaccinations by Age Category: " + averageVaccinationsByAgeCategory);

        // Calculate the average age of people who have never been vaccinated using partitioningBy
        Map<Boolean, Double> averageAgeOfUnvaccinated = people.stream()
                .collect(Collectors.partitioningBy(
                        p -> p.getDoses().isEmpty(),
                        Collectors.averagingInt(Person::getAge)));

        System.out.println("Average Age of Unvaccinated: " + averageAgeOfUnvaccinated);
    }
}
