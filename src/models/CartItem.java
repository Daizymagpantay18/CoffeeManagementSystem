/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;


public class CartItem {
     private Product product;
    private int sizeId;
    private String sizeName;
    private int quantity;
    private double price;

    public CartItem(Product product, int sizeId, String sizeName, double price) {
        this.product = product;
        this.sizeId = sizeId;
        this.sizeName = sizeName;
        this.price = price;
        this.quantity = 1;
    }

    public void increaseQty(){
        quantity++;
    }

    public void decreaseQty(){
        if(quantity > 1){
            quantity--;
        }
    }

    public int getQuantity(){
        return quantity;
    }

    public Product getProduct(){
        return product;
    }

    public int getSizeId(){
        return sizeId;
    }

    public String getSizeName(){
        return sizeName;
    }

    public double getPrice(){
        return price;
    }

    public double getSubtotal(){
        return quantity * price;
    }
}
