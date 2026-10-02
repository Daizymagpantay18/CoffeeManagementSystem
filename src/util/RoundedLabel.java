/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package util;
import javax.swing.*;
import java.awt.*;

public class RoundedLabel extends JLabel {

    private int cornerRadius = 20;

    public RoundedLabel(String text) {
        super(text);
        setOpaque(false);
        setForeground(Color.BLACK);
        setHorizontalAlignment(SwingConstants.CENTER);
        setBorder(new javax.swing.border.EmptyBorder(2,5,2,5));
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2.setColor(new Color(0xF5EADB));
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), cornerRadius, cornerRadius);

        g2.dispose();
        super.paintComponent(g);
    }
}
