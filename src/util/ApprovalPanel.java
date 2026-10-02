/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package util;


import controllers.ApprovalController;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.util.List;
import javax.swing.AbstractCellEditor;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableRowSorter;
import models.Approval;


public class ApprovalPanel extends JPanel{
    private JTable table;
    private DefaultTableModel model;
    private ApprovalController approvalController;
    private TableRowSorter<DefaultTableModel> sorter;
    private JPanel mainPanel;

    public ApprovalPanel(JPanel mainPanel) {
        this.mainPanel = mainPanel;
        approvalController = new ApprovalController();

        setPreferredSize(new Dimension(774, 606));
        setLayout(new BorderLayout(15, 15));
        setBackground(new Color(245, 238, 226));
        setBorder(BorderFactory.createEmptyBorder(20, 25, 20, 25));

        // ===== Top Panel (Title + Back Button) =====
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setOpaque(false);

        RoundedButton backBtn = new RoundedButton(" Back");

        java.net.URL backUrl = getClass().getResource("/image/back.png");

        if (backUrl != null) {
            ImageIcon icon = new ImageIcon(backUrl);
            Image img = icon.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
            backBtn.setIcon(new ImageIcon(img));
        }

        backBtn.setPreferredSize(new Dimension(50, 35));

        backBtn.addActionListener(e -> {
            CardLayout cl = (CardLayout) mainPanel.getLayout();
            cl.show(mainPanel, "user");
        });

        JLabel title = new JLabel(" Requests ", SwingConstants.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 24));
        title.setForeground(new Color(92, 64, 51));

        topPanel.add(backBtn, BorderLayout.WEST);
        topPanel.add(title, BorderLayout.CENTER);

        add(topPanel, BorderLayout.NORTH);

        // ===== Table =====
        String[] columns = {"ID", "Staff", "Module", "Type", "Date", "Status"};
        model = new DefaultTableModel(columns, 0);
        table = new JTable(model);
        
        sorter = new TableRowSorter<>(model);
        table.setRowSorter(sorter);

        table.setRowHeight(40);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        table.setGridColor(new Color(220, 210, 190));
        table.setShowVerticalLines(false);

        // column widths...
        int[] widths = {60, 130, 100, 130, 120, 140};
        for (int i = 0; i < widths.length; i++) {
            table.getColumnModel().getColumn(i).setPreferredWidth(widths[i]);
        }

        // table header styles...
        
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
        table.getTableHeader().setBackground(new Color(141, 101, 65));
        table.getTableHeader().setForeground(Color.WHITE);

        // renderers
        table.setDefaultRenderer(Object.class, new ZebraRenderer());
        table.getColumn("Status").setCellRenderer(new StatusRenderer());
        table.getColumn("Status").setCellEditor(new ButtonEditor(table));
        table.getColumn("Module").setCellRenderer(new ModuleRenderer());

        JScrollPane scroll = new JScrollPane(table);
        scroll.setBorder(BorderFactory.createLineBorder(new Color(200, 180, 150), 1));
        add(scroll, BorderLayout.CENTER);
 

