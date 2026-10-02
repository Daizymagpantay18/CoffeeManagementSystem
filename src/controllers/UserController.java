/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package controllers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import models.user;
import data_base.data_Base;
import java.util.List;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.ResultSet;
import java.util.ArrayList;

public class UserController {
    // Register new user — rely on MySQL trigger for hashing
    public void register(user user) {
        String query = "INSERT INTO users (firstName, lastName, username, password, roles) VALUES (?,?,?,?,?)";

        try (Connection conn = data_Base.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setString(1, user.getFirstName());
            ps.setString(2, user.getLastName());
            ps.setString(3, user.getUserName());
            ps.setString(4, user.getPassword()); // <-- plain text, trigger will hash
            ps.setString(5, user.getRole());

            ps.executeUpdate();
            System.out.println("Register success!");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    // Authenticate user
    public user authenticate(String userName, String password) {
        user User = null;
        String sql = "SELECT * FROM users WHERE username = ?";

        try (Connection conn = data_Base.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, userName.trim()); // remove spaces
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    String storedHash = rs.getString("password").trim();

                    // Check password
                    if (!hashPassword(password).equals(storedHash)) {
                        return null; // wrong password
                    }

                    // Get status but do NOT block inactive accounts here
                    String status = rs.getString("status").trim();

                    // Build user object
                    User = new user();
                    User.setUserId(rs.getInt("userId"));
                    User.setUserName(rs.getString("username"));
                    User.setFirstName(rs.getString("firstName"));
                    User.setLastName(rs.getString("lastName"));
                    User.setRole(rs.getString("roles"));
                    User.setStatus(status); // important for login check
                    User.setPassword(storedHash);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return User; // returns null only if user not found or wrong password
    }

    public boolean isUsernameExists(String username) {
        String query = "SELECT userId FROM users WHERE username = ?";

        try (Connection conn = data_Base.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();

            return rs.next(); 

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    
    public List<user> getAllStaffExceptAdmin(int adminId) {
        List<user> staffList = new ArrayList<>();
        String query = "SELECT * FROM users WHERE roles != ? AND userId != ? ORDER BY userId ASC";

        try (Connection conn = data_Base.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setString(1, "Admin");
            ps.setInt(2, adminId);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                user u = new user();
                u.setUserId(rs.getInt("userId"));
                u.setUserName(rs.getString("username"));
                u.setFirstName(rs.getString("firstName"));
                u.setLastName(rs.getString("lastName"));
                u.setRole(rs.getString("roles"));
                u.setStatus(rs.getString("status"));
                staffList.add(u);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return staffList;
    }
    
    public void updateUser(user u) {
        String query = "UPDATE users SET firstName=?, lastName=?, roles=?, status=? WHERE userId=?";
        try (Connection conn = data_Base.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setString(1, u.getFirstName());
            ps.setString(2, u.getLastName());
            ps.setString(3, u.getRole());
            ps.setString(4, u.getStatus());
            ps.setInt(5, u.getUserId());
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    // Submit password change request
    public void requestPasswordChange(int userId, String newPassword) {

        String insertRequest = "INSERT INTO requests (user_id, module, request_type, status) VALUES (?, ?, ?, ?)";
        String insertPasswordRequest = "INSERT INTO password_requests (user_id, new_password, status) VALUES (?, ?, ?)";

        try (Connection conn = data_Base.getConnection()) {
            conn.setAutoCommit(false);

            // 1️⃣ Insert into requests table
            try (PreparedStatement ps1 = conn.prepareStatement(insertRequest)) {
                ps1.setInt(1, userId);
                ps1.setString(2, "Password");
                ps1.setString(3, "Password Change");
                ps1.setString(4, "Pending");
                ps1.executeUpdate();
            }

            // 2️⃣ Hash password before inserting into password_requests
            String hashedNewPassword = hashPassword(newPassword);

            try (PreparedStatement ps2 = conn.prepareStatement(insertPasswordRequest)) {
                ps2.setInt(1, userId);
                ps2.setString(2, hashedNewPassword); // hashed here
                ps2.setString(3, "Pending");
                ps2.executeUpdate();
            }

            conn.commit();
            System.out.println("Password change request submitted!");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    // Hash helper
    public static String hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hashed = md.digest(password.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : hashed) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("SHA-256 algorithm not found!");
        }
    }
    public boolean verifyPassword(String username, String inputPassword) {
        String sql = "SELECT * FROM users WHERE username = ?";
        try (Connection conn = data_Base.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {

            pst.setString(1, username.trim());
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    String storedHash = rs.getString("password").trim();
                    return hashPassword(inputPassword).equals(storedHash);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    public static void main(String[] args) {
        
    }
    
    public void updatePassword(int userId, String newPassword){
        String sql = "UPDATE users SET password = ? WHERE user_id = ?";

        try(Connection conn = data_Base.getConnection();
            PreparedStatement pst = conn.prepareStatement(sql)){

            pst.setString(1, newPassword);
            pst.setInt(2, userId);

            pst.executeUpdate();

        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
}
