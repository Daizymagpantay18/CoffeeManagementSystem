/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package views.Cashier;

import controllers.UserController;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JOptionPane;
import models.user;
import util.RoundedButton;
import util.SidebarUtils;
import views.login;
import javax.swing.JPasswordField;
import util.ButtonStyler;
import util.PanelUtils;
import util.PlaceholderUtil;
import util.RoundedField;
import util.StaffInfoPanel;


public class Cashier extends javax.swing.JFrame {

    private StaffInfoPanel staffInfoPanel;
    private user loggedUser;
    public Cashier(models.user loggedUser) {
        initComponents();
        fullName.setText(loggedUser.getFullName());
        fullName1.setText(loggedUser.getFullName());
        role.setText(loggedUser.getRole().toUpperCase());
        
        this.loggedUser = loggedUser;
        PanelUtils.makeRounded(changePasswordPanel, 30);
        PanelUtils.makeRounded(staffInformationPanel, 30);
        
        
        staffInfoPanel = new StaffInfoPanel();
        staffInformationPanel.setLayout(new BorderLayout());
        staffInformationPanel.add(staffInfoPanel, BorderLayout.CENTER);
        staffInformationPanel.setPreferredSize(new Dimension(717, 182));
        
        staffInfoPanel.setStaffInfo(
            loggedUser.getFullName(),
            loggedUser.getUserName(),
            loggedUser.getRole(),
            loggedUser.getStatus()
        );
        
        SidebarUtils.setupSidebarButton(btnPos);
        SidebarUtils.setupSidebarButton(btnSettings);

        SidebarUtils.setIcon(btnPos, "/image/card.png", Cashier.class,new Color(244, 236, 223));
        SidebarUtils.setIcon(btnSettings, "/image/setting.png", Cashier.class, new Color(244, 236, 223));
        SidebarUtils.setIcon(notif, "/image/notif.png", Cashier.class, new Color(239,224,205));
        
        btnPos.doClick();
        
        
        // get the layout
        javax.swing.GroupLayout layout = (javax.swing.GroupLayout) changePasswordPanel.getLayout();

        // create new RoundedFields
        RoundedField current = new RoundedField(15, true);
        RoundedField newPass = new RoundedField(15, true);
        RoundedField confirm = new RoundedField(15, true);

        // replace old components
        layout.replace(txtCurrentPassword, current);
        layout.replace(txtNewPassword, newPass);
        layout.replace(txtConfirmPassword, confirm);

        // update references
        txtCurrentPassword = current;
        txtNewPassword = newPass;
        txtConfirmPassword = confirm;

        // Placeholder setup
        setupPasswordPlaceholder(txtCurrentPassword, "Enter Current Password");
        setupPasswordPlaceholder(txtNewPassword, "Enter New Password");
        setupPasswordPlaceholder(txtConfirmPassword, "Confirm New Password");

        // revalidate panel
        changePasswordPanel.revalidate();
        changePasswordPanel.repaint();
        
        RoundedButton SubmitRequest = new RoundedButton("Submit Request", null);
        ButtonStyler.applyCoffeeStyle(SubmitRequest);
        SubmitRequest.addActionListener(evt -> btnSubmitRequestActionPerformed(evt));
        
        layout.replace(btnSubmitRequest, SubmitRequest);
        btnSubmitRequest = SubmitRequest;
        RoundedButton cancel = new RoundedButton("Cancel", null);
        ButtonStyler.applyCancelStyle(cancel);
        cancel.addActionListener(evt -> btnCancelActionPerformed(evt));
        layout.replace(btnCancel, cancel);
        btnCancel = cancel;
        
    }
    
    private void setupPasswordPlaceholder(JPasswordField field, String placeholder) {

        field.setText(placeholder);
        field.setEchoChar('\u0000');
        PlaceholderUtil.addPlaceholderStyle(field);

        field.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                if(String.valueOf(field.getPassword()).equals(placeholder)){
                    field.setText("");
                    field.setEchoChar('•');
                    PlaceholderUtil.removePlaceholderStyle(field);
                }
            }

