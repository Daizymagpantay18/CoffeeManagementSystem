package util;

import controllers.IngredientController;
import models.Ingredient;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.List;

public class LowStockAlertPanel extends JPanel {

    private final Color textColor = new Color(46, 41, 33); // darker brown for text
    private final IngredientController ingredientController = new IngredientController();
    private final int maxVisibleRows = 4; // maximum rows before scrolling
    private final int rowHeight = 50; // approximate row height including spacing

    public LowStockAlertPanel() {
        setOpaque(false);
        setLayout(new BorderLayout());

        // --- Title ---
        JLabel title = new JLabel("Low Stock Alert");
        title.setFont(new Font("Segoe UI", Font.BOLD, 18));
        title.setForeground(textColor);
        title.setBorder(new EmptyBorder(10, 15, 10, 15));

        add(title, BorderLayout.NORTH);

        // --- Container for ingredient rows ---
        JPanel rowsPanel = new JPanel();
        rowsPanel.setOpaque(false);
        rowsPanel.setLayout(new BoxLayout(rowsPanel, BoxLayout.Y_AXIS));
        rowsPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        // Populate rows
        List<Ingredient> lowStockIngredients = ingredientController.getLowStockIngredients();
        for (Ingredient ing : lowStockIngredients) {
            rowsPanel.add(createIngredientRow(ing));
            rowsPanel.add(Box.createRigidArea(new Dimension(0, 10))); // spacing between rows
        }

        // --- Scroll pane for rows ---
        JScrollPane scrollPane = new JScrollPane(rowsPanel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());

        // Limit scroll pane height to maxVisibleRows
        int visibleHeight = rowHeight * maxVisibleRows;
        scrollPane.setPreferredSize(new Dimension(400, visibleHeight));

        add(scrollPane, BorderLayout.CENTER);
    }

    private JPanel createIngredientRow(Ingredient ing) {
        JPanel row = new RoundedPanel(15, new Color(242, 234, 221)); // row with rounded background
        row.setLayout(new BorderLayout(10, 0));
        row.setBorder(new EmptyBorder(8, 12, 8, 12));

        JLabel nameLabel = new JLabel(ing.getName());
        nameLabel.setForeground(textColor);
        nameLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        String qtyText = String.format("%.0f %s", ing.getStockQty(), ing.getUnit());
        JLabel qtyLabel = new JLabel(qtyText);
        qtyLabel.setForeground(textColor);
        qtyLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        boolean isCritical = ing.getStockQty() <= ing.getLowStockThreshold() / 2;
        String statusText = isCritical ? "CRITICAL" : "LOW";
        Color statusColor = isCritical ? new Color(220, 53, 69) : new Color(255, 193, 7);

        JLabel statusLabel = new JLabel(statusText);
        statusLabel.setOpaque(true);
        statusLabel.setBackground(statusColor);
        statusLabel.setForeground(Color.WHITE);
        statusLabel.setFont(new Font("Segoe UI", Font.BOLD, 12));
        statusLabel.setBorder(BorderFactory.createEmptyBorder(3, 10, 3, 10));
        statusLabel.setHorizontalAlignment(SwingConstants.CENTER);

        JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        rightPanel.setOpaque(false);
        rightPanel.add(qtyLabel);
        rightPanel.add(statusLabel);

        row.add(nameLabel, BorderLayout.WEST);
        row.add(rightPanel, BorderLayout.EAST);

        return row;
    }

    // --- Rounded main panel painting ---
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        int arc = 20;
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(new Color(254, 252, 251)); // panel background color
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), arc, arc);
        g2.dispose();
    }

    // --- RoundedPanel class for rows ---
    static class RoundedPanel extends JPanel {
        private final int cornerRadius;
        private final Color backgroundColor;

        public RoundedPanel(int radius, Color bgColor) {
            super();
            cornerRadius = radius;
            backgroundColor = bgColor;
            setOpaque(false);
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(backgroundColor);
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), cornerRadius, cornerRadius);
            g2.dispose();
        }
    }
}