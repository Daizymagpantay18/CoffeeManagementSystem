/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controllers;
import data_base.data_Base;
import java.sql.Connection;
import models.Product;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import models.Categories;
import models.ProductSize;

public class ProductController {
    
    public List<Product> getAllProducts(){

        List<Product> list = new ArrayList<>();

        String sql = """
            SELECT p.product_id,p.product_name, c.category_name, p.status
            FROM products p
            JOIN categories c ON p.category_id = c.category_id
        """;

        try(Connection conn = data_Base.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery()){

            while(rs.next()){

                Product p = new Product();
                p.setProductId(rs.getInt("product_id"));
                p.setName(rs.getString("product_name"));
                p.setCategory(rs.getString("category_name"));
                p.setStatus(rs.getString("status"));

                list.add(p);
            }

        }catch(Exception e){
            e.printStackTrace();
        }

        return list;
    }
    
    public boolean productExists(String name) {

        try(Connection conn = data_Base.getConnection()) {

            String sql = "SELECT * FROM products WHERE product_name = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, name);

            ResultSet rs = ps.executeQuery();

            return rs.next();

        } catch(Exception e){
            e.printStackTrace();
        }

        return false;
    }

    public int insertProductGetId(Product p) {
        int categoryId = getCategoryIdByName(p.getCategory());

        try (Connection conn = data_Base.getConnection()) {
            String sql = "INSERT INTO products(product_name,category_id,description,status) VALUES (?,?,?,?)";
            PreparedStatement ps = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);

            ps.setString(1, p.getName());
            ps.setInt(2, categoryId);
            ps.setString(3, p.getDescription());
            ps.setString(4, p.getStatus());

            int affectedRows = ps.executeUpdate();
            if (affectedRows == 0) {
                throw new Exception("Creating product failed, no rows affected.");
            }

            try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return generatedKeys.getInt(1);
                } else {
                    throw new Exception("Creating product failed, no ID obtained.");
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return -1;
    }
    
    public boolean categoryExists(String category) {

        try(Connection conn = data_Base.getConnection()) {

            String sql = "SELECT * FROM categories WHERE category_name = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, category);

            ResultSet rs = ps.executeQuery();

            return rs.next();

        } catch(Exception e){
            e.printStackTrace();
        }

        return false;
    }
    
    public boolean insertCategory(Categories c){

        try(Connection conn = data_Base.getConnection()) {

            String sql = "INSERT INTO categories(category_name) VALUES (?)";

            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setString(1, c.getName());

            ps.executeUpdate();

            return true;

        }catch(Exception e){
            e.printStackTrace();
        }

        return false;
    }
    
