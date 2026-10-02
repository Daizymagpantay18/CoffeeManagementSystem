/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

public class Product {

    private int productId;
    private String name;
    private String category;
    private String description;
    private String status;
    
    public Product(){}
    
    public Product(int productId,String name, String category, String description, String status) {
        this.productId = productId;
        this.name = name;
        this.category = category;
        this.description = description;
        this.status = status;
    }
    
    public Product(String name,String category,String description,String status){
        this.name = name;
        this.category = category;
        this.description = description;
        this.status = status;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    public boolean isAvailable(){
        return status != null && status.equalsIgnoreCase("Available");
    }
}
