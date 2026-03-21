package pokemonGame.db;
import java.sql.*;

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

                pstmt.executeUpdate();
                System.out.println("Pokémon with ID " + pokemonId + " added to trainer ID " + trainerId + "'s team in slot " + slotIndex + ".");

                try (ResultSet teamSet = pstmt.getGeneratedKeys()) {
                    if (teamSet.next()) {
                        int teamEntryId = teamSet.getInt(1);
                        System.out.println("New Team Entry ID: " + teamEntryId);
                        return slotIndex; // Return the slot index where the Pokémon was added
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("Error adding Pokémon to team: " + e.getMessage());
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

}
