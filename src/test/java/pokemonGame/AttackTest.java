package pokemonGame;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

import pokemonGame.mons.Abra;
import pokemonGame.mons.Bulbasaur;
import pokemonGame.mons.Electrode;
import pokemonGame.mons.Slowbro;
import pokemonGame.moves.Psychic;
import pokemonGame.moves.MegaPunch;
import pokemonGame.moves.Flamethrower;

class AttackTest {

    private Attack attack;

    @BeforeEach
    void setUp() {
        attack = new Attack();
    }

    // --- Effectiveness ---

    /*
     * CHECKS:  calculateEffectiveness() returns 2.0f for a super-effective matchup
     *          (Psychic type move against a Poison-type defender).
     * HOW:     Instantiates a Psychic move, passes "Poison" as the defending type, and
     *          asserts the result equals 2.0f exactly.
     * IMPROVE: Parameterize with multiple super-effective pairs (e.g., Water→Fire,
     *          Electric→Water) to confirm the whole type chart is correctly populated,
     *          not just this single entry.
     */
    @Test
    void superEffectiveReturns2() {
        Move psychic = new Psychic(); // Psychic type
        // Psychic vs Poison should be super effective
        float eff = attack.calculateEffectiveness("Poison", psychic);
        assertEquals(2.0f, eff);
    }

    /*
     * CHECKS:  calculateEffectiveness() returns 0.5f for a resisted matchup
     *          (Fire move against a Water-type defender).
     * HOW:     Instantiates a Flamethrower, passes "Water" as the defending type, and
     *          asserts the result equals 0.5f exactly.
     * IMPROVE: Parameterize with multiple not-very-effective pairs; also assert the
     *          inverse matchup (Water→Fire = 2.0) to confirm type asymmetry is not
     *          accidentally collapsed in the chart lookup.
     */
    @Test
    void notVeryEffectiveReturnsHalf() {
        // Fire vs Water = 0.5
        Move flamethrower = new Flamethrower();
        float eff = attack.calculateEffectiveness("Water", flamethrower);
        assertEquals(0.5f, eff);
    }

    /*
     * CHECKS:  calculateEffectiveness() returns 0.0f when the attacking type is
     *          completely immune to the defending type (Normal vs Ghost).
     * HOW:     Instantiates a MegaPunch (Normal type), passes "Ghost" as the defending
     *          type, and asserts the result equals 0.0f exactly.
     * IMPROVE: Test additional immunities (Electric→Ground, Ground→Flying, Psychic→Dark)
     *          to increase confidence that immunity handling works broadly across the
     *          chart, not just for this single pair.
     */
    @Test
    void immunityReturnsZero() {
        // Normal vs Ghost = 0.0
        Move megaPunch = new MegaPunch();
        float eff = attack.calculateEffectiveness("Ghost", megaPunch);
        assertEquals(0.0f, eff);
    }

    /*
     * CHECKS:  calculateEffectiveness() returns 1.0f for a neutral matchup where the
     *          attacking type has no special relationship with the defending type
     *          (Normal vs Water).
     * HOW:     Instantiates a MegaPunch (Normal type), passes "Water" as the defending
     *          type, and asserts the result equals 1.0f exactly.
     * IMPROVE: Also verify that an unknown or unrecognized type string returns 1.0 as
     *          the default fallback, strengthening boundary-value coverage.
     */
    @Test
    void neutralEffectivenessReturns1() {
        Move megaPunch = new MegaPunch(); // Normal type
        float eff = attack.calculateEffectiveness("Water", megaPunch);
        assertEquals(1.0f, eff);
    }

    /*
     * CHECKS:  calculateEffectiveness() handles a null defending type gracefully,
     *          returning 1.0f (neutral) instead of throwing a NullPointerException.
     * HOW:     Passes null as the defender type alongside a Psychic move and asserts
     *          the result is 1.0f.
     * IMPROVE: Consider whether null is a valid state at all. If the domain guarantees
     *          types are never null, the contract could be tightened to assert that an
     *          exception is thrown, making the null-handling policy explicit.
     */
    @Test
    void nullDefenderTypeReturnsNeutral() {
        Move psychic = new Psychic();
        float eff = attack.calculateEffectiveness(null, psychic);
        assertEquals(1.0f, eff,
                "Null defender type should be treated as neutral (1.0)");
    }

