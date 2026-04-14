package pokemonGame.modelTests;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import pokemonGame.model.Trainer;
import pokemonGame.model.Team;
import pokemonGame.model.Pokemon;
import pokemonGame.species.Abra;

/**
 * Unit tests for the {@link Trainer} class.
 * 
 * This test class will cover the following aspects of the Trainer class:
 * - Constructor and getters: Ensure that the Trainer is initialized correctly and that getters return the expected values.
 * - Adding and removing Pokémon: Test the functionality of adding and removing Pokémon from the Trainer's collection.
 * - Battle functionality: If the Trainer class includes methods for battling other Trainers or Pokémon, test these methods to ensure they work as expected.
 * - Edge cases: Test edge cases such as adding duplicate Pokémon, removing Pokémon that do not exist, and handling empty collections.
 */

class TrainerTest {
    private Trainer trainer= new Trainer("Ash Ketchum");


    // =========================================================================
    // --- Constructor, Setters, and Getters Tests ---
    // =========================================================================


    /*
     * CHECKS:   Initializes a Trainer with the given name and verifies that the getter returns the correct name.
     * HOW:      Asserts the trainer's name is "Ash Ketchum" after construction.
     * IDEAL:    The trainer's name should be "Ash Ketchum".
     */
    @Test
    void testConstructor() {
        assertTrue(trainer.getTrainerName().equals("Ash Ketchum"));
    }

    /*
     * CHECKS:   Updates the trainer's name and verifies that the getter returns the updated name.
     * HOW:      Sets the trainer's name to "Misty" and asserts that the getter returns "Misty".
     * IDEAL:    The trainer's name should be "Misty".
     */
    @Test
    void testSetGetTrainerName() {
        trainer.setTrainerName("Misty");
        assertTrue(trainer.getTrainerName().equals("Misty"));
    }

    /*
     * CHECKS:   Updates the trainer's database ID and verifies that the getter returns the updated ID.
     * HOW:      Sets the trainer's database ID to 123 and asserts that the getter returns 123.
     * IDEAL:    The trainer's database ID should be 123.
     */
    @Test
    void testSetGetTrainerDbId() {
        trainer.setTrainerDbId(123);
        assertEquals(123, trainer.getTrainerDbId());
    }

    /*
     * CHECKS:   Updates the trainer's Discord ID and verifies that the getter returns the updated ID.
     * HOW:      Sets the trainer's Discord ID to 987654321L and asserts that the getter returns 987654321L.
     * IDEAL:    The trainer's Discord ID should be 987654321L.
     */
    @Test
    void testSetGetDiscordId() {
        trainer.setDiscordId(987654321L);
        assertEquals(987654321L, trainer.getDiscordId());
    }   


    /*
     * CHECKS:   Creates a new team for the trainer and verifies that the team is created correctly.
     * HOW:      Creates a team named "Pallet Town Team" and asserts that the team is not null and has the correct name.
     * IDEAL:    The team should be created with the name "Pallet Town Team".
     */
    @Test
    void testCreateTeam() {
        trainer.createTeam("Pallet Town Team");
        assertNotNull(trainer.getTeam("Pallet Town Team"));
        assertEquals("Pallet Town Team", trainer.getTeam("Pallet Town Team").getTeamName());
    }

    /*
     * CHECKS:   Sets a team for the trainer and verifies that the team is set correctly.
     * HOW:      Creates a team named "Pallet Town Team", sets it for the trainer, and asserts that the team is not null and has the correct name.
     * IDEAL:    The team should be set with the name "Pallet Town Team".
     */
    @Test
    void testSetTeam() {
        Team team = new Team("Pallet Town Team");
        trainer.setTeam(team);
        assertNotNull(trainer.getTeam("Pallet Town Team"));
        assertEquals("Pallet Town Team", trainer.getTeam("Pallet Town Team").getTeamName());
    }

    @Test
    void testGetPokemonFromTeam() {
        trainer.createTeam("Pallet Town Team");
        Team team = trainer.getTeam("Pallet Town Team");
        Pokemon abra = new Abra("Test Abra");
        team.add(abra);;
        assertNotNull(team);
        assertEquals("Pallet Town Team", trainer.getTeam("Pallet Town Team").getTeamName());
        assertEquals(1, trainer.getTeam("Pallet Town Team").getTeamAsList().size());
        assertEquals(abra, trainer.getTeam("Pallet Town Team").getTeamAsList().get(0));
    }   

}
