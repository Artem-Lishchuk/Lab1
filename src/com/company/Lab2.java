package com.company;


public class Lab2 {
    Lab2() {
    }

    public static void run(){
       Driver d1 = new Driver("Ivan Ivanov",  "AA123456");
        Driver d2 = new Driver("Oleh Petrenko", "BB987654");
        Driver d3 = new Driver("Maria Kovalenko","CC555777");

        Taxi        t1 = new Taxi("Toyota",  "Prius",   d1);                       
        Taxi        t2 = new Taxi("Skoda",   "Octavia", d2);
        PremiumTaxi t3 = new PremiumTaxi("Mercedes","E-Class",
                                         true,true,1.8,d3);                 

        Taksopark park = new Taksopark("Bolt Fleet");
        park.addTaxi(t1);
        park.addTaxi(t2);
        park.addTaxi(t3);

        park.addTaxi(t2); 

        Auto[] autos = { t1, t2, t3 };
        System.out.println("\n--- Array<Auto> iteration:");
        for (Auto a : autos) System.out.println(a);


        t1.start();           t1.drive(15);   t1.stop();     
        t3.start();           t3.drive(15);   t3.stop();     


        t2.refuel();          // +5 л
        t2.start();           t2.drive();     t2.stop();          // default 10 км


        System.out.println("\nMost profitable:   " + park.getMostProfitableTaxi());
        System.out.println("Least profitable:  " + park.getLeastProfitableTaxi());


        Taksopark.Statistics stats = park.new Statistics();
        System.out.printf("\nFleet size: %d | Total profit: $%.2f | Premium count: %d%n",
                          stats.getTotalTaxis(), stats.getTotalProfit(), stats.getNumberOfPremiumTaxis());


        System.out.println("\n--- Report:");
        park.report();   

        Taxi replacement = new Taxi("VW", "Golf", new Driver("Stepan Horokh","DD000111"));
        park.replaceTaxi(t2.getTaxiNumberID(), replacement);

        park.removeTaxi(t1.getTaxiNumberID());   // видалили Prius
        System.out.println("\nAfter replace/remove, fleet: " + park);


        System.out.println("\n--- instanceof checks:");
        for (Auto a : park.getTaxis()) {  
            if (a instanceof PremiumTaxi) {
                PremiumTaxi prem = (PremiumTaxi) a;
                System.out.printf("Taxi #%d is Premium (WiFi=%b)%n",
                                  prem.getTaxiNumberID(), prem.hasWiFi());
            } else if (a instanceof Taxi) {
                Taxi tx = (Taxi) a;
                System.out.printf("Taxi #%d is Standard with driver %s%n",
                                  tx.getTaxiNumberID(), tx.getDriver().getName());
            }
        }
    }
}
