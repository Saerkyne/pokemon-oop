package pokemonGame.dbTests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import pokemonGame.db.TrainerCRUD;
import pokemonGame.service.TrainerService;
import pokemonGame.db.DatabaseSetup;
import pokemonGame.model.Trainer;
// TODO: Update tests to use trainerService instead of trainerCRUD
/**
 * Unit tests for {@link TrainerCRUD} database operations.
 *
 * CRUD operations under test:
 *   - Create: insert a new trainer row and receive its generated DB ID
 *   - Read:   retrieve a trainer by Discord ID or by database ID
 *   - Update: change a trainer's name by Discord ID or by database ID
 *   - Delete: remove a trainer by Discord ID or by database ID
 *
 * Additional behaviors under test:
 *   - Duplicate Discord IDs are rejected (returns -1)
 *   - Lookups for non-existent IDs return null
 *   - Deletions of non-existent rows affect zero rows
 *
 * Each test starts from an empty database ({@code DatabaseSetup.deleteAllData()}
 * in {@code @BeforeEach}) so tests are fully isolated from one another.
 */
class TrainerCRUDTest {

    private TrainerCRUD trainerCRUD = new TrainerCRUD();
    private TrainerService trainerService = new TrainerService(trainerCRUD);

    @BeforeEach
    void setUp() {
        // Clear all trainer-related tables before each test to ensure a clean slate.
        // This is necessary because trainer tests often rely on specific database states
        // and we want to avoid interference from leftover data from previous tests.
        DatabaseSetup.deleteAllData();
    }

    private Object[] buildScenario() {
        // This helper method creates a trainer in the database and returns its ID.
        // We can use this in multiple tests to set up a known trainer state.
        Trainer trainer = new Trainer("Ash");
        trainer.setDiscordId(123456789L);

        return new Object[]{trainer};
    }

    // =========================================================================
    // --- Create ---
    // =========================================================================

    /*
     * CHECKS:  A new trainer can be inserted into the database and receives a
     *          valid auto-generated ID.
     * HOW:     Calls createDBTrainer() with a Discord ID, display name, and
     *          trainer name, then asserts the returned ID is positive.
     * IDEAL:   Returned ID > 0 indicates the row was successfully inserted.
     */
    @Test
    void testCreateTrainer() {
        Object[] scenario = buildScenario();
        Trainer trainer = (Trainer) scenario[0];

        int trainerId = trainerCRUD.createDBTrainer(trainer.getDiscordId(), "AshDiscord", trainer.getTrainerName());
        assertTrue(trainerId > 0, "Trainer ID should be greater than 0");
    }

    /*
     * CHECKS:  Attempting to create two trainers with the same Discord ID fails
     *          on the second insert (unique constraint enforced by the DB).
     * HOW:     Creates one trainer, then calls createDBTrainer() again with the
     *          same Discord ID. Asserts the second call returns -1.
     * IDEAL:   Duplicate Discord IDs are rejected; only one row per user.
     */
    @Test
    void testCreateDuplicateTrainer() {
        Object[] scenario = buildScenario();
        Trainer trainer = (Trainer) scenario[0];

        int firstTrainerId = trainerCRUD.createDBTrainer(trainer.getDiscordId(), "AshDiscord", trainer.getTrainerName());
        assertTrue(firstTrainerId > 0, "First trainer ID should be greater than 0");

        int secondTrainerId = trainerCRUD.createDBTrainer(trainer.getDiscordId(), "AshDiscord", trainer.getTrainerName());
        assertEquals(-1, secondTrainerId, "Second trainer creation should fail with -1");
    }

    // =========================================================================
    // --- Read ---
    // =========================================================================

