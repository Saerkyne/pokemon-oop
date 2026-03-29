package pokemonGame;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

import pokemonGame.mons.Abra;
import pokemonGame.mons.Bulbasaur;
import pokemonGame.moves.Psychic;
import pokemonGame.moves.Teleport;
import pokemonGame.moves.MegaPunch;
import pokemonGame.moves.MegaKick;
import pokemonGame.moves.Toxic;
import pokemonGame.TypeChart.Category;
import pokemonGame.TypeChart.Type;

class PokemonTest {

    private Pokemon abra;

    @BeforeEach
    void setUp() {
        abra = new Abra("Test Abra");
    }

    // --- Level ---

    /*
     * CHECKS:  A freshly constructed Pokémon starts at level 5, the universal default
     *          starting level used throughout the game.
     * HOW:     Calls getLevel() on a newly constructed Abra and asserts it equals 5.
     * IMPROVE: Verify this default across multiple species to confirm level 5 is
     *          universal and not Abra-specific behavior.
     */
    @Test
    void newPokemonStartsAtLevel5() {
        assertEquals(5, abra.getLevel());
    }

    /*
     * CHECKS:  setLevel() changes the level field to the specified value.
     * HOW:     Calls setLevel(50) and asserts getLevel() returns 50.
     * IMPROVE: Test boundary levels (1, 100) and invalid inputs (0, 101, negative) to
     *          confirm the method validates or clamps the level appropriately.
     */
    @Test
    void setLevelUpdatesLevel() {
        abra.setLevel(50);
        assertEquals(50, abra.getLevel());
    }

    

    

    /*
     * CHECKS:  levelUp() increases the Pokémon's level by exactly 1.
     * HOW:     Records the level before calling levelUp(), then asserts level equals
     *          the recorded value + 1.
     * IMPROVE: Test levelUp() at level 100 (the maximum) to confirm it either caps at
     *          100 or throws, rather than allowing level 101.
     */
    @Test
    void levelUpIncrementsLevel() {
        int before = abra.getLevel();
        abra.levelUp();
        assertEquals(before + 1, abra.getLevel());
    }

    /*
     * CHECKS:  After levelUp(), maxHP does not decrease (stats are recalculated upward
     *          on every level gain), and the Pokémon's level is now 50.
     * HOW:     Sets level to 49, records maxHP, calls levelUp(), then asserts the new
     *          maxHP is >= the recorded value and the level is 50.
     * IMPROVE: Assert maxHP strictly increases (not just >=) since any level gain with
     *          normal IVs/EVs should produce a stat gain. Also verify at least one
     *          other stat to confirm full recalculation occurs.
     */
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

    /*
     * CHECKS:  setName() updates the Pokémon's display name (nickname).
     * HOW:     Calls setName("Kadabra") and asserts getName() returns "Kadabra".
     * IMPROVE: Confirm the species name is unaffected by a nickname change (i.e.,
     *          getSpecies() still returns "Abra" after setName("Kadabra")). Also test
     *          null and empty string inputs to verify validation behavior.
     */
    @Test
    void setNameUpdatesName() {
        abra.setNickname("Kadabra");
        assertEquals("Kadabra", abra.getNickname());
    }

    /*
     * CHECKS:  When a Pokémon is constructed with its species name, getName() returns
     *          that exact string (the name is correctly stored at construction time).
     * HOW:     Creates a new Abra("Abra") and asserts getName() returns "Abra".
     * IMPROVE: Test with a custom nickname (new Abra("Sparky")) to confirm the
     *          constructor stores the provided name rather than always defaulting to the
     *          species name.
     */
    @Test
    void defaultNameMatchesSpecies() {
        Pokemon fresh = new Abra("Abra");
        assertEquals("Abra", fresh.getNickname());
    }

    // --- Moveset management ---

    /*
     * CHECKS:  A newly created Pokémon has no moves in its moveset (empty collection).
     * HOW:     Calls getMoveset().isEmpty() and asserts true.
     * IMPROVE: Also assert getMoveset() returns a non-null collection so callers can
     *          safely iterate without performing a null check first.
     */
    @Test
    void newPokemonStartsWithEmptyMoveset() {
        assertTrue(abra.getMoveset().isEmpty());
    }

