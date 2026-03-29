package pokemonGame;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

import pokemonGame.mons.Abra;

/**
 * Unit tests for the EV adder methods on {@link Pokemon}.
 *
 * EV rules under test:
 *   - Per-stat maximum: 252
 *   - Total EV maximum across all six stats: 510
 *   - Adders ADD TO the current stat value (not overwrite)
 *   - evTotal must always equal the sum of all six individual EVs
 *   - Adders call calculateCurrentStats() after applying — stats should update
 *   - Adders should not accept negative inputs (EVs only go up via training)
 *
 * These tests define correct behavior. The current implementation may fail
 * some of these if it contains bugs — that is the point. Fix the code to
 * make them pass.
 */
class EvAdderTest {

    private Pokemon pokemon;

    @BeforeEach
    void setUp() {
        pokemon = new Abra("EV Test Abra");
    }

    // =========================================================================
    // --- Basic add and retrieve ---
    // =========================================================================

    /*
     * CHECKS:  addEvHp() increases the HP EV by the given amount from 0.
     * HOW:     Calls addEvHp(50), then asserts getEvHp() returns 50.
     * IDEAL:   Adding to 0 gives exactly the added amount.
     * CURRENT: Should pass.
     */
    @Test
    void addEvHpFromZero() {
        EvManager evManager = new EvManager();
        evManager.setEv(pokemon, Stat.HP, 50);
        assertEquals(50, EvManager.getEv(pokemon, Stat.HP));
    }

    /*
     * CHECKS:  addEvAttack() increases the Attack EV by the given amount from 0.
     * HOW:     Calls addEvAttack(100), then asserts getEvAttack() returns 100.
     * IDEAL:   Adding to 0 gives exactly the added amount.
     * CURRENT: Should pass.
     */
    @Test
    void addEvAttackFromZero() {
        EvManager evManager = new EvManager();
        evManager.setEv(pokemon, Stat.ATTACK, 100);
        assertEquals(100, EvManager.getEv(pokemon, Stat.ATTACK));
    }

    /*
     * CHECKS:  Multiple adds to the same stat accumulate correctly.
     * HOW:     Calls addEvHp(50) three times. Asserts getEvHp() returns 150.
     * IDEAL:   Repeated adds accumulate: 50 + 50 + 50 = 150.
     * CURRENT: Should pass.
     */
    @Test
    void multipleAddsAccumulate() {
        EvManager evManager = new EvManager();
        evManager.addEv(pokemon, Stat.HP, 50);
        evManager.addEv(pokemon, Stat.HP, 50);
        evManager.addEv(pokemon, Stat.HP, 50);
        assertEquals(150, EvManager.getEv(pokemon, Stat.HP));
    }

    // =========================================================================
    // --- evTotal tracking ---
    // =========================================================================

    /*
     * CHECKS:  After adding EVs to one stat, evTotal reflects the added amount.
     * HOW:     Calls addEvHp(80), then asserts getEvTotal() returns 80.
     * IDEAL:   evTotal is updated on every add.
     * CURRENT: Should pass.
     */
    @Test
    void evTotalReflectsSingleAdd() {
        EvManager evManager = new EvManager();
        evManager.addEv(pokemon, Stat.HP, 80);
        assertEquals(80, EvManager.getTotalEv(pokemon));
    }

    /*
     * CHECKS:  After adding EVs to multiple stats, evTotal equals their sum.
     * HOW:     Adds HP=100, Attack=50, Speed=30 (sum=180). Asserts evTotal=180.
     * IDEAL:   evTotal always equals the sum of all six individual EVs.
     * CURRENT: Should pass.
     */
    @Test
    void evTotalReflectsMultipleAdds() {
        EvManager evManager = new EvManager();
        evManager.addEv(pokemon, Stat.HP, 100);
        evManager.addEv(pokemon, Stat.ATTACK, 50);
        evManager.addEv(pokemon, Stat.SPEED, 30);
        assertEquals(180, EvManager.getTotalEv(pokemon));
    }

    // =========================================================================
    // --- Per-stat cap (252) ---
    // =========================================================================

