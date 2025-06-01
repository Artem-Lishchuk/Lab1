package com.company;

public class Main {
    public static void main(String[] args) {

        Auto auto1 = new Auto();  
        Auto auto2 = new Auto("Toyota", "Corolla");  
        Auto auto3 = new Auto("Honda", "Civic", 2018, 50000, 20);  


        System.out.println("Auto1: " + auto1);
        System.out.println("Auto2: " + auto2);
        System.out.println("Auto3: " + auto3);


        auto1.setMake("Mazda");
        auto1.setModell("CX-5");
        auto1.setYear(2020);
        auto1.setMilleage(15000);
        auto1.setFuelLevel(10);

        System.out.println("Updated Auto1: " + auto1);
        System.out.println("Make of Auto2: " + auto2.getMake());


        auto1.start();
        auto1.drive();             
        auto1.drive(30);           
        auto1.stop();

        auto1.refuel(5);
        auto1.refuel();            


        System.out.println("Total Autos created: " + Auto.getNumberOfAutos());


        for (int i = 0; i < 8; i++) {
            new Auto();
        }


        Auto autoOverflow = new Auto();


        System.out.println("MAX_AUTOS allowed: " + Auto.MAX_AUTOS);
        System.out.println("Final number of Autos: " + Auto.getNumberOfAutos());
    }
}
