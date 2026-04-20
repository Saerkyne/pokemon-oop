// This class demonstrates basic CRUD operations for Pokémon and trainers
// in the database. It creates a trainer, adds Pokémon to that trainer,
// retrieves and displays the trainer's Pokémon, updates a Pokémon's level,
// and finally deletes a Pokémon from the database.

// Future revisions should use prepared statements, implement moves, 
// and handle edge cases (e.g., duplicate names, non-existent records) 
// for a more robust application.

// It should also add connection back to the main application instead of hardcoded
// Pokemon values. Remember, all values in the database should be mutable ones, 
// not default/base values. Max HP isn't kept here, base HP isn't kept here, 
// but current HP IS. Anything that can be re-calculated on the fly
// should be kept in the code, not the database.   

package pokemonGame.db;
import pokemonGame.core.EvManager;
import pokemonGame.core.Stat;
import pokemonGame.model.Pokemon;
import pokemonGame.model.Trainer;

import java.sql.*;
import java.util.List;
import java.util.ArrayList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PokemonCRUD {

    private static final Logger LOGGER = LoggerFactory.getLogger(PokemonCRUD.class);

    public PokemonCRUD() {
    }

    public int createDBPokemon(Pokemon pokemon) {
        try (Connection conn = DatabaseSetup.getConnection()) {
            String sql = "INSERT INTO pokemon_instances "
                    + "(trainer_id, species, nickname, level, nature, "
                    + "iv_hp, iv_attack, iv_defense, iv_sp_attack, iv_sp_defense, iv_speed, "
                    + "current_hp) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

            try (PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                pstmt.setInt(1, pokemon.getTrainerDbId());
                pstmt.setString(2, pokemon.getSpecies().name());
                pstmt.setString(3, pokemon.getNickname());
                pstmt.setInt(4, pokemon.getLevel());
                pstmt.setString(5, pokemon.getNature().name());
                pstmt.setInt(6, pokemon.getIvHp());
                pstmt.setInt(7, pokemon.getIvAttack());
                pstmt.setInt(8, pokemon.getIvDefense());
                pstmt.setInt(9, pokemon.getIvSpecialAttack());
                pstmt.setInt(10, pokemon.getIvSpecialDefense());
                pstmt.setInt(11, pokemon.getIvSpeed());
                pstmt.setInt(12, pokemon.getCurrentHP());

                pstmt.executeUpdate();
                LOGGER.info("Pokemon '{}' ({}) created successfully for trainer ID {}.", pokemon.getNickname(), pokemon.getSpecies().name(), pokemon.getTrainerDbId());

                try (ResultSet pkmnSet = pstmt.getGeneratedKeys()) {
                    if (pkmnSet.next()) {
                        int pokemonId = pkmnSet.getInt(1);
                        pokemon.setPokemonDbId(pokemonId);
                        LOGGER.info("New Pokemon ID: {}", pokemonId);
                        return pokemonId; // Return the generated Pokémon ID
                    }
                }
            }
        } catch (SQLException e) {
            LOGGER.error("Error creating Pokemon: {}", e.getMessage(), e);
            return -1; // Return -1 to indicate an error occurred
        }
        return -1; // Return -1 if Pokemon creation failed
    }
    
    public boolean updateDBPokemon(Pokemon pokemon) {
        try (Connection conn = DatabaseSetup.getConnection()) {
            String sql = "UPDATE pokemon_instances SET "
                    + "species = ?, nickname = ?, level = ?, nature = ?, "
                    + "iv_hp = ?, iv_attack = ?, iv_defense = ?, iv_sp_attack = ?, iv_sp_defense = ?, iv_speed = ?, "
                    + "current_hp = ?, ev_hp = ?, ev_attack = ?, ev_defense = ?, ev_sp_attack = ?, ev_sp_defense = ?, ev_speed = ?, "
                    + "current_exp = ? "
                    + "WHERE instance_id = ? AND trainer_id = ?";

            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, pokemon.getSpecies().name());
                pstmt.setString(2, pokemon.getNickname());
                pstmt.setInt(3, pokemon.getLevel());
                pstmt.setString(4, pokemon.getNature().name());
                pstmt.setInt(5, pokemon.getIvHp());
                pstmt.setInt(6, pokemon.getIvAttack());
                pstmt.setInt(7, pokemon.getIvDefense());
                pstmt.setInt(8, pokemon.getIvSpecialAttack());
                pstmt.setInt(9, pokemon.getIvSpecialDefense());
                pstmt.setInt(10, pokemon.getIvSpeed());
                pstmt.setInt(11, pokemon.getCurrentHP());
                pstmt.setInt(12, EvManager.getEv(pokemon, Stat.HP));
                pstmt.setInt(13, EvManager.getEv(pokemon, Stat.ATTACK));
                pstmt.setInt(14, EvManager.getEv(pokemon, Stat.DEFENSE));
                pstmt.setInt(15, EvManager.getEv(pokemon, Stat.SPECIAL_ATTACK));
                pstmt.setInt(16, EvManager.getEv(pokemon, Stat.SPECIAL_DEFENSE));
                pstmt.setInt(17, EvManager.getEv(pokemon, Stat.SPEED));
                pstmt.setInt(18, pokemon.getCurrentExp());
                pstmt.setInt(19, pokemon.getPokemonDbId());
                pstmt.setLong(20, pokemon.getTrainerDbId());

                int rowsAffected = pstmt.executeUpdate();
                if (rowsAffected > 0) {
                    LOGGER.info("Pokemon '{}' ({}) updated successfully for trainer ID {}.", pokemon.getNickname(), pokemon.getSpecies().name(), pokemon.getTrainerDbId());
                    return true; // Return true to indicate successful update
                } else {
                    LOGGER.warn("No Pokemon found with ID: {} for trainer ID: {}", pokemon.getPokemonDbId(), pokemon.getTrainerDbId());
                    return false; // Return false to indicate no Pokemon found to update
                }
            }
        } catch (SQLException e) {
            LOGGER.error("Error updating Pokemon: {}", e.getMessage(), e);
            return false; // Return false to indicate an error occurred
        }
    }

    public boolean deleteDBPokemon(Pokemon pokemon) {
        try (Connection conn = DatabaseSetup.getConnection()) {
            String sql = "DELETE FROM pokemon_instances WHERE instance_id = ? AND trainer_id = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setInt(1, pokemon.getPokemonDbId());
                pstmt.setInt(2, pokemon.getTrainerDbId());

                int rowsAffected = pstmt.executeUpdate();
                if (rowsAffected > 0) {
                    LOGGER.info("Pokemon '{}' ({}) deleted successfully for trainer ID {}.", pokemon.getNickname(), pokemon.getSpecies().name(), pokemon.getTrainerDbId());
                    return true; // Return true to indicate successful deletion
                } else {
                    LOGGER.warn("No Pokemon found with ID: {} for trainer ID: {}", pokemon.getPokemonDbId(), pokemon.getTrainerDbId());
                    return false; // Return false to indicate no Pokemon found to delete
                }
            }
        } catch (SQLException e) {
            LOGGER.error("Error deleting Pokemon: {}", e.getMessage(), e);
            return false; // Return false to indicate an error occurred
        }
    }

    public int getPokemonDbIdByNicknameAndTrainer(String nickname, Trainer trainer) {
        try (Connection conn = DatabaseSetup.getConnection()) {
            String sql = "SELECT * FROM pokemon_instances WHERE trainer_id = ? AND nickname = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setInt(1, trainer.getTrainerDbId());
                pstmt.setString(2, nickname);
                try (ResultSet rs = pstmt.executeQuery()) {
                    if (rs.next()) {
                        int pokemonDbId = rs.getInt("instance_id");
                        LOGGER.info("Pokemon with ID '{}' retrieved successfully for trainer ID {}.", pokemonDbId, trainer.getTrainerDbId());
                        return pokemonDbId;
                    } else {
                        LOGGER.warn("No Pokemon found with nickname: '{}' for trainer ID: {}", nickname, trainer.getTrainerDbId());
                        return -1; // Return -1 if no Pokémon is found
                    }
                }
            }
        } catch (SQLException e) {
            LOGGER.error("Error retrieving Pokemon by nickname: {}", e.getMessage(), e);
            return -1; // Return -1 to indicate an error occurred
        }
    }

    // TODO: Method to return pokemon based on trainer DB ID and nickname
    // TODO: method to return pokemon based on trainer Discord ID and nickname
    /**
     * Either need a method that returns a multi-objecttype array, or multiple
     * methods that each return specific information. Could likely get by with
     * having two methods, one returning String information and one returning ints.
     */
    public List<Object> getPokemonAsArray(int instanceId) {
        List<Object> pokemonData = new ArrayList<>();

        LOGGER.debug("Retrieving Pokémon data as array for instance ID: {}", instanceId);
        try (Connection conn = DatabaseSetup.getConnection()) {
            String sql = "SELECT * FROM pokemon_instances WHERE instance_id = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setInt(1, instanceId);
                try (ResultSet rs = pstmt.executeQuery()) {
                    if (rs.next()) {
                        // Populate the pokemonData list with the retrieved data
                        pokemonData.add(rs.getInt("instance_id"));
                        pokemonData.add(rs.getInt("trainer_id"));
                        pokemonData.add(rs.getString("species"));
                        pokemonData.add(rs.getString("nickname"));
                        pokemonData.add(rs.getInt("level"));
                        pokemonData.add(rs.getString("nature"));
                        pokemonData.add(rs.getInt("iv_hp"));
                        pokemonData.add(rs.getInt("iv_attack"));
                        pokemonData.add(rs.getInt("iv_defense"));
                        pokemonData.add(rs.getInt("iv_special_attack"));
                        pokemonData.add(rs.getInt("iv_special_defense"));
                        pokemonData.add(rs.getInt("iv_speed"));
                        pokemonData.add(rs.getInt("ev_hp"));
                        pokemonData.add(rs.getInt("ev_attack"));
                        pokemonData.add(rs.getInt("ev_defense"));
                        pokemonData.add(rs.getInt("ev_special_attack"));
                        pokemonData.add(rs.getInt("ev_special_defense"));
                        pokemonData.add(rs.getInt("ev_speed"));
                        pokemonData.add(rs.getInt("current_exp"));
                        pokemonData.add(rs.getInt("current_hp"));
                    }
                }
            }
        } catch (SQLException e) {
            LOGGER.error("Error retrieving Pokémon data as array for instance ID: {}", instanceId, e);

        }
        return pokemonData;
    }
}

