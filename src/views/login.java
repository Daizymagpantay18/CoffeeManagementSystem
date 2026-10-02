/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package views;
import views.Barista.Barista;
import views.Admin.Admin;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import controllers.UserController;
import java.awt.Image;
import java.awt.Toolkit;
import javax.swing.ImageIcon;
import javax.swing.JPasswordField;
import util.RoundedField;
import util.PlaceholderUtil;
import util.ButtonStyler;
import util.PanelUtils;
import util.RoundedButton;
import views.Cashier.C;

public class login extends javax.swing.JFrame {

    public login() {
        initComponents();
        enterimg();setIconImage();
        PanelUtils.makeRounded(loginPanel, 25);
        
        javax.swing.GroupLayout layout = (javax.swing.GroupLayout) loginPanel.getLayout();
        
        RoundedField username = new RoundedField(15, false);
        RoundedField password = new RoundedField(15, true);

        // replace old components
        layout.replace(txtUsername, username);
        layout.replace(txtPassword, password);

        // update references
        txtUsername = username;
        txtPassword = password;

        // Placeholder setup
        setupPlaceholder(txtUsername, "Enter Username");
        setupPlaceholder(txtPassword, "Enter Password");

        // revalidate panel
        loginPanel.revalidate();
        loginPanel.repaint();
        
        RoundedButton loginBtn = new RoundedButton("Login", null);
        ButtonStyler.applyCoffeeStyle(loginBtn);
        loginBtn.addActionListener(evt -> btnLoginActionPerformed(evt));

        layout.replace(btnLogin, loginBtn);
        btnLogin = loginBtn;
        
        this.setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);

