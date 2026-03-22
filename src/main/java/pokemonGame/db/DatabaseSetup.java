// This class sets a connection to the database and creates the type_chart table
// if it does not already exist.

// In a production environment, the database credentials should be stored securely,
// such as in environment variables or a secure vault, rather than hardcoded in the source code.

// The connection code in this class is basic and does not include connection pooling, 
// but it can be used as a starting point and template for a dedicated connection manager



package pokemonGame.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DatabaseSetup {

    private static final Logger logger = LoggerFactory.getLogger(DatabaseSetup.class);


    private static final String URL = "jdbc:mariadb://192.168.1.212:3306/pokemon_db";
    private static final String USER = "pokemon_db_user";
    // This is temporary for testing purposes, in production this should be stored 
    // securely and not hardcoded
    private static final String PASSWORD = "fdr3invoices3MUY3wyatt";

    public static Connection getConnection() throws SQLException {
        logger.info("Attempting to connect to database at {}", URL);
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public static void deleteAllData() {

        try (Connection conn = getConnection()) {

            String sqlDisableFKChecks = "SET FOREIGN_KEY_CHECKS = 0";
            String sqlEnableFKChecks = "SET FOREIGN_KEY_CHECKS = 1";

            try (var disable = conn.prepareStatement(sqlDisableFKChecks)) {
                disable.execute();
            }

            try (var getTables = conn.prepareStatement("SHOW TABLES")) {
                var rs = getTables.executeQuery();
                while (rs.next()) {
                    String tableName = rs.getString(1);
                    try (var delete = conn.prepareStatement("TRUNCATE TABLE " + tableName)) {
                        delete.execute();
                        logger.info("Deleted data from table: {}", tableName);
                    } catch (SQLException e) {
                        logger.error("Error deleting data from table {}: {}", tableName, e.getMessage(), e);
                    }
                }
            }
            
            
            try (var enable = conn.prepareStatement(sqlEnableFKChecks)) {
                enable.execute();
            }
            logger.info("All data deleted successfully.");
        } catch (SQLException e) {
            logger.error("Error deleting data from database: {}", e.getMessage(), e);
        }
    }

}
