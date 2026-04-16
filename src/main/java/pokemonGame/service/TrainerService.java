package pokemonGame.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pokemonGame.db.TrainerCRUD;
import pokemonGame.model.Trainer;

/**
 * Service layer for trainer-related operations. Sits between the bot/controller
 * layer and the persistence layer ({@link TrainerCRUD}), coordinating
 * validation and multi-step workflows.
 *
 * <p><b>Why this class exists:</b> Previously, {@link Trainer} contained a
 * static {@code createTrainer()} method that called {@link TrainerCRUD}
 * directly — mixing domain modeling with persistence. The bot layer
 * ({@code SlashExample}) also instantiated {@code TrainerCRUD} directly for
 * lookups. This service centralizes all trainer-related business logic:</p>
 *
 * <ul>
 *   <li>Checking if a trainer already exists before creating a new one</li>
 *   <li>Persisting the trainer via {@link TrainerCRUD}</li>
 *   <li>Building and returning a {@link Trainer} domain object</li>
 * </ul>
 *
 * <p><b>The layered call flow becomes:</b></p>
 * <pre>
 *   SlashExample (parse Discord input)
 *       → TrainerService (validate, coordinate, log)
 *           → TrainerCRUD (execute SQL)
 *           → Trainer (build in-memory domain object)
 *       ← returns result
 *   SlashExample (format Discord reply)
 * </pre>
 *
 * @see Trainer
 * @see TrainerCRUD
 * @see TeamService
 */
public class TrainerService {

    private static final Logger LOGGER = LoggerFactory.getLogger(TrainerService.class);

    private final TrainerCRUD trainerCRUD;

    // TODO: SVC-3 — Add constructor injection (TrainerService(TrainerCRUD)) for testability. Keep this as convenience default.
    public TrainerService() {
        this.trainerCRUD = new TrainerCRUD();
    }

    /**
     * Creates a new trainer if one does not already exist for the given
     * Discord ID. Returns the newly created {@link Trainer} domain object,
     * or {@code null} if a trainer already exists (i.e. duplicate signup).
     *
     * <p>This replaces the static {@code Trainer.createTrainer()} method,
     * which mixed domain construction with DAO calls and console I/O.</p>
     *
     * @param trainerName     the display name chosen by the player
     * @param discordId       the player's Discord user ID
     * @param discordUsername  the player's Discord username (for DB storage)
     * @return the new {@link Trainer} with its database ID set, or
     *         {@code null} if a trainer already exists for this Discord ID
     */
    public Trainer createTrainer(String trainerName, long discordId, String discordUsername) {
        // Check for existing trainer first
        Trainer existing = trainerCRUD.getTrainerByDiscordId(discordId);
        if (existing != null) {
            LOGGER.info("Trainer already exists for Discord ID {}: '{}'.", discordId, existing.getTrainerName());
            return null; // Caller interprets null as "already exists"
        }

        // Persist to database
        int trainerId = trainerCRUD.createDBTrainer(discordId, discordUsername, trainerName);
        if (trainerId == -1) {
            LOGGER.error("Failed to persist trainer '{}' to database.", trainerName);
            return null;
        }

        // Build domain object
        Trainer trainer = new Trainer(trainerName);
        trainer.setTrainerDbId(trainerId);
        trainer.setDiscordId(discordId);

        LOGGER.info("Created trainer '{}' (DB ID {}) for Discord ID {}.", trainerName, trainerId, discordId);
        return trainer;
    }

    /**
     * Looks up a trainer by their Discord user ID.
     *
     * <p>This replaces the direct {@code trainerCRUD.getTrainerByDiscordId()}
     * calls that were previously scattered across {@code SlashExample}.</p>
     *
     * @param discordId the Discord user ID
     * @return the {@link Trainer} if found, or {@code null}
     */
    public Trainer getTrainerByDiscordId(long discordId) {
        return trainerCRUD.getTrainerByDiscordId(discordId);
    }

    /**
     * Looks up a trainer by their database primary key.
     *
     * @param dbId the trainer's database ID
     * @return the {@link Trainer} if found, or {@code null}
     */
    public Trainer getTrainerByDbId(int dbId) {
        return trainerCRUD.getTrainerByDbId(dbId);
    }

    /**
     * Deletes a trainer by their Discord user ID.
     *
     * @param discordId the Discord user ID
     * @return the number of rows deleted, 0 if not found, or -1 on error
     */
    public int deleteTrainer(long discordId) {
        return trainerCRUD.deleteTrainerByDiscordId(discordId);
    }

    /**
     * Updates a trainer's display name.
     *
     * @param discordId the Discord user ID
     * @param newName   the new display name
     * @return the number of rows updated, 0 if not found, or -1 on error
     */
    public int updateTrainerName(long discordId, String newName) {
        return trainerCRUD.updateTrainerNameByDiscordId(discordId, newName);
    }
}
