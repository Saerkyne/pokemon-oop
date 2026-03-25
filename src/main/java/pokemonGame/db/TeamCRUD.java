package pokemonGame.db;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import pokemonGame.Pokemon;
import pokemonGame.Trainer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TeamCRUD {
    private static final Logger LOGGER = LoggerFactory.getLogger(TeamCRUD.class);


    public int addPokemonToDBTeam(int trainerId, int pokemonId) {
        try (Connection conn = DatabaseSetup.getConnection()) {
            int slotIndex = checkSlotIndex(trainerId);

            if (slotIndex == -1) {
                LOGGER.error("Error checking slot index for trainer ID {}", trainerId);
                return -1; // Return -1 to indicate an error occurred
            } else if (slotIndex >= 6) {
                LOGGER.warn("Trainer ID {}'s team is full! Cannot add more Pokemon.", trainerId);
                return -3; // Return -3 to indicate the team is full
            }
            String sql = "INSERT INTO trainer_teams (trainer_id, instance_id, slot_index) VALUES (?, ?, ?)";

            try (PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                pstmt.setInt(1, trainerId);
                pstmt.setInt(2, pokemonId);
                pstmt.setInt(3, slotIndex);
                LOGGER.info("Attempting to add Pokemon with ID {} to trainer ID {}'s team in slot {}...", pokemonId, trainerId, slotIndex);

                int rowsAffected = pstmt.executeUpdate();
                if (rowsAffected > 0) {
                    LOGGER.info("Pokemon with ID {} added to trainer ID {}'s team in slot {}.", pokemonId, trainerId, slotIndex);
                    // Some DB schemas for trainer_teams may not use an auto-generated primary key, so generated keys can be empty.
                    // We already know the insertion slot index, so return it.
                    return slotIndex;
                } else {
                    LOGGER.error("Failed to insert team entry for trainer ID {} and pokemon ID {}.", trainerId, pokemonId);
                    return -1;
                }
            }
        } catch (SQLException e) {
            LOGGER.error("Error adding Pokemon to team: {}", e.getMessage(), e);
            e.printStackTrace();
            return -1; // Return -1 to indicate an error occurred
        }
    }

    public int checkSlotIndex(int trainerId) {
        try (Connection conn = DatabaseSetup.getConnection()) {
            String sql = "SELECT COUNT(*) AS slot_index FROM trainer_teams WHERE trainer_id = ?";

            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setInt(1, trainerId);

                try (ResultSet rs = pstmt.executeQuery()) {
                    if (rs.next()) {
                        LOGGER.info("Trainer ID {} currently has {} Pokemon in their team.", trainerId, rs.getInt("slot_index"));   
                        int slotIndex = rs.getInt("slot_index");
                        return slotIndex; // Return the current count of Pokémon in the team
                    }
                }
            }
        } catch (SQLException e) {
            LOGGER.error("Error checking slot index: {}", e.getMessage(), e);
            return -1; // Return -1 to indicate an error occurred
        }
        return -1; // Return -1 if checking slot index failed
    }

    public Boolean removePokemonFromDBTeam(int trainerId, int slotIndex) {

        PokemonCRUD pokemonCRUD = new PokemonCRUD();
        Trainer trainer = new Trainer("empty");
        trainer.setDbId(trainerId);
        Pokemon pokemonToDelete = getPokemonInSlotForTrainer(trainer, slotIndex);

        try (Connection conn = DatabaseSetup.getConnection()) {
            String sql = "DELETE FROM trainer_teams WHERE trainer_id = ? AND slot_index = ?";

            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setInt(1, trainerId);
                pstmt.setInt(2, slotIndex);

                int affectedRows = pstmt.executeUpdate();
                if (affectedRows > 0) {
                    LOGGER.info("Pokemon in slot {} removed from trainer ID {}'s team.", slotIndex, trainerId);

                    pokemonCRUD.deleteDBPokemon(pokemonToDelete);
                    return true; // Return true to indicate successful removal
                } else {
                    LOGGER.warn("No Pokemon in slot {} found in trainer ID {}'s team.", slotIndex, trainerId);
                    return false; // Return false to indicate no Pokemon found to remove
                }
            }
        } catch (SQLException e) {
            LOGGER.error("Error removing Pokemon from team: {}", e.getMessage(), e);
            return false; // Return false to indicate an error occurred
        }
    }

    public List<Pokemon> getDBTeamForTrainer(Trainer trainer) {
        List<Pokemon> team = new ArrayList<>();
        try (Connection conn = DatabaseSetup.getConnection()) {
            String sql = "SELECT p.* FROM pokemon_instances p "
                    + "JOIN trainer_teams tt ON p.instance_id = tt.instance_id "
                    + "WHERE tt.trainer_id = ? ORDER BY tt.slot_index";

            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setInt(1, trainer.getDbId());

                try (ResultSet rs = pstmt.executeQuery()) {
                    while (rs.next()) {
                        LOGGER.info("Mapping Pokemon from database for trainer ID {}: instance_id={}, species={}, level={}", 
                                trainer.getDbId(), rs.getInt("instance_id"), rs.getString("species"), rs.getInt("level"));
                        Pokemon pokemon = PokemonCRUD.mapResultSetToPokemon(rs, trainer);
                        team.add(pokemon);
                    }
                }
            }
        } catch (SQLException e) {
            LOGGER.error("Error retrieving team for trainer ID {}: {}", trainer.getDbId(), e.getMessage(), e);
        }
        return team;
    }

    public Pokemon getPokemonInSlotForTrainer(Trainer trainer, int slotIndex) {
        try (Connection conn = DatabaseSetup.getConnection()) {
            String sql = "SELECT p.* FROM pokemon_instances p "
                    + "JOIN trainer_teams tt ON p.instance_id = tt.instance_id "
                    + "WHERE tt.trainer_id = ? AND tt.slot_index = ?";

            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setInt(1, trainer.getDbId());
                pstmt.setInt(2, slotIndex);

                try (ResultSet rs = pstmt.executeQuery()) {
                    if (rs.next()) {
                        LOGGER.info("Mapping Pokemon from database for trainer ID {}: instance_id={}, species={}, level={}", 
                                trainer.getDbId(), rs.getInt("instance_id"), rs.getString("species"), rs.getInt("level"));
                        return PokemonCRUD.mapResultSetToPokemon(rs, trainer);
                    }
                }
            }
        } catch (SQLException e) {
            LOGGER.error("Error retrieving Pokemon in slot {} for trainer ID {}: {}", slotIndex, trainer.getDbId(), e.getMessage(), e);
        }
        return null; // Return null if retrieving Pokemon failed
    }

    public int reorderTeamAfterRelease(int trainerId) {
        try (Connection conn = DatabaseSetup.getConnection()) {
            String sql = "SELECT instance_id FROM trainer_teams WHERE trainer_id = ? ORDER BY slot_index";
            List<Integer> instanceIds = new ArrayList<>();

            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setInt(1, trainerId);

                try (ResultSet rs = pstmt.executeQuery()) {
                    while (rs.next()) {
                        LOGGER.info("Found Pokemon instance_id {} for trainer ID {} during reordering after release.", rs.getInt("instance_id"), trainerId);
                        instanceIds.add(rs.getInt("instance_id"));
                    }
                }
            }

            for (int i = 0; i < instanceIds.size(); i++) {
                String updateSql = "UPDATE trainer_teams SET slot_index = ? WHERE instance_id = ?";
                try (PreparedStatement updatePstmt = conn.prepareStatement(updateSql)) {
                    updatePstmt.setInt(1, i);
                    updatePstmt.setInt(2, instanceIds.get(i));
                    updatePstmt.executeUpdate();
                    LOGGER.info("Updated slot_index for Pokemon instance_id {} to {} for trainer ID {}.", instanceIds.get(i), i, trainerId);

                }
            }
            return 0; // Return 0 to indicate successful reordering
        } catch (SQLException e) {
            LOGGER.error("Error reordering team after release for trainer ID {}: {}", trainerId, e.getMessage(), e);
            return -1; // Return -1 to indicate an error occurred
        }
    }

    public int getSlotIndexForPokemon(int dbId, int id) {
        try (Connection conn = DatabaseSetup.getConnection()) {
            String sql = "SELECT slot_index FROM trainer_teams WHERE trainer_id = ? AND instance_id = ?";

            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setInt(1, dbId);
                pstmt.setInt(2, id);

                try (ResultSet rs = pstmt.executeQuery()) {
                    if (rs.next()) {
                        int slotIndex = rs.getInt("slot_index");
                        LOGGER.info("Found Pokemon with instance_id {} in slot {} for trainer ID {}.", id, slotIndex, dbId);
                        return slotIndex; // Return the slot index of the Pokémon
                    } else {
                        LOGGER.warn("No Pokemon with instance_id {} found for trainer ID {}.", id, dbId);
                        return -1; // Return -1 to indicate no Pokémon found
                    }
                }
            }
        } catch (SQLException e) {
            LOGGER.error("Error retrieving slot index for Pokemon with instance_id {} and trainer ID {}: {}", id, dbId, e.getMessage(), e);
            return -1; // Return -1 to indicate an error occurred
        }
    }
}
