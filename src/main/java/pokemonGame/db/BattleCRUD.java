package pokemonGame.db;

import pokemonGame.Battle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.sql.*;

public class BattleCRUD {

    private static final Logger LOGGER = LoggerFactory.getLogger(BattleCRUD.class);

    public int createBattle(int trainer1Id, int trainer2Id, String status) {
        // Implementation to create a battle in the database and return its ID
        LOGGER.info("Creating battle between trainer {} and trainer {}", trainer1Id, trainer2Id);
        try (Connection conn = DatabaseSetup.getConnection()) {
            String sql = "INSERT INTO battles (trainer1_id, trainer2_id, trainer1_active_pokemon_id, " 
                + "trainer2_active_pokemon_id, status, created_at, updated_at) VALUES (?, ?, ?, ?, ?, ?, ?)";

            try (PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                pstmt.setInt(1, trainer1Id);
                pstmt.setInt(2, trainer2Id);
                pstmt.setNull(3, Types.INTEGER); // Active Pokémon will be set later
                pstmt.setNull(4, Types.INTEGER); // Active Pokémon will be set later
                pstmt.setString(5, status);
                Timestamp now = new Timestamp(System.currentTimeMillis());
                pstmt.setTimestamp(6, now);
                pstmt.setTimestamp(7, now);

                pstmt.executeUpdate();
                try (ResultSet battleSet = pstmt.getGeneratedKeys()) {
                    if (battleSet.next()) {
                        int battleId = battleSet.getInt(1);
                        LOGGER.info("Battle created with ID {}", battleId);
                        return battleId;
                    }
                }
            }
        } catch (SQLException e) {
            LOGGER.error("Error creating battle", e);
            return -1; // Indicate failure
        }
        return -1; // Indicate failure if we reach here
    }

    public Battle getBattleById(int battleId) {
        // Implementation to retrieve a battle from the database by its ID
        LOGGER.info("Retrieving battle with ID {}", battleId);

        try (Connection conn = DatabaseSetup.getConnection()) {
            String sql = "SELECT * FROM battles WHERE battle_id = ?";

            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setInt(1, battleId);
                try (ResultSet battleSet = pstmt.executeQuery()) {
                    if (battleSet.next()) {
                        Battle battle = new Battle();
                        battle.setBattleId(battleSet.getInt("battle_id"));
                        battle.setTrainer1Id(battleSet.getInt("trainer1_id"));
                        battle.setTrainer2Id(battleSet.getInt("trainer2_id"));
                        battle.setTrainer1ActivePokemonId(battleSet.getInt("trainer1_active_pokemon_id"));
                        battle.setTrainer2ActivePokemonId(battleSet.getInt("trainer2_active_pokemon_id"));
                        battle.setStatus(Battle.Status.valueOf(battleSet.getString("status")));
                        battle.setStartTime(battleSet.getTimestamp("created_at"));
                        battle.setUpdateTime(battleSet.getTimestamp("updated_at"));
                        return battle;
                    }
                }
            }
        } catch (SQLException e) {
            LOGGER.error("Error retrieving battle with ID {}", battleId, e);
        }