    /*
     * CHECKS:  isMovesetFull() returns false when the moveset is empty (capacity not
     *          yet reached).
     * HOW:     Asserts isMovesetFull() is false on a freshly constructed Abra.
     * IMPROVE: Also assert isMovesetFull() returns false after adding 1, 2, and 3
     *          moves, confirming the "full" state is triggered only at exactly 4.
     */
    @Test
    void movesetNotFullWhenEmpty() {
        assertFalse(abra.isMovesetFull());
    }

    /*
     * CHECKS:  addMove() returns true on success and adds the move to the moveset.
     * HOW:     Calls addMove(new Psychic()), asserts the return value is true, and
     *          asserts getMoveset().size() is 1.
     * IMPROVE: Note: the test name has a typo ("Suceeds" vs "Succeeds"). Also assert
     *          the stored move is the correct one (covered by addMoveStoresCorrectMove).
     */
    @Test
    void addMoveSuceeds() {
        assertTrue(abra.addMove(new Psychic()));
        assertEquals(1, abra.getMoveset().size());
    }

    /*
     * CHECKS:  The move stored by addMove() can be retrieved and has the correct name.
     * HOW:     Adds a Psychic move and asserts the stored move name equals "Psychic"
     *          via getMoveset().get(0).getMove().getMoveName().
     * IMPROVE: Use assertSame instead of a name comparison to verify the identical Move
     *          object is stored (not a copy), guarding against accidental deep-copy bugs.
     */
    @Test
    void addMoveStoresCorrectMove() {
        abra.addMove(new Psychic());
        assertEquals("Psychic", abra.getMoveset().get(0).getMove().getMoveName());
    }

    /*
     * CHECKS:  Up to four moves can be added, and isMovesetFull() returns true only
     *          when the moveset is at capacity (4 moves).
     * HOW:     Adds four distinct moves and asserts size equals 4 and isMovesetFull()
     *          is true.
     * IMPROVE: Add intermediate assertions after each move addition (1, 2, 3 = not
     *          full; 4 = full) to confirm the capacity check is correct at every step.
     */
    @Test
    void canAddUpToFourMoves() {
        abra.addMove(new Psychic());
        abra.addMove(new Teleport());
        abra.addMove(new MegaPunch());
        abra.addMove(new MegaKick());
        assertEquals(4, abra.getMoveset().size());
        assertTrue(abra.isMovesetFull());
    }

    /*
     * CHECKS:  addMove() returns false and does not insert a 5th move when the moveset
     *          is already at capacity (4 moves).
     * HOW:     Fills the moveset with 4 moves, attempts to add a 5th, asserts false.
     * IMPROVE: Also assert the moveset size is still exactly 4 after the failed add,
     *          confirming the existing moves were not modified or overwritten.
     */
    @Test
    void addMoveFailsWhenFull() {
        abra.addMove(new Psychic());
        abra.addMove(new Teleport());
        abra.addMove(new MegaPunch());
        abra.addMove(new MegaKick());
        assertFalse(abra.addMove(new Toxic()),
                "Adding a 5th move should fail");
    }

    /*
     * CHECKS:  replaceMove() swaps a move at a valid slot index and returns true.
     * HOW:     Adds Psychic to slot 0, calls replaceMove(0, new Teleport()), and asserts
     *          the slot now contains "Teleport".
     * IMPROVE: Assert the moveset size stays at 1 after the replacement (it should not
     *          grow). Also test replacing a move in the middle of a 4-move set to
     *          confirm only the targeted slot is changed.
     */
    @Test
    void replaceMoveSucceeds() {
        abra.addMove(new Psychic());
        assertTrue(abra.replaceMove(0, new Teleport()));
        assertEquals("Teleport", abra.getMoveset().get(0).getMove().getMoveName());
    }

