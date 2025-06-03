package com.company;

import java.time.Year;
import java.util.Comparator;


public class Auto {
    
    private String make; // name of the car manufacturer
    private String modell; // name of the car model
    private int year; // year of manufacture
    private double milleage; // distance traveled by the car
    private double fuelLevel; // current fuel level in liters
    private boolean isRunning; // indicates if the car is currently running    
    
    static int numberOfAutos = 0; 

    final static int MAX_AUTOS = 10; // maximum number of cars allowed
    final static int fuelEfficiency = 15; // fuel efficiency in km/l
    final static int defaultDistance = 10; // default distance to drive in km
    final static double defaultFuelLevel = 5.0; // default fuel level in liters

    private boolean validation(){
        if (numberOfAutos == MAX_AUTOS) {
            return false; // cannot create more cars
        }
        return true; // can create more cars
    }

    protected boolean isEnoughFuel(double distance) {
        double fuelNeeded = distance / fuelEfficiency; // assuming 15 km/l fuel efficiency
        return fuelNeeded <= this.fuelLevel;
    }
    
    public Auto() {
        if (!validation()) {
            System.out.println("Cannot create more cars. Maximum limit reached.");
            return; // exit constructor if max limit is reached
        }
        else {
            numberOfAutos++;
            this.make = "Unknown";
            this.modell = "Unknown";
            this.year = Year.now().getValue();
            this.milleage = 0.0;
            this.fuelLevel = 0.0;
        }
    }

    public Auto(String make, String modell) {
        if (!validation()) {
            System.out.println("Cannot create more cars. Maximum limit reached.");
            return; // exit constructor if max limit is reached
        }
        else {
            numberOfAutos++;
            this.make = make;
            this.modell = modell;
            this.year = Year.now().getValue();
            this.milleage = 0.0;
            this.fuelLevel = 0.0;
        }
    }

    public Auto(String make, String modell, int year, double milleage, double fuelLevel) {
        if (!validation()) {
            System.out.println("Cannot create more cars. Maximum limit reached.");
            return; // exit constructor if max limit is reached
        }
        else {
            numberOfAutos++;
            this.make = make;
            this.modell = modell;
            this.year = year;
            this.milleage = milleage;
            this.fuelLevel = fuelLevel;
        }
    }

    public String getMake() {
        return this.make;
    }

    public String getModell() {
        return modell;
    }
    public int getYear() {
        return year;
    }
    public double getMilleage() {
        return milleage;
    }
    public double getFuelLevel() {
        return fuelLevel;
    }
    public void setMake(String make) {
        this.make = make;
    }
    public void setModell(String modell) {
        this.modell = modell;
    }
    public void setYear(int year) {
        this.year = year;
    }
    public void setMilleage(double milleage) {
        this.milleage = milleage;
    }
    public void setFuelLevel(double fuelLevel) {
        this.fuelLevel = fuelLevel;
    }
    public static int getFuelEfficiency() {
        return fuelEfficiency;
    }
    public static int getDefaultDistance() {
        return defaultDistance;
    }
    public static int getNumberOfAutos() {
        return numberOfAutos;
    }
    public boolean isRunning() {
        return isRunning;
    }
    public String toString() {
        return "Auto{" +
                "make='" + make + '\'' +
                ", modell='" + modell + '\'' +
                ", year=" + year +
                ", milleage=" + milleage +
                ", fuelLevel=" + fuelLevel +
                '}';
    }
    public void start() {
        if (this.isRunning) {
            System.out.println("The car is already running.");
            return;
        }
        this.isRunning = true;
        System.out.println("The car has started.");
    }
    public void stop() {
        if (!this.isRunning) {
            System.out.println("The car is already stopped.");
            return;
        }
        this.isRunning = false;
        System.out.println("The car has stopped.");
    }
    public void refuel(double liters) {
        if (this.isRunning) {
            System.out.println("Cannot refuel while the car is running. Please stop the car first.");
            return;
        }
        else if (liters < 0) {
            System.out.println("Cannot refuel with negative liters.");
            return;
        }
        this.fuelLevel += liters;
        System.out.println("Refueled " + liters + " liters. Current fuel level: " + this.fuelLevel + " liters.");
    }

    public void refuel() {
        if(this.isRunning) {
            System.out.println("Cannot refuel while the car is running. Please stop the car first.");
            return;
        }
        double liters =  defaultFuelLevel; // default fuel level to refuel
        this.fuelLevel += liters;
        System.out.println("Refueled " + liters + " liters. Current fuel level: " + this.fuelLevel + " liters.");
    }

    protected boolean drive(double distance, int profitPerKm) {
        if (!this.isRunning) {
            System.out.println("The car is not running. Please start the car first.");
            return false;
        }
        else if (distance < 0) {
            System.out.println("Cannot drive negative distance.");
            return false;
        }
        else if (!isEnoughFuel(distance)) {
            System.out.println("Not enough fuel to drive this distance.");
            return false;
        }
        this.milleage += distance;
        this.fuelLevel -= distance/fuelEfficiency;
        System.out.println("Drove " + distance + " km. Current milleage: " + this.milleage + " km, fuel level: " + this.fuelLevel + " liters.");
        return true;
    }

    public boolean drive(double distance) {
        return this.drive(distance, 0); 
    }

    public boolean drive() {
        return this.drive(getDefaultDistance(), 0); 
    }

    public static Comparator<Auto> byYear(){
        return Comparator.comparing(a -> a.getYear());
    }

    public static Comparator<Auto> byMillage(){
        return Comparator.comparing(a -> a.getMilleage());
    }

    public static Comparator<Auto> byFuelLevel(){
        return Comparator.comparing(a -> a.getFuelLevel());
    }
}
