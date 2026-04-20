package pokemonGame.db;

import pokemonGame.battle.BattleAction;
import pokemonGame.battle.MoveAction;
import pokemonGame.battle.SwitchAction;
import pokemonGame.service.BattleService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.sql.*;

public class BattleTurnCRUD {

    private static final Logger LOGGER = LoggerFactory.getLogger(BattleTurnCRUD.class);

    /*
     * One trainer should have one pending-action row per battle.
     *
     * First submission inserts that row. If the player changes their choice before
     * the opponent submits, we want to overwrite the existing row instead of failing
     * with a duplicate-key error. That is why the SQL uses ON DUPLICATE KEY UPDATE.
     *
     * Important detail: SQL does not treat (...) as a placeholder for column names.
     * The INSERT still needs the real column list:
     *   (battle_id, trainer_id, action_type, move_slot_index, switch_pokemon_id, submitted_at)
     *
     * If we leave (...) in the SQL string, MariaDB parses it literally and rejects
     * the statement before the duplicate-key logic ever runs.
     *
     * Action mapping stays simple:
     * - MoveAction writes move_slot_index and stores NULL for switch_pokemon_id.
     * - SwitchAction writes switch_pokemon_id and stores NULL for move_slot_index.
     */
    public void submitPendingAction(int battleId, int trainerId, BattleAction action) {
        // Implementation to submit a pending action for a battle turn
        LOGGER.info("Submitting pending action for battleId: {}, trainerId: {}, action: {}", battleId, trainerId, action);
        try (Connection conn = DatabaseSetup.getConnection()) {
            String sql = "INSERT INTO battle_pending_actions "
                    + "(battle_id, trainer_id, action_type, move_slot_index, switch_pokemon_id, submitted_at) "
                    + "VALUES (?, ?, ?, ?, ?, ?) "
                    + "ON DUPLICATE KEY UPDATE action_type = VALUES(action_type), "
                    + "move_slot_index = VALUES(move_slot_index), "
                    + "switch_pokemon_id = VALUES(switch_pokemon_id), "
                    + "submitted_at = VALUES(submitted_at)";
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

    // TODO: Refactor this to return an array of BattleAction IDs. 
    // TODO: Remove call to BattleService and only return IDs,
    public BattleAction[] getPendingActions(int battleId) {
        // Implementation to retrieve pending action IDs for a battle turn
        BattleAction[] actionIds = new BattleAction[2]; // Assuming 2 trainers per battle
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
                        // TODO: Check if this is the right direction. Theoretically DAO shouldn't call Service
                        BattleAction action = BattleService.createActionFromDbRow(trainerId, actionType, moveSlotIndex, switchPokemonId);
                        // Collect actions into a list/array to return
                        if (action != null) {
                            if (actionIds[0] == null) {
                                actionIds[0] = action;
                            } else {
                                actionIds[1] = action;
                            }
                        }
                    }
                }
            }
        } catch (SQLException e) {
            LOGGER.error("Error retrieving pending actions for battleId: {}", battleId, e);
        }
        return actionIds;
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
                    // TODO: DB-7 — Implement: iterate ResultSet, collect turn_number + summary into list, return as array.
                }
            }
        } catch (SQLException e) {
            LOGGER.error("Error retrieving turn history for battleId: {}", battleId, e);
        }
        return new String[0]; // Placeholder return value
    }

}
