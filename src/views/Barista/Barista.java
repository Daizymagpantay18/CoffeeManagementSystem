/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package views.Barista;

import controllers.OrderController;
import controllers.UserController;
import data_base.data_Base;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import models.user;
import util.PlaceholderUtil;
import util.RoundedButton;
import util.RoundedField;
import util.ButtonStyler;
import util.PanelUtils;
import util.SidebarUtils;
import util.StaffInfoPanel;
import views.login;
import javax.swing.*;
import java.awt.*;
import java.util.List;
import models.Order;
import models.OrderItem;
import util.UIStyles;
import static views.Cashier.C.setLabelImage;

public class Barista extends javax.swing.JFrame {
    private StaffInfoPanel staffInfoPanel;
    private user loggedUser;
    private JPanel pendingContainer;
    private JPanel preparingContainer;
    private JPanel servedContainer;
    public Barista(models.user loggedUser) {
        initComponents();
        
        pendingContainer = new JPanel();
        pendingContainer.setLayout(new BoxLayout(pendingContainer, BoxLayout.Y_AXIS));

        preparingContainer = new JPanel();
        preparingContainer.setLayout(new BoxLayout(preparingContainer, BoxLayout.Y_AXIS));

        servedContainer = new JPanel();
        servedContainer.setLayout(new BoxLayout(servedContainer, BoxLayout.Y_AXIS));

        pendingPanel.setViewportView(pendingContainer);
        preparingPanel.setViewportView(preparingContainer);
        servedPanel.setViewportView(servedContainer);
        
        loadOrders();
        
        fullName.setText(loggedUser.getFullName()+ "      • BARISTA");
        
        this.loggedUser = loggedUser;
        
        PanelUtils.makeRounded(changePasswordPanel, 30);
        PanelUtils.makeRounded(staffInformationPanel, 30);
        PanelUtils.makeRounded(pending_panel, 30);
        PanelUtils.makeRounded(preparing_panel, 30);
        PanelUtils.makeRounded(served_panel, 30);
        
        setLabelImage(pending_icon, "/image/pending.png");
        setLabelImage(preparing_icon, "/image/preparing.png");
        setLabelImage(served_icon, "/image/served.png");
        
        staffInfoPanel = new StaffInfoPanel();
        staffInformationPanel.setLayout(new BorderLayout());
        staffInformationPanel.add(staffInfoPanel, BorderLayout.CENTER);
        staffInformationPanel.setPreferredSize(new Dimension(932, 180));
        
        changePasswordPanel.setPreferredSize(new Dimension(932, 343));
        
        staffInfoPanel.setStaffInfo(
            loggedUser.getFullName(),
            loggedUser.getUserName(),
            loggedUser.getRole(),
            loggedUser.getStatus()
        );
        
        SidebarUtils.setupSidebarButton(btnOrders);
        SidebarUtils.setupSidebarButton(btnSettings);
        

        SidebarUtils.setIcon(btnOrders, "/image/order.png", Barista.class,new Color(244, 236, 223));
        SidebarUtils.setIcon(btnSettings, "/image/setting.png", Barista.class, new Color(244, 236, 223));
        SidebarUtils.setIcon(btnLogout, "/image/logout.png", Barista.class, new Color(244, 236, 223)); 
        
        btnOrders.doClick();
        
        
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
        
        
        this.setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);

