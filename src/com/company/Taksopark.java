package com.company;

import static com.company.Taxi.byProfit;

import java.util.ArrayList;

public class Taksopark {
    private ArrayList<Taxi> taxis;
    private String name;

    final int MAX_TAXIS_PER_FLEET = 10; // maximum number of taxis allowed

    public Taksopark() {
        this.taxis = new ArrayList<>();
        this.name = "Default Taksopark";
    }

    public Taksopark(String name) {
        this.taxis = new ArrayList<>();
        this.name = name;
    }

    // CRUD operations for taxis in the fleet

    public void addTaxi(Taxi taxi) {
        taxis.stream().filter(t -> t.getTaxiNumberID() == taxi.getTaxiNumberID())
                .findFirst()
                .ifPresentOrElse(
                        existingTaxi -> System.out.println("Taxi with ID " + taxi.getTaxiNumberID() + " already exists."),
                        () -> taxis.add(taxi)
                );
    }

    public ArrayList<Taxi> getTaxis() {
        return taxis;
    }

    public void removeTaxi(int taxiNumberID) {
        taxis.stream()
                .filter(taxi -> taxi.getTaxiNumberID() == taxiNumberID)
                .findFirst()
                .ifPresentOrElse(
                        taxi -> {
                            taxis.remove(taxi);
                            System.out.println("Taxi " + taxiNumberID + " removed from the fleet.");
                        },
                        () -> System.out.println("Taxi " + taxiNumberID + " not found in the fleet.")
                );
    }

    public Taxi getTaxi(int taxiNumberID) {
        return taxis.stream().filter(taxi -> taxi.getTaxiNumberID() == taxiNumberID)
                .findFirst()
                .orElseGet(() -> {
                    System.out.println("Taxi " + taxiNumberID + " not found in the fleet.");
                    return null; // return null if taxi not found
                });
    }

    public void replaceTaxi(int taxiNumberID, Taxi newTaxi) {
        taxis.stream().filter(taxi -> taxi.getTaxiNumberID() == taxiNumberID)
                .findFirst()
                .ifPresentOrElse(
                        taxi -> {
                            taxis.remove(taxi);
                            taxis.add(newTaxi);
                            System.out.println("Taxi " + taxiNumberID + " replaced with new taxi.");
                        },
                        () -> System.out.println("Taxi " + taxiNumberID + " not found in the fleet.")
                );
    }

    class Statistics{
        public int getTotalTaxis() {
            return taxis.size();
        }

        public double getTotalProfit() {
            return taxis.stream().mapToDouble(taxi -> taxi.getProfit()).sum();
        }

        public long getNumberOfPremiumTaxis() {
            return taxis.stream().filter(taxi -> taxi instanceof PremiumTaxi).count();

        }
    }

    private void sortTaxisByProfit() {
        taxis.sort(byProfit().
                              thenComparing(Auto.byYear().reversed()).
                              thenComparing(Auto.byMillage()));
    }

    public Taxi getMostProfitableTaxi() {
        sortTaxisByProfit();
        if (taxis.isEmpty()) {
            System.out.println("No taxis in the fleet.");
            return null;
        }
        return taxis.get(taxis.size() - 1); 
    }

    public Taxi getLeastProfitableTaxi() {
        sortTaxisByProfit();
        if (taxis.isEmpty()) {
            System.out.println("No taxis in the fleet.");
            return null;
        }
        return taxis.get(0); // first taxi after sorting by profit
    }

    public void report(){

        class ReportEntry {
            final Driver driver;
            final Taxi taxi;

            ReportEntry(Taxi taxi) {
                this.taxi = taxi;
                this.driver = taxi.getDriver();
            }
            
            @Override
            public String toString() {
                if (taxi instanceof PremiumTaxi) {
                    PremiumTaxi premiumTaxi = (PremiumTaxi) taxi;
                    return "Driver: " + driver.toString() + ", Taxi: " + premiumTaxi.toString() + ", Type: Premium";
                } else {
                    return "Driver: " + driver.toString() + ", Taxi: " + taxi.toString() + ", Type: Standard";
                }
            }
        }

    taxis.forEach(taxi -> new ReportEntry(taxi).toString());
    }

    @Override public String toString() {
        return "Taksopark \"" + name + "\" with " + taxis.size() + " taxis";
    }
}

