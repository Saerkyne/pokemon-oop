package pokemonGame.db;

import java.sql.*;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This class provides CRUD operations for managing Pokémon teams in the database.
 * The only caller should be {@link TeamService} to manage in-memory building or 
 * dehydration of team information. All team information passed is just IDs, 
 * no actual object construction happens here. 
 */
public class TeamCRUD {
    private static final Logger LOGGER = LoggerFactory.getLogger(TeamCRUD.class);
    private static final int MAX_TEAM_SIZE = 6;

    public int addPokemonToDBTeam(int trainerId, int pokemonId, int teamId) {
        try (Connection conn = DatabaseSetup.getConnection()) {
            String teamName = getTeamName(conn, trainerId, teamId);
            if (teamName == null) {
                LOGGER.error("No team found for trainer ID {} and team ID {}. Cannot add Pokemon to non-existent team.", trainerId, teamId);
                return -1;
            }

            int slotIndex = countMembers(conn, teamId);

            if (slotIndex == -1) {
                LOGGER.error("Error checking slot index for trainer ID {}", trainerId);
                return -1; // Return -1 to indicate an error occurred
            } else if (slotIndex >= MAX_TEAM_SIZE) {
                LOGGER.warn("Trainer ID {}'s team is full! Cannot add more Pokemon.", trainerId);
                return -3; // Return -3 to indicate the team is full
            }

            String sql = "INSERT INTO team_members (team_id, slot_index, instance_id) VALUES (?, ?, ?)";

            try (PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                pstmt.setInt(1, teamId);
                pstmt.setInt(2, slotIndex);
                pstmt.setInt(3, pokemonId);
                LOGGER.info("Attempting to add Pokemon with ID {} to trainer ID {}'s team in slot {}...", pokemonId, trainerId, slotIndex);

                int rowsAffected = pstmt.executeUpdate();
                if (rowsAffected > 0) {
                    LOGGER.info("Pokemon with ID {} added to trainer ID {}'s team in slot {}.", pokemonId, trainerId, slotIndex);
                    return slotIndex;
                } else {
                    LOGGER.error("Failed to insert team entry for trainer ID {} and pokemon ID {}.", trainerId, pokemonId);
                    return -1;
                }
            }
        } catch (SQLException e) {
            LOGGER.error("Error adding Pokemon to team: {}", e.getMessage(), e);
            return -1; // Return -1 to indicate an error occurred
        }
    }

    public int checkSlotIndex(int trainerId, int team_id) {
        try (Connection conn = DatabaseSetup.getConnection()) {
            String teamName = getTeamName(conn, trainerId, team_id);
            if (teamName == null) {
                LOGGER.warn("No team found for trainer ID {} and team ID {} while checking slot index.", trainerId, team_id);
                return -1;
            }

            int slotCount = countMembers(conn, team_id);
            LOGGER.info("Trainer ID {} currently has {} Pokemon in their team (team_id={}).", trainerId, slotCount, team_id);
            return slotCount;
        } catch (SQLException e) {
            LOGGER.error("Error checking slot index: {}", e.getMessage(), e);
            return -1; // Return -1 to indicate an error occurred
        }
    }

