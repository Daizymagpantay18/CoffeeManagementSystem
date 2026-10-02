/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;
import java.util.Date;
import java.util.List;
public class Order {
    private int orderId;
    private String orderNumber;
    private double subtotal;
    private double discount;
    private double total;
    private Date createdAt;
    private List<OrderItem> items;
    private String status; 
    public Order(){};

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(double subtotal) {
        this.subtotal = subtotal;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public List<OrderItem> getItems() {
        return items;
    }

    public void setItems(List<OrderItem> items) {
        this.items = items;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    public Order(int orderId, String orderNumber, List<OrderItem> items, double subtotal, double discount, double total) {
        this.orderId = orderId;
        this.orderNumber = orderNumber;
        this.items = items;
        this.subtotal = subtotal;
        this.discount = discount;
        this.total = total;
        this.createdAt = new Date();
        this.status = "Pending"; 
    }

    
}
