package pokemonGame.db;

import pokemonGame.BattleAction;
import pokemonGame.BattleService;
import pokemonGame.MoveAction;
import pokemonGame.SwitchAction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.sql.*;

public class BattleTurnCRUD {

    private static final Logger LOGGER = LoggerFactory.getLogger(BattleTurnCRUD.class);

    /*
     * This method takes a BattleAction (the sealed interface) and uses pattern
     * matching to figure out which columns to populate. This is the right place
     * for that branching — the CRUD class knows the column structure, so it
     * decides how each action type maps to the database row.
     *
     * BattleService just calls: turnCrud.submitPendingAction(battleId, trainerId, action)
     * It doesn't need to know whether the action is a move or a switch. That's
     * the CRUD's concern.
     *
     * The key technique: use setNull() for the column that doesn't apply.
     * A MoveAction sets move_slot_index and nulls switch_pokemon_id.
     * A SwitchAction sets switch_pokemon_id and nulls move_slot_index.
     * The SQL is the same either way — only the parameter values differ.
     *
     * REFACTORED APPROACH:
     *
     *   public void submitPendingAction(int battleId, int trainerId, BattleAction action) {
     *       String sql = "INSERT INTO battle_pending_actions (battle_id, trainer_id, "
     *           + "action_type, move_slot_index, switch_pokemon_id, submitted_at) "
     *           + "VALUES (?, ?, ?, ?, ?, ?) "
     *           + "ON DUPLICATE KEY UPDATE action_type = VALUES(action_type), "
     *           + "move_slot_index = VALUES(move_slot_index), "
     *           + "switch_pokemon_id = VALUES(switch_pokemon_id), "
     *           + "submitted_at = VALUES(submitted_at)";
     *
     *       try (Connection conn = DatabaseSetup.getConnection();
     *            PreparedStatement pstmt = conn.prepareStatement(sql)) {
     *
     *           pstmt.setInt(1, battleId);
     *           pstmt.setInt(2, trainerId);
     *
     *           // Pattern matching on the sealed interface — the compiler knows
     *           // MoveAction and SwitchAction are the only possibilities.
     *           switch (action) {
     *               case MoveAction ma -> {
     *                   pstmt.setString(3, "MOVE");
     *                   pstmt.setInt(4, ma.moveSlotIndex());
     *                   pstmt.setNull(5, Types.INTEGER);  // no switch target
     *               }
     *               case SwitchAction sa -> {
     *                   pstmt.setString(3, "SWITCH");
     *                   pstmt.setNull(4, Types.INTEGER);  // no move slot
     *                   pstmt.setInt(5, sa.teamSlotIndex());
     *               }
     *           }
     *
     *           pstmt.setTimestamp(6, new Timestamp(System.currentTimeMillis()));
     *           pstmt.executeUpdate();
     *
     *       } catch (SQLException e) {
     *           LOGGER.error("Error submitting pending action for battleId: {}, trainerId: {}", battleId, trainerId, e);
     *       }
     *   }
     *
     * NOTES:
     *   - ON DUPLICATE KEY UPDATE lets a player change their mind before the other
     *     player submits. If they click a different move button, it overwrites their
     *     previous choice instead of throwing a duplicate key error.
     *   - The timestamp is set here in the CRUD, not on the BattleAction record.
     *     The CRUD knows when the row was written — the domain object doesn't need to.
     *   - "MOVE" and "SWITCH" are stored as strings in the action_type column.
     *     When reading back (getPendingActions), you'll use these strings to decide
     *     whether to construct a MoveAction or SwitchAction — see the getPendingActions
     *     comment below.
     */
    public void submitPendingAction(int battleId, int trainerId, BattleAction action) {
        // Implementation to submit a pending action for a battle turn
        LOGGER.info("Submitting pending action for battleId: {}, trainerId: {}, action: {}", battleId, trainerId, action);
        try (Connection conn = DatabaseSetup.getConnection()) {
            String sql = "INSERT INTO battle_pending_actions (battle_id, trainer_id, " 
            + "action_type, move_slot_index, switch_pokemon_id, submitted_at) " 
            + "VALUES (?, ?, ?, ?, ?, ?)";
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setInt(1, battleId);
                pstmt.setInt(2, trainerId);
                switch (action) {
                    case MoveAction ma -> {
                        pstmt.setString(3, "MOVE");
                        pstmt.setInt(4, ma.moveSlotIndex());
                        pstmt.setNull(5, Types.INTEGER);  // no switch target
                    }
                    case SwitchAction sa -> {
                        pstmt.setString(3, "SWITCH");
                        pstmt.setNull(4, Types.INTEGER);  // no move slot
                        pstmt.setInt(5, sa.teamSlotIndex());
                    }
                }
                pstmt.setTimestamp(6, new Timestamp(System.currentTimeMillis()));
                pstmt.executeUpdate();
            }
        } catch (SQLException e) {
            LOGGER.error("Error submitting pending action for battleId: {}, trainerId: {}, action: {}", battleId, trainerId, action, e);
        }
    }


    public BattleAction[] getPendingActions(int battleId) {
        // Implementation to retrieve pending actions for a battle turn
        BattleAction[] actions = new BattleAction[2]; // Assuming 2 trainers per battle
        try (Connection conn = DatabaseSetup.getConnection()) {
            String sql = "SELECT trainer_id, action_type, move_slot_index, switch_pokemon_id, submitted_at " 
            + "FROM battle_pending_actions WHERE battle_id = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setInt(1, battleId);
                try (ResultSet rs = pstmt.executeQuery()) {
                    while (rs.next()) {
                        int trainerId = rs.getInt("trainer_id");
                        String actionType = rs.getString("action_type");
                        int moveSlotIndex = rs.getInt("move_slot_index");
                        int switchPokemonId = rs.getInt("switch_pokemon_id");

                        // pass to BattleService to rehydrate into BattleAction records
                        // call BattleService.createActionFromDbRow(trainerId, actionType, moveSlotIndex, switchPokemonId)
                        // BattleService will parse actionType into MoveAction or SwitchAction, 
                        // load Trainer and Pokemon from DB, then return the correct BattleAction record.
                        BattleAction action = BattleService.createActionFromDbRow(trainerId, actionType, moveSlotIndex, switchPokemonId);
                        // Collect actions into a list/array to return
                        if (action != null) {
                            if (actions[0] == null) {
                                actions[0] = action;
                            } else {
                                actions[1] = action;
                            }
                        }
                    }
                }
            }
        } catch (SQLException e) {
            LOGGER.error("Error retrieving pending actions for battleId: {}", battleId, e);
        }
        return actions;
    }

    public void clearPendingActions(int battleId) {
        // Implementation to clear pending actions for a battle turn
        try (Connection conn = DatabaseSetup.getConnection()) {
            String sql = "DELETE FROM battle_pending_actions WHERE battle_id = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setInt(1, battleId);
                pstmt.executeUpdate();
            }
        } catch (SQLException e) {
            LOGGER.error("Error clearing pending actions for battleId: {}", battleId, e);
        }
    }

    public void saveTurnHistory(int battleId, int turnNumber, String summary) {
        // Implementation to save the history of a battle turn
        try (Connection conn = DatabaseSetup.getConnection()) {
            String sql = "INSERT INTO battle_turn_history (battle_id, turn_number, summary) VALUES (?, ?, ?)";
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setInt(1, battleId);
                pstmt.setInt(2, turnNumber);
                pstmt.setString(3, summary);
                pstmt.executeUpdate();
            }
        } catch (SQLException e) {
            LOGGER.error("Error saving turn history for battleId: {}, turnNumber: {}", battleId, turnNumber, e);
        }
    }

    public String[] getTurnHistory(int battleId) {
        // Implementation to retrieve the history of a battle
        try (Connection conn = DatabaseSetup.getConnection()) {
            String sql = "SELECT turn_number, summary FROM battle_turn_history WHERE battle_id = ? ORDER BY turn_number";
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setInt(1, battleId);
                try (ResultSet rs = pstmt.executeQuery()) {
                    // Process result set and create an array of turn summaries
                    // For simplicity, returning an empty array here
                }
            }
        } catch (SQLException e) {
            LOGGER.error("Error retrieving turn history for battleId: {}", battleId, e);
        }
        return new String[0]; // Placeholder return value
    }

}
