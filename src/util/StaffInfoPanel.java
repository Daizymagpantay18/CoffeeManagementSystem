/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package util;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.RenderingHints;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

public class StaffInfoPanel extends JPanel {

    private JLabel lblFullNameValue;
    private JLabel lblUsernameValue;
    private JLabel lblRoleValue;
    private JLabel lblStatusValue;

    public StaffInfoPanel() {

        setOpaque(false);
        setLayout(new BorderLayout(0, 15));
        setBorder(new EmptyBorder(15, 0, 15, 0));
        setPreferredSize(new Dimension(717, 182));

        // ===== Title =====
        JLabel lblTitle = new JLabel("");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 22));
        lblTitle.setForeground(new Color(70, 45, 30));

        // ===== Card Container =====
        RoundedPanel card = new RoundedPanel(30);
        card.setBackground(new Color(0xFEFCFB));
        card.setLayout(new GridLayout(1, 4, 30, 0)); 
        card.setBorder(new EmptyBorder(25, 30, 25, 30));
        

        // ===== Value Labels =====
        lblFullNameValue = createValueLabel();
        lblUsernameValue = createValueLabel();
        lblRoleValue = createValueLabel();
        lblStatusValue = new RoundedStatusLabel();

        card.add(createInfoBlock("Full Name", lblFullNameValue));
        card.add(createInfoBlock("Username", lblUsernameValue));
        card.add(createInfoBlock("Role", lblRoleValue));
        card.add(createInfoBlock("Status", lblStatusValue));

        add(lblTitle, BorderLayout.NORTH);
        add(card, BorderLayout.CENTER);
    }

    // ===== Normal Value Label =====
    private JLabel createValueLabel() {
        JLabel lbl = new JLabel("-");
        lbl.setFont(new Font("Segoe UI", Font.BOLD, 18));
        lbl.setForeground(new Color(0x553523));
        return lbl;
    }

    // ===== Info Block (Label + Value) =====
    private JPanel createInfoBlock(String labelText, JLabel valueLabel) {
        JPanel panel = new JPanel();
        panel.setOpaque(false);
        panel.setLayout(new BorderLayout(0, 6));

        JLabel label = new JLabel(labelText);
        label.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        label.setForeground(new Color(140, 110, 90));

        panel.add(label, BorderLayout.NORTH);
        panel.add(valueLabel, BorderLayout.CENTER);

        return panel;
    }

    // ===== Set Staff Info =====
    public void setStaffInfo(String fullName, String username, String role, String status) {
        lblFullNameValue.setText(fullName);
        lblUsernameValue.setText(username);
        lblRoleValue.setText(role);
        lblStatusValue.setText(status);

        if (status.equalsIgnoreCase("Active")) {
            lblStatusValue.setBackground(new Color(90, 160, 100));
        } else {
            lblStatusValue.setBackground(new Color(200, 80, 80));
        }
    }

    // ===== Rounded Card Panel =====
    class RoundedPanel extends JPanel {

        private int radius;

        public RoundedPanel(int radius) {
            this.radius = radius;
            setOpaque(false);
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON);

            g2.setColor(getBackground());
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), radius, radius);

            g2.dispose();
            super.paintComponent(g);
        }
    }

    // ===== Rounded Status Pill =====
    class RoundedStatusLabel extends JLabel {

        public RoundedStatusLabel() {
            setFont(new Font("Segoe UI", Font.BOLD, 14));
            setForeground(Color.WHITE);
            setHorizontalAlignment(SwingConstants.CENTER);
            setPreferredSize(new Dimension(110, 32));
            setOpaque(false);
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON);

            g2.setColor(getBackground());
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), 40, 40);

            super.paintComponent(g);
            g2.dispose();
        }
    }
}
