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
import java.sql.Statement;

public class DatabaseSetup {
    private static final String URL = "jdbc:mariadb://192.168.1.212:3306/pokemon_db";
    private static final String USER = "pokemon_db_user";
    // This is temporary for testing purposes, in production this should be stored 
    // securely and not hardcoded
    private static final String PASSWORD = "fdr3invoices3MUY3wyatt";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public static void main(String[] args) {
        try (Connection conn = getConnection()) {
            System.out.println("Connected to: " + conn.getMetaData().getDatabaseProductName()
                    + " " + conn.getMetaData().getDatabaseProductVersion() + " at " + 
                    conn.getMetaData().getURL() + " as user " + conn.getMetaData().getUserName());

            try (Statement stmt = conn.createStatement()) {
                stmt.execute("""
                        CREATE TABLE IF NOT EXISTS type_chart (
                            attacking_type VARCHAR(20) NOT NULL,
                            defending_type VARCHAR(20) NOT NULL,
                            effectiveness DECIMAL(3,2) NOT NULL,
                            PRIMARY KEY (attacking_type, defending_type)
                        )
                """);
                        System.out.println("type_chart table ready.");
            }
        } catch (SQLException e) {
            System.out.println("Database error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
