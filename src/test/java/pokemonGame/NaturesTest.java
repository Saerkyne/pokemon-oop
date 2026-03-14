package pokemonGame;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import pokemonGame.mons.Abra;

class NaturesTest {

    // --- Modifier values ---

    /*
     * CHECKS:  The ADAMANT nature's modifier for ATTACK is 1.1 (a 10% boost), matching
     *          the Generation III–IX nature mechanic.
     * HOW:     Calls Natures.ADAMANT.modifierFor(Stat.ATTACK) and asserts the result
     *          equals 1.1 within a tolerance of 0.001.
     * IMPROVE: Parameterize all "boosted stat" tests into a single @ParameterizedTest
     *          mapping each offensive nature to its expected boosted stat and value,
     *          reducing boilerplate across the nature modifier tests.
     */
    @Test
    void adamantBoostsAttack() {
        assertEquals(1.1, Natures.ADAMANT.modifierFor(Stat.ATTACK), 0.001);
    }

    /*
     * CHECKS:  ADAMANT lowers SPECIAL_ATTACK by 10% (modifier = 0.9), the penalty
     *          that offsets ADAMANT's Attack boost.
     * HOW:     Calls modifierFor(Stat.SPECIAL_ATTACK) on ADAMANT and asserts 0.9 ±
     *          0.001.
     * IMPROVE: Combine with adamantBoostsAttack in a single test that verifies both
     *          the boosted stat (1.1) and the lowered stat (0.9) to fully specify the
     *          ADAMANT nature contract in one place.
     */
    @Test
    void adamantLowersSpecialAttack() {
        assertEquals(0.9, Natures.ADAMANT.modifierFor(Stat.SPECIAL_ATTACK), 0.001);
    }

    /*
     * CHECKS:  ADAMANT is neutral (1.0) for a stat it neither boosts nor lowers
     *          (DEFENSE is unaffected by ADAMANT).
     * HOW:     Calls modifierFor(Stat.DEFENSE) on ADAMANT and asserts 1.0 ± 0.001.
     * IMPROVE: Iterate over all neutral stats for ADAMANT (DEFENSE, SPECIAL_DEFENSE,
     *          SPEED) in a loop rather than spot-checking a single stat, giving
     *          complete neutral-stat coverage in one test.
     */
    @Test
    void adamantNeutralForDefense() {
        assertEquals(1.0, Natures.ADAMANT.modifierFor(Stat.DEFENSE), 0.001);
    }

    /*
     * CHECKS:  MODEST boosts SPECIAL_ATTACK by 10% (modifier = 1.1).
     * HOW:     Calls modifierFor(Stat.SPECIAL_ATTACK) on MODEST and asserts 1.1 ±
     *          0.001.
     * IMPROVE: Combine with modestLowersAttack into a single test that verifies both
     *          sides of the MODEST contract (SpAtk boosted, Attack lowered) at once,
     *          mirroring how natures are specified in-game.
     */
    @Test
    void modestBoostsSpecialAttack() {
        assertEquals(1.1, Natures.MODEST.modifierFor(Stat.SPECIAL_ATTACK), 0.001);
    }

    /*
     * CHECKS:  MODEST lowers ATTACK by 10% (modifier = 0.9), offsetting its SpAtk boost.
     * HOW:     Calls modifierFor(Stat.ATTACK) on MODEST and asserts 0.9 ± 0.001.
     * IMPROVE: See modestBoostsSpecialAttack — fold both assertions into one test.
     */
    @Test
    void modestLowersAttack() {
        assertEquals(0.9, Natures.MODEST.modifierFor(Stat.ATTACK), 0.001);
    }

    /*
     * CHECKS:  TIMID boosts SPEED by 10% (modifier = 1.1).
     * HOW:     Calls modifierFor(Stat.SPEED) on TIMID and asserts 1.1 ± 0.001.
     * IMPROVE: Add a corresponding assertion for TIMID's lowered stat (ATTACK = 0.9)
     *          in the same test to fully specify the nature's effect.
     */
    @Test
    void timidBoostsSpeed() {
        assertEquals(1.1, Natures.TIMID.modifierFor(Stat.SPEED), 0.001);
    }