        this.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {

                JOptionPane.showMessageDialog(
                    null,
                    "Please logout first before closing the system."
                );

            }
        });

    }
    public static void setLabelImage(JLabel label, String path) {

        ImageIcon icon = new ImageIcon(UIStyles.class.getResource(path));

        Image img = icon.getImage().getScaledInstance(
                label.getWidth(),
                label.getHeight(),
                Image.SCALE_SMOOTH
        );

        label.setIcon(new ImageIcon(img));
        label.setHorizontalAlignment(JLabel.CENTER);
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
    private JPanel createOrderCard(Order order) {
        JPanel panel = new JPanel();
        panel.setBackground(Color.decode("#FEFCFB")); 
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Color.decode("#E5DED8")),
            BorderFactory.createEmptyBorder(10,10,10,10)
        ));
        panel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 150));

        // Order number
        JLabel orderNumber = new JLabel( order.getOrderNumber());
        orderNumber.setFont(new Font("Arial", Font.BOLD, 16));
        orderNumber.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.add(orderNumber);

        panel.add(Box.createVerticalStrut(5));

        // Order items
        OrderController controller = new OrderController();
        List<OrderItem> items = controller.getOrderItems(order.getOrderNumber());

        JPanel itemsPanel = new JPanel();
        itemsPanel.setLayout(new BoxLayout(itemsPanel, BoxLayout.Y_AXIS));
        itemsPanel.setOpaque(false);
        itemsPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        for(OrderItem item : items){
            String line = item.getQuantity() + "x " + item.getProductName() + " (" + item.getSizeName() + ")";
            JLabel itemLabel = new JLabel(line);
            itemLabel.setFont(new Font("Arial", Font.PLAIN, 14));
            itemLabel.setForeground(Color.BLACK);
            itemsPanel.add(itemLabel);
        }

        panel.add(itemsPanel);
        panel.add(Box.createVerticalStrut(5));

        // Total
        JLabel totalLabel = new JLabel("₱" + order.getTotal());
        totalLabel.setFont(new Font("Arial", Font.BOLD, 14));
        totalLabel.setForeground(new Color(85, 53, 35));
        totalLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.add(totalLabel);

        if(!order.getStatus().equals("Served")) {
            panel.setCursor(new Cursor(Cursor.HAND_CURSOR));
            panel.addMouseListener(new java.awt.event.MouseAdapter() {
                @Override
                public void mouseClicked(java.awt.event.MouseEvent e) {
                    showOrderPopup(order);
                }
            });
        } else {
            // Optional: ibang cursor para ipakita na hindi clickable
            panel.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        }

        return panel;
    }
    private void showOrderPopup(Order order){

        OrderController controller = new OrderController();
        List<OrderItem> items = controller.getOrderItems(order.getOrderNumber());

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(new Color(245,234,219));
        panel.setBorder(BorderFactory.createEmptyBorder(15,15,15,15));

        JLabel orderLabel = new JLabel("Order #" + order.getOrderNumber());
        orderLabel.setFont(new Font("Arial", Font.BOLD, 18));
        orderLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        panel.add(orderLabel);
        panel.add(Box.createVerticalStrut(10));

        for(OrderItem item : items){

            JPanel itemRow = new JPanel(new BorderLayout());
            itemRow.setOpaque(false);
            itemRow.setAlignmentX(Component.LEFT_ALIGNMENT);

            JLabel name = new JLabel(
                item.getQuantity() + "x " + item.getProductName() + 
                " (" + item.getSizeName() + ")"
            );

            name.setFont(new Font("Arial", Font.PLAIN, 14));
            name.setAlignmentX(Component.LEFT_ALIGNMENT);

            itemRow.add(name, BorderLayout.WEST);

            panel.add(itemRow);
            panel.add(Box.createVerticalStrut(5));
        }

        panel.add(Box.createVerticalStrut(10));

        JLabel total = new JLabel("Total: ₱" + order.getTotal());
        total.setFont(new Font("Arial", Font.BOLD, 15));
        total.setForeground(new Color(85,53,35));
        total.setAlignmentX(Component.LEFT_ALIGNMENT);

        panel.add(total);

        int option = JOptionPane.showConfirmDialog(
                this,
                panel,
                order.getStatus().equals("Pending") ? "Start Preparing?" : "Mark as Served?",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.PLAIN_MESSAGE
        );

        if(option == JOptionPane.YES_OPTION){

            if(order.getStatus().equals("Pending")){
                controller.updateOrderStatus(order.getOrderNumber(),"Preparing");
            }
            else if(order.getStatus().equals("Preparing")){
                controller.updateOrderStatus(order.getOrderNumber(),"Served");
            }

            loadOrders();
        }
    }
    private void loadOrders(){

        pendingContainer.removeAll();
        preparingContainer.removeAll();
        servedContainer.removeAll();

        OrderController controller = new OrderController();

        List<Order> pendingOrders = controller.getOrdersByStatus("Pending");
        List<Order> preparingOrders = controller.getOrdersByStatus("Preparing");
        List<Order> servedOrders = controller.getOrdersByStatus("Served");
        
        pendingtotal.setText(String.valueOf(pendingOrders.size()));
        preparingtotal.setText(String.valueOf(preparingOrders.size()));
        servedtotal.setText(String.valueOf(servedOrders.size()));

        for(Order order : pendingOrders){
            pendingContainer.add(createOrderCard(order));
        }

        for(Order order : preparingOrders){
            preparingContainer.add(createOrderCard(order));
        }

        for(Order order : servedOrders){
            servedContainer.add(createOrderCard(order));
        }

        pendingContainer.revalidate();
        preparingContainer.revalidate();
        servedContainer.revalidate();

        pendingContainer.repaint();
        preparingContainer.repaint();
        servedContainer.repaint();
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        mainPanel = new javax.swing.JPanel();
        ordersPanel = new javax.swing.JPanel();
        pendingPanel = new javax.swing.JScrollPane();
        servedPanel = new javax.swing.JScrollPane();
        preparingPanel = new javax.swing.JScrollPane();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        pending_panel = new javax.swing.JPanel();
        pending_icon = new javax.swing.JLabel();
        pendingtotal = new javax.swing.JLabel();
        pendingtotal1 = new javax.swing.JLabel();
        served_panel = new javax.swing.JPanel();
        served_icon = new javax.swing.JLabel();
        servedtotal = new javax.swing.JLabel();
        pendingtotal3 = new javax.swing.JLabel();
        preparing_panel = new javax.swing.JPanel();
        preparing_icon = new javax.swing.JLabel();
        preparingtotal = new javax.swing.JLabel();
        pendingtotal2 = new javax.swing.JLabel();
        settingsPanel = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        staffInformationPanel = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        changePasswordPanel = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        txtCurrentPassword = new javax.swing.JPasswordField();
        jLabel7 = new javax.swing.JLabel();
        txtNewPassword = new javax.swing.JPasswordField();
        jLabel8 = new javax.swing.JLabel();
        txtConfirmPassword = new javax.swing.JPasswordField();
        btnSubmitRequest = new javax.swing.JButton();
        sideBar = new javax.swing.JPanel();
        btnLogout = new javax.swing.JButton();
        btnOrders = new javax.swing.JButton();
        btnSettings = new javax.swing.JButton();
        fullName = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Coffee Magement System");
        setBackground(new java.awt.Color(245, 234, 219));

        jPanel1.setBackground(new java.awt.Color(245, 234, 219));

        mainPanel.setBackground(new java.awt.Color(245, 234, 219));
        mainPanel.setPreferredSize(new java.awt.Dimension(774, 700));
        mainPanel.setLayout(new java.awt.CardLayout());

        ordersPanel.setBackground(new java.awt.Color(245, 234, 219));

        pendingPanel.setBackground(new java.awt.Color(254, 252, 251));
        pendingPanel.setPreferredSize(new java.awt.Dimension(315, 2));

        servedPanel.setBackground(new java.awt.Color(245, 234, 219));
        servedPanel.setForeground(new java.awt.Color(245, 234, 219));

        preparingPanel.setBackground(new java.awt.Color(245, 234, 219));
        preparingPanel.setPreferredSize(new java.awt.Dimension(315, 2));

        jLabel1.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(107, 75, 58));
        jLabel1.setText("Pending");

        jLabel2.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(107, 75, 58));
        jLabel2.setText("Preparing");

        jLabel11.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(107, 75, 58));
        jLabel11.setText("Served");

        pending_panel.setBackground(new java.awt.Color(254, 252, 251));
        pending_panel.setPreferredSize(new java.awt.Dimension(315, 80));

        pendingtotal.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        pendingtotal.setText("0");

        pendingtotal1.setFont(new java.awt.Font("Times New Roman", 2, 20)); // NOI18N
        pendingtotal1.setForeground(new java.awt.Color(146, 109, 79));
        pendingtotal1.setText("Pending Orders");

        javax.swing.GroupLayout pending_panelLayout = new javax.swing.GroupLayout(pending_panel);
        pending_panel.setLayout(pending_panelLayout);
        pending_panelLayout.setHorizontalGroup(
            pending_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pending_panelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(pending_icon, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(36, 36, 36)
                .addComponent(pendingtotal, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pendingtotal1, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pending_panelLayout.setVerticalGroup(
            pending_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pending_panelLayout.createSequentialGroup()
                .addGroup(pending_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pending_panelLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(pending_icon, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pending_panelLayout.createSequentialGroup()
                        .addGap(22, 22, 22)
                        .addGroup(pending_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(pendingtotal)
                            .addComponent(pendingtotal1))))
                .addContainerGap(29, Short.MAX_VALUE))
        );

        served_panel.setBackground(new java.awt.Color(254, 252, 251));
        served_panel.setPreferredSize(new java.awt.Dimension(315, 80));

        servedtotal.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        servedtotal.setText("0");

        pendingtotal3.setFont(new java.awt.Font("Times New Roman", 2, 20)); // NOI18N
        pendingtotal3.setForeground(new java.awt.Color(146, 109, 79));
        pendingtotal3.setText("Served Orders");

        javax.swing.GroupLayout served_panelLayout = new javax.swing.GroupLayout(served_panel);
        served_panel.setLayout(served_panelLayout);
        served_panelLayout.setHorizontalGroup(
            served_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(served_panelLayout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(served_icon, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(31, 31, 31)
                .addComponent(servedtotal, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(pendingtotal3, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(51, Short.MAX_VALUE))
        );
        served_panelLayout.setVerticalGroup(
            served_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(served_panelLayout.createSequentialGroup()
                .addGroup(served_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(served_panelLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(served_icon, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(served_panelLayout.createSequentialGroup()
                        .addGap(19, 19, 19)
                        .addGroup(served_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(servedtotal)
                            .addComponent(pendingtotal3))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        preparing_panel.setBackground(new java.awt.Color(254, 252, 251));
        preparing_panel.setPreferredSize(new java.awt.Dimension(315, 80));

        preparingtotal.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        preparingtotal.setText("0");

        pendingtotal2.setFont(new java.awt.Font("Times New Roman", 2, 20)); // NOI18N
        pendingtotal2.setForeground(new java.awt.Color(146, 109, 79));
        pendingtotal2.setText("Preparing Orders");

        javax.swing.GroupLayout preparing_panelLayout = new javax.swing.GroupLayout(preparing_panel);
        preparing_panel.setLayout(preparing_panelLayout);
        preparing_panelLayout.setHorizontalGroup(
            preparing_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(preparing_panelLayout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(preparing_icon, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(24, 24, 24)
                .addComponent(preparingtotal, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(pendingtotal2, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        preparing_panelLayout.setVerticalGroup(
            preparing_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(preparing_panelLayout.createSequentialGroup()
                .addGroup(preparing_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(preparing_panelLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(preparing_icon, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(preparing_panelLayout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(preparing_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(preparingtotal)
                            .addComponent(pendingtotal2))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout ordersPanelLayout = new javax.swing.GroupLayout(ordersPanel);
        ordersPanel.setLayout(ordersPanelLayout);
        ordersPanelLayout.setHorizontalGroup(
            ordersPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ordersPanelLayout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addGroup(ordersPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(pendingPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(pending_panel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(ordersPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(preparingPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(preparing_panel, javax.swing.GroupLayout.DEFAULT_SIZE, 307, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(ordersPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(served_panel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(servedPanel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 315, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
            .addGroup(ordersPanelLayout.createSequentialGroup()
                .addGap(38, 38, 38)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(228, 228, 228)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(216, 216, 216)
                .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(192, Short.MAX_VALUE))
        );
        ordersPanelLayout.setVerticalGroup(
            ordersPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, ordersPanelLayout.createSequentialGroup()
                .addGap(0, 30, Short.MAX_VALUE)
                .addGroup(ordersPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(served_panel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(preparing_panel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pending_panel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(ordersPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel11, javax.swing.GroupLayout.Alignment.TRAILING))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(ordersPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(servedPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 432, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(ordersPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(preparingPanel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 434, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(pendingPanel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 434, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(13, 13, 13))
        );

        mainPanel.add(ordersPanel, "order");

        settingsPanel.setBackground(new java.awt.Color(245, 234, 219));

        jLabel3.setFont(new java.awt.Font("Segoe UI", 3, 24)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(85, 53, 35));
        jLabel3.setText("Settings");

        staffInformationPanel.setBackground(new java.awt.Color(254, 252, 251));

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
                .addContainerGap(127, Short.MAX_VALUE))
        );

        changePasswordPanel.setBackground(new java.awt.Color(254, 252, 251));

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(85, 53, 35));
        jLabel4.setText("Change Password");

        jLabel5.setFont(new java.awt.Font("Arial", 0, 10)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(125, 102, 88));
        jLabel5.setText("Password changes required Admin approval. Your request will be viewed before the update takes effect.");

        jLabel6.setText("Current Password");

        txtCurrentPassword.setText("Enter Current Password");
        txtCurrentPassword.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCurrentPasswordActionPerformed(evt);
            }
        });

        jLabel7.setText("New Password");

        txtNewPassword.setText("jPasswordField1");

        jLabel8.setText("Confirm New Password");

        txtConfirmPassword.setText("jPasswordField1");

        btnSubmitRequest.setText("Submit Request");
        btnSubmitRequest.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSubmitRequestActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout changePasswordPanelLayout = new javax.swing.GroupLayout(changePasswordPanel);
        changePasswordPanel.setLayout(changePasswordPanelLayout);
        changePasswordPanelLayout.setHorizontalGroup(
            changePasswordPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(changePasswordPanelLayout.createSequentialGroup()
                .addGap(66, 66, 66)
                .addGroup(changePasswordPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(changePasswordPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 353, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 629, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel6)
                        .addComponent(jLabel8)
                        .addComponent(jLabel7)
                        .addComponent(txtCurrentPassword, javax.swing.GroupLayout.DEFAULT_SIZE, 783, Short.MAX_VALUE)
                        .addComponent(txtNewPassword)
                        .addComponent(txtConfirmPassword, javax.swing.GroupLayout.DEFAULT_SIZE, 783, Short.MAX_VALUE))
                    .addComponent(btnSubmitRequest, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(83, Short.MAX_VALUE))
        );
        changePasswordPanelLayout.setVerticalGroup(
            changePasswordPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(changePasswordPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel5)
                .addGap(18, 18, 18)
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtCurrentPassword, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtNewPassword, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtConfirmPassword, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnSubmitRequest, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(34, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout settingsPanelLayout = new javax.swing.GroupLayout(settingsPanel);
        settingsPanel.setLayout(settingsPanelLayout);
        settingsPanelLayout.setHorizontalGroup(
            settingsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(settingsPanelLayout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(settingsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 353, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(changePasswordPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(staffInformationPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        settingsPanelLayout.setVerticalGroup(
            settingsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(settingsPanelLayout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(staffInformationPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(changePasswordPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 29, Short.MAX_VALUE))
        );

        mainPanel.add(settingsPanel, "setting");

        sideBar.setBackground(new java.awt.Color(146, 109, 79));
        sideBar.setPreferredSize(new java.awt.Dimension(223, 700));

        btnLogout.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        btnLogout.setForeground(new java.awt.Color(244, 236, 223));
        btnLogout.setBorderPainted(false);
        btnLogout.setContentAreaFilled(false);
        btnLogout.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLogoutActionPerformed(evt);
            }
        });

        btnOrders.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        btnOrders.setForeground(new java.awt.Color(244, 236, 223));
        btnOrders.setBorderPainted(false);
        btnOrders.setContentAreaFilled(false);
        btnOrders.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOrdersActionPerformed(evt);
            }
        });

        btnSettings.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        btnSettings.setForeground(new java.awt.Color(244, 236, 223));
        btnSettings.setBorderPainted(false);
        btnSettings.setContentAreaFilled(false);
        btnSettings.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSettingsActionPerformed(evt);
            }
        });

        fullName.setFont(new java.awt.Font("Segoe UI Black", 0, 15)); // NOI18N
        fullName.setForeground(new java.awt.Color(244, 236, 223));
        fullName.setText("jLabel4");

        javax.swing.GroupLayout sideBarLayout = new javax.swing.GroupLayout(sideBar);
        sideBar.setLayout(sideBarLayout);
        sideBarLayout.setHorizontalGroup(
            sideBarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, sideBarLayout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(fullName, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(618, 618, 618)
                .addComponent(btnOrders, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnSettings, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnLogout, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18))
        );
        sideBarLayout.setVerticalGroup(
            sideBarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btnSettings, javax.swing.GroupLayout.DEFAULT_SIZE, 51, Short.MAX_VALUE)
            .addComponent(btnOrders, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(btnLogout, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, sideBarLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(fullName)
                .addGap(14, 14, 14))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(sideBar, javax.swing.GroupLayout.DEFAULT_SIZE, 1007, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(mainPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 1007, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addComponent(sideBar, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(mainPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 604, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
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

    private void btnOrdersActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOrdersActionPerformed
        CardLayout cl = (CardLayout)(mainPanel).getLayout();
        cl.show(mainPanel, "order");
    }//GEN-LAST:event_btnOrdersActionPerformed

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
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCurrentPasswordActionPerformed

    private void btnSubmitRequestActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSubmitRequestActionPerformed
        String currentPlaceholder = "Enter Current Password";
        String newPlaceholder = "Enter New Password";
        String confirmPlaceholder = "Confirm New Password";

        char[] currentPassChars = txtCurrentPassword.getPassword();
        char[] newPassChars = txtNewPassword.getPassword();
        char[] confirmPassChars = txtConfirmPassword.getPassword();

        String currentPass = new String(currentPassChars).trim();
        String newPass = new String(newPassChars).trim();
        String confirmPass = new String(confirmPassChars).trim();

        // Check empty or placeholder
        if(currentPass.isEmpty() || currentPass.equals(currentPlaceholder) ||
           newPass.isEmpty() || newPass.equals(newPlaceholder) ||
           confirmPass.isEmpty() || confirmPass.equals(confirmPlaceholder)) {
            JOptionPane.showMessageDialog(this, "All fields are required!");
            return;
        }
        int minLength = 6;
        if(newPass.length() < minLength || confirmPass.length() < minLength) {
            JOptionPane.showMessageDialog(this, "Password must be at least " + minLength + " characters!");
            return;
        }

        // Verify current password
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

        // Confirmation before sending to admin
        int confirm = JOptionPane.showConfirmDialog(
            this,
            "Are you sure you want to submit this password change request to Admin?",
            "Confirm Submission",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.QUESTION_MESSAGE
        );

        if(confirm != JOptionPane.YES_OPTION) {
            return; 
        }

        controller.requestPasswordChange(loggedUser.getUserId(), newPass);

        JOptionPane.showMessageDialog(this, "Password change request submitted for Admin approval.");

        // Clear fields
        txtCurrentPassword.setText("");
        txtNewPassword.setText("");
        txtConfirmPassword.setText("");
    }//GEN-LAST:event_btnSubmitRequestActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        new login().setVisible(true);
        
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnLogout;
    private javax.swing.JButton btnOrders;
    private javax.swing.JButton btnSettings;
    private javax.swing.JButton btnSubmitRequest;
    private javax.swing.JPanel changePasswordPanel;
    private javax.swing.JLabel fullName;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel mainPanel;
    private javax.swing.JPanel ordersPanel;
    private javax.swing.JScrollPane pendingPanel;
    private javax.swing.JLabel pending_icon;
    private javax.swing.JPanel pending_panel;
    private javax.swing.JLabel pendingtotal;
    private javax.swing.JLabel pendingtotal1;
    private javax.swing.JLabel pendingtotal2;
    private javax.swing.JLabel pendingtotal3;
    private javax.swing.JScrollPane preparingPanel;
    private javax.swing.JLabel preparing_icon;
    private javax.swing.JPanel preparing_panel;
    private javax.swing.JLabel preparingtotal;
    private javax.swing.JScrollPane servedPanel;
    private javax.swing.JLabel served_icon;
    private javax.swing.JPanel served_panel;
    private javax.swing.JLabel servedtotal;
    private javax.swing.JPanel settingsPanel;
    private javax.swing.JPanel sideBar;
    private javax.swing.JPanel staffInformationPanel;
    private javax.swing.JPasswordField txtConfirmPassword;
    private javax.swing.JPasswordField txtCurrentPassword;
    private javax.swing.JPasswordField txtNewPassword;
    // End of variables declaration//GEN-END:variables
}
