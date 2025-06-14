package com.company;

import java.util.Comparator;

public class Taxi extends Auto {

    private static int taxiNumberCounter = 1; // static counter to generate unique taxi numbers
    
    private Driver driver; // driver of the taxi, for simplification, one Driver per one Taxi
    private int taxiNumberID; // unique taxi number ID
    private double profit;

    static int PROFIT_PER_KM = 2; // profit per kilometer driven

    public Taxi(String make, String modell, Driver driver) {
        super(make, modell);
        this.taxiNumberID = taxiNumberCounter;
        this.driver = driver;
        this.profit = 0.0; // initial profit is zero
        taxiNumberCounter++;
    }

    public Taxi(String string, String string2, int i, String string3, int j, int k) {
        //TODO Auto-generated constructor stub
    }

    public int getTaxiNumberID() {
        return taxiNumberID;
    }

    public Driver getDriver() {
        return driver;
    }

    public void setDriver(Driver driver) {
        this.driver = driver;
    }

    public double getProfit() {
        return profit;
    }

    public static int getProfitPerKm() {
        return PROFIT_PER_KM;
    }

    @Override
    protected boolean drive(double distance, int profitPerKm) {
        if (super.drive(distance, profitPerKm)) {
            profit += distance * profitPerKm;
            return true; // if the drive method in Auto returns true, we continue to calculate profit
        }
        else {
            return false; // if the drive method in Auto returns false, we stop here
        }
        
    }

    @Override
    public boolean drive(double distance) {
        return this.drive(distance, PROFIT_PER_KM); // use the default profit per km
    }

    @Override
    public boolean drive() {
        return this.drive(getDefaultDistance(), PROFIT_PER_KM); // use the default distance and profit per km
    }

    @Override
    public String toString() {
        return super.toString() +
        "Taxi{" +
                "taxiNumberID=" + taxiNumberID +
                ", driver=" + driver.toString() +
                ", profit=" + profit +
                '}';
    }

    public static Comparator<Taxi> byProfit(){
        return Comparator.comparingDouble(Taxi::getProfit);
    }
}
