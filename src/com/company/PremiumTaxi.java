package com.company;
public class PremiumTaxi extends Taxi{
    private boolean hasWiFi;
    private boolean hasMiniBar;
    private double ratePerKmMultiplier; // multiplier for the rate per kilometer. E.g.  if the base rate is $2, and this multiplier is 1.5, then the rate per km is $3.

    public PremiumTaxi(String make, String model, boolean hasWiFi, boolean hasMiniBar, double ratePerKmMultiplier, Driver driver) {
        super(make, model, driver);
        this.hasWiFi = hasWiFi;
        this.hasMiniBar = hasMiniBar;
        this.ratePerKmMultiplier = ratePerKmMultiplier;
    }

    public boolean hasWiFi() { return hasWiFi; }
    public boolean hasMiniBar() { return hasMiniBar; }
    public double ratePerKmMultiplier() { return ratePerKmMultiplier; }

    public void setHasWiFi(boolean hasWiFi) { this.hasWiFi = hasWiFi; }
    public void setHasMiniBar(boolean hasMiniBar) { this.hasMiniBar = hasMiniBar; }
    public void setRatePerKmMultiplier(double ratePerKmMultiplier) {
        this.ratePerKmMultiplier = ratePerKmMultiplier;
    }

    @Override
    public String toString() {
        return super.toString() +
                ", WiFi: " + hasWiFi +
                ", MiniBar: " + hasMiniBar +
                ", Multiplier of Rate per Km: $" + ratePerKmMultiplier;
    }

    @Override
    public boolean drive(double distance) {
        return super.drive(distance, (int)(getProfitPerKm() * ratePerKmMultiplier));
    }

    @Override
    public boolean drive() {
        return super.drive(getDefaultDistance(), (int)(getProfitPerKm() * ratePerKmMultiplier));
    }



}