    /*
     * CHECKS:  TIMID lowers ATTACK by 10% (modifier = 0.9).
     * HOW:     Calls modifierFor(Stat.ATTACK) on TIMID and asserts 0.9 ± 0.001.
     * IMPROVE: Combine with timidBoostsSpeed into a single test for the complete TIMID
     *          contract (Speed boosted, Attack lowered).
     */
    @Test
    void timidLowersAttack() {
        assertEquals(0.9, Natures.TIMID.modifierFor(Stat.ATTACK), 0.001);
    }

    /*
     * CHECKS:  BOLD boosts DEFENSE by 10% (modifier = 1.1).
     * HOW:     Calls modifierFor(Stat.DEFENSE) on BOLD and asserts 1.1 ± 0.001.
     * IMPROVE: Combine with boldLowersAttack into a single test that verifies both
     *          sides of the BOLD nature contract simultaneously.
     */
    @Test
    void boldBoostsDefense() {
        assertEquals(1.1, Natures.BOLD.modifierFor(Stat.DEFENSE), 0.001);
    }

    /*
     * CHECKS:  BOLD lowers ATTACK by 10% (modifier = 0.9), offsetting its Defense boost.
     * HOW:     Calls modifierFor(Stat.ATTACK) on BOLD and asserts 0.9 ± 0.001.
     * IMPROVE: See boldBoostsDefense — merge both assertions into one test for a
     *          complete BOLD contract check.
     */
    @Test
    void boldLowersAttack() {
        assertEquals(0.9, Natures.BOLD.modifierFor(Stat.ATTACK), 0.001);
    }

    /*
     * CHECKS:  CALM boosts SPECIAL_DEFENSE by 10% (modifier = 1.1).
     * HOW:     Calls modifierFor(Stat.SPECIAL_DEFENSE) on CALM and asserts 1.1 ± 0.001.
     * IMPROVE: Also verify CALM's lowered stat (ATTACK = 0.9) in this test, and assert
     *          that all remaining stats return 1.0, giving a complete CALM specification.
     */
    @Test
    void calmBoostsSpecialDefense() {
        assertEquals(1.1, Natures.CALM.modifierFor(Stat.SPECIAL_DEFENSE), 0.001);
    }

    /*
     * CHECKS:  JOLLY boosts SPEED (1.1) AND lowers SPECIAL_ATTACK (0.9) in a single
     *          test, verifying both sides of the nature contract at once.
     * HOW:     Calls modifierFor(Stat.SPEED) and modifierFor(Stat.SPECIAL_ATTACK) on
     *          JOLLY and asserts both expected float values.
     * IMPROVE: This is the best-structured of the single-nature tests. The other nature
     *          tests should adopt the same pattern of asserting both the boosted and
     *          lowered stat in a single test for complete per-nature coverage.
     */
    @Test
    void jollyBoostsSpeedLowersSpecialAttack() {
        assertEquals(1.1, Natures.JOLLY.modifierFor(Stat.SPEED), 0.001);
        assertEquals(0.9, Natures.JOLLY.modifierFor(Stat.SPECIAL_ATTACK), 0.001);
    }

    // --- Neutral natures ---

    /*
     * CHECKS:  BASHFUL (a neutral nature) returns 1.0 for every stat, confirming it
     *          applies no bonus or penalty.
     * HOW:     Iterates over all Stat.values() and asserts modifierFor(stat) == 1.0 ±
     *          0.001 for each.
     * IMPROVE: The loop approach is ideal since it automatically covers any new stats
     *          added to the enum. The five neutral-nature tests (BASHFUL, HARDY,
     *          DOCILE, QUIRKY, SERIOUS) are structurally identical and could be reduced
     *          to a single @ParameterizedTest for maintainability.
     */
    @Test
    void bashfulIsNeutralForAllStats() {
        for (Stat stat : Stat.values()) {
            assertEquals(1.0, Natures.BASHFUL.modifierFor(stat), 0.001,
                    "Bashful should be neutral for " + stat);
        }
    }

    /*
     * CHECKS:  HARDY (a neutral nature) returns 1.0 for every stat.
     * HOW:     Same loop approach as bashfulIsNeutralForAllStats.
     * IMPROVE: See bashfulIsNeutralForAllStats — consolidate all five neutral-nature
     *          checks into one @ParameterizedTest(BASHFUL, HARDY, DOCILE, QUIRKY,
     *          SERIOUS) to eliminate duplication.
     */
    @Test
    void hardyIsNeutralForAllStats() {
        for (Stat stat : Stat.values()) {
            assertEquals(1.0, Natures.HARDY.modifierFor(stat), 0.001,
                    "Hardy should be neutral for " + stat);
        }
    }

