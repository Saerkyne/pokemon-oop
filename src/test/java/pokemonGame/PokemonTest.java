package pokemonGame;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import static org.junit.jupiter.api.Assertions.*;

import pokemonGame.mons.Abra;
import pokemonGame.mons.Bulbasaur;
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

    @Test
    void setLevelResetsCurrentHPToMax() {
        abra.setLevel(50);
        assertEquals(abra.getMaxHP(), abra.getCurrentHP(),
                "After setLevel, current HP should equal max HP");
    }

    @Test
    void levelUpIncrementsLevel() {
        int before = abra.getLevel();
        abra.levelUp();
        assertEquals(before + 1, abra.getLevel());
    }

    @Test
    void levelUpRecalculatesStats() {
        abra.setLevel(49);
        int hpAt49 = abra.getMaxHP();
        abra.levelUp();
        assertTrue(abra.getMaxHP() >= hpAt49,
                "Max HP should not decrease after leveling up");
        assertEquals(50, abra.getLevel());
    }

    // --- Name ---

    @Test
    void setNameUpdatesName() {
        abra.setName("Kadabra");
        assertEquals("Kadabra", abra.getName());
    }

    @Test
    void defaultNameMatchesSpecies() {
        Pokemon fresh = new Abra("Abra");
        assertEquals("Abra", fresh.getName());
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
        Move physical = new MegaPunch();
        assertEquals("Physical", physical.getMoveCategory());
        int attackStat = abra.getAttackStatForMove(physical);
        assertEquals(abra.getCurrentAttack(), attackStat);
    }

    @Test
    void specialMoveUsesSpecialAttackStat() {
        Move special = new Psychic();
        assertEquals("Special", special.getMoveCategory());
        int spAtkStat = abra.getAttackStatForMove(special);
        assertEquals(abra.getCurrentSpecialAttack(), spAtkStat);
    }

    @Test
    void statusMoveReturnsZeroAttack() {
        Move teleport = new Teleport();
        assertEquals("Status", teleport.getMoveCategory());
        assertEquals(0, abra.getAttackStatForMove(teleport));
    }

    @Test
    void physicalMoveUsesDefenseStat() {
        Move physical = new MegaPunch();
        assertEquals(abra.getCurrentDefense(), abra.getDefenseStatForMove(physical));
    }

    @Test
    void specialMoveUsesSpecialDefenseStat() {
        Move special = new Psychic();
        assertEquals(abra.getCurrentSpecialDefense(), abra.getDefenseStatForMove(special));
    }

    @Test
    void statusMoveReturnsZeroDefense() {
        Move teleport = new Teleport();
        assertEquals(0, abra.getDefenseStatForMove(teleport));
    }

    // --- IVs ---

    @Test
    void ivsAreInValidRange() {
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
        assertEquals(252, abra.getEvTotal());
    }

    @Test
    void evTotalCannotExceed510() {
        abra.setEvAttack(252);
        abra.setEvSpeed(252);
        // evTotal is now 504; only 6 more EVs can fit under the 510 cap
        abra.setEvHp(100); // Requests 100 but only 6 should be granted
        assertEquals(510, abra.getEvTotal(),
                "EV total should be capped at exactly 510");
        assertEquals(6, abra.getEvHp(),
                "Only 6 HP EVs should have been added (510 - 504 = 6 remaining room)");
    }

    @Test
    void individualEvsCappedAt252() {
        abra.setEvDefense(252);
        assertEquals(252, abra.getEvDefense());
    }

    // --- Nature ---

    @Test
    void pokemonHasNatureAssigned() {
        assertNotNull(abra.getNature(),
                "A newly created Pokémon should have a nature assigned");
    }

    @Test
    void setNatureUpdatesNature() {
        abra.setNature(Natures.ADAMANT);
        assertEquals(Natures.ADAMANT, abra.getNature());
    }

    @Test
    void applyNatureSetsANature() {
        abra.applyNature();
        assertNotNull(abra.getNature());
    }

    // --- isFainted ---

    @Test
    void newPokemonIsNotFainted() {
        assertFalse(abra.getIsFainted());
    }

    @Test
    void setIsFaintedUpdatesFlag() {
        abra.setIsFainted(true);
        assertTrue(abra.getIsFainted());
    }

    @Test
    void setIsFaintedBackToFalse() {
        abra.setIsFainted(true);
        abra.setIsFainted(false);
        assertFalse(abra.getIsFainted());
    }

    // --- HP management ---

    @Test
    void currentHPStartsAtMaxHP() {
        assertEquals(abra.getMaxHP(), abra.getCurrentHP(),
                "Current HP should start at max HP");
    }

    @Test
    void setCurrentHPUpdatesHP() {
        abra.setCurrentHP(10);
        assertEquals(10, abra.getCurrentHP());
    }

    // IDEAL BEHAVIOR: setCurrentHP should clamp at 0 so HP is never negative.
    // CURRENT BEHAVIOR: setCurrentHP stores whatever int it receives, including
    // negative values. This is the root cause of the HP-below-zero issues
    // documented in BattleTest.dealDamage_hpShouldBeClampedAtZero.
    // TODO: Remove @Disabled when setCurrentHP clamps at 0.
    @Disabled("KNOWN LIMITATION: setCurrentHP does not clamp — negative HP can be stored")
    @Test
    void setCurrentHP_shouldClampAtZero() {
        abra.setCurrentHP(-5);
        assertEquals(0, abra.getCurrentHP(),
                "HP should be clamped at 0 when set to a negative value");
    }

    // --- Stat calculation formulas ---

    @Test
    void calcMaxHPUsesCorrectFormula() {
        // Formula: ((2*base + IV + EV/4) * level / 100) + level + 10
        int result = abra.calcMaxHP(45, 50, 15, 100);
        int expected = ((2 * 45 + 15 + 100 / 4) * 50 / 100) + 50 + 10;
        assertEquals(expected, result);
    }

    @Test
    void calcCurrentStatUsesCorrectFormula() {
        // Formula: (((2*base + IV + EV/4) * level / 100) + 5) * nature
        int result = abra.calcCurrentStat(80, 50, 20, 0, 1.0);
        int expected = (int) Math.floor((((2 * 80 + 20 + 0) * 50 / 100) + 5) * 1.0);
        assertEquals(expected, result);
    }

    @Test
    void calcCurrentStatWithNatureBoost() {
        int neutral = abra.calcCurrentStat(80, 50, 20, 0, 1.0);
        int boosted = abra.calcCurrentStat(80, 50, 20, 0, 1.1);
        assertTrue(boosted > neutral,
                "A boosted nature should give a higher stat");
    }

    @Test
    void calcCurrentStatWithNaturePenalty() {
        int neutral = abra.calcCurrentStat(80, 50, 20, 0, 1.0);
        int lowered = abra.calcCurrentStat(80, 50, 20, 0, 0.9);
        assertTrue(lowered < neutral,
                "A lowered nature should give a lower stat");
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

    @Test
    void dualTypePokemonHasSecondaryType() {
        Pokemon bulba = new Bulbasaur("Bulba");
        assertEquals("Grass", bulba.getTypePrimary());
        assertEquals("Poison", bulba.getTypeSecondary());
    }

    // --- Learnset ---

    @Test
    void learnsetIsNotEmpty() {
        assertFalse(abra.getLearnset().isEmpty());
    }

    // --- EV yield ---

    @Test
    void evYieldIsNotNull() {
        assertNotNull(abra.getEvYield(),
                "EV yield should be set for species");
    }

    // --- Base stat setters recalculate ---

    @Test
    void setHpBaseRecalculatesStats() {
        int hpBefore = abra.getMaxHP();
        abra.setHpBase(200);
        assertTrue(abra.getMaxHP() > hpBefore,
                "Increasing HP base should increase max HP");
    }

    @Test
    void setSpeedBaseRecalculatesStats() {
        int speedBefore = abra.getCurrentSpeed();
        abra.setSpeedBase(200);
        assertTrue(abra.getCurrentSpeed() > speedBefore,
                "Increasing speed base should increase current speed");
    }
}
