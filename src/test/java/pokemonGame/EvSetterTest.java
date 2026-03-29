package pokemonGame;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

import pokemonGame.mons.Abra;

/**
 * Unit tests for the EV setter methods on {@link Pokemon}.
 *
 * EV rules under test:
 *   - Per-stat maximum: 252
 *   - Total EV maximum across all six stats: 510
 *   - Setters OVERWRITE (not add to) the stat value
 *   - evTotal must always equal the sum of all six individual EVs
 *
 * These tests define correct behavior. The current implementation may fail
 * some of these if it contains bugs — that is the point. Fix the code to
 * make them pass, not the other way around.
 */
class EvSetterTest {

    private Pokemon pokemon;

    @BeforeEach
    void setUp() {
        pokemon = new Abra("EV Test Abra");
    }

    // =========================================================================
    // --- Fresh Pokémon defaults ---
    // =========================================================================

    /*
     * CHECKS:  A newly constructed Pokémon has 0 EVs in every stat and 0 total.
     * HOW:     Asserts all six EV getters and getEvTotal() return 0.
     * IDEAL:   All EVs start at 0. The constructor should not assign any EVs.
     * CURRENT: Should pass — constructors do not assign EVs.
     */
    @Test
    void freshPokemonHasZeroEvs() {
        assertEquals(0, pokemon.getEvHp());
        assertEquals(0, pokemon.getEvAttack());
        assertEquals(0, pokemon.getEvDefense());
        assertEquals(0, pokemon.getEvSpecialAttack());
        assertEquals(0, pokemon.getEvSpecialDefense());
        assertEquals(0, pokemon.getEvSpeed());
        assertEquals(0, pokemon.getEvTotal());
    }

    // =========================================================================
    // --- Basic set and retrieve ---
    // =========================================================================

    /*
     * CHECKS:  setEvHp() stores the provided value and it can be retrieved.
     * HOW:     Calls setEvHp(100), then asserts getEvHp() returns 100.
     * IDEAL:   The setter directly assigns the value when within limits.
     * CURRENT: Should pass with the corrected setter.
     */
    @Test
    void setEvHpStoresValue() {
        EvManager evManager = new EvManager();
        evManager.setEv(pokemon, Stat.HP, 100);
        assertEquals(100, pokemon.getEvHp());
    }

    /*
     * CHECKS:  setEvAttack() stores the provided value and it can be retrieved.
     * HOW:     Calls setEvAttack(200), then asserts getEvAttack() returns 200.
     * IDEAL:   The setter directly assigns the value when within limits.
     * CURRENT: Should pass.
     */
    @Test
    void setEvAttackStoresValue() {
        EvManager evManager = new EvManager();
        evManager.setEv(pokemon, Stat.ATTACK, 200);
        assertEquals(200, pokemon.getEvAttack());
    }

    /*
     * CHECKS:  setEvDefense() stores the provided value and it can be retrieved.
     * HOW:     Calls setEvDefense(150), then asserts getEvDefense() returns 150.
     * IDEAL:   The setter directly assigns the value when within limits.
     * CURRENT: Should pass.
     */
    @Test
    void setEvDefenseStoresValue() {
        EvManager evManager = new EvManager();
        evManager.setEv(pokemon, Stat.DEFENSE, 150);
        assertEquals(150, pokemon.getEvDefense());
    }

    /*
     * CHECKS:  setEvSpecialAttack() stores the provided value and it can be retrieved.
     * HOW:     Calls setEvSpecialAttack(252), then asserts getEvSpecialAttack() returns 252.
     * IDEAL:   The per-stat max of 252 is accepted without capping.
     * CURRENT: Should pass.
     */
    @Test
    void setEvSpecialAttackStoresValue() {
        EvManager evManager = new EvManager();
        evManager.setEv(pokemon, Stat.SPECIAL_ATTACK, 252);
        assertEquals(252, pokemon.getEvSpecialAttack());
    }

