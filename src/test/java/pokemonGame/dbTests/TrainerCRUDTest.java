package pokemonGame.dbTests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import pokemonGame.db.TrainerCRUD;
import pokemonGame.db.DatabaseSetup;
import pokemonGame.model.Trainer;

class TrainerCRUDTest {

    private TrainerCRUD trainerCRUD = new TrainerCRUD();

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

    @Test
    void testCreateTrainer() {
        // This test will create a trainer record in the database and then attempt to retrieve it.
        // We will verify that the retrieved trainer matches the one we created.
        Object[] scenario = buildScenario();
        Trainer trainer = (Trainer) scenario[0];

        // Create the trainer in the database and get the generated ID
        int trainerId = trainerCRUD.createDBTrainer(trainer.getDiscordId(), "AshDiscord", trainer.getTrainerName());
        assertTrue(trainerId > 0, "Trainer ID should be greater than 0");
    }

    @Test
    void testGetTrainerByDiscordId() {
        // This test will create a trainer and then attempt to retrieve it by its Discord ID.
        Object[] scenario = buildScenario();
        Trainer trainer = (Trainer) scenario[0];
        int trainerId = trainerCRUD.createDBTrainer(trainer.getDiscordId(), "AshDiscord", trainer.getTrainerName());
        assertTrue(trainerId > 0, "Trainer ID should be greater than 0");

        Trainer retrievedTrainer = trainerCRUD.getTrainerByDiscordId(trainer.getDiscordId());
        assertNotNull(retrievedTrainer, "Retrieved trainer should not be null");
        assertEquals(trainer.getTrainerName(), retrievedTrainer.getTrainerName(), "Trainer names should match");
        assertEquals(trainer.getDiscordId(), retrievedTrainer.getDiscordId(), "Discord IDs should match");
        assertEquals(trainerId, retrievedTrainer.getTrainerDbId(), "Trainer DB IDs should match");
    }

    @Test
    void testGetTrainerByDiscordIdNotFound() {
        // This test will attempt to retrieve a trainer by a Discord ID that does not exist in the database.
        Trainer retrievedTrainer = trainerCRUD.getTrainerByDiscordId(999999999L);
        assertNull(retrievedTrainer, "Retrieved trainer should be null for non-existent Discord ID");
    }

    @Test
    void testCreateDuplicateTrainer() {
        // This test will attempt to create two trainers with the same Discord ID and verify that the second creation fails.
        Object[] scenario = buildScenario();
        Trainer trainer = (Trainer) scenario[0];

        int firstTrainerId = trainerCRUD.createDBTrainer(trainer.getDiscordId(), "AshDiscord", trainer.getTrainerName());
        assertTrue(firstTrainerId > 0, "First trainer ID should be greater than 0");

        int secondTrainerId = trainerCRUD.createDBTrainer(trainer.getDiscordId(), "AshDiscord", trainer.getTrainerName());
        assertEquals(-1, secondTrainerId, "Second trainer creation should fail with -1");
    }   

    @Test
    void testGetTrainerByDbId() {
        // This test will create a trainer and then attempt to retrieve it by its database ID.
        Object[] scenario = buildScenario();
        Trainer trainer = (Trainer) scenario[0];
        int trainerId = trainerCRUD.createDBTrainer(trainer.getDiscordId(), "AshDiscord", trainer.getTrainerName());
        assertTrue(trainerId > 0, "Trainer ID should be greater than 0");   

        Trainer retrievedTrainer = trainerCRUD.getTrainerByDbId(trainerId);
        assertNotNull(retrievedTrainer, "Retrieved trainer should not be null");
        assertEquals(trainer.getTrainerName(), retrievedTrainer.getTrainerName(), "Trainer names should match");
        assertEquals(trainer.getDiscordId(), retrievedTrainer.getDiscordId(), "Discord IDs should match");
        assertEquals(trainerId, retrievedTrainer.getTrainerDbId(), "Trainer DB IDs should match");
    }

