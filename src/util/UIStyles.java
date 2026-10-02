/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package util;


import java.awt.*;
import javax.swing.*;
import javax.swing.plaf.basic.BasicButtonUI;

public class UIStyles {

    // Apply rounded style to any JButton
    public static void applyRoundedButton(JButton button) {

        button.setUI(new BasicButtonUI());
        button.setOpaque(false);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setContentAreaFilled(false);

        button.setBackground(new Color(0,102,204));
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Segoe UI", Font.BOLD, 14));

        // Hover effect
        button.addMouseListener(new java.awt.event.MouseAdapter() {

            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(255,204,0));
                button.setForeground(Color.BLACK);
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(0,102,204));
                button.setForeground(Color.WHITE);
            }
        });

        // Custom paint
        button.setUI(new RoundedPainter());
    }

    // Painter class
    static class RoundedPainter extends BasicButtonUI {

        int radius = 25;

        @Override
        public void paint(Graphics g, JComponent c) {

            JButton button = (JButton) c;

            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                                RenderingHints.VALUE_ANTIALIAS_ON);

            g2.setColor(button.getBackground());
            g2.fillRoundRect(0, 0, button.getWidth(), button.getHeight(), radius, radius);

            super.paint(g, c);
            g2.dispose();
        }
    }
}
