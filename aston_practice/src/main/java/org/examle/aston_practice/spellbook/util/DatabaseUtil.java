package org.examle.aston_practice.spellbook.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Utility class for managing database connections.
 */
public class DatabaseUtil {
    private static final Logger logger = LoggerFactory.getLogger(DatabaseUtil.class);

    private static final String URL = "jdbc:mysql://localhost:3306/spellbook";
    private static final String USER = "root";
    private static final String PASSWORD = "password";

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            logger.error("MySQL JDBC driver not found.", e);
            throw new RuntimeException("MySQL JDBC driver not found.", e);
        }
    }

    /**
     * Retrieves a connection to the database.
     *
     * @return a Connection object to the database
     * @throws SQLException if a database access error occurs
     */
    public static Connection getConnection() throws SQLException {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            logger.info("Connection to database successful!");
        } catch (SQLException e) {
            logger.error("Failed to connect to the database.", e);
            throw e;
        }
        return connection;
    }

    /**
     * Closes the given database connection.
     *
     * @param connection the Connection object to close
     */
    public static void closeConnection(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
                logger.info("Connection to database closed successfully!");
            } catch (SQLException e) {
                logger.error("Failed to close connection to the database.", e);
            }
        }
    }
}
