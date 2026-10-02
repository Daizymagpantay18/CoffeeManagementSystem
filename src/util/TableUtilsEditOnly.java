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
public class TableUtilsEditOnly {
    // optional table styling
    public static void styleTable(JTable table) {
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 12));
        table.setRowHeight(30);
    }

    // -------------------- ACTION RENDERER --------------------
    public static class ActionRenderer extends JPanel implements TableCellRenderer {

        private final JButton btnEdit = new JButton("Edit");

        public ActionRenderer() {
            setLayout(new FlowLayout(FlowLayout.CENTER, 5, 0));
            btnEdit.setFocusable(false);
            add(btnEdit);
        }

        @Override
        public Component getTableCellRendererComponent(
                JTable table, Object value,
                boolean isSelected, boolean hasFocus,
                int row, int column) {

            return this;
        }
    }

    // -------------------- ACTION EDITOR --------------------
    public static class ActionEditor extends AbstractCellEditor implements TableCellEditor {

        private JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 0));
        private JButton btnEdit = new JButton("Edit");

        public ActionEditor(JTable table, ActionHandler handler) {

            panel.add(btnEdit);

            btnEdit.addActionListener(e -> {
                int row = table.getEditingRow();
                handler.onEdit(row);
                fireEditingStopped();
            });
        }

        @Override
        public Component getTableCellEditorComponent(
                JTable table, Object value,
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

        // interface
        public interface ActionHandler {
            void onEdit(int row);
        }
    }
}