    /*
     * CHECKS:  calculateEffectiveness() treats the sentinel string "None" (used for a
     *          missing secondary type) as neutral, returning 1.0f.
     * HOW:     Passes "None" as the defender type and asserts the result is 1.0f.
     * IMPROVE: Define "None" as a named constant rather than a magic string so this
     *          test is not fragile to renaming. Also verify that other edge-case
     *          strings (empty string, whitespace) behave consistently.
     */
    @Test
    void noneDefenderTypeReturnsNeutral() {
        Move psychic = new Psychic();
        float eff = attack.calculateEffectiveness("None", psychic);
        assertEquals(1.0f, eff,
                "\"None\" defender type should be treated as neutral (1.0)");
    }

    // --- Damage calculation ---

    /*
     * CHECKS:  calculateDamage() never returns a negative value, even for a single
     *          call between equal-level opponents.
     * HOW:     Creates two level-50 Abra instances, calls calculateDamage once with a
     *          Psychic move, and asserts the result is >= 0.
     * IMPROVE: A single trial may theoretically produce 0 (e.g., if a miss mechanic
     *          is added). Running multiple trials and asserting all results are >= 0
     *          would give stronger assurance. Also test a high-defense target to
     *          confirm no integer underflow occurs in the formula.
     */
    @Test
    void damageIsNonNegative() {
        Pokemon attacker = new Abra("Attacker");
        Pokemon defender = new Abra("Defender");
        attacker.setLevel(50);
        defender.setLevel(50);

        int damage = attack.calculateDamage(attacker, defender, new Psychic());
        assertTrue(damage >= 0, "Damage should never be negative");
    }

    /*
     * CHECKS:  A higher-level attacker deals more average damage than a lower-level
     *          attacker against the same defender using the same move.
     * HOW:     Runs 50 trials each for a level-10 and a level-80 Abra, sums total
     *          damage for each, and asserts the high-level total exceeds the low-level
     *          total.
     * IMPROVE: 50 trials may occasionally fail due to the random damage roll (statistically
     *          flaky). Increasing to 500+ trials or seeding the RNG (if supported) makes
     *          the test deterministic and eliminates false negatives.
     */
    @Test
    void higherLevelDealsMoreDamage() {
        Pokemon lowLevel = new Abra("Low");
        Pokemon highLevel = new Abra("High");
        Pokemon defender = new Abra("Defender");
        defender.setLevel(50);
        lowLevel.setLevel(10);
        highLevel.setLevel(80);

        Move psychic = new Psychic();

        // Run multiple times to account for random factor, check averages
        int lowTotal = 0;
        int highTotal = 0;
        int trials = 50;
        for (int i = 0; i < trials; i++) {
            lowTotal += attack.calculateDamage(lowLevel, defender, psychic);
            highTotal += attack.calculateDamage(highLevel, defender, psychic);
        }
        assertTrue(highTotal > lowTotal,
                "Higher level Pokémon should deal more damage on average");
    }

    /*
     * CHECKS:  STAB (Same-Type Attack Bonus) increases average damage when the
     *          attacker's type matches the move's type (Abra + Psychic = STAB)
     *          compared to a non-STAB attacker of equal base Special Attack.
     * HOW:     Normalizes Bulbasaur's base SpAtk to match Abra's and applies the same
     *          nature, then runs 100 trials each and asserts the STAB total is higher.
     * IMPROVE: Add an assertion before the loop confirming both Pokémon have identical
     *          getCurrentSpecialAttack() values, making the stat-normalization
     *          precondition explicit and catching setup bugs immediately.
     */
    @Test
    void stabIncreasesTotalDamage() {
        // Abra is Psychic type; Psychic is a Psychic-type move → STAB applies
        Pokemon abraAttacker = new Abra("STAB Attacker");
        // Bulbasaur is Grass/Poison type; Psychic is not Grass or Poison → no STAB
        Pokemon bulbaAttacker = new Bulbasaur("No STAB Attacker");
        Pokemon defender = new Abra("Defender");
        abraAttacker.setLevel(50);
        bulbaAttacker.setLevel(50);
        // Give both the same base special attack so we isolate the STAB effect
        bulbaAttacker.setSpecialAttackBase(abraAttacker.getSpecialAttackBaseStat());
        // Use the same nature to be fair
        bulbaAttacker.setNature(abraAttacker.getNature());
        bulbaAttacker.calculateCurrentStats();
        defender.setLevel(50);

        Move psychic = new Psychic();
        int stabTotal = 0;
        int noStabTotal = 0;
        int trials = 100;
        for (int i = 0; i < trials; i++) {
            stabTotal += attack.calculateDamage(abraAttacker, defender, psychic);
            noStabTotal += attack.calculateDamage(bulbaAttacker, defender, psychic);
        }
        assertTrue(stabTotal > noStabTotal,
                "STAB should increase damage on average (STAB total=" + stabTotal
                + " vs non-STAB total=" + noStabTotal + ")");
    }

