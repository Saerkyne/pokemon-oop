package pokemonGame;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

import pokemonGame.mons.Abra;
import pokemonGame.moves.Psychic;
import pokemonGame.moves.MegaPunch;

class AttackTest {

    private Attack attack;

    @BeforeEach
    void setUp() {
        attack = new Attack();
    }

    // --- Effectiveness ---

    @Test
    void superEffectiveReturns2() {
        // Fire → Grass = 2.0
        Move psychic = new Psychic(); // Psychic type
        // Psychic vs Poison should be super effective
        float eff = attack.calculateEffectiveness("Poison", psychic);
        assertEquals(2.0f, eff);
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
        attacker.addMove(new Psychic());

        int damage = attack.calculateDamage(attacker, defender, new Psychic());
        assertTrue(damage >= 0, "Damage should never be negative");
    }

    @Test
    void higherLevelDealMoreDamage() {
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

    // --- Random int helper ---

    @Test
    void randomIntWithinBounds() {
        for (int i = 0; i < 100; i++) {
            int val = attack.randomInt(1, 10);
            assertTrue(val >= 1 && val <= 10,
                    "randomInt(1, 10) returned " + val + ", expected 1-10");
        }
    }

    // --- Critical hits ---

    @Test
    void criticalHitReturnsBooleanWithoutError() {
        Pokemon attacker = new Abra("Attacker");
        Pokemon defender = new Abra("Defender");
        attacker.setLevel(50);
        defender.setLevel(50);
        // Just verify it doesn't throw — result is random
        boolean crit = attack.calculateCriticalHit(attacker, defender);
        assertTrue(crit || !crit); // always true, just checking no exception
    }
}
