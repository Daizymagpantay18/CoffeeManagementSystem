/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package views.Cashier;

import controllers.OrderController;
import controllers.ProductController;
import controllers.UserController;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.RenderingHints;
import java.util.ArrayList;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import models.user;
import util.PlaceholderUtil;
import util.RoundedButton;
import util.RoundedField;
import util.ButtonStyler;
import util.PanelUtils;
import util.SidebarUtils;
import util.StaffInfoPanel;
import views.login;
import models.Cart;
import models.CartItem;
import models.Order;
import models.OrderItem;
import models.Product;
import models.ProductSize;
import util.CartItemPanel;
import util.RoundedComboBox;
import util.RoundedLabel;
import util.ScrollPanelUtils;
import util.UIStyles;

public class C extends javax.swing.JFrame {
    private StaffInfoPanel staffInfoPanel;
    private user loggedUser;
    private Cart cartModel = new Cart();
    private JPanel menuContainer;
    private JPanel cartPanel;
    public C(models.user loggedUser) {
        initComponents();
        
        javax.swing.GroupLayout layout = (javax.swing.GroupLayout) panelss.getLayout();
        
        RoundedField username = new RoundedField(15, false);

        // replace old components
        layout.replace(numberCashReceived, username);

        // update references
        numberCashReceived = username;
        // Placeholder setup
        setupPlaceholder(numberCashReceived, "Enter Cash Received");

        // revalidate panel
        panelss.revalidate();
        panelss.repaint();
        
        RoundedLabel roundedItems = new RoundedLabel("0 items");
        layout.replace(items, roundedItems);
        items = roundedItems;
        
        cartPanel = new JPanel();
        cartPanel.setLayout(new javax.swing.BoxLayout(cartPanel, javax.swing.BoxLayout.Y_AXIS));
        cartPanel.setBackground(Color.decode("#fefcfb"));
        cart.setBorder(null); 
        cart.setViewportBorder(null); 
        cart.setBackground(Color.decode("#fefcfb")); 
        cart.setViewportView(cartPanel);

        menuContainer = new JPanel();
        menuContainer.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10));
        menuContainer.setBackground(new Color(245,234,219));

        menuPanel.setViewportView(menuContainer);
        
        fullName.setText(loggedUser.getFullName() + "     • CASHIER");
        
        this.loggedUser = loggedUser;
        PanelUtils.makeRounded(changePasswordPanel, 30);
        PanelUtils.makeRounded(staffInformationPanel, 30);
        PanelUtils.makeRounded(panelss, 30);
        
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
        
        SidebarUtils.setupSidebarButton(btnPos);
        SidebarUtils.setupSidebarButton(btnSettings);
        SidebarUtils.setupSidebarButton(btnConfirmPayment);

        SidebarUtils.setIcon(btnPos, "/image/cart.png", C.class, new Color(244, 236, 223));
        SidebarUtils.setIcon(btnSettings, "/image/setting.png", C.class, new Color(244, 236, 223));
        SidebarUtils.setIcon(btnLogout, "/image/logout.png", C.class, new Color(244, 236, 223)); 
        
        btnPos.doClick();
        
        
        // get the layout
        javax.swing.GroupLayout layoutt = (javax.swing.GroupLayout) changePasswordPanel.getLayout();

        // create new RoundedFields
        RoundedField current = new RoundedField(15, true);
        RoundedField newPass = new RoundedField(15, true);
        RoundedField confirm = new RoundedField(15, true);

        // replace old components
        layoutt.replace(txtCurrentPassword, current);
        layoutt.replace(txtNewPassword, newPass);
        layoutt.replace(txtConfirmPassword, confirm);

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
        
        RoundedComboBox<String> roundedCategory = new RoundedComboBox<>();
        javax.swing.GroupLayout layouts = (javax.swing.GroupLayout) posPanel.getLayout();
        layouts.replace(selectCategory, roundedCategory);
        selectCategory = roundedCategory;
        selectCategory.setPreferredSize(new Dimension(180, 35));
        selectCategory.setBackground(new Color(244,236,223));
        selectCategory.setForeground(new Color(85,53,35));
        
        loadCategories(); 
        loadMenuProductsByCategory((String) selectCategory.getSelectedItem());
        refreshCart();
        selectCategory.addActionListener(evt -> {
            String category = (String) selectCategory.getSelectedItem();
            loadMenuProductsByCategory(category);
        });
        RoundedButton SubmitRequest = new RoundedButton("Submit Request", null);
        ButtonStyler.applyCoffeeStyle(SubmitRequest);
        SubmitRequest.addActionListener(evt -> btnSubmitRequestActionPerformed(evt));
        
        layoutt.replace(btnSubmitRequest, SubmitRequest);
        btnSubmitRequest = SubmitRequest;
        
        RoundedButton ConfirmCash = new RoundedButton("Confirm Payment", null);
        ButtonStyler.applyCoffeeStyle(ConfirmCash);
        ConfirmCash.addActionListener(evt -> btnConfirmPaymentActionPerformed(evt));
        
        layout.replace(btnConfirmPayment, ConfirmCash);
        btnConfirmPayment = ConfirmCash;
        
        setLabelImage(icon_cart, "/image/cart.png");
        
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
        
        roundedPanel();
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
    private void roundedPanel(){
        menuPanel.setBackground(Color.decode("#fefcfb"));
        menuPanel.getViewport().setBackground(Color.decode("#fefcfb"));
        menuPanel.setOpaque(true);
        
        menuPanel.setPreferredSize(new Dimension(570, 560));
        menuPanel.setMinimumSize(new Dimension(570, 560));
        menuPanel.setMaximumSize(new Dimension(570, 560));

        panelss.setPreferredSize(new Dimension(380, 560));
        panelss.setMinimumSize(new Dimension(380, 560));
        panelss.setMaximumSize(new Dimension(380, 560));

        ScrollPanelUtils.makeRounded(menuPanel, 25);
        PanelUtils.makeRounded(panelss, 25);

        menuContainer.setLayout(new GridLayout(0, 4, 10, 15));
        menuContainer.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        
        menuPanel.getVerticalScrollBar().setUnitIncrement(16);
    } 
    private void loadCategories(){
        ProductController controller = new ProductController();

        selectCategory.removeAllItems(); 

        for(String category : controller.getCategories()){
            selectCategory.addItem(category);
        }
        if(selectCategory.getItemCount() > 0){
            loadMenuProductsByCategory((String) selectCategory.getSelectedItem());
        }
    }
    private void loadMenuProductsByCategory(String category) {
        ProductController pc = new ProductController();
        List<Product> allProducts = pc.getAllProducts();

        menuContainer.removeAll();
        for (Product p : allProducts) {
            if (p.getCategory().equalsIgnoreCase(category)) {
                menuContainer.add(createProductCard(p));
            }
        }

        menuContainer.revalidate();
        menuContainer.repaint();
    }
    private JPanel createProductCard(Product product) {
        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                // Anti-alias for smooth corners
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                // Fill rounded rectangle with your color
                g2.setColor(Color.decode("#fefcfb"));
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);
            }

            @Override
            protected void paintBorder(Graphics g) {
                // Optional: Draw border
                Graphics2D g2 = (Graphics2D) g;
                g2.setColor(Color.GRAY);
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 14, 14);
            }
        };

        panel.setLayout(new BorderLayout());
        panel.setPreferredSize(new Dimension(100, 80));
        panel.setCursor(new Cursor(Cursor.HAND_CURSOR));

        JLabel name = new JLabel(product.getName(), SwingConstants.CENTER);
        name.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 14));
        panel.add(name, BorderLayout.CENTER);

        if(product.getStatus().equalsIgnoreCase("Available")){

            panel.addMouseListener(new java.awt.event.MouseAdapter() {
                @Override
                public void mouseClicked(java.awt.event.MouseEvent e) {
                    showSizeSelection(product);
                }
            });

        }else{

            panel.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            panel.setBackground(new Color(255,220,220)); // light red

            JLabel out = new JLabel("OUT OF STOCK", SwingConstants.CENTER);
            out.setForeground(Color.RED);
            out.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 10));

            panel.add(out, BorderLayout.SOUTH);
        }

        return panel;
    }
    private void showSizeSelection(Product product){

        ProductController pc = new ProductController();

        List<ProductSize> sizes = pc.getProductSizes(product.getProductId());

        String[] options = new String[sizes.size()];

        for(int i=0;i<sizes.size();i++){

            ProductSize s = sizes.get(i);

            options[i] = s.getSizeName() + " - ₱" + s.getPrice();
        }

        int choice = JOptionPane.showOptionDialog(
                this,
                "Select size for " + product.getName(),
                "Choose Size",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.INFORMATION_MESSAGE,
                null,
                options,
                options[0]
        );

        if(choice >= 0){

            ProductSize selected = sizes.get(choice);

            addToCart(product, selected);
        }
    }    
    private void addToCart(Product product, ProductSize size){

        cartModel.addItem(
            product,
            size.getSizeId(),
            size.getSizeName(),
            size.getPrice()
        );
        
        refreshCart();
        
    }   
    private void refreshCart() {
        cartPanel.removeAll();
        double total = 0;
        int totalItems = 0;

        for (CartItem item : cartModel.getItems()) {

        CartItemPanel itemPanel = new CartItemPanel(
            item.getProduct().getName(),
            item.getSizeName(),
            item.getPrice() * item.getQuantity(),
            () -> { cartModel.removeItem(item); refreshCart(); },
            () -> { item.decreaseQty(); if(item.getQuantity() == 0) cartModel.removeItem(item); refreshCart(); },
            () -> { item.increaseQty(); refreshCart(); },
            item.getQuantity()
        );

        itemPanel.setAlignmentX(JPanel.LEFT_ALIGNMENT);
        total += item.getSubtotal();
        totalItems += item.getQuantity();

        cartPanel.add(itemPanel);
        }
        double discount = 0;

        if (ifDiscounted.isSelected()) {
            discount = total * 0.20;
        }
        items.setText(totalItems + " item" + (totalItems != 1 ? "s" : ""));
        double finalTotal = total - discount;

        amountDiscount.setText("₱ " + String.format("%.2f", discount));

        totalAmountOfOrder.setText("₱ " + String.format("%.2f", finalTotal));
        cartPanel.revalidate();
        cartPanel.repaint();
    }
    private void showOrderReceiptPopup(Order order, List<CartItem> orderItems, double cash, double change) {
        // Create frame
        JFrame receiptFrame = new JFrame("Order Receipt");
        receiptFrame.setSize(350, 500);
        receiptFrame.setLocationRelativeTo(null);

        // Get logo path
        String logoPath = getClass().getResource("/image/ja.png").toString();

        // Build receipt HTML
        StringBuilder receiptHtml = new StringBuilder();
        receiptHtml.append("<html>");

        // Centered part: logo, name, order number, date
        receiptHtml.append("<div style='text-align:center; font-family: monospace;'>");
        receiptHtml.append("<img src='").append(logoPath).append("' width='60' height='60'><br>");
        receiptHtml.append("JjaiZy Brew Track<br>");
        receiptHtml.append("Coffee Shop System<br>");
        receiptHtml.append("===============================<br>");
        receiptHtml.append("Order No: ").append(order.getOrderNumber()).append("<br>");
        receiptHtml.append("Date: ").append(java.time.LocalDateTime.now()
                .format(java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"))).append("<br>");
        receiptHtml.append("===============================<br>");
        receiptHtml.append("</div>");

        // Left-aligned part: cart items, subtotal, totals
        receiptHtml.append("<div style='text-align:left; font-family: monospace;'>");
        receiptHtml.append("-------------------------------<br>");
        for (CartItem item : orderItems) {
            receiptHtml.append(item.getProduct().getName())
                    .append(" (").append(item.getSizeName()).append(") x").append(item.getQuantity())
                    .append("<br>");
            receiptHtml.append(String.format("%.2f", item.getPrice() * item.getQuantity()))
                    .append("<br>");
        }
        receiptHtml.append("-------------------------------<br>");

        double subtotal = orderItems.stream().mapToDouble(i -> i.getPrice() * i.getQuantity()).sum();
        double discount = order.getDiscount();
        double total = subtotal - discount;

        receiptHtml.append("Subtotal: ").append(String.format("%.2f", subtotal)).append("<br>");
        receiptHtml.append("Discount: ").append(String.format("%.2f", discount)).append("<br>");
        receiptHtml.append("TOTAL: ").append(String.format("%.2f", total)).append("<br>");
        receiptHtml.append("-------------------------------<br>");
        receiptHtml.append("Cash: ").append(String.format("%.2f", cash)).append("<br>");
        receiptHtml.append("Change: ").append(String.format("%.2f", change)).append("<br>");
        receiptHtml.append("</div>");

        // Centered footer: thank you
        receiptHtml.append("<div style='text-align:center; font-family: monospace;'>");
        receiptHtml.append("===============================<br>");
        receiptHtml.append("Thank you for visiting!<br>");
        receiptHtml.append("===============================<br>");
        receiptHtml.append("</div>");

        receiptHtml.append("</html>");

        // Add receipt to JLabel
        JLabel receiptLabel = new JLabel(receiptHtml.toString());
        receiptLabel.setVerticalAlignment(SwingConstants.TOP);

        // Scroll pane in case receipt is long
        JScrollPane scrollPane = new JScrollPane(receiptLabel);
        receiptFrame.add(scrollPane);

        receiptFrame.setVisible(true);
        refreshCart();
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        sideBar = new javax.swing.JPanel();
        btnSettings = new javax.swing.JButton();
        btnLogout = new javax.swing.JButton();
        btnPos = new javax.swing.JButton();
        fullName = new javax.swing.JLabel();
        mainPanel = new javax.swing.JPanel();
        posPanel = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        panelss = new javax.swing.JPanel();
        numberCashReceived = new javax.swing.JTextField();
        btnConfirmPayment = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        totalAmountOfOrder = new javax.swing.JLabel();
        ifDiscounted = new javax.swing.JCheckBox();
        amountDiscount = new javax.swing.JLabel();
        cart = new javax.swing.JScrollPane();
        jSeparator1 = new javax.swing.JSeparator();
        icon_cart = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        items = new javax.swing.JLabel();
        menuPanel = new javax.swing.JScrollPane();
        selectCategory = new javax.swing.JComboBox<>();
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

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Coffee Management System");
        setBackground(new java.awt.Color(245, 234, 219));

        jPanel1.setBackground(new java.awt.Color(245, 234, 219));

        sideBar.setBackground(new java.awt.Color(146, 109, 79));
        sideBar.setPreferredSize(new java.awt.Dimension(223, 700));

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
        btnLogout.setPreferredSize(new java.awt.Dimension(104, 29));
        btnLogout.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLogoutActionPerformed(evt);
            }
        });

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

        fullName.setFont(new java.awt.Font("Segoe UI Black", 0, 15)); // NOI18N
        fullName.setForeground(new java.awt.Color(244, 236, 223));
        fullName.setText("jLabel4");

        javax.swing.GroupLayout sideBarLayout = new javax.swing.GroupLayout(sideBar);
        sideBar.setLayout(sideBarLayout);
        sideBarLayout.setHorizontalGroup(
            sideBarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, sideBarLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(fullName, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(611, 611, 611)
                .addComponent(btnPos, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnSettings, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnLogout, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        sideBarLayout.setVerticalGroup(
            sideBarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(sideBarLayout.createSequentialGroup()
                .addGroup(sideBarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnPos, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(fullName))
                .addGap(0, 0, Short.MAX_VALUE))
            .addComponent(btnLogout, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(btnSettings, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        mainPanel.setBackground(new java.awt.Color(245, 234, 219));
        mainPanel.setPreferredSize(new java.awt.Dimension(774, 700));
        mainPanel.setLayout(new java.awt.CardLayout());

        posPanel.setBackground(new java.awt.Color(245, 234, 219));

        jLabel11.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(85, 53, 35));
        jLabel11.setText("Point of Sale");

        panelss.setBackground(new java.awt.Color(254, 252, 251));

        numberCashReceived.setBackground(new java.awt.Color(244, 236, 223));
        numberCashReceived.setForeground(new java.awt.Color(51, 51, 51));
        numberCashReceived.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                numberCashReceivedActionPerformed(evt);
            }
        });

        btnConfirmPayment.setBackground(new java.awt.Color(153, 102, 0));
        btnConfirmPayment.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        btnConfirmPayment.setText("Confirm Payment");
        btnConfirmPayment.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnConfirmPaymentActionPerformed(evt);
            }
        });

        jLabel1.setText("TOTAL");

        totalAmountOfOrder.setText("₱ 0.00");

        ifDiscounted.setText("Discount");
        ifDiscounted.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ifDiscountedActionPerformed(evt);
            }
        });

        amountDiscount.setText("₱ 0.00");

        cart.setBackground(new java.awt.Color(254, 252, 251));
        cart.setOpaque(false);

        jLabel12.setFont(new java.awt.Font("Times New Roman", 1, 19)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(85, 53, 35));
        jLabel12.setText("Cart");

        items.setBackground(new java.awt.Color(245, 234, 219));
        items.setText("O item");

        javax.swing.GroupLayout panelssLayout = new javax.swing.GroupLayout(panelss);
        panelss.setLayout(panelssLayout);
        panelssLayout.setHorizontalGroup(
            panelssLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelssLayout.createSequentialGroup()
                .addGroup(panelssLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(panelssLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(icon_cart, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 217, Short.MAX_VALUE)
                        .addComponent(items, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, panelssLayout.createSequentialGroup()
                        .addGap(15, 15, 15)
                        .addGroup(panelssLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(numberCashReceived)
                            .addComponent(btnConfirmPayment, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(panelssLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(cart))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, panelssLayout.createSequentialGroup()
                        .addGap(14, 14, 14)
                        .addGroup(panelssLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panelssLayout.createSequentialGroup()
                                .addGap(21, 21, 21)
                                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(totalAmountOfOrder, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jSeparator1)
                            .addGroup(panelssLayout.createSequentialGroup()
                                .addComponent(ifDiscounted)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(amountDiscount, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addGap(22, 22, 22))
        );
        panelssLayout.setVerticalGroup(
            panelssLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelssLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelssLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(panelssLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel12)
                        .addComponent(items))
                    .addComponent(icon_cart, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(26, 26, 26)
                .addComponent(cart, javax.swing.GroupLayout.DEFAULT_SIZE, 346, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(5, 5, 5)
                .addGroup(panelssLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(ifDiscounted)
                    .addComponent(amountDiscount))
                .addGap(11, 11, 11)
                .addGroup(panelssLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(totalAmountOfOrder))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(numberCashReceived, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnConfirmPayment, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        menuPanel.setBackground(new java.awt.Color(245, 234, 219));
        menuPanel.setBorder(null);
        menuPanel.setOpaque(false);

        selectCategory.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                selectCategoryActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout posPanelLayout = new javax.swing.GroupLayout(posPanel);
        posPanel.setLayout(posPanelLayout);
        posPanelLayout.setHorizontalGroup(
            posPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(posPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(posPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, posPanelLayout.createSequentialGroup()
                        .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(433, 433, 433))
                    .addGroup(posPanelLayout.createSequentialGroup()
                        .addGroup(posPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(selectCategory, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(menuPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 570, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)))
                .addComponent(panelss, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(8, Short.MAX_VALUE))
        );
        posPanelLayout.setVerticalGroup(
            posPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(posPanelLayout.createSequentialGroup()
                .addComponent(jLabel11)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(posPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(panelss, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(posPanelLayout.createSequentialGroup()
                        .addComponent(selectCategory, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(7, 7, 7)
                        .addComponent(menuPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 516, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(9, Short.MAX_VALUE))
        );

        mainPanel.add(posPanel, "home");

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

        txtCurrentPassword.setBackground(new java.awt.Color(245, 234, 219));
        txtCurrentPassword.setText("Enter Current Password");
        txtCurrentPassword.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCurrentPasswordActionPerformed(evt);
            }
        });

        jLabel7.setText("New Password");

        txtNewPassword.setBackground(new java.awt.Color(245, 234, 219));
        txtNewPassword.setText("jPasswordField1");

        jLabel8.setText("Confirm New Password");

        txtConfirmPassword.setBackground(new java.awt.Color(245, 234, 219));
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
                    .addComponent(btnSubmitRequest)
                    .addGroup(changePasswordPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 353, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 629, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel6)
                        .addComponent(jLabel8)
                        .addComponent(jLabel7)
                        .addComponent(txtCurrentPassword, javax.swing.GroupLayout.DEFAULT_SIZE, 783, Short.MAX_VALUE)
                        .addComponent(txtNewPassword)
                        .addComponent(txtConfirmPassword, javax.swing.GroupLayout.DEFAULT_SIZE, 783, Short.MAX_VALUE)))
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
                .addComponent(btnSubmitRequest, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(38, Short.MAX_VALUE))
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
                .addGap(0, 39, Short.MAX_VALUE))
        );

        mainPanel.add(settingsPanel, "setting");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(sideBar, javax.swing.GroupLayout.DEFAULT_SIZE, 998, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(mainPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 992, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(sideBar, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(mainPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 614, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
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
                .addGap(0, 6, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnConfirmPaymentActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnConfirmPaymentActionPerformed
        OrderController oc = new OrderController();
        String orderNumber = oc.formatOrderNumber((int)(System.currentTimeMillis() % 1000));

        // 1. Check if cart is empty
        if(cartModel.getItems().isEmpty()){
            JOptionPane.showMessageDialog(
                this,
                "Cart is empty. Please add items first.",
                "No Order",
                JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        // 2. Get cash input
        String placeholder = "Enter Cash Received";
        String cashInput = numberCashReceived.getText().trim();

        if(cashInput.isEmpty() || cashInput.equals(placeholder)){
            JOptionPane.showMessageDialog(
                this,
                "Please enter cash received.",
                "Input Required",
                JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        double cash;
        try {
            cash = Double.parseDouble(cashInput);
            if(cash <= 0){
                JOptionPane.showMessageDialog(
                    this,
                    "Cash must be greater than 0.",
                    "Invalid Input",
                    JOptionPane.ERROR_MESSAGE
                );
                return;
            }
        } catch(NumberFormatException e){
            JOptionPane.showMessageDialog(
                this,
                "Please enter a valid number.",
                "Invalid Input",
                JOptionPane.ERROR_MESSAGE
            );
            return;
        }

        // 3. Calculate totals
        double subtotal = cartModel.getTotal();
        double discount = ifDiscounted.isSelected() ? subtotal * 0.20 : 0;
        double total = subtotal - discount;

        if(cash < total){
            JOptionPane.showMessageDialog(
                this,
                "Insufficient payment.",
                "Payment Error",
                JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        double change = cash - total;

        // 4. Confirm payment dialog
        String confirmMsg = String.format(
            "Please confirm payment details:\n\n" +
            "Subtotal: ₱ %.2f\n" +
            "Discount: ₱ %.2f\n" +
            "Total: ₱ %.2f\n" +
            "Cash Received: ₱ %.2f\n" +
            "Change: ₱ %.2f\n\n" +
            "Proceed with payment?",
            subtotal, discount, total, cash, change
        );

        int confirm = JOptionPane.showConfirmDialog(
            this,
            confirmMsg,
            "Confirm Payment",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.QUESTION_MESSAGE
        );

        if(confirm != JOptionPane.YES_OPTION){
            return; // Cancel payment if NO
        }

        // 5. Create order object
        Order order = new Order();
        order.setOrderNumber(orderNumber);
        order.setStatus("Pending");
        order.setSubtotal(subtotal);
        order.setDiscount(discount);
        order.setTotal(total);

        // Copy cart items for receipt
        List<CartItem> cartItemsCopy = new ArrayList<>();
        List<OrderItem> orderItems = new ArrayList<>();
        for(CartItem item : cartModel.getItems()){
            cartItemsCopy.add(item); // for receipt

            OrderItem oi = new OrderItem();
            oi.setProductId(item.getProduct().getProductId());
            oi.setSizeId(item.getSizeId());
            oi.setQuantity(item.getQuantity());
            oi.setPrice(item.getPrice());
            oi.setSubtotal(item.getSubtotal());
            orderItems.add(oi);
        }

        // 6. Save order
        try {
            boolean saved = oc.saveOrder(order, orderItems);
            if(!saved){
                JOptionPane.showMessageDialog(this, "Failed to save order. Please try again.");
                return;
            }
        } catch(Exception e){
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error saving order: " + e.getMessage());
            return;
        }

        // 7. Show receipt popup
        showOrderReceiptPopup(order, cartItemsCopy, cash, change);

        // 8. Clear cart and reset UI
        cartModel.clearCart();
        numberCashReceived.setText("");
        ifDiscounted.setSelected(false);
        refreshCart();



    }//GEN-LAST:event_btnConfirmPaymentActionPerformed

    private void numberCashReceivedActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_numberCashReceivedActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_numberCashReceivedActionPerformed

    private void ifDiscountedActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ifDiscountedActionPerformed
        refreshCart();

        if(ifDiscounted.isSelected()){
            JOptionPane.showMessageDialog(this, "20% Discount Applied");
        }
    }//GEN-LAST:event_ifDiscountedActionPerformed

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

    private void btnPosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPosActionPerformed
        CardLayout cl = (CardLayout)(mainPanel).getLayout();
        cl.show(mainPanel, "home");
    }//GEN-LAST:event_btnPosActionPerformed

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

    private void btnSettingsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSettingsActionPerformed
        CardLayout cl = (CardLayout)(mainPanel).getLayout();
        cl.show(mainPanel, "setting");
    }//GEN-LAST:event_btnSettingsActionPerformed

    private void selectCategoryActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_selectCategoryActionPerformed
        String category = (String) selectCategory.getSelectedItem();
        loadMenuProductsByCategory(category);
    }//GEN-LAST:event_selectCategoryActionPerformed

    
    public static void main(String args[]) {
        new login().setVisible(true);
        
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel amountDiscount;
    private javax.swing.JButton btnConfirmPayment;
    private javax.swing.JButton btnLogout;
    private javax.swing.JButton btnPos;
    private javax.swing.JButton btnSettings;
    private javax.swing.JButton btnSubmitRequest;
    private javax.swing.JScrollPane cart;
    private javax.swing.JPanel changePasswordPanel;
    private javax.swing.JLabel fullName;
    private javax.swing.JLabel icon_cart;
    private javax.swing.JCheckBox ifDiscounted;
    private javax.swing.JLabel items;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JPanel mainPanel;
    private javax.swing.JScrollPane menuPanel;
    private javax.swing.JTextField numberCashReceived;
    private javax.swing.JPanel panelss;
    private javax.swing.JPanel posPanel;
    private javax.swing.JComboBox<String> selectCategory;
    private javax.swing.JPanel settingsPanel;
    private javax.swing.JPanel sideBar;
    private javax.swing.JPanel staffInformationPanel;
    private javax.swing.JLabel totalAmountOfOrder;
    private javax.swing.JPasswordField txtConfirmPassword;
    private javax.swing.JPasswordField txtCurrentPassword;
    private javax.swing.JPasswordField txtNewPassword;
    // End of variables declaration//GEN-END:variables
}