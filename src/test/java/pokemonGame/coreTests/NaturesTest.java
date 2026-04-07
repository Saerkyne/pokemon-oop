package pokemonGame.coreTests;

import static org.junit.jupiter.api.Assertions.*;
import java.util.Set;
import java.util.HashSet;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import pokemonGame.core.Natures;
import pokemonGame.core.Stat;
import pokemonGame.core.StatCalculator;
import pokemonGame.model.Pokemon;
import pokemonGame.species.Abra;

/**
 * Unit tests for the {@link Natures} enum.
 * 
 * Test coverage includes:
 * - Correct modifier values for each nature and stat combination.
 * - Proper handling of neutral natures (BASHFUL, HARDY, DOCILE, QUIRKY, SERIOUS).
 * - Accurate display names for each nature.
 * - Existence of all 25 natures defined in the enum.
 * - Case-insensitive lookup via fromString().
 * - Random nature generation producing valid and varied results.
 * - assignTo and assignRandom correctly setting a Pokémon's nature.
 */

class NaturesTest {

    // =========================================================================
    // --- Modifier values ---
    // =========================================================================


    // Stream for parameterized test of nature modifiers
    static Stream<Arguments> natureModifierProvider() {
        return Stream.of(
            Arguments.of(Natures.ADAMANT, Stat.ATTACK, 1.1, Stat.SPECIAL_ATTACK, 0.9),
            Arguments.of(Natures.BASHFUL, Stat.ATTACK, 1.0, Stat.SPECIAL_ATTACK, 1.0), // neutral
            Arguments.of(Natures.BOLD, Stat.DEFENSE, 1.1, Stat.ATTACK, 0.9),
            Arguments.of(Natures.BRAVE, Stat.ATTACK, 1.1, Stat.SPEED, 0.9),
            Arguments.of(Natures.CALM, Stat.SPECIAL_DEFENSE, 1.1, Stat.ATTACK, 0.9),
            Arguments.of(Natures.CAREFUL, Stat.SPECIAL_DEFENSE, 1.1, Stat.SPECIAL_ATTACK, 0.9),
            Arguments.of(Natures.DOCILE, Stat.ATTACK, 1.0, Stat.SPECIAL_ATTACK, 1.0), // neutral
            Arguments.of(Natures.GENTLE, Stat.SPECIAL_DEFENSE, 1.1, Stat.DEFENSE, 0.9),
            Arguments.of(Natures.HARDY, Stat.ATTACK, 1.0, Stat.SPECIAL_ATTACK, 1.0), // neutral
            Arguments.of(Natures.HASTY, Stat.SPEED, 1.1, Stat.DEFENSE, 0.9),
            Arguments.of(Natures.IMPISH, Stat.DEFENSE, 1.1, Stat.SPECIAL_ATTACK, 0.9),
            Arguments.of(Natures.JOLLY, Stat.SPEED, 1.1, Stat.SPECIAL_ATTACK, 0.9),
            Arguments.of(Natures.LAX, Stat.DEFENSE, 1.1, Stat.SPECIAL_DEFENSE, 0.9),
            Arguments.of(Natures.LONELY, Stat.ATTACK, 1.1, Stat.DEFENSE, 0.9),
            Arguments.of(Natures.MILD, Stat.SPECIAL_ATTACK, 1.1, Stat.DEFENSE, 0.9),
            Arguments.of(Natures.MODEST, Stat.SPECIAL_ATTACK, 1.1, Stat.ATTACK, 0.9),
            Arguments.of(Natures.NAIVE, Stat.SPEED, 1.1, Stat.SPECIAL_DEFENSE, 0.9),
            Arguments.of(Natures.NAUGHTY, Stat.ATTACK, 1.1, Stat.SPECIAL_DEFENSE, 0.9),
            Arguments.of(Natures.QUIET, Stat.SPECIAL_ATTACK, 1.1, Stat.SPEED, 0.9),
            Arguments.of(Natures.QUIRKY, Stat.ATTACK, 1.0, Stat.SPECIAL_ATTACK, 1.0), // neutral
            Arguments.of(Natures.RASH, Stat.SPECIAL_ATTACK, 1.1, Stat.SPECIAL_DEFENSE, 0.9),
            Arguments.of(Natures.RELAXED, Stat.DEFENSE, 1.1, Stat.SPEED, 0.9),
            Arguments.of(Natures.SASSY, Stat.SPECIAL_DEFENSE, 1.1, Stat.SPEED, 0.9),
            Arguments.of(Natures.SERIOUS, Stat.ATTACK, 1.0, Stat.SPECIAL_ATTACK, 1.0), // neutral
            Arguments.of(Natures.TIMID, Stat.SPEED, 1.1, Stat.ATTACK, 0.9)
        );
    }
    