    /*
     * CHECKS:  replaceMove() returns false for out-of-bounds indices (-1 and 5) without
     *          throwing an exception.
     * HOW:     Calls replaceMove(-1, ...) and replaceMove(5, ...) on a 1-move moveset
     *          and asserts both return false.
     * IMPROVE: Test slot 4 when the moveset has exactly 4 moves (valid boundary), and
     *          slot 0 on an empty moveset (invalid), to cover additional edge cases.
     *          Also assert the moveset is unchanged after each failed replace.
     */
    @Test
    void replaceMoveFailsForInvalidSlot() {
        abra.addMove(new Psychic());
        assertFalse(abra.replaceMove(-1, new Teleport()));
        assertFalse(abra.replaceMove(5, new Teleport()));
    }

    // --- Stat attack/defense routing ---

    /*
     * CHECKS:  getAttackStatForMove() returns the current Attack stat when given a
     *          Physical move (MegaPunch), confirming the Physical damage category is
     *          correctly routed to the Attack stat.
     * HOW:     Asserts the move's category is "Physical" (precondition guard), then
     *          asserts getAttackStatForMove(physical) equals getCurrentAttack().
     * IMPROVE: The category assertion is a precondition for the routing logic under
     *          test. Consider moving it to MegaPunch's own unit test to keep this test
     *          focused solely on the stat-routing behavior.
     */
    @Test
    void physicalMoveUsesAttackStat() {
        Move physical = new MegaPunch();
        assertEquals(Category.PHYSICAL, physical.getMoveCategory());
        int attackStat = abra.getAttackStatForMove(physical);
        assertEquals(abra.getCurrentAttack(), attackStat);
    }

    /*
     * CHECKS:  getAttackStatForMove() returns the current Special Attack stat when given
     *          a Special move (Psychic), confirming the Special damage category is
     *          correctly routed.
     * HOW:     Asserts the move's category is "Special", then asserts the returned stat
     *          equals getCurrentSpecialAttack().
     * IMPROVE: Same as physicalMoveUsesAttackStat — decouple the category precondition
     *          from the routing assertion for cleaner test responsibility.
     */
    @Test
    void specialMoveUsesSpecialAttackStat() {
        Move special = new Psychic();
        assertEquals(Category.SPECIAL, special.getMoveCategory());
        int spAtkStat = abra.getAttackStatForMove(special);
        assertEquals(abra.getCurrentSpecialAttack(), spAtkStat);
    }

    /*
     * CHECKS:  getAttackStatForMove() returns 0 for a Status move (Teleport), since
     *          status moves deal no direct damage and have no associated attack stat.
     * HOW:     Asserts the move's category is "Status", then asserts the returned value
     *          is 0.
     * IMPROVE: Confirm 0 is intentional (not a missing stat mapping) by also asserting
     *          that the damage calculation skips status moves entirely rather than
     *          computing 0-damage hits.
     */
    @Test
    void statusMoveReturnsZeroAttack() {
        Move teleport = new Teleport();
        assertEquals(Category.STATUS, teleport.getMoveCategory());
        assertEquals(0, abra.getAttackStatForMove(teleport));
    }

    /*
     * CHECKS:  getDefenseStatForMove() returns the current Defense stat for a Physical
     *          move (MegaPunch).
     * HOW:     Asserts getDefenseStatForMove(physical) equals getCurrentDefense().
     * IMPROVE: Test at a non-default level or with EVs to confirm the fully-calculated
     *          current stat is returned, not just the base stat.
     */
    @Test
    void physicalMoveUsesDefenseStat() {
        Move physical = new MegaPunch();
        assertEquals(abra.getCurrentDefense(), abra.getDefenseStatForMove(physical));
    }

    /*
     * CHECKS:  getDefenseStatForMove() returns the current Special Defense stat for a
     *          Special move (Psychic).
     * HOW:     Asserts getDefenseStatForMove(special) equals getCurrentSpecialDefense().
     * IMPROVE: Same as physicalMoveUsesDefenseStat — test with a non-default level to
     *          confirm the calculated stat is used rather than a raw base value.
     */
    @Test
    void specialMoveUsesSpecialDefenseStat() {
        Move special = new Psychic();
        assertEquals(abra.getCurrentSpecialDefense(), abra.getDefenseStatForMove(special));
    }

