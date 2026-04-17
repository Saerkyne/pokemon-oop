package pokemonGame.db;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// CODE REVIEW NOTES (2026-04-09):
//
// DONE 1. getMovesForPokemon — loads moves from pokemon_movesets, called by
//         PokemonCRUD.mapResultSetToPokemon to populate the in-memory moveset on load.
//
// DONE 2. updateMoveForPokemon — UPDATE query for replacing a move when the moveset is full.
//
// 3. Still missing: deleteMoveForPokemon(int instanceId, int slotIndex)
//    — a DELETE query, if we ever need to remove a move without replacing it.
//
// DONE 4. updatePP — persists PP changes; used by MoveSlotService.use() and restore().
//
// 5. The 4-move limit is enforced at two levels:
//    - In-memory: Pokemon.addMove() rejects when moveset.size() >= 4
//    - DB: PRIMARY KEY (instance_id, slot_index) + CHECK (slot_index BETWEEN 0 AND 3)
public class MoveCRUD {
    private static final Logger LOGGER = LoggerFactory.getLogger(MoveCRUD.class);

    public int insertMoveForPokemon(int pokemonId, int moveSlotIndex, String moveName, int currentPP) {
        String sql = "INSERT INTO pokemon_movesets (instance_id, slot_index, move_name, current_pp) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseSetup.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, pokemonId);
            pstmt.setInt(2, moveSlotIndex);
            pstmt.setString(3, moveName);
            pstmt.setInt(4, currentPP);
            int affectedRows = pstmt.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Inserting move failed, no rows affected.");
            }
            return affectedRows;
        } catch (SQLException e) {
            LOGGER.error("Error inserting move for Pokémon: {}", e.getMessage());
            return -1; // Indicate failure
        }
    }

    public int updatePP(int pokemonId, int moveSlotIndex, int currentPP) {
        String sql = "UPDATE pokemon_movesets SET current_pp = ? WHERE instance_id = ? AND slot_index = ?";
        try (Connection conn = DatabaseSetup.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, currentPP);
            pstmt.setInt(2, pokemonId);
            pstmt.setInt(3, moveSlotIndex);
            return pstmt.executeUpdate(); // Returns the number of rows affected
        } catch (SQLException e) {
            LOGGER.error("Error updating PP for Pokémon: {}", e.getMessage());
            return -1; // Indicate failure
        }
    }

    /**
     * Load all moves for a Pokémon from the database, ordered by slot index.
     * Each element is a String[3]: { move_name, current_pp, slot_index }.
     * The caller is responsible for converting move_name into a Move object
     * (via PokeMove.fromString) and constructing MoveSlots.
     *
     * @param instanceId the pokemon_instances.instance_id
     * @return rows ordered by slot_index, or an empty list on error / no moves
     */
    public List<String[]> getMovesForPokemon(int instanceId) {
        String sql = "SELECT move_name, current_pp, slot_index FROM pokemon_movesets WHERE instance_id = ? ORDER BY slot_index";
        List<String[]> moves = new ArrayList<>();
        try (Connection conn = DatabaseSetup.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, instanceId);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    moves.add(new String[]{
                        rs.getString("move_name"),
                        String.valueOf(rs.getInt("current_pp")),
                        String.valueOf(rs.getInt("slot_index"))
                    });
                }
            }
        } catch (SQLException e) {
            LOGGER.error("Error loading moves for Pokémon instance {}: {}", instanceId, e.getMessage());
        }
        return moves;
    }

    /**
     * Replace a move in an existing slot. Used when the moveset is full and
     * the player chooses which move to forget.
     *
     * @return number of rows affected (1 on success), or -1 on error
     */
    public int updateMoveForPokemon(int instanceId, int slotIndex, String moveName, int currentPP) {
        String sql = "UPDATE pokemon_movesets SET move_name = ?, current_pp = ? WHERE instance_id = ? AND slot_index = ?";
        try (Connection conn = DatabaseSetup.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, moveName);
            pstmt.setInt(2, currentPP);
            pstmt.setInt(3, instanceId);
            pstmt.setInt(4, slotIndex);
            return pstmt.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error("Error updating move for Pokémon instance {}: {}", instanceId, e.getMessage());
            return -1;
        }
    }

}
