package pokemonGame.db;
import java.sql.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pokemonGame.model.Trainer;

/**
 * This class provides CRUD operations for managing trainer objects in the database.
 * The only caller should be {@link TrainerService} to manage in-memory building or 
 * dehydration of trainer information. All trainer information passed is just IDs, 
 * no actual object construction happens here.
 */

public class TrainerCRUD {
    private static final Logger LOGGER = LoggerFactory.getLogger(TrainerCRUD.class);
    
    public int createDBTrainer(long discordID, String discordUsername, String name) {
        try (Connection conn = DatabaseSetup.getConnection()) {
            String sql = "INSERT INTO trainers (discord_id, discord_username, name) VALUES (?, ?, ?)";

            try (PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                pstmt.setLong(1, discordID);
                pstmt.setString(2, discordUsername);
                pstmt.setString(3, name);

                pstmt.executeUpdate();
                LOGGER.info("Trainer '{}' created successfully.", name);

                try (ResultSet trainerSet = pstmt.getGeneratedKeys()) {
                    if (trainerSet.next()) {
                        int trainerId = trainerSet.getInt(1);
                        LOGGER.info("New Trainer ID: {}", trainerId);
                        return trainerId; // Return the generated trainer ID
                    }
                }
            }
        } catch (SQLException e) {
            LOGGER.error("Error creating trainer: {}", e.getMessage(), e);
            return -1; // Return -1 to indicate an error occurred
        } 
        return -1; // Return -1 if trainer creation failed
    }

    // TODO: Refactor this to provide the DbId, we don't want rehydration here.
    public Trainer getTrainerByDiscordId(long discordID) {
        try (Connection conn = DatabaseSetup.getConnection()) {
            String sql = "SELECT * FROM trainers WHERE discord_id = ?";

            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setLong(1, discordID);

                try (ResultSet rs = pstmt.executeQuery()) {
                    if (rs.next()) {
                        Trainer trainer = new Trainer(rs.getString("name"));
                        trainer.setTrainerDbId(rs.getInt("trainer_id")); // Set the trainer's ID from the database
                        trainer.setDiscordId(rs.getLong("discord_id")); // Set the trainer's Discord ID from the database

                        LOGGER.info("Trainer '{}' retrieved successfully.", trainer.getTrainerName());
                        return trainer; // Return the retrieved trainer
                    } else {
                        LOGGER.warn("No trainer found with Discord ID: {}", discordID);
                        return null; // Return null if no trainer is found
                    }
                }
            }
        } catch (SQLException e) {
            LOGGER.error("Error retrieving trainer: {}", e.getMessage(), e);
            return null; // Return null to indicate an error occurred
        }
    }

    // TODO: Refactor this to return Discord ID
    public Trainer getTrainerByDbId(int trainerDbId) {
        try (Connection conn = DatabaseSetup.getConnection()) {
            String sql = "SELECT * FROM trainers WHERE trainer_id = ?";

            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setInt(1, trainerDbId);

                try (ResultSet rs = pstmt.executeQuery()) {
                    if (rs.next()) {
                        Trainer trainer = new Trainer(rs.getString("name"));
                        trainer.setTrainerDbId(rs.getInt("trainer_id")); // Set the trainer's ID from the database
                        trainer.setDiscordId(rs.getLong("discord_id")); // Set the trainer's Discord ID from the database

                        LOGGER.info("Trainer '{}' retrieved successfully by DB ID.", trainer.getTrainerName());
                        return trainer; // Return the retrieved trainer
                    } else {
                        LOGGER.warn("No trainer found with DB ID: {}", trainerDbId);
                        return null; // Return null if no trainer is found
                    }
                }
            }
        } catch (SQLException e) {
            LOGGER.error("Error retrieving trainer by DB ID: {}", e.getMessage(), e);
            return null; // Return null to indicate an error occurred
        }
    }

    public int deleteTrainerByDiscordId(long discordID) {
        try (Connection conn = DatabaseSetup.getConnection()) {
            String sql = "DELETE FROM trainers WHERE discord_id = ?";

            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setLong(1, discordID);
                int affectedRows = pstmt.executeUpdate();
                if (affectedRows > 0) {
                    LOGGER.info("Trainer with Discord ID: {} deleted successfully.", discordID);
                    return affectedRows; // Return the number of affected rows
                } else {
                    LOGGER.warn("No trainer found with Discord ID: {}", discordID);
                    return 0; // Return 0 to indicate no trainer was deleted
                }
            }
        } catch (SQLException e) {
            LOGGER.error("Error deleting trainer: {}", e.getMessage(), e);
            return -1; // Return -1 to indicate an error occurred
        }
    }

    public int deleteTrainerByDbId(int trainerDbId) {
        try (Connection conn = DatabaseSetup.getConnection()) {
            String sql = "DELETE FROM trainers WHERE trainer_id = ?";

            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setInt(1, trainerDbId);
                int affectedRows = pstmt.executeUpdate();
                if (affectedRows > 0) {
                    LOGGER.info("Trainer with DB ID: {} deleted successfully.", trainerDbId);
                    return affectedRows; // Return the number of affected rows
                } else {
                    LOGGER.warn("No trainer found with DB ID: {}", trainerDbId);
                    return 0; // Return 0 to indicate no trainer was deleted
                }
            }
        } catch (SQLException e) {
            LOGGER.error("Error deleting trainer by DB ID: {}", e.getMessage(), e);
            return -1; // Return -1 to indicate an error occurred
        }
    }

    public int updateTrainerNameByDiscordId(long discordID, String newName) {
        try (Connection conn = DatabaseSetup.getConnection()) {
            String sql = "UPDATE trainers SET name = ? WHERE discord_id = ?";

            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, newName);
                pstmt.setLong(2, discordID);
                int affectedRows = pstmt.executeUpdate();
                if (affectedRows > 0) {
                    LOGGER.info("Trainer with Discord ID: {} updated successfully to name: {}", discordID, newName);
                    return affectedRows; // Return the number of affected rows
                } else {
                    LOGGER.warn("No trainer found with Discord ID: {}", discordID);
                    return 0; // Return 0 to indicate no trainer was updated
                }
            }
        } catch (SQLException e) {
            LOGGER.error("Error updating trainer: {}", e.getMessage(), e);
            return -1; // Return -1 to indicate an error occurred
        }
    }

    public int updateTrainerNameByDbId(int trainerDbId, String newName) {
        try (Connection conn = DatabaseSetup.getConnection()) {
            String sql = "UPDATE trainers SET name = ? WHERE trainer_id = ?";

            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, newName);
                pstmt.setInt(2, trainerDbId);
                int affectedRows = pstmt.executeUpdate();
                if (affectedRows > 0) {
                    LOGGER.info("Trainer with DB ID: {} updated successfully to name: {}", trainerDbId, newName);
                    return affectedRows; // Return the number of affected rows
                } else {
                    LOGGER.warn("No trainer found with DB ID: {}", trainerDbId);
                    return 0; // Return 0 to indicate no trainer was updated
                }
            }
        } catch (SQLException e) {
            LOGGER.error("Error updating trainer by DB ID: {}", e.getMessage(), e);
            return -1; // Return -1 to indicate an error occurred
        }
    }

    // TODO: Method for returning trainer Discord Username via Discord ID
    // TODO: Method for returning trainer Discord Username via Database ID
    // TODO: Method for returning trainer name via Discord ID
    // TODO: Method for returning trainer name via Database ID 
}
