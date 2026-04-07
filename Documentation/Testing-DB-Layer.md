# Testing the Database (CRUD) Layer

**Date:** April 6, 2026

## Overview

The DB layer (`pokemonGame.db.*`) is testable with **integration tests** — tests that run real SQL against a real database. These give high confidence that your queries, prepared statements, and result-set mapping actually work.

## Approach: Integration Tests with a Test Database

Use a separate MariaDB database (e.g., `mokepons_test`) so tests never touch production data. Set the `DB_URL` env var to point to the test database when running tests.

### Example: TrainerCRUDTest

```java
class TrainerCRUDTest {

    private TrainerCRUD trainerCRUD = new TrainerCRUD();

    @BeforeEach
    void setUp() {
        // Clear test data before each test so tests don't interfere with each other.
        // This is called "test isolation" — each test starts with a clean slate.
        DatabaseSetup.deleteAllData();
    }

    @Test
    void createAndRetrieveTrainer() {
        int id = trainerCRUD.createDBTrainer(12345L, "testuser", "Ash");
        assertTrue(id > 0, "Should return a positive DB ID");

        Trainer trainer = trainerCRUD.getTrainerByDiscordId(12345L);
        assertNotNull(trainer);
        assertEquals("Ash", trainer.getTrainerName());
        assertEquals(12345L, trainer.getDiscordId());
    }

    @Test
    void getTrainerByDiscordIdReturnsNullWhenNotFound() {
        Trainer trainer = trainerCRUD.getTrainerByDiscordId(99999L);
        assertNull(trainer);
    }
}
```

## What to Test

- **CRUD round-trips:** Create → Read → verify fields match
- **Update/delete:** Modify a record, re-read, verify changes
- **Edge cases:** Duplicate Discord IDs, deleting a nonexistent trainer, reading from an empty table
- **Foreign key relationships:** Create trainer → create team → verify the team is linked to the trainer
- **Each CRUD class independently:** `TrainerCRUD`, `TeamCRUD`, `PokemonCRUD`, `BattleCRUD`, `BattleTurnCRUD`

## Prerequisites

- The `DB_URL`, `DB_USER`, and `DB_USER_PASSWORD` environment variables must be set when running tests.
- `DatabaseSetup` uses a static initializer that throws if env vars are missing, so tests will crash at class-load time if the DB isn't configured. This is fine for integration tests — you *want* them to fail explicitly if the database is unavailable.
- Use `deleteAllData()` in `@BeforeEach` to ensure test isolation.

## Best Practice: Separate Test Database

Point `DB_URL` at a dedicated test schema (e.g., `mokepons_test`) so you never accidentally corrupt real data. The test database should have the same schema as production — use the same migration scripts to create it.

## Alternative: Testcontainers (Advanced)

Testcontainers can spin up a throwaway MariaDB in a Docker container per test run, making tests fully self-contained. However, this requires Docker to be available on the host machine. For a learning project, a dedicated test database is simpler and sufficient.

```xml
<!-- pom.xml dependency (if using Testcontainers) -->
<dependency>
    <groupId>org.testcontainers</groupId>
    <artifactId>mariadb</artifactId>
    <version>1.19.7</version>
    <scope>test</scope>
</dependency>
```

### Docker-in-Docker Note

Since the development environment already runs inside a Docker container, Testcontainers requires extra setup. There are two approaches:

1. **Docker Socket Mounting (preferred):** Mount the host's Docker socket (`/var/run/docker.sock`) into the container. Testcontainers then creates "sibling" containers via the host's Docker daemon. Only works if the host has Docker and the socket is accessible.

2. **True Docker-in-Docker (DinD):** Run a Docker daemon inside the container. Requires `--privileged` mode, which is a security risk and usually not allowed in managed environments.

For this project, skip Testcontainers and use a dedicated test database — the MariaDB container is already running alongside the dev container, so just create a `mokepons_test` schema on it.
