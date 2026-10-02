/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package views.Admin;

import controllers.IngredientController;
import javax.swing.JOptionPane;
import models.Ingredient;

public class addIngredient extends javax.swing.JFrame {
    private Ingredient ingredientToEdit = null;
    
    public addIngredient() {
        initComponents();
        
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);

        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {

                int confirm = JOptionPane.showConfirmDialog(
                    null,
                    "Cancel ingredient product?",
                    "Confirm",
                    JOptionPane.YES_NO_OPTION
                );

                if(confirm == JOptionPane.YES_OPTION){
                    dispose();
                }
            }
        });
    }
    public addIngredient(Ingredient ingredient) {  
        this.ingredientToEdit = ingredient;
        initComponents();
        populateFields(ingredient);
        
        jLabel1.setText("Edit Ingredient");
        btnSave.setText("Update");
        
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);

        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {

                int confirm = JOptionPane.showConfirmDialog(
                    null,
                    "Cancel ingredient product?",
                    "Confirm",
                    JOptionPane.YES_NO_OPTION
                );

                if(confirm == JOptionPane.YES_OPTION){
                    dispose();
                }
            }
        });
    }

    private void populateFields(Ingredient i) { 
        txtIngredientName.setText(i.getName());
        roles.setSelectedItem(i.getUnit());
        txtPricePerUnit.setText(String.valueOf(i.getPricePerUnit()));
        txtInitialStock.setText(String.valueOf(i.getStockQty()));
        txtLowStock.setText(String.valueOf(i.getLowStockThreshold()));
    }
    private boolean validateIngredientsBeforeSave(){

        String name = txtIngredientName.getText().trim().toLowerCase();
        String priceText = txtPricePerUnit.getText().trim();
        String stockText = txtInitialStock.getText().trim();
        String thresholdText = txtLowStock.getText().trim();

        if(name.isEmpty() || priceText.isEmpty() || stockText.isEmpty() || thresholdText.isEmpty()){
            JOptionPane.showMessageDialog(this, "All fields are required.");
            return false;
        }

        IngredientController ic = new IngredientController();

        // duplicate check only for ADD
        if(ingredientToEdit == null && ic.isIngredientExists(name)){
            JOptionPane.showMessageDialog(this, "Ingredient name already exists.");
            return false;
        }

        try{
            int price = Integer.parseInt(priceText);

            if(price < 0){
                JOptionPane.showMessageDialog(this, "Price cannot be negative.");
                return false;
            }

            if(price > 5000){
                JOptionPane.showMessageDialog(this, "Price cannot exceed 5000.");
                return false;
            }

        }catch(NumberFormatException e){
            JOptionPane.showMessageDialog(this, "Invalid price value.");
            return false;
        }

        try{
            double stock = Double.parseDouble(stockText);

            if(stock < 0){
                JOptionPane.showMessageDialog(this, "Stock cannot be negative.");
                return false;
            }

        }catch(NumberFormatException e){
            JOptionPane.showMessageDialog(this, "Invalid stock value.");
            return false;
        }

        try{
            double threshold = Double.parseDouble(thresholdText);

            if(threshold < 0){
                JOptionPane.showMessageDialog(this, "Threshold cannot be negative.");
                return false;
            }

        }catch(NumberFormatException e){
            JOptionPane.showMessageDialog(this, "Invalid threshold value.");
            return false;
        }

        return true;
    }
    private void insertIngredient(){

        String name = txtIngredientName.getText().trim();
        String unit = roles.getSelectedItem().toString();
        int price = Integer.parseInt(txtPricePerUnit.getText().trim());
        double stock = Double.parseDouble(txtInitialStock.getText().trim());
        double threshold = Double.parseDouble(txtLowStock.getText().trim());

        IngredientController ic = new IngredientController();

        ic.addIngredient(name, unit, price, stock, threshold);

        JOptionPane.showMessageDialog(this, "Ingredient Added Successfully!");
    }
    private void updateIngredientData(){

        String name = txtIngredientName.getText().trim();
        String unit = roles.getSelectedItem().toString();
        int price = Integer.parseInt(txtPricePerUnit.getText().trim());
        double stock = Double.parseDouble(txtInitialStock.getText().trim());
        double threshold = Double.parseDouble(txtLowStock.getText().trim());
        if(name.equalsIgnoreCase(ingredientToEdit.getName()) &&
            unit.equals(ingredientToEdit.getUnit()) &&
            price == ingredientToEdit.getPricePerUnit() &&
            stock == ingredientToEdit.getStockQty() &&
            threshold == ingredientToEdit.getLowStockThreshold()){

             JOptionPane.showMessageDialog(this, "No changes detected.");
             return;
         }

        IngredientController ic = new IngredientController();

        ingredientToEdit.setName(name);
        ingredientToEdit.setUnit(unit);
        ingredientToEdit.setPricePerUnit(price);
        ingredientToEdit.setStockQty(stock);
        ingredientToEdit.setLowStockThreshold(threshold);

        ic.updateIngredient(ingredientToEdit);

        JOptionPane.showMessageDialog(this, "Ingredient Updated Successfully!");
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        mainPanel = new javax.swing.JPanel();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        txtIngredientName = new javax.swing.JTextField();
        roles = new javax.swing.JComboBox<>();
        txtPricePerUnit = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        txtInitialStock = new javax.swing.JTextField();
        txtLowStock = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        btnSave = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        mainPanel.setBackground(new java.awt.Color(245, 234, 219));

        jLabel1.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        jLabel1.setText("Add New Ingredient");

        jLabel2.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel2.setText("Define a new ingredient for your inventory.");

        jLabel4.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        jLabel4.setText("Unit *");

        jLabel3.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        jLabel3.setText("Ingredient Name *");

        txtIngredientName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtIngredientNameActionPerformed(evt);
            }
        });

        roles.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "ml", "L", "g", "kg", "pcs", "pack", "box", "oz" }));
        roles.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rolesActionPerformed(evt);
            }
        });

        txtPricePerUnit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtPricePerUnitActionPerformed(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        jLabel5.setText("Price Per Unit *");

        txtInitialStock.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtInitialStockActionPerformed(evt);
            }
        });

        txtLowStock.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtLowStockActionPerformed(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        jLabel6.setText("Low Stock Threshold *");

        jLabel7.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        jLabel7.setText("Initial Stock *");

        btnSave.setText("Save");
        btnSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout mainPanelLayout = new javax.swing.GroupLayout(mainPanel);
        mainPanel.setLayout(mainPanelLayout);
        mainPanelLayout.setHorizontalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mainPanelLayout.createSequentialGroup()
                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(mainPanelLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jSeparator1, javax.swing.GroupLayout.DEFAULT_SIZE, 416, Short.MAX_VALUE))
                    .addGroup(mainPanelLayout.createSequentialGroup()
                        .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(mainPanelLayout.createSequentialGroup()
                                .addGap(49, 49, 49)
                                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel3)
                                    .addComponent(txtIngredientName, javax.swing.GroupLayout.PREFERRED_SIZE, 330, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(mainPanelLayout.createSequentialGroup()
                                        .addComponent(roles, javax.swing.GroupLayout.PREFERRED_SIZE, 162, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(txtPricePerUnit, javax.swing.GroupLayout.PREFERRED_SIZE, 162, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(mainPanelLayout.createSequentialGroup()
                                        .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel4)
                                            .addComponent(txtInitialStock, javax.swing.GroupLayout.PREFERRED_SIZE, 162, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel7))
                                        .addGap(6, 6, 6)
                                        .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel6)
                                            .addComponent(txtLowStock, javax.swing.GroupLayout.PREFERRED_SIZE, 162, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel5)))
                                    .addComponent(btnSave, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(mainPanelLayout.createSequentialGroup()
                                .addGap(14, 14, 14)
                                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 285, Short.MAX_VALUE)
                                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        mainPanelLayout.setVerticalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mainPanelLayout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2)
                .addGap(16, 16, 16)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(16, 16, 16)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtIngredientName, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(jLabel5))
                .addGap(3, 3, 3)
                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(roles, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtPricePerUnit, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(jLabel6))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtInitialStock, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtLowStock, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(32, 32, 32)
                .addComponent(btnSave, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(51, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(mainPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(mainPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtIngredientNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtIngredientNameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtIngredientNameActionPerformed

    private void rolesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rolesActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_rolesActionPerformed

    private void txtPricePerUnitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtPricePerUnitActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtPricePerUnitActionPerformed

    private void txtInitialStockActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtInitialStockActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtInitialStockActionPerformed

    private void txtLowStockActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtLowStockActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtLowStockActionPerformed

    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveActionPerformed
        if(!validateIngredientsBeforeSave()){
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(
                this,
                "Are you sure you want to save this ingredient?",
                "Confirm Save",
                JOptionPane.YES_NO_OPTION
        );

        if(confirm != JOptionPane.YES_OPTION){
            return;
        }

        if(ingredientToEdit == null){
            insertIngredient();
        }else{
            updateIngredientData();
        }

        dispose();
    }//GEN-LAST:event_btnSaveActionPerformed

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new addIngredient().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnSave;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JPanel mainPanel;
    private javax.swing.JComboBox<String> roles;
    private javax.swing.JTextField txtIngredientName;
    private javax.swing.JTextField txtInitialStock;
    private javax.swing.JTextField txtLowStock;
    private javax.swing.JTextField txtPricePerUnit;
    // End of variables declaration//GEN-END:variables
}
