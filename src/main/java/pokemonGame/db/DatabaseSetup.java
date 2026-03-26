// This class sets a connection to the database and creates the type_chart table
// if it does not already exist.

// In a production environment, the database credentials should be stored securely,
// such as in environment variables or a secure vault, rather than hardcoded in the source code.

// The connection code in this class is basic and does not include connection pooling, 
// but it can be used as a starting point and template for a dedicated connection manager



package pokemonGame.db;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Set;
import com.zaxxer.hikari.HikariDataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DatabaseSetup {

    private static final Logger logger = LoggerFactory.getLogger(DatabaseSetup.class);

    // Whitelist of tables that deleteAllData() is allowed to truncate.
    // Because prepared-statement parameters (?) can only substitute VALUES,
    // not identifiers (table names, column names, keywords), we cannot write
    // "TRUNCATE TABLE ?" — the DB needs the table name at query-planning time.
    // Instead we validate dynamically-discovered names against this whitelist
    // so only known application tables are ever placed into the SQL string.
    private static final Set<String> ALLOWED_TABLES = Set.of(
        "trainer_teams",
        "pokemon_movesets",
        "pokemon_instances",
        "trainers"
    );

    private static final String URL = System.getenv("DB_URL");
    private static final String USER = System.getenv("DB_USER");
    private static final String PASSWORD = System.getenv("DB_USER_PASSWORD");
    private static final HikariDataSource DataSource = new HikariDataSource();
    static {
        DataSource.setJdbcUrl(URL);
        DataSource.setUsername(USER);
        DataSource.setPassword(PASSWORD);
    }

    public static Connection getConnection() {

        logger.info("Attempting to get database connection with URL: {}", URL);

        if (URL == null || USER == null || PASSWORD == null) {
            logger.error("Database credentials are not set in environment variables.");
            throw new IllegalStateException("Database credentials are missing");
        }
        try {
            return DataSource.getConnection();
        } catch (SQLException e) {
            logger.error("Error obtaining database connection: {}", e.getMessage(), e);
            throw new IllegalStateException("Unable to obtain database connection", e);
        }
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
                    if (!ALLOWED_TABLES.contains(tableName)) {
                        logger.warn("Skipping unknown table '{}' — not in whitelist", tableName);
                        continue;
                    }
                    // Table name is safe to use here because it was validated
                    // against ALLOWED_TABLES (a hardcoded Set of known names).
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