    /*
     * CHECKS:  addEvHp() caps the stat at 252 when a single add exceeds it.
     * HOW:     Calls addEvHp(300). Asserts getEvHp() is 252, not 300.
     * IDEAL:   A single large add is clamped to the per-stat max.
     * CURRENT: Should pass — the Math.min(addedEv, roomStat) handles this.
     */
    @Test
    void singleAddCapsAtPerStatMax() {
        EvManager evManager = new EvManager();
        evManager.setEv(pokemon, Stat.HP, 300);
        assertEquals(252, EvManager.getEv(pokemon, Stat.HP));
    }

    /*
     * CHECKS:  Multiple adds to the same stat stop at 252, not beyond.
     * HOW:     Adds HP=200, then HP=200 again. Total adds = 400, but the stat
     *          should cap at 252. The second add should contribute only 52.
     * IDEAL:   Cumulative adds never push a stat past 252.
     * CURRENT: Should pass.
     */
    @Test
    void cumulativeAddsCapsAtPerStatMax() {
        EvManager evManager = new EvManager();
        evManager.addEv(pokemon, Stat.HP, 200);
        evManager.addEv(pokemon, Stat.HP, 200);
        assertEquals(252, EvManager.getEv(pokemon, Stat.HP));
        assertEquals(252, EvManager.getTotalEv(pokemon));
    }

    /*
     * CHECKS:  Adding 0 to a stat at 252 is a no-op — stat and total stay
     *          unchanged.
     * HOW:     Adds HP=252, then addEvHp(0). Asserts HP still 252, total 252.
     * IDEAL:   A zero add doesn't corrupt the state.
     * CURRENT: Should pass — the `actual <= 0` guard handles this.
     */
    @Test
    void addZeroAtMaxIsNoOp() {
        EvManager evManager = new EvManager();
        evManager.setEv(pokemon, Stat.HP, 252);
        evManager.addEv(pokemon, Stat.HP, 0);
        assertEquals(252, EvManager.getEv(pokemon, Stat.HP));
        assertEquals(252, EvManager.getTotalEv(pokemon));
    }

    // =========================================================================
    // --- Total cap (510) ---
    // =========================================================================

    /*
     * CHECKS:  When the total is near 510, a large add is reduced so the
     *          total lands at exactly 510.
     * HOW:     Adds HP=252, Attack=252 (total=504). Then addEvDefense(100).
     *          Only 6 room remains, so Defense should be 6.
     * IDEAL:   Total never exceeds 510. Excess is silently discarded.
     * CURRENT: Should pass.
     */
    @Test
    void totalCapReducesAdd() {
        EvManager evManager = new EvManager();
        evManager.setEv(pokemon, Stat.HP, 252);
        evManager.setEv(pokemon, Stat.ATTACK, 252);
        evManager.setEv(pokemon, Stat.DEFENSE, 100);
        assertEquals(6, EvManager.getEv(pokemon, Stat.DEFENSE));
        assertEquals(510, EvManager.getTotalEv(pokemon));
    }

    /*
     * CHECKS:  Once the total is at 510, any further add is rejected entirely.
     * HOW:     Fills to 510 (HP=252, Attack=252, Defense=6). Then addEvSpeed(50).
     *          Speed should remain 0.
     * IDEAL:   At total=510, all adds to any stat are no-ops.
     * CURRENT: Should pass — the `if (evTotal >= 510) return;` guard handles this.
     */
    @Test
    void noAddPossibleAtTotalMax() {
        EvManager evManager = new EvManager();
        evManager.setEv(pokemon, Stat.HP, 252);
        evManager.setEv(pokemon, Stat.ATTACK, 252);
        evManager.setEv(pokemon, Stat.DEFENSE, 6);
        assertEquals(510, EvManager.getTotalEv(pokemon));

        evManager.setEv(pokemon, Stat.SPEED, 50);
        assertEquals(0, EvManager.getEv(pokemon, Stat.SPEED));
        assertEquals(510, EvManager.getTotalEv(pokemon));
    }