    /*
     * CHECKS:  getDefenseStatForMove() returns 0 for a Status move (Teleport).
     * HOW:     Asserts getDefenseStatForMove(teleport) equals 0.
     * IMPROVE: Combine with statusMoveReturnsZeroAttack into a single test that
     *          verifies both routing methods return 0 for Status moves, reducing
     *          duplication and keeping the full status-move contract in one place.
     */
    @Test
    void statusMoveReturnsZeroDefense() {
        Move teleport = new Teleport();
        assertEquals(0, abra.getDefenseStatForMove(teleport));
    }

    // --- IVs ---

    /*
     * CHECKS:  All six IVs (HP, Attack, Defense, SpAtk, SpDef, Speed) are generated
     *          within the valid range [0, 31] upon Pokémon construction.
     * HOW:     Asserts each getIvX() return value satisfies 0 <= iv <= 31.
     * IMPROVE: Run this check across many instances (e.g., 50 different Pokémon) to
     *          increase confidence the RNG never produces out-of-range values. Also
     *          verify the sum of all IVs is <= 186 to catch overflow bugs.
     */
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

    /*
     * CHECKS:  A newly constructed Pokémon starts with 0 total EVs (no pre-assigned
     *          effort values).
     * HOW:     Calls getEvTotal() on a fresh Abra and asserts it equals 0.
     * IMPROVE: Also assert each individual EV getter (getEvHp, getEvAttack, etc.) is 0
     *          to confirm the total is not masking non-zero individual values that
     *          happen to cancel out.
     */
    @Test
    void newPokemonStartsWithZeroEvs() {
        assertEquals(0, abra.getEvTotal());
    }

    /*
     * CHECKS:  The EV setter is additive: calling setEvHp(100) followed by setEvHp(50)
     *          results in a total of 150 HP EVs.
     * HOW:     Calls setEvHp twice and asserts getEvTotal() equals 150.
     * IMPROVE: Also assert getEvHp() == 150 (not just the total) to confirm the
     *          per-stat value accumulates correctly. Clarify in a comment whether
     *          additive behavior is intentional (accumulate) vs a setter (replace).
     */
    @Test
    void evSetterOnlySetsCurrentValue() {
        EvManager evManager = new EvManager();
        evManager.setEv(abra, Stat.HP, 100);
        evManager.setEv(abra, Stat.HP, 50);
        // NOT Additive: should now be 50
        assertEquals(50, abra.getEvHp());
        assertEquals(50, abra.getEvTotal());
    }

    /*
     * CHECKS:  A single stat's EVs cannot exceed 252; attempts to add beyond that cap
     *          are silently ignored.
     * HOW:     Calls addEvHp(252) then addEvHp(10), and asserts getEvTotal() equals 252
     *          (not 262).
     * IMPROVE: Also assert getEvHp() == 252 to confirm the cap is applied per-stat.
     *          Verify that the rejected 10 EVs were not silently redirected to another stat.
     */
    @Test
    void evCapsAtPerStatMax252() {
        EvManager evManager = new EvManager();
        evManager.setEv(abra, Stat.HP, 252);
        evManager.addEv(abra, Stat.HP, 10); // Should not exceed 252
        assertEquals(252, abra.getEvHp());
        assertEquals(252, abra.getEvTotal());
    }

    /*
     * CHECKS:  The global EV total is capped at 510. When adding EVs that would push
     *          beyond the cap, only the remaining allowance is granted.
     * HOW:     Fills 504 EVs (252 Attack + 252 Speed), then adds 100 HP EVs. Asserts
     *          total is exactly 510 and HP EVs is exactly 6 (only 6 slots remained).
     * IMPROVE: Verify that a subsequent addEV call after reaching 510 does nothing
     *          (total stays 510). Also test with different stat combinations to confirm
     *          the 510 cap is not hardcoded for Attack+Speed specifically.
     */
    @Test
    void evTotalCannotExceed510() {
        EvManager evManager = new EvManager();
        evManager.setEv(abra, Stat.ATTACK, 252);
        evManager.setEv(abra, Stat.SPEED, 252);
        // evTotal is now 504; only 6 more EVs can fit under the 510 cap
        evManager.setEv(abra, Stat.HP, 100); // Requests 100 but only 6 should be granted
        assertEquals(510, abra.getEvTotal(),
                "EV total should be capped at exactly 510");
        assertEquals(6, abra.getEvHp(),
                "Only 6 HP EVs should have been added (510 - 504 = 6 remaining room)");
    }

