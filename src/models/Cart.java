/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

import java.util.ArrayList;
import java.util.List;


public class Cart {
    private List<CartItem> items = new ArrayList<>();

    public List<CartItem> getItems(){
        return items;
    }

    public void addItem(Product product, int sizeId, String sizeName, double price){

        for(CartItem item : items){

            if(item.getProduct().getProductId() == product.getProductId()
                    && item.getSizeId() == sizeId){

                item.increaseQty();
                return;
            }
        }

        items.add(new CartItem(product, sizeId, sizeName, price));
    }

    public void removeItem(CartItem item){
        items.remove(item);
    }

    public void clearCart(){
        items.clear();
    }

    public double getTotal(){

        double total = 0;

        for(CartItem item : items){
            total += item.getSubtotal();
        }

        return total;
    }
    
    
}
