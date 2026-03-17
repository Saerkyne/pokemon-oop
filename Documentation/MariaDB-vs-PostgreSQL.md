# MariaDB vs PostgreSQL — A Detailed Comparison for Java Developers

This document compares **MariaDB** and **PostgreSQL** across several dimensions relevant to this project, with hands-on Java examples using the Pokemon-OOP codebase as a foundation.

---

## Table of Contents

1. [Overview](#overview)
2. [Ease of Setup and Maintenance](#ease-of-setup-and-maintenance)
3. [Java Connectivity (JDBC)](#java-connectivity-jdbc)
4. [Storage Model and Data Types](#storage-model-and-data-types)
5. [Speed and Performance](#speed-and-performance)
6. [Example Programs](#example-programs)
   - [Example 1 — Basic Connection and Table Creation](#example-1--basic-connection-and-table-creation)
   - [Example 2 — CRUD Operations (Inserting & Reading Pokémon)](#example-2--crud-operations-inserting--reading-pokémon)
   - [Example 3 — Prepared Statements and SQL Injection Prevention](#example-3--prepared-statements-and-sql-injection-prevention)
   - [Example 4 — Transactions (Trading Pokémon Between Trainers)](#example-4--transactions-trading-pokémon-between-trainers)
   - [Example 5 — Connection Pooling with HikariCP](#example-5--connection-pooling-with-hikaricp)
   - [Example 6 — JOINs and Relationships (Pokémon Movesets)](#example-6--joins-and-relationships-pokémon-movesets)
7. [Why Not MySQL or SQLite?](#why-not-mysql-or-sqlite)
8. [Summary Table](#summary-table)
9. [Recommendation](#recommendation)

---

## Overview

| | MariaDB | PostgreSQL |
|---|---|---|
| **Origin** | Fork of MySQL (2009), led by MySQL's original creator | Independent project since 1996, descended from UC Berkeley's POSTGRES |
| **License** | GPL v2 (fully open source) | PostgreSQL License (permissive, similar to MIT/BSD) |
| **Philosophy** | Drop-in MySQL replacement; ease-of-use first | Standards compliance and correctness first |
| **Default Port** | 3306 | 5432 |

MariaDB aims to be the community-driven successor to MySQL, maintaining wire-level compatibility with MySQL clients and tools. PostgreSQL is an independent, feature-rich, standards-compliant relational database known for its extensibility and data integrity.

---

## Ease of Setup and Maintenance

### Installation

#### MariaDB

**Windows:**
- Download the MSI installer from mariadb.org — it includes a GUI setup wizard.
- The wizard configures the root password, service name, and port in a few clicks.
- Comes with HeidiSQL (a GUI client) bundled in the installer.

**Linux (Debian/Ubuntu):**
```bash
sudo apt update
sudo apt install mariadb-server
sudo mysql_secure_installation   # interactive hardening script
```

**Docker:**
```bash
docker run --name pokemon-mariadb -e MARIADB_ROOT_PASSWORD=secret -e MARIADB_DATABASE=pokemon_db -p 3306:3306 -d mariadb:latest
```

#### PostgreSQL

**Windows:**
- Download the installer from postgresql.org (EnterpriseDB distribution).
- GUI wizard walks through password, port, and locale settings.
- Includes pgAdmin 4 (a full-featured web-based GUI client).

**Linux (Debian/Ubuntu):**
```bash
sudo apt update
sudo apt install postgresql postgresql-contrib
# PostgreSQL creates a 'postgres' system user automatically
sudo -u postgres psql   # connect to the default database
```

**Docker:**
```bash
docker run --name pokemon-postgres -e POSTGRES_PASSWORD=secret -e POSTGRES_DB=pokemon_db -p 5432:5432 -d postgres:latest
```

### Day-to-Day Maintenance

| Task | MariaDB | PostgreSQL |
|---|---|---|
| **Config file** | `my.cnf` / `my.ini` — flat key=value, easy to read | `postgresql.conf` + `pg_hba.conf` — more files, but well-documented |
| **User management** | `CREATE USER` / `GRANT` — MySQL-style, widely known | `CREATE ROLE` — more granular, role-based system |
| **Backups** | `mariadb-dump` (logical) or Mariabackup (physical, hot backups) | `pg_dump` (logical) or `pg_basebackup` (physical, streaming) |
| **Replication** | Built-in binary log replication; straightforward primary/replica setup | Streaming replication (WAL-based); more complex but very reliable |
| **Upgrades** | `mariadb-upgrade` script handles schema migration between versions | `pg_upgrade` utility; major version upgrades require more care |
| **Monitoring** | `SHOW STATUS`, `SHOW PROCESSLIST` — simple commands | `pg_stat_*` views — richer data, more to learn |

**Verdict:** MariaDB is easier to set up and maintain for beginners. Its MySQL heritage means there are more tutorials and StackOverflow answers available. PostgreSQL has a steeper initial learning curve but offers more powerful administration tools once you understand them.

---

## Java Connectivity (JDBC)

Both databases connect to Java through **JDBC (Java Database Connectivity)** — the standard Java API for relational database access. The only differences are the driver dependency and the connection URL string.

### Maven Dependencies

Add one of these to your `pom.xml` alongside your existing JUnit dependency:

**MariaDB:**
```xml
<dependency>
    <groupId>org.mariadb.jdbc</groupId>
    <artifactId>mariadb-java-client</artifactId>
    <version>3.3.3</version>
</dependency>
```

**PostgreSQL:**
```xml
<dependency>
    <groupId>org.postgresql</groupId>
    <artifactId>postgresql</artifactId>
    <version>42.7.3</version>
</dependency>
```

### Connection URLs

```java
// MariaDB
String url = "jdbc:mariadb://localhost:3306/pokemon_db";

// PostgreSQL
String url = "jdbc:postgresql://localhost:5432/pokemon_db";
```

### Key JDBC Differences

| Aspect | MariaDB (mariadb-java-client) | PostgreSQL (pgjdbc) |
|---|---|---|
| **Driver class** | `org.mariadb.jdbc.Driver` (auto-loaded in modern JDBC) | `org.postgresql.Driver` (auto-loaded in modern JDBC) |
| **URL prefix** | `jdbc:mariadb://` | `jdbc:postgresql://` |
| **Batch insert performance** | Fast with `rewriteBatchedStatements=true` URL parameter | Fast natively; supports `COPY` for bulk loading |
| **Auto-increment ID retrieval** | `Statement.RETURN_GENERATED_KEYS` — works seamlessly | `Statement.RETURN_GENERATED_KEYS` — works, or use `RETURNING` clause |
| **Boolean type** | Stored as `TINYINT(1)` — JDBC maps it automatically | Native `BOOLEAN` type — cleaner mapping |
| **ENUM columns** | Native `ENUM` type (stored as string index) | Create a custom type with `CREATE TYPE ... AS ENUM` |
| **Case sensitivity** | Table/column names are case-**insensitive** by default | Table/column names are case-**sensitive** if quoted, otherwise folded to lowercase |

**Key takeaway:** Once you learn JDBC with one database, switching to the other requires changing only the dependency and URL. The Java code itself is nearly identical — that's the entire point of JDBC as an abstraction layer.

---

## Storage Model and Data Types

### Storage Engines

**MariaDB** lets you choose a storage engine per table:

| Engine | Use Case |
|---|---|
| **InnoDB** (default) | ACID-compliant, row-level locking, foreign keys — best for most applications |
| **Aria** | Crash-safe improvement over MyISAM; good for read-heavy temp tables |
| **ColumnStore** | Analytical / data warehouse workloads |
| **MEMORY** | Temporary tables stored entirely in RAM |

**PostgreSQL** uses a single storage engine with a sophisticated **MVCC (Multi-Version Concurrency Control)** system. There are no engine choices to make — it is always ACID-compliant with row-level locking.

### Data Types Relevant to the Pokemon Project

| Concept | MariaDB | PostgreSQL |
|---|---|---|
| Pokémon name | `VARCHAR(50)` | `VARCHAR(50)` or `TEXT` (no performance difference in PG) |
| Dex index | `INT` or `SMALLINT` | `INTEGER` or `SMALLINT` |
| Base stats (0-255) | `TINYINT UNSIGNED` | `SMALLINT` (PG has no unsigned types) |
| Type (e.g., "Fire") | `ENUM('Normal','Fire',...)` or `VARCHAR(20)` | `VARCHAR(20)` or custom `CREATE TYPE pokemon_type AS ENUM(...)` |
| IV values (0-31) | `TINYINT UNSIGNED` | `SMALLINT CHECK (value BETWEEN 0 AND 31)` |
| EV values (0-252) | `SMALLINT UNSIGNED` | `SMALLINT CHECK (value BETWEEN 0 AND 252)` |
| Is fainted? | `TINYINT(1)` / `BOOLEAN` (alias) | `BOOLEAN` (native) |
| Move power | `SMALLINT` | `SMALLINT` |
| Accuracy (0-100) | `TINYINT UNSIGNED` | `SMALLINT CHECK (value BETWEEN 0 AND 100)` |
| JSON data | `JSON` (alias for `LONGTEXT` with validation) | `JSON` / `JSONB` (binary, indexable — **much** more powerful) |
| Array of types | Not natively supported; use a join table | `TEXT[]` — native array type |

### Disk Usage

- **MariaDB (InnoDB):** Generally uses less disk space for simple schemas. The row format is compact.
- **PostgreSQL:** MVCC means dead rows accumulate until `VACUUM` runs (autovacuum handles this). This can use more disk for write-heavy workloads, but for a learning project the difference is negligible.

**Verdict:** PostgreSQL has richer data types (arrays, JSONB, custom types, range types) but MariaDB's type system covers everything this project needs. PostgreSQL's `CHECK` constraints are useful for enforcing domain rules (like IV ranges) at the database level.

---

## Speed and Performance

### General Benchmarks

| Workload | Advantage | Why |
|---|---|---|
| **Simple reads (SELECT by PK)** | MariaDB (slight) | Lighter query planner overhead |
| **Complex queries (JOINs, subqueries, CTEs)** | PostgreSQL | Superior query planner and optimizer |
| **Write-heavy (many INSERTs)** | MariaDB (slight) | Less per-row overhead in InnoDB |
| **Concurrent writes** | PostgreSQL | True MVCC; readers never block writers |
| **Full-text search** | PostgreSQL | Built-in `tsvector`/`tsquery`; MariaDB's FTS is more limited |
| **JSON operations** | PostgreSQL (significant) | JSONB is binary-indexed; MariaDB re-parses JSON on each query |
| **Analytical queries** | PostgreSQL | Parallel query execution, advanced aggregates |

### For This Project

For a learning project with ~151 Pokémon, ~165 moves, and a handful of trainers, **both databases will be instantaneous**. Performance differences only matter at scale (millions of rows, thousands of concurrent connections). Choose based on which you want to learn, not on benchmarks.

### Indexing Comparison

Both support B-tree indexes (the default). PostgreSQL additionally supports:
- **GiST** — for geometric/spatial data and full-text search
- **GIN** — for JSONB, arrays, and full-text search
- **BRIN** — for very large, naturally ordered tables
- **Hash** — for equality-only lookups

MariaDB supports B-tree and hash indexes, with full-text indexes on InnoDB tables.

---

## Example Programs

All examples below show **both** MariaDB and PostgreSQL versions side-by-side. The differences are minimal — usually just the connection URL and an occasional SQL syntax variation.

### Database Schema (Run This First)

Create the database and tables. Run the appropriate version in your database client.

**MariaDB:**
```sql
CREATE DATABASE IF NOT EXISTS pokemon_db;
USE pokemon_db;

CREATE TABLE trainers (
    trainer_id   INT AUTO_INCREMENT PRIMARY KEY,
    name         VARCHAR(50) NOT NULL
);

CREATE TABLE pokemon (
    pokemon_id       INT AUTO_INCREMENT PRIMARY KEY,
    species          VARCHAR(50) NOT NULL,
    name             VARCHAR(50) NOT NULL,
    dex_index        SMALLINT NOT NULL,
    type_primary     VARCHAR(20) NOT NULL,
    type_secondary   VARCHAR(20),
    level            SMALLINT NOT NULL DEFAULT 5,
    base_hp          SMALLINT NOT NULL,
    base_attack      SMALLINT NOT NULL,
    base_defense     SMALLINT NOT NULL,
    base_sp_attack   SMALLINT NOT NULL,
    base_sp_defense  SMALLINT NOT NULL,
    base_speed       SMALLINT NOT NULL,
    current_hp       INT,
    is_fainted       BOOLEAN DEFAULT FALSE,
    trainer_id       INT,
    FOREIGN KEY (trainer_id) REFERENCES trainers(trainer_id)
);

CREATE TABLE moves (
    move_id      INT AUTO_INCREMENT PRIMARY KEY,
    move_name    VARCHAR(50) NOT NULL,
    move_power   SMALLINT NOT NULL,
    move_type    VARCHAR(20) NOT NULL,
    category     VARCHAR(10) NOT NULL,
    accuracy     SMALLINT NOT NULL,
    max_pp       SMALLINT NOT NULL
);

CREATE TABLE pokemon_moves (
    pokemon_id   INT NOT NULL,
    move_id      INT NOT NULL,
    current_pp   SMALLINT NOT NULL,
    slot_index   SMALLINT NOT NULL,
    PRIMARY KEY (pokemon_id, slot_index),
    FOREIGN KEY (pokemon_id) REFERENCES pokemon(pokemon_id),
    FOREIGN KEY (move_id) REFERENCES moves(move_id)
);
```

**PostgreSQL:**
```sql
CREATE DATABASE pokemon_db;
-- Connect to pokemon_db, then run:

CREATE TABLE trainers (
    trainer_id   SERIAL PRIMARY KEY,
    name         VARCHAR(50) NOT NULL
);

CREATE TABLE pokemon (
    pokemon_id       SERIAL PRIMARY KEY,
    species          VARCHAR(50) NOT NULL,
    name             VARCHAR(50) NOT NULL,
    dex_index        SMALLINT NOT NULL,
    type_primary     VARCHAR(20) NOT NULL,
    type_secondary   VARCHAR(20),
    level            SMALLINT NOT NULL DEFAULT 5,
    base_hp          SMALLINT NOT NULL,
    base_attack      SMALLINT NOT NULL,
    base_defense     SMALLINT NOT NULL,
    base_sp_attack   SMALLINT NOT NULL,
    base_sp_defense  SMALLINT NOT NULL,
    base_speed       SMALLINT NOT NULL,
    current_hp       INTEGER,
    is_fainted       BOOLEAN DEFAULT FALSE,
    trainer_id       INTEGER,
    FOREIGN KEY (trainer_id) REFERENCES trainers(trainer_id)
);

CREATE TABLE moves (
    move_id      SERIAL PRIMARY KEY,
    move_name    VARCHAR(50) NOT NULL,
    move_power   SMALLINT NOT NULL,
    move_type    VARCHAR(20) NOT NULL,
    category     VARCHAR(10) NOT NULL,
    accuracy     SMALLINT NOT NULL,
    max_pp       SMALLINT NOT NULL
);

CREATE TABLE pokemon_moves (
    pokemon_id   INTEGER NOT NULL,
    move_id      INTEGER NOT NULL,
    current_pp   SMALLINT NOT NULL,
    slot_index   SMALLINT NOT NULL,
    PRIMARY KEY (pokemon_id, slot_index),
    FOREIGN KEY (pokemon_id) REFERENCES pokemon(pokemon_id),
    FOREIGN KEY (move_id) REFERENCES moves(move_id)
);
```

**Key difference:** MariaDB uses `AUTO_INCREMENT` for auto-generated IDs. PostgreSQL uses `SERIAL` (which is shorthand for creating a sequence). PostgreSQL 10+ also supports `GENERATED ALWAYS AS IDENTITY`, which is the SQL-standard approach.

---

### Example 1 — Basic Connection and Table Creation

**Concept:** Establishing a JDBC connection, executing DDL statements, and proper resource cleanup with try-with-resources.

```java
package pokemonGame.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseSetup {

    // ─── Toggle between these two configurations ───

    // MariaDB
    // private static final String URL  = "jdbc:mariadb://localhost:3306/pokemon_db";
    // private static final String USER = "root";
    // private static final String PASS = "secret";

    // PostgreSQL
    private static final String URL  = "jdbc:postgresql://localhost:5432/pokemon_db";
    private static final String USER = "postgres";
    private static final String PASS = "secret";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASS);
    }

    public static void main(String[] args) {
        // try-with-resources ensures the connection is closed even if an exception occurs
        try (Connection conn = getConnection()) {
            System.out.println("Connected to: " + conn.getMetaData().getDatabaseProductName()
                    + " " + conn.getMetaData().getDatabaseProductVersion());

            try (Statement stmt = conn.createStatement()) {
                // This table stores type effectiveness data (like our TypeChart class)
                // IF NOT EXISTS makes this safe to run multiple times
                stmt.execute("""
                    CREATE TABLE IF NOT EXISTS type_chart (
                        attacking_type  VARCHAR(20) NOT NULL,
                        defending_type  VARCHAR(20) NOT NULL,
                        effectiveness   DECIMAL(3,2) NOT NULL,
                        PRIMARY KEY (attacking_type, defending_type)
                    )
                """);
                System.out.println("type_chart table ready.");
            }
        } catch (SQLException e) {
            System.err.println("Database error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
```

**What this teaches:**
- `DriverManager.getConnection()` — the simplest way to open a database connection
- **Try-with-resources** — Java's mechanism for auto-closing `Connection`, `Statement`, and `ResultSet` objects (they implement `AutoCloseable`)
- **DDL via JDBC** — `Statement.execute()` can run any SQL, including `CREATE TABLE`
- **Database metadata** — `Connection.getMetaData()` lets you inspect the database programmatically

---

### Example 2 — CRUD Operations (Inserting & Reading Pokémon)

**Concept:** The four fundamental database operations — Create, Read, Update, Delete — using `Statement` and `ResultSet`.

```java
package pokemonGame.db;

import java.sql.*;

public class PokemonCRUD {

    public static void main(String[] args) {
        try (Connection conn = DatabaseSetup.getConnection()) {

            // ── CREATE: Insert a trainer and some Pokémon ──
            try (Statement stmt = conn.createStatement()) {

                stmt.executeUpdate(
                    "INSERT INTO trainers (name) VALUES ('Red')"
                );

                // Get the auto-generated trainer_id
                // MariaDB and PostgreSQL both support this via RETURN_GENERATED_KEYS
                // but here we'll query it back for clarity
                ResultSet rs = stmt.executeQuery(
                    "SELECT trainer_id FROM trainers WHERE name = 'Red'"
                );
                rs.next();
                int redId = rs.getInt("trainer_id");
                System.out.println("Created trainer Red with ID: " + redId);

                // Insert Pokémon linked to Red
                stmt.executeUpdate(
                    "INSERT INTO pokemon (species, name, dex_index, type_primary, type_secondary, "
                    + "level, base_hp, base_attack, base_defense, base_sp_attack, base_sp_defense, "
                    + "base_speed, trainer_id) VALUES "
                    + "('Bulbasaur', 'Bulbasaur', 1, 'Grass', 'Poison', 5, 45, 49, 49, 65, 65, 45, " + redId + ")"
                );

                stmt.executeUpdate(
                    "INSERT INTO pokemon (species, name, dex_index, type_primary, type_secondary, "
                    + "level, base_hp, base_attack, base_defense, base_sp_attack, base_sp_defense, "
                    + "base_speed, trainer_id) VALUES "
                    + "('Charmander', 'Charmander', 4, 'Fire', NULL, 5, 39, 52, 43, 60, 50, 65, " + redId + ")"
                );
            }

            // ── READ: Query all of Red's Pokémon ──
            try (Statement stmt = conn.createStatement()) {
                ResultSet rs = stmt.executeQuery(
                    "SELECT p.name, p.species, p.level, p.type_primary, p.type_secondary "
                    + "FROM pokemon p "
                    + "JOIN trainers t ON p.trainer_id = t.trainer_id "
                    + "WHERE t.name = 'Red'"
                );

                System.out.println("\nRed's team:");
                while (rs.next()) {
                    String name = rs.getString("name");
                    String species = rs.getString("species");
                    int level = rs.getInt("level");
                    String type1 = rs.getString("type_primary");
                    String type2 = rs.getString("type_secondary"); // may be null
                    String typeStr = type2 != null ? type1 + "/" + type2 : type1;
                    System.out.printf("  Lv.%d %s (%s) [%s]%n", level, name, species, typeStr);
                }
            }

            // ── UPDATE: Level up Bulbasaur ──
            try (Statement stmt = conn.createStatement()) {
                int rows = stmt.executeUpdate(
                    "UPDATE pokemon SET level = 6 WHERE species = 'Bulbasaur' AND name = 'Bulbasaur'"
                );
                System.out.println("\nUpdated " + rows + " row(s) — Bulbasaur leveled up to 6!");
            }

            // ── DELETE: Release Charmander ──
            try (Statement stmt = conn.createStatement()) {
                int rows = stmt.executeUpdate(
                    "DELETE FROM pokemon WHERE species = 'Charmander' AND name = 'Charmander'"
                );
                System.out.println("Deleted " + rows + " row(s) — Charmander was released.");
            }

        } catch (SQLException e) {
            System.err.println("Database error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
```

**What this teaches:**
- `executeUpdate()` — for INSERT, UPDATE, DELETE (returns affected row count)
- `executeQuery()` — for SELECT (returns a `ResultSet`)
- `ResultSet` navigation — `rs.next()` advances the cursor; `rs.getString()`, `rs.getInt()` extract columns
- **NULL handling** — `rs.getString("type_secondary")` returns Java `null` when the SQL value is NULL, which mirrors how our `Pokemon` class handles single-type Pokémon

---

### Example 3 — Prepared Statements and SQL Injection Prevention

**Concept:** Why you should **never** concatenate user input into SQL strings, and how `PreparedStatement` prevents SQL injection.

```java
package pokemonGame.db;

import java.sql.*;

public class PreparedStatementExample {

    // ❌ DANGEROUS — Never do this! Vulnerable to SQL injection.
    public static void unsafeSearch(Connection conn, String userInput) throws SQLException {
        // If userInput is:  ' OR '1'='1
        // The query becomes: SELECT * FROM pokemon WHERE name = '' OR '1'='1'
        // This returns ALL rows — a classic injection attack.
        String sql = "SELECT * FROM pokemon WHERE name = '" + userInput + "'";
        try (Statement stmt = conn.createStatement()) {
            ResultSet rs = stmt.executeQuery(sql);
            // ... process results ...
        }
    }

    // ✅ SAFE — Use PreparedStatement with parameter placeholders
    public static void safeSearch(Connection conn, String userInput) throws SQLException {
        String sql = "SELECT species, name, level, type_primary FROM pokemon WHERE name = ?";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, userInput);  // Parameter index is 1-based

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    System.out.printf("Found: Lv.%d %s (%s) [%s]%n",
                            rs.getInt("level"),
                            rs.getString("name"),
                            rs.getString("species"),
                            rs.getString("type_primary"));
                }
            }
        }
    }

    // ✅ Inserting a Pokémon safely with PreparedStatement
    public static int insertPokemon(Connection conn, String species, String name,
            int dexIndex, String type1, String type2, int level,
            int hp, int atk, int def, int spAtk, int spDef, int spd,
            Integer trainerId) throws SQLException {

        String sql = """
            INSERT INTO pokemon (species, name, dex_index, type_primary, type_secondary,
                                 level, base_hp, base_attack, base_defense,
                                 base_sp_attack, base_sp_defense, base_speed, trainer_id)
            VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
            """;

        try (PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, species);
            pstmt.setString(2, name);
            pstmt.setInt(3, dexIndex);
            pstmt.setString(4, type1);
            if (type2 != null) {
                pstmt.setString(5, type2);
            } else {
                pstmt.setNull(5, Types.VARCHAR);  // Explicit NULL for single-type Pokémon
            }
            pstmt.setInt(6, level);
            pstmt.setInt(7, hp);
            pstmt.setInt(8, atk);
            pstmt.setInt(9, def);
            pstmt.setInt(10, spAtk);
            pstmt.setInt(11, spDef);
            pstmt.setInt(12, spd);
            if (trainerId != null) {
                pstmt.setInt(13, trainerId);
            } else {
                pstmt.setNull(13, Types.INTEGER);  // Wild Pokémon have no trainer
            }

            pstmt.executeUpdate();

            // Retrieve the auto-generated ID
            try (ResultSet keys = pstmt.getGeneratedKeys()) {
                if (keys.next()) {
                    return keys.getInt(1);
                }
            }
        }
        return -1;
    }

    public static void main(String[] args) {
        try (Connection conn = DatabaseSetup.getConnection()) {

            // Insert Pikachu safely
            int id = insertPokemon(conn, "Pikachu", "Sparky",
                    25, "Electric", null, 10,
                    35, 55, 40, 50, 50, 90, null);
            System.out.println("Inserted Pikachu with ID: " + id);

            // Search safely — even with malicious input, this won't break
            System.out.println("\nSearching for 'Sparky':");
            safeSearch(conn, "Sparky");

            System.out.println("\nSearching with injection attempt (safely handled):");
            safeSearch(conn, "' OR '1'='1");  // Returns nothing — the injection is treated as a literal string

        } catch (SQLException e) {
            System.err.println("Database error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
```

**What this teaches:**
- **SQL injection** — the #1 web application vulnerability (OWASP Top 10) and why string concatenation in SQL is dangerous
- **PreparedStatement** — the database driver escapes/parameterizes input, making injection impossible
- **`setNull()`** — how to explicitly set SQL NULL values (important for `typeSecondary` in our Pokemon model)
- **`RETURN_GENERATED_KEYS`** — retrieving auto-generated primary keys after INSERT (works identically on both databases)

---

### Example 4 — Transactions (Trading Pokémon Between Trainers)

**Concept:** Transactions ensure that a series of operations either **all succeed** or **all roll back**. This is critical when multiple tables must stay consistent — like a Pokémon trade where one trainer loses a Pokémon and another gains one.

```java
package pokemonGame.db;

import java.sql.*;

public class TransactionExample {

    /**
     * Trade a Pokémon from one trainer to another.
     * Both the removal and addition must succeed, or neither should.
     */
    public static boolean tradePokemon(Connection conn, int pokemonId,
            int fromTrainerId, int toTrainerId) throws SQLException {

        // Check that the destination trainer has room (max 6 Pokémon)
        String countSql = "SELECT COUNT(*) FROM pokemon WHERE trainer_id = ?";
        try (PreparedStatement countStmt = conn.prepareStatement(countSql)) {
            countStmt.setInt(1, toTrainerId);
            try (ResultSet rs = countStmt.executeQuery()) {
                rs.next();
                if (rs.getInt(1) >= 6) {
                    System.out.println("Trade failed: destination trainer's team is full!");
                    return false;
                }
            }
        }

        // Disable auto-commit to start a transaction
        conn.setAutoCommit(false);

        try {
            // Step 1: Verify the Pokémon belongs to the source trainer
            String verifySql = "SELECT name, species FROM pokemon WHERE pokemon_id = ? AND trainer_id = ?";
            String pokemonName;
            try (PreparedStatement verifyStmt = conn.prepareStatement(verifySql)) {
                verifyStmt.setInt(1, pokemonId);
                verifyStmt.setInt(2, fromTrainerId);
                try (ResultSet rs = verifyStmt.executeQuery()) {
                    if (!rs.next()) {
                        System.out.println("Trade failed: Pokémon not found or doesn't belong to trader.");
                        conn.rollback();
                        return false;
                    }
                    pokemonName = rs.getString("name");
                }
            }

            // Step 2: Transfer the Pokémon
            String transferSql = "UPDATE pokemon SET trainer_id = ? WHERE pokemon_id = ?";
            try (PreparedStatement transferStmt = conn.prepareStatement(transferSql)) {
                transferStmt.setInt(1, toTrainerId);
                transferStmt.setInt(2, pokemonId);
                transferStmt.executeUpdate();
            }

            // If we get here, everything succeeded — commit the transaction
            conn.commit();
            System.out.println("Trade successful! " + pokemonName + " was traded.");
            return true;

        } catch (SQLException e) {
            // Something went wrong — roll back all changes
            conn.rollback();
            System.err.println("Trade failed, rolling back: " + e.getMessage());
            throw e;
        } finally {
            // Always restore auto-commit mode
            conn.setAutoCommit(true);
        }
    }

    public static void main(String[] args) {
        try (Connection conn = DatabaseSetup.getConnection()) {

            // Setup: create two trainers
            try (Statement stmt = conn.createStatement()) {
                stmt.executeUpdate("INSERT INTO trainers (name) VALUES ('Blue')");
                stmt.executeUpdate("INSERT INTO trainers (name) VALUES ('Green')");
            }

            // Insert a Pokémon for Blue (assume Blue's trainer_id is 2)
            int pokemonId = PreparedStatementExample.insertPokemon(conn,
                    "Squirtle", "Squirtle", 7, "Water", null, 5,
                    44, 48, 65, 50, 64, 43, 2);

            System.out.println("Before trade:");
            printTrainerTeams(conn);

            // Trade Squirtle from Blue (id=2) to Green (id=3)
            tradePokemon(conn, pokemonId, 2, 3);

            System.out.println("\nAfter trade:");
            printTrainerTeams(conn);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void printTrainerTeams(Connection conn) throws SQLException {
        String sql = """
            SELECT t.name AS trainer, p.name AS pokemon, p.species
            FROM trainers t
            LEFT JOIN pokemon p ON t.trainer_id = p.trainer_id
            ORDER BY t.name, p.name
            """;
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            String currentTrainer = "";
            while (rs.next()) {
                String trainer = rs.getString("trainer");
                if (!trainer.equals(currentTrainer)) {
                    System.out.println("  " + trainer + "'s team:");
                    currentTrainer = trainer;
                }
                String pokemon = rs.getString("pokemon");
                if (pokemon != null) {
                    System.out.println("    - " + pokemon + " (" + rs.getString("species") + ")");
                } else {
                    System.out.println("    (empty)");
                }
            }
        }
    }
}
```

**What this teaches:**
- **`setAutoCommit(false)`** — begins a transaction (JDBC auto-commits each statement by default)
- **`conn.commit()`** — makes all changes permanent
- **`conn.rollback()`** — undoes all changes since the last commit
- **ACID properties** — Atomicity (all-or-nothing), Consistency (foreign keys enforce valid trainer IDs), Isolation (other connections don't see partial trades), Durability (committed trades survive crashes)
- **Error handling** — always rollback in the catch block and restore auto-commit in finally

**MariaDB vs PostgreSQL difference:** Both handle transactions identically through JDBC. PostgreSQL's MVCC means concurrent reads won't block during the trade. MariaDB (InnoDB) uses row-level locking, which is also fine for this use case.

---

### Example 5 — Connection Pooling with HikariCP

**Concept:** Opening a new database connection for every operation is slow. A **connection pool** maintains a set of reusable connections. HikariCP is the fastest Java connection pool and is the default in Spring Boot.

**Add to `pom.xml`:**
```xml
<dependency>
    <groupId>com.zaxxer</groupId>
    <artifactId>HikariCP</artifactId>
    <version>5.1.0</version>
</dependency>
```

```java
package pokemonGame.db;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import javax.sql.DataSource;
import java.sql.*;

public class ConnectionPoolExample {

    private static HikariDataSource dataSource;

    // Initialize the pool once at application startup
    public static void initPool() {
        HikariConfig config = new HikariConfig();

        // ─── MariaDB ───
        // config.setJdbcUrl("jdbc:mariadb://localhost:3306/pokemon_db");
        // config.setUsername("root");
        // config.setPassword("secret");
        // config.setDriverClassName("org.mariadb.jdbc.Driver");

        // ─── PostgreSQL ───
        config.setJdbcUrl("jdbc:postgresql://localhost:5432/pokemon_db");
        config.setUsername("postgres");
        config.setPassword("secret");
        config.setDriverClassName("org.postgresql.Driver");

        // Pool settings
        config.setMaximumPoolSize(10);       // Max 10 connections in the pool
        config.setMinimumIdle(2);            // Keep at least 2 idle connections ready
        config.setConnectionTimeout(30000);  // Wait up to 30s for a connection
        config.setIdleTimeout(600000);       // Close idle connections after 10 minutes
        config.setMaxLifetime(1800000);      // Recycle connections every 30 minutes

        dataSource = new HikariDataSource(config);
    }

    public static DataSource getDataSource() {
        return dataSource;
    }

    // Shut down the pool when the application exits
    public static void closePool() {
        if (dataSource != null && !dataSource.isClosed()) {
            dataSource.close();
        }
    }

    public static void main(String[] args) {
        initPool();

        try {
            // Each getConnection() borrows from the pool — fast!
            // Closing the connection returns it to the pool (doesn't actually close it)
            try (Connection conn = dataSource.getConnection()) {
                PreparedStatement pstmt = conn.prepareStatement(
                        "SELECT species, level, type_primary FROM pokemon WHERE level >= ?");
                pstmt.setInt(1, 5);

                try (ResultSet rs = pstmt.executeQuery()) {
                    System.out.println("Pokémon at level 5 or above:");
                    while (rs.next()) {
                        System.out.printf("  %s (Lv.%d) [%s]%n",
                                rs.getString("species"),
                                rs.getInt("level"),
                                rs.getString("type_primary"));
                    }
                }
            }

            // Demonstrate that pool stats are accessible
            System.out.printf("%nPool stats: active=%d, idle=%d, total=%d%n",
                    dataSource.getHikariPoolMXBean().getActiveConnections(),
                    dataSource.getHikariPoolMXBean().getIdleConnections(),
                    dataSource.getHikariPoolMXBean().getTotalConnections());

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closePool();
        }
    }
}
```

**What this teaches:**
- **Why pools matter** — creating a TCP connection + authenticating + allocating server resources takes ~5-20ms per connection. A pool amortizes that cost.
- **`DataSource` vs `DriverManager`** — `DataSource` is the modern, poolable approach; `DriverManager` is simpler but creates a new connection every time
- **Pool configuration** — understanding `maxPoolSize`, `idleTimeout`, and `maxLifetime`
- **Transparent usage** — code using `dataSource.getConnection()` looks the same as `DriverManager.getConnection()`. The pool is invisible to business logic.

---

### Example 6 — JOINs and Relationships (Pokémon Movesets)

**Concept:** Relational databases model relationships using foreign keys and JOIN queries. This example mirrors the `Pokemon` → `MoveSlot` → `Move` relationship from our Java code.

```java
package pokemonGame.db;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MovesetExample {

    /** Insert a move and return its generated ID */
    public static int insertMove(Connection conn, String name, int power,
            String type, String category, int accuracy, int pp) throws SQLException {
        String sql = """
            INSERT INTO moves (move_name, move_power, move_type, category, accuracy, max_pp)
            VALUES (?, ?, ?, ?, ?, ?)
            """;
        try (PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, name);
            pstmt.setInt(2, power);
            pstmt.setString(3, type);
            pstmt.setString(4, category);
            pstmt.setInt(5, accuracy);
            pstmt.setInt(6, pp);
            pstmt.executeUpdate();

            try (ResultSet keys = pstmt.getGeneratedKeys()) {
                keys.next();
                return keys.getInt(1);
            }
        }
    }

    /** Teach a move to a Pokémon (add to their moveset) */
    public static void teachMove(Connection conn, int pokemonId, int moveId,
            int slotIndex) throws SQLException {
        // First check if the Pokémon already has 4 moves
        String countSql = "SELECT COUNT(*) FROM pokemon_moves WHERE pokemon_id = ?";
        try (PreparedStatement countStmt = conn.prepareStatement(countSql)) {
            countStmt.setInt(1, pokemonId);
            try (ResultSet rs = countStmt.executeQuery()) {
                rs.next();
                if (rs.getInt(1) >= 4) {
                    System.out.println("Moveset full! Must forget a move first.");
                    return;
                }
            }
        }

        // Get the move's max PP
        String ppSql = "SELECT max_pp FROM moves WHERE move_id = ?";
        int maxPp;
        try (PreparedStatement ppStmt = conn.prepareStatement(ppSql)) {
            ppStmt.setInt(1, moveId);
            try (ResultSet rs = ppStmt.executeQuery()) {
                rs.next();
                maxPp = rs.getInt("max_pp");
            }
        }

        // Teach the move
        String sql = "INSERT INTO pokemon_moves (pokemon_id, move_id, current_pp, slot_index) VALUES (?, ?, ?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, pokemonId);
            pstmt.setInt(2, moveId);
            pstmt.setInt(3, maxPp);  // Start with full PP
            pstmt.setInt(4, slotIndex);
            pstmt.executeUpdate();
        }
    }

    /** Display a Pokémon's full moveset using a JOIN query */
    public static void printMoveset(Connection conn, int pokemonId) throws SQLException {
        // This JOIN mirrors the relationship:
        //   Pokemon --has many--> MoveSlot --references--> Move
        // In our Java code: Pokemon.moveset (ArrayList<MoveSlot>) where MoveSlot wraps a Move
        String sql = """
            SELECT p.name AS pokemon_name, p.species,
                   m.move_name, m.move_power, m.move_type, m.category,
                   pm.current_pp, m.max_pp, pm.slot_index
            FROM pokemon p
            JOIN pokemon_moves pm ON p.pokemon_id = pm.pokemon_id
            JOIN moves m ON pm.move_id = m.move_id
            WHERE p.pokemon_id = ?
            ORDER BY pm.slot_index
            """;

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, pokemonId);

            try (ResultSet rs = pstmt.executeQuery()) {
                boolean first = true;
                while (rs.next()) {
                    if (first) {
                        System.out.printf("%s (%s)'s Moveset:%n",
                                rs.getString("pokemon_name"),
                                rs.getString("species"));
                        first = false;
                    }
                    System.out.printf("  Slot %d: %-15s | Power: %3d | Type: %-10s | %s | PP: %d/%d%n",
                            rs.getInt("slot_index"),
                            rs.getString("move_name"),
                            rs.getInt("move_power"),
                            rs.getString("move_type"),
                            rs.getString("category"),
                            rs.getInt("current_pp"),
                            rs.getInt("max_pp"));
                }
                if (first) {
                    System.out.println("Pokémon not found or has no moves.");
                }
            }
        }
    }

    public static void main(String[] args) {
        try (Connection conn = DatabaseSetup.getConnection()) {

            // Create some Gen 1 moves
            int tackle   = insertMove(conn, "Tackle", 40, "Normal", "Physical", 100, 35);
            int growl    = insertMove(conn, "Growl", 0, "Normal", "Status", 100, 40);
            int vineWhip = insertMove(conn, "Vine Whip", 45, "Grass", "Physical", 100, 25);
            int razorLeaf = insertMove(conn, "Razor Leaf", 55, "Grass", "Physical", 95, 25);

            // Assume Bulbasaur is pokemon_id = 1 from a previous example
            int bulbasaurId = 1;

            // Teach Bulbasaur its moves (mirrors the learnset system in our Java code)
            teachMove(conn, bulbasaurId, tackle, 0);
            teachMove(conn, bulbasaurId, growl, 1);
            teachMove(conn, bulbasaurId, vineWhip, 2);
            teachMove(conn, bulbasaurId, razorLeaf, 3);

            // Display the moveset — this uses a multi-table JOIN
            printMoveset(conn, bulbasaurId);

            // Demonstrate a more complex query: find all Pokémon that know Grass-type moves
            System.out.println("\nPokémon that know Grass-type moves:");
            String grassSql = """
                SELECT DISTINCT p.name, p.species, m.move_name
                FROM pokemon p
                JOIN pokemon_moves pm ON p.pokemon_id = pm.pokemon_id
                JOIN moves m ON pm.move_id = m.move_id
                WHERE m.move_type = ?
                ORDER BY p.name
                """;
            try (PreparedStatement pstmt = conn.prepareStatement(grassSql)) {
                pstmt.setString(1, "Grass");
                try (ResultSet rs = pstmt.executeQuery()) {
                    while (rs.next()) {
                        System.out.printf("  %s knows %s%n",
                                rs.getString("name"),
                                rs.getString("move_name"));
                    }
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
```

**What this teaches:**
- **Foreign keys** — `pokemon_moves.pokemon_id` references `pokemon.pokemon_id`, enforcing that moves can only be assigned to existing Pokémon
- **Junction/bridge tables** — `pokemon_moves` is a many-to-many relationship table (a Pokémon can know many moves; a move can be known by many Pokémon)
- **Multi-table JOINs** — combining data from `pokemon`, `pokemon_moves`, and `moves` in a single query
- **`DISTINCT`** — eliminating duplicate rows when a Pokémon knows multiple Grass moves
- **Object-relational mapping concept** — this query reconstructs the same data our `Pokemon.moveset` (ArrayList of MoveSlots) holds in memory

---

## Why Not MySQL or SQLite?

Before committing to a database, it's worth understanding *why* certain options were excluded. Choosing the right tool for the job is an engineering skill that matters as much as writing the SQL itself.

### MySQL — Redundant With MariaDB

MariaDB was created in 2009 as a fork of MySQL by **Michael "Monty" Widenius** — the same person who originally created MySQL. The fork happened after Oracle acquired Sun Microsystems (and MySQL with it), and the community was concerned about Oracle's stewardship of the open-source project.

Because of this shared history:

| Aspect | MySQL | MariaDB |
|---|---|---|
| **SQL dialect** | Identical for 99% of operations | Identical for 99% of operations |
| **Wire protocol** | MySQL protocol | Same MySQL protocol — clients are interchangeable |
| **JDBC driver** | `mysql-connector-j` | `mariadb-java-client` (also works with MySQL servers) |
| **Default storage engine** | InnoDB | InnoDB |
| **Command-line tools** | `mysql`, `mysqldump` | `mariadb`, `mariadb-dump` (aliased to `mysql`/`mysqldump`) |
| **License** | GPL v2 + proprietary Enterprise edition | GPL v2 only (fully open source, no proprietary tier) |

**What MariaDB adds over MySQL:**
- More storage engines (Aria, ColumnStore, Spider for sharding)
- Faster optimizer in some cases (derived table optimizations, subquery rewrites)
- Truly open-source governance — no features locked behind a commercial license
- Thread pool included in the community edition (MySQL restricts this to Enterprise)

**What MySQL has that MariaDB doesn't:**
- Oracle's backing and enterprise support contracts
- MySQL Shell (a more advanced CLI tool)
- Group Replication / InnoDB Cluster (MySQL's HA solution — MariaDB has Galera Cluster instead)

**Bottom line:** For learning JDBC and relational database concepts, MySQL and MariaDB are interchangeable. Every SQL query, every `PreparedStatement`, every transaction example in this document works on MySQL without modification. Learning MariaDB *is* learning MySQL. There's no reason to cover both — it would be like writing separate tutorials for two editions of the same textbook.

### SQLite — Wrong Architecture for a Multi-User Bot

SQLite is an excellent database, but it solves a fundamentally different problem. Understanding *why* it's wrong here teaches an important architectural concept: **matching your database's concurrency model to your application's access pattern.**

| Characteristic | SQLite | MariaDB / PostgreSQL |
|---|---|---|
| **Architecture** | Embedded library, no server process | Client-server: separate database process |
| **Storage** | Single file on disk (e.g., `pokemon.db`) | Server manages its own data directory |
| **Concurrency** | One writer at a time (file-level lock) | Many simultaneous readers and writers (row-level locking / MVCC) |
| **Network access** | None — must be on the same machine as the app | Connects over TCP/IP from anywhere |
| **User authentication** | None — anyone with file access has full control | Role-based authentication and permissions |
| **Max database size** | ~281 TB (theoretical); practical limit ~1 TB | Effectively unlimited |
| **JDBC driver** | `org.xerial:sqlite-jdbc` | See examples above |
| **Configuration** | Zero — this is its biggest strength | Requires setup (ports, users, passwords, config files) |

#### Why This Matters for the Discord Bot

Picture the runtime scenario:

```
[Discord User A] ──battle command──▶ ┌──────────────┐      ┌──────────┐
                                      │  Discord Bot │ ───▶ │ Database │
[Discord User B] ──battle command──▶ │  (Java app)  │ ───▶ │          │
[Discord User C] ──team command───▶  └──────────────┘      └──────────┘
```

Three users send commands nearly simultaneously. The bot needs to:
1. **Read** User A's team and their opponent's team (two queries)
2. **Write** the battle result, updating HP, applying EV gains (several writes)
3. **Read** User B's team at the same time
4. **Write** User C's team changes at the same time

**With SQLite:** Steps 2 and 4 cannot happen simultaneously. One write must finish and release the file lock before the other can begin. Under load, users experience delays and potentially `SQLITE_BUSY` errors. You'd have to implement retry logic and queuing — complexity that the database should handle for you.

**With PostgreSQL (MVCC):** All four operations run concurrently without blocking each other. Readers never block writers, and writers only block other writers touching the *same rows*. Two different battles update different Pokémon — no conflict at all.

**With MariaDB (InnoDB):** Similar to PostgreSQL — row-level locking means concurrent writes to different rows proceed in parallel.

#### When SQLite *Is* the Right Choice

SQLite shines in scenarios that are the opposite of a Discord bot:

- **Mobile apps** — Android uses SQLite internally; one user, one device, embedded in the app
- **Desktop applications** — a single-user Pokémon game with local saves
- **Embedded systems** — IoT devices, configuration storage
- **Prototyping** — quick experiments with zero setup
- **Read-heavy, single-writer workloads** — content delivery, caching
- **Testing** — spinning up an in-memory database for unit tests

If this project were a single-player desktop game (no Discord, no network), SQLite would actually be the *best* choice — zero configuration, no server to install, the database is just a file you can ship with the game.

#### The Decision Framework

When choosing a database, ask these questions:

```
1. Will multiple users write to the database at the same time?
   └─ Yes → Client-server database (MariaDB, PostgreSQL)
   └─ No  → SQLite is fine

2. Does the database need to be on a different machine than the application?
   └─ Yes → Client-server database
   └─ No  → SQLite is fine

3. Do you need user-level access control (roles, permissions)?
   └─ Yes → Client-server database
   └─ No  → SQLite is fine

4. Is the application a single-user desktop/mobile app?
   └─ Yes → SQLite is likely the best choice
   └─ No  → Client-server database
```

For this project's planned Discord bot: the answer to questions 1, 2, and 3 is **yes**, which firmly places it in client-server territory — MariaDB or PostgreSQL.

---

## Summary Table

| Category | MariaDB | PostgreSQL | Winner for Learning |
|---|---|---|---|
| **Installation** | Simpler installer, fewer config files | Slightly more setup, but well-documented | MariaDB |
| **GUI Tools** | HeidiSQL (bundled), DBeaver, phpMyAdmin | pgAdmin 4 (bundled), DBeaver | Tie |
| **JDBC Setup** | 1 dependency, 1 URL change | 1 dependency, 1 URL change | Tie |
| **SQL Syntax** | MySQL-flavored, widely taught | Standards-compliant, more features | PostgreSQL |
| **Data Types** | Covers basics well; `TINYINT UNSIGNED` is handy | Richer: arrays, JSONB, custom types, `CHECK` constraints | PostgreSQL |
| **Transactions** | Full ACID with InnoDB | Full ACID with MVCC | Tie |
| **Performance (small scale)** | Fast for simple queries | Fast for complex queries | Tie |
| **Performance (large scale)** | Good with tuning | Excellent query planner, parallel queries | PostgreSQL |
| **Community & Tutorials** | Huge (MySQL + MariaDB ecosystem) | Large and growing; excellent official docs | MariaDB (quantity) |
| **Industry Adoption** | Common in web hosting, WordPress, LAMP stack | Common in enterprise, startups, cloud-native apps | Tie |
| **Advanced Features** | Fewer; simpler to learn | More: CTEs, window functions, materialized views | PostgreSQL |
| **Storage Engines** | Multiple (InnoDB, Aria, ColumnStore) | Single (MVCC-based) — simpler mental model | PostgreSQL |

---

## Recommendation

**For this project and learning path:**

- **Start with MariaDB** if you want the smoothest onboarding and the fastest path to running JDBC examples. The MySQL/MariaDB ecosystem has the most beginner-friendly tutorials and the simplest installation experience on Windows.

- **Move to PostgreSQL** when you're comfortable with SQL fundamentals and want to explore advanced features like JSONB, array columns, window functions, and `CHECK` constraints. PostgreSQL's stricter standards compliance also builds better SQL habits.

- **Both are excellent choices.** The JDBC code is 95% identical between the two — the skills transfer directly. Learning one makes it trivial to switch to the other. The examples in this document work on both databases with only the connection URL changed.

**For a Discord bot adaptation:** PostgreSQL's connection pooling (via HikariCP or PgBouncer), JSONB support for storing complex game state, and robust concurrency handling make it the stronger long-term choice for a multi-user application.

### Quick-Start Checklist

1. Install MariaDB or PostgreSQL (or both via Docker)
2. Add the JDBC driver dependency to `pom.xml`
3. Run the schema SQL from the [Database Schema](#database-schema-run-this-first) section
4. Start with [Example 1](#example-1--basic-connection-and-table-creation) and work through each example in order
5. Experiment: try modifying the examples to add more Pokémon, query by type, or track battle results
