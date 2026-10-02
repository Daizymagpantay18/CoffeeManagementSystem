/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package views.Admin;

import controllers.IngredientController;
import controllers.OrderController;
import controllers.ProductController;
import util.EditUserDialog;
import util.ApprovalPanel;
import controllers.UserController;
import data_base.data_Base;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.RenderingHints;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import models.user;
import util.SidebarUtils;
import util.TableUtils;
import views.login;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import models.Ingredient;
import models.Product;
import util.ButtonStyler;
import util.LowStockAlertPanel;
import util.PanelUtils;
import util.PlaceholderUtil;
import util.RoundedButton;
import util.RoundedField;
import util.RoundedLabel;
import util.SalesChartPanel;
import util.StaffInfoPanelSmall;
import util.TableUtils1;
import util.TopProductsChartPanel;
import util.UIStyles;
import util.WeeklySalesChart;

public class Admin extends javax.swing.JFrame {

    private user currentUser;
    private javax.swing.JScrollPane scrollPaneStaff;
    private List<user> staffList;
    private ApprovalPanel approvalPanel;
    private IngredientController ingredientController = new IngredientController();
    private StaffInfoPanelSmall staffInfoPanel;
    
    
    public Admin(user adminUser) {
        initComponents();
        initMenuTable();
        initIngredientTable();
        loadIngredientTable();
        loadDashboardData();
        loadSold();
        
        buttons();
        RoundedLabel numNotif = new RoundedLabel("0");
        numNotif.setPreferredSize(new Dimension(20,20));
        numNotif.setForeground(Color.WHITE);
        numNotif.setBackground(new Color(220,53,69));
        numNotif.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        approvalPanel = new ApprovalPanel(mainPanel);
        mainPanel.add(approvalPanel, "approvals");
        
        this.currentUser = adminUser;
        initUserInfo();
        initSidebar();
        initUserTable();
        initNotificationPanel();
        setupNotificationButton();
        
        pack();  
        java.awt.EventQueue.invokeLater(() -> {

            javax.swing.JLayeredPane layeredPane = getLayeredPane();

            notifPopupPanel.setBounds(
                top.getX() + top.getWidth() - 280,
                top.getY() + top.getHeight(),
                260,
                0
            );

            layeredPane.add(notifPopupPanel, javax.swing.JLayeredPane.POPUP_LAYER);
        });
        
        btnHome.doClick();
        
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
    private void initUserInfo() {
        fullName.setText(currentUser.getFullName());
        fullName1.setText(currentUser.getFullName());
        role.setText(currentUser.getRole().toUpperCase());
        
        staffInfoPanel = new StaffInfoPanelSmall();
        staffInformationPanel.setLayout(new BorderLayout());
        staffInformationPanel.removeAll();
        staffInformationPanel.add(staffInfoPanel, BorderLayout.CENTER);
        staffInformationPanel.setPreferredSize(null);

        changePasswordPanel.setPreferredSize(new java.awt.Dimension(200, 335));

        staffInfoPanel.setStaffInfo(
            currentUser.getFullName(),
            currentUser.getUserName(),
            currentUser.getRole(),
            currentUser.getStatus()
        );
        staffInformationPanel.revalidate();
        staffInformationPanel.repaint();
        
        OrderController oc = new OrderController();
        
        double totalSales = oc.getTotalSalesOrders();
        total_Sales.setText("₱" + String.format("%.2f", totalSales));
        
        int totalOrder = oc.getTotalOrdersCount();
        total_orders.setText(String.valueOf(totalOrder));
        
        List<Ingredient> lowStock = ingredientController.getLowStockIngredients();
        total_low.setText(String.valueOf(lowStock.size()));
        
        int served_Orders = oc.getServedOrdersCount();
        order_served.setText(String.valueOf(served_Orders));   
        PanelUtils.makeRounded(inerPanel, 30);
        PanelUtils.makeRounded(inerPanel3, 30);
        PanelUtils.makeRounded(inerPanel5, 30);
        PanelUtils.makeRounded(salesPanel, 20);
        PanelUtils.makeRounded(totalOrders, 20);
        PanelUtils.makeRounded(lowStockItem, 20);
        PanelUtils.makeRounded(servedOrders, 20);
        PanelUtils.makeRounded(changePasswordPanel, 30);
        PanelUtils.makeRounded(staffInformationPanel, 30);
        
        setLabelImage(sales_icon, "/image/dollar.png");
        setLabelImage(orders_icon, "/image/order.png");
        setLabelImage(stock_icon, "/image/alert.png");
        setLabelImage(ord_icon, "/image/served.png");
        
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
        
        RoundedButton SubmitRequest = new RoundedButton("Save Changes", null);
        ButtonStyler.applyCoffeeStyle(SubmitRequest);
        SubmitRequest.addActionListener(evt -> btnSubmitRequestActionPerformed(evt));
        
        
        layout.replace(btnSubmitRequest, SubmitRequest);
        btnSubmitRequest = SubmitRequest;
        
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
    private void loadDashboardData() {

        OrderController orderController = new OrderController();
        IngredientController ingredientController = new IngredientController();
        ProductController productController = new ProductController();

        // --- Summary Labels ---
        double totalSales = orderController.getTotalSalesOrders();
        total_Sales.setText("₱" + String.format("%.2f", totalSales));

        int totalOrder = orderController.getTotalOrdersCount();
        total_orders.setText(String.valueOf(totalOrder));

        List<Ingredient> lowStock = ingredientController.getLowStockIngredients();
        total_low.setText(String.valueOf(lowStock.size()));

        int servedOrders = orderController.getServedOrdersCount();
        order_served.setText(String.valueOf(servedOrders));

        // --- Sales Chart ---
        double[] weeklySales = orderController.getWeeklySales();
        SalesChartPanel salesChart = new SalesChartPanel(weeklySales);

        salesBar.removeAll();
        salesBar.setLayout(new BorderLayout());
        salesBar.add(salesChart, BorderLayout.CENTER);
        salesBar.revalidate();
        salesBar.repaint();

        // --- Top Products Chart ---
        Object[] chartData = productController.getTopProductsChartData();
        String[] names = (String[]) chartData[0];
        double[] values = (double[]) chartData[1];

        TopProductsChartPanel topChart = new TopProductsChartPanel(names, values);
        topProduct.removeAll();
        topProduct.setLayout(new BorderLayout());
        topProduct.add(topChart, BorderLayout.CENTER);
        topProduct.revalidate();
        topProduct.repaint();

        // --- Low Stock Panel ---
        lowPanel.removeAll();
        LowStockAlertPanel lowStockPanel = new LowStockAlertPanel();
        lowPanel.setLayout(new BorderLayout());
        lowPanel.add(lowStockPanel, BorderLayout.CENTER);
        lowPanel.revalidate();
        lowPanel.repaint();
        
        weakly.removeAll();
        weakly.setLayout(new BorderLayout());
        WeeklySalesChart chart = new WeeklySalesChart();
        weakly.add(chart);
        weakly.revalidate();
        weakly.repaint();
    }
    private void startAutoRefresh(){

        int delay = 5000; // 5 seconds

        new javax.swing.Timer(delay, e -> {

            loadDashboardData();

        }).start();
    }
    private void initSidebar() {
        SidebarUtils.setupSidebarButton(btnHome);
        SidebarUtils.setupSidebarButton(btnUser);
        SidebarUtils.setupSidebarButton(btnMenu);
        SidebarUtils.setupSidebarButton(btnInventory);
        SidebarUtils.setupSidebarButton(btnReport);
        SidebarUtils.setupSidebarButton(btnSettings);

        SidebarUtils.setIcon(btnHome, "/image/dashboard.png", Admin.class,new Color(244, 236, 223));
        SidebarUtils.setIcon(btnUser, "/image/user.png", Admin.class, new Color(244, 236, 223));
        SidebarUtils.setIcon(btnMenu, "/image/coffeeMenu.png", Admin.class, new Color(244, 236, 223));
        SidebarUtils.setIcon(btnInventory, "/image/inventory.png", Admin.class, new Color(244, 236, 223));
        SidebarUtils.setIcon(btnReport, "/image/report.png", Admin.class, new Color(244, 236, 223));
        SidebarUtils.setIcon(btnSettings, "/image/setting.png", Admin.class, new Color(244, 236, 223));
        SidebarUtils.setIcon(btnLogout, "/image/logout.png", Admin.class, new Color(244, 236, 223));
    }
    private void initUserTable() {
        TableUtils.styleTable(userTable);
        userTable.getColumnModel().getColumn(0).setMinWidth(0);
        userTable.getColumnModel().getColumn(0).setMaxWidth(0);
        userTable.getColumnModel().getColumn(0).setWidth(0);
        userTable.getColumn("Status").setCellRenderer(new TableUtils.StatusRenderer());
        userTable.getColumn("Action").setCellRenderer(new TableUtils.ActionRenderer());
        userTable.getColumn("Action").setCellEditor(new TableUtils.ActionEditor(userTable, row -> {
            user selectedUser = staffList.get(row);
            new EditUserDialog(selectedUser).setVisible(true);
            loadUserTable();
        }));
        DefaultTableCellRenderer center = new DefaultTableCellRenderer();
        center.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        userTable.getColumn("Staff ID").setCellRenderer(center);
        userTable.getColumn("Full Name").setCellRenderer(center);
        userTable.getColumn("Username").setCellRenderer(center);
        userTable.getColumn("Role").setCellRenderer(center);

        userTable.setBorder(null);
        userTable.setFillsViewportHeight(true);

        loadUserTable(); 
    }
    private void initIngredientTable() {

        TableUtils1.styleTable(ingredientTable);

        ingredientTable.getColumnModel().getColumn(0).setMinWidth(0);
        ingredientTable.getColumnModel().getColumn(0).setMaxWidth(0);
        ingredientTable.getColumnModel().getColumn(0).setWidth(0);
        
        ingredientTable.getColumn("Actions").setPreferredWidth(120);
        ingredientTable.setRowHeight(35);
        // STATUS COLUMN
        ingredientTable.getColumn("Status")
                .setCellRenderer(new TableUtils1.StatusRenderer());

        // ACTION COLUMN RENDERER
        ingredientTable.getColumn("Actions")
                .setCellRenderer(new TableUtils1.ActionRenderer());

        // ACTION COLUMN EDITOR
        ingredientTable.getColumn("Actions")
                .setCellEditor(new TableUtils1.ActionEditor(
                        ingredientTable,
                        new TableUtils1.ActionEditor.ActionHandler() {

            @Override
            public void onEdit(int row) {

                int ingredientId = (int) ingredientTable.getValueAt(row, 0);

                Ingredient ingredient =
                        ingredientController.getIngredientById(ingredientId);

                addIngredient form = new addIngredient(ingredient);
                form.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosed(java.awt.event.WindowEvent e) {
                        loadIngredientTable();
                    }
                });
                
                form.setVisible(true);
            }

            @Override
            public void onDelete(int row) {

                int confirm = JOptionPane.showConfirmDialog(
                        null,
                        "Are you sure you want to delete this ingredient?",
                        "Confirm Delete",
                        JOptionPane.YES_NO_OPTION
                );

                if(confirm == JOptionPane.YES_OPTION){

                    int ingredientId =
                            (int) ingredientTable.getValueAt(row, 0);

                    if(ingredientController.isIngredientUsedInRecipe(ingredientId)){

                        JOptionPane.showMessageDialog(
                                null,
                                "Cannot delete ingredient because it is used in a recipe."
                        );

                    }else{

                        ingredientController.deleteIngredient(ingredientId);

                        JOptionPane.showMessageDialog(
                                null,
                                "Ingredient deleted successfully."
                        );

                        loadIngredientTable();
                    }
                }
            }
        }));
        
        DefaultTableCellRenderer center = new DefaultTableCellRenderer();
        center.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        ingredientTable.getColumn("ID").setCellRenderer(center);
        ingredientTable.getColumn("Ingredient").setCellRenderer(center);
        ingredientTable.getColumn("Stock").setCellRenderer(center);
        ingredientTable.getColumn("Prize").setCellRenderer(center);
        ingredientTable.getColumn("Threshold").setCellRenderer(center);

        ingredientTable.setBorder(null);
        ingredientTable.setFillsViewportHeight(true);
        
        loadIngredientTable();
    }
    private void initMenuTable() {

        TableUtils.styleTable(tblMenu);

        // hide ID column
        tblMenu.getColumnModel().getColumn(0).setMinWidth(0);
        tblMenu.getColumnModel().getColumn(0).setMaxWidth(0);
        tblMenu.getColumnModel().getColumn(0).setWidth(0);

        // STATUS COLUMN
        tblMenu.getColumn("Status")
                .setCellRenderer(new TableUtils.StatusRenderer());

        // ACTION COLUMN RENDERER
        tblMenu.getColumn("Action")
                .setCellRenderer(new TableUtils.ActionRenderer());

        // ACTION COLUMN EDITOR
        tblMenu.getColumn("Action")
                .setCellEditor(new TableUtils.ActionEditor(
                tblMenu,
                row -> {

            int productId = Integer.parseInt(
                    tblMenu.getValueAt(row, 0).toString());

            ProductController pc = new ProductController();
            Product product = pc.getProductById(productId);

            addMenu form = new addMenu(product);

            form.addWindowListener(new java.awt.event.WindowAdapter() {
                @Override
                public void windowClosed(java.awt.event.WindowEvent e) {
                    loadMenuTable();
                }
            });

            form.setVisible(true);
        }));


        // CENTER ALIGN COLUMNS
        DefaultTableCellRenderer center = new DefaultTableCellRenderer();
        center.setHorizontalAlignment(SwingConstants.CENTER);

        tblMenu.getColumn("ID").setCellRenderer(center);
        tblMenu.getColumn("Product").setCellRenderer(center);
        tblMenu.getColumn("Category").setCellRenderer(center);
        tblMenu.getColumn("Base Prize").setCellRenderer(center);

        tblMenu.setBorder(null);
        tblMenu.setFillsViewportHeight(true);

        loadMenuTable();
    }
    private void loadSold(String search) {

        DefaultTableModel model = (DefaultTableModel) tblSold.getModel();
        model.setRowCount(0);

        String sql = "SELECT oi.order_item_id, p.product_name, oi.quantity, oi.price, o.total, o.created_at " +
                     "FROM order_items oi " +
                     "JOIN orders o ON oi.order_id = o.order_id " +
                     "JOIN products p ON oi.product_id = p.product_id " +
                     "WHERE o.order_status = 'Served' AND p.product_name LIKE ? " +
                     "ORDER BY o.created_at DESC";

        try (Connection conn = data_Base.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, "%" + search + "%");

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Object[] row = new Object[]{
                    rs.getInt("order_item_id"),
                    rs.getString("product_name"),
                    rs.getInt("quantity"),
                    rs.getDouble("price"),
                    rs.getDouble("total"),
                    rs.getTimestamp("created_at")
                };
                model.addRow(row);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Failed to load sold items.", "Error", JOptionPane.ERROR_MESSAGE);
        }

