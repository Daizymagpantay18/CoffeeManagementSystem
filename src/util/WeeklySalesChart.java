package util;

import controllers.OrderController;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;

public class WeeklySalesChart extends JPanel {

    private final double[] sales;
    private final Color line = new Color(146, 109, 79);
    private final Color textColor = new Color(146, 109, 79);
    private final String[] days = {"Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun"};

    public WeeklySalesChart() {
        setPreferredSize(new Dimension(679, 256));
        setOpaque(false);
        sales = new OrderController().getWeeklySales();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int w = getWidth();
        int h = getHeight();
        int padding = 50;
        int graphWidth = w - 2 * padding;
        int graphHeight = h - 2 * padding;

        // Background
        g2.setColor(Color.decode("#FEFCFB"));
        g2.fillRoundRect(0, 0, w, h, 20, 20);

        // Title
        g2.setColor(textColor);
        g2.setFont(new Font("Segoe UI", Font.BOLD, 16));
        g2.drawString("Weekly Sales Trend", 20, 25);

        double max = Arrays.stream(sales).max().orElse(100);

        // Draw Y-axis labels and optional grid lines
        g2.setColor(textColor);
        g2.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        int steps = 5; // number of labels
        for (int i = 0; i <= steps; i++) {
            int y = padding + graphHeight - (i * graphHeight / steps);
            double value = max * i / steps;
            String text = String.format("%.0f", value);
            g2.drawString(text, 5, y + 5); // Y-axis label

            // Optional: horizontal grid line
            g2.setColor(line.brighter().brighter());
            g2.drawLine(padding, y, w - padding, y);
            g2.setColor(textColor);
        }

        // Draw the sales line and points
        g2.setColor(line);
        g2.setStroke(new BasicStroke(3));
        int prevX = padding;
        int prevY = padding + graphHeight - (int) ((sales[0] / max) * graphHeight);

        for (int i = 0; i < sales.length; i++) {
            int x = padding + i * graphWidth / (sales.length - 1);
            int y = padding + graphHeight - (int) ((sales[i] / max) * graphHeight);

            // Draw point
            g2.fillOval(x - 5, y - 5, 10, 10);

            // Draw line connecting points
            if (i > 0) g2.drawLine(prevX, prevY, x, y);

            // Draw sales value above the point
            g2.setFont(new Font("Segoe UI", Font.PLAIN, 12));
            g2.setColor(textColor);
            g2.drawString(String.format("%.0f", sales[i]), x - 10, y - 10);

            // Draw day label
            g2.drawString(days[i], x - 10, h - 10);

            prevX = x;
            prevY = y;
            g2.setColor(line);
        }
    }
}