package pokemonGame;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import pokemonGame.mons.Abra;
import pokemonGame.moves.Psychic;
import java.util.List;

class LearnsetEntryTest {

    // --- Constructor / getters ---

    /*
     * CHECKS:  The LearnsetEntry constructor correctly stores the Move reference, and
     *          getMove() returns the same object.
     * HOW:     Creates a Psychic move, wraps it in a LearnsetEntry, then uses assertSame
     *          to verify the identical object reference is returned by getMove().
     * IMPROVE: Also test with a different Move subclass (e.g., MegaPunch) to confirm
     *          the constructor is not accidentally type-specific or hardcoded.
     */
    @Test
    void constructorStoresMove() {
        Move psychic = new Psychic();
        LearnsetEntry entry = new LearnsetEntry(psychic, LearnsetEntry.Source.TM, 29);
        assertSame(psychic, entry.getMove());
    }

    /*
     * CHECKS:  The constructor stores the Source enum value, and getSource() returns it.
     * HOW:     Creates an entry with Source.TM and asserts getSource() equals TM.
     * IMPROVE: Parameterize over all five Source enum values (TM, HM, LEVEL, EGG,
     *          TUTOR) to confirm each can be stored and retrieved without error.
     */
    @Test
    void constructorStoresSource() {
        LearnsetEntry entry = new LearnsetEntry(new Psychic(), LearnsetEntry.Source.TM, 29);
        assertEquals(LearnsetEntry.Source.TM, entry.getSource());
    }

    /*
     * CHECKS:  The constructor stores the int parameter (e.g. the level at which a
     *          move is learned, or a TM number), and getParameter() returns it.
     * HOW:     Creates an entry with parameter 16 (LEVEL source) and asserts
     *          getParameter() returns 16.
     * IMPROVE: Test boundary values (0, negative, Integer.MAX_VALUE) to confirm the
     *          parameter is stored as-is without silent clamping or validation.
     */
    @Test
    void constructorStoresParameter() {
        LearnsetEntry entry = new LearnsetEntry(new Psychic(), LearnsetEntry.Source.LEVEL, 16);
        assertEquals(16, entry.getParameter());
    }

    // --- getEligibleMoves ---

    /*
     * CHECKS:  getEligibleMoves() excludes moves the Pokémon already knows, preventing
     *          the same move from being offered for re-learning.
     * HOW:     Gets eligible moves, learns the first one via addMove(), fetches the
     *          eligible list again, and asserts that move no longer appears in it.
     * IMPROVE: Verify that the comparison uses move-name equality (not object identity),
     *          so two different instances of the same move class are both excluded.
     *          Also check that learning all eligible moves produces an empty list.
     */
    @Test
    void eligibleMovesExcludesAlreadyKnownMoves() {
        Pokemon abra = new Abra("Test Abra");
        List<LearnsetEntry> before = LearnsetEntry.getEligibleMoves(abra);

        // Learn the first eligible move
        Move firstMove = before.get(0).getMove();
        abra.addMove(firstMove);

        List<LearnsetEntry> after = LearnsetEntry.getEligibleMoves(abra);
        boolean stillContains = after.stream()
                .anyMatch(e -> e.getMove().getMoveName().equals(firstMove.getMoveName()));
        assertFalse(stillContains,
                "A move the Pokémon already knows should not appear in eligible moves");
    }

    /*
     * CHECKS:  TM moves appear in the eligible list even for a low-level Pokémon (level
     *          5), confirming TMs are not gated behind a minimum level requirement.
     * HOW:     Gets eligible moves for a fresh Abra (level 5) and asserts at least one
     *          entry has Source.TM.
     * IMPROVE: Verify that ALL TM entries for Abra's learnset appear, not just one.
     *          Also test at level 1 (if that is a valid minimum) to confirm the
     *          no-level-gate rule holds for the absolute minimum level.
     */
    @Test
    void tmMovesAlwaysEligibleRegardlessOfLevel() {
        Pokemon abra = new Abra("Test Abra"); // Level 5
        List<LearnsetEntry> eligible = LearnsetEntry.getEligibleMoves(abra);
        boolean hasTmMove = eligible.stream()
                .anyMatch(e -> e.getSource() == LearnsetEntry.Source.TM);
        assertTrue(hasTmMove,
                "TM moves should always be eligible regardless of level");
    }

    /*
     * CHECKS:  HM moves appear in the eligible list for a low-level Pokémon, confirming
     *          HMs are not gated behind a minimum level requirement.
     * HOW:     Gets eligible moves for a level-5 Abra and asserts at least one entry
     *          has Source.HM.
     * IMPROVE: Same as tmMovesAlwaysEligibleRegardlessOfLevel — verify all HM entries
     *          are present. Both TM and HM checks could be merged into a single
     *          @ParameterizedTest(Source.TM, Source.HM) to reduce duplication.
     */
    @Test
    void hmMovesAlwaysEligibleRegardlessOfLevel() {
        Pokemon abra = new Abra("Test Abra"); // Level 5
        List<LearnsetEntry> eligible = LearnsetEntry.getEligibleMoves(abra);
        boolean hasHmMove = eligible.stream()
                .anyMatch(e -> e.getSource() == LearnsetEntry.Source.HM);
        assertTrue(hasHmMove,
                "HM moves should always be eligible regardless of level");
    }

