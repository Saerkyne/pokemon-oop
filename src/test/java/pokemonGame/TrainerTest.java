package pokemonGame;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

import pokemonGame.mons.Abra;
import pokemonGame.mons.Bulbasaur;

class TrainerTest {

    private Trainer trainer;

    @BeforeEach
    void setUp() {
        trainer = new Trainer("Ash");
    }

    /*
     * CHECKS:  The Trainer constructor stores the name passed to it, and getName()
     *          returns it correctly.
     * HOW:     Creates Trainer("Ash") and asserts getName() equals "Ash".
     * IMPROVE: Test edge-case names (empty string, very long name, null) to confirm
     *          the constructor validates or stores them without unexpected errors.
     */
    @Test
    void trainerHasCorrectName() {
        assertEquals("Ash", trainer.getName());
    }

    /*
     * CHECKS:  A newly created Trainer has an empty (size 0) Pokémon team.
     * HOW:     Calls getTeam().isEmpty() and asserts true.
     * IMPROVE: Also assert getTeam() returns a non-null collection so callers can
     *          safely iterate without a null check.
     */
    @Test
    void newTrainerHasEmptyTeam() {
        assertTrue(trainer.getTeam().isEmpty());
    }

    /*
     * CHECKS:  addPokemonToTeam() successfully adds a Pokémon, increasing team size
     *          from 0 to 1.
     * HOW:     Adds one Abra and asserts getTeam().size() equals 1.
     * IMPROVE: Also assert the added Pokémon is the one at index 0 (covered separately
     *          by addedPokemonIsRetrievable). Consider combining the size and identity
     *          checks into a single focused setup test.
     */
    @Test
    void addPokemonIncreasesTeamSize() {
        trainer.addPokemonToTeam(new Abra("Abra"));
        assertEquals(1, trainer.getTeam().size());
    }

    /*
     * CHECKS:  A trainer can add exactly six Pokémon to their team (the standard
     *          Gen I–IX team size limit).
     * HOW:     Adds 6 Abra instances in a loop and asserts the final team size is 6.
     * IMPROVE: Add intermediate size assertions (1 through 5) to confirm the team grows
     *          correctly at each step, not just the final count.
     */
    @Test
    void canAddUpToSixPokemon() {
        for (int i = 1; i <= 6; i++) {
            trainer.addPokemonToTeam(new Abra("Abra " + i));
        }
        assertEquals(6, trainer.getTeam().size());
    }

    /*
     * CHECKS:  addPokemonToTeam() silently rejects a 7th Pokémon when the team is
     *          already full, keeping the team size at 6.
     * HOW:     Fills the team with 6 Pokémon, attempts to add a 7th, and asserts the
     *          team size is still 6.
     * IMPROVE: Verify the 7th Pokémon was not added by asserting the last team member
     *          is NOT "Too Many". Also consider asserting addPokemonToTeam returns false
     *          (or throws) when the team is full, making the failure mode explicit.
     */
    @Test
    void cannotAddSeventhPokemon() {
        for (int i = 1; i <= 6; i++) {
            trainer.addPokemonToTeam(new Abra("Abra " + i));
        }
        trainer.addPokemonToTeam(new Abra("Too Many"));
        assertEquals(6, trainer.getTeam().size(),
                "Team should remain at 6 when trying to add a 7th Pokémon");
    }

    /*
     * CHECKS:  setName() updates the Trainer's display name.
     * HOW:     Calls trainer.setName("Gary") and asserts getName() returns "Gary".
     * IMPROVE: Verify that the original name ("Ash") is fully replaced, not appended.
     *          Also test setName(null) and setName("") to confirm edge-case handling.
     */
    @Test
    void setNameUpdatesTrainerName() {
        trainer.setName("Gary");
        assertEquals("Gary", trainer.getName());
    }

    /*
     * CHECKS:  The exact Pokémon object added via addPokemonToTeam() is retrievable
     *          from the team at index 0 (no defensive copy is made).
     * HOW:     Adds an Abra and uses assertSame to verify the retrieved object is the
     *          same reference.
     * IMPROVE: Test with multiple Pokémon to confirm identity is preserved at every
     *          index, not just index 0.
     */
    @Test
    void addedPokemonIsRetrievable() {
        Pokemon abra = new Abra("My Abra");
        trainer.addPokemonToTeam(abra);
        assertSame(abra, trainer.getTeam().get(0),
                "The same Pokémon object should be retrievable from the team");
    }

    /*
     * CHECKS:  The team preserves insertion order: the first Pokémon added is at index
     *          0, the second at index 1, and the third at index 2.
     * HOW:     Adds three Pokémon in a known order and asserts each is at its expected
     *          index via assertSame.
     * IMPROVE: Verify ordering at full capacity (6 Pokémon) to confirm no sort or
     *          shuffle occurs when the team is completely full.
     */
    @Test
    void teamPreservesInsertionOrder() {
        Pokemon first = new Abra("First");
        Pokemon second = new Bulbasaur("Second");
        Pokemon third = new Abra("Third");

        trainer.addPokemonToTeam(first);
        trainer.addPokemonToTeam(second);
        trainer.addPokemonToTeam(third);

        assertSame(first, trainer.getTeam().get(0));
        assertSame(second, trainer.getTeam().get(1));
        assertSame(third, trainer.getTeam().get(2));
    }

    /*
     * CHECKS:  The team can hold Pokémon of different species; addPokemonToTeam() does
     *          not enforce a single-species restriction.
     * HOW:     Adds an Abra and a Bulbasaur and asserts both are in the team at the
     *          correct indices with the expected names.
     * IMPROVE: Test with six different species (a full team) to confirm there is no
     *          uniqueness constraint on species or name.
     */
    @Test
    void canAddDifferentSpecies() {
        trainer.addPokemonToTeam(new Abra("Abra"));
        trainer.addPokemonToTeam(new Bulbasaur("Bulbasaur"));
        assertEquals(2, trainer.getTeam().size());
        assertEquals("Abra", trainer.getTeam().get(0).getNickname());
        assertEquals("Bulbasaur", trainer.getTeam().get(1).getNickname());
    }

    /*
     * CHECKS:  Two Trainer instances each maintain their own independent team;
     *          adding a Pokémon to one trainer does not affect the other.
     * HOW:     Adds one Pokémon to each of two trainers and asserts each team has
     *          exactly 1 entry with the correct species name.
     * IMPROVE: Also remove a Pokémon from one trainer (if removal is supported) and
     *          verify the other trainer's team is unaffected, providing stronger
     *          evidence of collection independence.
     */
    @Test
    void twoTrainersHaveIndependentTeams() {
        Trainer other = new Trainer("Gary");
        trainer.addPokemonToTeam(new Abra("Ash's Abra"));
        other.addPokemonToTeam(new Bulbasaur("Gary's Bulbasaur"));

        assertEquals(1, trainer.getTeam().size());
        assertEquals(1, other.getTeam().size());
        assertEquals("Ash's Abra", trainer.getTeam().get(0).getNickname());
        assertEquals("Gary's Bulbasaur", other.getTeam().get(0).getNickname());
    }
}
