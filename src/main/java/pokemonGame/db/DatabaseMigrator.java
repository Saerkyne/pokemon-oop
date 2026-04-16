package pokemonGame.db;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Runs versioned SQL migration scripts against the current database.
 *
 * <h3>How it works</h3>
 * <ol>
 *   <li>On first run, creates a {@code schema_migrations} table to track
 *       which migrations have already been applied.</li>
 *   <li>Scans classpath folder {@code db/migrations/} for {@code .sql} files
 *       whose names follow the pattern {@code V###__description.sql}
 *       (e.g. {@code V001__drop_is_fainted.sql}).</li>
 *   <li>Compares discovered files against the {@code schema_migrations} table
 *       and runs only the ones not yet recorded.</li>
 *   <li>Each migration runs inside a transaction — if any statement fails,
 *       the entire migration is rolled back and no partial changes are applied.</li>
 * </ol>
 *
 * <h3>Why a migrations table?</h3>
 * Without a record of what has already run, the migrator would either try to
 * re-apply old changes (crash) or need the developer to manually remember
 * which scripts were executed. The {@code schema_migrations} table is the
 * standard solution — Flyway, Liquibase, Rails, Django, and Laravel all use
 * this same pattern. It makes deployments <em>idempotent</em>: running the
 * migrator twice in a row is safe because it skips already-applied scripts.
 *
 * <h3>Usage</h3>
 * <pre>
 *   // Run against whatever DB the app is configured to use (prod or test):
 *   DatabaseMigrator.migrate();
 * </pre>
 * This is called automatically from {@link DatabaseSetup} on startup, so both
 * the production bot and the test suite get a migrated schema without any
 * manual SSH work.
 */
public class DatabaseMigrator {

    private static final Logger LOGGER = LoggerFactory.getLogger(DatabaseMigrator.class);

    // Classpath folder where migration SQL files live.
    // In the Maven layout this maps to src/main/resources/db/migrations/
    private static final String MIGRATIONS_PATH = "db/migrations/";

    // The manifest file listing migration filenames in order.
    // We use an explicit list rather than classpath scanning because JAR files
    // don't support directory listing reliably across all environments.
    private static final String MANIFEST_FILE = MIGRATIONS_PATH + "manifest.txt";

    private DatabaseMigrator() {
        // Utility class — no instances
    }

    /**
     * Apply all pending migrations to the database that {@link DatabaseSetup}
     * is currently connected to.
     */
    public static void migrate() {
        try (Connection conn = DatabaseSetup.getConnection()) {
            ensureMigrationsTable(conn);
            List<String> allMigrations = discoverMigrations();
            List<String> applied = getAppliedMigrations(conn);

            for (String filename : allMigrations) {
                if (applied.contains(filename)) {
                    LOGGER.debug("Migration already applied, skipping: {}", filename);
                    continue;
                }
                applyMigration(conn, filename);
            }
        } catch (SQLException e) {
            LOGGER.error("Migration failed: {}", e.getMessage(), e);
            throw new IllegalStateException("Database migration failed", e);
        }
    }

    /**
     * Creates the {@code schema_migrations} tracking table if it doesn't
     * already exist.  {@code IF NOT EXISTS} makes this safe to call every
     * time the application starts.
     */
    private static void ensureMigrationsTable(Connection conn) throws SQLException {
        try (Statement stmt = conn.createStatement()) {
            stmt.execute("""
                CREATE TABLE IF NOT EXISTS schema_migrations (
                    filename    VARCHAR(255) PRIMARY KEY,
                    applied_at  TIMESTAMP DEFAULT CURRENT_TIMESTAMP
                )
            """);
        }
    }

    /**
     * Reads the manifest file to get an ordered list of migration filenames.
     * Each non-blank, non-comment line is treated as a filename relative to
     * the {@code db/migrations/} classpath folder.
     */
    private static List<String> discoverMigrations() {
        List<String> migrations = new ArrayList<>();
        InputStream manifest = DatabaseMigrator.class.getClassLoader()
                .getResourceAsStream(MANIFEST_FILE);
        if (manifest == null) {
            LOGGER.info("No migration manifest found at {}; nothing to migrate.", MANIFEST_FILE);
            return migrations;
        }
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(manifest, StandardCharsets.UTF_8))) {
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.strip();
                if (!line.isEmpty() && !line.startsWith("#")) {
                    migrations.add(line);
                }
            }
        } catch (IOException e) {
            LOGGER.error("Error reading migration manifest: {}", e.getMessage(), e);
        }
        return migrations;
    }

    /**
     * Returns filenames of all migrations that have already been applied,
     * as recorded in the {@code schema_migrations} table.
     */
    private static List<String> getAppliedMigrations(Connection conn) throws SQLException {
        List<String> applied = new ArrayList<>();
        String sql = "SELECT filename FROM schema_migrations ORDER BY filename";
        try (PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                applied.add(rs.getString("filename"));
            }
        }
        return applied;
    }

    /**
     * Loads one migration file from the classpath and executes it inside a
     * transaction.  If any statement fails, the entire migration is rolled
     * back so you never end up with a half-applied change.
     *
     * <h4>Why transactions matter here</h4>
     * Imagine a migration that (1) adds a column and (2) backfills data.
     * If step 2 crashes without a transaction, step 1 has already been
     * committed and the migration is recorded as "not applied" — but the
     * column already exists.  Re-running would crash on step 1.  Wrapping
     * both in a transaction means either both succeed or neither does.
     */
    private static void applyMigration(Connection conn, String filename) throws SQLException {
        String resourcePath = MIGRATIONS_PATH + filename;
        String sql = loadResource(resourcePath);
        if (sql == null) {
            LOGGER.error("Migration file not found on classpath: {}", resourcePath);
            throw new SQLException("Missing migration file: " + resourcePath);
        }

        LOGGER.info("Applying migration: {}", filename);
        boolean originalAutoCommit = conn.getAutoCommit();
        try {
            conn.setAutoCommit(false);

            // Split on semicolons to allow multi-statement migration files.
            // Statements are trimmed; empty fragments (from trailing semicolons) are skipped.
            String[] statements = sql.split(";");
            for (String stmtText : statements) {
                stmtText = stmtText.strip();
                if (!stmtText.isEmpty()) {
                    try (Statement stmt = conn.createStatement()) {
                        stmt.execute(stmtText);
                    }
                }
            }

            // Record that this migration has been applied
            String insertSql = "INSERT INTO schema_migrations (filename) VALUES (?)";
            try (PreparedStatement pstmt = conn.prepareStatement(insertSql)) {
                pstmt.setString(1, filename);
                pstmt.executeUpdate();
            }

            conn.commit();
            LOGGER.info("Migration applied successfully: {}", filename);
        } catch (SQLException e) {
            conn.rollback();
            LOGGER.error("Migration failed, rolled back: {} — {}", filename, e.getMessage(), e);
            throw e;
        } finally {
            conn.setAutoCommit(originalAutoCommit);
        }
    }

    /**
     * Loads a classpath resource as a UTF-8 string, or returns {@code null}
     * if the resource doesn't exist.
     */
    private static String loadResource(String path) {
        InputStream is = DatabaseMigrator.class.getClassLoader().getResourceAsStream(path);
        if (is == null) {
            return null;
        }
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(is, StandardCharsets.UTF_8))) {
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line).append('\n');
            }
            return sb.toString();
        } catch (IOException e) {
            LOGGER.error("Error reading resource {}: {}", path, e.getMessage(), e);
            return null;
        }
    }
}