    /*
     * CHECKS:  Both the per-stat cap and total cap are enforced simultaneously
     *          during an add. The stricter cap wins.
     * HOW:     Adds HP=252, Attack=252 (total=504). Then addEvDefense(300).
     *          Per-stat cap allows 252, but total cap only allows 6. Result: 6.
     * IDEAL:   Math.min(added, Math.min(roomTotal, roomStat)) — both applied.
     * CURRENT: Should pass.
     */
    @Test
    void bothCapsEnforcedOnAdd() {
        EvManager evManager = new EvManager();
        evManager.setEv(pokemon, Stat.HP, 252);
        evManager.setEv(pokemon, Stat.ATTACK, 252);
        evManager.setEv(pokemon, Stat.DEFENSE, 300);
        assertEquals(6, EvManager.getEv(pokemon, Stat.DEFENSE));
        assertEquals(510, EvManager.getTotalEv(pokemon));
    }

    // =========================================================================
    // --- Negative input handling ---
    // =========================================================================

    /*
     * CHECKS:  Adding a negative value does not decrease the stat (EVs only
     *          go up from training in the game rules).
     * HOW:     Adds HP=100, then addEvHp(-50). Asserts HP is still 100.
     * IDEAL:   Negative adds are rejected. The stat stays unchanged.
     * CURRENT: Should pass — the Math.min(-50, roomStat) gives a negative
     *          `actual`, and the `if (actual <= 0) return;` guard catches it.
     */
    @Test
    void negativeAddDoesNotDecreaseStat() {
        EvManager evManager = new EvManager();
        evManager.setEv(pokemon, Stat.HP, 100);
        evManager.addEv(pokemon, Stat.HP, -50);
        assertEquals(100, EvManager.getEv(pokemon, Stat.HP));
        assertEquals(100, EvManager.getTotalEv(pokemon));
    }

    /*
     * CHECKS:  Adding a negative value from 0 does not produce a negative stat.
     * HOW:     Calls addEvAttack(-10). Asserts getEvAttack() is 0, evTotal is 0.
     * IDEAL:   Negative adds on a fresh Pokémon are safely ignored.
     * CURRENT: Should pass.
     */
    @Test
    void negativeAddOnFreshPokemonIsNoOp() {
        EvManager evManager = new EvManager();
        evManager.setEv(pokemon, Stat.ATTACK, -10);
        assertEquals(0, EvManager.getEv(pokemon, Stat.ATTACK));
        assertEquals(0, EvManager.getTotalEv(pokemon));
    }

    // =========================================================================
    // --- Interaction between setters and adders ---
    // =========================================================================

    /*
     * CHECKS:  After using a setter to assign an EV, an adder correctly adds
     *          on top of the set value.
     * HOW:     Calls setEvHp(100), then addEvHp(50). Asserts getEvHp() is 150
     *          and evTotal is 150.
     * IDEAL:   Setters and adders share the same underlying state. Adding
     *          after setting accumulates from the set value.
     * CURRENT: Should pass if both setters and adders maintain evTotal correctly.
     */
    @Test
    void adderWorksAfterSetter() {
        EvManager evManager = new EvManager();
        evManager.setEv(pokemon, Stat.HP, 100);
        evManager.setEv(pokemon, Stat.HP, 150);
        assertEquals(150, EvManager.getEv(pokemon, Stat.HP));
        assertEquals(150, EvManager.getTotalEv(pokemon));
    }

    /*
     * CHECKS:  After using adders to train EVs, a setter overwrites (not adds
     *          to) the trained value, and the total adjusts.
     * HOW:     Adds HP=100, Attack=50 (total=150). Then sets HP to 10.
     *          HP should be 10, total should be 60.
     * IDEAL:   Setters overwrite regardless of whether EVs came from adders.
     * CURRENT: Should pass.
     */
    @Test
    void setterOverwritesAfterAdder() {
        EvManager evManager = new EvManager();
        evManager.setEv(pokemon, Stat.HP, 100);
        evManager.setEv(pokemon, Stat.ATTACK, 50);
        assertEquals(150, EvManager.getTotalEv(pokemon));

        evManager.setEv(pokemon, Stat.HP, 10);
        assertEquals(10, EvManager.getEv(pokemon, Stat.HP));
        assertEquals(60, EvManager.getTotalEv(pokemon));
    }

    // =========================================================================
    // --- evTotal consistency invariant ---
    // =========================================================================

