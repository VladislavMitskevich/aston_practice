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
            logger.error("Failed to load MySQL JDBC driver", e);
            throw new RuntimeException("Failed to load MySQL JDBC driver", e);
        }
    }

    /**
     * Retrieves a connection to the database.
     *
     * @return a Connection object to the database
     * @throws SQLException if a database access error occurs
     */
    public static Connection getConnection() throws SQLException {
        Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
        if (connection != null) {
            logger.info("Connection to database successful!");
        } else {
            logger.error("Failed to establish connection to the database.");
        }
        return connection;
    }
}