    /*
     * CHECKS:  setEvSpecialDefense() stores the provided value and it can be retrieved.
     * HOW:     Calls setEvSpecialDefense(80), then asserts getEvSpecialDefense() returns 80.
     * IDEAL:   The setter directly assigns the value when within limits.
     * CURRENT: Should pass.
     */
    @Test
    void setEvSpecialDefenseStoresValue() {
        EvManager evManager = new EvManager();
        evManager.setEv(pokemon, Stat.SPECIAL_DEFENSE, 80);
        assertEquals(80, pokemon.getEvSpecialDefense());
    }

    /*
     * CHECKS:  setEvSpeed() stores the provided value and it can be retrieved.
     * HOW:     Calls setEvSpeed(44), then asserts getEvSpeed() returns 44.
     * IDEAL:   The setter directly assigns the value when within limits.
     * CURRENT: Should pass.
     */
    @Test
    void setEvSpeedStoresValue() {
        EvManager evManager = new EvManager();
        evManager.setEv(pokemon, Stat.SPEED, 44);
        assertEquals(44, pokemon.getEvSpeed());
    }

    // =========================================================================
    // --- evTotal tracking ---
    // =========================================================================

    /*
     * CHECKS:  After setting one EV, evTotal equals that single value.
     * HOW:     Calls setEvHp(100), then asserts getEvTotal() returns 100.
     * IDEAL:   evTotal always equals the sum of all six individual EVs.
     * CURRENT: Should pass with the corrected setter.
     */
    @Test
    void evTotalReflectsSingleSetter() {
        EvManager evManager = new EvManager();
        evManager.setEv(pokemon, Stat.HP, 100);
        assertEquals(100, pokemon.getEvTotal());
    }

    /*
     * CHECKS:  After setting EVs in multiple stats, evTotal equals their sum.
     * HOW:     Sets HP=100, Attack=150, Speed=50, then asserts evTotal is 300.
     * IDEAL:   evTotal is always the exact sum of all six individual EVs.
     * CURRENT: Should pass if all setters correctly track the total.
     */
    @Test
    void evTotalReflectsMultipleSetters() {
        EvManager evManager = new EvManager();
        evManager.setEv(pokemon, Stat.HP, 100);
        evManager.setEv(pokemon, Stat.ATTACK, 150);
        evManager.setEv(pokemon, Stat.SPEED, 50);
        assertEquals(300, pokemon.getEvTotal());
    }

    /*
     * CHECKS:  evTotal equals the sum of all six stats when all are set.
     * HOW:     Sets all six EVs to specific values summing to 510, then asserts
     *          evTotal equals 510.
     * IDEAL:   A fully EV-trained Pokémon reports exactly 510 total.
     * CURRENT: Should pass.
     */
    @Test
    void evTotalSumsAllSixStats() {
        EvManager evManager = new EvManager();
        evManager.setEv(pokemon, Stat.HP, 252);
        evManager.setEv(pokemon, Stat.ATTACK, 252);
        evManager.setEv(pokemon, Stat.DEFENSE, 6);
        assertEquals(510, pokemon.getEvTotal());
    }

    // =========================================================================
    // --- Per-stat cap (252) ---
    // =========================================================================

    /*
     * CHECKS:  setEvHp() caps at 252 when given a value above the per-stat max.
     * HOW:     Calls setEvHp(300), then asserts getEvHp() returns 252.
     * IDEAL:   No single stat can exceed 252. Values above are silently capped.
     * CURRENT: Should pass with the Math.min(252, ...) logic.
     */
    @Test
    void setEvHpCapsAtPerStatMax() {
        EvManager evManager = new EvManager();
        evManager.setEv(pokemon, Stat.HP, 300);
        assertEquals(252, pokemon.getEvHp());
    }

    /*
     * CHECKS:  setEvAttack() caps at 252 when given a value above the per-stat max.
     * HOW:     Calls setEvAttack(999), then asserts getEvAttack() returns 252.
     * IDEAL:   No single stat can exceed 252.
     * CURRENT: Should pass.
     */
    @Test
    void setEvAttackCapsAtPerStatMax() {
        EvManager evManager = new EvManager();
        evManager.setEv(pokemon, Stat.ATTACK, 999);
        assertEquals(252, pokemon.getEvAttack());
    }

