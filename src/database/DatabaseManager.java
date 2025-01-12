package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseManager {

    // Database connection details
    private static final String URL = "jdbc:mysql://localhost:3306/task_management"; 
    private static final String USER = "root"; 
    private static final String PASSWORD = "uu.UU2275765";

    // Method to establish and return a connection to the database
    public static Connection getConnection() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver"); 
        } 
        catch (ClassNotFoundException e) {
            throw new SQLException("MySQL Driver not found!", e);
        }
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    // Main method to test the database connection
    public static void main(String[] args) {
        try (Connection connection = getConnection()) {
            System.out.println("Connection to MySQL database successful!");
        } 
        catch (SQLException e) {
            System.err.println("Failed to connect to the database.");
            e.printStackTrace(); 
        }
    }
}
