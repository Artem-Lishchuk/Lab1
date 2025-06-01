package com.company;

import java.time.Year;

public class Auto {
    
    private String make; // name of the car manufacturer
    private String modell; // name of the car model
    private int year; // year of manufacture
    private double milleage; // distance traveled by the car
    private double fuelLevel; // current fuel level in liters
    static int numberOfAutos = 0; // unique identifier for each car

    final static int MAX_AUTOS = 10; // maximum number of cars allowed

    private boolean validation(){
        if (numberOfAutos == MAX_AUTOS) {
            return false; // cannot create more cars
        }
        return true; // can create more cars
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

    public static int getNumberOfAutos() {
        return numberOfAutos;
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

    
}
