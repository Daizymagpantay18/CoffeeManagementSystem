/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package util;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.SwingConstants;
import javax.swing.border.Border;

/**
 *
 * @author Daizy Magpantay
 */
public class SidebarUtils {
    private static JButton selectedButton = null;

    // ================= ICON RESIZER =================
    public static void setIcon(JButton btn, String path, Class<?> clazz, Color color) {

        ImageIcon originalIcon =
                new ImageIcon(clazz.getResource(path));

        Image img = originalIcon.getImage()
                .getScaledInstance(24, 24, Image.SCALE_SMOOTH);

        ImageIcon defaultIcon = new ImageIcon(img);
        ImageIcon coloredIcon = new ImageIcon(makeColored(img,color));

        btn.setIcon(coloredIcon);

        btn.putClientProperty("defaultIcon", coloredIcon);
        btn.putClientProperty("activeIcon", coloredIcon);

        btn.setHorizontalAlignment(SwingConstants.LEFT);
        btn.setHorizontalTextPosition(SwingConstants.RIGHT);
        btn.setVerticalTextPosition(SwingConstants.CENTER);
        
        btn.setIconTextGap(15);
        btn.setBorder(BorderFactory.createEmptyBorder(5, 13, 5, 10));
    }

    // ================= MAKE ICON SINGLE COLOR =================
    private static Image makeColored(Image img, Color color) {

        BufferedImage buffered =
                new BufferedImage(24, 24, BufferedImage.TYPE_INT_ARGB);

        Graphics2D g2 = buffered.createGraphics();
        g2.drawImage(img, 0, 0, null);

        g2.setComposite(AlphaComposite.SrcAtop);
        g2.setColor(color);
        g2.fillRect(0, 0, 24, 24);

        g2.dispose();

        return buffered;
    }

    // ================= SIDEBAR BUTTON STYLE =================
    public static void setupSidebarButton(JButton btn) {

        btn.setContentAreaFilled(false);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setOpaque(false);

        Color defaultBg = new Color(0, 0, 0, 0);
        Color activeBg = new Color(0x795e49);
        Color lightActiveBg = new Color(0xA18671);

        btn.setBackground(defaultBg);

        // ================= CUSTOM BUTTON PAINT =================
        btn.setUI(new javax.swing.plaf.basic.BasicButtonUI() {
            @Override
            public void paint(Graphics g, JComponent c) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                        RenderingHints.VALUE_ANTIALIAS_ON);

                g2.setColor(btn.getBackground());
                g2.fillRoundRect(0, 0, btn.getWidth(), btn.getHeight(), 15, 15);

                super.paint(g2, c);
                g2.dispose();
            }
        });

        // ================= HOVER EFFECT =================
        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                if (selectedButton != btn) {
                    btn.setBackground(lightActiveBg);
                }
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                if (selectedButton != btn) {
                    btn.setBackground(defaultBg);
                }
            }
        });

        // ================= CLICK/SELECT =================
        btn.addActionListener(e -> {
            if (selectedButton != null) {
                selectedButton.setBackground(defaultBg);
                selectedButton.setForeground(Color.decode("#F4ECDF"));
                selectedButton.setIcon(
                        (ImageIcon) selectedButton.getClientProperty("defaultIcon")
                );
            }

            btn.setBackground(activeBg);
            btn.setForeground(Color.WHITE);
            btn.setIcon(
                    (ImageIcon) btn.getClientProperty("activeIcon")
            );

            selectedButton = btn;
        });

        // ================= CENTER ICON =================
        btn.setHorizontalAlignment(SwingConstants.CENTER);
        btn.setVerticalAlignment(SwingConstants.CENTER);
        btn.setHorizontalTextPosition(SwingConstants.CENTER);
        btn.setVerticalTextPosition(SwingConstants.CENTER);
    }

}
