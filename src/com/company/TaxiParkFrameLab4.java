package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Comparator;

/**
 * TaxiParkFrame.java
 *
 * Головне вікно (GUI) для керування таксопарком.
 * Використовує JOptionPane для виведення всіх повідомлень (без текстового поля).
 *
 * Не змінює існуючі класи Auto, Taxi, PremiumTaxi, Driver, Taksopark.
 */
public class TaxiParkFrameLab4 extends JFrame {
    private final Taksopark park;

    public TaxiParkFrameLab4(Taksopark park) {
        super("Taxi Park Manager");
        this.park = park;

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(400, 300);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(7, 1, 5, 5));

        // Кнопки для функціоналу
        JButton addTaxiBtn     = new JButton("Add Taxi");
        JButton removeTaxiBtn  = new JButton("Remove Taxi");
        JButton driveTaxiBtn   = new JButton("Drive Taxi");
        JButton refuelTaxiBtn  = new JButton("Refuel Taxi");
        JButton showMostBtn    = new JButton("Show Most Profitable Taxi");
        JButton showLeastBtn   = new JButton("Show Least Profitable Taxi");
        JButton exitBtn        = new JButton("Exit");

        add(addTaxiBtn);
        add(removeTaxiBtn);
        add(driveTaxiBtn);
        add(refuelTaxiBtn);
        add(showMostBtn);
        add(showLeastBtn);
        add(exitBtn);

        // --- ДІЯЛЬНІСТЬ КНОПОК ---

