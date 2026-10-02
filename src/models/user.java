/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;


public class user {

    private int userId;
    private String firstName;
    private String lastName;
    private String role;      
    private String userName;
    private String password;
    private String status;

    // Constructor
    public user(int userId, String firstName, String lastName,
                String role, String userName, String password, String status) {

        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.role = role;
        this.userName = userName;
        this.password = password;
        this.status = status;
    }

    public user() {}

    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }

    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    public String getUserName() { return userName; }
    public void setUserName(String userName) { this.userName = userName; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    // Helper method (useful for table)
    public String getFullName() {
        return firstName + " " + lastName;
    }
    
    public String getFormattedId() {
        return "EMP-" + String.format("%03d", userId);
    }
}
