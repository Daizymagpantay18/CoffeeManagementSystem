/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package util;

import java.awt.CardLayout;
import java.awt.Frame;
import javax.swing.JDialog;
import javax.swing.JPanel;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;

public class AddProductDialog extends JDialog{
    private CardLayout cardLayout;
    private JPanel cardPanel;
    private JTextField txtName;
    private JTextArea txtDesc;
    private JComboBox<String> category;

    public AddProductDialog(Frame parent) {
        super(parent, "Add New Product", true);
        setSize(600, 450);
        setLocationRelativeTo(parent);

        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);

        cardPanel.add(step1Panel(), "step1");
        cardPanel.add(step2Panel(), "step2");
        cardPanel.add(step3Panel(), "step3");

        add(cardPanel);
    }
    
    private JPanel step1Panel() {

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JTextField txtName = new JTextField();
        JTextArea txtDesc = new JTextArea(3,20);

        JComboBox<String> category = new JComboBox<>(
            new String[]{"Coffee","Tea","Milk","Pastry"}
        );

        JButton next = new JButton("Next");

        next.addActionListener(e -> {
            cardLayout.show(cardPanel, "step2");
        });

        panel.add(new JLabel("Product Name"));
        panel.add(txtName);

        panel.add(new JLabel("Category"));
        panel.add(category);

        panel.add(new JLabel("Description"));
        panel.add(txtDesc);

        panel.add(next);

        return panel;
    }
    
    private JPanel step2Panel() {

        JPanel panel = new JPanel();

        JCheckBox small = new JCheckBox("Small");
        JCheckBox medium = new JCheckBox("Medium");
        JCheckBox large = new JCheckBox("Large");

        JButton back = new JButton("Back");
        JButton next = new JButton("Next");

        back.addActionListener(e -> {
            cardLayout.show(cardPanel, "step1");
        });

        next.addActionListener(e -> {
            cardLayout.show(cardPanel, "step3");
        });

        panel.add(small);
        panel.add(medium);
        panel.add(large);
        panel.add(back);
        panel.add(next);

        return panel;
    }
        private JPanel step3Panel() {

        JPanel panel = new JPanel();

        JComboBox<String> ingredients = new JComboBox<>(
            new String[]{"Espresso","Milk","Caramel Syrup"}
        );

        JSpinner qty = new JSpinner(new SpinnerNumberModel(1,1,100,1));

        JButton add = new JButton("Add");
        JButton save = new JButton("Save Product");
        JButton back = new JButton("Back");

        back.addActionListener(e -> {
            cardLayout.show(cardPanel, "step2");
        });

        save.addActionListener(e -> {
            JOptionPane.showMessageDialog(this,"Product Saved!");
            dispose();
        });

        panel.add(new JLabel("Ingredient"));
        panel.add(ingredients);

        panel.add(new JLabel("Qty"));
        panel.add(qty);

        panel.add(add);
        panel.add(back);
        panel.add(save);

        return panel;
    }
}
