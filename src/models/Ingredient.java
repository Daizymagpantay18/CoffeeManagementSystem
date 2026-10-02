/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

public class Ingredient {
    private int ingredientId;
    private String name;
    private String unit;
    private int pricePerUnit;
    private double stockQty;
    private double lowStockThreshold;
    public Ingredient(int ingredientId, String name, String unit, int pricePerUnit, double stockQty, double lowStockThreshold){
        this.ingredientId = ingredientId;
        this.name = name;
        this.unit = unit;
        this.pricePerUnit = pricePerUnit;
        this.stockQty = stockQty;
        this.lowStockThreshold = lowStockThreshold;
    }

    public void setIngredientId(int ingredientId) {
        this.ingredientId = ingredientId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public void setPricePerUnit(int pricePerUnit) {
        this.pricePerUnit = pricePerUnit;
    }

    public void setStockQty(double stockQty) {
        this.stockQty = stockQty;
    }

    public void setLowStockThreshold(double lowStockThreshold) {
        this.lowStockThreshold = lowStockThreshold;
    }

    public int getIngredientId() {
        return ingredientId;
    }

    public String getName() {
        return name;
    }

    public String getUnit() {
        return unit;
    }

    public int getPricePerUnit() {
        return pricePerUnit;
    }

    public double getStockQty() {
        return stockQty;
    }

    public double getLowStockThreshold() {
        return lowStockThreshold;
    }

   

    
}
