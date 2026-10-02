package util;

import javax.swing.*;
import java.awt.*;

public class SalesChartPanel extends JPanel {

    private double[] sales;
    private String[] days = {"Mon","Tue","Wed","Thu","Fri","Sat","Sun"};

    private Color barColor = new Color(146,109,79);
    private Color textColor = new Color(146,109,79);

    public SalesChartPanel(double[] sales) {
        this.sales = sales;
        setPreferredSize(new Dimension(328,214));
        setOpaque(false);
    }

    @Override
    protected void paintComponent(Graphics g) {

        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        int width = getWidth();
        int height = getHeight();

        int topPadding = 35;
        int bottomPadding = 35;
        int leftPadding = 40;
        int rightPadding = 15;

        int chartHeight = height - topPadding - bottomPadding;
        int chartWidth = width - leftPadding - rightPadding;

        // Card background
        g2.setColor(Color.decode("#FEFCFB"));
        g2.fillRoundRect(0,0,width,height,20,20);

        // Title
        g2.setColor(textColor);
        g2.setFont(new Font("Segoe UI",Font.BOLD,14));
        g2.drawString("Sales This Week",115,30);

        // Find max value
        double max = 1;
        for(double v : sales){
            if(v > max){
                max = v;
            }
        }

        // Y axis numbers
        g2.setFont(new Font("Segoe UI",Font.PLAIN,10));
        g2.setColor(textColor);

        int steps = 4;

        for(int i = 0; i <= steps; i++){

            int value = (int)(max / steps * i);

            int y = height - bottomPadding - (chartHeight * i / steps);

            g2.drawString(String.valueOf(value),8,y);
        }

        // Bars
        int barWidth = chartWidth / sales.length;

        for(int i = 0; i < sales.length; i++){

            int x = leftPadding + i * barWidth;

            int barHeight = (int)((sales[i] / max) * chartHeight);

            int y = height - bottomPadding - barHeight;

            // Bar
            g2.setColor(barColor);
            g2.fillRoundRect(x,y,barWidth-10,barHeight,8,8);

            // Day label
            g2.setColor(textColor);
            g2.setFont(new Font("Segoe UI",Font.PLAIN,10));

            g2.drawString(days[i],x + 6,height - 12);
        }
    }
}