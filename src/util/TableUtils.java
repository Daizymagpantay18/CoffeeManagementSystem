/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package util;

import java.awt.*;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;

public class TableUtils {
    private TableActionListener listener;
    
    public static void styleTable(JTable table) {
        table.setRowHeight(30);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        table.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
        
        table.setBackground(new Color(245, 234, 219));
        table.setForeground(new Color(60, 40, 20));

        table.setShowGrid(false);
        table.setIntercellSpacing(new Dimension(0, 0));

        JTableHeader header = table.getTableHeader();
        header.setFont(new Font("Segoe UI", Font.BOLD, 14));
        header.setBackground(new Color(146, 109, 79));
        header.setForeground(Color.WHITE);
        header.setOpaque(true);

        table.setSelectionBackground(new Color(220, 200, 170));
        table.setSelectionForeground(Color.BLACK);
    }

    public static class StatusRenderer extends JLabel implements TableCellRenderer {
        private Color bgColor;
        private int cornerRadius = 20;
        public StatusRenderer() {
            setHorizontalAlignment(SwingConstants.CENTER);
            setOpaque(false); // IMPORTANT
            setFont(new Font("Segoe UI", Font.BOLD, 12));
            setForeground(Color.WHITE);
            setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value,
                                                       boolean isSelected, boolean hasFocus,
                                                       int row, int column) {

            String status = value == null ? "" : value.toString();
            setText(status);

            switch (status) {
                case "Active":
                case "Available":
                    bgColor = new Color(40, 167, 69); // Green
                    break;
                case "LOW STOCK":
                    bgColor = new Color(220, 53, 69); // Red
                    break;
                case "Not Available":
                    bgColor = new Color(108, 117, 125); // Gray
                    break;
                default:
                    bgColor = new Color(146, 109, 79); // Brown for unknown status
                    break;
            }

            if (isSelected) {
                setBorder(BorderFactory.createLineBorder(Color.BLACK));
            } else {
                setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));
            }
            return this;
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                                RenderingHints.VALUE_ANTIALIAS_ON);

            g2.setColor(bgColor);
            g2.fillRoundRect(5, 5, getWidth() - 10, getHeight() - 10,
                             cornerRadius, cornerRadius);

            g2.dispose();
            super.paintComponent(g);
        }
    }

    public static class ActionRenderer extends JPanel implements TableCellRenderer {

        private JButton editBtn;

        public ActionRenderer() {
            setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
            setBackground(new Color(245, 234, 219));

            ImageIcon icon = new ImageIcon(getClass().getResource("/image/edit.png"));
            Image img = icon.getImage().getScaledInstance(18, 18, Image.SCALE_SMOOTH);
            ImageIcon scaledIcon = new ImageIcon(img);

            editBtn = new JButton(scaledIcon);
            editBtn.setPreferredSize(new Dimension(28, 28));
            editBtn.setBorderPainted(false);
            editBtn.setContentAreaFilled(false);
            editBtn.setFocusPainted(false);
            editBtn.setBorderPainted(false);
            editBtn.setContentAreaFilled(false);

            add(editBtn);
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value,
                                                       boolean isSelected, boolean hasFocus,
                                                       int row, int column) {
            return this;
        }
    }

    public static class ActionEditor extends AbstractCellEditor implements TableCellEditor {

        private JPanel panel;
        private JButton editBtn;
        private JTable table;

        public ActionEditor(JTable table, TableActionListener listener) {
            this.table = table;

            panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 5));
            panel.setBackground(new Color(245, 234, 219));

            ImageIcon icon = new ImageIcon(getClass().getResource("/image/edit.png"));
            Image img = icon.getImage().getScaledInstance(18, 18, Image.SCALE_SMOOTH);
            ImageIcon scaledIcon = new ImageIcon(img);

            editBtn = new JButton(scaledIcon);
            editBtn.setPreferredSize(new Dimension(28, 28));
            editBtn.setBorderPainted(false);
            editBtn.setContentAreaFilled(false);
            editBtn.setFocusPainted(false);

            editBtn.setBorderPainted(false);
            editBtn.setContentAreaFilled(false);

            editBtn.addActionListener(e -> {
                int row = table.getSelectedRow();
                if (listener != null) {
                    listener.onEdit(row);
                }
                fireEditingStopped();
            });


            panel.add(editBtn);
            
        }

        @Override
        public Component getTableCellEditorComponent(JTable table, Object value,
                                                     boolean isSelected, int row, int column) {
            return panel;
        }

        @Override
        public Object getCellEditorValue() {
            return null;
        }
    }
    
    public interface TableActionListener {
        void onEdit(int row);
    }
}
