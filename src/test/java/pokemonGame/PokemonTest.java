package pokemonGame;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

import pokemonGame.mons.Abra;
import pokemonGame.moves.Psychic;
import pokemonGame.moves.Teleport;
import pokemonGame.moves.MegaPunch;
import pokemonGame.moves.MegaKick;
import pokemonGame.moves.Toxic;

class PokemonTest {

    private Pokemon abra;

    @BeforeEach
    void setUp() {
        abra = new Abra("Test Abra");
    }

    // --- Level ---

    @Test
    void newPokemonStartsAtLevel5() {
        assertEquals(5, abra.getLevel());
    }

    @Test
    void setLevelUpdatesLevel() {
        abra.setLevel(50);
        assertEquals(50, abra.getLevel());
    }

    @Test
    void setLevelRecalculatesStats() {
        int hpAtLevel5 = abra.getMaxHP();
        abra.setLevel(50);
        assertTrue(abra.getMaxHP() > hpAtLevel5,
                "Max HP at level 50 should be higher than at level 5");
    }

    // --- Moveset management ---

    @Test
    void newPokemonStartsWithEmptyMoveset() {
        assertTrue(abra.getMoveset().isEmpty());
    }

    @Test
    void movesetNotFullWhenEmpty() {
        assertFalse(abra.isMovesetFull());
    }

    @Test
    void addMoveSuceeds() {
        assertTrue(abra.addMove(new Psychic()));
        assertEquals(1, abra.getMoveset().size());
    }

    @Test
    void addMoveStoresCorrectMove() {
        abra.addMove(new Psychic());
        assertEquals("Psychic", abra.getMoveset().get(0).getMove().getMoveName());
    }

    @Test
    void canAddUpToFourMoves() {
        abra.addMove(new Psychic());
        abra.addMove(new Teleport());
        abra.addMove(new MegaPunch());
        abra.addMove(new MegaKick());
        assertEquals(4, abra.getMoveset().size());
        assertTrue(abra.isMovesetFull());
    }

    @Test
    void addMoveFailsWhenFull() {
        abra.addMove(new Psychic());
        abra.addMove(new Teleport());
        abra.addMove(new MegaPunch());
        abra.addMove(new MegaKick());
        assertFalse(abra.addMove(new Toxic()),
                "Adding a 5th move should fail");
    }

    @Test
    void replaceMoveSucceeds() {
        abra.addMove(new Psychic());
        assertTrue(abra.replaceMove(0, new Teleport()));
        assertEquals("Teleport", abra.getMoveset().get(0).getMove().getMoveName());
    }

    @Test
    void replaceMoveFailsForInvalidSlot() {
        abra.addMove(new Psychic());
        assertFalse(abra.replaceMove(-1, new Teleport()));
        assertFalse(abra.replaceMove(5, new Teleport()));
    }

    // --- Stat attack/defense routing ---

    @Test
    void physicalMoveUsesAttackStat() {
        // MegaPunch is Physical
        Move physical = new MegaPunch();
        assertEquals("Physical", physical.getMoveCategory());
        int attackStat = abra.getAttackStatForMove(physical);
        assertEquals(abra.getCurrentAttack(), attackStat);
    }

    @Test
    void specialMoveUsesSpecialAttackStat() {
        // Psychic is Special
        Move special = new Psychic();
        assertEquals("Special", special.getMoveCategory());
        int spAtkStat = abra.getAttackStatForMove(special);
        assertEquals(abra.getCurrentSpecialAttack(), spAtkStat);
    }

    // --- IVs ---

    @Test
    void ivsAreInValidRange() {
        // IVs should be 0-31
        assertTrue(abra.getIvHp() >= 0 && abra.getIvHp() <= 31);
        assertTrue(abra.getIvAttack() >= 0 && abra.getIvAttack() <= 31);
        assertTrue(abra.getIvDefense() >= 0 && abra.getIvDefense() <= 31);
        assertTrue(abra.getIvSpecialAttack() >= 0 && abra.getIvSpecialAttack() <= 31);
        assertTrue(abra.getIvSpecialDefense() >= 0 && abra.getIvSpecialDefense() <= 31);
        assertTrue(abra.getIvSpeed() >= 0 && abra.getIvSpeed() <= 31);
    }

    // --- EVs ---

    @Test
    void newPokemonStartsWithZeroEvs() {
        assertEquals(0, abra.getEvTotal());
    }

    @Test
    void evSetterAddsToCurrentValue() {
        abra.setEvHp(100);
        abra.setEvHp(50);
        // Additive: should now be 150
        assertEquals(150, abra.getEvTotal());
    }

    @Test
    void evCapsAtPerStatMax252() {
        abra.setEvHp(252);
        abra.setEvHp(10); // Should not exceed 252
        // Total should stay at 252, not 262
        assertEquals(252, abra.getEvTotal());
    }

    // --- Nature ---

    @Test
    void pokemonHasNatureAssigned() {
        assertNotNull(abra.getNature(),
                "A newly created Pokémon should have a nature assigned");
    }

    // --- Species info ---

    @Test
    void speciesIsAbra() {
        assertEquals("Abra", abra.getSpecies());
    }

    @Test
    void primaryTypeIsPsychic() {
        assertEquals("Psychic", abra.getTypePrimary());
    }

    @Test
    void secondaryTypeIsNull() {
        assertNull(abra.getTypeSecondary(),
                "Abra is a single-type Pokémon, secondary should be null");
    }

    // --- Learnset ---

    @Test
    void learnsetIsNotEmpty() {
        assertFalse(abra.getLearnset().isEmpty());
    }
}