    /*
     * CHECKS:  DOCILE (a neutral nature) returns 1.0 for every stat.
     * HOW:     Same loop approach as the other neutral-nature tests.
     * IMPROVE: See hardyIsNeutralForAllStats — consolidate into a parameterized test.
     */
    @Test
    void docileIsNeutralForAllStats() {
        for (Stat stat : Stat.values()) {
            assertEquals(1.0, Natures.DOCILE.modifierFor(stat), 0.001,
                    "Docile should be neutral for " + stat);
        }
    }

    /*
     * CHECKS:  QUIRKY (a neutral nature) returns 1.0 for every stat.
     * HOW:     Same loop approach as the other neutral-nature tests.
     * IMPROVE: See hardyIsNeutralForAllStats — consolidate into a parameterized test.
     */
    @Test
    void quirkyIsNeutralForAllStats() {
        for (Stat stat : Stat.values()) {
            assertEquals(1.0, Natures.QUIRKY.modifierFor(stat), 0.001,
                    "Quirky should be neutral for " + stat);
        }
    }

    /*
     * CHECKS:  SERIOUS (a neutral nature) returns 1.0 for every stat.
     * HOW:     Same loop approach as the other neutral-nature tests.
     * IMPROVE: See hardyIsNeutralForAllStats — consolidate into a parameterized test.
     */
    @Test
    void seriousIsNeutralForAllStats() {
        for (Stat stat : Stat.values()) {
            assertEquals(1.0, Natures.SERIOUS.modifierFor(stat), 0.001,
                    "Serious should be neutral for " + stat);
        }
    }

    // --- Display name ---

    /*
     * CHECKS:  getDisplayName() returns the human-readable name of a nature (e.g.,
     *          "Adamant" not "ADAMANT"), spot-checking four natures.
     * HOW:     Calls getDisplayName() on ADAMANT, MODEST, TIMID, and HARDY and uses
     *          assertEquals to compare each against its expected string.
     * IMPROVE: Check all 25 natures rather than just 4. A data-driven approach pairing
     *          each enum constant with its expected display name would give full coverage
     *          and immediately reveal any nature whose name string was misspelled.
     */
    @Test
    void displayNameMatchesExpected() {
        assertEquals("Adamant", Natures.ADAMANT.getDisplayName());
        assertEquals("Modest", Natures.MODEST.getDisplayName());
        assertEquals("Timid", Natures.TIMID.getDisplayName());
        assertEquals("Hardy", Natures.HARDY.getDisplayName());
    }

    // --- All 25 natures exist ---

    /*
     * CHECKS:  Exactly 25 nature constants are defined in the Natures enum, matching
     *          the full set from Generation III–IX Pokémon games.
     * HOW:     Calls Natures.values().length and asserts it equals 25.
     * IMPROVE: The test name has a typo ("thereare" instead of "thereAre"). Also
     *          verify that all 25 natures are reachable via fromString() to confirm
     *          complete round-trip consistency between enum constants and name lookup.
     */
    @Test
    void thereare25Natures() {
        assertEquals(25, Natures.values().length,
                "There should be exactly 25 natures");
    }

    // --- fromString lookup ---

    /*
     * CHECKS:  fromString("Adamant") returns the ADAMANT enum constant.
     * HOW:     Calls Natures.fromString("Adamant") and asserts the result equals ADAMANT.
     * IMPROVE: This only spot-checks one nature. Parameterize with all 25 nature names
     *          to guarantee fromString works for every entry in the enum and no lookup
     *          is accidentally missing.
     */
    @Test
    void fromStringFindsAdamant() {
        assertEquals(Natures.ADAMANT, Natures.fromString("Adamant"));
    }