        this.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                if(confirmExit()){
                    System.exit(0);
                }
            }
        });
        
        
    }
    public void enterimg(){
        ImageIcon icon = new ImageIcon(getClass().getResource("/image/ja.png"));
        Image img = icon.getImage().getScaledInstance(logo.getWidth(), logo.getHeight(), Image.SCALE_SMOOTH);
        logo.setIcon(new ImageIcon(img));
    }
    private void setIconImage(){
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/image/ja.png")));
    }
    private boolean confirmExit() {
        int confirm = JOptionPane.showConfirmDialog(
            this,
            "Are you sure you want to exit the system?",
            "Exit Confirmation",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.QUESTION_MESSAGE
        );

        return confirm == JOptionPane.YES_OPTION;
    }
    
    // For regular JTextField
    private void setupPlaceholder(JTextField field, String placeholder) {
        field.setText(placeholder);
        PlaceholderUtil.addPlaceholderStyle(field);

        field.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                if (field.getText().equals(placeholder)) {
                    field.setText("");
                    PlaceholderUtil.removePlaceholderStyle(field);
                }
            }

            public void focusLost(java.awt.event.FocusEvent evt) {
                if (field.getText().trim().isEmpty()) {
                    field.setText(placeholder);
                    PlaceholderUtil.addPlaceholderStyle(field);
                }
            }
        });
    }

    // For JPasswordField
    private void setupPlaceholder(JPasswordField field, String placeholder) {
        field.setText(placeholder);
        field.setEchoChar('\u0000'); 
        PlaceholderUtil.addPlaceholderStyle(field);

        field.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                if (String.valueOf(field.getPassword()).equals(placeholder)) {
                    field.setText("");
                    field.setEchoChar('•'); 
                    PlaceholderUtil.removePlaceholderStyle(field);
                }
            }

            public void focusLost(java.awt.event.FocusEvent evt) {
                if (String.valueOf(field.getPassword()).trim().isEmpty()) {
                    field.setText(placeholder);
                    field.setEchoChar('\u0000'); 
                    PlaceholderUtil.addPlaceholderStyle(field);
                }
            }
        });
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        loginPanel = new javax.swing.JPanel();
        txtPassword = new javax.swing.JPasswordField();
        btnLogin = new javax.swing.JButton();
        txtUsername = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        logo = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Coffee Management System");

        jPanel1.setBackground(new java.awt.Color(244, 236, 223));
        jPanel1.setForeground(new java.awt.Color(244, 236, 223));

        jLabel2.setFont(new java.awt.Font("Georgia", 3, 20)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(76, 51, 35));
        jLabel2.setText(" Coffee Management System");

        loginPanel.setBackground(new java.awt.Color(254, 252, 251));
        loginPanel.setBorder(javax.swing.BorderFactory.createCompoundBorder(javax.swing.BorderFactory.createEtchedBorder(java.awt.Color.white, java.awt.Color.white), null));

        txtPassword.setBackground(new java.awt.Color(244, 236, 223));
        txtPassword.setText("Enter Password");

        btnLogin.setBackground(new java.awt.Color(153, 102, 0));
        btnLogin.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        btnLogin.setForeground(new java.awt.Color(254, 252, 251));
        btnLogin.setText("Login");
        btnLogin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLoginActionPerformed(evt);
            }
        });

        txtUsername.setBackground(new java.awt.Color(244, 236, 223));
        txtUsername.setForeground(new java.awt.Color(51, 51, 51));
        txtUsername.setText("Enter Username");
        txtUsername.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtUsernameActionPerformed(evt);
            }
        });

        jLabel6.setText("Username");

        jLabel7.setText("Password");

        javax.swing.GroupLayout loginPanelLayout = new javax.swing.GroupLayout(loginPanel);
        loginPanel.setLayout(loginPanelLayout);
        loginPanelLayout.setHorizontalGroup(
            loginPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(loginPanelLayout.createSequentialGroup()
                .addGroup(loginPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(loginPanelLayout.createSequentialGroup()
                        .addGap(147, 147, 147)
                        .addComponent(btnLogin, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(loginPanelLayout.createSequentialGroup()
                        .addGap(66, 66, 66)
                        .addGroup(loginPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel6)
                            .addGroup(loginPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(txtPassword)
                                .addComponent(txtUsername, javax.swing.GroupLayout.DEFAULT_SIZE, 243, Short.MAX_VALUE))
                            .addComponent(jLabel7))))
                .addContainerGap(59, Short.MAX_VALUE))
        );
        loginPanelLayout.setVerticalGroup(
            loginPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(loginPanelLayout.createSequentialGroup()
                .addGap(54, 54, 54)
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtUsername, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel7)
                .addGap(0, 0, 0)
                .addComponent(txtPassword, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(40, 40, 40)
                .addComponent(btnLogin)
                .addContainerGap(44, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(169, 169, 169)
                        .addComponent(logo, javax.swing.GroupLayout.PREFERRED_SIZE, 176, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(79, 79, 79)
                        .addComponent(loginPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(111, 111, 111)
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 304, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(83, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(logo, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(loginPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(71, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnLoginActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLoginActionPerformed

        String uname = txtUsername.getText().trim();
        String password = new String(txtPassword.getPassword()).trim();

        UserController usercon = new UserController();
        models.user loggedUser = usercon.authenticate(uname, password);

        if (loggedUser != null) {
            String status = loggedUser.getStatus();
         
            if (status.equalsIgnoreCase("inactive")) {
                JOptionPane.showMessageDialog(this, 
                        "Your account is inactive. Please contact the admin.",
                        "Access Denied",
                        JOptionPane.WARNING_MESSAGE);
                txtPassword.setText("");
                txtUsername.requestFocus();
                return; 
            }


            String role = loggedUser.getRole();

            switch (role.toLowerCase()) {

                case "admin" -> {
                    JOptionPane.showMessageDialog(this, 
                            "Welcome Admin " + loggedUser.getFirstName());
                    new Admin(loggedUser).setVisible(true);

                }

                case "cashier" -> {
                    JOptionPane.showMessageDialog(this, 
                            "Welcome Cashier " + loggedUser.getFirstName());
                    new C(loggedUser).setVisible(true);
                }

                case "barista" -> {
                    JOptionPane.showMessageDialog(this, 
                            "Welcome Barista " + loggedUser.getFirstName());
                    new Barista(loggedUser).setVisible(true);
                }

                default -> {
                    JOptionPane.showMessageDialog(this,
                            "Unknown role detected!",
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }

            this.dispose();

        } else {
            JOptionPane.showMessageDialog(this, 
                    "Invalid Username or Password",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnLoginActionPerformed

    private void txtUsernameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtUsernameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtUsernameActionPerformed
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
      
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new login().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnLogin;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel loginPanel;
    private javax.swing.JLabel logo;
    private javax.swing.JPasswordField txtPassword;
    private javax.swing.JTextField txtUsername;
    // End of variables declaration//GEN-END:variables
}
