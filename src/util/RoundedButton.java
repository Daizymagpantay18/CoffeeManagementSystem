/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package util;

import java.awt.Color;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Insets;
import java.awt.RenderingHints;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.SwingConstants;


public class RoundedButton extends JButton{
    private int radius = 20;
    
    public RoundedButton(String text) {
        this(text, null);
    }
    public RoundedButton(String text, String iconPath) {
        super(text);

        setContentAreaFilled(false);
        setFocusPainted(false);
        setBorderPainted(false);
        setForeground(new Color(245, 234, 219));
        setBackground(new Color(146, 109, 79));
        setFont(new Font("Segoe UI", Font.PLAIN, 14));
        setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Resize and set icon properly
        if (iconPath != null) {
            ImageIcon originalIcon = new ImageIcon(getClass().getResource(iconPath));

            // Resize
            Image resized = originalIcon.getImage().getScaledInstance(15, 15, Image.SCALE_SMOOTH);

            // Gamitin ImageIcon para malaman ang tamang width/height
            ImageIcon resizedIcon = new ImageIcon(resized);
            int w = resizedIcon.getIconWidth();
            int h = resizedIcon.getIconHeight();

            // Create BufferedImage safely
            BufferedImage buffered = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2 = buffered.createGraphics();
            g2.drawImage(resized, 0, 0, null);
            g2.dispose();

            // Make icon white while preserving alpha
            for (int y = 0; y < h; y++) {
                for (int x = 0; x < w; x++) {
                    int alpha = (buffered.getRGB(x, y) >> 24) & 0xff;
                    if (alpha != 0) {
                        buffered.setRGB(x, y, (alpha << 24) | 0xFFFFFF);
                    }
                }
            }

            setIcon(new ImageIcon(buffered));
        }

        // Layout
        setHorizontalTextPosition(SwingConstants.RIGHT);
        setVerticalTextPosition(SwingConstants.CENTER);
        setIconTextGap(5);
        

        // Size
        setPreferredSize(new Dimension(135, 35));
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        g2.setColor(getBackground());
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), radius, radius);

        super.paintComponent(g);
        g2.dispose();
    }

    @Override
    protected void paintBorder(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        g2.setColor(getBackground().darker());
        g2.setStroke(new java.awt.BasicStroke(0.5f));
        g2.drawRoundRect(0, 0, getWidth()-1, getHeight()-1, radius, radius);
        g2.dispose();
    }

    public static RoundedButton makeRounded(JButton button, String text) {
        RoundedButton rb = new RoundedButton(text, null);

        // Copy action listeners
        for (ActionListener al : button.getActionListeners()) {
            rb.addActionListener(al);
        }

        // Replace in parent container
        Container parent = button.getParent();
        if (parent != null) {
            int index = -1;
            for (int i = 0; i < parent.getComponentCount(); i++) {
                if (parent.getComponent(i) == button) {
                    index = i;
                    break;
                }
            }
            if (index != -1) {
                parent.remove(index);
                parent.add(rb, index);
                parent.revalidate();
                parent.repaint();
            }
        }

        return rb;
    }
}