            public void focusLost(java.awt.event.FocusEvent evt) {
                if(String.valueOf(field.getPassword()).trim().isEmpty()){
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
        mainPanel = new javax.swing.JPanel();
        posPanel = new javax.swing.JPanel();
        jComboBox1 = new javax.swing.JComboBox<>();
        settingsPanel = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        staffInformationPanel = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        changePasswordPanel = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        txtCurrentPassword = new javax.swing.JPasswordField();
        jLabel10 = new javax.swing.JLabel();
        txtNewPassword = new javax.swing.JPasswordField();
        jLabel11 = new javax.swing.JLabel();
        txtConfirmPassword = new javax.swing.JPasswordField();
        btnSubmitRequest = new javax.swing.JButton();
        btnCancel = new javax.swing.JButton();
        jSeparator3 = new javax.swing.JSeparator();
        sideBar = new javax.swing.JPanel();
        btnPos = new javax.swing.JButton();
        btnSettings = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JSeparator();
        btnLogout = new javax.swing.JButton();
        fullName = new javax.swing.JLabel();
        role = new javax.swing.JLabel();
        jSeparator2 = new javax.swing.JSeparator();
        jLabel4 = new javax.swing.JLabel();
        top = new javax.swing.JPanel();
        fullName1 = new javax.swing.JLabel();
        notif = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(245, 234, 219));

        jPanel1.setBackground(new java.awt.Color(245, 234, 219));

        mainPanel.setBackground(new java.awt.Color(245, 234, 219));
        mainPanel.setPreferredSize(new java.awt.Dimension(774, 700));
        mainPanel.setLayout(new java.awt.CardLayout());

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        javax.swing.GroupLayout posPanelLayout = new javax.swing.GroupLayout(posPanel);
        posPanel.setLayout(posPanelLayout);
        posPanelLayout.setHorizontalGroup(
            posPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, posPanelLayout.createSequentialGroup()
                .addContainerGap(382, Short.MAX_VALUE)
                .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(342, 342, 342))
        );
        posPanelLayout.setVerticalGroup(
            posPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(posPanelLayout.createSequentialGroup()
                .addGap(223, 223, 223)
                .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(320, Short.MAX_VALUE))
        );

        mainPanel.add(posPanel, "POS");

        settingsPanel.setBackground(new java.awt.Color(245, 234, 219));

        jLabel5.setFont(new java.awt.Font("Segoe UI", 3, 24)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(85, 53, 35));
        jLabel5.setText("Settings");

        staffInformationPanel.setBackground(new java.awt.Color(239, 224, 205));

        jLabel9.setFont(new java.awt.Font("Segoe UI", 1, 22)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(85, 53, 35));
        jLabel9.setText("Account Information");

