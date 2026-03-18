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
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PokemonCRUD {
    public static void main(String[] args) {
        // initialize database connection
        try (Connection conn = DatabaseSetup.getConnection()) {
            // Create a trainer and some Pokémon for testing
            try (Statement stmt = conn.createStatement()) {
                stmt.executeUpdate(
                    "INSERT INTO trainers (name) VALUES ('Red')"
                );
                // Get the ID of the newly created trainer
                ResultSet rs = stmt.executeQuery(
                    "SELECT trainer_id FROM trainers WHERE name = 'Red'"
                );
                // Assuming the trainer was created successfully, we should have a result
                rs.next();
                // Get the trainer ID for 'Red'
                int redId = rs.getInt("trainer_id");
                // Print the trainer ID to confirm it was created
                System.out.println("Created trainer 'Red' with ID: " + redId);
                // Now we can create some Pokémon for 'Red' using the retrieved trainer ID
                stmt.executeUpdate(
                    "INSERT INTO pokemon (species, name, dex_index, type_primary, type_secondary, "
                    + "level, base_hp, base_attack, base_defense, base_sp_attack, base_sp_defense, "
                    + "base_speed, trainer_id) VALUES "
                    + "('Bulbasaur', 'Bulby', 1, 'Grass', 'Poison', 5, 45, 49, 49, 65, 65, 45, " + redId + ")"
                );

                stmt.executeUpdate(
                    "INSERT INTO pokemon (species, name, dex_index, type_primary, type_secondary, "
                    + "level, base_hp, base_attack, base_defense, base_sp_attack, base_sp_defense, "
                    + "base_speed, trainer_id) VALUES "
                    + "('Charmander', 'Charmander', 4, 'Fire', NULL, 5, 39, 52, 43, 60, 50, 65, " + redId + ")"
                );
            }

            try (Statement stmt = conn.createStatement()) {
                ResultSet rs = stmt.executeQuery(
                  "SELECT p.name, p.species, p.level, p.type_primary, p.type_secondary, "
                  + "FROM Pokemon p "
                  + "JOIN trainers t ON p.trainer_id = t.trainer_id "
                  + "WHERE t.name = 'Red'"
                );

                System.out.println("\nRed's Team:");
                while(rs.next()) {
                    String name = rs.getString("name");
                    String species = rs.getString("species");
                    int level = rs.getInt("level");
                    String typePrimary = rs.getString("type_primary");
                    String typeSecondary = rs.getString("type_secondary"); //may be null
                    String typeStr = typeSecondary != null ? typePrimary + "/" + typeSecondary : typePrimary;
                    System.out.printf("  Lv. %d %s (%s) [%s]%n", level, name, species, typeStr);
                }
            }

            try (Statement stmt = conn.createStatement()) {
                int rows = stmt.executeUpdate(
                    "UPDATE pokemon SET level = 6 WHERE species = 'Bulbasaur' AND name = 'Bulby'"
                );
                System.out.println("\nUpdated " + rows + " row(s) - Bulby leveled up to 6!");
            }

            try (Statement stmt = conn.createStatement()) {
                int rows = stmt.executeUpdate(
                    "DELETE FROM pokemon WHERE species = 'Charmander' AND name = 'Charmander'"
                );
                System.out.println("Deleted " + rows + " row(s) — Charmander was released.");
            }

        } catch (SQLException e) {
            System.err.println("Database error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}

