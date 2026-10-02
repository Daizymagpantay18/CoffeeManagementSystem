/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controllers;

import data_base.data_Base;
import java.util.ArrayList;
import java.util.List;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import models.Ingredient;

public class IngredientController {
    public void addIngredient(String name, String unit, int price, double stock, double threshold){

        String query = "INSERT INTO ingredients (ingredient_name, stock_unit, price_per_unit, stock_qty, low_stock_threshold) VALUES (?, ?, ?, ?, ?)";

        try(Connection conn = data_Base.getConnection();
            PreparedStatement ps = conn.prepareStatement(query)){

            ps.setString(1, name);
            ps.setString(2, unit);
            ps.setInt(3, price);
            ps.setDouble(4, stock);
            ps.setDouble(5, threshold);

            ps.executeUpdate();

        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public List<Ingredient> getAllIngredients(){

        List<Ingredient> list = new ArrayList<>();

        String query = "SELECT * FROM ingredients";

        try(Connection conn = data_Base.getConnection();
            PreparedStatement ps = conn.prepareStatement(query);
            ResultSet rs = ps.executeQuery()){

            while(rs.next()){

                Ingredient ing = new Ingredient(
                        rs.getInt("ingredient_id"),
                        rs.getString("ingredient_name"),
                        rs.getString("stock_unit"),
                        rs.getInt("price_per_unit"),
                        rs.getDouble("stock_qty"),
                        rs.getDouble("low_stock_threshold")
                );

                list.add(ing);
            }

        }catch(Exception e){
            e.printStackTrace();
        }

        return list;
    }
    // GET ONE INGREDIENT (FOR EDIT)
    public Ingredient getIngredientById(int id){

        String query = "SELECT * FROM ingredients WHERE ingredient_id = ?";

        try(Connection conn = data_Base.getConnection();
            PreparedStatement ps = conn.prepareStatement(query)){

            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();

            if(rs.next()){
                return new Ingredient(
                        rs.getInt("ingredient_id"),
                        rs.getString("ingredient_name"),
                        rs.getString("stock_unit"),
                        rs.getInt("price_per_unit"),
                        rs.getDouble("stock_qty"),
                        rs.getDouble("low_stock_threshold")
                );
            }

        }catch(Exception e){
            e.printStackTrace();
        }

        return null;
    }

    public boolean isIngredientExists(String name) {
        boolean exists = false;

        String sql = "SELECT COUNT(*) FROM ingredients WHERE LOWER(ingredient_name) = LOWER(?)";

        try (Connection conn = data_Base.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, name);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                exists = rs.getInt(1) > 0;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return exists;
    }
    public boolean isIngredientUsedInRecipe(int ingredientId){

        String sql = "SELECT * FROM recipes WHERE ingredient_id = ?";

        try(Connection conn = data_Base.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)){

            ps.setInt(1, ingredientId);

            ResultSet rs = ps.executeQuery();

            return rs.next();

        }catch(Exception e){
            e.printStackTrace();
        }

        return false;
    }
    public void deleteIngredient(int id){

        String sql = "DELETE FROM ingredients WHERE ingredient_id = ?";

        try(Connection conn = data_Base.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)){

            ps.setInt(1, id);
            ps.executeUpdate();

        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public void updateIngredient(Ingredient i){
        String sql = "UPDATE ingredients SET ingredient_name = ?, stock_unit = ?, price_per_unit = ?, stock_qty = ?, low_stock_threshold = ? WHERE ingredient_id = ?";

        try(Connection conn = data_Base.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)){

            ps.setString(1, i.getName());
            ps.setString(2, i.getUnit());
            ps.setInt(3, i.getPricePerUnit());
            ps.setDouble(4, i.getStockQty());
            ps.setDouble(5, i.getLowStockThreshold());
            ps.setInt(6, i.getIngredientId());

            ps.executeUpdate();

        } catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public int getIngredientIdByName(String name){
        try(Connection conn = data_Base.getConnection()){

            String sql = "SELECT ingredient_id FROM ingredients WHERE ingredient_name=?";
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setString(1, name);

            ResultSet rs = ps.executeQuery();

            if(rs.next()){
                return rs.getInt("ingredient_id");
            }

        }catch(Exception e){
            e.printStackTrace();
        }

        return -1;
    }
    
    public List<Ingredient> getLowStockIngredients() {
        List<Ingredient> lowStockList = new ArrayList<>();

        String sql = "SELECT * FROM ingredients WHERE stock_qty <= low_stock_threshold AND status = 'ACTIVE'";

        try (Connection conn = data_Base.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Ingredient ing = new Ingredient(
                    rs.getInt("ingredient_id"),
                    rs.getString("ingredient_name"),
                    rs.getString("stock_unit"),
                    rs.getInt("price_per_unit"),
                    rs.getDouble("stock_qty"),
                    rs.getDouble("low_stock_threshold")
                );
                lowStockList.add(ing);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return lowStockList;
    }
    
}
