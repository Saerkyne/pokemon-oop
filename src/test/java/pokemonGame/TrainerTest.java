package pokemonGame;

import org.junit.jupiter.api.Test;

import pokemonGame.model.Pokemon;
import pokemonGame.model.Team;
import pokemonGame.model.Trainer;
import pokemonGame.service.TeamService;
import pokemonGame.species.Abra;
import pokemonGame.species.Bulbasaur;

import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

class TrainerTest {

    private Trainer trainer;

    @BeforeEach
    void setUp() {
        trainer = new Trainer("Ash");
        Team team = new Team("Ash's Team");
        trainer.setTeam(team);
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
        assertEquals("Ash", trainer.getTrainerName());
    }

    /*
     * CHECKS:  A newly created Trainer has an empty (size 0) Pokémon team.
     * HOW:     Calls getTeam().isEmpty() and asserts true.
     * IMPROVE: Also assert getTeam() returns a non-null collection so callers can
     *          safely iterate without a null check.
     */
    @Test
    void newTrainerHasEmptyTeam() {
        assertTrue(trainer.getTeam().getPokemonList().isEmpty());
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
        TeamService teamService = new TeamService();
        teamService.addPokemonToTeam(trainer.getTeam(), new Abra("Abra"));
        assertEquals(1, trainer.getTeam().getTeamSize());
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
        TeamService teamService = new TeamService();
        for (int i = 1; i <= 6; i++) {
            teamService.addPokemonToTeam(trainer.getTeam(), new Abra("Abra " + i));
        }
        assertEquals(6, trainer.getTeam().getTeamSize());
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
        TeamService teamService = new TeamService();
        for (int i = 1; i <= 6; i++) {
            teamService.addPokemonToTeam(trainer.getTeam(), new Abra("Abra " + i));
        }
        teamService.addPokemonToTeam(trainer.getTeam(), new Abra("Too Many"));
        assertEquals(6, trainer.getTeam().getTeamSize(),
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
        trainer.setTrainerName("Gary");
        assertEquals("Gary", trainer.getTrainerName());
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
        TeamService teamService = new TeamService();
        teamService.addPokemonToTeam(trainer.getTeam(), abra);
        assertSame(abra, trainer.getTeam().getTeamSlot(0),
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

        TeamService teamService = new TeamService();
        teamService.addPokemonToTeam(trainer.getTeam(), first);
        teamService.addPokemonToTeam(trainer.getTeam(), second);
        teamService.addPokemonToTeam(trainer.getTeam(), third);

        assertSame(first, trainer.getTeam().getTeamSlot(0));
        assertSame(second, trainer.getTeam().getTeamSlot(1));
        assertSame(third, trainer.getTeam().getTeamSlot(2));
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
        TeamService teamService = new TeamService();
        teamService.addPokemonToTeam(trainer.getTeam(), new Abra("Abra"));
        teamService.addPokemonToTeam(trainer.getTeam(), new Bulbasaur("Bulbasaur"));
        assertEquals(2, trainer.getTeam().getTeamSize());
        assertEquals("Abra", trainer.getTeam().getTeamSlot(0).getNickname());
        assertEquals("Bulbasaur", trainer.getTeam().getTeamSlot(1).getNickname());
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
        TeamService teamService = new TeamService();
        teamService.addPokemonToTeam(trainer.getTeam(), new Abra("Ash's Abra"));
        teamService.addPokemonToTeam(other.getTeam(), new Bulbasaur("Gary's Bulbasaur"));

        assertEquals(1, trainer.getTeam().getTeamSize());
        assertEquals(1, other.getTeam().getTeamSize());
        assertEquals("Ash's Abra", trainer.getTeam().getTeamSlot(0).getNickname());
        assertEquals("Gary's Bulbasaur", other.getTeam().getTeamSlot(0).getNickname());
    }
}
