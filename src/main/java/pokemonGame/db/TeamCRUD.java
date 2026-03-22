package pokemonGame.db;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import pokemonGame.Pokemon;
import pokemonGame.Trainer;

public class TeamCRUD {
    public int addPokemonToDBTeam(int trainerId, int pokemonId) {
        try (Connection conn = DatabaseSetup.getConnection()) {
            int slotIndex = checkSlotIndex(trainerId);

            if (slotIndex == -1) {
                System.err.println("Error checking slot index for trainer ID " + trainerId);
                return -1; // Return -1 to indicate an error occurred
            } else if (slotIndex >= 6) {
                System.out.println("Trainer ID " + trainerId + "'s team is full! Cannot add more Pokémon.");
                return -1; // Return -1 to indicate the team is full
            }
            String sql = "INSERT INTO trainer_teams (trainer_id, instance_id, slot_index) VALUES (?, ?, ?)";

            try (PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                pstmt.setInt(1, trainerId);
                pstmt.setInt(2, pokemonId);
                pstmt.setInt(3, slotIndex);
                System.out.println("Attempting to add Pokemon with ID " + pokemonId + " to trainer ID " + trainerId + "'s team in slot " + slotIndex + "...");

                pstmt.executeUpdate();
                System.out.println("Pokemon with ID " + pokemonId + " added to trainer ID " + trainerId + "'s team in slot " + slotIndex + ".");

                try (ResultSet teamSet = pstmt.getGeneratedKeys()) {
                    if (teamSet.next()) {
                        int teamEntryId = teamSet.getInt(1);
                        System.out.println("New Team Entry ID: " + teamEntryId);
                        return slotIndex; // Return the slot index where the Pokémon was added
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("Error adding Pokemon to team: " + e.getMessage());
            e.printStackTrace();
            return -1; // Return -1 to indicate an error occurred
        }
        return -1; // Return -1 if adding Pokémon to team failed
    }

    public int checkSlotIndex(int trainerId) {
        try (Connection conn = DatabaseSetup.getConnection()) {
            String sql = "SELECT COUNT(*) AS slot_index FROM trainer_teams WHERE trainer_id = ?";

            try (PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                pstmt.setInt(1, trainerId);

                try (ResultSet rs = pstmt.executeQuery()) {
                    if (rs.next()) {
                        int slotIndex = rs.getInt("slot_index");
                        return slotIndex; // Return the current count of Pokémon in the team
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("Error checking slot index: " + e.getMessage());
            e.printStackTrace();
            return -1; // Return -1 to indicate an error occurred
        }
        return -1; // Return -1 if checking slot index failed
    }

    public Boolean removePokemonFromDBTeam(int trainerId, int pokemonId) {
        try (Connection conn = DatabaseSetup.getConnection()) {
            String sql = "DELETE FROM trainer_teams WHERE trainer_id = ? AND instance_id = ?";

            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setInt(1, trainerId);
                pstmt.setInt(2, pokemonId);

                int affectedRows = pstmt.executeUpdate();
                if (affectedRows > 0) {
                    System.out.println("Pokemon with ID " + pokemonId + " removed from trainer ID " + trainerId + "'s team.");
                    return true; // Return true to indicate successful removal
                } else {
                    System.out.println("No Pokemon with ID " + pokemonId + " found in trainer ID " + trainerId + "'s team.");
                    return false; // Return false to indicate no Pokemon found to remove
                }
            }
        } catch (SQLException e) {
            System.err.println("Error removing Pokemon from team: " + e.getMessage());
            e.printStackTrace();
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
                pstmt.setInt(1, trainer.getDBId());

                try (ResultSet rs = pstmt.executeQuery()) {
                    while (rs.next()) {
                        Pokemon pokemon = PokemonCRUD.mapResultSetToPokemon(rs, trainer);
                        team.add(pokemon);
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving team for trainer ID " + trainer.getDBId() + ": " + e.getMessage());
            e.printStackTrace();
        }
        return team;
    }
}
