package controllers;

import com.mysql.cj.jdbc.CallableStatement;
import data_base.data_Base;
import models.Order;
import models.OrderItem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrderController {

    // SAVE ORDER
    public boolean saveOrder(Order order, List<OrderItem> orderItems) {
        Connection conn = null;

        try {
            conn = data_Base.getConnection();
            conn.setAutoCommit(false);

            if (order.getStatus() == null || order.getStatus().isEmpty()) {
                order.setStatus("Pending");
            }

            if (order.getDiscount() <= 0) {
                order.setDiscount(0.0);
            }

            String orderSQL = "INSERT INTO orders (order_number, order_status, subtotal, discount, total) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement orderStmt = conn.prepareStatement(orderSQL, PreparedStatement.RETURN_GENERATED_KEYS);

            orderStmt.setString(1, order.getOrderNumber());
            orderStmt.setString(2, order.getStatus());
            orderStmt.setDouble(3, order.getSubtotal());
            orderStmt.setDouble(4, order.getDiscount());
            orderStmt.setDouble(5, order.getTotal());

            orderStmt.executeUpdate();

            ResultSet rs = orderStmt.getGeneratedKeys();

            int orderId = 0;
            if (rs.next()) {
                orderId = rs.getInt(1);
            }

            if (orderId == 0) {
                throw new SQLException("Failed to get order ID");
            }

            String itemSQL = "INSERT INTO order_items (order_id, product_id, size_id, quantity, price, subtotal) VALUES (?, ?, ?, ?, ?, ?)";

            PreparedStatement itemStmt = conn.prepareStatement(itemSQL);

            for (OrderItem oi : orderItems) {

                if (oi.getQuantity() <= 0) continue;

                itemStmt.setInt(1, orderId);
                itemStmt.setInt(2, oi.getProductId());
                itemStmt.setInt(3, oi.getSizeId());
                itemStmt.setInt(4, oi.getQuantity());
                itemStmt.setDouble(5, oi.getPrice());
                itemStmt.setDouble(6, oi.getSubtotal());

                itemStmt.addBatch();
            }

            itemStmt.executeBatch();
            
            
            conn.commit();

            return true;

        } catch (Exception e) {

            e.printStackTrace();

            try {
                if (conn != null) conn.rollback();
            } catch (Exception ex) {
                ex.printStackTrace();
            }

            return false;

        } finally {

            try {
                if (conn != null) {
                    conn.setAutoCommit(true);
                    conn.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }
    
    
    public String formatOrderNumber(int orderId) {
        return String.format("ORD-%03d", orderId);
    }


    // UPDATE ORDER STATUS
    public void updateOrderStatus(String orderNumber, String status) {

        try (Connection conn = data_Base.getConnection()) {

            String sql = "UPDATE orders SET order_status=? WHERE order_number=?";

            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setString(1, status);
            stmt.setString(2, orderNumber);

            stmt.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    // GET ORDERS BY STATUS
    public List<Order> getOrdersByStatus(String status) {

        List<Order> orders = new ArrayList<>();

        String sql = "SELECT * FROM orders WHERE order_status=?";

        try (Connection conn = data_Base.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, status);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {

                Order order = new Order();

                order.setOrderId(rs.getInt("order_id"));
                order.setOrderNumber(rs.getString("order_number"));
                order.setStatus(rs.getString("order_status"));
                order.setSubtotal(rs.getDouble("subtotal"));
                order.setDiscount(rs.getDouble("discount"));
                order.setTotal(rs.getDouble("total"));

                // GET ITEMS
                List<OrderItem> items = getOrderItems(order.getOrderId());
                order.setItems(items);

                orders.add(order);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return orders;
    }

    // GET ORDER ITEMS
    public List<OrderItem> getOrderItems(int orderId) {
        List<OrderItem> items = new ArrayList<>();

        String sql =
                "SELECT oi.*, p.product_name, s.size_name " +
                "FROM order_items oi " +
                "JOIN products p ON oi.product_id = p.product_id " +
                "JOIN product_sizes s ON oi.size_id = s.size_id " +
                "WHERE oi.order_id=?";

        try (Connection conn = data_Base.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, orderId);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {

                OrderItem item = new OrderItem();

                item.setOrderItemId(rs.getInt("order_item_id"));
                item.setOrderId(rs.getInt("order_id"));
                item.setProductId(rs.getInt("product_id"));
                item.setSizeId(rs.getInt("size_id"));
                item.setQuantity(rs.getInt("quantity"));
                item.setPrice(rs.getDouble("price"));
                item.setSubtotal(rs.getDouble("subtotal"));

                // NEW
                item.setProductName(rs.getString("product_name"));
                item.setSizeName(rs.getString("size_name"));

                items.add(item);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return items;
    }
   public List<OrderItem> getOrderItems(String orderNumber) {
        List<OrderItem> items = new ArrayList<>();
        if(orderNumber == null || orderNumber.isEmpty()) return items;

        String sql = "SELECT oi.*, p.product_name, ps.size_name " +
                     "FROM order_items oi " +
                     "JOIN orders o ON oi.order_id = o.order_id " +
                     "JOIN products p ON oi.product_id = p.product_id " +
                     "JOIN product_sizes ps ON oi.size_id = ps.size_id " +
                     "WHERE o.order_number = ?";

        try (Connection conn = data_Base.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, orderNumber);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                OrderItem item = new OrderItem();
                item.setOrderItemId(rs.getInt("order_item_id"));
                item.setOrderId(rs.getInt("order_id"));
                item.setProductId(rs.getInt("product_id"));
                item.setSizeId(rs.getInt("size_id"));
                item.setQuantity(rs.getInt("quantity"));
                item.setPrice(rs.getDouble("price")); // from order_items
                item.setSubtotal(rs.getDouble("subtotal"));
                item.setProductName(rs.getString("product_name"));
                item.setSizeName(rs.getString("size_name"));

                items.add(item);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return items;
    }
    public double getTotalSalesOrders() {
        double total = 0.0;

        String sql = "SELECT SUM(total) as total_sales FROM orders WHERE order_status = ?";

        try (Connection conn = data_Base.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, "Served");

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                total = rs.getDouble("total_sales"); // kung walang rows, magiging 0.0
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return total;
    }
    public int getTotalOrdersCount() {
        int count = 0;

        String sql = "SELECT COUNT(*) as total_count FROM orders";

        try (Connection conn = data_Base.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                count = rs.getInt("total_count");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return count;
    }
    public int getServedOrdersCount() {
        int count = 0;

        String sql = "SELECT COUNT(*) as total_served FROM orders WHERE order_status = 'Served'";

        try (Connection conn = data_Base.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                count = rs.getInt("total_served");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return count;
    }
    
    public double[] getWeeklySales() {

        double[] sales = new double[7]; // Mon-Sun

        String sql = "SELECT DAYOFWEEK(created_at) as day_num, SUM(total) as total_sales " +
                     "FROM orders " +
                     "WHERE order_status='Served' " +
                     "AND YEARWEEK(created_at,1)=YEARWEEK(CURDATE(),1) " +
                     "GROUP BY DAYOFWEEK(created_at)";

        try (Connection conn = data_Base.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {

                int day = rs.getInt("day_num");
                double total = rs.getDouble("total_sales");

                // MySQL: Sunday=1
                int index = day - 2;
                if(index < 0) index = 6;

                sales[index] = total;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return sales;
    }
 
}