        TableUtils.styleTable(tblSold);

        tblSold.getColumnModel().getColumn(0).setMinWidth(0);
        tblSold.getColumnModel().getColumn(0).setMaxWidth(0);
        tblSold.getColumnModel().getColumn(0).setWidth(0);

        DefaultTableCellRenderer center = new DefaultTableCellRenderer();
        center.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        tblSold.getColumn("ID").setCellRenderer(center);
        tblSold.getColumn("Product Name").setCellRenderer(center);
        tblSold.getColumn("Quantity").setCellRenderer(center);
        tblSold.getColumn("Price").setCellRenderer(center);
        tblSold.getColumn("Total").setCellRenderer(center);
        tblSold.getColumn("Date").setCellRenderer(center);

        tblSold.setBorder(null);
        tblSold.setFillsViewportHeight(true);
    }
    private void loadSold() {
        loadSold("");
    }
    private void initNotificationPanel() {
        notifPopupPanel = new javax.swing.JPanel();
        notifPopupPanel.setLayout(new javax.swing.BoxLayout(notifPopupPanel, javax.swing.BoxLayout.Y_AXIS));

        // Coffee theme colors
        notifPopupPanel.setBackground(new Color(245, 234, 219)); // light cream
        notifPopupPanel.setBorder(BorderFactory.createLineBorder(new Color(146,109,79), 1));

        notifPopupPanel.setVisible(false);
        notifPopupPanel.setSize(260, 0);

        notifPopupPanel.setBounds(500, 50, 260, 0);

        top.add(notifPopupPanel);

        notif.addActionListener(evt -> toggleNotificationPanel());

    }
    private void buttons(){
        RoundedButton addStaffBtn = new RoundedButton("Add Staff", "/image/add.png"); 
        addStaffBtn.addActionListener(evt -> btnAddStaffActionPerformed(evt)); 
        javax.swing.GroupLayout layout = (javax.swing.GroupLayout) inerPanel.getLayout(); 
        layout.replace(btnAddStaff, addStaffBtn); btnAddStaff = addStaffBtn; 
        inerPanel.revalidate(); 
        inerPanel.repaint();
        
        RoundedButton requestBtn = new RoundedButton("Request", "/image/add.png"); 
        requestBtn.addActionListener(evt -> btnRequestActionPerformed(evt)); 
        javax.swing.GroupLayout layoutt = (javax.swing.GroupLayout) inerPanel.getLayout(); 
        layoutt.replace(btnRequest, requestBtn); btnRequest = requestBtn; 
        inerPanel.revalidate(); 
        inerPanel.repaint();
        
        RoundedButton menuBtn = new RoundedButton("Add Product", "/image/add.png"); 
        menuBtn.addActionListener(evt -> btnAddMenuActionPerformed(evt)); 
        javax.swing.GroupLayout layouttt = (javax.swing.GroupLayout) inerPanel3.getLayout(); 
        layouttt.replace(btnAddMenu, menuBtn); btnAddMenu = menuBtn; 
        inerPanel3.revalidate(); 
        inerPanel3.repaint();
        
        RoundedButton inredientsBtn = new RoundedButton("Add Inredients", "/image/add.png"); 
        inredientsBtn.addActionListener(evt -> btnAddIngredientActionPerformed(evt)); 
        javax.swing.GroupLayout layoutttt = (javax.swing.GroupLayout) inerPanel5.getLayout(); 
        layoutttt.replace(btnAddIngredient, inredientsBtn); btnAddIngredient = inredientsBtn; 
        inerPanel5.revalidate(); 
        inerPanel5.repaint();
        
    }
    public static void setLabelImage(JLabel label, String path) {
        // 1️⃣ Load image
        ImageIcon icon = new ImageIcon(UIStyles.class.getResource(path));
        Image img = icon.getImage().getScaledInstance(
                label.getWidth(),
                label.getHeight(),
                Image.SCALE_SMOOTH
        );
        label.setIcon(new ImageIcon(img));
        label.setHorizontalAlignment(JLabel.CENTER);

        label.setOpaque(false);

        // 3️⃣ Store default and hover background colors
        Color defaultBg = Color.decode("#EDE9E7");
        Color defaultBgg = Color.decode("#FEFCFB");
        Color hoverBg = new Color(255, 204, 0);
        int radius = 25;

        label.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                label.repaint();
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                label.repaint();
            }
        });

        label.setUI(new javax.swing.plaf.basic.BasicLabelUI() {
            @Override
            public void paint(Graphics g, JComponent c) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                // Check if mouse is over the label
                Point mousePos = label.getMousePosition();
                g2.setColor(mousePos != null ? hoverBg : defaultBgg);

                g2.fillRoundRect(0, 0, c.getWidth(), c.getHeight(), radius, radius);

                super.paint(g2, c);
                g2.dispose();
            }
        });
    }
    private void toggleNotificationPanel() {
            if (notifVisible) {
                notifPopupPanel.setVisible(false);
                notifVisible = false;
            } else {
                showPendingRequests();
                notifPopupPanel.setVisible(true);
                notifVisible = true;
            }
    }
    private void setupNotificationButton() {
            notif.setBorderPainted(false);
            notif.setContentAreaFilled(false);
            notif.setFocusPainted(false);
            notif.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
            notif.setPreferredSize(new java.awt.Dimension(40, 40));

            SidebarUtils.setIcon(notif,
                "/image/bell.png",
                Admin.class,
                new Color(60,40,30)   // dark coffee
            );
    }
    private void showPendingRequests() {
        
        notifPopupPanel.removeAll();
        pendingCount = getPendingPasswordRequests().size();
        pendingCount = getPendingPasswordRequests().size();
        numNotif.setText(String.valueOf(pendingCount));
        notif.repaint();
        List<user> pending = getPendingPasswordRequests();

        if (pending.isEmpty()) {
            javax.swing.JLabel emptyLabel = new javax.swing.JLabel("No pending requests");
            emptyLabel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
            emptyLabel.setForeground(new Color(125,102,88));
            notifPopupPanel.add(emptyLabel);
        } else {

            for (user u : pending) {

                javax.swing.JPanel itemPanel = new javax.swing.JPanel();
                itemPanel.setLayout(new java.awt.BorderLayout());
                itemPanel.setMaximumSize(new java.awt.Dimension(250, 50));
                itemPanel.setBackground(new Color(239,224,205));

                javax.swing.JLabel label = new javax.swing.JLabel(
                    "<html><b>" + u.getFullName() + "</b><br/>requested password change</html>"
                );

                label.setBorder(BorderFactory.createEmptyBorder(5,10,5,10));
                label.setForeground(new Color(85,53,35));

                itemPanel.add(label, java.awt.BorderLayout.CENTER);

                // CLICK EVENT
                itemPanel.addMouseListener(new java.awt.event.MouseAdapter() {

                    @Override
                    public void mouseEntered(java.awt.event.MouseEvent e) {
                        itemPanel.setBackground(new Color(220,200,170));
                    }

                    @Override
                    public void mouseExited(java.awt.event.MouseEvent e) {
                        itemPanel.setBackground(new Color(239,224,205));
                    }

                    @Override
                    public void mouseClicked(java.awt.event.MouseEvent e) {

                        CardLayout cl = (CardLayout)(mainPanel.getLayout());
                        cl.show(mainPanel, "approvals");
                        
                        approvalPanel.loadApprovals();
                        approvalPanel.highlightByUserId(u.getUserId());

                        for (int i = 0; i < userTable.getRowCount(); i++) {
                            if (staffList.get(i).getUserId() == u.getUserId()) {
                                userTable.setRowSelectionInterval(i, i);
                                userTable.scrollRectToVisible(
                                    userTable.getCellRect(i, 0, true)
                                );
                                break;
                            }
                        }

                        notifPopupPanel.setVisible(false);
                        notifVisible = false;
                    }
                });

                notifPopupPanel.add(itemPanel);
            }
        }

        notifPopupPanel.setSize(260, notifPopupPanel.getPreferredSize().height);
        notifPopupPanel.revalidate();
        notifPopupPanel.repaint();
    }
    private void loadMenuTable(String search){

        DefaultTableModel model = (DefaultTableModel) tblMenu.getModel();
        model.setRowCount(0);

        ProductController pc = new ProductController();
        List<Product> products = pc.getAllProducts();
        
        String keyword = search.toLowerCase().trim();

        for(Product p : products){
            if(!search.isEmpty() && 
                !p.getName().toLowerCase().contains(search.toLowerCase()) &&
                !p.getCategory().toLowerCase().contains(search.toLowerCase())){
                 continue;
             }

            double basePrice = pc.getBasePrice(p.getProductId());

            String status;

            if(p.getStatus().equalsIgnoreCase("Available")){
                status = "Available";
            }else{
                status = "Not Available";
            }

            model.addRow(new Object[]{
                p.getProductId(),
                p.getName(),
                p.getCategory(),
                basePrice,
                status,
                "Edit"
            });
        }
    }
    private void loadMenuTable(){
        loadMenuTable("");
    }
    private void loadIngredientTable(String search){
        DefaultTableModel model = (DefaultTableModel) ingredientTable.getModel();
        model.setRowCount(0);

        List<Ingredient> list = ingredientController.getAllIngredients();
        String keyword = search.toLowerCase().trim();

        for(Ingredient i : list){
            
            String name = i.getName().toLowerCase();

            if(!keyword.isEmpty() && !name.contains(keyword)){
                continue;
            }

            String status;

            if(i.getStockQty() <= i.getLowStockThreshold()){
                status = "LOW STOCK";
            }else{
                status = "GOOD";
            }

            model.addRow(new Object[]{
                i.getIngredientId(),i.getName(), i.getStockQty(),i.getPricePerUnit(), i.getLowStockThreshold(), status,  "Edit | Delete"
            });
        }
    }
    private void loadIngredientTable(){
        loadIngredientTable("");
    }
    private void loadUserTable(String search) {
        UserController userController = new UserController();
        staffList = userController.getAllStaffExceptAdmin(currentUser.getUserId()); 

        DefaultTableModel model = (DefaultTableModel) userTable.getModel();
        model.setRowCount(0); 
        String keyword = search.toLowerCase().trim();
        for (user u : staffList) {
            String name = u.getFullName().toLowerCase();
            String username = u.getUserName().toLowerCase();
            String role = u.getRole().toLowerCase();
            String status = u.getStatus().toLowerCase();

            if(!keyword.isEmpty() &&
               !name.contains(keyword) &&
               !username.contains(keyword) &&
               !role.contains(keyword) &&
               !status.contains(keyword)){
                continue;
            }
            model.addRow(new Object[]{
                u.getFormattedId(),
                u.getFullName(), 
                u.getUserName(),
                u.getRole(),
                u.getStatus(),
                "Edit"
            });
        }
    }
    private void loadUserTable(){
        loadUserTable("");
    }
    public List<user> getPendingPasswordRequests() {
        List<user> pending = new ArrayList<>();
        String query = "SELECT pr.id, u.userId, u.firstName, u.lastName, u.username " +
                       "FROM password_requests pr " +
                       "JOIN users u ON pr.user_id = u.userId " +
                       "WHERE pr.status = 'pending'";

        try (Connection conn = data_Base.getConnection();
             PreparedStatement ps = conn.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                user u = new user();
                u.setUserId(rs.getInt("userId"));
                u.setFirstName(rs.getString("firstName"));
                u.setLastName(rs.getString("lastName"));
                u.setUserName(rs.getString("username"));
                pending.add(u);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        

        return pending;
    }
    public void approvePasswordChange(int userId) {
        String selectQuery = "SELECT newPassword FROM password_requests WHERE user_id = ? AND status = 'pending'";
        String updateUser = "UPDATE users SET password = ? WHERE userId = ?";
        String updateRequest = "UPDATE password_requests SET status = 'approved' WHERE user_id = ? AND status = 'pending'";

        try (Connection conn = data_Base.getConnection();
             PreparedStatement ps1 = conn.prepareStatement(selectQuery)) {

            ps1.setInt(1, userId);
            ResultSet rs = ps1.executeQuery();

            if (rs.next()) {
                String hashedPass = rs.getString("newPassword");

                try (PreparedStatement ps2 = conn.prepareStatement(updateUser)) {
                    ps2.setString(1, hashedPass);
                    ps2.setInt(2, userId);
                    ps2.executeUpdate();
                }

                try (PreparedStatement ps3 = conn.prepareStatement(updateRequest)) {
                    ps3.setInt(1, userId);
                    ps3.executeUpdate();
                }

                System.out.println("Password approved for userId: " + userId);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void rejectPasswordChange(int userId) {
        String query = "UPDATE password_requests SET status = 'rejected' WHERE user_id = ? AND status = 'pending'";
        try (Connection conn = data_Base.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setInt(1, userId);
            ps.executeUpdate();
            System.out.println("Password rejected for userId: " + userId);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        mainPanel = new javax.swing.JPanel();
        reportPanel = new javax.swing.JPanel();
        weakly = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblSold = new javax.swing.JTable();
        jLabel26 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        searchSold = new javax.swing.JTextField();
        settingsPanel = new javax.swing.JPanel();
        staffInformationPanel = new javax.swing.JPanel();
        jLabel15 = new javax.swing.JLabel();
        changePasswordPanel = new javax.swing.JPanel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        txtCurrentPassword = new javax.swing.JPasswordField();
        jLabel19 = new javax.swing.JLabel();
        txtNewPassword = new javax.swing.JPasswordField();
        jLabel20 = new javax.swing.JLabel();
        txtConfirmPassword = new javax.swing.JPasswordField();
        btnSubmitRequest = new javax.swing.JButton();
        jLabel27 = new javax.swing.JLabel();
        manuPanel = new javax.swing.JPanel();
        inerPanel3 = new javax.swing.JPanel();
        btnAddMenu = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblMenu = new javax.swing.JTable();
        searchMenu = new javax.swing.JTextField();
        jLabel28 = new javax.swing.JLabel();
        userPanel = new javax.swing.JPanel();
        inerPanel = new javax.swing.JPanel();
        btnAddStaff = new javax.swing.JButton();
        jScrollPane4 = new javax.swing.JScrollPane();
        userTable = new javax.swing.JTable();
        btnRequest = new javax.swing.JButton();
        searchUser = new javax.swing.JTextField();
        jLabel29 = new javax.swing.JLabel();
        inventoryPanel = new javax.swing.JPanel();
        inerPanel5 = new javax.swing.JPanel();
        ingredients = new javax.swing.JScrollPane();
        ingredientTable = new javax.swing.JTable();
        btnAddIngredient = new javax.swing.JButton();
        searchInventory = new javax.swing.JTextField();
        jLabel30 = new javax.swing.JLabel();
        dashboardPanel = new javax.swing.JPanel();
        salesPanel = new javax.swing.JPanel();
        sales_icon = new javax.swing.JLabel();
        pendingtotal5 = new javax.swing.JLabel();
        total_Sales = new javax.swing.JLabel();
        totalOrders = new javax.swing.JPanel();
        orders_icon = new javax.swing.JLabel();
        pendingtotal6 = new javax.swing.JLabel();
        total_orders = new javax.swing.JLabel();
        lowStockItem = new javax.swing.JPanel();
        stock_icon = new javax.swing.JLabel();
        pendingtotal7 = new javax.swing.JLabel();
        total_low = new javax.swing.JLabel();
        servedOrders = new javax.swing.JPanel();
        ord_icon = new javax.swing.JLabel();
        pendingtotal8 = new javax.swing.JLabel();
        order_served = new javax.swing.JLabel();
        salesBar = new javax.swing.JPanel();
        topProduct = new javax.swing.JPanel();
        lowPanel = new javax.swing.JPanel();
        jLabel31 = new javax.swing.JLabel();
        sideBar = new javax.swing.JPanel();
        btnHome = new javax.swing.JButton();
        btnUser = new javax.swing.JButton();
        btnMenu = new javax.swing.JButton();
        btnReport = new javax.swing.JButton();
        btnSettings = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JSeparator();
        btnLogout = new javax.swing.JButton();
        fullName = new javax.swing.JLabel();
        role = new javax.swing.JLabel();
        jSeparator2 = new javax.swing.JSeparator();
        jLabel4 = new javax.swing.JLabel();
        btnInventory = new javax.swing.JButton();
        top = new javax.swing.JPanel();
        fullName1 = new javax.swing.JLabel();
        notif = new javax.swing.JButton();
        numNotif = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(245, 234, 219));

        jPanel1.setBackground(new java.awt.Color(245, 234, 219));

        mainPanel.setLayout(new java.awt.CardLayout());

        reportPanel.setBackground(new java.awt.Color(245, 234, 219));

        javax.swing.GroupLayout weaklyLayout = new javax.swing.GroupLayout(weakly);
        weakly.setLayout(weaklyLayout);
        weaklyLayout.setHorizontalGroup(
            weaklyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 679, Short.MAX_VALUE)
        );
        weaklyLayout.setVerticalGroup(
            weaklyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 225, Short.MAX_VALUE)
        );

        tblSold.setBackground(new java.awt.Color(239, 224, 205));
        tblSold.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "ID", "Product Name", "Quantity", "Price", "Total", "Date"
            }
        ));
        jScrollPane1.setViewportView(tblSold);

        jLabel26.setFont(new java.awt.Font("Times New Roman", 1, 36)); // NOI18N
        jLabel26.setForeground(new java.awt.Color(85, 53, 35));
        jLabel26.setText("Reports");

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(146, 109, 79));
        jLabel1.setText("Sold Table");

        searchSold.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchSoldActionPerformed(evt);
            }
        });
        searchSold.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                searchSoldKeyReleased(evt);
            }
        });

        javax.swing.GroupLayout reportPanelLayout = new javax.swing.GroupLayout(reportPanel);
        reportPanel.setLayout(reportPanelLayout);
        reportPanelLayout.setHorizontalGroup(
            reportPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(reportPanelLayout.createSequentialGroup()
                .addGroup(reportPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(reportPanelLayout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jLabel26, javax.swing.GroupLayout.PREFERRED_SIZE, 227, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(reportPanelLayout.createSequentialGroup()
                        .addGap(39, 39, 39)
                        .addGroup(reportPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(weakly, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(reportPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(searchSold, javax.swing.GroupLayout.PREFERRED_SIZE, 199, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 672, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap(62, Short.MAX_VALUE))
        );
        reportPanelLayout.setVerticalGroup(
            reportPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(reportPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel26)
                .addGap(17, 17, 17)
                .addComponent(weakly, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(reportPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(searchSold, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 179, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(63, Short.MAX_VALUE))
        );

        mainPanel.add(reportPanel, "report");

        settingsPanel.setBackground(new java.awt.Color(245, 234, 219));

        staffInformationPanel.setBackground(new java.awt.Color(254, 252, 251));

        jLabel15.setFont(new java.awt.Font("Segoe UI", 1, 22)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(85, 53, 35));
        jLabel15.setText("Account Information");

        javax.swing.GroupLayout staffInformationPanelLayout = new javax.swing.GroupLayout(staffInformationPanel);
        staffInformationPanel.setLayout(staffInformationPanelLayout);
        staffInformationPanelLayout.setHorizontalGroup(
            staffInformationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(staffInformationPanelLayout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 353, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        staffInformationPanelLayout.setVerticalGroup(
            staffInformationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(staffInformationPanelLayout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(jLabel15)
                .addContainerGap(83, Short.MAX_VALUE))
        );

        changePasswordPanel.setBackground(new java.awt.Color(254, 252, 251));

        jLabel16.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(85, 53, 35));
        jLabel16.setText("Change Password");

        jLabel17.setFont(new java.awt.Font("Arial", 0, 10)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(125, 102, 88));
        jLabel17.setText("Password changes required Admin approval. Your request will be viewed before the update takes effect.");

        jLabel18.setText("Current Password");

        txtCurrentPassword.setBackground(new java.awt.Color(245, 234, 219));
        txtCurrentPassword.setText("Enter Current Password");
        txtCurrentPassword.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCurrentPasswordActionPerformed(evt);
            }
        });

        jLabel19.setText("New Password");

        txtNewPassword.setBackground(new java.awt.Color(245, 234, 219));
        txtNewPassword.setText("jPasswordField1");

        jLabel20.setText("Confirm New Password");

        txtConfirmPassword.setBackground(new java.awt.Color(245, 234, 219));
        txtConfirmPassword.setText("jPasswordField1");

        btnSubmitRequest.setText("Save Changes");
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
                .addGap(34, 34, 34)
                .addGroup(changePasswordPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 353, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 629, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel18)
                    .addComponent(jLabel20)
                    .addComponent(jLabel19)
                    .addGroup(changePasswordPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(btnSubmitRequest)
                        .addGroup(changePasswordPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(txtCurrentPassword, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 573, Short.MAX_VALUE)
                            .addComponent(txtNewPassword, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtConfirmPassword, javax.swing.GroupLayout.Alignment.LEADING))))
                .addContainerGap(36, Short.MAX_VALUE))
        );
        changePasswordPanelLayout.setVerticalGroup(
            changePasswordPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(changePasswordPanelLayout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(jLabel16)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel17)
                .addGap(18, 18, 18)
                .addComponent(jLabel18)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtCurrentPassword, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel19)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtNewPassword, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel20)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtConfirmPassword, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnSubmitRequest, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(23, Short.MAX_VALUE))
        );

        jLabel27.setFont(new java.awt.Font("Times New Roman", 1, 36)); // NOI18N
        jLabel27.setForeground(new java.awt.Color(85, 53, 35));
        jLabel27.setText("Settings");

        javax.swing.GroupLayout settingsPanelLayout = new javax.swing.GroupLayout(settingsPanel);
        settingsPanel.setLayout(settingsPanelLayout);
        settingsPanelLayout.setHorizontalGroup(
            settingsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(settingsPanelLayout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(settingsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel27, javax.swing.GroupLayout.PREFERRED_SIZE, 497, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(settingsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(staffInformationPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(changePasswordPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap(57, Short.MAX_VALUE))
        );
        settingsPanelLayout.setVerticalGroup(
            settingsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(settingsPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel27)
                .addGap(11, 11, 11)
                .addComponent(staffInformationPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(changePasswordPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(68, Short.MAX_VALUE))
        );

        mainPanel.add(settingsPanel, "setting");

        manuPanel.setBackground(new java.awt.Color(245, 234, 219));

        inerPanel3.setBackground(new java.awt.Color(254, 252, 251));
        inerPanel3.setPreferredSize(new java.awt.Dimension(708, 512));

        btnAddMenu.setBackground(new java.awt.Color(146, 109, 79));
        btnAddMenu.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnAddMenu.setForeground(new java.awt.Color(245, 234, 219));
        btnAddMenu.setText("Add Product");
        btnAddMenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddMenuActionPerformed(evt);
            }
        });

        tblMenu.setBackground(new java.awt.Color(245, 234, 219));
        tblMenu.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "ID", "Product", "Category", "Base Prize", "Status", "Action"
            }
        ));
        jScrollPane3.setViewportView(tblMenu);

        searchMenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchMenuActionPerformed(evt);
            }
        });
        searchMenu.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                searchMenuKeyReleased(evt);
            }
        });

        javax.swing.GroupLayout inerPanel3Layout = new javax.swing.GroupLayout(inerPanel3);
        inerPanel3.setLayout(inerPanel3Layout);
        inerPanel3Layout.setHorizontalGroup(
            inerPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, inerPanel3Layout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addComponent(searchMenu, javax.swing.GroupLayout.DEFAULT_SIZE, 335, Short.MAX_VALUE)
                .addGap(158, 158, 158)
                .addComponent(btnAddMenu, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(49, 49, 49))
            .addGroup(inerPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(inerPanel3Layout.createSequentialGroup()
                    .addGap(29, 29, 29)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 655, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(46, Short.MAX_VALUE)))
        );
        inerPanel3Layout.setVerticalGroup(
            inerPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(inerPanel3Layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addGroup(inerPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnAddMenu)
                    .addComponent(searchMenu, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(463, Short.MAX_VALUE))
            .addGroup(inerPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(inerPanel3Layout.createSequentialGroup()
                    .addGap(84, 84, 84)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 390, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(47, Short.MAX_VALUE)))
        );

        jLabel28.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel28.setForeground(new java.awt.Color(85, 53, 35));
        jLabel28.setText("Menu Management");

        javax.swing.GroupLayout manuPanelLayout = new javax.swing.GroupLayout(manuPanel);
        manuPanel.setLayout(manuPanelLayout);
        manuPanelLayout.setHorizontalGroup(
            manuPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(manuPanelLayout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addGroup(manuPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel28, javax.swing.GroupLayout.PREFERRED_SIZE, 333, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(inerPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 730, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(33, Short.MAX_VALUE))
        );
        manuPanelLayout.setVerticalGroup(
            manuPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, manuPanelLayout.createSequentialGroup()
                .addContainerGap(7, Short.MAX_VALUE)
                .addComponent(jLabel28, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(inerPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 521, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(32, 32, 32))
        );

        mainPanel.add(manuPanel, "menu");

        userPanel.setBackground(new java.awt.Color(245, 234, 219));

        inerPanel.setBackground(new java.awt.Color(254, 252, 251));

        btnAddStaff.setBackground(new java.awt.Color(146, 109, 79));
        btnAddStaff.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnAddStaff.setForeground(new java.awt.Color(245, 234, 219));
        btnAddStaff.setText("Add Staff");
        btnAddStaff.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddStaffActionPerformed(evt);
            }
        });

        userTable.setAutoCreateRowSorter(true);
        userTable.setBackground(new java.awt.Color(239, 224, 205));
        userTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "Staff ID", "Full Name", "Username", "Role", "Status", "Action"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Object.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jScrollPane4.setViewportView(userTable);

        btnRequest.setBackground(new java.awt.Color(146, 109, 79));
        btnRequest.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnRequest.setForeground(new java.awt.Color(245, 234, 219));
        btnRequest.setText("Request");
        btnRequest.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRequestActionPerformed(evt);
            }
        });

        searchUser.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchUserActionPerformed(evt);
            }
        });
        searchUser.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                searchUserKeyReleased(evt);
            }
        });

        javax.swing.GroupLayout inerPanelLayout = new javax.swing.GroupLayout(inerPanel);
        inerPanel.setLayout(inerPanelLayout);
        inerPanelLayout.setHorizontalGroup(
            inerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(inerPanelLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(inerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(inerPanelLayout.createSequentialGroup()
                        .addComponent(searchUser, javax.swing.GroupLayout.PREFERRED_SIZE, 237, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(147, 147, 147)
                        .addComponent(btnAddStaff, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnRequest, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 668, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(20, Short.MAX_VALUE))
        );
        inerPanelLayout.setVerticalGroup(
            inerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(inerPanelLayout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addGroup(inerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAddStaff, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnRequest)
                    .addComponent(searchUser, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 31, Short.MAX_VALUE)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 408, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(31, 31, 31))
        );

        jLabel29.setFont(new java.awt.Font("Times New Roman", 1, 10)); // NOI18N
        jLabel29.setForeground(new java.awt.Color(85, 53, 35));
        jLabel29.setText("Staff Management");

        javax.swing.GroupLayout userPanelLayout = new javax.swing.GroupLayout(userPanel);
        userPanel.setLayout(userPanelLayout);
        userPanelLayout.setHorizontalGroup(
            userPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(userPanelLayout.createSequentialGroup()
                .addGroup(userPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(userPanelLayout.createSequentialGroup()
                        .addGap(21, 21, 21)
                        .addComponent(inerPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(userPanelLayout.createSequentialGroup()
                        .addGap(31, 31, 31)
                        .addComponent(jLabel29, javax.swing.GroupLayout.PREFERRED_SIZE, 302, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(51, Short.MAX_VALUE))
        );
        userPanelLayout.setVerticalGroup(
            userPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(userPanelLayout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(jLabel29)
                .addGap(18, 18, 18)
                .addComponent(inerPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(49, Short.MAX_VALUE))
        );

        mainPanel.add(userPanel, "user");

        inventoryPanel.setBackground(new java.awt.Color(245, 234, 219));

        inerPanel5.setBackground(new java.awt.Color(254, 252, 251));
        inerPanel5.setPreferredSize(new java.awt.Dimension(708, 512));

        ingredientTable.setAutoCreateRowSorter(true);
        ingredientTable.setBackground(new java.awt.Color(245, 234, 219));
        ingredientTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "ID", "Ingredient", "Stock", "Prize", "Threshold", "Status", "Actions"
            }
        ));
        ingredients.setViewportView(ingredientTable);

        btnAddIngredient.setBackground(new java.awt.Color(146, 109, 79));
        btnAddIngredient.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnAddIngredient.setForeground(new java.awt.Color(245, 234, 219));
        btnAddIngredient.setText("Add Ingredient");
        btnAddIngredient.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddIngredientActionPerformed(evt);
            }
        });

        searchInventory.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchInventoryActionPerformed(evt);
            }
        });
        searchInventory.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                searchInventoryKeyReleased(evt);
            }
        });

        javax.swing.GroupLayout inerPanel5Layout = new javax.swing.GroupLayout(inerPanel5);
        inerPanel5.setLayout(inerPanel5Layout);
        inerPanel5Layout.setHorizontalGroup(
            inerPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(inerPanel5Layout.createSequentialGroup()
                .addContainerGap(21, Short.MAX_VALUE)
                .addGroup(inerPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(inerPanel5Layout.createSequentialGroup()
                        .addComponent(searchInventory, javax.swing.GroupLayout.DEFAULT_SIZE, 343, Short.MAX_VALUE)
                        .addGap(106, 106, 106)
                        .addComponent(btnAddIngredient, javax.swing.GroupLayout.PREFERRED_SIZE, 205, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(64, Short.MAX_VALUE))
                    .addGroup(inerPanel5Layout.createSequentialGroup()
                        .addComponent(ingredients, javax.swing.GroupLayout.PREFERRED_SIZE, 664, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(54, Short.MAX_VALUE))))
        );
        inerPanel5Layout.setVerticalGroup(
            inerPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(inerPanel5Layout.createSequentialGroup()
                .addContainerGap(53, Short.MAX_VALUE)
                .addGroup(inerPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAddIngredient)
                    .addComponent(searchInventory, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(17, 17, 17)
                .addComponent(ingredients, javax.swing.GroupLayout.PREFERRED_SIZE, 377, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(53, 53, 53))
        );

        jLabel30.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel30.setForeground(new java.awt.Color(85, 53, 35));
        jLabel30.setText("Inventory Management");

        javax.swing.GroupLayout inventoryPanelLayout = new javax.swing.GroupLayout(inventoryPanel);
        inventoryPanel.setLayout(inventoryPanelLayout);
        inventoryPanelLayout.setHorizontalGroup(
            inventoryPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(inventoryPanelLayout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addGroup(inventoryPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(inerPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, 739, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel30, javax.swing.GroupLayout.PREFERRED_SIZE, 389, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(24, Short.MAX_VALUE))
        );
        inventoryPanelLayout.setVerticalGroup(
            inventoryPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(inventoryPanelLayout.createSequentialGroup()
                .addGap(8, 8, 8)
                .addComponent(jLabel30)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(inerPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, 532, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(26, Short.MAX_VALUE))
        );

        mainPanel.add(inventoryPanel, "inventory");

        dashboardPanel.setBackground(new java.awt.Color(245, 234, 219));

        salesPanel.setBackground(new java.awt.Color(254, 252, 251));
        salesPanel.setPreferredSize(new java.awt.Dimension(175, 80));

        pendingtotal5.setFont(new java.awt.Font("Times New Roman", 0, 15)); // NOI18N
        pendingtotal5.setForeground(new java.awt.Color(146, 109, 79));
        pendingtotal5.setText("Total Sales");

        total_Sales.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        total_Sales.setText("0");

        javax.swing.GroupLayout salesPanelLayout = new javax.swing.GroupLayout(salesPanel);
        salesPanel.setLayout(salesPanelLayout);
        salesPanelLayout.setHorizontalGroup(
            salesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, salesPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(salesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(salesPanelLayout.createSequentialGroup()
                        .addComponent(pendingtotal5)
                        .addGap(0, 45, Short.MAX_VALUE))
                    .addComponent(total_Sales, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(sales_icon, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(15, 15, 15))
        );
        salesPanelLayout.setVerticalGroup(
            salesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(salesPanelLayout.createSequentialGroup()
                .addGroup(salesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(salesPanelLayout.createSequentialGroup()
                        .addGap(15, 15, 15)
                        .addComponent(sales_icon, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(salesPanelLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(pendingtotal5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(total_Sales)))
                .addContainerGap(21, Short.MAX_VALUE))
        );

        totalOrders.setBackground(new java.awt.Color(254, 252, 251));
        totalOrders.setPreferredSize(new java.awt.Dimension(175, 80));

        pendingtotal6.setFont(new java.awt.Font("Times New Roman", 0, 15)); // NOI18N
        pendingtotal6.setForeground(new java.awt.Color(146, 109, 79));
        pendingtotal6.setText("Total Orders");

        total_orders.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        total_orders.setText("0");

        javax.swing.GroupLayout totalOrdersLayout = new javax.swing.GroupLayout(totalOrders);
        totalOrders.setLayout(totalOrdersLayout);
        totalOrdersLayout.setHorizontalGroup(
            totalOrdersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, totalOrdersLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(totalOrdersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(totalOrdersLayout.createSequentialGroup()
                        .addComponent(pendingtotal6)
                        .addGap(0, 28, Short.MAX_VALUE))
                    .addComponent(total_orders, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(orders_icon, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(15, 15, 15))
        );
        totalOrdersLayout.setVerticalGroup(
            totalOrdersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(totalOrdersLayout.createSequentialGroup()
                .addGroup(totalOrdersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(totalOrdersLayout.createSequentialGroup()
                        .addGap(15, 15, 15)
                        .addComponent(orders_icon, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(totalOrdersLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(pendingtotal6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(total_orders)))
                .addContainerGap(21, Short.MAX_VALUE))
        );

        lowStockItem.setBackground(new java.awt.Color(254, 252, 251));
        lowStockItem.setPreferredSize(new java.awt.Dimension(175, 80));

        pendingtotal7.setFont(new java.awt.Font("Times New Roman", 0, 15)); // NOI18N
        pendingtotal7.setForeground(new java.awt.Color(146, 109, 79));
        pendingtotal7.setText("Low Stock Items");

        total_low.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        total_low.setText("0");

        javax.swing.GroupLayout lowStockItemLayout = new javax.swing.GroupLayout(lowStockItem);
        lowStockItem.setLayout(lowStockItemLayout);
        lowStockItemLayout.setHorizontalGroup(
            lowStockItemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, lowStockItemLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(lowStockItemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pendingtotal7)
                    .addComponent(total_low, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 14, Short.MAX_VALUE)
                .addComponent(stock_icon, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(15, 15, 15))
        );
        lowStockItemLayout.setVerticalGroup(
            lowStockItemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(lowStockItemLayout.createSequentialGroup()
                .addGroup(lowStockItemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(lowStockItemLayout.createSequentialGroup()
                        .addGap(15, 15, 15)
                        .addComponent(stock_icon, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(lowStockItemLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(pendingtotal7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(total_low)))
                .addContainerGap(21, Short.MAX_VALUE))
        );

        servedOrders.setBackground(new java.awt.Color(254, 252, 251));
        servedOrders.setPreferredSize(new java.awt.Dimension(175, 80));

        pendingtotal8.setFont(new java.awt.Font("Times New Roman", 0, 15)); // NOI18N
        pendingtotal8.setForeground(new java.awt.Color(146, 109, 79));
        pendingtotal8.setText("Order Served");

        order_served.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        order_served.setText("0");

        javax.swing.GroupLayout servedOrdersLayout = new javax.swing.GroupLayout(servedOrders);
        servedOrders.setLayout(servedOrdersLayout);
        servedOrdersLayout.setHorizontalGroup(
            servedOrdersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, servedOrdersLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(servedOrdersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(pendingtotal8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(order_served, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 36, Short.MAX_VALUE)
                .addComponent(ord_icon, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(15, 15, 15))
        );
        servedOrdersLayout.setVerticalGroup(
            servedOrdersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(servedOrdersLayout.createSequentialGroup()
                .addGroup(servedOrdersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(servedOrdersLayout.createSequentialGroup()
                        .addGap(15, 15, 15)
                        .addComponent(ord_icon, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(servedOrdersLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(pendingtotal8)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(order_served)))
                .addContainerGap(21, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout salesBarLayout = new javax.swing.GroupLayout(salesBar);
        salesBar.setLayout(salesBarLayout);
        salesBarLayout.setHorizontalGroup(
            salesBarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        salesBarLayout.setVerticalGroup(
            salesBarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 236, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout topProductLayout = new javax.swing.GroupLayout(topProduct);
        topProduct.setLayout(topProductLayout);
        topProductLayout.setHorizontalGroup(
            topProductLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        topProductLayout.setVerticalGroup(
            topProductLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 236, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout lowPanelLayout = new javax.swing.GroupLayout(lowPanel);
        lowPanel.setLayout(lowPanelLayout);
        lowPanelLayout.setHorizontalGroup(
            lowPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        lowPanelLayout.setVerticalGroup(
            lowPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 122, Short.MAX_VALUE)
        );

        jLabel31.setFont(new java.awt.Font("Times New Roman", 1, 28)); // NOI18N
        jLabel31.setForeground(new java.awt.Color(85, 53, 35));
        jLabel31.setText("Dashboard");

        javax.swing.GroupLayout dashboardPanelLayout = new javax.swing.GroupLayout(dashboardPanel);
        dashboardPanel.setLayout(dashboardPanelLayout);
        dashboardPanelLayout.setHorizontalGroup(
            dashboardPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dashboardPanelLayout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(dashboardPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel31, javax.swing.GroupLayout.PREFERRED_SIZE, 389, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(dashboardPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(lowPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(dashboardPanelLayout.createSequentialGroup()
                            .addGroup(dashboardPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(salesBar, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(dashboardPanelLayout.createSequentialGroup()
                                    .addComponent(salesPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(totalOrders, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(dashboardPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addGroup(dashboardPanelLayout.createSequentialGroup()
                                    .addComponent(lowStockItem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(servedOrders, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addComponent(topProduct, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))))
                .addContainerGap(37, Short.MAX_VALUE))
        );
        dashboardPanelLayout.setVerticalGroup(
            dashboardPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dashboardPanelLayout.createSequentialGroup()
                .addComponent(jLabel31)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(dashboardPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(servedOrders, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lowStockItem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(totalOrders, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(salesPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(dashboardPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(salesBar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(topProduct, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lowPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(106, Short.MAX_VALUE))
        );

        mainPanel.add(dashboardPanel, "home");

        sideBar.setBackground(new java.awt.Color(146, 109, 79));
        sideBar.setPreferredSize(new java.awt.Dimension(223, 700));

        btnHome.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        btnHome.setForeground(new java.awt.Color(244, 236, 223));
        btnHome.setText("Dashboard");
        btnHome.setAlignmentY(0.0F);
        btnHome.setBorderPainted(false);
        btnHome.setContentAreaFilled(false);
        btnHome.setRequestFocusEnabled(false);
        btnHome.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHomeActionPerformed(evt);
            }
        });

        btnUser.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        btnUser.setForeground(new java.awt.Color(244, 236, 223));
        btnUser.setText("Staff");
        btnUser.setBorderPainted(false);
        btnUser.setContentAreaFilled(false);
        btnUser.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUserActionPerformed(evt);
            }
        });

        btnMenu.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        btnMenu.setForeground(new java.awt.Color(244, 236, 223));
        btnMenu.setText("Menu");
        btnMenu.setBorderPainted(false);
        btnMenu.setContentAreaFilled(false);
        btnMenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMenuActionPerformed(evt);
            }
        });

        btnReport.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        btnReport.setForeground(new java.awt.Color(244, 236, 223));
        btnReport.setText("Reports");
        btnReport.setBorderPainted(false);
        btnReport.setContentAreaFilled(false);
        btnReport.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnReportActionPerformed(evt);
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

        btnInventory.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        btnInventory.setForeground(new java.awt.Color(244, 236, 223));
        btnInventory.setText("Inventory");
        btnInventory.setBorderPainted(false);
        btnInventory.setContentAreaFilled(false);
        btnInventory.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnInventoryActionPerformed(evt);
            }
        });

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
                            .addComponent(btnInventory, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnHome, javax.swing.GroupLayout.DEFAULT_SIZE, 217, Short.MAX_VALUE)
                            .addComponent(btnUser, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnMenu, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnReport, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
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
                .addComponent(btnHome, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnUser, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnMenu, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(4, 4, 4)
                .addComponent(btnInventory, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnReport, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
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

        numNotif.setBackground(new java.awt.Color(245, 234, 219));

        javax.swing.GroupLayout topLayout = new javax.swing.GroupLayout(top);
        top.setLayout(topLayout);
        topLayout.setHorizontalGroup(
            topLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, topLayout.createSequentialGroup()
                .addContainerGap(544, Short.MAX_VALUE)
                .addComponent(notif, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(numNotif, javax.swing.GroupLayout.PREFERRED_SIZE, 13, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(17, 17, 17)
                .addComponent(fullName1, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        topLayout.setVerticalGroup(
            topLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(notif, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(topLayout.createSequentialGroup()
                .addGroup(topLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(topLayout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(fullName1))
                    .addComponent(numNotif, javax.swing.GroupLayout.PREFERRED_SIZE, 12, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 6, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(sideBar, javax.swing.GroupLayout.PREFERRED_SIZE, 229, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(top, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(mainPanel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 780, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(sideBar, javax.swing.GroupLayout.DEFAULT_SIZE, 640, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(top, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(mainPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 1019, javax.swing.GroupLayout.PREFERRED_SIZE)
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

    private void btnHomeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHomeActionPerformed
        CardLayout cl = (CardLayout)(mainPanel).getLayout();
        cl.show(mainPanel, "home");
        loadDashboardData();
    }//GEN-LAST:event_btnHomeActionPerformed

    private void btnUserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUserActionPerformed
        CardLayout cl = (CardLayout)(mainPanel).getLayout();
        cl.show(mainPanel, "user");
        
        loadUserTable();
    }//GEN-LAST:event_btnUserActionPerformed

    private void btnMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMenuActionPerformed
        CardLayout cl = (CardLayout)(mainPanel).getLayout();
        cl.show(mainPanel, "menu");
        
        loadMenuTable();
    }//GEN-LAST:event_btnMenuActionPerformed

    private void btnReportActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnReportActionPerformed
        CardLayout cl = (CardLayout)(mainPanel).getLayout();
        cl.show(mainPanel, "report");
        loadDashboardData();
    }//GEN-LAST:event_btnReportActionPerformed

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

    private void btnInventoryActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnInventoryActionPerformed
        CardLayout cl = (CardLayout)(mainPanel).getLayout();
        cl.show(mainPanel, "inventory");
        loadIngredientTable();
    }//GEN-LAST:event_btnInventoryActionPerformed

    private void txtCurrentPasswordActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCurrentPasswordActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCurrentPasswordActionPerformed

    private void btnSubmitRequestActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSubmitRequestActionPerformed
        String currentPlaceholder = "Enter Current Password";
        String newPlaceholder = "Enter New Password";
        String confirmPlaceholder = "Confirm New Password";

        // Get password values
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

        // Minimum password length
        int minLength = 6;
        if(newPass.length() < minLength || confirmPass.length() < minLength) {
            JOptionPane.showMessageDialog(this, "Password must be at least " + minLength + " characters!");
            return;
        }

        // Verify current password
        UserController controller = new UserController();
        boolean isCurrentValid = controller.verifyPassword(currentUser.getUserName(), currentPass);

        if(!isCurrentValid) {
            JOptionPane.showMessageDialog(this, "Current password is incorrect!");
            return;
        }

        // Check if new passwords match
        if(!newPass.equals(confirmPass)) {
            JOptionPane.showMessageDialog(this, "New passwords do not match!");
            return;
        }

        // Confirm action
        int confirm = JOptionPane.showConfirmDialog(
            this,
            "Are you sure you want to change your password?",
            "Confirm Password Change",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.QUESTION_MESSAGE
        );
        if(confirm != JOptionPane.YES_OPTION) return;

        // Update password directly (hashed inside updatePassword)
        controller.updatePassword(currentUser.getUserId(), newPass);

        JOptionPane.showMessageDialog(this, "Password changed successfully!");

        // Clear fields
        txtCurrentPassword.setText("");
        txtNewPassword.setText("");
        txtConfirmPassword.setText("");
    }//GEN-LAST:event_btnSubmitRequestActionPerformed

    private void btnAddMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddMenuActionPerformed
        addMenu form = new addMenu(null); // null means bagong menu

        form.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosed(java.awt.event.WindowEvent e) {
                loadMenuTable(); 
            }
        });

        form.setLocationRelativeTo(this); 
        form.setResizable(false);
        form.setVisible(true);
    }//GEN-LAST:event_btnAddMenuActionPerformed

    private void btnAddStaffActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddStaffActionPerformed
        addStaff addStaffFrame = new addStaff();

        addStaffFrame.setStaffAddedListener(() -> {
            loadUserTable(); 
        });
        addStaffFrame.setLocationRelativeTo(this);
        addStaffFrame.setResizable(false);

        addStaffFrame.setVisible(true);
    }//GEN-LAST:event_btnAddStaffActionPerformed

    private void btnRequestActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRequestActionPerformed
        ApprovalPanel approvalPanel = new ApprovalPanel(mainPanel);

        mainPanel.add(approvalPanel, "approval");

        CardLayout cl = (CardLayout) mainPanel.getLayout();
        cl.show(mainPanel, "approval");
    }//GEN-LAST:event_btnRequestActionPerformed

    private void btnAddIngredientActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddIngredientActionPerformed
        addIngredient addIngFrame = new addIngredient();
    
        addIngFrame.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosed(java.awt.event.WindowEvent e) {
                loadIngredientTable(); 
            }
        });

        addIngFrame.setVisible(true);
    }//GEN-LAST:event_btnAddIngredientActionPerformed

    private void searchSoldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchSoldActionPerformed
        loadSold(searchMenu.getText().trim());      
    }//GEN-LAST:event_searchSoldActionPerformed

    private void searchUserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchUserActionPerformed
        loadUserTable(searchUser.getText().trim());
    }//GEN-LAST:event_searchUserActionPerformed

    private void searchMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchMenuActionPerformed
        loadMenuTable(searchMenu.getText().trim());
    }//GEN-LAST:event_searchMenuActionPerformed

    private void searchInventoryActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchInventoryActionPerformed
        loadIngredientTable(searchInventory.getText().trim());      
    }//GEN-LAST:event_searchInventoryActionPerformed

    private void searchInventoryKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_searchInventoryKeyReleased
        loadIngredientTable(searchInventory.getText().trim());
    }//GEN-LAST:event_searchInventoryKeyReleased

    private void searchSoldKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_searchSoldKeyReleased
        loadSold(searchSold.getText().trim());
    }//GEN-LAST:event_searchSoldKeyReleased

    private void searchMenuKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_searchMenuKeyReleased
        loadMenuTable(searchMenu.getText().trim());
    }//GEN-LAST:event_searchMenuKeyReleased

    private void searchUserKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_searchUserKeyReleased
        loadUserTable(searchUser.getText().trim());
    }//GEN-LAST:event_searchUserKeyReleased

    public static void main(String args[]) {

        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                  new login().setVisible(true);
            }
        });
    }
    private javax.swing.JPanel notifPopupPanel;
    private boolean notifVisible = false;
    private int pendingCount = 0;

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAddIngredient;
    private javax.swing.JButton btnAddMenu;
    private javax.swing.JButton btnAddStaff;
    private javax.swing.JButton btnHome;
    private javax.swing.JButton btnInventory;
    private javax.swing.JButton btnLogout;
    private javax.swing.JButton btnMenu;
    private javax.swing.JButton btnReport;
    private javax.swing.JButton btnRequest;
    private javax.swing.JButton btnSettings;
    private javax.swing.JButton btnSubmitRequest;
    private javax.swing.JButton btnUser;
    private javax.swing.JPanel changePasswordPanel;
    private javax.swing.JPanel dashboardPanel;
    private javax.swing.JLabel fullName;
    private javax.swing.JLabel fullName1;
    private javax.swing.JPanel inerPanel;
    private javax.swing.JPanel inerPanel3;
    private javax.swing.JPanel inerPanel5;
    private javax.swing.JTable ingredientTable;
    private javax.swing.JScrollPane ingredients;
    private javax.swing.JPanel inventoryPanel;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JPanel lowPanel;
    private javax.swing.JPanel lowStockItem;
    private javax.swing.JPanel mainPanel;
    private javax.swing.JPanel manuPanel;
    private javax.swing.JButton notif;
    private javax.swing.JLabel numNotif;
    private javax.swing.JLabel ord_icon;
    private javax.swing.JLabel order_served;
    private javax.swing.JLabel orders_icon;
    private javax.swing.JLabel pendingtotal5;
    private javax.swing.JLabel pendingtotal6;
    private javax.swing.JLabel pendingtotal7;
    private javax.swing.JLabel pendingtotal8;
    private javax.swing.JPanel reportPanel;
    private javax.swing.JLabel role;
    private javax.swing.JPanel salesBar;
    private javax.swing.JPanel salesPanel;
    private javax.swing.JLabel sales_icon;
    private javax.swing.JTextField searchInventory;
    private javax.swing.JTextField searchMenu;
    private javax.swing.JTextField searchSold;
    private javax.swing.JTextField searchUser;
    private javax.swing.JPanel servedOrders;
    private javax.swing.JPanel settingsPanel;
    private javax.swing.JPanel sideBar;
    private javax.swing.JPanel staffInformationPanel;
    private javax.swing.JLabel stock_icon;
    private javax.swing.JTable tblMenu;
    private javax.swing.JTable tblSold;
    private javax.swing.JPanel top;
    private javax.swing.JPanel topProduct;
    private javax.swing.JPanel totalOrders;
    private javax.swing.JLabel total_Sales;
    private javax.swing.JLabel total_low;
    private javax.swing.JLabel total_orders;
    private javax.swing.JPasswordField txtConfirmPassword;
    private javax.swing.JPasswordField txtCurrentPassword;
    private javax.swing.JPasswordField txtNewPassword;
    private javax.swing.JPanel userPanel;
    private javax.swing.JTable userTable;
    private javax.swing.JPanel weakly;
    // End of variables declaration//GEN-END:variables
}
