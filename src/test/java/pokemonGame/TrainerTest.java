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

    @Test
    void trainerHasCorrectName() {
        assertEquals("Ash", trainer.getName());
    }

    @Test
    void newTrainerHasEmptyTeam() {
        assertTrue(trainer.getTeam().isEmpty());
    }

    @Test
    void addPokemonIncreasesTeamSize() {
        trainer.addPokemonToTeam(new Abra("Abra"));
        assertEquals(1, trainer.getTeam().size());
    }

    @Test
    void canAddUpToSixPokemon() {
        for (int i = 1; i <= 6; i++) {
            trainer.addPokemonToTeam(new Abra("Abra " + i));
        }
        assertEquals(6, trainer.getTeam().size());
    }

    @Test
    void cannotAddSeventhPokemon() {
        for (int i = 1; i <= 6; i++) {
            trainer.addPokemonToTeam(new Abra("Abra " + i));
        }
        trainer.addPokemonToTeam(new Abra("Too Many"));
        assertEquals(6, trainer.getTeam().size(),
                "Team should remain at 6 when trying to add a 7th Pokémon");
    }

    @Test
    void setNameUpdatesTrainerName() {
        trainer.setName("Gary");
        assertEquals("Gary", trainer.getName());
    }

    @Test
    void addedPokemonIsRetrievable() {
        Pokemon abra = new Abra("My Abra");
        trainer.addPokemonToTeam(abra);
        assertSame(abra, trainer.getTeam().get(0),
                "The same Pokémon object should be retrievable from the team");
    }

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

    @Test
    void canAddDifferentSpecies() {
        trainer.addPokemonToTeam(new Abra("Abra"));
        trainer.addPokemonToTeam(new Bulbasaur("Bulbasaur"));
        assertEquals(2, trainer.getTeam().size());
        assertEquals("Abra", trainer.getTeam().get(0).getName());
        assertEquals("Bulbasaur", trainer.getTeam().get(1).getName());
    }

    @Test
    void twoTrainersHaveIndependentTeams() {
        Trainer other = new Trainer("Gary");
        trainer.addPokemonToTeam(new Abra("Ash's Abra"));
        other.addPokemonToTeam(new Bulbasaur("Gary's Bulbasaur"));

        assertEquals(1, trainer.getTeam().size());
        assertEquals(1, other.getTeam().size());
        assertEquals("Ash's Abra", trainer.getTeam().get(0).getName());
        assertEquals("Gary's Bulbasaur", other.getTeam().get(0).getName());
    }
}
