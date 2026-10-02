/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package util;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableCellEditor;
import java.awt.*;
import java.util.EventObject;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;

public class TableUtils1 {
    
    // Call this to style table (optional: change colors/fonts)
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

    // -------------------- STATUS RENDERER --------------------
    public static class StatusRenderer extends JLabel implements TableCellRenderer {
        private Color bgColor;
        private int cornerRadius = 20;
        public StatusRenderer() {
            setHorizontalAlignment(SwingConstants.CENTER);
            setOpaque(false); 
            setFont(new Font("Segoe UI", Font.BOLD, 12));
            setForeground(Color.WHITE);
            setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));
        }

        @Override
        public Component getTableCellRendererComponent(
                JTable table, Object value,
                boolean isSelected, boolean hasFocus,
                int row, int column) {

            setText(value == null ? "" : value.toString());

            if ("LOW STOCK".equals(value)) {
                bgColor = new Color(220, 53, 69);
                setForeground(Color.WHITE);
            } else {
                bgColor = new Color(40, 167, 69);
                setForeground(Color.WHITE);
            }

            if (isSelected) {
                setBorder(BorderFactory.createLineBorder(Color.BLACK));
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
    
    // -------------------- ACTION RENDERER --------------------
    public static class ActionRenderer extends JPanel implements TableCellRenderer {

        private JButton editBtn;
        private JButton deleteBtn;

        public ActionRenderer() {
            setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
            setBackground(new Color(245, 234, 219));

            // EDIT ICON
            ImageIcon editIcon = new ImageIcon(getClass().getResource("/image/edit.png"));
            Image editImg = editIcon.getImage().getScaledInstance(18, 18, Image.SCALE_SMOOTH);
            editBtn = new JButton(new ImageIcon(editImg));

            // DELETE ICON
            ImageIcon deleteIcon = new ImageIcon(getClass().getResource("/image/delete.png"));
            Image deleteImg = deleteIcon.getImage().getScaledInstance(18, 18, Image.SCALE_SMOOTH);
            deleteBtn = new JButton(new ImageIcon(deleteImg));

            styleButton(editBtn);
            styleButton(deleteBtn);

            add(editBtn);
            add(deleteBtn);
        }

        private void styleButton(JButton btn){
            btn.setPreferredSize(new Dimension(28,28));
            btn.setBorderPainted(false);
            btn.setContentAreaFilled(false);
            btn.setFocusPainted(false);
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value,
                boolean isSelected, boolean hasFocus, int row, int column) {
            return this;
        }
    }


    // -------------------- ACTION EDITOR --------------------
    public static class ActionEditor extends AbstractCellEditor implements TableCellEditor {

        private JPanel panel;
        private JButton editBtn;
        private JButton deleteBtn;
        private JTable table;

        public ActionEditor(JTable table, ActionHandler handler) {

            this.table = table;

            panel = new JPanel(new FlowLayout(FlowLayout.CENTER,5,5));
            panel.setBackground(new Color(245,234,219));

            // EDIT ICON
            ImageIcon editIcon = new ImageIcon(getClass().getResource("/image/edit.png"));
            Image editImg = editIcon.getImage().getScaledInstance(18,18,Image.SCALE_SMOOTH);
            editBtn = new JButton(new ImageIcon(editImg));

            // DELETE ICON
            ImageIcon deleteIcon = new ImageIcon(getClass().getResource("/image/delete.png"));
            Image deleteImg = deleteIcon.getImage().getScaledInstance(18,18,Image.SCALE_SMOOTH);
            deleteBtn = new JButton(new ImageIcon(deleteImg));

            styleButton(editBtn);
            styleButton(deleteBtn);

            editBtn.addActionListener(e -> {
                int row = table.getEditingRow();
                handler.onEdit(row);
                fireEditingStopped();
            });

            deleteBtn.addActionListener(e -> {
                int row = table.getEditingRow();
                handler.onDelete(row);
                fireEditingStopped();
            });

            panel.add(editBtn);
            panel.add(deleteBtn);
        }

        private void styleButton(JButton btn){
            btn.setPreferredSize(new Dimension(28,28));
            btn.setBorderPainted(false);
            btn.setContentAreaFilled(false);
            btn.setFocusPainted(false);
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

        @Override
        public boolean isCellEditable(EventObject e) {
            return true;
        }

        // Interface
        public interface ActionHandler {
            void onEdit(int row);
            void onDelete(int row);
        }
    }

}