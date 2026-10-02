/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package data_base;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class data_Base {
    
    private static final String URL = "jdbc:mysql://localhost:3306/coffeemanagementsystem";
    private static final String USER = "root";
    private static final String PASSWORD = "magpantay03182006.";
    
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
