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
import pokemonGame.Pokemon;
import pokemonGame.Natures;
import pokemonGame.Trainer;
import java.sql.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PokemonCRUD {

    private static final Logger LOGGER = LoggerFactory.getLogger(PokemonCRUD.class);

    public int createDBPokemon(Pokemon pokemon) {
        try (Connection conn = DatabaseSetup.getConnection()) {
            String sql = "INSERT INTO pokemon_instances "
                    + "(trainer_id, species, nickname, level, nature, "
                    + "iv_hp, iv_attack, iv_defense, iv_sp_attack, iv_sp_defense, iv_speed, "
                    + "current_hp) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

            try (PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                pstmt.setInt(1, pokemon.getTrainerDbId());
                pstmt.setString(2, pokemon.getSpecies());
                pstmt.setString(3, pokemon.getNickname());
                pstmt.setInt(4, pokemon.getLevel());
                pstmt.setString(5, pokemon.getNature().getDisplayName());
                pstmt.setInt(6, pokemon.getIvHp());
                pstmt.setInt(7, pokemon.getIvAttack());
                pstmt.setInt(8, pokemon.getIvDefense());
                pstmt.setInt(9, pokemon.getIvSpecialAttack());
                pstmt.setInt(10, pokemon.getIvSpecialDefense());
                pstmt.setInt(11, pokemon.getIvSpeed());
                pstmt.setInt(12, pokemon.getCurrentHP());

                pstmt.executeUpdate();
                LOGGER.info("Pokemon '{}' ({}) created successfully for trainer ID {}.", pokemon.getNickname(), pokemon.getSpecies(), pokemon.getTrainerDbId());

                try (ResultSet pkmnSet = pstmt.getGeneratedKeys()) {
                    if (pkmnSet.next()) {
                        int pokemonId = pkmnSet.getInt(1);
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

    public Pokemon getSpecificDBPokemonForTrainer(Trainer trainer, int pokemonId) {
        try (Connection conn = DatabaseSetup.getConnection()) {
            String sql = "SELECT * FROM pokemon_instances WHERE trainer_id = ? AND instance_id = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setInt(1, trainer.getDbId());
                pstmt.setInt(2, pokemonId);
                try (ResultSet rs = pstmt.executeQuery()) {
                    
                    if (rs.next()) {
                        Pokemon foundPokemon = mapResultSetToPokemon(rs, trainer);
                        LOGGER.info("Pokemon '{}' ({}) retrieved successfully for trainer ID {}.", foundPokemon.getNickname(), foundPokemon.getSpecies(), trainer.getDbId());
                        return foundPokemon; // Return the retrieved Pokémon
                    } else {
                        LOGGER.warn("No Pokemon found with ID: {} for trainer ID: {}", pokemonId, trainer.getDbId());
                        return null; // Return null if no Pokémon is found
                    }
                }
            }
        } catch (SQLException e) {
            LOGGER.error("Error retrieving Pokemon: {}", e.getMessage(), e);
            return null; // Return null to indicate an error occurred
        }
    }
    
    public boolean updateDBPokemon(Pokemon pokemon) {
        try (Connection conn = DatabaseSetup.getConnection()) {
            String sql = "UPDATE pokemon_instances SET "
                    + "species = ?, nickname = ?, level = ?, nature = ?, "
                    + "iv_hp = ?, iv_attack = ?, iv_defense = ?, iv_sp_attack = ?, iv_sp_defense = ?, iv_speed = ?, "
                    + "current_hp = ?, ev_hp = ?, ev_attack = ?, ev_defense = ?, ev_sp_attack = ?, ev_sp_defense = ?, ev_speed = ?, "
                    + "current_exp = ?, is_fainted = ? "
                    + "WHERE instance_id = ? AND trainer_id = ?";

            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, pokemon.getSpecies());
                pstmt.setString(2, pokemon.getNickname());
                pstmt.setInt(3, pokemon.getLevel());
                pstmt.setString(4, pokemon.getNature().getDisplayName());
                pstmt.setInt(5, pokemon.getIvHp());
                pstmt.setInt(6, pokemon.getIvAttack());
                pstmt.setInt(7, pokemon.getIvDefense());
                pstmt.setInt(8, pokemon.getIvSpecialAttack());
                pstmt.setInt(9, pokemon.getIvSpecialDefense());
                pstmt.setInt(10, pokemon.getIvSpeed());
                pstmt.setInt(11, pokemon.getCurrentHP());
                pstmt.setInt(12, pokemon.getEvHp());
                pstmt.setInt(13, pokemon.getEvAttack());
                pstmt.setInt(14, pokemon.getEvDefense());
                pstmt.setInt(15, pokemon.getEvSpecialAttack());
                pstmt.setInt(16, pokemon.getEvSpecialDefense());
                pstmt.setInt(17, pokemon.getEvSpeed());
                pstmt.setInt(18, pokemon.getCurrentExp());
                pstmt.setBoolean(19, pokemon.getIsFainted());
                pstmt.setInt(20, pokemon.getId());
                pstmt.setLong(21, pokemon.getTrainerDbId());

                int rowsAffected = pstmt.executeUpdate();
                if (rowsAffected > 0) {
                    LOGGER.info("Pokemon '{}' ({}) updated successfully for trainer ID {}.", pokemon.getNickname(), pokemon.getSpecies(), pokemon.getTrainerDbId());
                    return true; // Return true to indicate successful update
                } else {
                    LOGGER.warn("No Pokemon found with ID: {} for trainer ID: {}", pokemon.getId(), pokemon.getTrainerDbId());
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
                pstmt.setInt(1, pokemon.getId());
                pstmt.setInt(2, pokemon.getTrainerDbId());

                int rowsAffected = pstmt.executeUpdate();
                if (rowsAffected > 0) {
                    LOGGER.info("Pokemon '{}' ({}) deleted successfully for trainer ID {}.", pokemon.getNickname(), pokemon.getSpecies(), pokemon.getTrainerDbId());
                    return true; // Return true to indicate successful deletion
                } else {
                    LOGGER.warn("No Pokemon found with ID: {} for trainer ID: {}", pokemon.getId(), pokemon.getTrainerDiscordId());
                    return false; // Return false to indicate no Pokemon found to delete
                }
            }
        } catch (SQLException e) {
            LOGGER.error("Error deleting Pokemon: {}", e.getMessage(), e);
            return false; // Return false to indicate an error occurred
        }
    }

    public static Pokemon mapResultSetToPokemon(ResultSet rs, Trainer trainer) throws SQLException {
        int foundPokemonId = rs.getInt("instance_id");
        String species = rs.getString("species");
        String name = rs.getString("nickname");
        int level = rs.getInt("level");
        String nature = rs.getString("nature");
        int ivHp = rs.getInt("iv_hp");
        int ivAttack = rs.getInt("iv_attack");
        int ivDefense = rs.getInt("iv_defense");
        int ivSpAttack = rs.getInt("iv_sp_attack");
        int ivSpDefense = rs.getInt("iv_sp_defense");
        int ivSpeed = rs.getInt("iv_speed");
        int currentHp = rs.getInt("current_hp");
        int evHp = rs.getInt("ev_hp");
        int evAttack = rs.getInt("ev_attack");
        int evDefense = rs.getInt("ev_defense");
        int evSpAttack = rs.getInt("ev_sp_attack");
        int evSpDefense = rs.getInt("ev_sp_defense");
        int evSpeed = rs.getInt("ev_speed");
        int currentExp = rs.getInt("current_exp");
        Boolean isFainted = rs.getBoolean("is_fainted");

        // Create a new Pokemon object and populate its fields from the ResultSet
        Pokemon foundPokemon = Pokemon.createPokemon(species, name, trainer); 
        foundPokemon.setId(foundPokemonId);
        foundPokemon.setLevel(level);
        foundPokemon.setNature(Natures.valueOf(nature.toUpperCase()));
        foundPokemon.setIvHp(ivHp);
        foundPokemon.setIvAttack(ivAttack);
        foundPokemon.setIvDefense(ivDefense);
        foundPokemon.setIvSpecialAttack(ivSpAttack);
        foundPokemon.setIvSpecialDefense(ivSpDefense);
        foundPokemon.setIvSpeed(ivSpeed);
        foundPokemon.setCurrentHP(currentHp);
        foundPokemon.setEvHp(evHp);
        foundPokemon.setEvAttack(evAttack);
        foundPokemon.setEvDefense(evDefense);
        foundPokemon.setEvSpecialAttack(evSpAttack);
        foundPokemon.setEvSpecialDefense(evSpDefense);
        foundPokemon.setEvSpeed(evSpeed);
        foundPokemon.setCurrentExp(currentExp);
        foundPokemon.setIsFainted(isFainted);
        foundPokemon.calculateCurrentStats(); // Recalculate stats based on IVs, EVs, and level

        return foundPokemon; // Return the Pokémon object
    }
}

