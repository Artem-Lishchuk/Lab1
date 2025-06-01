package com.company;

public class Main {

    public static void main(String[] args) {
    Auto mazda = new Auto("Mazda", "CX-5", 2020, 15000, 50);
    System.out.println(mazda);
    mazda.refuel();
    System.out.println(mazda);
    }
}