    /*
     * CHECKS:  A single stat's EV value can reach exactly 252 and is retrievable.
     * HOW:     Calls setEvDefense(252) and asserts getEvDefense() returns 252.
     * IMPROVE: Test adding EVs across multiple calls to reach 252 (e.g., 200 + 52)
     *          and confirm each partial add is applied correctly without over-capping
     *          prematurely.
     */
    @Test
    void individualEvsCappedAt252() {
        EvManager evManager = new EvManager();
        evManager.setEv(abra, Stat.DEFENSE, 252);
        assertEquals(252, abra.getEvDefense());
    }

    // --- Nature ---

    /*
     * CHECKS:  A newly constructed Pokémon always has a non-null nature assigned.
     * HOW:     Calls getNature() on a fresh Abra and asserts it is not null.
     * IMPROVE: Verify the assigned nature is a valid Natures enum constant (e.g.,
     *          Natures.values() contains it) to rule out a non-null sentinel object
     *          being returned.
     */
    @Test
    void pokemonHasNatureAssigned() {
        assertNotNull(abra.getNature(),
                "A newly created Pokémon should have a nature assigned");
    }

    /*
     * CHECKS:  setNature() changes the Pokémon's nature to the specified value.
     * HOW:     Calls setNature(Natures.ADAMANT) and asserts getNature() returns ADAMANT.
     * IMPROVE: Also verify whether setNature triggers a stat recalculation. If not,
     *          document that callers must call calculateCurrentStats() manually so the
     *          new nature's modifiers are reflected in current stats.
     */
    @Test
    void setNatureUpdatesNature() {
        abra.setNature(Natures.ADAMANT);
        assertEquals(Natures.ADAMANT, abra.getNature());
    }

    /*
     * CHECKS:  applyNature() assigns a non-null nature to the Pokémon.
     * HOW:     Calls applyNature() and asserts getNature() is not null.
     * IMPROVE: Call applyNature() multiple times to confirm the nature can change
     *          between calls. Also verify stat recalculation occurs each time a new
     *          nature is applied.
     */
    @Test
    void applyNatureSetsANature() {
        abra.applyNature();
        assertNotNull(abra.getNature());
    }

    // --- isFainted ---

    /*
     * CHECKS:  A newly constructed Pokémon has isFainted == false (alive by default).
     * HOW:     Calls getIsFainted() on a fresh Abra and asserts false.
     * IMPROVE: Verify this default across multiple species to ensure no Pokémon is
     *          created in a pre-fainted state.
     */
    @Test
    void newPokemonIsNotFainted() {
        assertFalse(abra.getIsFainted());
    }

    /*
     * CHECKS:  setIsFainted(true) sets the isFainted flag to true.
     * HOW:     Calls setIsFainted(true) and asserts getIsFainted() returns true.
     * IMPROVE: See setIsFaintedBackToFalse — these two tests together document the
     *          full toggle lifecycle. Consider merging them or controlling execution
     *          order to make the lifecycle explicit.
     */
    @Test
    void setIsFaintedUpdatesFlag() {
        abra.setIsFainted(true);
        assertTrue(abra.getIsFainted());
    }

    /*
     * CHECKS:  setIsFainted(false) clears the fainted flag after it was previously set
     *          to true.
     * HOW:     Calls setIsFainted(true) then setIsFainted(false) and asserts the final
     *          value is false.
     * IMPROVE: Document whether clearing the fainted flag is a supported "revival"
     *          operation. If it is, add a test that restores HP alongside the flag
     *          reset and verifies the Pokémon behaves correctly in battle.
     */
    @Test
    void setIsFaintedBackToFalse() {
        abra.setIsFainted(true);
        abra.setIsFainted(false);
        assertFalse(abra.getIsFainted());
    }

    // --- HP management ---

    

