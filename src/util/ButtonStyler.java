/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package util;

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JButton;


public class ButtonStyler {

    public static void applyCoffeeStyle(JButton button) {

        Color normal = new Color(146, 109, 79);
        Color hover = new Color(125, 90, 65);
        Color pressed = new Color(105, 75, 55);

        button.setBackground(normal);
        button.setForeground(Color.WHITE);

        // IMPORTANT FOR ROUNDED BUTTON
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setOpaque(false);

        button.setFont(new java.awt.Font("Segoe UI Semibold", java.awt.Font.PLAIN, 13));
        button.setBorder(javax.swing.BorderFactory.createEmptyBorder(8, 20, 8, 20));

        button.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(hover);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(normal);
            }

            @Override
            public void mousePressed(MouseEvent e) {
                button.setBackground(pressed);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                button.setBackground(hover);
            }
        });
    }

    public static void applyCancelStyle(JButton button) {

        Color normal = new Color(232, 218, 199);
        Color hover = new Color(200, 80, 80);
        Color pressed = new Color(170, 50, 50);

        button.setBackground(normal);
        button.setForeground(new Color(92, 64, 51));

        // IMPORTANT FOR ROUNDED BUTTON
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setOpaque(false);

        button.setFont(new java.awt.Font("Segoe UI Semibold", java.awt.Font.PLAIN, 13));
        button.setBorder(javax.swing.BorderFactory.createEmptyBorder(8, 20, 8, 20));

        button.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(hover);
                button.setForeground(Color.WHITE);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(normal);
                button.setForeground(new Color(92, 64, 51));
            }

            @Override
            public void mousePressed(MouseEvent e) {
                button.setBackground(pressed);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                button.setBackground(hover);
            }
        });
    }
}
