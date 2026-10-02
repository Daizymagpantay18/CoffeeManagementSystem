/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package views.Admin;

import controllers.IngredientController;
import controllers.ProductController;
import java.awt.CardLayout;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import models.Categories;
import models.Ingredient;
import java.util.HashMap;
import java.util.ArrayList;
import models.Product;

public class addMenu extends javax.swing.JFrame {
    private Product editingProduct;
    HashMap<String, ArrayList<Object[]>> sizeIngredients = new HashMap<>();
    public addMenu(){
        initComponents();
        setupTable();
        loadCategories();
        loadDefaultSizes();
        loadIngredientsToComboBox();
        
        productIngredients.setModel(new DefaultTableModel(
            new Object[][]{},
            new String[]{"Ingredient", "Qty", "Unit", "Action"}
        ));
        
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);

        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {

                int confirm = JOptionPane.showConfirmDialog(
                    null,
                    "Cancel adding product?",
                    "Confirm",
                    JOptionPane.YES_NO_OPTION
                );

                if(confirm == JOptionPane.YES_OPTION){
                    dispose();
                }
            }
        });
    }
    public addMenu(Product product){
        this(); 

        this.editingProduct = product;

        if(product != null){

            txtProductName.setText(product.getName());
            txtDescription.setText(product.getDescription());
            selectCategory.setSelectedItem(product.getCategory());
            
            

            if(product.getStatus().equalsIgnoreCase("Available")){
                available.setSelected(true);
            }else{
                unAvailable.setSelected(true);
            }

            ProductController controller = new ProductController();

            DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
            model.setRowCount(0);

            List<Object[]> sizes = controller.getSizes(product.getProductId());

            for(Object[] row : sizes){
                model.addRow(new Object[]{
                    row[0], 
                    row[1], 
                    "Delete"
                });
            }

            for(Object[] row : sizes){

                String size = row[0].toString().trim();

                List<Object[]> ingredients = controller.getIngredients(product.getProductId(), size);

                ArrayList<Object[]> list = new ArrayList<>();

                for(Object[] ing : ingredients){

                    Object[] ingredientRow = new Object[]{
                        ing[0], 
                        ing[1],
                        ing[2], 
                        "Delete"
                    };

                    list.add(ingredientRow);
                }

                sizeIngredients.put(size, list);
            }

            loadSizesToComboBox();
            if(selectSize.getItemCount() > 0){
                selectSize.setSelectedIndex(0);
            }
        }
    }
    private void loadCategories(){
        ProductController controller = new ProductController();

        selectCategory.removeAllItems(); 

        for(String category : controller.getCategories()){
            selectCategory.addItem(category);
        }
    }
    
    private void setupTable(){

        DefaultTableModel model = new DefaultTableModel(
            new Object [][] {},
            new String [] {"Size", "Price (₱)", "Action"}
        ){
            @Override
            public boolean isCellEditable(int row, int column){
                return column != 2; 
            }
        };

        jTable1.setModel(model);
    }
    private void loadDefaultSizes(){

        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();

        model.addRow(new Object[]{"Small", "", "Delete"});
        model.addRow(new Object[]{"Medium", "", "Delete"});
        model.addRow(new Object[]{"Large", "", "Delete"});

    }
    
    private boolean validateSizesBeforeSave() {
        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
        
        if(model.getRowCount() == 0){
            JOptionPane.showMessageDialog(this,"You must add at least one size!");
            return false;
        }
        java.util.Set<String> sizeSet = new java.util.HashSet<>();
        java.util.Set<Integer> priceSet = new java.util.HashSet<>();

        for (int i = 0; i < model.getRowCount(); i++) {
            String size = model.getValueAt(i, 0).toString().trim();
            String priceCell = String.valueOf(model.getValueAt(i, 1)).trim();

            if (priceCell.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter price for size: " + size);
                return false;
            }

            int price = 0;
            try {
                price = Integer.parseInt(priceCell);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Price must be a number for size: " + size);
                return false;
            }

            if (price <= 0) {
                JOptionPane.showMessageDialog(this, "Price must be greater than zero for size: " + size);
                return false;
            }

            if (price > 5000) {
                JOptionPane.showMessageDialog(this, "Price cannot exceed ₱5000 for size: " + size);
                return false;
            }

            if (sizeSet.contains(size.toLowerCase())) {
                JOptionPane.showMessageDialog(this, "Duplicate size found: " + size);
                return false;
            } else {
                sizeSet.add(size.toLowerCase());
            }

            if (priceSet.contains(price)) {
                JOptionPane.showMessageDialog(this, "Another size already has price: ₱" + price);
                return false;
            } else {
                priceSet.add(price);
            }
            
            
        }

        return true; 
    }
    private void loadSizesToComboBox(){

        selectSize.removeAllItems();

        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();

        for(int i = 0; i < model.getRowCount(); i++){

            String size = model.getValueAt(i,0).toString().trim();

            selectSize.addItem(size);
        }
    }
    private void loadIngredientsToComboBox() {
        ingredientsAvailable.removeAllItems(); 

        IngredientController ic = new IngredientController();
        List<Ingredient> allIngredients = ic.getAllIngredients();

        for (Ingredient ing : allIngredients) {
            ingredientsAvailable.addItem(ing.getName());
        }
    }
    private boolean validateIngredientsForAllSizes() {

        DefaultTableModel sizeModel = (DefaultTableModel) jTable1.getModel();

        for (int i = 0; i < sizeModel.getRowCount(); i++) {

            String size = sizeModel.getValueAt(i, 0).toString();

            if (!sizeIngredients.containsKey(size) || sizeIngredients.get(size).isEmpty()) {
                JOptionPane.showMessageDialog(this,
                        "Size '" + size + "' must have at least one ingredient!");
                return false;
            }
        }

        return true;
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        mainPanel = new javax.swing.JPanel();
        step1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        txtProductName = new javax.swing.JTextField();
        selectCategory = new javax.swing.JComboBox<>();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtDescription = new javax.swing.JTextArea();
        jLabel6 = new javax.swing.JLabel();
        available = new javax.swing.JCheckBox();
        unAvailable = new javax.swing.JCheckBox();
        btnNext1 = new javax.swing.JButton();
        jLabel12 = new javax.swing.JLabel();
        txtCategoryName = new javax.swing.JTextField();
        btnCategoryAdd = new javax.swing.JButton();
        step2 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        sizeTable = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        txtSizeName = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        txtPrice = new javax.swing.JTextField();
        btnAddSize = new javax.swing.JButton();
        jLabel14 = new javax.swing.JLabel();
        btnNext2 = new javax.swing.JButton();
        btnBack = new javax.swing.JButton();
        step3 = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        btnSaveProduct = new javax.swing.JButton();
        btnBack1 = new javax.swing.JButton();
        selectSize = new javax.swing.JComboBox<>();
        jLabel13 = new javax.swing.JLabel();
        sizeTable1 = new javax.swing.JScrollPane();
        productIngredients = new javax.swing.JTable();
        ingredientsAvailable = new javax.swing.JComboBox<>();
        jLabel15 = new javax.swing.JLabel();
        txtQuantity = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        btnAddIngredients = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel17 = new javax.swing.JLabel();
        txtUnitIngredients = new javax.swing.JComboBox<>();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(245, 234, 219));

        mainPanel.setBackground(new java.awt.Color(248, 242, 226));
        mainPanel.setPreferredSize(new java.awt.Dimension(774, 700));
        mainPanel.setLayout(new java.awt.CardLayout());

        jLabel1.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        jLabel1.setText("Add New Product");

        jLabel2.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel2.setText("Step 1 of 3");

        jLabel3.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        jLabel3.setText("Product Name *");

        txtProductName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtProductNameActionPerformed(evt);
            }
        });

        selectCategory.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                selectCategoryActionPerformed(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        jLabel4.setText("Category *");

        jLabel5.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        jLabel5.setText("Description");

        txtDescription.setColumns(20);
        txtDescription.setRows(5);
        jScrollPane1.setViewportView(txtDescription);

        jLabel6.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        jLabel6.setText("Status");

        available.setText("Available");
        available.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                availableActionPerformed(evt);
            }
        });

        unAvailable.setText("Unavailable");
        unAvailable.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                unAvailableActionPerformed(evt);
            }
        });

        btnNext1.setText("Next");
        btnNext1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNext1ActionPerformed(evt);
            }
        });

        jLabel12.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        jLabel12.setText("Category Name");

        txtCategoryName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCategoryNameActionPerformed(evt);
            }
        });

        btnCategoryAdd.setText("Add");
        btnCategoryAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCategoryAddActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout step1Layout = new javax.swing.GroupLayout(step1);
        step1.setLayout(step1Layout);
        step1Layout.setHorizontalGroup(
            step1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(step1Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(step1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btnNext1, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(step1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(txtProductName, javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(selectCategory, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(step1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(step1Layout.createSequentialGroup()
                                .addComponent(available)
                                .addGap(18, 18, 18)
                                .addComponent(unAvailable))
                            .addComponent(jLabel6)
                            .addComponent(jLabel12)
                            .addComponent(jLabel4)
                            .addComponent(jLabel3)
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 285, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel5)
                            .addGroup(step1Layout.createSequentialGroup()
                                .addComponent(txtCategoryName, javax.swing.GroupLayout.PREFERRED_SIZE, 438, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btnCategoryAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addGap(18, 18, 18))
        );
        step1Layout.setVerticalGroup(
            step1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(step1Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2)
                .addGap(31, 31, 31)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtProductName, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel4)
                .addGroup(step1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(step1Layout.createSequentialGroup()
                        .addGap(56, 56, 56)
                        .addComponent(jLabel12)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(step1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtCategoryName, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnCategoryAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(step1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(selectCategory, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(step1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(available)
                    .addComponent(unAvailable))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnNext1, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        mainPanel.add(step1, "step1");

        jLabel7.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        jLabel7.setText("Add New Product");

        jLabel8.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel8.setText("Step 2 of 3");

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Size", "Price (₱)", ""
            }
        ));
        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable1MouseClicked(evt);
            }
        });
        sizeTable.setViewportView(jTable1);

        txtSizeName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtSizeNameActionPerformed(evt);
            }
        });

        jLabel9.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        jLabel9.setText("Size Name");

        txtPrice.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtPriceActionPerformed(evt);
            }
        });

        btnAddSize.setText("Add");
        btnAddSize.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddSizeActionPerformed(evt);
            }
        });

        jLabel14.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        jLabel14.setText("Price");

        btnNext2.setText("Next");
        btnNext2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNext2ActionPerformed(evt);
            }
        });

        btnBack.setText("Back");
        btnBack.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBackActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout step2Layout = new javax.swing.GroupLayout(step2);
        step2.setLayout(step2Layout);
        step2Layout.setHorizontalGroup(
            step2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(step2Layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addGroup(step2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 285, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(sizeTable, javax.swing.GroupLayout.PREFERRED_SIZE, 549, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(step2Layout.createSequentialGroup()
                        .addGroup(step2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel9)
                            .addComponent(txtSizeName, javax.swing.GroupLayout.PREFERRED_SIZE, 324, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel14))
                    .addGroup(step2Layout.createSequentialGroup()
                        .addGap(329, 329, 329)
                        .addGroup(step2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtPrice, javax.swing.GroupLayout.DEFAULT_SIZE, 103, Short.MAX_VALUE)
                            .addComponent(btnBack, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(step2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(btnAddSize, javax.swing.GroupLayout.DEFAULT_SIZE, 103, Short.MAX_VALUE)
                            .addComponent(btnNext2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap(23, Short.MAX_VALUE))
        );
        step2Layout.setVerticalGroup(
            step2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(step2Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel8)
                .addGap(18, 18, 18)
                .addComponent(sizeTable, javax.swing.GroupLayout.PREFERRED_SIZE, 199, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(24, 24, 24)
                .addGroup(step2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(step2Layout.createSequentialGroup()
                        .addComponent(jLabel9)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtSizeName, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(step2Layout.createSequentialGroup()
                        .addComponent(jLabel14)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(step2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtPrice, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnAddSize, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(27, 27, 27)
                        .addGroup(step2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnBack, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnNext2, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        mainPanel.add(step2, "step2");

        jLabel10.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        jLabel10.setText("Add New Product");

        jLabel11.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel11.setText("Step 3 of 3");

        btnSaveProduct.setText("Save Product");
        btnSaveProduct.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveProductActionPerformed(evt);
            }
        });

        btnBack1.setText("Back");
        btnBack1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBack1ActionPerformed(evt);
            }
        });

        selectSize.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                selectSizeActionPerformed(evt);
            }
        });

        jLabel13.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        jLabel13.setText("Select Size");

        productIngredients.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Ingredient", "Qty", "Unit", ""
            }
        ));
        productIngredients.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                productIngredientsMouseClicked(evt);
            }
        });
        sizeTable1.setViewportView(productIngredients);

        ingredientsAvailable.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ingredientsAvailableActionPerformed(evt);
            }
        });

        jLabel15.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        jLabel15.setText("Ingredient");

        txtQuantity.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtQuantityActionPerformed(evt);
            }
        });

        jLabel16.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        jLabel16.setText("Qty");

        btnAddIngredients.setText("Add");
        btnAddIngredients.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddIngredientsActionPerformed(evt);
            }
        });

        jLabel17.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        jLabel17.setText("Unit");

        txtUnitIngredients.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "ml", "L", "g", "kg", "pcs", "pack", "box", "oz" }));

        javax.swing.GroupLayout step3Layout = new javax.swing.GroupLayout(step3);
        step3.setLayout(step3Layout);
        step3Layout.setHorizontalGroup(
            step3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(step3Layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addGroup(step3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(step3Layout.createSequentialGroup()
                        .addGroup(step3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 285, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(step3Layout.createSequentialGroup()
                                .addComponent(ingredientsAvailable, javax.swing.GroupLayout.PREFERRED_SIZE, 227, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(step3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(step3Layout.createSequentialGroup()
                                        .addGap(35, 35, 35)
                                        .addComponent(jLabel16))
                                    .addGroup(step3Layout.createSequentialGroup()
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(txtQuantity, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGroup(step3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(step3Layout.createSequentialGroup()
                                        .addGap(35, 35, 35)
                                        .addComponent(jLabel17))
                                    .addGroup(step3Layout.createSequentialGroup()
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(txtUnitIngredients, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addComponent(selectSize, javax.swing.GroupLayout.PREFERRED_SIZE, 537, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(step3Layout.createSequentialGroup()
                        .addGap(269, 269, 269)
                        .addComponent(btnBack1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnSaveProduct, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(36, 36, 36))
                    .addGroup(step3Layout.createSequentialGroup()
                        .addGroup(step3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel15)
                            .addComponent(jLabel13)
                            .addComponent(btnAddIngredients, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(sizeTable1, javax.swing.GroupLayout.DEFAULT_SIZE, 537, Short.MAX_VALUE)
                            .addComponent(jSeparator1))
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        step3Layout.setVerticalGroup(
            step3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(step3Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(jLabel10)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel11)
                .addGap(35, 35, 35)
                .addComponent(jLabel13)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(selectSize, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(14, 14, 14)
                .addComponent(sizeTable1, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(step3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel15)
                    .addComponent(jLabel16)
                    .addComponent(jLabel17))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(step3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(ingredientsAvailable, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtQuantity, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtUnitIngredients, javax.swing.GroupLayout.DEFAULT_SIZE, 35, Short.MAX_VALUE)
                    .addComponent(btnAddIngredients, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(25, 25, 25)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 64, Short.MAX_VALUE)
                .addGroup(step3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnSaveProduct, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnBack1, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(32, 32, 32))
        );

        mainPanel.add(step3, "step3");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(mainPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 595, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(mainPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 479, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtProductNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtProductNameActionPerformed
        String name = txtProductName.getText().trim();

        if(name.isEmpty()){
            JOptionPane.showMessageDialog(this, "Product name is required!");
            txtProductName.requestFocus();
            return;
        }
        if(name.length() > 50){
            JOptionPane.showMessageDialog(this, "Product name cannot exceed 50 characters!");
            txtProductName.requestFocus();
            return;
        }

        ProductController controller = new ProductController();
        if(controller.productExists(name)){
            JOptionPane.showMessageDialog(this, "Product already exists!");
            txtProductName.setText("");
            txtProductName.requestFocus();
        }
    }//GEN-LAST:event_txtProductNameActionPerformed

    private void availableActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_availableActionPerformed
        if (available.isSelected()) {
            unAvailable.setSelected(false);
        }
    }//GEN-LAST:event_availableActionPerformed

    private void unAvailableActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_unAvailableActionPerformed
        if (unAvailable.isSelected()) {
            available.setSelected(false);
        }
    }//GEN-LAST:event_unAvailableActionPerformed

    private void txtSizeNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtSizeNameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtSizeNameActionPerformed

    private void txtPriceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtPriceActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtPriceActionPerformed

    private void btnNext2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNext2ActionPerformed
        if (!validateSizesBeforeSave()) {
            return;
        }
        loadSizesToComboBox();
        loadIngredientsToComboBox();
        
        CardLayout cl = (CardLayout) mainPanel.getLayout();
        cl.show(mainPanel, "step3");
    }//GEN-LAST:event_btnNext2ActionPerformed

    private void btnBackActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBackActionPerformed
        CardLayout cl = (CardLayout) mainPanel.getLayout();
        cl.show(mainPanel, "step1");
    }//GEN-LAST:event_btnBackActionPerformed

    private void btnBack1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBack1ActionPerformed
        CardLayout cl = (CardLayout) mainPanel.getLayout();
        cl.show(mainPanel, "step2");
    }//GEN-LAST:event_btnBack1ActionPerformed

    private void btnNext1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNext1ActionPerformed
        String productName = txtProductName.getText().trim();
        String category = (String) selectCategory.getSelectedItem();

        if(productName.isEmpty()){
            JOptionPane.showMessageDialog(this, "Product name is required!");
            txtProductName.requestFocus();
            return;
        }

        if(productName.length() > 50){
            JOptionPane.showMessageDialog(this, "Product name cannot exceed 50 characters!");
            txtProductName.requestFocus();
            return;
        }

        if(selectCategory.getItemCount() == 0){
            JOptionPane.showMessageDialog(this, "Please add a category first before continuing.");
            txtCategoryName.requestFocus();
            return;
        }

        if(category == null || category.trim().isEmpty()){
            JOptionPane.showMessageDialog(this, "Please select a category!");
            selectCategory.requestFocus();
            return;
        }

        if(!available.isSelected() && !unAvailable.isSelected()){
            JOptionPane.showMessageDialog(this, "Please select product status!");
            return;
        }

        CardLayout cl = (CardLayout) mainPanel.getLayout();
        cl.show(mainPanel, "step2");
    }//GEN-LAST:event_btnNext1ActionPerformed

    private void txtCategoryNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCategoryNameActionPerformed
        String category = txtCategoryName.getText().trim();

        if(category.length() > 50){
            JOptionPane.showMessageDialog(this, "Category cannot exceed 50 characters!");
            txtCategoryName.requestFocus();
            return;
        }

        ProductController controller = new ProductController();
        if(controller.categoryExists(category)){
            JOptionPane.showMessageDialog(this, "Category already exists!");
            txtCategoryName.setText("");
            txtCategoryName.requestFocus();
        }
    }//GEN-LAST:event_txtCategoryNameActionPerformed

    private void txtQuantityActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtQuantityActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtQuantityActionPerformed

    private void btnSaveProductActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveProductActionPerformed

        String productName = txtProductName.getText().trim();
        String category = (String) selectCategory.getSelectedItem();
        String description = txtDescription.getText().trim();
        String status = available.isSelected() ? "Available" : "Unavailable";

        if (!validateSizesBeforeSave()) return;
        if (!validateIngredientsForAllSizes()) return;

        ProductController controller = new ProductController();
        IngredientController ic = new IngredientController();

        boolean productChanged = false;
        boolean sizesChanged = false;
        boolean ingredientsChanged = false;

        if(editingProduct != null){
            if(!editingProduct.getName().equals(productName) ||
               !editingProduct.getCategory().equals(category) ||
               !editingProduct.getDescription().equals(description) ||
               !editingProduct.getStatus().equals(status)) {
                productChanged = true;
            }

            List<Object[]> dbSizes = controller.getSizes(editingProduct.getProductId());
            DefaultTableModel model = (DefaultTableModel) jTable1.getModel();

            if(dbSizes.size() != model.getRowCount()){
                sizesChanged = true;
            } else {
                for(int i = 0; i < model.getRowCount(); i++){
                    String sizeName = model.getValueAt(i, 0).toString();
                    int price = Integer.parseInt(model.getValueAt(i, 1).toString());

                    boolean matchFound = false;
                    for(Object[] dbRow : dbSizes){
                        String dbSize = dbRow[0].toString();
                        int dbPrice = Integer.parseInt(dbRow[1].toString());
                        if(dbSize.equalsIgnoreCase(sizeName) && dbPrice == price){
                            matchFound = true;
                            break;
                        }
                    }
                    if(!matchFound){
                        sizesChanged = true;
                        break;
                    }
                }
            }

            for(String size : sizeIngredients.keySet()){
                List<Object[]> dbIngredients = controller.getIngredients(editingProduct.getProductId(), size);
                ArrayList<Object[]> uiIngredients = sizeIngredients.get(size);

                if(dbIngredients.size() != uiIngredients.size()){
                    ingredientsChanged = true;
                    break;
                }

                for(Object[] uiRow : uiIngredients){
                    boolean matchFound = false;
                    for(Object[] dbRow : dbIngredients){
                        String dbIng = dbRow[0].toString();
                        double dbQty = Double.parseDouble(dbRow[1].toString());
                        String dbUnit = dbRow[2].toString();

                        if(dbIng.equalsIgnoreCase(uiRow[0].toString()) &&
                           dbQty == Double.parseDouble(uiRow[1].toString()) &&
                           dbUnit.equals(uiRow[2].toString())){
                            matchFound = true;
                            break;
                        }
                    }
                    if(!matchFound){
                        ingredientsChanged = true;
                        break;
                    }
                }
                if(ingredientsChanged) break;
            }

            if(!productChanged && !sizesChanged && !ingredientsChanged){
                JOptionPane.showMessageDialog(this, "No changes detected. Nothing to save.");
                return;
            }
        } else {
            if(controller.productExists(productName)){
                JOptionPane.showMessageDialog(this, "Product already exists!");
                CardLayout cl = (CardLayout) mainPanel.getLayout();
                cl.show(mainPanel, "step1");
                txtProductName.requestFocus();
                return;
            }
        }

        int confirm = JOptionPane.showConfirmDialog(
            this,
            "Are you sure you want to save this product with all sizes and ingredients?",
            "Confirm Save",
            JOptionPane.YES_NO_OPTION
        );
        if (confirm != JOptionPane.YES_OPTION) return;

        int productId;

        if(editingProduct != null){

            Product updatedProduct = new Product();
            updatedProduct.setProductId(editingProduct.getProductId());
            updatedProduct.setName(productName);
            updatedProduct.setCategory(category);
            updatedProduct.setDescription(description);
            updatedProduct.setStatus(status);

            controller.updateProduct(updatedProduct);

            productId = editingProduct.getProductId();

        }else{

            productId = controller.insertProductGetId(
                new Product(productName, category, description, status)
            );

        }

        if(productId == -1){
            JOptionPane.showMessageDialog(this, "Failed to save product. Please try again.");
            return;
        }

        // --- SIZES ---
        DefaultTableModel sizeModel = (DefaultTableModel) jTable1.getModel();
        for (int i = 0; i < sizeModel.getRowCount(); i++) {
            String size = sizeModel.getValueAt(i, 0).toString().trim();
            int price = Integer.parseInt(sizeModel.getValueAt(i, 1).toString().trim());
            controller.addOrUpdateProductSize(productId, size, price);
        }

        // --- INGREDIENTS ---
        for(String size : sizeIngredients.keySet()){
            int sizeId = controller.getSizeId(productId, size);
            if(sizeId == -1){
                JOptionPane.showMessageDialog(this,"Error retrieving size: "+size);
                return;
            }

            ArrayList<Object[]> ingredientsList = sizeIngredients.get(size);

            for(Object[] row : ingredientsList){
                String ingredientName = row[0].toString();
                double quantity = Double.parseDouble(row[1].toString());
                String unit = row[2].toString();

                int ingredientId = ic.getIngredientIdByName(ingredientName);
                if(ingredientId == -1){
                    JOptionPane.showMessageDialog(this, "Ingredient not found: " + ingredientName);
                    return;
                }

                controller.addOrUpdateRecipe(sizeId, ingredientId, quantity, unit);
            }
        }

        JOptionPane.showMessageDialog(this, "Product saved successfully!");
        this.dispose();

    }//GEN-LAST:event_btnSaveProductActionPerformed

    private void selectCategoryActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_selectCategoryActionPerformed
        
    }//GEN-LAST:event_selectCategoryActionPerformed

    private void btnCategoryAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCategoryAddActionPerformed
        String category = txtCategoryName.getText().trim();
        

        if (category.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Category name cannot be empty!");
            txtCategoryName.requestFocus();
            return;
        }

        if (category.length() > 50) {
            JOptionPane.showMessageDialog(null, "Category cannot exceed 50 characters!");
            txtCategoryName.requestFocus();
            return;
        }

        ProductController controller = new ProductController();

        if (controller.categoryExists(category)) {
            JOptionPane.showMessageDialog(null, "Category already exists!");
            txtCategoryName.setText("");
            txtCategoryName.requestFocus();
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(
                this,
                "Are you sure you want to add this category?\n\nCategory: " + category,
                "Confirm Category",
                JOptionPane.YES_NO_OPTION
        );

        if (confirm != JOptionPane.YES_OPTION) {
            return; 
        }

        Categories c = new Categories();
        c.setName(category);

        if (controller.insertCategory(c)) {
            JOptionPane.showMessageDialog(null, "Category added successfully!");

            loadCategories();
            selectCategory.setSelectedItem(category);

            txtCategoryName.setText("");
        } else {
            JOptionPane.showMessageDialog(null, "Failed to add category. Please try again.");
        }
    }//GEN-LAST:event_btnCategoryAddActionPerformed

    private void btnAddSizeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddSizeActionPerformed
        String size = txtSizeName.getText().trim();
        String priceText = txtPrice.getText().trim();

        if(size.isEmpty() || priceText.isEmpty()){
            JOptionPane.showMessageDialog(this,"Size and price are required!");
            return;
        }

        int price;

        try{
            double tempPrice = Double.parseDouble(priceText);
            if (tempPrice != (int) tempPrice) {
                JOptionPane.showMessageDialog(this,"Price cannot have decimals!");
                return;
            }
            if(tempPrice <= 0){
                JOptionPane.showMessageDialog(this,"Price cannot be zero or negative!");
                return;
            }

            if(tempPrice > 5000){
                JOptionPane.showMessageDialog(this,"Price cannot exceed ₱5000!");
                return;
            }
            price = (int) tempPrice;
        }catch(Exception e){
            JOptionPane.showMessageDialog(this,"Price must be a number!");
            return;
        }

        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();

        for(int i = 0; i < model.getRowCount(); i++){

            String existingSize = model.getValueAt(i,0).toString();
            String priceCell = model.getValueAt(i,1).toString();

            int existingPrice = 0;

            if(!priceCell.isEmpty()){
                existingPrice = Integer.parseInt(priceCell);
            }

            if(existingSize.equalsIgnoreCase(size)){
                JOptionPane.showMessageDialog(this,"Size already exists!");
                return;
            }

            // duplicate price
            if(existingPrice == price){
                JOptionPane.showMessageDialog(this,"Another size already has this price!");
                return;
            }
        }

        model.addRow(new Object[]{size, price, "Delete"});

        txtSizeName.setText("");
        txtPrice.setText("");
    }//GEN-LAST:event_btnAddSizeActionPerformed

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked
        int row = jTable1.getSelectedRow();
        int col = jTable1.getSelectedColumn();
        
        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
        if(model.getRowCount() == 0){
            loadDefaultSizes();
        }

        if(col == 2){ 

            int confirm = JOptionPane.showConfirmDialog(
                null,
                "Delete this size?",
                "Confirm",
                JOptionPane.YES_NO_OPTION
            );

            if(confirm == JOptionPane.YES_OPTION){
                model.removeRow(row);
            }
        }
    }//GEN-LAST:event_jTable1MouseClicked

    private void btnAddIngredientsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddIngredientsActionPerformed
        String selectedIngredient = (String) ingredientsAvailable.getSelectedItem();
        String quantityText = txtQuantity.getText().trim();

        if (selectedIngredient == null || selectedIngredient.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please select an ingredient!");
            return;
        }

        if (quantityText.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter quantity!");
            txtQuantity.requestFocus();
            return;
        }

        double quantity = 0;
        try {
            quantity = Double.parseDouble(quantityText);
            if (quantity <= 0) {
                JOptionPane.showMessageDialog(this, "Quantity must be greater than zero!");
                txtQuantity.requestFocus();
                return;
            }
            if (quantity > 5000) {
                JOptionPane.showMessageDialog(this, "Quantity cannot exceed 5000!");
                txtQuantity.requestFocus();
                return;
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Quantity must be a number!");
            txtQuantity.requestFocus();
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(
            this,
            "Add ingredient?\n\nIngredient: " + selectedIngredient + "\nQuantity: " + quantity,
            "Confirm Add",
            JOptionPane.YES_NO_OPTION
        );

        if (confirm != JOptionPane.YES_OPTION) {
            return; 
        }

        DefaultTableModel model = (DefaultTableModel) productIngredients.getModel();
        String size = (String) selectSize.getSelectedItem();

        if(!sizeIngredients.containsKey(size)){
            sizeIngredients.put(size, new ArrayList<>());
        }

        ArrayList<Object[]> ingredientList = sizeIngredients.get(size);

        for(Object[] row : ingredientList){
            String existingIngredient = row[0].toString();
            if(existingIngredient.equalsIgnoreCase(selectedIngredient)){
                JOptionPane.showMessageDialog(this, "This ingredient has already been added for this size!");
                return;
            }
        }

        String unit = (String) txtUnitIngredients.getSelectedItem(); 
        if (unit.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please select a unit!");
            txtUnitIngredients.requestFocus();
            return;
        }

        Object[] row = {selectedIngredient, quantity, unit, "Delete"};

        sizeIngredients.get(size).add(row);
        model.addRow(row);
        txtQuantity.setText("");
        txtUnitIngredients.setSelectedIndex(0);
    }//GEN-LAST:event_btnAddIngredientsActionPerformed

    private void ingredientsAvailableActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ingredientsAvailableActionPerformed
        
    }//GEN-LAST:event_ingredientsAvailableActionPerformed

    private void selectSizeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_selectSizeActionPerformed
        String size = (String) selectSize.getSelectedItem();

        DefaultTableModel model = (DefaultTableModel) productIngredients.getModel();
        model.setRowCount(0);

        ArrayList<Object[]> list = sizeIngredients.get(size);

        if(list != null){
            for(Object[] row : list){
                model.addRow(row);
            }
        }
    }//GEN-LAST:event_selectSizeActionPerformed

    private void productIngredientsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_productIngredientsMouseClicked
        int row = productIngredients.getSelectedRow();
        int col = productIngredients.getSelectedColumn();
        
        DefaultTableModel model = (DefaultTableModel) productIngredients.getModel();
        if(model.getRowCount() == 0){
            loadIngredientsToComboBox();
        }

        if(col == 3){ 

            int confirm = JOptionPane.showConfirmDialog(
                null,
                "Delete this ingredients?",
                "Confirm",
                JOptionPane.YES_NO_OPTION
            );

            if(confirm == JOptionPane.YES_OPTION){
                model.removeRow(row);
            }
        }
    }//GEN-LAST:event_productIngredientsMouseClicked

    public static void main(String args[]) {

        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                  new addMenu().setVisible(true);
            }
        });
    }
    private javax.swing.JPanel notifPopupPanel;
    private boolean notifVisible = false;
    private int pendingCount = 0;

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JCheckBox available;
    private javax.swing.JButton btnAddIngredients;
    private javax.swing.JButton btnAddSize;
    private javax.swing.JButton btnBack;
    private javax.swing.JButton btnBack1;
    private javax.swing.JButton btnCategoryAdd;
    private javax.swing.JButton btnNext1;
    private javax.swing.JButton btnNext2;
    private javax.swing.JButton btnSaveProduct;
    private javax.swing.JComboBox<String> ingredientsAvailable;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTable jTable1;
    private javax.swing.JPanel mainPanel;
    private javax.swing.JTable productIngredients;
    private javax.swing.JComboBox<String> selectCategory;
    private javax.swing.JComboBox<String> selectSize;
    private javax.swing.JScrollPane sizeTable;
    private javax.swing.JScrollPane sizeTable1;
    private javax.swing.JPanel step1;
    private javax.swing.JPanel step2;
    private javax.swing.JPanel step3;
    private javax.swing.JTextField txtCategoryName;
    private javax.swing.JTextArea txtDescription;
    private javax.swing.JTextField txtPrice;
    private javax.swing.JTextField txtProductName;
    private javax.swing.JTextField txtQuantity;
    private javax.swing.JTextField txtSizeName;
    private javax.swing.JComboBox<String> txtUnitIngredients;
    private javax.swing.JCheckBox unAvailable;
    // End of variables declaration//GEN-END:variables
}
