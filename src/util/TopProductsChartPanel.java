package util;

import javax.swing.*;
import java.awt.*;

public class TopProductsChartPanel extends JPanel {

    private String[] products;
    private double[] values;

    private Color[] colors = {
            new Color(92,55,49),
            new Color(121,72,65),
            new Color(212,175,129),
            new Color(88,140,135),
            new Color(220,213,204)
    };

    private Color textColor = new Color(146,109,79);

    public TopProductsChartPanel(String[] products, double[] values) {

        this.products = products;
        this.values = values;

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

        // Card background
        g2.setColor(Color.decode("#FEFCFB"));
        g2.fillRoundRect(0,0,width,height,20,20);

        // Title
        g2.setColor(textColor);
        g2.setFont(new Font("Segoe UI",Font.BOLD,14));
        g2.drawString("Top Products",110,30);

        if(values == null || values.length == 0) return;

        // total
        double total = 0;
        for(double v : values){
            total += v;
        }

        int x = 25;
        int y = 50;
        int size = 110;

        int startAngle = 0;

        // donut slices
        for(int i=0;i<values.length;i++){

            int angle = (int)Math.round((values[i] / total) * 360);

            g2.setColor(colors[i % colors.length]);
            g2.fillArc(x,y,size,size,startAngle,angle);

            startAngle += angle;
        }

        // donut hole
        g2.setColor(Color.decode("#FEFCFB"));
        g2.fillOval(x+30,y+30,50,50);

        // legend
        g2.setFont(new Font("Segoe UI",Font.PLAIN,11));

        int legendX = 160;
        int legendY = 60;

        for(int i=0;i<products.length;i++){

            int percent = (int)Math.round((values[i] / total) * 100);

            g2.setColor(colors[i % colors.length]);
            g2.fillOval(legendX, legendY + i*22,10,10);

            g2.setColor(textColor);
            g2.drawString(products[i], legendX + 18, legendY + 10 + i*22);
            g2.drawString(percent + "%", legendX + 105, legendY + 10 + i*22);
        }
    }
}