    /*
     * CHECKS:  setCurrentHP() updates currentHP to the specified value.
     * HOW:     Calls setCurrentHP(10) and asserts getCurrentHP() returns 10.
     * IMPROVE: Test boundary values: setCurrentHP(0), setCurrentHP(maxHP), and
     *          setCurrentHP(maxHP + 1) to confirm over-heal is handled (clamped or
     *          rejected). See setCurrentHP_shouldClampAtZero for the negative-value case.
     */
    @Test
    void setCurrentHPUpdatesHP() {
        abra.setCurrentHP(10);
        assertEquals(10, abra.getCurrentHP());
    }

    /*
     * CHECKS:  setCurrentHP() should clamp negative values to 0, preventing HP from
     *          going below 0. Currently @Disabled because this is not yet implemented.
     * HOW:     Calls setCurrentHP(-5) and asserts getCurrentHP() reads back as 0.
     * IMPROVE: Implement HP clamping in setCurrentHP() and remove @Disabled. This also
     *          unblocks dealDamage_hpShouldBeClampedAtZero in BattleTest, which
     *          depends on the same fix.
     */
    // IDEAL BEHAVIOR: setCurrentHP should clamp at 0 so HP is never negative.
    // CURRENT BEHAVIOR: setCurrentHP stores whatever int it receives, including
    // negative values. This is the root cause of the HP-below-zero issues
    // documented in BattleTest.dealDamage_hpShouldBeClampedAtZero.
    @Test
    void setCurrentHP_shouldClampAtZero() {
        abra.setCurrentHP(-5);
        assertEquals(0, abra.getCurrentHP(),
                "HP should be clamped at 0 when set to a negative value");
    }

    // --- Stat calculation formulas ---

    /*
     * CHECKS:  calcMaxHP() correctly implements the Generation III HP formula:
     *          floor(((2 * base + IV + EV/4) * level / 100) + level + 10).
     * HOW:     Calls calcMaxHP(base=45, level=50, IV=15, EV=100) and compares against
     *          the manually computed expected value.
     * IMPROVE: Test with EV=0, IV=0 (minimum stats) and EV=252, IV=31 (maximum stats)
     *          to confirm the formula handles both extremes. Also test at level 100 to
     *          verify the additive +level+10 term is applied correctly.
     */
    @Test
    void calcMaxHPUsesCorrectFormula() {
        // Formula: ((2*base + IV + EV/4) * level / 100) + level + 10
        int result = StatCalculator.calcMaxHP(45, 50, 15, 100);
        int expected = ((2 * 45 + 15 + 100 / 4) * 50 / 100) + 50 + 10;
        assertEquals(expected, result);
    }

    /*
     * CHECKS:  calcCurrentStat() correctly implements the Generation III battle stat
     *          formula: floor(((2*base + IV + EV/4) * level / 100) + 5) * nature.
     * HOW:     Calls calcCurrentStat(base=80, level=50, IV=20, EV=0, nature=1.0) and
     *          compares against the manually computed expected value.
     * IMPROVE: Test with EV=252 to confirm the EV/4 integer-division step is applied
     *          correctly. Also test that the floor() rounding is consistent at boundary
     *          values near .5 fractions.
     */
    @Test
    void calcCurrentStatUsesCorrectFormula() {
        // Formula: (((2*base + IV + EV/4) * level / 100) + 5) * nature
        int result = StatCalculator.calcCurrentStat(80, 50, 20, 0, 1.0);
        int expected = (int) Math.floor((((2 * 80 + 20 + 0) * 50 / 100) + 5) * 1.0);
        assertEquals(expected, result);
    }

    /*
     * CHECKS:  A 1.1x nature modifier produces a strictly higher stat than a neutral
     *          1.0 modifier on the same base inputs.
     * HOW:     Calls calcCurrentStat with modifiers 1.0 and 1.1 and asserts the boosted
     *          value is strictly greater.
     * IMPROVE: Assert the difference is within the expected ~10% range (accounting for
     *          floor truncation), rather than just >, to catch bugs where a non-zero
     *          but incorrect multiplier still passes the current assertion.
     */
    @Test
    void calcCurrentStatWithNatureBoost() {
        int neutral = StatCalculator.calcCurrentStat(80, 50, 20, 0, 1.0);
        int boosted = StatCalculator.calcCurrentStat(80, 50, 20, 0, 1.1);
        assertTrue(boosted > neutral,
                "A boosted nature should give a higher stat");
    }

