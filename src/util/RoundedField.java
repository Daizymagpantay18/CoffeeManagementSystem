/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package util;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.RenderingHints;
import javax.swing.JPasswordField;
import javax.swing.border.Border;

public class RoundedField extends JPasswordField { 

    private int radius;
    private boolean isPassword;

    public RoundedField(int radius) {
        this(radius, false);
        setBackground(new Color(0xF5EADB));
    }

    public RoundedField(int radius, boolean isPassword) {
        this.radius = radius;
        this.isPassword = isPassword;
        setOpaque(false);
        setBackground(new Color(0xF5EADB));
        setForeground(new Color(0x553523));
        setBorder(new RoundedBorder(radius));

        if (isPassword) {
            setEchoChar('•'); 
        } else {
            setEchoChar((char)0); 
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(getBackground());
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), radius, radius);
        g2.dispose();
        super.paintComponent(g);
    }

    private static class RoundedBorder implements Border {
        private int radius;
        public RoundedBorder(int radius) { this.radius = radius; }

        @Override
        public Insets getBorderInsets(Component c) {
            return new Insets(radius/2, radius/2, radius/2, radius/2);
        }

        @Override
        public boolean isBorderOpaque() { return false; }

        @Override
        public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(new Color(0x553523));
            g2.drawRoundRect(x, y, width-1, height-1, radius, radius);
            g2.dispose();
        }
    }
}
