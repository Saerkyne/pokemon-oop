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
import java.sql.*;

public class PokemonCRUD {
    public static void main(String[] args) {
        // initialize database connection
        try (Connection conn = DatabaseSetup.getConnection()) {
            // Create a trainer and some Pokémon for testing
            try (Statement stmt = conn.createStatement()) {
                stmt.executeUpdate(
                    "INSERT INTO trainers (discord_id, discord_username, name) "
                    + "VALUES (123456789012345678, 'Calabriel', 'Red')"
                );
                // Get the ID of the newly created trainer
                ResultSet rs = stmt.executeQuery(
                    "SELECT trainer_id FROM trainers WHERE discord_id = 123456789012345678"
                );
                // Assuming the trainer was created successfully, we should have a result
                rs.next();
                // Get the trainer ID for 'Red'
                int redId = rs.getInt("trainer_id");
                // Print the trainer ID to confirm it was created
                System.out.println("Created trainer 'Red' with ID: " + redId);
                // Now we can create some Pokémon for 'Red' using the retrieved trainer ID
                stmt.executeUpdate(
                    "INSERT INTO pokemon_instances "
                    + "(trainer_id, species, nickname, level, nature, "
                    + "iv_hp, iv_attack, iv_defense, iv_sp_attack, iv_sp_defense, iv_speed, "
                    + "current_hp) VALUES "
                    + "(" + redId + ", 'Bulbasaur', 'Bulby', 5, 'Adamant', "
                    + "28, 31, 14, 22, 19, 25, "
                    + "21)"
                );

                stmt.executeUpdate(
                    "INSERT INTO pokemon_instances "
                    + "(trainer_id, species, nickname, level, nature, "
                    + "iv_hp, iv_attack, iv_defense, iv_sp_attack, iv_sp_defense, iv_speed, "
                    + "current_hp) VALUES "
                    + "(" + redId + ", 'Charmander', 'Charmander', 5, 'Jolly', "
                    + "15, 22, 18, 27, 11, 30, "
                    + "19)"
                );
            }

            try (Statement stmt = conn.createStatement()) {
                ResultSet rs = stmt.executeQuery(
                    "SELECT pi.nickname, pi.species, pi.level, pi.nature, pi.current_hp "
                    + "FROM pokemon_instances pi "
                    + "JOIN trainers t ON pi.trainer_id = t.trainer_id "
                    + "WHERE t.name = 'Red'"
                );

                System.out.println("\nRed's team:");
                while (rs.next()) {
                    System.out.printf("  Lv.%d %s (%s) [%s] HP: %d%n",
                            rs.getInt("level"),
                            rs.getString("nickname"),
                            rs.getString("species"),
                            rs.getString("nature"),
                            rs.getInt("current_hp"));
                }
            }

            try (Statement stmt = conn.createStatement()) {
                int rows = stmt.executeUpdate(
                    "UPDATE pokemon_instances SET level = 6 "
                    + "WHERE species = 'Bulbasaur' AND nickname = 'Bulby'"
                );
                System.out.println("\nUpdated " + rows + " row(s) — Bulby leveled up to 6!");
            }

            try (Statement stmt = conn.createStatement()) {
                int rows = stmt.executeUpdate(
                    "DELETE FROM pokemon_instances "
                    + "WHERE species = 'Charmander' AND nickname = 'Charmander'"
                );
                System.out.println("Deleted " + rows + " row(s) — Charmander was released.");
            }

        } catch (SQLException e) {
            System.err.println("Database error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}