    /*
     * CHECKS:  The natureModifierValues test verifies that each nature correctly modifies
     *          the appropriate stats according to its definition.
     * HOW:     Uses a parameterized test with a method source providing all natures and
     *          their expected boosted and dropped stats along with the corresponding
     *          modifiers.
     * IDEAL:   Each nature should boost one stat by 10% (modifier 1.1) and drop another stat by 10%
     *          (modifier 0.9), while leaving the remaining stats unchanged (modifier 1.0). Neutral natures should have a modifier of 1.0 for all stats.
     */
    @ParameterizedTest
    @MethodSource("natureModifierProvider")
    void natureModifierValues(Natures nature, Stat boostedStat, double boostedModifier, Stat droppedStat, double droppedModifier) {
        // Check boosted stat modifier
        assertEquals(boostedModifier, nature.modifierFor(boostedStat),
                nature.getDisplayName() + " should boost " + boostedStat);
        
        // Check dropped stat modifier
        assertEquals(droppedModifier, nature.modifierFor(droppedStat),
                nature.getDisplayName() + " should drop " + droppedStat);
        
        // Check a neutral stat (not boosted or dropped)
        for (Stat stat : Stat.values()) {
            if (stat != boostedStat && stat != droppedStat) {
                assertEquals(1.0, nature.modifierFor(stat),
                        nature.getDisplayName() + " should not affect " + stat);
            }
        }

        // Apply the nature to a Pokémon and verify the current stats reflect the modifiers
        Pokemon abra = new Abra("Test Abra");
        abra.setLevel(50);
        abra.setNature(Natures.BASHFUL); // Start with a neutral nature to get base stats without modifiers
        StatCalculator.calculateAllStats(abra); // Calculate base stats before applying nature

        int originalBoostedStat = abra.getStat(boostedStat);
        int originalDroppedStat = abra.getStat(droppedStat);

        // Capture ALL neutral-nature stat values before applying the test nature,
        // so we can verify unaffected stats didn't change.
        java.util.Map<Stat, Integer> neutralStats = new java.util.EnumMap<>(Stat.class);
        for (Stat stat : Stat.values()) {
            neutralStats.put(stat, abra.getStat(stat));
        }

        nature.assignTo(abra);
        StatCalculator.calculateAllStats(abra); // Ensure stats are recalculated after assigning nature 
        assertEquals((int) (originalBoostedStat * boostedModifier), abra.getStat(boostedStat));
        assertEquals((int) (originalDroppedStat * droppedModifier), abra.getStat(droppedStat));

        // Check that neutral stats remain unchanged after applying the nature.
        // We need to compare against the ORIGINAL neutral-nature value, not the
        // stat against itself (which is a no-op that always passes).
        for (Stat stat : Stat.values()) {
            if (stat != boostedStat && stat != droppedStat && stat != Stat.HP) {
                assertEquals(neutralStats.get(stat), abra.getStat(stat),
                        nature.getDisplayName() + " should not affect " + stat);
            }
        }    
    }

