package util;

import javax.swing.*;
import java.awt.*;

public class RoundedComboBox<E> extends JComboBox<E> {

    private int radius = 20;

    public RoundedComboBox() {
        setOpaque(false);
        setBackground(Color.WHITE);
        setForeground(Color.BLACK);
        setBorder(BorderFactory.createEmptyBorder(5,10,5,10));
    }

    @Override
    protected void paintComponent(Graphics g) {

        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                            RenderingHints.VALUE_ANTIALIAS_ON);

        g2.setColor(getBackground());
        g2.fillRoundRect(0,0,getWidth(),getHeight(),radius,radius);

        super.paintComponent(g);
    }

    @Override
    protected void paintBorder(Graphics g) {

        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                            RenderingHints.VALUE_ANTIALIAS_ON);

        g2.setColor(new Color(146,109,79));
        g2.drawRoundRect(0,0,getWidth()-1,getHeight()-1,radius,radius);
    }
}