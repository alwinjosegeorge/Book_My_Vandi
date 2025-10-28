package database;

import java.sql.*;

/**
 * DBConnection
 *  - Singleton SQLite connection helper.
 *  - Keeps a single Connection instance and exposes helper method to get it.
 *
 *  Place your existing 'carrental.db' in the project root (or change JDBC_URL).
 */
public class DBConnection {
    // Use the same DB filename you used before
    private static final String JDBC_URL = "jdbc:sqlite:carrental.db";
    private static Connection connection = null;

    // private constructor to prevent instantiation
    private DBConnection() {}

    // Get the singleton connection (creates it if needed)
    public static synchronized Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            try {
                // Load driver (ok even if driver is on classpath)
                Class.forName("org.sqlite.JDBC");
            } catch (ClassNotFoundException e) {
                // The SQLite JDBC JAR must be on the classpath when you run.
                System.err.println("SQLite JDBC driver not found. Add sqlite-jdbc jar to classpath.");
            }
            connection = DriverManager.getConnection(JDBC_URL);
        }
        return connection;
    }

    // Close connection (optional utility)
    public static synchronized void close() {
        if (connection != null) {
            try { connection.close(); } catch (SQLException ignored) {}
            connection = null;
       
        
        
        }
    }
}
