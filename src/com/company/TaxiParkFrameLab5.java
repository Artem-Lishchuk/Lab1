package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

/**
 * TaxiParkFrame.java
 *
 * Головне вікно з усіма кнопками:
 * - Add Taxi
 * - Remove Taxi
 * - Drive Taxi (10 км)
 * - Refuel Taxi (5 л)
 * - Show Most Profitable Taxi
 * - Show Least Profitable Taxi
 * - Start Animation
 * - Pause Animation
 * - Stop Animation
 * - Exit
 *
 * Всі повідомлення (успіх, помилка) виводяться через JOptionPane.
 */
public class TaxiParkFrameLab5 extends JFrame {
    private final Taksopark park;
    private final TaxiParkPanel parkPanel;
    private final JPanel buttonPanel;
    private final Timer animationTimer;
    private boolean isAnimating = false;

    public TaxiParkFrameLab5(Taksopark park) {
        super("Taxi Park Simulator");
        this.park = park;

        // 1) Основні налаштування вікна
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(700, 600);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // 2) Панель, яка показуватиме анімацію
        parkPanel = new TaxiParkPanel(park);
        add(parkPanel, BorderLayout.CENTER);

        // 3) Кнопки зверху
        buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 8, 8));

        var addTaxiBtn    = new JButton("Add Taxi");
        var removeTaxiBtn = new JButton("Remove Taxi");
        var driveBtn      = new JButton("Drive (10km)");
        var refuelBtn     = new JButton("Refuel (+5 L)");
        var mostBtn       = new JButton("Most Profitable");
        var leastBtn      = new JButton("Least Profitable");
        var startAnimBtn  = new JButton("▶ Start Anim");
        var pauseAnimBtn  = new JButton("❚❚ Pause Anim");
        var stopAnimBtn   = new JButton("■ Stop Anim");
        var exitBtn       = new JButton("Exit");

        buttonPanel.add(addTaxiBtn);
        buttonPanel.add(removeTaxiBtn);
        buttonPanel.add(driveBtn);
        buttonPanel.add(refuelBtn);
        buttonPanel.add(mostBtn);
        buttonPanel.add(leastBtn);
        buttonPanel.add(startAnimBtn);
        buttonPanel.add(pauseAnimBtn);
        buttonPanel.add(stopAnimBtn);
        buttonPanel.add(exitBtn);

        add(buttonPanel, BorderLayout.NORTH);

        // 4) Налаштовуємо Timer для анімації (500 мс)
        animationTimer = new Timer(500, (ActionEvent e) -> parkPanel.moveAllTaxis());

        // 5) Додаємо обробники подій для кожної кнопки

        // --- Add Taxi ---
        addTaxiBtn.addActionListener(e -> {
            try {
                String[] types = { "Standard Taxi", "Premium Taxi" };
                int choice = JOptionPane.showOptionDialog(
                        this,
                        "Choose taxi type:",
                        "Add Taxi",
                        JOptionPane.DEFAULT_OPTION,
                        JOptionPane.QUESTION_MESSAGE,
                        null,
                        types,
                        types[0]
                );
                if (choice == -1) return; // користувач закрив діалог

                // Загальні поля
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
                Taxi taxiToAdd;

                if (choice == 0) {
                    // Стандартне таксі
                    taxiToAdd = new Taxi(make.trim(), model.trim(), driver);
                } else {
                    // PremiumTaxi
                    int wifiOpt = JOptionPane.showConfirmDialog(this, "Include WiFi?", "WiFi", JOptionPane.YES_NO_OPTION);
                    boolean hasWiFi = (wifiOpt == JOptionPane.YES_OPTION);

                    int minibarOpt = JOptionPane.showConfirmDialog(this, "Include MiniBar?", "MiniBar", JOptionPane.YES_NO_OPTION);
                    boolean hasMiniBar = (minibarOpt == JOptionPane.YES_OPTION);

                    String multiplierStr = JOptionPane.showInputDialog(this,
                            "Enter rate-per-km multiplier (e.g. 1.5):", "Rate Multiplier", JOptionPane.PLAIN_MESSAGE);
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

                    taxiToAdd = new PremiumTaxi(make.trim(), model.trim(), hasWiFi, hasMiniBar, multiplier, driver);
                }

                park.addTaxi(taxiToAdd);
                parkPanel.registerTaxi(taxiToAdd);

                JOptionPane.showMessageDialog(this,
                        "Added: " + taxiToAdd,
                        "Success", JOptionPane.INFORMATION_MESSAGE);

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this,
                        "Error adding taxi:\n" + ex.getMessage(),
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        // --- Remove Taxi ---
        removeTaxiBtn.addActionListener(e -> {
            try {
                String idStr = JOptionPane.showInputDialog(this, "Enter taxi ID to remove:", "Remove Taxi", JOptionPane.PLAIN_MESSAGE);
                if (idStr == null || idStr.trim().isEmpty()) return;
                int id = Integer.parseInt(idStr.trim());

                park.removeTaxi(id);
                parkPanel.unregisterTaxi(id);

                JOptionPane.showMessageDialog(this,
                        "Attempted to remove taxi with ID " + id,
                        "Remove Taxi", JOptionPane.INFORMATION_MESSAGE);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this,
                        "Invalid ID. Must be an integer.",
                        "Input Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        // --- Drive Taxi (10 км) ---
        driveBtn.addActionListener(e -> {
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
                boolean ok = taxi.drive(10);
                taxi.stop();

                if (ok) {
                    JOptionPane.showMessageDialog(this,
                            "Taxi #" + id + " drove 10 km.\nCurrent profit: $" + taxi.getProfit(),
                            "Drive Successful", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(this,
                            "Taxi #" + id + " could not drive (insufficient fuel or not started).",
                            "Drive Failed", JOptionPane.WARNING_MESSAGE);
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this,
                        "Invalid ID. Must be an integer.",
                        "Input Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        // --- Refuel Taxi (+5 л) ---
        refuelBtn.addActionListener(e -> {
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

        // --- Show Most Profitable Taxi ---
        mostBtn.addActionListener(e -> {
            var list = park.getTaxis();
            if (list.isEmpty()) {
                JOptionPane.showMessageDialog(this,
                        "No taxis in the fleet.",
                        "Information", JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            Taxi best = park.getMostProfitableTaxi();
            JOptionPane.showMessageDialog(this,
                    "Most profitable taxi:\n" + best,
                    "Most Profitable", JOptionPane.INFORMATION_MESSAGE);
        });

        // --- Show Least Profitable Taxi ---
        leastBtn.addActionListener(e -> {
            var list = park.getTaxis();
            if (list.isEmpty()) {
                JOptionPane.showMessageDialog(this,
                        "No taxis in the fleet.",
                        "Information", JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            Taxi worst = park.getLeastProfitableTaxi();
            JOptionPane.showMessageDialog(this,
                    "Least profitable taxi:\n" + worst,
                    "Least Profitable", JOptionPane.INFORMATION_MESSAGE);
        });

        // --- Start Animation ---
        startAnimBtn.addActionListener(e -> {
            if (!isAnimating) {
                animationTimer.start();
                isAnimating = true;
            }
        });

        // --- Pause Animation ---
        pauseAnimBtn.addActionListener(e -> {
            if (isAnimating) {
                animationTimer.stop();
                isAnimating = false;
            }
        });

        // --- Stop Animation (зупиняє, і повертає таксі на початок) ---
        stopAnimBtn.addActionListener(e -> {
            animationTimer.stop();
            isAnimating = false;
            // Повертаємо позиції всіх таксі у випадкові початкові.
            for (Taxi t : park.getTaxis()) {
                parkPanel.unregisterTaxi(t.getTaxiNumberID());
                parkPanel.registerTaxi(t);
            }
        });

        // --- Exit ---
        exitBtn.addActionListener(e -> System.exit(0));
    }


}
