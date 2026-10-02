/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package util;

import controllers.UserController;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import models.user;

public class EditUserDialog extends JDialog{
    private JTextField txtFirstName, txtLastName, txtUserName;
    private JPasswordField txtPassword;
    private JComboBox<String> roleCombo;
    private JComboBox<String> statusCombo;
    private JButton btnSave, btnCancel;
    private user editingUser;
    private RefreshTableListener listener;

    public EditUserDialog(user u) {
        this.editingUser = u;
        setTitle("Edit User");
        setModal(true);
        setSize(400, 450);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel();
        mainPanel.setBackground(new Color(248, 242, 226)); 
        mainPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5,5,5,5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Title
        JLabel lblTitle = new JLabel("Edit User");
        lblTitle.setFont(new Font("Arial", Font.BOLD, 24));
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        mainPanel.add(lblTitle, gbc);

        JLabel lblDesc = new JLabel("Update user details");
        lblDesc.setFont(new Font("Arial", Font.PLAIN, 14));
        gbc.gridy = 1;
        mainPanel.add(lblDesc, gbc);

        gbc.gridwidth = 1; // reset

        // First Name
        gbc.gridx = 0; gbc.gridy = 2;
        mainPanel.add(new JLabel("First Name:"), gbc);
        txtFirstName = new JTextField(u.getFirstName(), 20);
        gbc.gridx = 1;
        mainPanel.add(txtFirstName, gbc);

        // Last Name
        gbc.gridx = 0; gbc.gridy = 3;
        mainPanel.add(new JLabel("Last Name:"), gbc);
        txtLastName = new JTextField(u.getLastName(), 20);
        gbc.gridx = 1;
        mainPanel.add(txtLastName, gbc);

        // Username
        gbc.gridx = 0; gbc.gridy = 4;
        mainPanel.add(new JLabel("Username:"), gbc);
        txtUserName = new JTextField(u.getUserName(), 20);
        gbc.gridx = 1;
        mainPanel.add(txtUserName, gbc);

        // Role
        gbc.gridx = 0; gbc.gridy = 6;
        mainPanel.add(new JLabel("Role:"), gbc);
        roleCombo = new JComboBox<>(new String[]{"Cashier", "Barista"});
        roleCombo.setSelectedItem(u.getRole());
        gbc.gridx = 1;
        mainPanel.add(roleCombo, gbc);

        // Status
        gbc.gridx = 0; gbc.gridy = 7;
        mainPanel.add(new JLabel("Status:"), gbc);
        statusCombo = new JComboBox<>(new String[]{"Active", "Inactive"});
        statusCombo.setSelectedItem(u.getStatus());
        gbc.gridx = 1;
        mainPanel.add(statusCombo, gbc);

        // Buttons
        btnSave = new JButton("Save");
        btnSave.setBackground(new Color(76, 175, 80)); // green
        btnSave.setForeground(Color.WHITE);
        gbc.gridx = 0; gbc.gridy = 8; gbc.gridwidth = 1;
        mainPanel.add(btnSave, gbc);

        btnCancel = new JButton("Cancel");
        btnCancel.setBackground(new Color(244, 67, 54)); // red
        btnCancel.setForeground(Color.WHITE);
        gbc.gridx = 1;
        mainPanel.add(btnCancel, gbc);

        add(mainPanel);

        // Actions
        btnSave.addActionListener(e -> saveWithConfirmation());
        btnCancel.addActionListener(e -> cancelWithConfirmation());
    }

    private void saveWithConfirmation() {
        int confirm = JOptionPane.showConfirmDialog(this,
                "Are you sure you want to save changes?",
                "Confirm Save", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            saveUser();
        }
    }

    private void cancelWithConfirmation() {
        int confirm = JOptionPane.showConfirmDialog(this,
                "Are you sure you want to cancel editing?",
                "Confirm Cancel", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            dispose();
        }
    }

    private void saveUser() {
        String fname = txtFirstName.getText().trim();
        String lname = txtLastName.getText().trim();
        String uname = txtUserName.getText().trim();

        // Validation
        if (fname.isEmpty() || lname.isEmpty() || uname.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "First Name, Last Name, and Username cannot be empty!",
                    "Validation Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (fname.length() < 2 || lname.length() < 2) {
            JOptionPane.showMessageDialog(this,
                    "First and Last Name must be at least 2 characters!",
                    "Validation Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!uname.equals(editingUser.getUserName()) &&
                new UserController().isUsernameExists(uname)) {
            JOptionPane.showMessageDialog(this,
                    "Username already exists!", "Validation Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        editingUser.setFirstName(fname);
        editingUser.setLastName(lname);
        editingUser.setUserName(uname);
        editingUser.setRole((String) roleCombo.getSelectedItem());
        editingUser.setStatus((String) statusCombo.getSelectedItem());

        new UserController().updateUser(editingUser);

        JOptionPane.showMessageDialog(this, "User updated successfully!");
        if (listener != null) listener.refreshTable();
        dispose();
    }

    public void setRefreshTableListener(RefreshTableListener listener) {
        this.listener = listener;
    }
}
interface RefreshTableListener {
    void refreshTable();
}