    // =========================================================================
    // --- Enum completeness ---
    // =========================================================================

    /*
     * Guards against accidental additions or deletions. If a nature is added
     * or removed, this test forces you to update the number AND add/remove
     * the corresponding row in natureModifierProvider().
     */
    @Test
    void allTwentyFiveNaturesExist() {
        assertEquals(25, Natures.values().length,
                "There should be exactly 25 natures (the canonical set from the main series)");
    }

    // =========================================================================
    // --- Display names ---
    // =========================================================================

    /*
     * Display names appear in Discord embeds, so they must be non-null,
     * non-empty, and start with an uppercase letter. This catches typos like
     * "adament" or accidentally setting a name to null.
     */
    @Test
    void allNaturesHaveValidDisplayNames() {
        for (Natures nature : Natures.values()) {
            String name = nature.getDisplayName();
            assertNotNull(name, nature.name() + " display name should not be null");
            assertFalse(name.isBlank(), nature.name() + " display name should not be blank");
            assertTrue(Character.isUpperCase(name.charAt(0)),
                    nature.name() + " display name should start with an uppercase letter, got: " + name);
        }
    }

    // =========================================================================
    // --- fromString() lookup ---
    // =========================================================================

    /*
     * fromString() is used when reading natures back from the database or from
     * user input. It should match on both the display name ("Adamant") and the
     * enum constant name ("ADAMANT"), case-insensitively.
     */
    @Test
    void fromString_matchesDisplayName() {
        assertEquals(Natures.ADAMANT, Natures.fromString("Adamant"));
        assertEquals(Natures.TIMID, Natures.fromString("Timid"));
    }

    @Test
    void fromString_caseInsensitive() {
        assertEquals(Natures.BOLD, Natures.fromString("bold"));
        assertEquals(Natures.BOLD, Natures.fromString("BOLD"));
        assertEquals(Natures.BOLD, Natures.fromString("BoLd"));
    }

    @Test
    void fromString_matchesEnumName() {
        // The method also matches on n.name() (the all-caps enum constant name)
        assertEquals(Natures.JOLLY, Natures.fromString("JOLLY"));
    }

    @Test
    void fromString_throwsForUnknownNature() {
        assertThrows(IllegalArgumentException.class,
                () -> Natures.fromString("MadeUpNature"),
                "fromString should throw for an unrecognized nature name");
    }

    @Test
    void fromString_throwsForNull() {
        // Null input should not silently return a default nature
        assertThrows(Exception.class,
                () -> Natures.fromString(null),
                "fromString should throw when given null");
    }

    // =========================================================================
    // --- assignTo() and assignRandom() ---
    // =========================================================================

    @Test
    void assignTo_setsNatureOnPokemon() {
        Pokemon abra = new Abra("Test Abra");
        Natures.BRAVE.assignTo(abra);
        assertEquals(Natures.BRAVE, abra.getNature(),
                "assignTo should set the Pokémon's nature to the specified value");
    }

    /*
     * We can't predict which nature assignRandom picks, but we CAN verify:
     *   1. The result is never null (the Pokémon always gets a nature).
     *   2. Over many calls, more than one distinct nature appears (it's not
     *      hard-coded to always return the same one).
     */
    @Test
    void assignRandom_producesValidAndVariedNatures() {
        Set<Natures> observed = new HashSet<>();
        for (int i = 0; i < 200; i++) {
            Pokemon abra = new Abra("Test Abra " + i);
            Natures.assignRandom(abra);
            assertNotNull(abra.getNature(),
                    "assignRandom should never leave the nature as null");
            observed.add(abra.getNature());
        }
        // With 25 natures and 200 trials, getting only 1 distinct nature has a
        // probability of (1/25)^199 ≈ 0 — this will not flake.
        assertTrue(observed.size() > 1,
                "assignRandom should produce more than one distinct nature over 200 trials");
    }

    
}
