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
import pokemonGame.core.Natures;
import pokemonGame.core.Stat;
import pokemonGame.core.StatCalculator;
import pokemonGame.model.Move;
import pokemonGame.model.Pokemon;
import pokemonGame.model.Trainer;
import pokemonGame.moves.PokeMove;
import pokemonGame.species.PokeSpecies;
import pokemonGame.species.PokemonFactory;

import java.sql.*;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PokemonCRUD {

    private static final Logger LOGGER = LoggerFactory.getLogger(PokemonCRUD.class);
    private static EvManager evManager = new EvManager();

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
                pstmt.setInt(1, trainer.getTrainerDbId());
                pstmt.setInt(2, pokemonId);
                try (ResultSet rs = pstmt.executeQuery()) {
                    
                    if (rs.next()) {
                        Pokemon foundPokemon = mapResultSetToPokemon(rs, trainer);
                        LOGGER.info("Pokemon '{}' ({}) retrieved successfully for trainer ID {}.", foundPokemon.getNickname(), foundPokemon.getSpecies().name(), trainer.getTrainerDbId());
                        return foundPokemon; // Return the retrieved Pokémon
                    } else {
                        LOGGER.warn("No Pokemon found with ID: {} for trainer ID: {}", pokemonId, trainer.getTrainerDbId());
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
                pstmt.setBoolean(19, pokemon.getIsFainted());
                pstmt.setInt(20, pokemon.getPokemonDbId());
                pstmt.setLong(21, pokemon.getTrainerDbId());

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

    public static Pokemon mapResultSetToPokemon(ResultSet rs, Trainer trainer) throws SQLException {

        
        int foundPokemonId = rs.getInt("instance_id");
        PokeSpecies species;
        try {
            species = PokeSpecies.valueOf(rs.getString("species"));
        } catch (IllegalArgumentException e) {
            LOGGER.error("Invalid species value in database: {}", rs.getString("species"), e);
            throw new SQLException("Invalid species value in database: " + rs.getString("species"), e);
        }
        String name = rs.getString("nickname");
        int level = rs.getInt("level");
        Natures nature;
        try {
            nature = Natures.valueOf(rs.getString("nature"));
        } catch (IllegalArgumentException e) {
            LOGGER.error("Invalid nature value in database: {}", rs.getString("nature"), e);
            throw new SQLException("Invalid nature value in database: " + rs.getString("nature"), e);
        }
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
        boolean isFainted = rs.getBoolean("is_fainted");

        // Create a new Pokemon object and populate its fields from the ResultSet
        Pokemon foundPokemon = PokemonFactory.createPokemonFromRegistry(species, name);
        foundPokemon.setTrainer(trainer); // Set the trainer using the Trainer object
        foundPokemon.setPokemonDbId(foundPokemonId);
        foundPokemon.setLevel(level);
        foundPokemon.setNature(nature);
        foundPokemon.setIvHp(ivHp);
        foundPokemon.setIvAttack(ivAttack);
        foundPokemon.setIvDefense(ivDefense);
        foundPokemon.setIvSpecialAttack(ivSpAttack);
        foundPokemon.setIvSpecialDefense(ivSpDefense);
        foundPokemon.setIvSpeed(ivSpeed);
        evManager.setEv(foundPokemon, Stat.HP, evHp);
        evManager.setEv(foundPokemon, Stat.ATTACK, evAttack);
        evManager.setEv(foundPokemon, Stat.DEFENSE, evDefense);
        evManager.setEv(foundPokemon, Stat.SPECIAL_ATTACK, evSpAttack);
        evManager.setEv(foundPokemon, Stat.SPECIAL_DEFENSE, evSpDefense);
        evManager.setEv(foundPokemon, Stat.SPEED, evSpeed);
        foundPokemon.setCurrentExp(currentExp);
        foundPokemon.setIsFainted(isFainted);
        StatCalculator.calculateAllStats(foundPokemon); // Recalculate stats based on IVs, EVs, and level
        foundPokemon.setCurrentHP(currentHp); // Set the current HP after recalculating stats

        // Load moves from pokemon_movesets and populate the in-memory moveset
        MoveCRUD moveCRUD = new MoveCRUD();
        List<String[]> moveRows = moveCRUD.getMovesForPokemon(foundPokemonId);
        for (String[] row : moveRows) {
            String moveName = row[0];
            int pp = Integer.parseInt(row[1]);
            try {
                Move move = PokeMove.fromString(moveName).createMove();
                foundPokemon.addMove(move);
                // Set the persisted PP (may differ from max if the move was partially used)
                foundPokemon.getMoveSet().get(foundPokemon.getMoveSet().size() - 1).setCurrentPP(pp);
            } catch (IllegalArgumentException e) {
                LOGGER.error("Unknown move '{}' in DB for Pokémon instance {}; skipping.", moveName, foundPokemonId);
            }
        }

        return foundPokemon; // Return the Pokémon object
    }

    public static Pokemon getPokemonByNicknameAndTrainer(String nickname, Trainer trainer) {
        try (Connection conn = DatabaseSetup.getConnection()) {
            String sql = "SELECT * FROM pokemon_instances WHERE trainer_id = ? AND nickname = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setInt(1, trainer.getTrainerDbId());
                pstmt.setString(2, nickname);
                try (ResultSet rs = pstmt.executeQuery()) {
                    if (rs.next()) {
                        Pokemon foundPokemon = mapResultSetToPokemon(rs, trainer);
                        LOGGER.info("Pokemon '{}' ({}) retrieved successfully for trainer ID {}.", foundPokemon.getNickname(), foundPokemon.getSpecies().name(), trainer.getTrainerDbId());
                        return foundPokemon; // Return the retrieved Pokémon
                    } else {
                        LOGGER.warn("No Pokemon found with nickname: '{}' for trainer ID: {}", nickname, trainer.getTrainerDbId());
                        return null; // Return null if no Pokémon is found
                    }
                }
            }
        } catch (SQLException e) {
            LOGGER.error("Error retrieving Pokemon by nickname: {}", e.getMessage(), e);
            return null; // Return null to indicate an error occurred
        }
    }
}

