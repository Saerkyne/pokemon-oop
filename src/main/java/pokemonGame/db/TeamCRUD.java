package pokemonGame.db;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
/*
* Need to add a way for a single trainer to manage multiple teams (e.g., for 
* battling multiple friends). This is a more complex schema change, but it allows for much more flexibility.
* New schema for trainer_teams:
* CREATE TABLE trainer_teams (
*     team_id      INT AUTO_INCREMENT PRIMARY KEY,
*     trainer_id   INT NOT NULL,
*     team_name    VARCHAR(50) NOT NULL,
*     slot_index   SMALLINT NOT NULL,
*     instance_id  INT NOT NULL,
*     UNIQUE (trainer_id, team_name, slot_index),
*     FOREIGN KEY (trainer_id) REFERENCES trainers(trainer_id),
*     FOREIGN KEY (instance_id) REFERENCES pokemon_instances(instance_id),
*     CHECK (slot_index BETWEEN 0 AND 5)
* );
*/

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pokemonGame.model.Pokemon;
import pokemonGame.model.Team;
import pokemonGame.model.Trainer;

public class TeamCRUD {
    private static final Logger LOGGER = LoggerFactory.getLogger(TeamCRUD.class);


    public int addPokemonToDBTeam(int trainerId, int pokemonId, int teamId) {
        try (Connection conn = DatabaseSetup.getConnection()) {
            int slotIndex = checkSlotIndex(trainerId, teamId);

            if (slotIndex == -1) {
                LOGGER.error("Error checking slot index for trainer ID {}", trainerId);
                return -1; // Return -1 to indicate an error occurred
            } else if (slotIndex >= 6) {
                LOGGER.warn("Trainer ID {}'s team is full! Cannot add more Pokemon.", trainerId);
                return -3; // Return -3 to indicate the team is full
            }
            String sql = "INSERT INTO trainer_teams (trainer_id, team_id, team_name, instance_id, slot_index) VALUES (?, ?, ?, ?, ?)";
            String teamName = getTeamName(trainerId, teamId);
            if (teamName == null) {
                teamName = "Default Team Name"; // Fallback team name if not found
            }

            try (PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                pstmt.setInt(1, trainerId);
                pstmt.setInt(2, teamId);
                pstmt.setString(3, teamName); // Use the retrieved or fallback team name
                pstmt.setInt(4, pokemonId);
                pstmt.setInt(5, slotIndex);
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

    public int checkSlotIndex(int trainerId, int team_id) {
        try (Connection conn = DatabaseSetup.getConnection()) {
            String sql = "SELECT COUNT(*) AS slot_count FROM trainer_teams WHERE trainer_id = ? AND team_id = ?";

            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setInt(1, trainerId);
                pstmt.setInt(2, team_id);
 
                try (ResultSet rs = pstmt.executeQuery()) {
                    if (rs.next()) {
                        int slotCount = rs.getInt("slot_count");
                        LOGGER.info("Trainer ID {} currently has {} Pokemon in their team (team_id={}).", trainerId, slotCount, team_id);
                        return slotCount; // Return the current count of Pokémon in the team
                    }
                }
            }
        } catch (SQLException e) {
            LOGGER.error("Error checking slot index: {}", e.getMessage(), e);
            return -1; // Return -1 to indicate an error occurred
        }
        return -1; // Return -1 if checking slot index failed
    }

    public Boolean removePokemonFromDBTeam(int trainerDbId, int teamId, int slotIndex) {

        PokemonCRUD pokemonCRUD = new PokemonCRUD();
        Pokemon pokemonToDelete = getPokemonInSlotForTrainer(trainerDbId, teamId, slotIndex);

        try (Connection conn = DatabaseSetup.getConnection()) {
            String sql = "DELETE FROM trainer_teams WHERE trainer_id = ? AND team_id = ? AND slot_index = ?";

            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setInt(1, trainerDbId);
                pstmt.setInt(2, teamId);
                pstmt.setInt(3, slotIndex);

                int affectedRows = pstmt.executeUpdate();
                if (affectedRows > 0) {
                    LOGGER.info("Pokemon in slot {} removed from trainer ID {}'s team.", slotIndex, trainerDbId);

                    pokemonCRUD.deleteDBPokemon(pokemonToDelete);
                    return true; // Return true to indicate successful removal
                } else {
                    LOGGER.warn("No Pokemon in slot {} found in trainer ID {}'s team.", slotIndex, trainerDbId);
                    return false; // Return false to indicate no Pokemon found to remove
                }
            }
        } catch (SQLException e) {
            LOGGER.error("Error removing Pokemon from team: {}", e.getMessage(), e);
            return false; // Return false to indicate an error occurred
        }
    }

    public Team getDBTeamForTrainer(int trainerDbId, int teamId) {
        List<Pokemon> team = new ArrayList<>();
        try (Connection conn = DatabaseSetup.getConnection()) {
            String sql = "SELECT p.* FROM pokemon_instances p "
                    + "JOIN trainer_teams tt ON p.instance_id = tt.instance_id "
                    + "WHERE tt.trainer_id = ? AND tt.team_id = ? ORDER BY tt.slot_index";

            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setInt(1, trainerDbId);
                pstmt.setInt(2, teamId);

                try (ResultSet rs = pstmt.executeQuery()) {
                    while (rs.next()) {
                        LOGGER.info("Mapping Pokemon from database for trainer ID {}: instance_id={}, species={}, level={}", 
                                trainerDbId, rs.getInt("instance_id"), rs.getString("species"), rs.getInt("level"));
                        TrainerCRUD trainerCRUD = new TrainerCRUD();
                        Trainer trainer = trainerCRUD.getTrainerByDbId(trainerDbId);
                        Pokemon pokemon = PokemonCRUD.mapResultSetToPokemon(rs, trainer);
                        
                        LOGGER.info("Mapped Pokemon for trainer ID {}: instance_id={}, species={}, level={}", 
                                trainerDbId, pokemon.getPokemonDbId(), pokemon.getSpecies().getDisplayName(), pokemon.getLevel());
                        LOGGER.info("Current HP is {} for Pokemon with instance_id {} in trainer ID {}'s team.", pokemon.getCurrentHP(), pokemon.getPokemonDbId(), trainerDbId);
                        team.add(pokemon);
                    }
                }
            }
        } catch (SQLException e) {
            LOGGER.error("Error retrieving team for trainer ID {}: {}", trainerDbId, e.getMessage(), e);
        }
        Team resultTeam = new Team("Loaded Team");
        resultTeam.setTeamAsList(team);
        return resultTeam;
    }

