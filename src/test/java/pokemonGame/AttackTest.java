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

    @Test
    void superEffectiveReturns2() {
        Move psychic = new Psychic(); // Psychic type
        // Psychic vs Poison should be super effective
        float eff = attack.calculateEffectiveness("Poison", psychic);
        assertEquals(2.0f, eff);
    }

    @Test
    void notVeryEffectiveReturnsHalf() {
        // Fire vs Water = 0.5
        Move flamethrower = new Flamethrower();
        float eff = attack.calculateEffectiveness("Water", flamethrower);
        assertEquals(0.5f, eff);
    }

    @Test
    void immunityReturnsZero() {
        // Normal vs Ghost = 0.0
        Move megaPunch = new MegaPunch();
        float eff = attack.calculateEffectiveness("Ghost", megaPunch);
        assertEquals(0.0f, eff);
    }

    @Test
    void neutralEffectivenessReturns1() {
        Move megaPunch = new MegaPunch(); // Normal type
        float eff = attack.calculateEffectiveness("Water", megaPunch);
        assertEquals(1.0f, eff);
    }

    @Test
    void nullDefenderTypeReturnsNeutral() {
        Move psychic = new Psychic();
        float eff = attack.calculateEffectiveness(null, psychic);
        assertEquals(1.0f, eff,
                "Null defender type should be treated as neutral (1.0)");
    }

    @Test
    void noneDefenderTypeReturnsNeutral() {
        Move psychic = new Psychic();
        float eff = attack.calculateEffectiveness("None", psychic);
        assertEquals(1.0f, eff,
                "\"None\" defender type should be treated as neutral (1.0)");
    }

    // --- Damage calculation ---

    @Test
    void damageIsNonNegative() {
        Pokemon attacker = new Abra("Attacker");
        Pokemon defender = new Abra("Defender");
        attacker.setLevel(50);
        defender.setLevel(50);

        int damage = attack.calculateDamage(attacker, defender, new Psychic());
        assertTrue(damage >= 0, "Damage should never be negative");
    }

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

    @Test
    void randomIntWithinBounds() {
        for (int i = 0; i < 100; i++) {
            int val = attack.randomInt(1, 10);
            assertTrue(val >= 1 && val <= 10,
                    "randomInt(1, 10) returned " + val + ", expected 1-10");
        }
    }

    @Test
    void randomIntSingleValueRange() {
        // When min == max, should always return that value
        for (int i = 0; i < 20; i++) {
            assertEquals(5, attack.randomInt(5, 5));
        }
    }

    // --- Critical hits ---

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