    /*
     * CHECKS:  Level-up moves with a required level above the Pokémon's current level
     *          do NOT appear in the eligible list.
     * HOW:     Iterates all eligible moves for a level-5 Abra and, for each LEVEL-source
     *          entry, asserts the required level does not exceed 5.
     * IMPROVE: After this check, raise the level and verify that previously filtered
     *          moves are now eligible, confirming the filter is dynamic rather than a
     *          one-time snapshot.
     */
    @Test
    void highLevelMovesFilteredOutAtLowLevel() {
        Pokemon abra = new Abra("Test Abra"); // Level 5
        List<LearnsetEntry> eligible = LearnsetEntry.getEligibleMoves(abra);
        for (LearnsetEntry e : eligible) {
            if (e.getSource() == LearnsetEntry.Source.LEVEL) {
                assertTrue(e.getParameter() <= abra.getLevel(),
                        "Level-up move " + e.getMove().getMoveName()
                                + " (level " + e.getParameter()
                                + ") should not be eligible at level " + abra.getLevel());
            }
        }
    }

    /*
     * CHECKS:  Raising a Pokémon's level causes more level-up moves to become eligible,
     *          confirming the eligibility filter is level-sensitive.
     * HOW:     Counts LEVEL-source eligible moves at level 5, sets level to 100, counts
     *          again, and asserts the level-100 count is >= the level-5 count.
     * IMPROVE: Use a specific intermediate level where a known Abra move is unlocked
     *          (e.g., the exact level Abra learns Kinesis) and assert the count
     *          increases by exactly 1 at that boundary for a precise regression test.
     */
    @Test
    void levelUpMovesUnlockWhenLevelIncreases() {
        Pokemon abra = new Abra("Test Abra"); // Level 5
        int eligibleAtLevel5 = (int) LearnsetEntry.getEligibleMoves(abra).stream()
                .filter(e -> e.getSource() == LearnsetEntry.Source.LEVEL)
                .count();

        abra.setLevel(100); // Max level — all level-up moves should be available
        int eligibleAtLevel100 = (int) LearnsetEntry.getEligibleMoves(abra).stream()
                .filter(e -> e.getSource() == LearnsetEntry.Source.LEVEL)
                .count();

        assertTrue(eligibleAtLevel100 >= eligibleAtLevel5,
                "More level-up moves should be eligible at higher levels");
    }

    /*
     * CHECKS:  getEligibleMoves(null) returns an empty list instead of throwing a
     *          NullPointerException.
     * HOW:     Calls getEligibleMoves(null) and asserts the result is not null and is
     *          empty.
     * IMPROVE: Verify the returned collection is a freshly created mutable list (not a
     *          shared singleton like Collections.emptyList()), since callers may
     *          attempt to add to the result or compare identity.
     */
    @Test
    void nullPokemonReturnsEmptyList() {
        List<LearnsetEntry> eligible = LearnsetEntry.getEligibleMoves(null);
        assertNotNull(eligible);
        assertTrue(eligible.isEmpty());
    }

    /*
     * CHECKS:  The eligible-move count decreases after a move is learned, confirming
     *          the "already known" filter is correctly applied.
     * HOW:     Records the count before learning a move, learns the first eligible move,
     *          fetches the count again, and asserts it is strictly smaller.
     * IMPROVE: If a move appears under multiple sources (e.g., both LEVEL and TM),
     *          learning it should remove ALL source entries for that move. Assert the
     *          count drops by the exact number of duplicate source entries for more
     *          precise verification.
     */
    @Test
    void eligibleMovesCountDecreaseAfterLearning() {
        Pokemon abra = new Abra("Test Abra");
        List<LearnsetEntry> before = LearnsetEntry.getEligibleMoves(abra);
        int countBefore = before.size();

        // Learn a move — may appear under multiple sources (e.g. LEVEL + TM),
        // so all entries for that move name get filtered out
        abra.addMove(before.get(0).getMove());

        List<LearnsetEntry> after = LearnsetEntry.getEligibleMoves(abra);
        assertTrue(after.size() < countBefore,
                "Eligible move count should decrease after learning a move");
    }

    /*
     * CHECKS:  All five expected Source enum values (LEVEL, TM, HM, EGG, TUTOR) are
     *          defined and accessible in the LearnsetEntry.Source enum.
     * HOW:     Calls assertNotNull on each expected constant.
     * IMPROVE: Also assert LearnsetEntry.Source.values().length == 5 to catch any
     *          undocumented additions. Additionally, verify each constant has a unique
     *          name so accidental duplicates are detected.
     */
    @Test
    void allSourceEnumsExist() {
        // Verify all expected Source enum values are present
        assertNotNull(LearnsetEntry.Source.LEVEL);
        assertNotNull(LearnsetEntry.Source.TM);
        assertNotNull(LearnsetEntry.Source.HM);
        assertNotNull(LearnsetEntry.Source.EGG);
        assertNotNull(LearnsetEntry.Source.TUTOR);
    }
}
