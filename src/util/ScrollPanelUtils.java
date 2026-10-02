package util;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import javax.swing.JScrollPane;
import javax.swing.JViewport;
import javax.swing.BorderFactory;

public class ScrollPanelUtils {

    // Gawing rounded ang JScrollPane
    public static void makeRounded(JScrollPane scrollPane, int radius) {
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());

        // Custom viewport para sa rounded background
        JViewport viewport = new JViewport() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(getBackground());
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), radius, radius);
                g2.dispose();
            }
        };

        viewport.setView(scrollPane.getViewport().getView());
        scrollPane.setViewport(viewport);
    }
}