    /*
     * CHECKS:  Super-effective hits (Psychic vs Poison/Bulbasaur = 2.0x) deal more
     *          average damage than resisted hits (Psychic vs Psychic/Abra = 0.5x)
     *          from the same attacker.
     * HOW:     Runs 100 trials against a weak target (Bulbasaur) and a resistant target
     *          (Abra), then asserts superEffTotal > resistTotal.
     * IMPROVE: The effectiveness ratio is 4:1 (2.0 vs 0.5). Asserting
     *          superEffTotal > 3 * resistTotal instead of merely > resistTotal would
     *          verify the full magnitude of the difference, catching bugs where the
     *          multiplier is applied partially rather than fully.
     */
    @Test
    void superEffectiveDealMoreDamage() {
        // Psychic vs Poison (Bulbasaur) = 2.0x effective
        // Psychic vs Psychic (Abra) = 0.5x effective
        Pokemon attacker = new Abra("Attacker");
        Pokemon weakDefender = new Bulbasaur("Weak Defender");
        Pokemon resistDefender = new Abra("Resist Defender");
        attacker.setLevel(50);
        weakDefender.setLevel(50);
        resistDefender.setLevel(50);

        Move psychic = new Psychic();
        int superEffTotal = 0;
        int resistTotal = 0;
        int trials = 100;
        for (int i = 0; i < trials; i++) {
            superEffTotal += attack.calculateDamage(attacker, weakDefender, psychic);
            resistTotal += attack.calculateDamage(attacker, resistDefender, psychic);
        }
        assertTrue(superEffTotal > resistTotal,
                "Super-effective hits should deal more damage on average");
    }

    // --- Random int helper ---

    /*
     * CHECKS:  randomInt(min, max) always returns a value within the inclusive range
     *          [min, max].
     * HOW:     Calls randomInt(1, 10) 100 times and asserts each result is >= 1 and
     *          <= 10.
     * IMPROVE: 100 trials do not guarantee full range coverage. A complementary test
     *          should verify that all values in [min, max] are actually reachable,
     *          ensuring the RNG is not accidentally narrowed or biased toward one end.
     *          Also test that randomInt with min > max either throws or is documented.
     */
    @Test
    void randomIntWithinBounds() {
        for (int i = 0; i < 100; i++) {
            int val = attack.randomInt(1, 10);
            assertTrue(val >= 1 && val <= 10,
                    "randomInt(1, 10) returned " + val + ", expected 1-10");
        }
    }

    /*
     * CHECKS:  When min == max, randomInt() always returns that single value (the
     *          degenerate case of a range with exactly one element).
     * HOW:     Calls randomInt(5, 5) 20 times and asserts every result equals 5.
     * IMPROVE: Also test the inverted range (min > max), e.g. randomInt(10, 1), to
     *          confirm the method either throws an IllegalArgumentException or handles
     *          it gracefully rather than silently returning a misleading result.
     */
    @Test
    void randomIntSingleValueRange() {
        // When min == max, should always return that value
        for (int i = 0; i < 20; i++) {
            assertEquals(5, attack.randomInt(5, 5));
        }
    }

    // --- Critical hits ---

