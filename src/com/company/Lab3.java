package com.company;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Lab3 {
    Lab3() {
    }

    public static void run() {
        Driver d1 = new Driver("Ivan Ivanov",  "AA123456");
        Driver d2 = new Driver("Oleh Petrenko","BB987654");
        Driver d3 = new Driver("Maria Kovalenko","CC555777");

        Taxi        t1 = new Taxi("Toyota",  "Prius",   d1);      
        Taxi        t2 = new Taxi("Skoda",   "Octavia", d2);          
        PremiumTaxi t3 = new PremiumTaxi("Mercedes","E-Class",
                                         true,true,1.8, d3);      
        Taksopark park = new Taksopark("Demo Fleet");
        park.addTaxi(t1);
        park.addTaxi(t2);
        park.addTaxi(t3);
        park.addTaxi(t2);                

        t1.start();  t1.drive(15);  t1.stop();     
        t2.refuel(); t2.start();  t2.drive();  t2.stop();         
        t3.start();  t3.drive(10); t3.stop();                   


        Taksopark.Statistics stat = park.new Statistics();
        System.out.printf("\nFleet size: %d,  profit sum: $%.2f,  premium count: %d%n",
                stat.getTotalTaxis(), stat.getTotalProfit(), stat.getNumberOfPremiumTaxis());


        System.out.println("Most profitable:  " + park.getMostProfitableTaxi());
        System.out.println("Least profitable: " + park.getLeastProfitableTaxi());

        System.out.println("\n--- REPORT ---");
        park.report();

        List<Taxi> list = new ArrayList<>(park.getTaxis());

        list.sort(Taxi.byProfit());
        System.out.println("\nSorted by PROFIT ↑ : " + list);

        list.sort((a,b) -> Integer.compare(b.getYear(), a.getYear()));
        System.out.println("Sorted by YEAR ↓   : " + list);


        list.sort(new Comparator<>() {
            @Override
            public int compare(Taxi a, Taxi b) {
                int c = Double.compare(a.getMilleage(), b.getMilleage());
                if (c != 0) return c;
                return Double.compare(b.getProfit(), a.getProfit());
            }
        });
        System.out.println("Sorted by MILEAGE ↑ then PROFIT ↓ : " + list);

 
        Taxi replacement = new Taxi("VW","Golf", new Driver("Stepan Horokh","DD001122"));
        park.replaceTaxi(t2.getTaxiNumberID(), replacement);        
        park.removeTaxi(t1.getTaxiNumberID());                   
        System.out.println("\nAfter replace/remove → " + park);


        Auto[] autos = { t3, replacement };
        System.out.println("\nArray<Auto> iteration:");
        for (Auto a : autos) System.out.println(a);
    }
}