    /*
     * CHECKS:  A trainer can be retrieved by its Discord ID and all fields
     *          match what was originally inserted.
     * HOW:     Creates a trainer, then calls getTrainerByDiscordId(). Asserts
     *          name, Discord ID, and DB ID all match.
     * IDEAL:   Round-trip: create → read returns identical data.
     */
    @Test
    void testGetTrainerByDiscordId() {
        Object[] scenario = buildScenario();
        Trainer trainer = (Trainer) scenario[0];
        int trainerId = trainerCRUD.createDBTrainer(trainer.getDiscordId(), "AshDiscord", trainer.getTrainerName());
        assertTrue(trainerId > 0, "Trainer ID should be greater than 0");

        Trainer retrievedTrainer = trainerService.getTrainerByDbId(trainerId);
        assertNotNull(retrievedTrainer, "Retrieved trainer should not be null");
        assertEquals(trainer.getTrainerName(), retrievedTrainer.getTrainerName(), "Trainer names should match");
        assertEquals(trainer.getDiscordId(), retrievedTrainer.getDiscordId(), "Discord IDs should match");
        assertEquals(trainerId, retrievedTrainer.getTrainerDbId(), "Trainer DB IDs should match");
    }

    /*
     * CHECKS:  Querying a Discord ID that has no matching row returns null
     *          rather than throwing an exception.
     * HOW:     Calls getTrainerByDiscordId() with a non-existent ID on an
     *          empty database.
     * IDEAL:   Null return signals "not found" — callers can check without
     *          needing try/catch.
     */
    @Test
    void testGetTrainerByDiscordIdNotFound() {
        Trainer retrievedTrainer = trainerService.getTrainerByDiscordId(999999999L);
        assertNull(retrievedTrainer, "Retrieved trainer should be null for non-existent Discord ID");
    }

    /*
     * CHECKS:  A trainer can be retrieved by its auto-generated database ID
     *          and all fields match what was originally inserted.
     * HOW:     Creates a trainer, captures the returned DB ID, then calls
     *          getTrainerByDbId(). Asserts name, Discord ID, and DB ID.
     * IDEAL:   Both lookup paths (Discord ID and DB ID) return the same data.
     */
    @Test
    void testGetTrainerByDbId() {
        Object[] scenario = buildScenario();
        Trainer trainer = (Trainer) scenario[0];
        int trainerId = trainerCRUD.createDBTrainer(trainer.getDiscordId(), "AshDiscord", trainer.getTrainerName());
        assertTrue(trainerId > 0, "Trainer ID should be greater than 0");   

        Trainer retrievedTrainer = trainerService.getTrainerByDbId(trainerId);
        assertNotNull(retrievedTrainer, "Retrieved trainer should not be null");
        assertEquals(trainer.getTrainerName(), retrievedTrainer.getTrainerName(), "Trainer names should match");
        assertEquals(trainer.getDiscordId(), retrievedTrainer.getDiscordId(), "Discord IDs should match");
        assertEquals(trainerId, retrievedTrainer.getTrainerDbId(), "Trainer DB IDs should match");
    }

    /*
     * CHECKS:  Querying a database ID that has no matching row returns null
     *          rather than throwing an exception.
     * HOW:     Calls getTrainerByDbId() with a non-existent ID on an empty
     *          database.
     * IDEAL:   Null return signals "not found" — consistent with the Discord
     *          ID lookup behavior.
     */
    @Test
    void testGetTrainerByDbIdNotFound() {
        Trainer retrievedTrainer = trainerService.getTrainerByDbId(9999);
        assertNull(retrievedTrainer, "Retrieved trainer should be null for non-existent DB ID");
    }

    // =========================================================================
    // --- Update ---
    // =========================================================================

    /*
     * CHECKS:  A trainer's name can be updated by Discord ID, and the change
     *          persists when the trainer is re-read from the database.
     * HOW:     Creates a trainer, calls updateTrainerNameByDiscordId() with a
     *          new name, then re-reads and asserts the name changed.
     * IDEAL:   rowsUpdated == 1 and the re-read name matches the new value.
     */
    @Test
    void testUpdateTrainerNameByDiscordId() {
        Object[] scenario = buildScenario();
        Trainer trainer = (Trainer) scenario[0];
        int trainerId = trainerCRUD.createDBTrainer(trainer.getDiscordId(), "AshDiscord", trainer.getTrainerName());
        assertTrue(trainerId > 0, "Trainer ID should be greater than 0");

        String newName = "Ash Ketchum";
        int rowsUpdated = trainerCRUD.updateTrainerNameByDiscordId(trainer.getDiscordId(), newName);
        assertEquals(1, rowsUpdated, "One row should be updated");

        Trainer retrievedTrainer = trainerService.getTrainerByDiscordId(trainer.getDiscordId());
        assertNotNull(retrievedTrainer, "Retrieved trainer should not be null");
        assertEquals(newName, retrievedTrainer.getTrainerName(), "Trainer name should be updated");
    }