    /*
     * CHECKS:  evTotal always equals the sum of all six individual EVs after
     *          a mixed sequence of adds across multiple stats.
     * HOW:     Performs a series of adds across all six stats, then verifies
     *          evTotal equals the sum of all six getters.
     * IDEAL:   The invariant evTotal == sum(all six EVs) holds at all times.
     * CURRENT: Should pass.
     */
    @Test
    void evTotalConsistentAfterMixedAdds() {
        EvManager evManager = new EvManager();
        evManager.setEv(pokemon, Stat.HP, 50);
        evManager.setEv(pokemon, Stat.ATTACK, 80);
        evManager.setEv(pokemon, Stat.DEFENSE, 30);
        evManager.setEv(pokemon, Stat.SPECIAL_ATTACK, 100);
        evManager.setEv(pokemon, Stat.SPECIAL_DEFENSE, 20);
        evManager.setEv(pokemon, Stat.SPEED, 70);

        int expectedTotal = EvManager.getEv(pokemon, Stat.HP)
                + EvManager.getEv(pokemon, Stat.ATTACK)
                + EvManager.getEv(pokemon, Stat.DEFENSE)
                + EvManager.getEv(pokemon, Stat.SPECIAL_ATTACK)
                + EvManager.getEv(pokemon, Stat.SPECIAL_DEFENSE)
                + EvManager.getEv(pokemon, Stat.SPEED);

        assertEquals(expectedTotal, EvManager.getTotalEv(pokemon),
                "evTotal must equal the sum of all six individual EVs");
    }

    /*
     * CHECKS:  A complex mixed sequence of setters and adders across all six
     *          stats maintains the evTotal invariant and never exceeds 510.
     * HOW:     Sets some EVs, adds to others, overwrites some back down, adds
     *          more. At the end, asserts evTotal equals the manual sum and
     *          is <= 510.
     * IDEAL:   No sequence of valid setter/adder calls can break the invariants.
     * CURRENT: This is the most comprehensive integration test. If this passes,
     *          the EV system is working correctly.
     */
    @Test
    void complexMixedSequenceMaintainsInvariants() {
        EvManager evManager = new EvManager();
        // Phase 1: set some EVs
        evManager.setEv(pokemon, Stat.HP, 200);
        evManager.setEv(pokemon, Stat.ATTACK, 200);

        // Phase 2: add to others
        evManager.setEv(pokemon, Stat.DEFENSE, 50);
        evManager.setEv(pokemon, Stat.SPEED, 40);

        // Phase 3: overwrite HP down
        evManager.setEv(pokemon, Stat.HP, 100);

        // Phase 4: fill SpAtk into the freed room
        evManager.setEv(pokemon, Stat.SPECIAL_ATTACK, 120);

        // Final assertions
        int manualSum = EvManager.getEv(pokemon, Stat.HP)
                + EvManager.getEv(pokemon, Stat.ATTACK)
                + EvManager.getEv(pokemon, Stat.DEFENSE)
                + EvManager.getEv(pokemon, Stat.SPECIAL_ATTACK)
                + EvManager.getEv(pokemon, Stat.SPECIAL_DEFENSE)
                + EvManager.getEv(pokemon, Stat.SPEED);

        assertEquals(manualSum, EvManager.getTotalEv(pokemon),
                "evTotal must match the manual sum of all six EVs");
        assertTrue(EvManager.getTotalEv(pokemon) <= 510,
                "evTotal must never exceed 510");
        assertTrue(EvManager.getEv(pokemon, Stat.HP) <= 252, "HP EV must not exceed 252");
        assertTrue(EvManager.getEv(pokemon, Stat.ATTACK) <= 252, "Attack EV must not exceed 252");
        assertTrue(EvManager.getEv(pokemon, Stat.DEFENSE) <= 252, "Defense EV must not exceed 252");
        assertTrue(EvManager.getEv(pokemon, Stat.SPECIAL_ATTACK) <= 252, "SpAtk EV must not exceed 252");
        assertTrue(EvManager.getEv(pokemon, Stat.SPECIAL_DEFENSE) <= 252, "SpDef EV must not exceed 252");
        assertTrue(EvManager.getEv(pokemon, Stat.SPEED) <= 252, "Speed EV must not exceed 252");
    }
}