    /*
     * CHECKS:  Setting a stat to exactly 252 is accepted without modification.
     * HOW:     Calls setEvSpeed(252), then asserts getEvSpeed() returns 252.
     * IDEAL:   252 is the boundary — it should be accepted as-is.
     * CURRENT: Should pass.
     */
    @Test
    void exactPerStatMaxIsAccepted() {
        EvManager evManager = new EvManager();
        evManager.setEv(pokemon, Stat.SPEED, 252);
        assertEquals(252, pokemon.getEvSpeed());
    }

    // =========================================================================
    // --- Total cap (510) ---
    // =========================================================================

    /*
     * CHECKS:  When setting a stat would push the total above 510, the value
     *          is reduced so the total lands at exactly 510.
     * HOW:     Sets HP=252, Attack=252 (total=504). Then sets Defense=100.
     *          Only 6 room remains, so Defense should be capped to 6.
     * IDEAL:   The total never exceeds 510. The last setter gets the remainder.
     * CURRENT: Should pass.
     */
    @Test
    void totalCapReducesExcessiveStat() {
        EvManager evManager = new EvManager();
        evManager.setEv(pokemon, Stat.HP, 252);
        evManager.setEv(pokemon, Stat.ATTACK, 252);
        // Total is 504, only 6 room left
        evManager.setEv(pokemon, Stat.DEFENSE, 100);
        assertEquals(6, pokemon.getEvDefense());
        assertEquals(510, pokemon.getEvTotal());
    }

    /*
     * CHECKS:  When the total is already at 510, setting a new stat that was
     *          previously 0 is capped to 0 (no room).
     * HOW:     Sets HP=252, Attack=252, Defense=6 (total=510). Then sets
     *          Speed=100. Speed should remain 0.
     * IDEAL:   Once 510 is reached, no additional EVs can be assigned to a
     *          stat that was 0.
     * CURRENT: Should pass.
     */
    @Test
    void noRoomLeftMeansZero() {
        EvManager evManager = new EvManager();
        evManager.setEv(pokemon, Stat.HP, 252);
        evManager.setEv(pokemon, Stat.ATTACK, 252);
        evManager.setEv(pokemon, Stat.DEFENSE, 6);
        // Total is now 510
        evManager.setEv(pokemon, Stat.SPEED, 100);
        assertEquals(0, pokemon.getEvSpeed());
        assertEquals(510, pokemon.getEvTotal());
    }

    /*
     * CHECKS:  Both the per-stat cap and total cap are enforced simultaneously.
     *          The stricter of the two wins.
     * HOW:     Sets HP=252, Attack=252 (total=504). Sets Defense=300.
     *          Per-stat cap is 252, total room is 6. The total room (6) is
     *          stricter, so Defense should be 6.
     * IDEAL:   Math.min(value, Math.min(252, roomTotal)) — both caps applied.
     * CURRENT: Should pass.
     */
    @Test
    void bothCapsAppliedSimultaneously() {
        EvManager evManager = new EvManager();
        evManager.setEv(pokemon, Stat.HP, 252);
        evManager.setEv(pokemon, Stat.ATTACK, 252);
        evManager.setEv(pokemon, Stat.DEFENSE, 300);
        assertEquals(6, pokemon.getEvDefense());
        assertEquals(510, pokemon.getEvTotal());
    }

    // =========================================================================
    // --- Overwrite semantics (setter, not adder) ---
    // =========================================================================

    /*
     * CHECKS:  Calling setEvHp() twice overwrites the first value, not adds to it.
     * HOW:     Calls setEvHp(100), then setEvHp(50). Asserts getEvHp() is 50,
     *          not 150. Also asserts evTotal is 50, not 150.
     * IDEAL:   Setters replace the current value. The total adjusts accordingly.
     * CURRENT: Critical test — the old code (before the fix) failed this because
     *          it treated the setter as additive.
     */
    @Test
    void setEvHpOverwritesDoesNotAdd() {
        EvManager evManager = new EvManager();
        evManager.setEv(pokemon, Stat.HP, 100);
        evManager.setEv(pokemon, Stat.HP, 50);
        assertEquals(50, pokemon.getEvHp());
        assertEquals(50, pokemon.getEvTotal());
    }