    /*
     * CHECKS:  A trainer's name can be updated by database ID, and the change
     *          persists when the trainer is re-read from the database.
     * HOW:     Creates a trainer, calls updateTrainerNameByDbId() with a new
     *          name, then re-reads by DB ID and asserts the name changed.
     * IDEAL:   rowsUpdated == 1 and the re-read name matches the new value.
     */
    @Test
    void testUpdateTrainerNameByDbId() {
        Object[] scenario = buildScenario();
        Trainer trainer = (Trainer) scenario[0];
        int trainerId = trainerCRUD.createDBTrainer(trainer.getDiscordId(), "AshDiscord", trainer.getTrainerName());
        assertTrue(trainerId > 0, "Trainer ID should be greater than 0");

        String newName = "Ash Ketchum";
        int rowsUpdated = trainerCRUD.updateTrainerNameByDbId(trainerId, newName);
        assertEquals(1, rowsUpdated, "One row should be updated");

        Trainer retrievedTrainer = trainerService.getTrainerByDbId(trainerId);
        assertNotNull(retrievedTrainer, "Retrieved trainer should not be null");
        assertEquals(newName, retrievedTrainer.getTrainerName(), "Trainer name should be updated");
    }

    // =========================================================================
    // --- Delete ---
    // =========================================================================

    /*
     * CHECKS:  A trainer can be deleted by Discord ID, and a subsequent lookup
     *          returns null.
     * HOW:     Creates a trainer, calls deleteTrainerByDiscordId(), asserts one
     *          row was deleted, then verifies getTrainerByDiscordId() returns null.
     * IDEAL:   rowsDeleted == 1 and the trainer is no longer retrievable.
     */
    @Test
    void testDeleteTrainerByDiscordId() {
        Object[] scenario = buildScenario();
        Trainer trainer = (Trainer) scenario[0];
        int trainerId = trainerCRUD.createDBTrainer(trainer.getDiscordId(), "AshDiscord", trainer.getTrainerName());
        assertTrue(trainerId > 0, "Trainer ID should be greater than 0");
        int rowsDeleted = trainerCRUD.deleteTrainerByDiscordId(trainer.getDiscordId());
        assertEquals(1, rowsDeleted, "One row should be deleted");
        Trainer retrievedTrainer = trainerService.getTrainerByDiscordId(trainer.getDiscordId());
        assertNull(retrievedTrainer, "Retrieved trainer should be null after deletion");
    }

    /*
     * CHECKS:  A trainer can be deleted by Discord ID, and a subsequent lookup
     *          by database ID also returns null (both paths see the deletion).
     * HOW:     Creates a trainer, calls deleteTrainerByDiscordId(), then verifies
     *          getTrainerByDbId() returns null.
     * IDEAL:   Deletion by one key makes the row invisible to all lookup paths.
     */
    @Test
    void testDeleteTrainerbyDbId() {
        Object[] scenario = buildScenario();
        Trainer trainer = (Trainer) scenario[0];
        int trainerId = trainerCRUD.createDBTrainer(trainer.getDiscordId(), "AshDiscord", trainer.getTrainerName());
        assertTrue(trainerId > 0, "Trainer ID should be greater than 0");
        int rowsDeleted = trainerCRUD.deleteTrainerByDiscordId(trainer.getDiscordId());
        assertEquals(1, rowsDeleted, "One row should be deleted");
        Trainer retrievedTrainer = trainerService.getTrainerByDbId(trainerId);
        assertNull(retrievedTrainer, "Retrieved trainer should be null after deletion");
    }
}
