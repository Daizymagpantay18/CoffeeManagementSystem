/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package util;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JButton;

/**
 *
 * @author Daizy Magpantay
 */
public class ui {
    // Generic notification button setup
    public static void setupNotificationButton(JButton button, String iconPath, Class<?> clazz, Runnable onClick) {
        ImageIcon originalIcon = new ImageIcon(clazz.getResource(iconPath));
        Image img = originalIcon.getImage().getScaledInstance(24, 24, Image.SCALE_SMOOTH);
        button.setIcon(new ImageIcon(makeColored(img)));

        button.setContentAreaFilled(false);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setOpaque(false);
        button.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        button.addActionListener(e -> {
            if(onClick != null) onClick.run();
        });
    }

    // Optional: single-color icon
    private static Image makeColored(Image img) {
        int size = 24;
        java.awt.image.BufferedImage buffered = new java.awt.image.BufferedImage(size, size, java.awt.image.BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = buffered.createGraphics();
        g2.drawImage(img, 0, 0, null);
        g2.setComposite(AlphaComposite.SrcAtop);
        g2.setColor(new Color(244, 236, 223)); // #F4ECDF
        g2.fillRect(0, 0, size, size);
        g2.dispose();
        return buffered;
    }

    // Update badge count
    public static void updateNotificationBadge(javax.swing.JButton button, int count) {

        if (count <= 0) {
            button.setText("");
            return;
        }

        String badge = "<html><div style='position:relative;'>"
                + "<span style='position:absolute; top:-8px; right:-8px; "
                + "background:red; color:white; border-radius:10px; "
                + "padding:2px 6px; font-size:10px;'>"
                + count
                + "</span></div></html>";

        button.setText(badge);
    }

}
