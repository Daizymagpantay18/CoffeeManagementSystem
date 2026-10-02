/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controllers;


import data_base.data_Base;
import java.util.ArrayList;
import java.util.List;
import models.Approval;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Daizy Magpantay
 */
public class ApprovalController {
    public List<Approval> getAllApprovals() {
        List<Approval> approvals = new ArrayList<>();
        String sql = "SELECT pr.id, CONCAT(u.firstName, ' ', u.lastName) AS fullname, " +
                     "pr.`module`, pr.request_type, pr.requested_at, pr.`status` " +
                     "FROM requests pr " +
                     "JOIN users u ON pr.user_id = u.userId " +
                     "WHERE pr.`status` = 'Pending' " +
                     "ORDER BY pr.requested_at DESC";

        try (Connection con = data_Base.getConnection();
             PreparedStatement pst = con.prepareStatement(sql);
             ResultSet rs = pst.executeQuery()) {

            while (rs.next()) {
                approvals.add(new Approval(
                    rs.getInt("id"),
                    rs.getString("fullname"),
                    rs.getString("module"),
                    rs.getString("request_type"),
                    rs.getTimestamp("requested_at"),
                    rs.getString("status")
                ));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return approvals;
    }

    public void approveRequest(int requestId) throws SQLException {
        try (Connection conn = data_Base.getConnection()) {
            conn.setAutoCommit(false);

            
            try (PreparedStatement pst = conn.prepareStatement(
                "UPDATE requests SET status='Approved' WHERE id=?")) {
                pst.setInt(1, requestId);
                pst.executeUpdate();
            }

            
            String sql = "SELECT user_id, new_password FROM password_requests " +
                         "WHERE user_id=(SELECT user_id FROM requests WHERE id=?) AND status='Pending'";
            int userId;
            String newPass;
            try (PreparedStatement pst = conn.prepareStatement(sql)) {
                pst.setInt(1, requestId);
                try (ResultSet rs = pst.executeQuery()) {
                    if (!rs.next()) throw new SQLException("No pending password request found.");
                    userId = rs.getInt("user_id");
                    newPass = rs.getString("new_password");
                }
            }

            
            String hashedPass = UserController.hashPassword(newPass);

           
            try (PreparedStatement pst = conn.prepareStatement(
                "UPDATE users SET password=? WHERE userId=?")) {
                pst.setString(1, hashedPass);
                pst.setInt(2, userId);
                pst.executeUpdate();
            }

            
            try (PreparedStatement pst = conn.prepareStatement(
                "UPDATE password_requests SET status='Approved' WHERE user_id=? AND status='Pending'")) {
                pst.setInt(1, userId);
                pst.executeUpdate();
            }

            conn.commit();
            System.out.println("Request approved successfully!");
        }
    }
    
    public List<Approval> getAllRequests() {
        List<Approval> approvals = new ArrayList<>();
        String sql = "SELECT pr.id, CONCAT(u.firstName, ' ', u.lastName) AS fullname, " +
                     "pr.`module`, pr.request_type, pr.requested_at, pr.`status` " +
                     "FROM requests pr " +
                     "JOIN users u ON pr.user_id = u.userId " +
                     "ORDER BY pr.requested_at DESC";

        try (Connection con = data_Base.getConnection();
             PreparedStatement pst = con.prepareStatement(sql);
             ResultSet rs = pst.executeQuery()) {

            while (rs.next()) {
                approvals.add(new Approval(
                    rs.getInt("id"),
                    rs.getString("fullname"),
                    rs.getString("module"),
                    rs.getString("request_type"),
                    rs.getTimestamp("requested_at"),
                    rs.getString("status")
                ));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return approvals;
    }

    public void rejectRequest(int requestId) throws SQLException {
        try (Connection conn = data_Base.getConnection()) {
            String sql = "UPDATE requests SET status = 'Rejected' WHERE id = ?";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1, requestId);
            pst.executeUpdate();
            pst.close();
        }
    }
    
}
