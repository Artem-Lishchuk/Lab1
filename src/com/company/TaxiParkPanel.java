package com.company;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * Панель, яка малює всі таксі з Taksopark і анімує їх рух.
 */
public class TaxiParkPanel extends JPanel {
    private final Taksopark park;
    private final Map<Integer, Point> positions = new HashMap<>();
    private final Random rnd = new Random();

    public TaxiParkPanel(Taksopark park) {
        this.park = park;
        // Ми задаємо розмір, але layout у Frame може змінити його пізніше
        setPreferredSize(new Dimension(600, 360));
        setBackground(Color.WHITE);
    }

    /**
     * Викликається щоразу, коли додається нове таксі — припускаємо, що його ID унікальний.
     * Ініціалізуємо випадкову позицію в межах панелі.
     */
    public void registerTaxi(Taxi t) {
        int pw = getPreferredSize().width;
        int ph = getPreferredSize().height;
        int x = rnd.nextInt(Math.max(1, pw - 60));
        int y = rnd.nextInt(Math.max(1, ph - 40));
        positions.put(t.getTaxiNumberID(), new Point(x, y));
        repaint();
    }

    /** Викликається при видаленні таксі */
    public void unregisterTaxi(int taxiId) {
        positions.remove(taxiId);
        repaint();
    }

    /** Пересуває всі таксі праворуч на фіксовану відстань, з wrap-around */
    public void moveAllTaxis() {
        int w = getWidth();
        int h = getHeight();

        for (Taxi t : getAllTaxis()) {
            Point p = positions.get(t.getTaxiNumberID());
            if (p == null) {
                // Якщо з якоїсь причини його ще немає в мапі, розміщуємо ліворуч
                int y = rnd.nextInt(Math.max(1, h - 40));
                p = new Point( -60, y );
                positions.put(t.getTaxiNumberID(), p);
            }
            p.x += 15;
            if (p.x > w) {
                // як тільки вийшло за правий край — зіскакуємо ліворуч
                p.x = -60;
                p.y = rnd.nextInt(Math.max(1, h - 40));
            }
        }
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Намалювати кожне таксі
        for (Taxi t : getAllTaxis()) {
            Point p = positions.get(t.getTaxiNumberID());
            if (p == null) continue;

            if (t instanceof PremiumTaxi) {
                g.setColor(Color.ORANGE);
            } else {
                g.setColor(Color.CYAN);
            }
            // Малюємо прямокутник 60×30
            g.fillRect(p.x, p.y, 60, 30);

            // Намалювати ID та прибуток
            g.setColor(Color.BLACK);
            g.drawString("ID:" + t.getTaxiNumberID(), p.x + 2, p.y + 12);
            g.drawString("$" + t.getProfit(), p.x + 2, p.y + 26);

            // Якщо це PremiumTaxi із WiFi — позначаємо синім
            if (t instanceof PremiumTaxi) {
                PremiumTaxi pt = (PremiumTaxi) t;
                if (pt.hasWiFi()) {
                    g.setColor(Color.BLUE);
                    g.drawString("WiFi", p.x + 40, p.y + 12);
                }
            }
        }
    }

    /** Використовуємо reflection, щоб отримати private ArrayList<Taxi> taxis з Taksopark */
    @SuppressWarnings("unchecked")
    private java.util.List<Taxi> getAllTaxis() {
        try {
            var field = Taksopark.class.getDeclaredField("taxis");
            field.setAccessible(true);
            return (java.util.List<Taxi>) field.get(park);
        } catch (Exception ex) {
            return java.util.Collections.emptyList();
        }
    }
}