    public boolean removePokemonFromDBTeam(int trainerDbId, int teamId, int slotIndex) {
        try (Connection conn = DatabaseSetup.getConnection()) {
            String teamName = getTeamName(conn, trainerDbId, teamId);
            if (teamName == null) {
                LOGGER.warn("No team found for trainer ID {} and team ID {} while removing Pokémon.", trainerDbId, teamId);
                return false;
            }

            String sql = "DELETE FROM team_members WHERE team_id = ? AND slot_index = ?";

            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setInt(1, teamId);
                pstmt.setInt(2, slotIndex);

                int affectedRows = pstmt.executeUpdate();
                if (affectedRows > 0) {
                    LOGGER.info("Pokemon in slot {} removed from trainer ID {}'s team.", slotIndex, trainerDbId);
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

    public List<Integer> getPokemonIdsForTeam(int trainerDbId, int teamId) {
        List<Integer> pokemonIds = new ArrayList<>();
        try (Connection conn = DatabaseSetup.getConnection()) {
            String teamName = getTeamName(conn, trainerDbId, teamId);
            if (teamName == null) {
                LOGGER.warn("No team found for trainer ID {} and team ID {} while loading team members.", trainerDbId, teamId);
                return pokemonIds;
            }

            String sql = "SELECT instance_id FROM team_members WHERE team_id = ? ORDER BY slot_index";

            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setInt(1, teamId);

                try (ResultSet rs = pstmt.executeQuery()) {
                    while (rs.next()) {
                        int pokemonId = rs.getInt("instance_id");
                        LOGGER.info("Found Pokemon instance_id {} for trainer ID {} and team ID {}.", pokemonId, trainerDbId, teamId);
                        pokemonIds.add(pokemonId);
                    }
                }
            }
        } catch (SQLException e) {
            LOGGER.error("Error retrieving Pokemon IDs for trainer ID {} and team ID {}: {}", trainerDbId, teamId, e.getMessage(), e);
        }
        return pokemonIds;
    }

    public int reorderTeamAfterRelease(int trainerId, int teamId) {
        try (Connection conn = DatabaseSetup.getConnection()) {
            String teamName = getTeamName(conn, trainerId, teamId);
            if (teamName == null) {
                LOGGER.warn("No team found for trainer ID {} and team ID {} while reordering.", trainerId, teamId);
                return -1;
            }

            String sql = "SELECT instance_id FROM team_members WHERE team_id = ? ORDER BY slot_index";
            List<Integer> instanceIds = new ArrayList<>();

            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setInt(1, teamId);


                try (ResultSet rs = pstmt.executeQuery()) {
                    while (rs.next()) {
                        LOGGER.info("Found Pokemon instance_id {} for trainer ID {} during reordering after release.", rs.getInt("instance_id"), trainerId);
                        instanceIds.add(rs.getInt("instance_id"));
                    }
                }
            }

            String updateSql = "UPDATE team_members SET slot_index = ? WHERE instance_id = ? AND team_id = ?";
            try (PreparedStatement updatePstmt = conn.prepareStatement(updateSql)) {
                for (int i = 0; i < instanceIds.size(); i++) {
                    updatePstmt.setInt(1, i); // Set the new slot index
                    updatePstmt.setInt(2, instanceIds.get(i)); // Set the instance_id
                    updatePstmt.setInt(3, teamId); // Set the team_id
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
            String teamName = getTeamName(conn, dbId, teamId);
            if (teamName == null) {
                LOGGER.warn("No team found for trainer ID {} and team ID {} while looking up slot index.", dbId, teamId);
                return -1;
            }

            String sql = "SELECT slot_index FROM team_members WHERE team_id = ? AND instance_id = ?";

            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setInt(1, teamId);
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

    public String getTeamName(int trainerId, int teamId) {
        try (Connection conn = DatabaseSetup.getConnection()) {
            return getTeamName(conn, trainerId, teamId);
        } catch (SQLException e) {
            LOGGER.error("Error retrieving team name for trainer ID {} and team ID {}: {}", trainerId, teamId, e.getMessage(), e);
            return null; // Return null to indicate an error occurred
        }
    }

    public int createTeamForTrainer(int trainerId, String teamName) {
        try (Connection conn = DatabaseSetup.getConnection()) {
            String sql = "INSERT INTO teams (trainer_id, team_name) VALUES (?, ?)";
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
            String sql = "SELECT team_id FROM teams WHERE trainer_id = ? ORDER BY team_id LIMIT 1";

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
            String sql = "SELECT team_name FROM teams WHERE trainer_id = ? ORDER BY team_name";

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

    public int getTeamIdByNameForTrainer(int trainerId, String teamName) {
        try (Connection conn = DatabaseSetup.getConnection()) {
            String sql = "SELECT team_id FROM teams WHERE trainer_id = ? AND team_name = ? LIMIT 1";

            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setInt(1, trainerId);
                pstmt.setString(2, teamName);

                try (ResultSet rs = pstmt.executeQuery()) {
                    if (rs.next()) {
                        int teamId = rs.getInt("team_id");
                        LOGGER.info("Retrieved team ID {} for team name '{}' and trainer ID {}.", teamId, teamName, trainerId);
                        return teamId;
                    } else {
                        LOGGER.warn("No team found with name '{}' for trainer ID {}.", teamName, trainerId);
                        return -1;
                    }
                }
            }
        } catch (SQLException e) {
            LOGGER.error("Error retrieving team by name '{}' for trainer ID {}: {}", teamName, trainerId, e.getMessage(), e);
            return -1;
        }
    }

    private String getTeamName(Connection conn, int trainerId, int teamId) throws SQLException {
        String sql = "SELECT team_name FROM teams WHERE trainer_id = ? AND team_id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, trainerId);
            pstmt.setInt(2, teamId);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    String teamName = rs.getString("team_name");
                    LOGGER.info("Retrieved team name '{}' for trainer ID {} and team ID {}.", teamName, trainerId, teamId);
                    return teamName;
                }
                return null;
            }
        }
    }

    private int countMembers(Connection conn, int teamId) throws SQLException {
        String sql = "SELECT COUNT(*) AS slot_count FROM team_members WHERE team_id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, teamId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("slot_count");
                }
            }
        }
        return -1;
    }
}