        loadApprovals();
    }

    public void loadApprovals() {
        List<Approval> approvals = approvalController.getAllRequests(); 
        model.setRowCount(0);

        for (Approval a : approvals) {
            model.addRow(new Object[]{
                "REQ-" + a.getRequestId(),
                a.getStaffName(),
                a.getModule(),
                a.getRequestType(),
                a.getDate(),
                a.getStatus()
            });
        }
    }

    // ===== Renderers =====
    class StatusRenderer extends DefaultTableCellRenderer {
        
        ButtonRenderer buttonRenderer = new ButtonRenderer();

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value,
                                                       boolean isSelected, boolean hasFocus,
                                                       int row, int column) {

            String status = value.toString();

            if (status.equalsIgnoreCase("Pending")) {
                return buttonRenderer.getTableCellRendererComponent(
                        table, value, isSelected, hasFocus, row, column);
            }

            return new RoundedStatusLabel(status);
        }
    }
    
    class RoundedStatusLabel extends JLabel {

        private Color bgColor;

        public RoundedStatusLabel(String text) {
            super(text, SwingConstants.CENTER);
            setOpaque(false);
            setForeground(Color.WHITE);
            setFont(new Font("Segoe UI", Font.BOLD, 12));
            setPreferredSize(new Dimension(90, 25));

            switch (text) {
                case "Pending" -> bgColor = new Color(255, 152, 0);
                case "Approved" -> bgColor = new Color(46, 125, 50);
                case "Rejected" -> bgColor = new Color(198, 40, 40);
                default -> bgColor = Color.GRAY;
            }
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                                RenderingHints.VALUE_ANTIALIAS_ON);

            g2.setColor(bgColor);
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);
            super.paintComponent(g);
            g2.dispose();
        }
    }

    // ===== Buttons =====
    class ButtonRenderer extends JPanel implements TableCellRenderer {
        JButton approve = new JButton();
        JButton reject = new JButton();

        public ButtonRenderer() {
            setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
            setBackground(Color.WHITE);

            // Set only icons, no text
            java.net.URL approveUrl = getClass().getResource("/image/check.png");
            java.net.URL rejectUrl = getClass().getResource("/image/exis.png");

            if (approveUrl != null) {
                ImageIcon icon = new ImageIcon(approveUrl);
                Image img = icon.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
                approve.setIcon(new ImageIcon(img));
            }

            if (rejectUrl != null) {
                ImageIcon icon = new ImageIcon(rejectUrl);
                Image img = icon.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
                reject.setIcon(new ImageIcon(img));
            }

            approve.setFocusPainted(false);
            approve.setBorderPainted(false);
            approve.setContentAreaFilled(false);

            reject.setFocusPainted(false);
            reject.setBorderPainted(false);
            reject.setContentAreaFilled(false);

            add(approve);
            add(reject);
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value,
                                                       boolean isSelected, boolean hasFocus,
                                                       int row, int column) {

            String status = table.getValueAt(row, 5).toString();

            if (!status.equalsIgnoreCase("Pending")) {
                return new RoundedStatusLabel(status); 
            }

            return this;
        }
    }

    class ButtonEditor extends AbstractCellEditor implements TableCellEditor {

        JPanel panel = new JPanel();
        JButton approve = new JButton();
        JButton reject = new JButton();

        public ButtonEditor(JTable table) {

            panel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
            panel.setBackground(Color.WHITE);

            // Load icons (same as renderer)
            java.net.URL approveUrl = getClass().getResource("/image/check.png");
            java.net.URL rejectUrl = getClass().getResource("/image/exis.png");

            if (approveUrl != null) {
                ImageIcon icon = new ImageIcon(approveUrl);
                Image img = icon.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
                approve.setIcon(new ImageIcon(img));
            }

            if (rejectUrl != null) {
                ImageIcon icon = new ImageIcon(rejectUrl);
                Image img = icon.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
                reject.setIcon(new ImageIcon(img));
            }

            approve.setFocusPainted(false);
            approve.setBorderPainted(false);
            approve.setContentAreaFilled(false);

            reject.setFocusPainted(false);
            reject.setBorderPainted(false);
            reject.setContentAreaFilled(false);

            panel.add(approve);
            panel.add(reject);

            // ===== APPROVE ACTION =====
            approve.addActionListener(e -> {

                int viewRow = table.getEditingRow();
                if (viewRow < 0) return;

                int row = table.convertRowIndexToModel(viewRow);

                String requestText = table.getModel().getValueAt(row, 0).toString();
                int requestId = Integer.parseInt(requestText.split("-")[1]);

                int confirm = JOptionPane.showConfirmDialog(
                        table,
                        "Approve this request?",
                        "Confirm Approval",
                        JOptionPane.YES_NO_OPTION
                );

                if (confirm == JOptionPane.YES_OPTION) {
                    try {
                        approvalController.approveRequest(requestId);
                        loadApprovals();
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(table,
                                "Failed to approve: " + ex.getMessage());
                    }
                }

                fireEditingStopped();
            });

            // ===== REJECT ACTION =====
            reject.addActionListener(e -> {

                int viewRow = table.getEditingRow();
                if (viewRow < 0) return;

                int row = table.convertRowIndexToModel(viewRow);

                String requestText = table.getModel().getValueAt(row, 0).toString();
                int requestId = Integer.parseInt(requestText.split("-")[1]);

                int confirm = JOptionPane.showConfirmDialog(
                        table,
                        "Reject this request?",
                        "Confirm Rejection",
                        JOptionPane.YES_NO_OPTION
                );

                if (confirm == JOptionPane.YES_OPTION) {
                    try {
                        approvalController.rejectRequest(requestId);
                        loadApprovals();
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(table,
                                "Failed to reject: " + ex.getMessage());
                    }
                }

                fireEditingStopped();
            });
        }

        @Override
        public Component getTableCellEditorComponent(JTable table, Object value,
                                                     boolean isSelected, int row, int column) {

            String status = table.getValueAt(row, 5).toString();

            if (!status.equalsIgnoreCase("Pending")) {
                return new RoundedStatusLabel(status);
            }

            return panel;
        }

        @Override
        public Object getCellEditorValue() {
            return null;
        }
    }
    
    class ModuleRenderer extends DefaultTableCellRenderer {
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value,
                                                       boolean isSelected, boolean hasFocus,
                                                       int row, int column) {
            JLabel label = new JLabel(value.toString(), SwingConstants.CENTER);
            label.setOpaque(true);
            switch (value.toString()) {
                case "Password" -> label.setBackground(new Color(255, 235, 59)); 
                case "Inventory" -> label.setBackground(new Color(33, 150, 243)); 
                case "Product" -> label.setBackground(new Color(76, 175, 80)); 
                default -> label.setBackground(Color.LIGHT_GRAY);
            }
            label.setForeground(Color.BLACK);
            return label;
        }
    }
    
    // ===== Zebra Row Renderer =====
    class ZebraRenderer extends DefaultTableCellRenderer {
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value,
                                                       boolean isSelected, boolean hasFocus,
                                                       int row, int column) {

            Component c = super.getTableCellRendererComponent(
                    table, value, isSelected, hasFocus, row, column);

            if (!isSelected) {
                if (row % 2 == 0) {
                    c.setBackground(new Color(255, 250, 240)); // light cream
                } else {
                    c.setBackground(Color.WHITE);
                }
            } else {
                c.setBackground(new Color(200, 180, 150)); // selected color
            }

            return c;
        }
    }
    
    public void highlightByUserId(int userId) {

        for (int i = 0; i < table.getRowCount(); i++) {

            String requestText = table.getValueAt(i, 0).toString();

            int requestId = Integer.parseInt(requestText.split("-")[1]);

            if (requestId == userId) {

                table.setRowSelectionInterval(i, i);

                table.scrollRectToVisible(
                    table.getCellRect(i, 0, true)
                );

                break;
            }
        }
    }
}