    /*
     * CHECKS:  Overwriting a stat to a lower value frees room in the total,
     *          allowing other stats to be set higher.
     * HOW:     Sets HP=252, Attack=252, Defense=6 (total=510). Then overwrites
     *          HP to 0. Total should drop to 258, freeing 252 room. Then sets
     *          Speed=200. Speed should be 200.
     * IDEAL:   Overwriting down reclaims total room for other stats.
     * CURRENT: Should pass.
     */
    @Test
    void overwritingDownFreesRoom() {
        EvManager evManager = new EvManager();
        evManager.setEv(pokemon, Stat.HP, 252);
        evManager.setEv(pokemon, Stat.ATTACK, 252);
        evManager.setEv(pokemon, Stat.DEFENSE, 6);
        assertEquals(510, pokemon.getEvTotal());

        // Overwrite HP to 0 — frees 252
        evManager.setEv(pokemon, Stat.HP, 0);
        assertEquals(0, pokemon.getEvHp());
        assertEquals(258, pokemon.getEvTotal());

        // Now there's room for Speed
        evManager.setEv(pokemon, Stat.SPEED, 200);
        assertEquals(200, pokemon.getEvSpeed());
        assertEquals(458, pokemon.getEvTotal());
    }

    /*
     * CHECKS:  Setting the same value twice is idempotent — the stat and total
     *          don't change.
     * HOW:     Calls setEvHp(100) twice. Asserts getEvHp()=100, evTotal=100.
     * IDEAL:   Repeated identical sets are harmless — no double-counting.
     * CURRENT: The old buggy code would have produced evTotal=200 here.
     */
    @Test
    void settingSameValueTwiceIsIdempotent() {
        EvManager evManager = new EvManager();
        evManager.setEv(pokemon, Stat.HP, 100);
        evManager.setEv(pokemon, Stat.HP, 100);
        assertEquals(100, pokemon.getEvHp());
        assertEquals(100, pokemon.getEvTotal());
    }

    /*
     * CHECKS:  A stat at 252 can be overwritten to 0 (the "already at max"
     *          guard in the old code would have blocked this).
     * HOW:     Calls setEvHp(252), then setEvHp(0). Asserts getEvHp()=0.
     * IDEAL:   Setters allow any valid value regardless of current value.
     * CURRENT: The old code had `if (this.evHp == 252) return;` which would
     *          have blocked this overwrite.
     */
    @Test
    void canOverwriteMaxStatToZero() {
        EvManager evManager = new EvManager();
        evManager.setEv(pokemon, Stat.HP, 252);
        assertEquals(252, pokemon.getEvHp());

        evManager.setEv(pokemon, Stat.HP, 0);
        assertEquals(0, pokemon.getEvHp());
        assertEquals(0, pokemon.getEvTotal());
    }

    // =========================================================================
    // --- Negative input handling ---
    // =========================================================================

    /*
     * CHECKS:  Negative EV values are treated as 0 (no negative EVs exist
     *          in the game rules).
     * HOW:     Calls setEvHp(-50). Asserts getEvHp() is 0 and evTotal is 0.
     * IDEAL:   Negative inputs are clamped to 0 — EVs cannot be negative.
     * CURRENT: May fail if the setter doesn't clamp negatives. The Math.min()
     *          chain would allow a negative through since Math.min(-50, 252)
     *          is -50. A Math.max(0, ...) wrapper is needed.
     */
    @Test
    void negativeEvIsTreatedAsZero() {
        EvManager evManager = new EvManager();
        evManager.setEv(pokemon, Stat.HP, -50);
        assertEquals(0, pokemon.getEvHp());
        assertEquals(0, pokemon.getEvTotal());
    }

