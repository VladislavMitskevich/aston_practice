package org.examle.aston_practice.spellbook.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Utility class for managing database connections.
 */
public class DatabaseUtil {
    private static final Logger logger = LoggerFactory.getLogger(DatabaseUtil.class);

    private static final String PROPERTIES_FILE = "application.properties";
    private static final Properties properties = new Properties();

    static {
        try (InputStream input = DatabaseUtil.class.getClassLoader().getResourceAsStream(PROPERTIES_FILE)) {
            if (input == null) {
                logger.error("Sorry, unable to find " + PROPERTIES_FILE);
                throw new RuntimeException("Unable to find " + PROPERTIES_FILE);
            }
            properties.load(input);
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (IOException | ClassNotFoundException e) {
            logger.error("Failed to initialize DatabaseUtil.", e);
            throw new RuntimeException("Failed to initialize DatabaseUtil.", e);
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
            connection = DriverManager.getConnection(
                    properties.getProperty("db.url"),
                    properties.getProperty("db.user"),
                    properties.getProperty("db.password")
            );
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

    /**
     * Tests the database connection.
     *
     * @return true if the connection is successful, false otherwise
     */
    public static boolean testConnection() {
        try (Connection connection = getConnection()) {
            return connection != null;
        } catch (SQLException e) {
            logger.error("Failed to test connection to the database.", e);
            return false;
        }
    }
}