        return null; // Placeholder return value
    }

    public Battle getActiveBattleForTrainer(int trainerId) {
        // Implementation to retrieve the active battle for a given trainer
        LOGGER.info("Retrieving active battle for trainer {}", trainerId);

        try (Connection conn = DatabaseSetup.getConnection()) {
            String sql = "SELECT * FROM battles WHERE (trainer1_id = ? OR trainer2_id = ?) AND status = 'ACTIVE'";

            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setInt(1, trainerId);
                pstmt.setInt(2, trainerId);
                try (ResultSet battleSet = pstmt.executeQuery()) {
                    if (battleSet.next()) {
                        Battle battle = new Battle();
                        battle.setBattleId(battleSet.getInt("battle_id"));
                        battle.setTrainer1Id(battleSet.getInt("trainer1_id"));
                        battle.setTrainer2Id(battleSet.getInt("trainer2_id"));
                        battle.setTrainer1ActivePokemonId(battleSet.getInt("trainer1_active_pokemon_id"));
                        battle.setTrainer2ActivePokemonId(battleSet.getInt("trainer2_active_pokemon_id"));
                        battle.setStatus(Battle.Status.valueOf(battleSet.getString("status")));
                        battle.setStartTime(battleSet.getTimestamp("created_at"));
                        battle.setUpdateTime(battleSet.getTimestamp("updated_at"));
                        return battle;
                    }
                }
            }
        } catch (SQLException e) {
            LOGGER.error("Error retrieving active battle for trainer {}", trainerId, e);
        }

        return null; // Placeholder return value
    }

    public Battle getActiveBattleForTrainerMatchup(int trainer1Id, int trainer2Id) {
        // Implementation to retrieve the active battle for a given trainer matchup
        LOGGER.info("Retrieving active battle for trainer matchup: {} vs {}", trainer1Id, trainer2Id);

        try (Connection conn = DatabaseSetup.getConnection()) {
            String sql = "SELECT * FROM battles WHERE ((trainer1_id = ? AND trainer2_id = ?) OR (trainer1_id = ? AND trainer2_id = ?)) AND (status = 'ACTIVE' OR status = 'PENDING')";

            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setInt(1, trainer1Id);
                pstmt.setInt(2, trainer2Id);
                pstmt.setInt(3, trainer2Id);
                pstmt.setInt(4, trainer1Id);
                try (ResultSet battleSet = pstmt.executeQuery()) {
                    if (battleSet.next()) {
                        Battle battle = new Battle();
                        battle.setBattleId(battleSet.getInt("battle_id"));
                        battle.setTrainer1Id(battleSet.getInt("trainer1_id"));
                        battle.setTrainer2Id(battleSet.getInt("trainer2_id"));
                        battle.setTrainer1ActivePokemonId(battleSet.getInt("trainer1_active_pokemon_id"));
                        battle.setTrainer2ActivePokemonId(battleSet.getInt("trainer2_active_pokemon_id"));
                        battle.setStatus(Battle.Status.valueOf(battleSet.getString("status")));
                        battle.setStartTime(battleSet.getTimestamp("created_at"));
                        battle.setUpdateTime(battleSet.getTimestamp("updated_at"));
                        return battle;
                    }
                }
             } catch (SQLException e) {
                LOGGER.error("Error retrieving active battle for trainer matchup: {} vs {}", trainer1Id, trainer2Id, e);
             }
        } catch (SQLException e) {
            LOGGER.error("Error retrieving active battle for trainer matchup: {} vs {}", trainer1Id, trainer2Id, e);
        }

        return null; // Placeholder return value
    }

    public void updateBattleStatus(Battle battle) {
        // Implementation to update a battle's state in the database
        LOGGER.info("Updating battle with ID {} to status {}", battle.getBattleId(), battle.getStatus());
        try (Connection conn = DatabaseSetup.getConnection()) {
            String sql = "UPDATE battles SET status = ?, trainer1_active_pokemon_id = ?, " 
            + "trainer2_active_pokemon_id = ?, updated_at = ? WHERE battle_id = ?";
        
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, battle.getStatus().name());
                pstmt.setInt(2, battle.getTrainer1ActivePokemonId());
                pstmt.setInt(3, battle.getTrainer2ActivePokemonId());
                pstmt.setTimestamp(4, battle.getUpdateTime());
                pstmt.setInt(5, battle.getBattleId());
                pstmt.executeUpdate();
            }
        } catch (SQLException e) {
            LOGGER.error("Error updating battle with ID {}", battle.getBattleId(), e);

        }
    }

    public void setActivePokemon(int battleId, int trainerId, int pokemonId) {
        // Implementation to set the active Pokémon for a trainer in a battle
        LOGGER.info("Setting active Pokémon for trainer {} in battle {} to Pokémon {}", trainerId, battleId, pokemonId);
        try (Connection conn = DatabaseSetup.getConnection()) {
            String sql = "UPDATE battles SET "+ (trainerId == getBattleById(battleId).getTrainer1Id() ? "trainer1_active_pokemon_id" : "trainer2_active_pokemon_id") 
            + "= ?, updated_at = ? WHERE battle_id = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setInt(1, pokemonId);
                pstmt.setTimestamp(2, new Timestamp(System.currentTimeMillis()));
                pstmt.setInt(3, battleId);
                pstmt.executeUpdate();
            }
        } catch (SQLException e) {
            LOGGER.error("Error setting active Pokémon for trainer {} in battle {}", trainerId, battleId, e);
        }

    }

    public void setWinner(int battleId, int winnerTrainerId) {
        // Implementation to set the winner of a battle in the database
        LOGGER.info("Setting winner for battle {} to trainer {}", battleId, winnerTrainerId);
        try (Connection conn = DatabaseSetup.getConnection()) {
            String sql = "UPDATE battles SET status = 'FINISHED', winner_id = ?, updated_at = ? WHERE battle_id = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setInt(1, winnerTrainerId);
                pstmt.setTimestamp(2, new Timestamp(System.currentTimeMillis()));
                pstmt.setInt(3, battleId);
                pstmt.executeUpdate();
            }
        } catch (SQLException e) {
            LOGGER.error("Error setting winner for battle {}", battleId, e);
        }
    }

    public Battle[] getBattleHistoryForTrainer(int trainerId) {
        // Implementation to retrieve the battle history for a given trainer
        LOGGER.info("Retrieving battle history for trainer {}", trainerId);
        try (Connection conn = DatabaseSetup.getConnection()) {
            String sql = "SELECT * FROM battles WHERE trainer1_id = ? OR trainer2_id = ? ORDER BY created_at DESC";

            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setInt(1, trainerId);
                pstmt.setInt(2, trainerId);
                try (ResultSet battleSet = pstmt.executeQuery()) {
                    // Collect battles into a list and convert to an array
                    // Placeholder implementation, should be replaced with actual collection logic
                }
            }
        } catch (SQLException e) {
            LOGGER.error("Error retrieving battle history for trainer {}", trainerId, e);
        }
        return new Battle[0]; // Placeholder return value
    }

    public Battle[] getAllActiveBattles() {
        // Implementation to retrieve all active battles from the database
        LOGGER.info("Retrieving all active battles");
        try (Connection conn = DatabaseSetup.getConnection()) {
            String sql = "SELECT * FROM battles WHERE status = 'ACTIVE' ORDER BY created_at DESC";
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                try (ResultSet battleSet = pstmt.executeQuery()) {
                    // Collect battles into a list and convert to an array
                    // Placeholder implementation, should be replaced with actual collection logic

                }
            }
        } catch (SQLException e) {
            LOGGER.error("Error retrieving all active battles", e);
        }

        return new Battle[0]; // Placeholder return value
    }

}