    /*
     * CHECKS:  A 0.9x nature modifier produces a strictly lower stat than a neutral
     *          1.0 modifier on the same base inputs.
     * HOW:     Calls calcCurrentStat with modifiers 1.0 and 0.9 and asserts the
     *          penalized value is strictly less.
     * IMPROVE: Same as calcCurrentStatWithNatureBoost — tighten the assertion to
     *          verify the ~10% penalty magnitude, not just direction.
     */
    @Test
    void calcCurrentStatWithNaturePenalty() {
        int neutral = StatCalculator.calcCurrentStat(80, 50, 20, 0, 1.0);
        int lowered = StatCalculator.calcCurrentStat(80, 50, 20, 0, 0.9);
        assertTrue(lowered < neutral,
                "A lowered nature should give a lower stat");
    }

    // --- Species info ---

    /*
     * CHECKS:  The species name for an Abra instance is "Abra".
     * HOW:     Calls getSpecies() and asserts the result equals "Abra".
     * IMPROVE: Verify getSpecies() is unaffected by setName(), confirming species name
     *          and display nickname are stored as independent fields.
     */
    @Test
    void speciesIsAbra() {
        assertEquals(PokeSpecies.ABRA, abra.getSpecies());
    }

    /*
     * CHECKS:  Abra's primary type is "Psychic", matching its in-game typing.
     * HOW:     Calls getTypePrimary() and asserts the value equals "Psychic".
     * IMPROVE: Use a type constant or enum instead of the magic string "Psychic" so
     *          the test is resilient to future string renaming. Also test a second
     *          species to confirm types vary by species.
     */
    @Test
    void primaryTypeIsPsychic() {
        assertEquals(Type.PSYCHIC, abra.getTypePrimary());
    }

    /*
     * CHECKS:  Abra's secondary type is null, confirming it is a single-type Pokémon.
     * HOW:     Calls getTypeSecondary() and asserts null.
     * IMPROVE: Pair with dualTypePokemonHasSecondaryType so the single-type and
     *          dual-type contracts are documented as a matched set.
     */
    @Test
    void secondaryTypeIsNONE() {
        assertEquals(Type.NONE, abra.getTypeSecondary(),
                "Abra is a single-type Pokémon, secondary should be NONE");
    }

    /*
     * CHECKS:  A dual-type Pokémon (Bulbasaur: Grass/Poison) has both a primary and a
     *          non-null secondary type.
     * HOW:     Creates a Bulbasaur and asserts getTypePrimary() equals "Grass" and
     *          getTypeSecondary() equals "Poison".
     * IMPROVE: Test a second dual-type Pokémon from a different type combination to
     *          confirm dual-type storage is not hard-coded only for Grass/Poison.
     */
    @Test
    void dualTypePokemonHasSecondaryType() {
        Pokemon bulba = new Bulbasaur("Bulba");
        assertEquals(Type.GRASS, bulba.getTypePrimary());
        assertEquals(Type.POISON, bulba.getTypeSecondary());
    }

    // --- Learnset ---

    /*
     * CHECKS:  An Abra instance has at least one entry in its learnset (it can learn
     *          moves).
     * HOW:     Calls getLearnset() and asserts the list is not empty.
     * IMPROVE: Assert the learnset contains at minimum one LEVEL move, one TM move,
     *          and that a known signature move (e.g., Teleport) is present, giving a
     *          more specific regression guard.
     */
    @Test
    void learnsetIsNotEmpty() {
        assertFalse(abra.getLearnset().isEmpty());
    }

    // --- EV yield ---

    /*
     * CHECKS:  Abra has a non-null EV yield map, confirming the species has a defined
     *          effort-value reward.
     * HOW:     Calls getEvYield() and asserts the result is not null.
     * IMPROVE: Assert that the EV yield contains the expected stat entry (Abra yields
     *          Special Attack EVs in Generation III). A value check would make this a
     *          meaningful data-correctness test rather than just a null check.
     */
    @Test
    void evYieldIsNotNull() {
        assertNotNull(abra.getEvYield(),
                "EV yield should be set for species");
    }

}
