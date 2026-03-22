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
import java.util.List;

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

    public static void deleteAllData(List<String> tableNames) {

        try (Connection conn = getConnection()) {

            String sqlDisableFKChecks = "SET FOREIGN_KEY_CHECKS = 0";
            String sqlEnableFKChecks = "SET FOREIGN_KEY_CHECKS = 1";

            try (var disable = conn.prepareStatement(sqlDisableFKChecks)) {
                disable.execute();
            }

            for (String tableName : tableNames) {
                logger.info("Connected to database. Preparing to clear data from table '{}'", tableName);
                String sql = "TRUNCATE TABLE " + tableName;
                try (var stmt = conn.prepareStatement(sql)) {
                    stmt.execute();
                    logger.info("Truncated table '{}'", tableName);
                }

            }
            
            try (var stmt = conn.prepareStatement(sqlEnableFKChecks)) {
                stmt.execute();
            }
            logger.info("All data deleted successfully.");
        } catch (SQLException e) {
            logger.error("Error deleting data from database: {}", e.getMessage(), e);
        }
    }

}
