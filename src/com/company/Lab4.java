package com.company;

import javax.swing.SwingUtilities;

public class Lab4 {

    public static void run(){
        SwingUtilities.invokeLater(() -> {
            Taksopark park = new Taksopark("My Taxi Park");
            TaxiParkFrameLab4 frame = new TaxiParkFrameLab4(park);
            frame.setVisible(true);
        });
    }
}
