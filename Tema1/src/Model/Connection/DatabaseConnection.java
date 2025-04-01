package Model.Connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/t1_ps";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "!Fe_te20_03";
    private Connection connection;

    public DatabaseConnection() {
    }

    public Connection getConnection() {
        try {
            if (connection == null || connection.isClosed()) {
                Class.forName("com.mysql.cj.jdbc.Driver");
                connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
                System.out.println("Model.Connection established.");
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error at connecting to database");
        }
        return connection;
    }

    public void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                System.out.println("Model.Connection to database closed.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
