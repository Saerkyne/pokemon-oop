# Database Documentation — Pokemon OOP

Architecture decisions, MariaDB table designs, JDBC reference, and hands-on Java examples for the Discord bot use case.

---

## Table of Contents

### [Part 1 — Architecture Decisions](#part-1--architecture-decisions)

1. [The Rule of Thumb](#the-rule-of-thumb)
2. [Stays as Java Classes (Game Rules / Logic)](#stays-as-java-classes-game-rules--logic)
3. [Database Table Designs](#database-table-designs)
4. [What About Species Data and Learnsets?](#what-about-species-data-and-learnsets)
5. [The Load/Save Pattern](#the-loadsave-pattern)

### [Part 2 — MariaDB Reference](#part-2--mariadb-reference)

1. [MariaDB Setup](#mariadb-setup)
2. [Java Connectivity (JDBC)](#java-connectivity-jdbc)
3. [Data Types for This Project](#data-types-for-this-project)
4. [Example Programs](#example-programs)
   - [Database Schema (Run This First)](#database-schema-run-this-first)
   - [Example 1 — Basic Connection and Table Creation](#example-1--basic-connection-and-table-creation)
   - [Example 2 — CRUD Operations](#example-2--crud-operations)
   - [Example 3 — Prepared Statements and SQL Injection Prevention](#example-3--prepared-statements-and-sql-injection-prevention)
   - [Example 4 — Transactions (Trading Pokémon Between Trainers)](#example-4--transactions-trading-pokémon-between-trainers)
   - [Example 5 — Connection Pooling with HikariCP](#example-5--connection-pooling-with-hikaricp)
   - [Example 6 — JOINs and Relationships (Pokémon Movesets)](#example-6--joins-and-relationships-pokémon-movesets)
5. [Why Not MySQL or SQLite?](#why-not-mysql-or-sqlite)

### [Part 3 — Summary](#part-3--summary)

1. [What Goes Where](#what-goes-where)

---

## Part 1 — Architecture Decisions

### The Rule of Thumb

**Reference data that defines the game rules** stays in code. **Instance state that's unique to each player's Pokémon/Trainer** goes in the database.

Ask yourself: *"Does this value change because a specific player did something?"*

- **No** → it's a game rule. Keep it in Java.
- **Yes** → it's instance state. Store it in the database.

---

### Stays as Java Classes (Game Rules / Logic)

These are *static definitions* that never change at runtime. They define what a Bulbasaur **is**, not a specific player's Bulbasaur.

| Class | Why It Stays |
| --- | --- |
| **`Attack`** | Pure stateless calculator. There's no data to store — just formulas. |
| **`Battle`** | Turn-by-turn logic. Battle state is ephemeral (lives in memory during a fight, discarded when it ends). |
| **`TypeChart`** | 18×18 float matrix — small, fixed, read-only. Hardcoding is faster and simpler than a database round-trip on every damage calculation. |
| **`Natures`** (enum) | 25 entries, never changes. Enum gives you compile-time type safety. |
| **`Stat`** (enum) | 6 values. Pure code concept. |
| **`Move`** subclasses | Each move's power/type/accuracy/PP are fixed game data. ~165 classes, but they're all tiny and immutable. A database lookup for every Tackle would add latency for no benefit. |

#### Why Not Put Moves or Type Charts in the Database?

You *could*, but consider: these values are defined by the game's rules and literally never change while the application runs. Loading them from a database means slower startup, more failure modes (what if the DB is down?), and no compile-time safety. Hardcoded constants are the right call for rule data in a game.

---

### Database Table Designs

This is data that's **unique to each player** and must **survive** between bot sessions.

> **MariaDB syntax note:** MariaDB's `SERIAL` is an alias for `BIGINT UNSIGNED NOT NULL AUTO_INCREMENT UNIQUE` — which causes foreign key type mismatches if the referencing column is a plain `INT`. These schemas use `INT AUTO_INCREMENT PRIMARY KEY` consistently so that all foreign key columns can be plain `INT NOT NULL` without type conflicts. MariaDB also silently ignores inline `REFERENCES` in column definitions — foreign keys must be declared as separate `FOREIGN KEY` clauses.

### `trainers` — One Row Per Discord User

```sql
CREATE TABLE trainers (
    trainer_id       INT AUTO_INCREMENT PRIMARY KEY,
    discord_id       BIGINT UNIQUE NOT NULL,
    discord_username VARCHAR(50) NOT NULL,
    name             VARCHAR(50) NOT NULL
);
```

Example data:

trainer_id | discord_id  | discord_username | name
-----------+-------------+------------------+------
1          | 28374918273 | Calabriel        | Red
2          | 91827364510 | JonBelf          | Blue

### `pokemon_instances` — Each Specific Pokémon a Player Owns

```sql
CREATE TABLE pokemon_instances (
    instance_id   INT AUTO_INCREMENT PRIMARY KEY,
    trainer_id    INT NOT NULL,
    species       VARCHAR(50) NOT NULL,
    nickname      VARCHAR(50) NOT NULL,
    level         SMALLINT NOT NULL DEFAULT 5,
    nature        VARCHAR(20) NOT NULL,
    iv_hp         SMALLINT NOT NULL,
    iv_attack     SMALLINT NOT NULL,
    iv_defense    SMALLINT NOT NULL,
    iv_sp_attack  SMALLINT NOT NULL,
    iv_sp_defense SMALLINT NOT NULL,
    iv_speed      SMALLINT NOT NULL,
    ev_hp         SMALLINT NOT NULL DEFAULT 0,
    ev_attack     SMALLINT NOT NULL DEFAULT 0,
    ev_defense    SMALLINT NOT NULL DEFAULT 0,
    ev_sp_attack  SMALLINT NOT NULL DEFAULT 0,
    ev_sp_defense SMALLINT NOT NULL DEFAULT 0,
    ev_speed      SMALLINT NOT NULL DEFAULT 0,
    current_hp    INT NOT NULL,
    current_exp   INT NOT NULL DEFAULT 0,
    is_fainted    BOOLEAN NOT NULL DEFAULT FALSE,
    FOREIGN KEY (trainer_id) REFERENCES trainers(trainer_id)
);
```

This stores the *randomly generated, mutable* per-instance data: IVs (rolled once at creation), EVs (accumulated over time), level, current HP, nature, and faint status. **Base stats are not stored** — they come from the Java species class and are used to recalculate derived stats on load.

Example data:

instance_id | trainer_id | species    | nickname   | level | nature  | iv_hp | iv_atk | ... | ev_hp | ev_atk | ... | current_hp | is_fainted
------------+------------+------------+------------+-------+---------+-------+--------+-----+-------+--------+-----+------------+-----------
1           | 1          | Bulbasaur  | Bulby      | 7     | Adamant | 28    | 31     | ... | 12    | 4      | ... | 42         | false
2           | 1          | Charmander | Charmander | 5     | Jolly   | 15    | 22     | ... | 0     | 0      | ... | 38         | false

### `pokemon_movesets` — Which Moves Each Instance Knows + Current PP

```sql
CREATE TABLE pokemon_movesets (
    instance_id  INT NOT NULL,
    slot_index   SMALLINT NOT NULL,
    move_name    VARCHAR(50) NOT NULL,
    current_pp   SMALLINT NOT NULL,
    PRIMARY KEY (instance_id, slot_index),
    FOREIGN KEY (instance_id) REFERENCES pokemon_instances(instance_id),
    CHECK (slot_index BETWEEN 0 AND 3)
);
```

The `move_name` column acts as a key to look up the corresponding Java `Move` object at load time. The move's stats (power, type, etc.) still come from the Java class — only the PP consumption is per-instance.

Example data:

instance_id | slot_index | move_name  | current_pp
------------+------------+------------+-----------
1           | 0          | Tackle     | 35
1           | 1          | Growl      | 40
1           | 2          | Vine Whip  | 22

### `trainer_teams` — Which Pokémon Are in the Active Party (and in What Order)

```sql
CREATE TABLE trainer_teams (
    trainer_id   INT NOT NULL,
    slot_index   SMALLINT NOT NULL,
    instance_id  INT NOT NULL,
    PRIMARY KEY (trainer_id, slot_index),
    FOREIGN KEY (trainer_id) REFERENCES trainers(trainer_id),
    FOREIGN KEY (instance_id) REFERENCES pokemon_instances(instance_id),
    CHECK (slot_index BETWEEN 0 AND 5)
);
```

NOTE: This table should only have UP TO 6 records (pokemon instances) per trainer at a time.

Example data:

trainer_id | slot_index | instance_id
-----------+------------+------------
1          | 0          | 1
1          | 1          | 2

---

### What About Species Data and Learnsets?

This is the most interesting decision. Right now every species is a Java subclass (`Bulbasaur.java`, `Charmander.java`, etc.) that hardcodes base stats and learnsets. There are two viable paths:

#### Option A — Keep Species in Java (Recommended for Now)

- Each species stays as a Java class with hardcoded base stats and learnset.
- When loading a Pokémon from the database, use the `species` column to instantiate the right class, then overwrite the instance fields (IVs, EVs, level, nature, HP) from the database row.
- **Pro:** Compile-time safety, no startup DB queries, matches the current architecture.
- **Con:** Adding a new species means writing a new Java file.

#### Option B — Move Species to the Database (More Advanced, Consider Later)

- Store base stats, types, EV yield, and learnsets in database tables.
- Pokémon become generic `Pokemon` objects populated entirely from DB rows.
- **Pro:** Add new species without recompiling, data-driven design.
- **Con:** Lose compile-time checking, need startup caching to avoid constant DB hits, significantly more complex.

#### Why Option A Is the Right Choice Now

Option A is the right choice because the project is a learning codebase. The separate class files make each species visible and debuggable, and the Java compiler catches mistakes (typos in type names, missing stats) that a database would silently accept. Option B is a valid long-term evolution once you're comfortable with both Java and SQL independently.

---

### The Load/Save Pattern

Here's how the Java classes and database work together at runtime:

#### Loading a Pokémon from the Database

```mermaid
STARTUP:
  Database row: { species: "Bulbasaur", level: 7, nature: "Adamant", iv_hp: 28, ... }
       ↓
  Java: new Bulbasaur("Bulby")        ← gets base stats, learnset from the class
       ↓
  Java: pokemon.setLevel(7)           ← overwrite with saved instance data
        pokemon.setNature(Natures.ADAMANT)
        pokemon.setIVs(28, 31, ...)
        pokemon.setEVs(12, 4, ...)
        pokemon.calculateCurrentStats() ← derived stats recomputed from the real values
       ↓
  Result: fully hydrated Pokemon object in memory
```

#### Saving a Pokémon After a Battle

```sql
SAVE (after battle):
  Java object → extract mutable fields → UPDATE pokemon_instances
                                           SET level = 8,
                                               ev_attack = 8,
                                               current_hp = 35,
                                               current_exp = 120,
                                               is_fainted = false
                                         WHERE instance_id = 1
```

#### Why Derived Stats Are Not Stored

**Derived stats** (`currentAttack`, `currentDefense`, `MaxHP`, etc.) are **not stored** in the database. They're recalculated from base stats + IVs + EVs + nature + level every time you load. Storing derived data creates a risk of the database disagreeing with the formula — a common source of bugs called **data denormalization inconsistency**.

For example, if you store `MaxHP = 42` in the database but then change the HP calculation formula in Java, every previously saved Pokémon now has a stale `MaxHP` that doesn't match what the formula would produce. By recomputing on load, the formula is always the single source of truth.

---

## Part 2 — MariaDB Reference

### MariaDB Setup

#### Overview

| | MariaDB |
| --- | --- |
| **Origin** | Fork of MySQL (2009), led by MySQL's original creator |
| **License** | GPL v2 (fully open source) |
| **Philosophy** | Drop-in MySQL replacement; ease-of-use first |
| **Default Port** | 3306 |

MariaDB aims to be the community-driven successor to MySQL, maintaining wire-level compatibility with MySQL clients and tools.

#### Installation

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

#### Day-to-Day Maintenance

| Task | How |
| --- | --- |
| **Config file** | `my.cnf` / `my.ini` — flat key=value, easy to read |
| **User management** | `CREATE USER` / `GRANT` — MySQL-style, widely known |
| **Backups** | `mariadb-dump` (logical) or Mariabackup (physical, hot backups) |
| **Replication** | Built-in binary log replication; straightforward primary/replica setup |
| **Upgrades** | `mariadb-upgrade` script handles schema migration between versions |
| **Monitoring** | `SHOW STATUS`, `SHOW PROCESSLIST` — simple commands |

#### Storage Engines

MariaDB lets you choose a storage engine per table:

| Engine | Use Case |
| --- | --- |
| **InnoDB** (default) | ACID-compliant, row-level locking, foreign keys — best for most applications |
| **Aria** | Crash-safe improvement over MyISAM; good for read-heavy temp tables |
| **ColumnStore** | Analytical / data warehouse workloads |
| **MEMORY** | Temporary tables stored entirely in RAM |

---

### Java Connectivity (JDBC)

MariaDB connects to Java through **JDBC (Java Database Connectivity)** — the standard Java API for relational database access.

#### Maven Dependency

Add this to your `pom.xml`:

```xml
<dependency>
    <groupId>org.mariadb.jdbc</groupId>
    <artifactId>mariadb-java-client</artifactId>
    <version>3.3.3</version>
</dependency>
```

#### Connection URL

```java
String url = "jdbc:mariadb://localhost:3306/pokemon_db";
```

#### Key JDBC Details

| Aspect | MariaDB (mariadb-java-client) |
| --- | --- |
| **Driver class** | `org.mariadb.jdbc.Driver` (auto-loaded in modern JDBC) |
| **URL prefix** | `jdbc:mariadb://` |
| **Batch insert performance** | Fast with `rewriteBatchedStatements=true` URL parameter |
| **Auto-increment ID retrieval** | `Statement.RETURN_GENERATED_KEYS` — works seamlessly |
| **Boolean type** | Stored as `TINYINT(1)` — JDBC maps it automatically |
| **ENUM columns** | Native `ENUM` type (stored as string index) |
| **Case sensitivity** | Table/column names are case-**insensitive** by default |

---

### Data Types for This Project

| Concept | MariaDB Type |
| --- | --- |
| Pokémon name / nickname | `VARCHAR(50)` |
| Dex index | `SMALLINT` |
| Base stats (0-255) | `TINYINT UNSIGNED` (in Java classes, not DB) |
| Type (e.g., "Fire") | `VARCHAR(20)` |
| IV values (0-31) | `SMALLINT` |
| EV values (0-252) | `SMALLINT` |
| Is fainted? | `BOOLEAN` (alias for `TINYINT(1)`) |
| Move power | `SMALLINT` (in Java classes, not DB) |
| Accuracy (0-100) | `SMALLINT` (in Java classes, not DB) |
| Discord user ID | `BIGINT` |
| Auto-increment primary key | `INT AUTO_INCREMENT PRIMARY KEY` |
| Foreign key reference | `INT NOT NULL` + explicit `FOREIGN KEY` clause |

**Important:** MariaDB's `SERIAL` keyword is an alias for `BIGINT UNSIGNED NOT NULL AUTO_INCREMENT UNIQUE`. If you use `SERIAL` for a primary key, then any foreign key column referencing it must also be `BIGINT UNSIGNED` — a plain `INT` will fail with error 150 ("Foreign key constraint is incorrectly formed"). Using `INT AUTO_INCREMENT PRIMARY KEY` consistently avoids this mismatch.

---

### Example Programs

#### Database Schema (Run This First)

Run this in your MariaDB client to create the database and all four tables:

```sql
CREATE DATABASE IF NOT EXISTS pokemon_db;
USE pokemon_db;

CREATE TABLE IF NOT EXISTS trainers (
    trainer_id       INT AUTO_INCREMENT PRIMARY KEY,
    discord_id       BIGINT UNIQUE NOT NULL,
    discord_username VARCHAR(50) NOT NULL,
    name             VARCHAR(50) NOT NULL
);

CREATE TABLE IF NOT EXISTS pokemon_instances (
    instance_id   INT AUTO_INCREMENT PRIMARY KEY,
    trainer_id    INT NOT NULL,
    species       VARCHAR(50) NOT NULL,
    nickname      VARCHAR(50) NOT NULL,
    level         SMALLINT NOT NULL DEFAULT 5,
    nature        VARCHAR(20) NOT NULL,
    iv_hp         SMALLINT NOT NULL,
    iv_attack     SMALLINT NOT NULL,
    iv_defense    SMALLINT NOT NULL,
    iv_sp_attack  SMALLINT NOT NULL,
    iv_sp_defense SMALLINT NOT NULL,
    iv_speed      SMALLINT NOT NULL,
    ev_hp         SMALLINT NOT NULL DEFAULT 0,
    ev_attack     SMALLINT NOT NULL DEFAULT 0,
    ev_defense    SMALLINT NOT NULL DEFAULT 0,
    ev_sp_attack  SMALLINT NOT NULL DEFAULT 0,
    ev_sp_defense SMALLINT NOT NULL DEFAULT 0,
    ev_speed      SMALLINT NOT NULL DEFAULT 0,
    current_hp    INT NOT NULL,
    current_exp   INT NOT NULL DEFAULT 0,
    is_fainted    BOOLEAN NOT NULL DEFAULT FALSE,
    FOREIGN KEY (trainer_id) REFERENCES trainers(trainer_id)
);

CREATE TABLE IF NOT EXISTS pokemon_movesets (
    instance_id  INT NOT NULL,
    slot_index   SMALLINT NOT NULL,
    move_name    VARCHAR(50) NOT NULL,
    current_pp   SMALLINT NOT NULL,
    PRIMARY KEY (instance_id, slot_index),
    FOREIGN KEY (instance_id) REFERENCES pokemon_instances(instance_id),
    CHECK (slot_index BETWEEN 0 AND 3)
);

CREATE TABLE IF NOT EXISTS trainer_teams (
    trainer_id   INT NOT NULL,
    slot_index   SMALLINT NOT NULL,
    instance_id  INT NOT NULL,
    PRIMARY KEY (trainer_id, slot_index),
    FOREIGN KEY (trainer_id) REFERENCES trainers(trainer_id),
    FOREIGN KEY (instance_id) REFERENCES pokemon_instances(instance_id),
    CHECK (slot_index BETWEEN 0 AND 5)
);

-- The type_chart table is optional — TypeChart.java is the primary source.
-- This exists so the database can be used independently for queries/reports.
CREATE TABLE IF NOT EXISTS type_chart (
    attacking_type VARCHAR(20) NOT NULL,
    defending_type VARCHAR(20) NOT NULL,
    effectiveness  DECIMAL(3,2) NOT NULL,
    PRIMARY KEY (attacking_type, defending_type)
);
```

---

#### Example 1 — Basic Connection and Table Creation

**Concept:** Establishing a JDBC connection, executing DDL statements, and proper resource cleanup with try-with-resources.

```java
package pokemonGame.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseSetup {

    private static final String URL  = "jdbc:mariadb://localhost:3306/pokemon_db";
    private static final String USER = "pokemon_db_user";
    private static final String PASS = "your_password_here";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASS);
    }

    public static void main(String[] args) {
        // try-with-resources ensures the connection is closed even if an exception occurs
        try (Connection conn = getConnection()) {
            System.out.println("Connected to: " + conn.getMetaData().getDatabaseProductName()
                    + " " + conn.getMetaData().getDatabaseProductVersion());

            try (Statement stmt = conn.createStatement()) {
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

#### Example 2 — CRUD Operations

**Concept:** The four fundamental database operations — Create, Read, Update, Delete — using `Statement` and `ResultSet`. This example uses the `pokemon_instances` schema where only mutable instance data is stored.

```java
package pokemonGame.db;

import java.sql.*;

public class PokemonCRUD {

    public static void main(String[] args) {
        try (Connection conn = DatabaseSetup.getConnection()) {

            // ── CREATE: Insert a trainer ──
            try (Statement stmt = conn.createStatement()) {
                stmt.executeUpdate(
                    "INSERT INTO trainers (discord_id, discord_username, name) "
                    + "VALUES (28374918273, 'Calabriel', 'Red')"
                );

                ResultSet rs = stmt.executeQuery(
                    "SELECT trainer_id FROM trainers WHERE discord_id = 28374918273"
                );
                rs.next();
                int redId = rs.getInt("trainer_id");
                System.out.println("Created trainer Red with ID: " + redId);

                // ── CREATE: Insert Pokémon instances for Red ──
                // Only mutable data goes here — base stats come from the Java class
                stmt.executeUpdate(
                    "INSERT INTO pokemon_instances "
                    + "(trainer_id, species, nickname, level, nature, "
                    + "iv_hp, iv_attack, iv_defense, iv_sp_attack, iv_sp_defense, iv_speed, "
                    + "current_hp) VALUES "
                    + "(" + redId + ", 'Bulbasaur', 'Bulby', 5, 'Adamant', "
                    + "28, 31, 14, 22, 19, 25, "
                    + "21)"
                );

                stmt.executeUpdate(
                    "INSERT INTO pokemon_instances "
                    + "(trainer_id, species, nickname, level, nature, "
                    + "iv_hp, iv_attack, iv_defense, iv_sp_attack, iv_sp_defense, iv_speed, "
                    + "current_hp) VALUES "
                    + "(" + redId + ", 'Charmander', 'Charmander', 5, 'Jolly', "
                    + "15, 22, 18, 27, 11, 30, "
                    + "19)"
                );
            }

            // ── READ: Query all of Red's Pokémon ──
            try (Statement stmt = conn.createStatement()) {
                ResultSet rs = stmt.executeQuery(
                    "SELECT pi.nickname, pi.species, pi.level, pi.nature, pi.current_hp "
                    + "FROM pokemon_instances pi "
                    + "JOIN trainers t ON pi.trainer_id = t.trainer_id "
                    + "WHERE t.name = 'Red'"
                );

                System.out.println("\nRed's team:");
                while (rs.next()) {
                    System.out.printf("  Lv.%d %s (%s) [%s] HP: %d%n",
                            rs.getInt("level"),
                            rs.getString("nickname"),
                            rs.getString("species"),
                            rs.getString("nature"),
                            rs.getInt("current_hp"));
                }
            }

            // ── UPDATE: Level up Bulby ──
            try (Statement stmt = conn.createStatement()) {
                int rows = stmt.executeUpdate(
                    "UPDATE pokemon_instances SET level = 6 "
                    + "WHERE species = 'Bulbasaur' AND nickname = 'Bulby'"
                );
                System.out.println("\nUpdated " + rows + " row(s) — Bulby leveled up to 6!");
            }

            // ── DELETE: Release Charmander ──
            try (Statement stmt = conn.createStatement()) {
                int rows = stmt.executeUpdate(
                    "DELETE FROM pokemon_instances "
                    + "WHERE species = 'Charmander' AND nickname = 'Charmander'"
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
- **NULL handling** — `rs.getString()` returns Java `null` when the SQL value is NULL

---

#### Example 3 — Prepared Statements and SQL Injection Prevention

**Concept:** Why you should **never** concatenate user input into SQL strings, and how `PreparedStatement` prevents SQL injection.

```java
package pokemonGame.db;

import java.sql.*;

public class PreparedStatementExample {

    // ❌ DANGEROUS — Never do this! Vulnerable to SQL injection.
    public static void unsafeSearch(Connection conn, String userInput) throws SQLException {
        // If userInput is:  ' OR '1'='1
        // The query becomes: SELECT * FROM pokemon_instances WHERE nickname = '' OR '1'='1'
        // This returns ALL rows — a classic injection attack.
        String sql = "SELECT * FROM pokemon_instances WHERE nickname = '" + userInput + "'";
        try (Statement stmt = conn.createStatement()) {
            ResultSet rs = stmt.executeQuery(sql);
            // ... process results ...
        }
    }

    // ✅ SAFE — Use PreparedStatement with parameter placeholders
    public static void safeSearch(Connection conn, String userInput) throws SQLException {
        String sql = "SELECT species, nickname, level, nature FROM pokemon_instances WHERE nickname = ?";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, userInput);  // Parameter index is 1-based

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    System.out.printf("Found: Lv.%d %s (%s) [%s]%n",
                            rs.getInt("level"),
                            rs.getString("nickname"),
                            rs.getString("species"),
                            rs.getString("nature"));
                }
            }
        }
    }

    // ✅ Inserting a Pokémon instance safely with PreparedStatement
    public static int insertPokemonInstance(Connection conn, int trainerId,
            String species, String nickname, String nature,
            int ivHp, int ivAtk, int ivDef, int ivSpAtk, int ivSpDef, int ivSpd,
            int currentHp) throws SQLException {

        String sql = """
            INSERT INTO pokemon_instances
                (trainer_id, species, nickname, nature,
                 iv_hp, iv_attack, iv_defense, iv_sp_attack, iv_sp_defense, iv_speed,
                 current_hp)
            VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
            """;

        try (PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setInt(1, trainerId);
            pstmt.setString(2, species);
            pstmt.setString(3, nickname);
            pstmt.setString(4, nature);
            pstmt.setInt(5, ivHp);
            pstmt.setInt(6, ivAtk);
            pstmt.setInt(7, ivDef);
            pstmt.setInt(8, ivSpAtk);
            pstmt.setInt(9, ivSpDef);
            pstmt.setInt(10, ivSpd);
            pstmt.setInt(11, currentHp);

            pstmt.executeUpdate();

            // Retrieve the auto-generated instance_id
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

            // Insert Pikachu safely (assume trainer Red is trainer_id = 1)
            int id = insertPokemonInstance(conn, 1,
                    "Pikachu", "Sparky", "Hasty",
                    20, 28, 15, 22, 18, 31,
                    20);
            System.out.println("Inserted Pikachu with instance_id: " + id);

            // Search safely — even with malicious input, this won't break
            System.out.println("\nSearching for 'Sparky':");
            safeSearch(conn, "Sparky");

            System.out.println("\nSearching with injection attempt (safely handled):");
            safeSearch(conn, "' OR '1'='1");  // Returns nothing — treated as a literal string

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
- **`RETURN_GENERATED_KEYS`** — retrieving auto-generated primary keys after INSERT

---

#### Example 4 — Transactions (Trading Pokémon Between Trainers)

**Concept:** Transactions ensure that a series of operations either **all succeed** or **all roll back**. This is critical when multiple tables must stay consistent — like a Pokémon trade where one trainer loses a Pokémon and another gains one.

```java
package pokemonGame.db;

import java.sql.*;

public class TransactionExample {

    /**
     * Trade a Pokémon from one trainer to another.
     * Updates both pokemon_instances (ownership) and trainer_teams (active party).
     */
    public static boolean tradePokemon(Connection conn, int instanceId,
            int fromTrainerId, int toTrainerId) throws SQLException {

        conn.setAutoCommit(false);

        try {
            // Step 1: Verify the Pokémon belongs to the source trainer
            String verifySql = "SELECT nickname, species FROM pokemon_instances "
                    + "WHERE instance_id = ? AND trainer_id = ?";
            String pokemonName;
            try (PreparedStatement verifyStmt = conn.prepareStatement(verifySql)) {
                verifyStmt.setInt(1, instanceId);
                verifyStmt.setInt(2, fromTrainerId);
                try (ResultSet rs = verifyStmt.executeQuery()) {
                    if (!rs.next()) {
                        System.out.println("Trade failed: Pokémon not found or doesn't belong to trader.");
                        conn.rollback();
                        return false;
                    }
                    pokemonName = rs.getString("nickname");
                }
            }

            // Step 2: Remove from source trainer's active team (if present)
            String removeTeamSql = "DELETE FROM trainer_teams "
                    + "WHERE trainer_id = ? AND instance_id = ?";
            try (PreparedStatement removeStmt = conn.prepareStatement(removeTeamSql)) {
                removeStmt.setInt(1, fromTrainerId);
                removeStmt.setInt(2, instanceId);
                removeStmt.executeUpdate();
            }

            // Step 3: Transfer ownership
            String transferSql = "UPDATE pokemon_instances SET trainer_id = ? WHERE instance_id = ?";
            try (PreparedStatement transferStmt = conn.prepareStatement(transferSql)) {
                transferStmt.setInt(1, toTrainerId);
                transferStmt.setInt(2, instanceId);
                transferStmt.executeUpdate();
            }

            // All succeeded — commit
            conn.commit();
            System.out.println("Trade successful! " + pokemonName + " was traded.");
            return true;

        } catch (SQLException e) {
            conn.rollback();
            System.err.println("Trade failed, rolling back: " + e.getMessage());
            throw e;
        } finally {
            conn.setAutoCommit(true);
        }
    }

    public static void main(String[] args) {
        try (Connection conn = DatabaseSetup.getConnection()) {

            // Setup: create two trainers
            try (Statement stmt = conn.createStatement()) {
                stmt.executeUpdate(
                    "INSERT INTO trainers (discord_id, discord_username, name) "
                    + "VALUES (11111111111, 'BlueUser', 'Blue')");
                stmt.executeUpdate(
                    "INSERT INTO trainers (discord_id, discord_username, name) "
                    + "VALUES (22222222222, 'GreenUser', 'Green')");
            }

            // Insert a Pokémon for Blue (assume Blue's trainer_id is 2)
            int instanceId = PreparedStatementExample.insertPokemonInstance(conn, 2,
                    "Squirtle", "Squirtle", "Modest",
                    18, 14, 25, 30, 22, 16, 20);

            System.out.println("Before trade:");
            printTrainerTeams(conn);

            // Trade Squirtle from Blue (id=2) to Green (id=3)
            tradePokemon(conn, instanceId, 2, 3);

            System.out.println("\nAfter trade:");
            printTrainerTeams(conn);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void printTrainerTeams(Connection conn) throws SQLException {
        String sql = """
            SELECT t.name AS trainer, pi.nickname, pi.species
            FROM trainers t
            LEFT JOIN pokemon_instances pi ON t.trainer_id = pi.trainer_id
            ORDER BY t.name, pi.nickname
            """;
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            String currentTrainer = "";
            while (rs.next()) {
                String trainer = rs.getString("trainer");
                if (!trainer.equals(currentTrainer)) {
                    System.out.println("  " + trainer + "'s Pokémon:");
                    currentTrainer = trainer;
                }
                String pokemon = rs.getString("nickname");
                if (pokemon != null) {
                    System.out.println("    - " + pokemon + " (" + rs.getString("species") + ")");
                } else {
                    System.out.println("    (none)");
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

---

#### Example 5 — Connection Pooling with HikariCP

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

        config.setJdbcUrl("jdbc:mariadb://localhost:3306/pokemon_db");
        config.setUsername("pokemon_db_user");
        config.setPassword("your_password_here");
        config.setDriverClassName("org.mariadb.jdbc.Driver");

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
                        "SELECT species, nickname, level, nature FROM pokemon_instances WHERE level >= ?");
                pstmt.setInt(1, 5);

                try (ResultSet rs = pstmt.executeQuery()) {
                    System.out.println("Pokémon at level 5 or above:");
                    while (rs.next()) {
                        System.out.printf("  Lv.%d %s (%s) [%s]%n",
                                rs.getInt("level"),
                                rs.getString("nickname"),
                                rs.getString("species"),
                                rs.getString("nature"));
                    }
                }
            }

            // Pool stats are accessible via JMX
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

#### Example 6 — JOINs and Relationships (Pokémon Movesets)

**Concept:** Relational databases model relationships using foreign keys and JOIN queries. This example mirrors the `Pokemon` → `MoveSlot` → `Move` relationship from our Java code, using the `pokemon_movesets` table where `move_name` is a string key that maps back to Java `Move` classes at load time.

```java
package pokemonGame.db;

import java.sql.*;

public class MovesetExample {

    /** Teach a move to a Pokémon instance (add to their moveset in the DB) */
    public static void teachMove(Connection conn, int instanceId, String moveName,
            int currentPp, int slotIndex) throws SQLException {

        // Check if the Pokémon already has 4 moves
        String countSql = "SELECT COUNT(*) FROM pokemon_movesets WHERE instance_id = ?";
        try (PreparedStatement countStmt = conn.prepareStatement(countSql)) {
            countStmt.setInt(1, instanceId);
            try (ResultSet rs = countStmt.executeQuery()) {
                rs.next();
                if (rs.getInt(1) >= 4) {
                    System.out.println("Moveset full! Must forget a move first.");
                    return;
                }
            }
        }

        // Teach the move
        String sql = "INSERT INTO pokemon_movesets (instance_id, move_name, current_pp, slot_index) "
                + "VALUES (?, ?, ?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, instanceId);
            pstmt.setString(2, moveName);
            pstmt.setInt(3, currentPp);
            pstmt.setInt(4, slotIndex);
            pstmt.executeUpdate();
        }
        System.out.println("Taught " + moveName + " in slot " + slotIndex);
    }

    /** Display a Pokémon's full moveset using a JOIN query */
    public static void printMoveset(Connection conn, int instanceId) throws SQLException {
        // JOIN mirrors the relationship: Pokemon --has many--> MoveSlots
        // In Java: Pokemon.moveset (ArrayList<MoveSlot>) where MoveSlot wraps a Move
        String sql = """
            SELECT pi.nickname, pi.species,
                   pm.move_name, pm.current_pp, pm.slot_index
            FROM pokemon_instances pi
            JOIN pokemon_movesets pm ON pi.instance_id = pm.instance_id
            WHERE pi.instance_id = ?
            ORDER BY pm.slot_index
            """;

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, instanceId);

            try (ResultSet rs = pstmt.executeQuery()) {
                boolean first = true;
                while (rs.next()) {
                    if (first) {
                        System.out.printf("%s (%s)'s Moveset:%n",
                                rs.getString("nickname"),
                                rs.getString("species"));
                        first = false;
                    }
                    System.out.printf("  Slot %d: %-15s PP: %d%n",
                            rs.getInt("slot_index"),
                            rs.getString("move_name"),
                            rs.getInt("current_pp"));
                }
                if (first) {
                    System.out.println("Pokémon not found or has no moves.");
                }
            }
        }
    }

    /** Display a trainer's full active party with movesets — multi-table JOIN */
    public static void printFullTeam(Connection conn, int trainerId) throws SQLException {
        String sql = """
            SELECT t.name AS trainer_name,
                   pi.nickname, pi.species, pi.level, pi.nature,
                   tt.slot_index AS team_slot,
                   pm.move_name, pm.current_pp, pm.slot_index AS move_slot
            FROM trainers t
            JOIN trainer_teams tt ON t.trainer_id = tt.trainer_id
            JOIN pokemon_instances pi ON tt.instance_id = pi.instance_id
            LEFT JOIN pokemon_movesets pm ON pi.instance_id = pm.instance_id
            WHERE t.trainer_id = ?
            ORDER BY tt.slot_index, pm.slot_index
            """;

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, trainerId);

            try (ResultSet rs = pstmt.executeQuery()) {
                int currentTeamSlot = -1;
                while (rs.next()) {
                    int teamSlot = rs.getInt("team_slot");
                    if (teamSlot != currentTeamSlot) {
                        System.out.printf("%n  [Slot %d] Lv.%d %s (%s) — %s%n",
                                teamSlot,
                                rs.getInt("level"),
                                rs.getString("nickname"),
                                rs.getString("species"),
                                rs.getString("nature"));
                        currentTeamSlot = teamSlot;
                    }
                    String move = rs.getString("move_name");
                    if (move != null) {
                        System.out.printf("           Move %d: %-15s PP: %d%n",
                                rs.getInt("move_slot"),
                                move,
                                rs.getInt("current_pp"));
                    }
                }
            }
        }
    }

    public static void main(String[] args) {
        try (Connection conn = DatabaseSetup.getConnection()) {

            // Assume Bulby is instance_id = 1, trainer Red is trainer_id = 1
            int bulbyId = 1;
            int redId = 1;

            // Teach Bulby its moves (PP values from the Java Move classes)
            teachMove(conn, bulbyId, "Tackle", 35, 0);
            teachMove(conn, bulbyId, "Growl", 40, 1);
            teachMove(conn, bulbyId, "Vine Whip", 25, 2);

            // Add Bulby to Red's active team
            try (PreparedStatement pstmt = conn.prepareStatement(
                    "INSERT INTO trainer_teams (trainer_id, slot_index, instance_id) VALUES (?, ?, ?)")) {
                pstmt.setInt(1, redId);
                pstmt.setInt(2, 0);  // First team slot
                pstmt.setInt(3, bulbyId);
                pstmt.executeUpdate();
            }

            // Display Bulby's moveset (2-table JOIN)
            printMoveset(conn, bulbyId);

            // Display Red's full team with movesets (4-table JOIN)
            System.out.println("\nRed's Full Team:");
            printFullTeam(conn, redId);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
```

**What this teaches:**

- **Foreign keys** — `pokemon_movesets.instance_id` references `pokemon_instances.instance_id`, enforcing that moves can only be assigned to existing Pokémon
- **Multi-table JOINs** — combining data from `trainers`, `trainer_teams`, `pokemon_instances`, and `pokemon_movesets` in a single query
- **LEFT JOIN** — includes Pokémon even if they have no moves yet (unlike INNER JOIN which would skip them)
- **String-keyed lookups** — `move_name` in the database maps back to Java `Move` subclasses at load time. The move's power, type, and accuracy come from the Java class, not the database.
- **Object-relational mapping concept** — these queries reconstruct the same data structures our Java objects hold in memory (`Trainer.team` → `Pokemon.moveset` → `MoveSlot`)

---

### Why Not MySQL or SQLite?

Before committing to a database, it's worth understanding *why* certain options were excluded. Choosing the right tool for the job is an engineering skill that matters as much as writing the SQL itself.

#### MySQL — Redundant With MariaDB

MariaDB was created in 2009 as a fork of MySQL by **Michael "Monty" Widenius** — the same person who originally created MySQL. The fork happened after Oracle acquired Sun Microsystems (and MySQL with it), and the community was concerned about Oracle's stewardship of the open-source project.

Because of this shared history:

| Aspect | MySQL | MariaDB |
| --- | --- | --- |
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

**Bottom line:** For learning JDBC and relational database concepts, MySQL and MariaDB are interchangeable. Every SQL query, every `PreparedStatement`, every transaction example in this document works on MySQL without modification. Learning MariaDB *is* learning MySQL.

#### SQLite — Wrong Architecture for a Multi-User Bot

SQLite is an excellent database, but it solves a fundamentally different problem. Understanding *why* it's wrong here teaches an important architectural concept: **matching your database's concurrency model to your application's access pattern.**

| Characteristic | SQLite | MariaDB |
| --- | --- | --- |
| **Architecture** | Embedded library, no server process | Client-server: separate database process |
| **Storage** | Single file on disk (e.g., `pokemon.db`) | Server manages its own data directory |
| **Concurrency** | One writer at a time (file-level lock) | Many simultaneous readers and writers (row-level locking) |
| **Network access** | None — must be on the same machine as the app | Connects over TCP/IP from anywhere |
| **User authentication** | None — anyone with file access has full control | Role-based authentication and permissions |
| **Max database size** | ~281 TB (theoretical); practical limit ~1 TB | Effectively unlimited |
| **Configuration** | Zero — this is its biggest strength | Requires setup (ports, users, passwords, config files) |

##### Why This Matters for the Discord Bot

Picture the runtime scenario:

```mermaid
[Discord User A] ──battle command──▶ ┌──────────────┐      ┌──────────┐
                                      │  Discord Bot │ ───▶ │ MariaDB  │
[Discord User B] ──battle command──▶ │  (Java app)  │ ───▶ │          │
[Discord User C] ──team command───▶  └──────────────┘      └──────────┘
```

Three users send commands nearly simultaneously. The bot needs to:

1. **Read** User A's team and their opponent's team (two queries)
2. **Write** the battle result, updating HP, applying EV gains (several writes)
3. **Read** User B's team at the same time
4. **Write** User C's team changes at the same time

**With SQLite:** Steps 2 and 4 cannot happen simultaneously. One write must finish and release the file lock before the other can begin. Under load, users experience delays and potentially `SQLITE_BUSY` errors.

**With MariaDB (InnoDB):** Row-level locking means concurrent writes to different rows proceed in parallel. Two different battles update different Pokémon — no conflict at all.

##### When SQLite *Is* the Right Choice

SQLite shines in scenarios that are the opposite of a Discord bot:

- **Mobile apps** — Android uses SQLite internally; one user, one device, embedded in the app
- **Desktop applications** — a single-user Pokémon game with local saves
- **Embedded systems** — IoT devices, configuration storage
- **Prototyping** — quick experiments with zero setup
- **Read-heavy, single-writer workloads** — content delivery, caching
- **Testing** — spinning up an in-memory database for unit tests

If this project were a single-player desktop game (no Discord, no network), SQLite would actually be the *best* choice — zero configuration, no server to install, the database is just a file you can ship with the game.

##### The Decision Framework

When choosing a database, ask these questions:

```mermaid
1. Will multiple users write to the database at the same time?
   └─ Yes → Client-server database (MariaDB)
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

For this project's planned Discord bot: the answer to questions 1, 2, and 3 is **yes**, which firmly places it in client-server territory.

---

## Part 3 — Summary

### What Goes Where

| Data | Where | Why |
| --- | --- | --- |
| Type chart (18×18) | Java `TypeChart` | Fixed rules, tiny, read-only |
| Natures (25 entries) | Java `Natures` enum | Fixed rules, type-safe |
| Move definitions (~165) | Java `Move` subclasses | Fixed rules, immutable |
| Species base stats | Java species subclasses | Fixed rules, compile-time safety |
| Learnsets | Java species subclasses | Fixed rules, tied to species |
| Damage/battle formulas | Java `Attack`/`Battle` | Pure logic, no data |
| **Trainer identity** | **Database** | Per-player, persistent |
| **Pokémon instances** (IVs, EVs, level, nature, HP) | **Database** | Per-instance, mutable, must survive restarts |
| **Movesets + current PP** | **Database** | Per-instance, mutable |
| **Team composition** | **Database** | Per-player, mutable |
| Derived stats (currentAttack, MaxHP...) | **Neither** — recalculated on load | Avoid storing values computable from other stored values |

### MariaDB Quick Reference

| Category | Detail |
| --- | --- |
| **JDBC dependency** | `org.mariadb.jdbc:mariadb-java-client:3.3.3` |
| **Connection URL** | `jdbc:mariadb://host:3306/pokemon_db` |
| **Auto-increment** | `INT AUTO_INCREMENT PRIMARY KEY` |
| **Foreign keys** | Must use explicit `FOREIGN KEY` clause (inline `REFERENCES` is silently ignored) |
| **Boolean** | `BOOLEAN` (alias for `TINYINT(1)`) |
| **CHECK constraints** | Supported since MariaDB 10.2.1 |
| **Storage engine** | InnoDB (default) — ACID-compliant, row-level locking, foreign keys |

### Quick-Start Checklist

1. Install MariaDB (native or Docker)
2. Add the JDBC driver dependency to `pom.xml`
3. Run the schema SQL from the [Database Schema](#database-schema-run-this-first) section
4. Start with [Example 1](#example-1--basic-connection-and-table-creation) and work through each example in order
5. Experiment: try modifying the examples to add more Pokémon, query by type, or track battle results