    /*
     * CHECKS:  fromString() is case-insensitive: both "bold" and "BOLD" resolve to
     *          Natures.BOLD.
     * HOW:     Calls fromString("bold") and fromString("BOLD") and asserts both return
     *          Natures.BOLD.
     * IMPROVE: Also test mixed-case input (e.g., "BoLd") to confirm the normalization
     *          handles arbitrary casing, not just purely lowercase or uppercase.
     */
    @Test
    void fromStringIsCaseInsensitive() {
        assertEquals(Natures.BOLD, Natures.fromString("bold"));
        assertEquals(Natures.BOLD, Natures.fromString("BOLD"));
    }

    /*
     * CHECKS:  fromString() accepts the raw enum name (all-uppercase, e.g. "ADAMANT")
     *          and returns the corresponding constant.
     * HOW:     Calls fromString("ADAMANT") and asserts it equals Natures.ADAMANT.
     * IMPROVE: This overlaps partially with fromStringIsCaseInsensitive. Consider
     *          clarifying the intent by testing the canonical display name ("Adamant")
     *          in fromStringFindsAdamant and relying on fromStringIsCaseInsensitive for
     *          the case-folding contract, rather than duplicating the uppercase check.
     */
    @Test
    void fromStringMatchesEnumName() {
        assertEquals(Natures.ADAMANT, Natures.fromString("ADAMANT"));
    }

    /*
     * CHECKS:  fromString() throws IllegalArgumentException for an unrecognized name,
     *          rather than returning null or a default nature.
     * HOW:     Uses assertThrows to call fromString("NotANature") and expects an
     *          IllegalArgumentException.
     * IMPROVE: Also test null and empty string ("") to cover additional invalid inputs
     *          that could trigger NullPointerException or a different failure mode.
     */
    @Test
    void fromStringThrowsForUnknownNature() {
        assertThrows(IllegalArgumentException.class, () -> Natures.fromString("NotANature"));
    }

    // --- Random nature ---

    /*
     * CHECKS:  Natures.random() returns a non-null Nature constant.
     * HOW:     Calls random() once and asserts the result is not null.
     * IMPROVE: A single call has a 1/25 chance of returning any particular nature.
     *          This only verifies that random() doesn't throw or return null; pair
     *          with randomReturnsVariety for a more meaningful smoke test.
     */
    @Test
    void randomReturnsAValidNature() {
        Natures nature = Natures.random();
        assertNotNull(nature);
    }

    /*
     * CHECKS:  Natures.random() produces more than one distinct result over 100 calls,
     *          confirming it is not always returning the same constant.
     * HOW:     Collects 100 random() results in a Set and asserts the set size > 1.
     * IMPROVE: With 25 possible natures and 100 trials, asserting only "more than 1"
     *          is a weak check. Asserting at least 10–15 distinct natures would more
     *          strongly verify the distribution is reasonably uniform.
     */
    @Test
    void randomReturnsVariety() {
        // Over many calls, we should see more than one nature
        java.util.Set<Natures> seen = new java.util.HashSet<>();
        for (int i = 0; i < 100; i++) {
            seen.add(Natures.random());
        }
        assertTrue(seen.size() > 1,
                "random() should produce more than one nature over 100 calls");
    }

    // --- assignTo / assignRandom ---

    /*
     * CHECKS:  Calling ADAMANT.assignTo(pokemon) sets that Pokémon's nature to ADAMANT.
     * HOW:     Calls ADAMANT.assignTo(abra) and asserts abra.getNature() == ADAMANT.
     * IMPROVE: Also verify whether assignTo triggers a stat recalculation. If it does
     *          not, document that callers must call calculateCurrentStats() manually
     *          after assigning a new nature to reflect the changed modifiers.
     */
    @Test
    void assignToSetsNatureOnPokemon() {
        Pokemon abra = new Abra("Test Abra");
        Natures.ADAMANT.assignTo(abra);
        assertEquals(Natures.ADAMANT, abra.getNature());
    }

    /*
     * CHECKS:  Natures.assignRandom(pokemon) assigns a non-null nature to the Pokémon.
     * HOW:     Calls assignRandom(abra) and asserts abra.getNature() is not null.
     * IMPROVE: Call assignRandom multiple times to confirm the nature varies across
     *          calls (i.e., it is truly random, not always the same value). Also verify
     *          that stats are recalculated after the random nature is applied.
     */
    @Test
    void assignRandomSetsNonNullNature() {
        Pokemon abra = new Abra("Test Abra");
        Natures.assignRandom(abra);
        assertNotNull(abra.getNature());
    }
}