    public Pokemon getPokemonInSlotForTrainer(int trainerDbId, int teamId, int slotIndex) {
        try (Connection conn = DatabaseSetup.getConnection()) {
            String sql = "SELECT p.* FROM pokemon_instances p "
                    + "JOIN trainer_teams tt ON p.instance_id = tt.instance_id "
                    + "WHERE tt.trainer_id = ? AND tt.team_id = ? AND tt.slot_index = ?";

            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setInt(1, trainerDbId);
                pstmt.setInt(2, teamId);
                pstmt.setInt(3, slotIndex);

                try (ResultSet rs = pstmt.executeQuery()) {
                    if (rs.next()) {
                        LOGGER.info("Mapping Pokemon from database for trainer ID {}: instance_id={}, species={}, level={}", 
                                trainerDbId, rs.getInt("instance_id"), rs.getString("species"), rs.getInt("level"));
                        TrainerCRUD trainerCRUD = new TrainerCRUD();
                        Trainer trainer = trainerCRUD.getTrainerByDbId(trainerDbId);
                        return PokemonCRUD.mapResultSetToPokemon(rs, trainer);
                    }
                }
            }
        } catch (SQLException e) {
            LOGGER.error("Error retrieving Pokemon in slot {} for trainer ID {}: {}", slotIndex, trainerDbId, e.getMessage(), e);
        }
        return null; // Return null if retrieving Pokemon failed
    }

    public int reorderTeamAfterRelease(int trainerId, int teamId) {
        try (Connection conn = DatabaseSetup.getConnection()) {
            String sql = "SELECT instance_id FROM trainer_teams WHERE trainer_id = ? AND team_id = ? ORDER BY slot_index";
            List<Integer> instanceIds = new ArrayList<>();

            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setInt(1, trainerId);
                pstmt.setInt(2, teamId);


                try (ResultSet rs = pstmt.executeQuery()) {
                    while (rs.next()) {
                        LOGGER.info("Found Pokemon instance_id {} for trainer ID {} during reordering after release.", rs.getInt("instance_id"), trainerId);
                        instanceIds.add(rs.getInt("instance_id"));
                    }
                }
            }

            String updateSql = "UPDATE trainer_teams SET slot_index = ? WHERE instance_id = ? AND trainer_id = ? AND team_id = ?";
            try (PreparedStatement updatePstmt = conn.prepareStatement(updateSql)) {
                for (int i = 0; i < instanceIds.size(); i++) {
                    updatePstmt.setInt(1, i); // Set the new slot index
                    updatePstmt.setInt(2, instanceIds.get(i)); // Set the instance_id
                    updatePstmt.setInt(3, trainerId); // Set the trainer_id
                    updatePstmt.setInt(4, teamId); // Set the team_id
                    updatePstmt.addBatch(); // Add to batch
                    LOGGER.info("Prepared update for Pokemon instance_id {} to slot index {} for trainer ID {}.", instanceIds.get(i), i, trainerId);
                }
                updatePstmt.executeBatch(); // Execute the batch
            }
            return 0; // Return 0 to indicate successful reordering
        } catch (SQLException e) {
            LOGGER.error("Error reordering team after release for trainer ID {}: {}", trainerId, e.getMessage(), e);
            return -1; // Return -1 to indicate an error occurred
        }
    }

    public int getSlotIndexForPokemon(int dbId, int teamId, int id) {
        try (Connection conn = DatabaseSetup.getConnection()) {
            String sql = "SELECT slot_index FROM trainer_teams WHERE trainer_id = ? AND team_id = ? AND instance_id = ?";

            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setInt(1, dbId);
                pstmt.setInt(2, teamId);
                pstmt.setInt(3, id);

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

    public String getTeamName(int trainerId, int teamId) {
        try (Connection conn = DatabaseSetup.getConnection()) {
            String sql = "SELECT team_name FROM trainer_teams WHERE trainer_id = ? AND team_id = ?";

            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setInt(1, trainerId);
                pstmt.setInt(2, teamId);

                try (ResultSet rs = pstmt.executeQuery()) {
                    if (rs.next()) {
                        String teamName = rs.getString("team_name");
                        LOGGER.info("Retrieved team name '{}' for trainer ID {} and team ID {}.", teamName, trainerId, teamId);
                        return teamName; // Return the team name
                    } else {
                        LOGGER.warn("No team found for trainer ID {} and team ID {}.", trainerId, teamId);
                        return null; // Return null to indicate no team found
                    }
                }
            }
        } catch (SQLException e) {
            LOGGER.error("Error retrieving team name for trainer ID {} and team ID {}: {}", trainerId, teamId, e.getMessage(), e);
            return null; // Return null to indicate an error occurred
        }
    }

    public int createTeamForTrainer(int trainerId, String teamName) {
        try (Connection conn = DatabaseSetup.getConnection()) {
            String sql = "INSERT INTO trainer_teams (trainer_id, team_name) VALUES (?, ?)";
            try (PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                pstmt.setInt(1, trainerId);
                pstmt.setString(2, teamName);
                int rowsAffected = pstmt.executeUpdate();
                if (rowsAffected > 0) {
                    try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                        if (generatedKeys.next()) {
                            int teamId = generatedKeys.getInt(1);
                            LOGGER.info("Created new team '{}' with ID {} for trainer ID {}.", teamName, teamId, trainerId);
                            return teamId; // Return the generated team ID
                        }
                    }
                }
            }
        } catch (SQLException e) {
            LOGGER.error("Error creating team '{}' for trainer ID {}: {}", teamName, trainerId, e.getMessage(), e);
        }
        return -1; // Return -1 to indicate an error occurred
    }

    public int getActiveTeamIdForTrainer(int trainerId) {
        try (Connection conn = DatabaseSetup.getConnection()) {
            String sql = "SELECT team_id FROM trainer_teams WHERE trainer_id = ? ORDER BY team_id LIMIT 1"; // Assuming the first team is the active one

            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setInt(1, trainerId);

                try (ResultSet rs = pstmt.executeQuery()) {
                    if (rs.next()) {
                        int teamId = rs.getInt("team_id");
                        LOGGER.info("Retrieved active team ID {} for trainer ID {}.", teamId, trainerId);
                        return teamId; // Return the active team ID
                    } else {
                        LOGGER.warn("No active team found for trainer ID {}.", trainerId);
                        return -1; // Return -1 to indicate no active team found
                    }
                }
            }
        } catch (SQLException e) {
            LOGGER.error("Error retrieving active team ID for trainer ID {}: {}", trainerId, e.getMessage(), e);
            return -1; // Return -1 to indicate an error occurred
        }
    }

    public List<String> getTeamNamesForTrainer(int trainerId) {
        List<String> teamNames = new ArrayList<>();
        try (Connection conn = DatabaseSetup.getConnection()) {
            String sql = "SELECT DISTINCT team_name FROM trainer_teams WHERE trainer_id = ?";

            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setInt(1, trainerId);

                try (ResultSet rs = pstmt.executeQuery()) {
                    while (rs.next()) {
                        String teamName = rs.getString("team_name");
                        LOGGER.info("Found team name '{}' for trainer ID {}.", teamName, trainerId);
                        teamNames.add(teamName); // Add the team name to the list
                    }
                }
            }
        } catch (SQLException e) {
            LOGGER.error("Error retrieving team names for trainer ID {}: {}", trainerId, e.getMessage(), e);
        }
        return teamNames; // Return the list of team names (empty if none found or an error occurred)
    }

    public Team getTeamByNameForTrainer(int trainerId, String teamName) {
        try (Connection conn = DatabaseSetup.getConnection()) {
            String sql = "SELECT team_id FROM trainer_teams WHERE trainer_id = ? AND team_name = ? LIMIT 1";

            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setInt(1, trainerId);
                pstmt.setString(2, teamName);

                try (ResultSet rs = pstmt.executeQuery()) {
                    if (rs.next()) {
                        int teamId = rs.getInt("team_id");
                        LOGGER.info("Retrieved team ID {} for team name '{}' and trainer ID {}.", teamId, teamName, trainerId);
                        return getDBTeamForTrainer(trainerId, teamId); // Return the Team object for the found team ID
                    } else {
                        LOGGER.warn("No team found with name '{}' for trainer ID {}.", teamName, trainerId);
                        return null; // Return null to indicate no team found
                    }
                }
            }
        } catch (SQLException e) {
            LOGGER.error("Error retrieving team by name '{}' for trainer ID {}: {}", teamName, trainerId, e.getMessage(), e);
            return null; // Return null to indicate an error occurred
        }
    }
}