    /*
     * CHECKS:  Negative Attack EV doesn't corrupt the total.
     * HOW:     Sets HP=100 first, then setEvAttack(-10). Asserts Attack=0,
     *          evTotal=100.
     * IDEAL:   Negative inputs are clamped to 0 and don't subtract from total.
     * CURRENT: May fail — same issue as above.
     */
    @Test
    void negativeEvDoesNotCorruptTotal() {
        EvManager evManager = new EvManager();
        evManager.setEv(pokemon, Stat.HP, 100);
        evManager.setEv(pokemon, Stat.ATTACK, -10);
        assertEquals(0, pokemon.getEvAttack());
        assertEquals(100, pokemon.getEvTotal());
    }

    // =========================================================================
    // --- evTotal consistency invariant ---
    // =========================================================================

    /*
     * CHECKS:  evTotal always equals the arithmetic sum of all six individual
     *          EVs, no matter how many setters are called in what order.
     * HOW:     Performs a series of sets and overwrites across all six stats,
     *          then asserts evTotal equals the manual sum of the six getters.
     * IDEAL:   The invariant evTotal == sum(all six EVs) holds at ALL times.
     * CURRENT: Should pass if all six setters correctly subtract old values
     *          before adding new ones.
     */
    @Test
    void evTotalAlwaysEqualsSumOfAllSixStats() {
        EvManager evManager = new EvManager();
        evManager.setEv(pokemon, Stat.HP, 100);
        evManager.setEv(pokemon, Stat.ATTACK, 200);
        evManager.setEv(pokemon, Stat.DEFENSE, 50);
        evManager.setEv(pokemon, Stat.SPECIAL_ATTACK, 80);
        evManager.setEv(pokemon, Stat.SPECIAL_DEFENSE, 30);
        evManager.setEv(pokemon, Stat.SPEED, 40);

        // Overwrite some
        evManager.setEv(pokemon, Stat.HP, 10);
        evManager.setEv(pokemon, Stat.ATTACK, 252);
        evManager.setEv(pokemon, Stat.SPECIAL_DEFENSE, 0);

        int expectedTotal = pokemon.getEvHp()
                + pokemon.getEvAttack()
                + pokemon.getEvDefense()
                + pokemon.getEvSpecialAttack()
                + pokemon.getEvSpecialDefense()
                + pokemon.getEvSpeed();

        assertEquals(expectedTotal, pokemon.getEvTotal(),
                "evTotal must equal the sum of all six individual EVs");
    }

    /*
     * CHECKS:  After a complex sequence of sets that fills the total to 510,
     *          then overwrites stats downward and fills again, the total is
     *          consistent and never exceeds 510.
     * HOW:     Fills to 510, overwrites HP down, fills Speed up. Asserts
     *          total <= 510 and equals the sum of all six EVs.
     * IDEAL:   No sequence of valid setter calls can break the 510 invariant.
     * CURRENT: Should pass.
     */
    @Test
    void complexSequenceNeverExceeds510() {
        EvManager evManager = new EvManager();
        // Fill to 510
        evManager.setEv(pokemon, Stat.HP, 252);
        evManager.setEv(pokemon, Stat.ATTACK, 252);
        evManager.setEv(pokemon, Stat.DEFENSE, 6);
        assertEquals(510, pokemon.getEvTotal());

        // Free room by overwriting HP down
        evManager.setEv(pokemon, Stat.HP, 100);
        // Fill Speed into the freed room
        evManager.setEv(pokemon, Stat.SPEED, 152);

        assertTrue(pokemon.getEvTotal() <= 510,
                "Total should never exceed 510");

        int manualSum = pokemon.getEvHp()
                + pokemon.getEvAttack()
                + pokemon.getEvDefense()
                + pokemon.getEvSpecialAttack()
                + pokemon.getEvSpecialDefense()
                + pokemon.getEvSpeed();
        assertEquals(manualSum, pokemon.getEvTotal(),
                "evTotal must match the manual sum");
    }
}
