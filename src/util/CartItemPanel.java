package util;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class CartItemPanel extends JPanel {
    
    public CartItemPanel(String productName, String sizeName, double price,
                         Runnable onDelete, Runnable onDecrease, Runnable onIncrease, int quantity) {

        setLayout(new BorderLayout());
        setBackground(Color.WHITE);
        setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200)),
            BorderFactory.createEmptyBorder(8, 10, 8, 10)
        ));

        // Left panel: product info
        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        infoPanel.setBackground(Color.WHITE);

        JLabel nameLabel = new JLabel(productName);
        nameLabel.setFont(new Font("Arial", Font.BOLD, 14));

        JLabel sizePriceLabel = new JLabel(sizeName + " • ₱" + String.format("%.2f", price));
        sizePriceLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        sizePriceLabel.setForeground(Color.GRAY);

        infoPanel.add(nameLabel);
        infoPanel.add(sizePriceLabel);

        // Right panel: quantity buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 5, 0));
        buttonPanel.setBackground(Color.WHITE);

        JButton minusBtn = new JButton("−");
        JButton qtyLabel = new JButton(String.valueOf(quantity));
        qtyLabel.setEnabled(false);
        JButton plusBtn = new JButton("+");
        JButton deleteBtn = new JButton("🗑");

        minusBtn.addActionListener(e -> onDecrease.run());
        plusBtn.addActionListener(e -> onIncrease.run());
        deleteBtn.addActionListener(e -> onDelete.run());

        buttonPanel.add(minusBtn);
        buttonPanel.add(qtyLabel);
        buttonPanel.add(plusBtn);
        buttonPanel.add(deleteBtn);

        add(infoPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.EAST);
    }
}