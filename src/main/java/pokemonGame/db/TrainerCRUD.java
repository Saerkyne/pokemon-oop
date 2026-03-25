package pokemonGame.db;
import java.sql.*;
import pokemonGame.Trainer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

    public Trainer getTrainerByDiscordId(long discordID) {
        try (Connection conn = DatabaseSetup.getConnection()) {
            String sql = "SELECT * FROM trainers WHERE discord_id = ?";

            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setLong(1, discordID);

                try (ResultSet rs = pstmt.executeQuery()) {
                    if (rs.next()) {
                        Trainer trainer = new Trainer(rs.getString("name"));
                        trainer.setDbId(rs.getInt("trainer_id")); // Set the trainer's ID from the database
                        trainer.setName(rs.getString("name")); // Set the trainer's name from the database
                        trainer.setDiscordId(rs.getLong("discord_id")); // Set the trainer's Discord ID from the database

                        LOGGER.info("Trainer '{}' retrieved successfully.", trainer.getName());
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
}