        javax.swing.GroupLayout staffInformationPanelLayout = new javax.swing.GroupLayout(staffInformationPanel);
        staffInformationPanel.setLayout(staffInformationPanelLayout);
        staffInformationPanelLayout.setHorizontalGroup(
            staffInformationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(staffInformationPanelLayout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 353, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        staffInformationPanelLayout.setVerticalGroup(
            staffInformationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(staffInformationPanelLayout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(jLabel9)
                .addContainerGap(128, Short.MAX_VALUE))
        );

        changePasswordPanel.setBackground(new java.awt.Color(239, 224, 205));

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(85, 53, 35));
        jLabel6.setText("Change Password");

        jLabel7.setFont(new java.awt.Font("Arial", 0, 10)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(125, 102, 88));
        jLabel7.setText("Password changes required Admin approval. Your request will be viewed before the update takes effect.");

        jLabel8.setText("Current Password");

        txtCurrentPassword.setText("Enter Current Password");
        txtCurrentPassword.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCurrentPasswordActionPerformed(evt);
            }
        });

        jLabel10.setText("New Password");

        txtNewPassword.setText("jPasswordField1");

        jLabel11.setText("Confirm New Password");

        txtConfirmPassword.setText("jPasswordField1");

        btnSubmitRequest.setText("Submit Request");
        btnSubmitRequest.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSubmitRequestActionPerformed(evt);
            }
        });

        btnCancel.setText("Cancel");
        btnCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelActionPerformed(evt);
            }
        });

        jSeparator3.setBackground(new java.awt.Color(0, 0, 0));

        javax.swing.GroupLayout changePasswordPanelLayout = new javax.swing.GroupLayout(changePasswordPanel);
        changePasswordPanel.setLayout(changePasswordPanelLayout);
        changePasswordPanelLayout.setHorizontalGroup(
            changePasswordPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, changePasswordPanelLayout.createSequentialGroup()
                .addContainerGap(51, Short.MAX_VALUE)
                .addGroup(changePasswordPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(changePasswordPanelLayout.createSequentialGroup()
                        .addComponent(btnSubmitRequest)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnCancel))
                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 353, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 629, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8)
                    .addComponent(jLabel11)
                    .addComponent(jLabel10)
                    .addGroup(changePasswordPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(txtConfirmPassword, javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(txtNewPassword, javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(txtCurrentPassword, javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jSeparator3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 595, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(37, 37, 37))
        );
        changePasswordPanelLayout.setVerticalGroup(
            changePasswordPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(changePasswordPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel7)
                .addGap(18, 18, 18)
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtCurrentPassword, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel10)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtNewPassword, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel11)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtConfirmPassword, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(changePasswordPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnSubmitRequest)
                    .addComponent(btnCancel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(23, 23, 23))
        );

        javax.swing.GroupLayout settingsPanelLayout = new javax.swing.GroupLayout(settingsPanel);
        settingsPanel.setLayout(settingsPanelLayout);
        settingsPanelLayout.setHorizontalGroup(
            settingsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(settingsPanelLayout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(settingsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 353, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(settingsPanelLayout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addGroup(settingsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(staffInformationPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(changePasswordPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap(49, Short.MAX_VALUE))
        );
        settingsPanelLayout.setVerticalGroup(
            settingsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(settingsPanelLayout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(staffInformationPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(changePasswordPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(11, Short.MAX_VALUE))
        );

        mainPanel.add(settingsPanel, "setting");

        sideBar.setBackground(new java.awt.Color(146, 109, 79));
        sideBar.setPreferredSize(new java.awt.Dimension(223, 700));

        btnPos.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        btnPos.setForeground(new java.awt.Color(244, 236, 223));
        btnPos.setText("POS");
        btnPos.setAlignmentY(0.0F);
        btnPos.setBorderPainted(false);
        btnPos.setContentAreaFilled(false);
        btnPos.setRequestFocusEnabled(false);
        btnPos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPosActionPerformed(evt);
            }
        });

        btnSettings.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        btnSettings.setForeground(new java.awt.Color(244, 236, 223));
        btnSettings.setText("Settings");
        btnSettings.setBorderPainted(false);
        btnSettings.setContentAreaFilled(false);
        btnSettings.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSettingsActionPerformed(evt);
            }
        });

        btnLogout.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        btnLogout.setForeground(new java.awt.Color(244, 236, 223));
        btnLogout.setText("Logout");
        btnLogout.setBorderPainted(false);
        btnLogout.setContentAreaFilled(false);
        btnLogout.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLogoutActionPerformed(evt);
            }
        });

        fullName.setFont(new java.awt.Font("Segoe UI Black", 0, 15)); // NOI18N
        fullName.setForeground(new java.awt.Color(244, 236, 223));
        fullName.setText("jLabel4");

        role.setForeground(new java.awt.Color(244, 236, 223));
        role.setText("jLabel4");

        jLabel4.setForeground(new java.awt.Color(244, 236, 223));
        jLabel4.setText("•");

        javax.swing.GroupLayout sideBarLayout = new javax.swing.GroupLayout(sideBar);
        sideBar.setLayout(sideBarLayout);
        sideBarLayout.setHorizontalGroup(
            sideBarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(sideBarLayout.createSequentialGroup()
                .addGroup(sideBarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(sideBarLayout.createSequentialGroup()
                        .addGap(39, 39, 39)
                        .addGroup(sideBarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(fullName, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(sideBarLayout.createSequentialGroup()
                                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(role, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))))
                    .addGroup(sideBarLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(sideBarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnPos, javax.swing.GroupLayout.DEFAULT_SIZE, 217, Short.MAX_VALUE)
                            .addComponent(btnSettings, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jSeparator1, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(btnLogout, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jSeparator2))))
                .addContainerGap())
        );
        sideBarLayout.setVerticalGroup(
            sideBarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(sideBarLayout.createSequentialGroup()
                .addGap(82, 82, 82)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnPos, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnSettings, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(fullName)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(sideBarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(role)
                    .addComponent(jLabel4))
                .addGap(4, 4, 4)
                .addComponent(btnLogout)
                .addContainerGap())
        );

        top.setBackground(new java.awt.Color(239, 224, 205));

        fullName1.setFont(new java.awt.Font("Segoe UI Black", 0, 15)); // NOI18N
        fullName1.setForeground(new java.awt.Color(146, 109, 79));
        fullName1.setText("jLabel4");

        javax.swing.GroupLayout topLayout = new javax.swing.GroupLayout(top);
        top.setLayout(topLayout);
        topLayout.setHorizontalGroup(
            topLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, topLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(notif, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(fullName1, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        topLayout.setVerticalGroup(
            topLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(topLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(fullName1)
                .addGap(0, 0, Short.MAX_VALUE))
            .addComponent(notif, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(sideBar, javax.swing.GroupLayout.PREFERRED_SIZE, 229, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(top, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(mainPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 796, Short.MAX_VALUE)))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(sideBar, javax.swing.GroupLayout.DEFAULT_SIZE, 606, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(top, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(mainPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 565, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 8, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnPosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPosActionPerformed
       CardLayout cl = (CardLayout)(mainPanel).getLayout();
       cl.show(mainPanel, "POS");
       
    }//GEN-LAST:event_btnPosActionPerformed

    private void btnSettingsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSettingsActionPerformed
        CardLayout cl = (CardLayout)(mainPanel).getLayout();
        cl.show(mainPanel, "setting");
    }//GEN-LAST:event_btnSettingsActionPerformed

    private void btnLogoutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLogoutActionPerformed
        int response = JOptionPane.showConfirmDialog(
            this, 
            "Are you sure you want to log out?", 
            "Logout Confirmation", 
            JOptionPane.YES_NO_OPTION,
            JOptionPane.QUESTION_MESSAGE
        );
 
        if (response == JOptionPane.YES_OPTION) {
            
            this.dispose();

            login logIn = new login(); 
            logIn.setVisible(true);
        }
    }//GEN-LAST:event_btnLogoutActionPerformed

    private void txtCurrentPasswordActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCurrentPasswordActionPerformed
        
    }//GEN-LAST:event_txtCurrentPasswordActionPerformed

    private void btnSubmitRequestActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSubmitRequestActionPerformed
        String currentPass = new String(txtCurrentPassword.getPassword());
        String newPass = new String(txtNewPassword.getPassword());
        String confirmPass = new String(txtConfirmPassword.getPassword());

        if(currentPass.isEmpty() || newPass.isEmpty() || confirmPass.isEmpty()) {
            JOptionPane.showMessageDialog(this, "All fields are required!");
            return;
        }

        // Compare current password by hashing
        UserController controller = new UserController();
        boolean isCurrentValid = controller.verifyPassword(loggedUser.getUserName(), currentPass);

        if(!isCurrentValid) {
            JOptionPane.showMessageDialog(this, "Current password is incorrect!");
            return;
        }

        if(!newPass.equals(confirmPass)) {
            JOptionPane.showMessageDialog(this, "New passwords do not match!");
            return;
        }

        controller.requestPasswordChange(loggedUser.getUserId(), newPass);

        JOptionPane.showMessageDialog(this, "Password change request submitted for Admin approval.");

        txtCurrentPassword.setText("");
        txtNewPassword.setText("");
        txtConfirmPassword.setText("");
    }//GEN-LAST:event_btnSubmitRequestActionPerformed

    private void btnCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelActionPerformed
        
        // TODO add your handling code here:
            int confirm = javax.swing.JOptionPane.showConfirmDialog(
                this,
                "Are you sure you want to cancel this password change request?",
                "Confirm Cancel",
                javax.swing.JOptionPane.YES_NO_OPTION,
                javax.swing.JOptionPane.WARNING_MESSAGE
        );

        if (confirm == javax.swing.JOptionPane.YES_OPTION) {

            javax.swing.JOptionPane.showMessageDialog(
                    this,
                    "Request has been cancelled successfully.",
                    "Cancelled",
                    javax.swing.JOptionPane.INFORMATION_MESSAGE
            );

        }
    }//GEN-LAST:event_btnCancelActionPerformed

    public static void main(String args[]) {
        new login().setVisible(true);
        
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCancel;
    private javax.swing.JButton btnLogout;
    private javax.swing.JButton btnPos;
    private javax.swing.JButton btnSettings;
    private javax.swing.JButton btnSubmitRequest;
    private javax.swing.JPanel changePasswordPanel;
    private javax.swing.JLabel fullName;
    private javax.swing.JLabel fullName1;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JPanel mainPanel;
    private javax.swing.JButton notif;
    private javax.swing.JPanel posPanel;
    private javax.swing.JLabel role;
    private javax.swing.JPanel settingsPanel;
    private javax.swing.JPanel sideBar;
    private javax.swing.JPanel staffInformationPanel;
    private javax.swing.JPanel top;
    private javax.swing.JPasswordField txtConfirmPassword;
    private javax.swing.JPasswordField txtCurrentPassword;
    private javax.swing.JPasswordField txtNewPassword;
    // End of variables declaration//GEN-END:variables
}
