package pokemonGame;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

import pokemonGame.mons.Abra;

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
}