        // 1) ДОДАТИ ТАКСІ
        addTaxiBtn.addActionListener((ActionEvent e) -> {
            try {
                String[] options = { "Standard Taxi", "Premium Taxi" };
                int choice = JOptionPane.showOptionDialog(
                    this,
                    "Select taxi type to add:",
                    "Add Taxi",
                    JOptionPane.DEFAULT_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    options,
                    options[0]
                );
                if (choice == -1) return; // користувач закрив діалог

                // Загальні дані
                String make = JOptionPane.showInputDialog(this, "Enter make (e.g. Toyota):", "Make", JOptionPane.PLAIN_MESSAGE);
                if (make == null || make.trim().isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Make is required.", "Input Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                String model = JOptionPane.showInputDialog(this, "Enter model (e.g. Corolla):", "Model", JOptionPane.PLAIN_MESSAGE);
                if (model == null || model.trim().isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Model is required.", "Input Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                String driverName = JOptionPane.showInputDialog(this, "Enter driver name:", "Driver Name", JOptionPane.PLAIN_MESSAGE);
                if (driverName == null || driverName.trim().isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Driver name is required.", "Input Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                String license = JOptionPane.showInputDialog(this, "Enter driver license number:", "License Number", JOptionPane.PLAIN_MESSAGE);
                if (license == null || license.trim().isEmpty()) {
                    JOptionPane.showMessageDialog(this, "License number is required.", "Input Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                Driver driver = new Driver(driverName.trim(), license.trim());
                Taxi newTaxi;

                if (choice == 0) {
                    // Standard Taxi
                    newTaxi = new Taxi(make.trim(), model.trim(), driver);
                } else {
                    // Premium Taxi
                    int wifiOption = JOptionPane.showConfirmDialog(this, "Include WiFi?", "WiFi", JOptionPane.YES_NO_OPTION);
                    boolean hasWiFi = (wifiOption == JOptionPane.YES_OPTION);

                    int minibarOption = JOptionPane.showConfirmDialog(this, "Include MiniBar?", "MiniBar", JOptionPane.YES_NO_OPTION);
                    boolean hasMiniBar = (minibarOption == JOptionPane.YES_OPTION);

                    String multiplierStr = JOptionPane.showInputDialog(this,
                        "Enter rate-per-km multiplier (e.g., 1.5):", "Rate Multiplier", JOptionPane.PLAIN_MESSAGE);
                    if (multiplierStr == null || multiplierStr.trim().isEmpty()) {
                        JOptionPane.showMessageDialog(this, "Multiplier is required.", "Input Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    double multiplier;
                    try {
                        multiplier = Double.parseDouble(multiplierStr.trim());
                        if (multiplier <= 0) throw new NumberFormatException();
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(this,
                            "Invalid multiplier. Must be a positive number.",
                            "Input Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    newTaxi = new PremiumTaxi(make.trim(), model.trim(), hasWiFi, hasMiniBar, multiplier, driver);
                }

                park.addTaxi(newTaxi);
                JOptionPane.showMessageDialog(this,
                    "Added: " + newTaxi,
                    "Success", JOptionPane.INFORMATION_MESSAGE);

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this,
                    "Error adding taxi:\n" + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        // 2) ВИДАЛИТИ ТАКСІ
        removeTaxiBtn.addActionListener(e -> {
            try {
                String idStr = JOptionPane.showInputDialog(this, "Enter taxi ID to remove:", "Remove Taxi", JOptionPane.PLAIN_MESSAGE);
                if (idStr == null || idStr.trim().isEmpty()) return;

                int id = Integer.parseInt(idStr.trim());
                park.removeTaxi(id);
                JOptionPane.showMessageDialog(this,
                    "Attempted to remove taxi with ID: " + id,
                    "Remove Taxi", JOptionPane.INFORMATION_MESSAGE);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this,
                    "Invalid ID. Must be an integer.",
                    "Input Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        // 3) ПРОЇХАТИ (10 км)
        driveTaxiBtn.addActionListener(e -> {
            try {
                String idStr = JOptionPane.showInputDialog(this, "Enter taxi ID to drive 10 km:", "Drive Taxi", JOptionPane.PLAIN_MESSAGE);
                if (idStr == null || idStr.trim().isEmpty()) return;

                int id = Integer.parseInt(idStr.trim());
                Taxi taxi = park.getTaxi(id);
                if (taxi == null) {
                    JOptionPane.showMessageDialog(this,
                        "Taxi with ID " + id + " not found.",
                        "Not Found", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                taxi.start();
                boolean success = taxi.drive(10);
                taxi.stop();
                if (success) {
                    JOptionPane.showMessageDialog(this,
                        "Taxi #" + id + " drove 10 km.\nCurrent profit: $" + taxi.getProfit(),
                        "Drive Successful", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(this,
                        "Taxi #" + id + " could not drive.\nEither no fuel or not started.",
                        "Drive Failed", JOptionPane.WARNING_MESSAGE);
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this,
                    "Invalid ID. Must be an integer.",
                    "Input Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        // 4) ЗАПРАВИТИ ТАКСІ
        refuelTaxiBtn.addActionListener(e -> {
            try {
                String idStr = JOptionPane.showInputDialog(this, "Enter taxi ID to refuel (adds 5 L):", "Refuel Taxi", JOptionPane.PLAIN_MESSAGE);
                if (idStr == null || idStr.trim().isEmpty()) return;

                int id = Integer.parseInt(idStr.trim());
                Taxi taxi = park.getTaxi(id);
                if (taxi == null) {
                    JOptionPane.showMessageDialog(this,
                        "Taxi with ID " + id + " not found.",
                        "Not Found", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                taxi.refuel();
                JOptionPane.showMessageDialog(this,
                    "Taxi #" + id + " refueled.\nCurrent fuel level: " + taxi.getFuelLevel() + " L",
                    "Refuel Successful", JOptionPane.INFORMATION_MESSAGE);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this,
                    "Invalid ID. Must be an integer.",
                    "Input Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        // 5) ПОКАЗАТИ НАЙПРИБУТКОВІШЕ ТАКСІ
        showMostBtn.addActionListener(e -> {
            ArrayList<Taxi> list = park.getTaxis();
            if (list.isEmpty()) {
                JOptionPane.showMessageDialog(this,
                    "No taxis in the fleet.",
                    "Information", JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            Taxi most = list.stream()
                .max(Comparator.comparingDouble(Taxi::getProfit))
                .orElse(null);
            JOptionPane.showMessageDialog(this,
                "Most profitable taxi:\n" + most,
                "Most Profitable", JOptionPane.INFORMATION_MESSAGE);
        });

        // 6) ПОКАЗАТИ НАЙМЕНШ ПРИБУТКОВЕ ТАКСІ
        showLeastBtn.addActionListener(e -> {
            ArrayList<Taxi> list = park.getTaxis();
            if (list.isEmpty()) {
                JOptionPane.showMessageDialog(this,
                    "No taxis in the fleet.",
                    "Information", JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            Taxi least = list.stream()
                .min(Comparator.comparingDouble(Taxi::getProfit))
                .orElse(null);
            JOptionPane.showMessageDialog(this,
                "Least profitable taxi:\n" + least,
                "Least Profitable", JOptionPane.INFORMATION_MESSAGE);
        });

        // 7) ВИХІД
        exitBtn.addActionListener(e -> System.exit(0));
    }

}