    /*
     * CHECKS:  calculateCriticalHit() completes without throwing an exception
     *          (smoke test — the boolean assertion inside is intentionally tautological;
     *          see inline comment for full explanation).
     * HOW:     Calls calculateCriticalHit on two level-50 Abra instances and uses a
     *          tautological assertTrue(crit || !crit) to satisfy JUnit's assertion
     *          requirement while letting the real check be "no exception thrown".
     * IMPROVE: Replace with a deterministic test by mocking or seeding the RNG to
     *          force a crit and then a non-crit, verifying each branch of the crit
     *          logic independently rather than relying on a smoke test.
     */
    @Test
    void criticalHitReturnsBooleanWithoutError() {
        // SMOKE TEST: The real purpose is to verify calculateCriticalHit runs
        // without throwing an exception. The assertion below is intentionally
        // tautological — (crit || !crit) is always true. We use it because
        // JUnit requires at least one assertion, and the meaningful check is
        // "no exception was thrown during execution."
        Pokemon attacker = new Abra("Attacker");
        Pokemon defender = new Abra("Defender");
        attacker.setLevel(50);
        defender.setLevel(50);
        boolean crit = attack.calculateCriticalHit(attacker, defender);
        assertTrue(crit || !crit); // tautology — the real test is no exception above
    }

    /*
     * CHECKS:  A Pokemon with much higher speed (Electrode, 150 base speed) has a
     *          statistically higher critical-hit rate than a slower one (Slowbro,
     *          30 base speed) over many trials.
     * HOW:     Runs 5 000 trials for each direction (fast attacks slow, slow attacks
     *          fast) and asserts the fast attacker's crit count is higher.
     * IMPROVE: 5 000 trials can still produce a close result due to random variance,
     *          making the test occasionally flaky. Increasing to 10 000+ trials or
     *          using a statistical confidence test (Z-test) would reduce flakiness.
     */
    @Test
    void critChanceHigherWhenFaster() {
        // Electrode has 150 base speed, Slowbro has 30 base speed
        // With a big speed advantage, crits should happen more often
        Pokemon fast = new Electrode("Fast");
        Pokemon slow = new Slowbro("Slow");
        fast.setLevel(50);
        slow.setLevel(50);

        int fastCrits = 0;
        int slowCrits = 0;
        int trials = 5000;
        for (int i = 0; i < trials; i++) {
            if (attack.calculateCriticalHit(fast, slow)) fastCrits++;
            if (attack.calculateCriticalHit(slow, fast)) slowCrits++;
        }
        assertTrue(fastCrits > slowCrits,
                "Faster Pokémon should crit more often (fast=" + fastCrits
                + " vs slow=" + slowCrits + " out of " + trials + ")");
    }

    /*
     * CHECKS:  The critical-hit rate never greatly exceeds the 15% formula cap, even
     *          with the maximum possible speed advantage.
     * HOW:     Counts crits over 10 000 trials with a level-100 Electrode vs a level-5
     *          Slowbro, then asserts the observed rate is <= 17% (a small tolerance
     *          above the 15% cap to account for normal statistical variation).
     * IMPROVE: The 17% threshold is somewhat arbitrary. A chi-squared or binomial
     *          confidence-interval test would provide a more statistically rigorous
     *          bound, reducing the risk of both false positives and false negatives.
     */
    @Test
    void critChanceNeverExceedsCap() {
        // The crit formula caps at 15% (1500/10000). Over 10,000 trials, the
        // observed rate should stay near 15%. We allow up to 17% to account
        // for normal statistical fluctuation, but anything higher than that
        // suggests the cap logic is broken.
        // (Previously this threshold was 20%, which was too loose — a 5%
        // margin above a 15% cap could hide real bugs.)
        Pokemon fast = new Electrode("Fast");
        Pokemon slow = new Slowbro("Slow");
        fast.setLevel(100);
        slow.setLevel(5);

        int crits = 0;
        int trials = 10000;
        for (int i = 0; i < trials; i++) {
            if (attack.calculateCriticalHit(fast, slow)) crits++;
        }
        double critRate = (double) crits / trials;
        assertTrue(critRate <= 0.17,
                "Crit rate should not greatly exceed 15% cap, got " + (critRate * 100) + "%");
    }
}