    public int getCategoryIdByName(String name){
        try(Connection conn = data_Base.getConnection()){
            String sql = "SELECT category_id FROM categories WHERE category_name = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, name);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                return rs.getInt("category_id");
            }
        } catch(Exception e){
            e.printStackTrace();
        }
        return -1;
    }
    
    public List<String> getCategories(){

        List<String> categories = new ArrayList<>();

        String sql = "SELECT category_name FROM categories";

        try(Connection conn = data_Base.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery()){

            while(rs.next()){
                categories.add(rs.getString("category_name"));
            }

        }catch(Exception e){
            e.printStackTrace();
        }

        return categories;
    }
    public boolean addProductSize(int productId, String size, int price) {
        try (Connection conn = data_Base.getConnection()) {
            String sql = "INSERT INTO product_sizes(product_id, size_name, price) VALUES (?, ?, ?)";
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setInt(1, productId);
            ps.setString(2, size);
            ps.setInt(3, price);

            ps.executeUpdate();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public int getSizeId(int productId, String sizeName){
        try(Connection conn = data_Base.getConnection()){

            String sql = "SELECT size_id FROM product_sizes WHERE product_id=? AND size_name=?";
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setInt(1, productId);
            ps.setString(2, sizeName);

            ResultSet rs = ps.executeQuery();

            if(rs.next()){
                return rs.getInt("size_id");
            }

        }catch(Exception e){
            e.printStackTrace();
        }

        return -1;
    }
    public boolean addRecipes(int size, int ingredients, double quantity, String unit) {
        try (Connection conn = data_Base.getConnection()) {
            String sql = "INSERT INTO recipes(size_id, ingredient_id, quantity, recipe_unit) VALUES (?,?,?,?)";
            PreparedStatement ps = conn.prepareStatement(sql);
            
            ps.setInt(1, size);
            ps.setInt(2,ingredients);
            ps.setDouble(3,quantity);
            ps.setString(4, unit);
            
            ps.executeUpdate();
            return true;
        }catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    } 
    
    public double getBasePrice(int productId) {
        double basePrice = 0;

        String sql = "SELECT MIN(price) AS base_price FROM product_sizes WHERE product_id = ?";

        try (Connection conn = data_Base.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, productId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                basePrice = rs.getDouble("base_price");
                System.out.println("DEBUG: product_id=" + productId + ", base_price_raw=" + rs.getObject("base_price"));
                if (rs.wasNull()) {
                    System.out.println("DEBUG: No sizes found for this product.");
                    basePrice = 0;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return basePrice;
    }
    
    public Product getProductByName(String name){

        Product p = null;

        String sql = """
            SELECT p.product_id, p.product_name, c.category_name, p.description, p.status
            FROM products p
            JOIN categories c ON p.category_id = c.category_id
            WHERE p.product_name = ?
        """;

        try(Connection conn = data_Base.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)){

            ps.setString(1, name);

            ResultSet rs = ps.executeQuery();

            if(rs.next()){
                p = new Product();
                p.setProductId(rs.getInt("product_id"));
                p.setName(rs.getString("product_name"));
                p.setCategory(rs.getString("category_name"));
                p.setDescription(rs.getString("description"));
                p.setStatus(rs.getString("status"));
            }

        }catch(Exception e){
            e.printStackTrace();
        }

        return p;
    }
    public Product getProductById(int productId){

        Product p = null;

        String sql = """
            SELECT p.product_id, p.product_name, c.category_name, p.description, p.status
            FROM products p
            JOIN categories c ON p.category_id = c.category_id
            WHERE p.product_id = ?
        """;

        try(Connection conn = data_Base.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)){

            ps.setInt(1, productId);

            ResultSet rs = ps.executeQuery();

            if(rs.next()){

                p = new Product();

                p.setProductId(rs.getInt("product_id"));
                p.setName(rs.getString("product_name"));
                p.setCategory(rs.getString("category_name"));
                p.setDescription(rs.getString("description"));
                p.setStatus(rs.getString("status"));
            }

        }catch(Exception e){
            e.printStackTrace();
        }

        return p;
    }
    
    public boolean updateProduct(Product p){

        String sql = """
            UPDATE products 
            SET product_name = ?, category_id = ?, description = ?, status = ?
            WHERE product_id = ?
        """;

        try(Connection conn = data_Base.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)){

            int categoryId = getCategoryIdByName(p.getCategory());

            ps.setString(1, p.getName());
            ps.setInt(2, categoryId);
            ps.setString(3, p.getDescription());
            ps.setString(4, p.getStatus());
            ps.setInt(5, p.getProductId());

            ps.executeUpdate();
            return true;

        }catch(Exception e){
            e.printStackTrace();
        }

        return false;
    }
    
    public List<Object[]> getSizes(int productId){

        List<Object[]> list = new ArrayList<>();

        String sql = "SELECT size_name, price FROM product_sizes WHERE product_id = ?";

        try(Connection conn = data_Base.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)){

            ps.setInt(1, productId);

            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                Object[] row = {
                    rs.getString("size_name"),
                    rs.getInt("price")
                };
                list.add(row);
            }

        }catch(Exception e){
            e.printStackTrace();
        }

        return list;
    }
    
    public List<Object[]> getIngredients(int productId, String sizeName){

        List<Object[]> list = new ArrayList<>();

        String sql = """
            SELECT i.ingredient_name, r.quantity, r.recipe_unit
            FROM recipes r
            JOIN product_sizes ps ON r.size_id = ps.size_id
            JOIN ingredients i ON r.ingredient_id = i.ingredient_id
            WHERE ps.product_id = ? AND ps.size_name = ?
        """;

        try(Connection conn = data_Base.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)){

            ps.setInt(1, productId);
            ps.setString(2, sizeName);

            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                Object[] row = {
                    rs.getString("ingredient_name"),
                    rs.getDouble("quantity"),
                    rs.getString("recipe_unit")
                };

                list.add(row);
            }

        }catch(Exception e){
            e.printStackTrace();
        }

        return list;
    }
    
    public void addOrUpdateProductSize(int productId, String size, int price){

        try(Connection conn = data_Base.getConnection()){

            String checkSql = "SELECT size_id FROM product_sizes WHERE product_id=? AND size_name=?";
            PreparedStatement check = conn.prepareStatement(checkSql);

            check.setInt(1, productId);
            check.setString(2, size);

            ResultSet rs = check.executeQuery();

            if(rs.next()){

                int sizeId = rs.getInt("size_id");

                String updateSql = "UPDATE product_sizes SET price=? WHERE size_id=?";
                PreparedStatement update = conn.prepareStatement(updateSql);

                update.setInt(1, price);
                update.setInt(2, sizeId);

                update.executeUpdate();

            }else{

                addProductSize(productId, size, price);

            }

        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void addOrUpdateRecipe(int sizeId, int ingredientId, double quantity, String unit){

        try(Connection conn = data_Base.getConnection()){

            String checkSql = "SELECT recipe_id FROM recipes WHERE size_id=? AND ingredient_id=?";
            PreparedStatement check = conn.prepareStatement(checkSql);

            check.setInt(1, sizeId);
            check.setInt(2, ingredientId);

            ResultSet rs = check.executeQuery();

            if(rs.next()){

                int recipeId = rs.getInt("recipe_id");

                String updateSql = "UPDATE recipes SET quantity=?, recipe_unit=? WHERE recipe_id=?";
                PreparedStatement update = conn.prepareStatement(updateSql);

                update.setDouble(1, quantity);
                update.setString(2, unit);
                update.setInt(3, recipeId);

                update.executeUpdate();

            }else{

                addRecipes(sizeId, ingredientId, quantity, unit);

            }

        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public List<ProductSize> getProductSizes(int productId){

        List<ProductSize> list = new ArrayList<>();

        String sql = "SELECT size_id, size_name, price FROM product_sizes WHERE product_id=?";

        try(Connection conn = data_Base.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)){

            ps.setInt(1, productId);

            ResultSet rs = ps.executeQuery();

            while(rs.next()){

                ProductSize s = new ProductSize();

                s.setSizeId(rs.getInt("size_id"));
                s.setSizeName(rs.getString("size_name"));
                s.setPrice(rs.getDouble("price"));

                list.add(s);
            }

        }catch(Exception e){
            e.printStackTrace();
        }

        return list;
    }
    public List<Object[]> getTopProducts(int limit) {

        List<Object[]> list = new ArrayList<>();

        String sql = """
            SELECT p.product_name,
                   SUM(oi.quantity) AS total_sold
            FROM order_items oi
            JOIN orders o ON oi.order_id = o.order_id
            JOIN products p ON oi.product_id = p.product_id
            WHERE o.order_status IN ('Pending','Preparing','Served')
            GROUP BY p.product_id, p.product_name
            ORDER BY total_sold DESC
            LIMIT ?
        """;

        try (Connection conn = data_Base.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, limit);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Object[] row = {
                    rs.getString("product_name"),
                    rs.getDouble("total_sold")
                };
                list.add(row);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }
    public Object[] getTopProductsChartData() {

        List<Object[]> list = getTopProducts(5);

        String[] names = new String[list.size()];
        double[] values = new double[list.size()];

        for(int i = 0; i < list.size(); i++){

            names[i] = (String) list.get(i)[0];
            values[i] = ((Number) list.get(i)[1]).doubleValue();

        }

        return new Object[]{names, values};
    }
    
    
}
