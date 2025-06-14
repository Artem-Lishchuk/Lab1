package com.company;

public class Lab5 {
    
    public static void run() {
        Taksopark park = new Taksopark("My Taxi Park");
        TaxiParkFrameLab5 frame = new TaxiParkFrameLab5(park);
        frame.setVisible(true);
    }
}