    @Test
    void testGetTrainerByDbIdNotFound() {
        // This test will attempt to retrieve a trainer by a database ID that does not exist.
        Trainer retrievedTrainer = trainerCRUD.getTrainerByDbId(9999);
        assertNull(retrievedTrainer, "Retrieved trainer should be null for non-existent DB ID");
    }

    @Test
    void testDeleteTrainerByDiscordId() {
        // This test will create a trainer, delete it by its Discord ID, and then verify that it can no longer be retrieved.
        Object[] scenario = buildScenario();
        Trainer trainer = (Trainer) scenario[0];
        int trainerId = trainerCRUD.createDBTrainer(trainer.getDiscordId(), "AshDiscord", trainer.getTrainerName());
        assertTrue(trainerId > 0, "Trainer ID should be greater than 0");
        int rowsDeleted = trainerCRUD.deleteTrainerByDiscordId(trainer.getDiscordId());
        assertEquals(1, rowsDeleted, "One row should be deleted");
        Trainer retrievedTrainer = trainerCRUD.getTrainerByDiscordId(trainer.getDiscordId());
        assertNull(retrievedTrainer, "Retrieved trainer should be null after deletion");
    }

    @Test
    void testDeleteTrainerbyDbId() {
        // This test will create a trainer, delete it by its database ID, and then verify that it can no longer be retrieved.
        Object[] scenario = buildScenario();
        Trainer trainer = (Trainer) scenario[0];
        int trainerId = trainerCRUD.createDBTrainer(trainer.getDiscordId(), "AshDiscord", trainer.getTrainerName());
        assertTrue(trainerId > 0, "Trainer ID should be greater than 0");
        int rowsDeleted = trainerCRUD.deleteTrainerByDiscordId(trainer.getDiscordId());
        assertEquals(1, rowsDeleted, "One row should be deleted");
        Trainer retrievedTrainer = trainerCRUD.getTrainerByDbId(trainerId);
        assertNull(retrievedTrainer, "Retrieved trainer should be null after deletion");
    }

    @Test
    void testUpdateTrainerNameByDiscordId() {
        // This test will create a trainer, update its name, and then verify that the updated name is correctly stored in the database.
        Object[] scenario = buildScenario();
        Trainer trainer = (Trainer) scenario[0];
        int trainerId = trainerCRUD.createDBTrainer(trainer.getDiscordId(), "AshDiscord", trainer.getTrainerName());
        assertTrue(trainerId > 0, "Trainer ID should be greater than 0");

        String newName = "Ash Ketchum";
        int rowsUpdated = trainerCRUD.updateTrainerNameByDiscordId(trainer.getDiscordId(), newName);
        assertEquals(1, rowsUpdated, "One row should be updated");

        Trainer retrievedTrainer = trainerCRUD.getTrainerByDiscordId(trainer.getDiscordId());
        assertNotNull(retrievedTrainer, "Retrieved trainer should not be null");
        assertEquals(newName, retrievedTrainer.getTrainerName(), "Trainer name should be updated");
    }

    @Test
    void testUpdateTrainerNameByDbId() {
        // This test will create a trainer, update its name by DB ID, and then verify that the updated name is correctly stored in the database.
        Object[] scenario = buildScenario();
        Trainer trainer = (Trainer) scenario[0];
        int trainerId = trainerCRUD.createDBTrainer(trainer.getDiscordId(), "AshDiscord", trainer.getTrainerName());
        assertTrue(trainerId > 0, "Trainer ID should be greater than 0");

        String newName = "Ash Ketchum";
        int rowsUpdated = trainerCRUD.updateTrainerNameByDbId(trainerId, newName);
        assertEquals(1, rowsUpdated, "One row should be updated");

        Trainer retrievedTrainer = trainerCRUD.getTrainerByDbId(trainerId);
        assertNotNull(retrievedTrainer, "Retrieved trainer should not be null");
        assertEquals(newName, retrievedTrainer.getTrainerName(), "Trainer name should be updated");
    }
}
