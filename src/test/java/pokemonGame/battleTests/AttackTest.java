package pokemonGame.battleTests;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.*;
import java.util.Random;

import pokemonGame.model.Pokemon;
import pokemonGame.species.Abra;
import pokemonGame.model.Move;
import pokemonGame.moves.Swift;
import pokemonGame.moves.Thunder;
import pokemonGame.moves.Tackle;
import pokemonGame.moves.Growl;
import pokemonGame.battle.Attack;
import pokemonGame.core.TypeChart.Type;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Unit tests for the {@link Attack} class.
 * 
 * Test coverage includes:
 * - Accuracy checks for moves with different accuracy values.
 * - Random number generation for accuracy and critical hits.
 * - Critical hit calculations based on speed differences.
 * - Damage calculations based on levels, stats, move power, and type effectiveness.
 * - Edge cases such as status moves, zero power moves, and null/none types.
 * 
 * Note: Some tests rely on seeding the RNG to ensure deterministic outcomes 
 * for accuracy and critical hit tests. This allows us to verify that moves with
 * specific accuracy values hit or miss as expected, and that critical hits occur 
 * at the correct rates based on speed differences.
 */

class AttackTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(AttackTest.class);

    @BeforeEach
    void setUp() {
        // Seed the RNG so damage calculations, accuracy, and crits are deterministic.
        // This eliminates flaky tests caused by random crit hits shifting damage
        // outside the expected range.
        Attack.setRng(new Random(42));
    }

    @AfterEach
    void tearDown() {
        // Restore default (non-seeded) RNG after each test so tests don't leak state.
        Attack.setRng(new Random());
    }

    // =========================================================================
    // --- Accuracy Tests ---
    // =========================================================================

    /*
     * CHECKS:  Moves with perfect accuracy always hit, even with accuracy modifiers.
     * HOW:     Uses a move with perfect accuracy and asserts it always hits.
     * IDEAL:   Perfect accuracy moves never miss.
     */
    @Test
    void checkAccuracy_PerfectAccuracy() {
        Pokemon attacker = new Abra("Attacker");
        Pokemon defender = new Abra("Defender");
        attacker.setLevel(50);
        defender.setLevel(50);
        Move move = new Swift();
        assertTrue(Attack.checkAccuracy(attacker, defender, move));
    }

    /*
     * CHECKS:  Moves with low accuracy hit approximately the expected number of times.
     * HOW:     Uses a move with 70% accuracy and counts hits over 1000 attempts.
     * IDEAL:   Low accuracy moves hit roughly according to their accuracy percentage.
     */
    @Test
    void checkAccuracy_LowAccuracy() {
        Pokemon attacker = new Abra("Attacker");
        Pokemon defender = new Abra("Defender");
        attacker.setLevel(50);
        defender.setLevel(50);
        Move move = new Thunder(); // 70% accuracy
        int hitCount = 0;
        for (int i = 0; i < 1000; i++) {
            if (Attack.checkAccuracy(attacker, defender, move)) {
                hitCount++;
            }
        }
        LOGGER.info("Move with 70% accuracy hit {} out of 1000 attempts.", hitCount);
        assertTrue(hitCount > 600 && hitCount < 800, "Move with 70% accuracy should hit at least once in 1000 attempts (Ideally around 700 hits).");
    }

    /*
    * CHECKS:  Moves with 100% accuracy should hit every time, if no accuracy modifiers are applied.
    * HOW:     Uses a move with 100% accuracy and counts hits over 1000 attempts.
    * IDEAL:   Perfect accuracy moves never miss, so it should hit all 1000 attempts.
    */
    @Test
    void checkAccuracy_HighAccuracy() {
        Pokemon attacker = new Abra("Attacker");
        Pokemon defender = new Abra("Defender");
        attacker.setLevel(50);
        defender.setLevel(50);
        Move move = new Tackle(); // 100% accuracy
        int hitCount = 0;
        for (int i = 0; i < 1000; i++) {
            if (Attack.checkAccuracy(attacker, defender, move)) {
                hitCount++;
            }
        }
        LOGGER.info("Move with 100% accuracy hit {} out of 1000 attempts.", hitCount);
        assertEquals(1000, hitCount, "Move with 100% accuracy should hit all 1000 attempts.");
    }

    // =========================================================================
    // --- Randomness Tests ---
    // =========================================================================

    /*
     * CHECKS:  Random integers are generated within the specified range.
     * HOW:     Calls randomInt(min, max) 1000 times and asserts each value is within the range.
     * IDEAL:   All generated values should be between min and max, inclusive.
     */
    @Test
    void randomInt_WithinRange() {
        int min = 1;
        int max = 10;
        for (int i = 0; i < 1000; i++) {
            int randomValue = Attack.randomInt(min, max);
            assertTrue(randomValue >= min && randomValue <= max, "Random value should be between " + min + " and " + max);
        }
    }

    /*
     * CHECKS:  Random integers are generated correctly across different ranges.
     * HOW:     Calls randomInt(min, max) 1000 times for each range and asserts each value is within the range.
     * IDEAL:   All generated values should be between min and max, inclusive.
     */
    @Test
    void randomInt_DifferentRanges() {
        int[][] ranges = {{1, 10}, {0, 100}, {-50, 50}, {100, 200}};
        for (int[] range : ranges) {
            int min = range[0];
            int max = range[1];
            for (int i = 0; i < 1000; i++) {
                int randomValue = Attack.randomInt(min, max);
                assertTrue(randomValue >= min && randomValue <= max, "Random value should be between " + min + " and " + max);
            }
        }
    }

    /*
     * CHECKS:  Random integers are generated with a uniform distribution.
     * HOW:     Calls randomInt(min, max) 10000 times and counts occurrences of each value.
     * IDEAL:   Each number in the range should appear approximately equally.
     */
    @Test
    void randomInt_IsRandom() {
        int min = 1;
        int max = 10;
        int[] counts = new int[max - min + 1];
        for (int i = 0; i < 10000; i++) {
            int randomValue = Attack.randomInt(min, max);
            counts[randomValue - min]++;
        }
        LOGGER.info("Random value distribution for range {}-{}: {}", min, max, counts);
        for (int count : counts) {
            assertTrue(count > 800 && count < 1200, "Each number in the range should appear approximately equally in 10000 attempts.");
        }
    }

    // =========================================================================
    // --- Critical Hit Tests ---
    // =========================================================================

    /*
     * CHECKS:  Critical hit calculation returns a boolean value.
     * HOW:     Calls calculateCriticalHit(attacker, defender) and asserts the result is a boolean.
     * IDEAL:   The result should always be true or false.
     */
    @Test
    void criticalHitReturnsBoolean() {
        Pokemon attacker = new Abra("Attacker");
        Pokemon defender = new Abra("Defender");
        attacker.setLevel(50);
        defender.setLevel(50);
        boolean isCritical = Attack.calculateCriticalHit(attacker, defender);
        assertTrue(isCritical == true || isCritical == false, "criticalHit should return a boolean value.");
    }

    /*
     * CHECKS:  Critical hit chance is at the base rate when there is no speed difference.
     * HOW:     Calls calculateCriticalHit(attacker, defender) 10000 times with equal speed and counts critical hits.
     * IDEAL:   The number of critical hits should be around the base rate (4%).
     */
    @Test
    void criticalHitChanceIsBaseAtNoDifference() {
        Pokemon attacker = new Abra("Attacker");
        Pokemon defender = new Abra("Defender");
        attacker.setLevel(50);
        defender.setLevel(50);
        attacker.setCurrentSpeed(100);
        defender.setCurrentSpeed(100);
        int critCount = 0;
        for (int i = 0; i < 10000; i++) {
            if (Attack.calculateCriticalHit(attacker, defender)) {
                critCount++;
            }
        }
        LOGGER.info("Critical hit count with no speed difference: {}", critCount);
        assertTrue(critCount > 300 && critCount < 500, "With no speed difference, critical hits should occur at the base rate of around 4% (Ideally around 400 hits in 10000 attempts).");
    }

    /*
     * CHECKS:  Critical hit chance scales with speed difference.
     * HOW:     Calls calculateCriticalHit(attacker, defender) 10000 times with a high speed difference and counts critical hits.
     * IDEAL:   The number of critical hits should be higher than the base rate, 
     *          and should match the expected rate based on the speed difference (e.g., around 12% for a 100 speed difference).
     */
    @Test
    void criticalHitChanceScalesWithSpeedDifference() {
        Pokemon fastAttacker = new Abra("Fast Attacker");
        Pokemon slowDefender = new Abra("Slow Defender");
        fastAttacker.setLevel(50);
        slowDefender.setLevel(50);
        fastAttacker.setCurrentSpeed(150);
        slowDefender.setCurrentSpeed(50);
        int critCount = 0;
        for (int i = 0; i < 10000; i++) {
            if (Attack.calculateCriticalHit(fastAttacker, slowDefender)) {
                critCount++;
            }
        }
        LOGGER.info("Critical hit count with high speed difference: {}", critCount);
        assertTrue(critCount > 1100 && critCount < 1400, "There should be some critical hits with a high speed difference.");
    }

    /*
     * CHECKS:  Critical hit chance is capped at a maximum value.
     * HOW:     Calls calculateCriticalHit(attacker, defender) 10000 times with an extremely high speed difference and counts critical hits.
     * IDEAL:   The number of critical hits should not exceed the cap (e.g., 20%).
     */
    @Test
    void criticalHitChanceIsCapped() {
        Pokemon veryFastAttacker = new Abra("Very Fast Attacker");
        Pokemon verySlowDefender = new Abra("Very Slow Defender");
        veryFastAttacker.setLevel(50);
        verySlowDefender.setLevel(50);
        veryFastAttacker.setCurrentSpeed(300);
        verySlowDefender.setCurrentSpeed(10);
        int critCount = 0;
        for (int i = 0; i < 10000; i++) {
            if (Attack.calculateCriticalHit(veryFastAttacker, verySlowDefender)) {
                critCount++;
            }
        }
        LOGGER.info("Critical hit count with very high speed difference: {}", critCount);
        assertTrue(critCount > 1900 && critCount < 2100, "Critical hit chance should be capped at 20%.");
    }

    // =========================================================================
    // --- Calculate Damage Tests ---
    // =========================================================================

    /*
     * CHECKS:  Status moves do not deal damage.
     * HOW:     Calls calculateDamage(attacker, defender, move) with a status move.
     * IDEAL:   The damage should be 0.
     */
    @Test
    void checkForStatusMove() {
        Pokemon attacker = new Abra("Attacker");
        Pokemon defender = new Abra("Defender");
        attacker.setLevel(50);
        defender.setLevel(50);
        Move move = new Growl(); // Status move with 0 power
        int damage = Attack.calculateDamage(attacker, defender, move, false);
        assertEquals(0, damage, "Status moves should not deal damage.");
    }

    /*
     * CHECKS:  Damage dealt should be non-negative.
     * HOW:     Calls calculateDamage(attacker, defender, move) many times with varied stats
     *          and different moves to ensure damage never goes negative, even in edge cases.
     * IDEAL:   The damage should always be >= 0, regardless of stat combinations or move used.
     */
    @Test
    void calculateDamageReturnsNonNegative() {
        Pokemon attacker = new Abra("Attacker");
        Pokemon defender = new Abra("Defender");
        Move physicalMove = new Tackle();
        Move specialMove = new Thunder();
        Move statusMove = new Growl();

        // Test across a variety of level, attack, and defense combinations
        // to catch any edge case where the damage formula might underflow.
        int[][] statCombinations = {
            // {attackerLevel, defenderLevel, attackStat, defenseStat}
            {1, 100, 1, 255},    // Weakest attacker vs strongest defender
            {5, 5, 10, 10},      // Low-level mirror match
            {50, 50, 100, 100},  // Standard mid-game scenario
            {100, 1, 255, 1},    // Strongest attacker vs weakest defender
            {1, 1, 1, 1},        // Absolute minimums
        };

        for (int[] stats : statCombinations) {
            attacker.setLevel(stats[0]);
            defender.setLevel(stats[1]);
            attacker.setCurrentAttack(stats[2]);
            attacker.setCurrentSpecialAttack(stats[2]);
            defender.setCurrentDefense(stats[3]);
            defender.setCurrentSpecialDefense(stats[3]);

            for (Move move : new Move[]{physicalMove, specialMove, statusMove}) {
                // Run multiple times per combination to account for random factor variance
                for (int i = 0; i < 100; i++) {
                    int damage = Attack.calculateDamage(attacker, defender, move, false);
                    assertTrue(damage >= 0,
                        String.format("Damage should be non-negative for level %d attacker (atk=%d) vs level %d defender (def=%d) using %s, but got %d.",
                            stats[0], stats[2], stats[1], stats[3], move.getMoveName(), damage));
                }
            }
        }
    }

    /*
      * CHECKS:  Higher level attackers should generally deal more damage than lower level attackers, all else being equal.
      * HOW:     Compares damage from a low-level attacker and a high-level attacker using the same move and stats.
      * IDEAL:   The higher level attacker should consistently deal more damage than the lower level attacker.
      */
    @Test
    void higherLevelDealsMoreDamage() {
        Pokemon lowLevelAttacker = new Abra("Low Level Attacker");
        Pokemon highLevelAttacker = new Abra("High Level Attacker");
        Pokemon defender = new Abra("Defender");
        lowLevelAttacker.setLevel(10);
        highLevelAttacker.setLevel(50);
        defender.setLevel(30);
        Move move = new Tackle(); // Physical move with 40 power
        int lowLevelDamage = Attack.calculateDamage(lowLevelAttacker, defender, move, false);
        int highLevelDamage = Attack.calculateDamage(highLevelAttacker, defender, move, false);
        assertTrue(highLevelDamage > lowLevelDamage, "Higher level attacker should deal more damage than lower level attacker.");
    }

    /*
      * CHECKS:  Higher power moves should generally deal more damage than lower power moves, all else being equal.
      * HOW:     Compares damage from a low-power move and a high-power move using the same attacker and defender stats.
      * IDEAL:   The higher power move should consistently deal more damage than the lower power move.
      */
    @Test
    void higherMovePowerDealsMoreDamage() {
        Pokemon attacker = new Abra("Attacker");
        Pokemon defender = new Abra("Defender");
        attacker.setLevel(50);
        defender.setLevel(50);
        Move lowPowerMove = new Tackle(); // Physical move with 40 power
        Move highPowerMove = new Thunder(); // Special move with 110 power  
        int lowPowerDamage = Attack.calculateDamage(attacker, defender, lowPowerMove, false);
        int highPowerDamage = Attack.calculateDamage(attacker, defender, highPowerMove, false);
        assertTrue(highPowerDamage > lowPowerDamage, "Higher power move should deal more damage than lower power move.");
    }

    /*
      * CHECKS:  Higher attack stat should generally deal more damage with physical moves, all else being equal.
      * HOW:     Compares damage from a low-attack attacker and a high-attack attacker using the same move and stats.
      * IDEAL:   The attacker with higher attack stat should consistently deal more damage than the attacker with lower attack stat.
      */
    @Test
    void higherAttackStatDealsMoreDamageWithPhysicalMove() {
        Pokemon lowAttackAttacker = new Abra("Low Attack Attacker");
        Pokemon highAttackAttacker = new Abra("High Attack Attacker");
        Pokemon defender = new Abra("Defender");
        lowAttackAttacker.setLevel(50);
        highAttackAttacker.setLevel(50);
        defender.setLevel(50);
        lowAttackAttacker.setCurrentAttack(50);
        highAttackAttacker.setCurrentAttack(150);
        defender.setCurrentDefense(100);
        Move move = new Tackle(); // Physical move with 40 power
        int lowAttackDamage = Attack.calculateDamage(lowAttackAttacker, defender, move, false);
        int highAttackDamage = Attack.calculateDamage(highAttackAttacker, defender, move, false);
        assertTrue(highAttackDamage > lowAttackDamage, "Attacker with higher attack stat should deal more damage than attacker with lower attack stat.");
    }

    /*
      * CHECKS:  Higher defense stat should generally reduce damage from physical moves, all else being equal.
      * HOW:     Compares damage from a low-defense defender and a high-defense defender using the same move and attacker stats.
      * IDEAL:   The defender with higher defense stat should consistently take less damage than the defender with lower defense stat.
      */
    @Test
    void higherDefenseStatReducesDamageWithPhysicalMove() {
        Pokemon attacker = new Abra("Attacker");
        Pokemon lowDefenseDefender = new Abra("Low Defense Defender");
        Pokemon highDefenseDefender = new Abra("High Defense Defender");
        attacker.setLevel(50);
        lowDefenseDefender.setLevel(50);
        highDefenseDefender.setLevel(50);
        attacker.setCurrentAttack(100);
        lowDefenseDefender.setCurrentDefense(50);
        highDefenseDefender.setCurrentDefense(150);
        Move move = new Tackle(); // Physical move with 40 power
        int lowDefenseDamage = Attack.calculateDamage(attacker, lowDefenseDefender, move, false);
        int highDefenseDamage = Attack.calculateDamage(attacker, highDefenseDefender, move, false);
        assertTrue(lowDefenseDamage > highDefenseDamage, "Defender with higher defense stat should take less damage than defender with lower defense stat.");
    }

    /*
      * CHECKS:  Higher special attack stat should generally deal more damage with special moves, all else being equal.
      * HOW:     Compares damage from a low-special-attack attacker and a high-special-attack attacker using the same move and stats.
      * IDEAL:   The attacker with higher special attack stat should consistently deal more damage than the attacker with lower special attack stat when using a special move.
      */
    @Test
    void higherSpecialAttackStatDealsMoreDamageWithSpecialMove() {
        Pokemon lowSpAttackAttacker = new Abra("Low Sp. Attack Attacker");
        Pokemon highSpAttackAttacker = new Abra("High Sp. Attack Attacker");
        Pokemon defender = new Abra("Defender");
        lowSpAttackAttacker.setLevel(50);
        highSpAttackAttacker.setLevel(50);
        defender.setLevel(50);
        lowSpAttackAttacker.setCurrentSpecialAttack(50);
        highSpAttackAttacker.setCurrentSpecialAttack(150);
        defender.setCurrentSpecialDefense(100);
        Move move = new Thunder(); // Special move with 110 power  
        int lowSpAttackDamage = Attack.calculateDamage(lowSpAttackAttacker, defender, move, false);
        int highSpAttackDamage = Attack.calculateDamage(highSpAttackAttacker, defender, move, false);
        assertTrue(highSpAttackDamage > lowSpAttackDamage, "Attacker with higher special attack stat should deal more damage than attacker with lower special attack stat when using a special move.");
    }

    /*
      * CHECKS:  Higher special defense stat should generally reduce damage from special moves, all else being equal.
      * HOW:     Compares damage from a low-special-defense defender and a high-special-defense defender using the same move and attacker stats.
      * IDEAL:   The defender with higher special defense stat should consistently take less damage than the defender with lower special defense stat when attacked with a special move.
      */
    @Test
    void higherSpecialDefenseStatReducesDamageWithSpecialMove() {
        Pokemon attacker = new Abra("Attacker");
        Pokemon lowSpDefenseDefender = new Abra("Low Sp. Defense Defender");
        Pokemon highSpDefenseDefender = new Abra("High Sp. Defense Defender");
        attacker.setLevel(50);
        lowSpDefenseDefender.setLevel(50);
        highSpDefenseDefender.setLevel(50);
        attacker.setCurrentSpecialAttack(100);
        lowSpDefenseDefender.setCurrentSpecialDefense(50);
        highSpDefenseDefender.setCurrentSpecialDefense(150);
        Move move = new Thunder(); // Special move with 110 power 
        int lowSpDefenseDamage = Attack.calculateDamage(attacker, lowSpDefenseDefender, move, false);
        int highSpDefenseDamage = Attack.calculateDamage(attacker, highSpDefenseDefender, move, false);
        assertTrue(lowSpDefenseDamage > highSpDefenseDamage, "Defender with higher special defense stat should take less damage than defender with lower special defense stat when attacked with a special move.");
    }

    /*
      * CHECKS:  Special attack stat should not affect damage from physical moves, all else being equal.
      * HOW:     Compares damage from a low-special-attack attacker and a high-special-attack attacker using the same physical move and stats.
      * IDEAL:   The damage should be within the expected range regardless of the attacker's special attack stat.
      */
    @Test
    void specialAttackStatDoesNotAffectPhysicalMoveDamage() {
        Pokemon lowSpAttackAttacker = new Abra("Low Sp. Attack Attacker");
        Pokemon highSpAttackAttacker = new Abra("High Sp. Attack Attacker");
        Pokemon defender = new Abra("Defender");
        lowSpAttackAttacker.setLevel(50);
        highSpAttackAttacker.setLevel(50);
        defender.setLevel(50);
        lowSpAttackAttacker.setCurrentSpecialAttack(50);
        lowSpAttackAttacker.setCurrentAttack(150);
        highSpAttackAttacker.setCurrentSpecialAttack(150);
        highSpAttackAttacker.setCurrentAttack(150);
        defender.setCurrentDefense(100);
        Move move = new Tackle(); // Physical move with 40 power
        int lowSpAttackDamage = Attack.calculateDamage(lowSpAttackAttacker, defender, move, false); // should be within 23-28
        int highSpAttackDamage = Attack.calculateDamage(highSpAttackAttacker, defender, move, false); //should be within 23-28
        assertTrue(lowSpAttackDamage >= 23 && lowSpAttackDamage <= 28, "Damage should be within expected range for low special attack attacker.");
        assertTrue(highSpAttackDamage >= 23 && highSpAttackDamage <= 28, "Damage should be within expected range for high special attack attacker.");
    }

    /*
      * CHECKS:  Attack stat should not affect damage from special moves, all else being equal.
      * HOW:     Compares damage from a low-attack attacker and a high-attack attacker using the same special move and stats.
      * IDEAL:   The damage should be within the expected range regardless of the attacker's attack stat.
      */
    @Test
    void attackStatDoesNotAffectSpecialMoveDamage() {
        Pokemon lowAttackAttacker = new Abra("Low Attack Attacker");
        Pokemon highAttackAttacker = new Abra("High Attack Attacker");
        Pokemon defender = new Abra("Defender");
        lowAttackAttacker.setLevel(50);
        highAttackAttacker.setLevel(50);
        defender.setLevel(50);
        lowAttackAttacker.setCurrentAttack(50);
        lowAttackAttacker.setCurrentSpecialAttack(150);
        highAttackAttacker.setCurrentAttack(150);
        highAttackAttacker.setCurrentSpecialAttack(150);
        defender.setCurrentSpecialDefense(100);
        Move move = new Thunder(); // Special move with 110 power 
        int lowAttackDamage = Attack.calculateDamage(lowAttackAttacker, defender, move, false); // should be within 68-81
        int highAttackDamage = Attack.calculateDamage(highAttackAttacker, defender, move, false); // should be within 68-81
        assertTrue(lowAttackDamage >= 68 && lowAttackDamage <= 81, "Damage should be within expected range for low attack attacker.");
        assertTrue(highAttackDamage >= 68 && highAttackDamage <= 81, "Damage should be within expected range for high attack attacker.");
    }

    /*
      * CHECKS:  Higher defense stat should generally not affect special move damage, all else being equal.
      * HOW:     Compares damage from a low-defense defender and a high-defense defender using the same move and attacker stats.
      * IDEAL:   The defender with higher defense stat should consistently take similar damage to the defender with lower defense stat.
      */
    @Test
    void defenseStatDoesNotReduceDamageOfSpecialMoves() {
        Pokemon attacker = new Abra("Attacker");
        Pokemon lowDefenseDefender = new Abra("Low Defense Defender");
        Pokemon highDefenseDefender = new Abra("High Defense Defender");
        attacker.setLevel(50);
        lowDefenseDefender.setLevel(50);
        highDefenseDefender.setLevel(50);
        attacker.setCurrentSpecialAttack(100);
        lowDefenseDefender.setCurrentDefense(50);
        lowDefenseDefender.setCurrentSpecialDefense(100);
        highDefenseDefender.setCurrentDefense(150);
        highDefenseDefender.setCurrentSpecialDefense(100);
        Move move = new Thunder(); // Special move with 110 power 
        int lowDefenseDamage = Attack.calculateDamage(attacker, lowDefenseDefender, move, false); // should be within 45-54
        int highDefenseDamage = Attack.calculateDamage(attacker, highDefenseDefender, move, false); // should be within 45-54
        assertTrue(lowDefenseDamage >= 45 && lowDefenseDamage <= 54, "Damage should be within expected range for low defense defender.");
        assertTrue(highDefenseDamage >= 45 && highDefenseDamage <= 54, "Damage should be within expected range for high defense defender.");
    }

    /*
      * CHECKS:  Higher special defense stat should generally not affect physical move damage, all else being equal.
      * HOW:     Compares damage from a low-special-defense defender and a high-special-defense defender using the same physical move and attacker stats.
      * IDEAL:   The defender with higher special defense stat should consistently take similar damage to the defender with lower special defense stat when attacked with a physical move.
      */
    @Test
    void specialDefenseStatDoesNotReduceDamageOfPhysicalMoves() {
        Pokemon attacker = new Abra("Attacker");
        Pokemon lowSpDefenseDefender = new Abra("Low Sp. Defense Defender");
        Pokemon highSpDefenseDefender = new Abra("High Sp. Defense Defender");
        attacker.setLevel(50);
        lowSpDefenseDefender.setLevel(50);
        highSpDefenseDefender.setLevel(50);
        attacker.setCurrentAttack(100);
        lowSpDefenseDefender.setCurrentDefense(100);
        lowSpDefenseDefender.setCurrentSpecialDefense(50);
        highSpDefenseDefender.setCurrentDefense(100);
        highSpDefenseDefender.setCurrentSpecialDefense(150);
        Move move = new Tackle(); // Physical move with 40 power
        int lowSpDefenseDamage = Attack.calculateDamage(attacker, lowSpDefenseDefender, move, false); // should be within 16-19
        int highSpDefenseDamage = Attack.calculateDamage(attacker, highSpDefenseDefender, move, false); // should be within 16-19
        assertTrue(lowSpDefenseDamage >= 16 && lowSpDefenseDamage <= 19, "Damage should be within expected range for low special defense defender.");
        assertTrue(highSpDefenseDamage >= 16 && highSpDefenseDamage <= 19, "Damage should be within expected range for high special defense defender.");
    }

    // =========================================================================
    // --- Effectiveness Type Check Tests ---
    // =========================================================================

    /*
      * CHECKS:  If the defender's secondary type is null, the combined effectiveness should equal the primary effectiveness.
      * HOW:     Sets the defender's secondary type to null and calculates the combined effectiveness of a move.
      * IDEAL:   The combined effectiveness should equal the primary effectiveness.
      */
    @Test
    void calculateEffectivenessReturnsOneOnNullType() {
        Pokemon defender = new Abra("Defender"); // Abra has a single type: Psychic
        defender.setTypeSecondary(null);
        Move move = new Tackle(); // Normal type move
        float primaryEffectiveness = Attack.calculateEffectiveness(defender.getTypePrimary(), move);
        float secondaryEffectiveness = Attack.calculateEffectiveness(defender.getTypeSecondary(), move);
        float combinedEffectiveness = primaryEffectiveness * secondaryEffectiveness;
        assertEquals(combinedEffectiveness, primaryEffectiveness, "If secondary type is null, combined effectiveness should equal primary effectiveness.");
    }

    /*
      * CHECKS:  If the defender's secondary type is NONE, the combined effectiveness should equal the primary effectiveness.
      * HOW:     Sets the defender's secondary type to NONE and calculates the combined effectiveness of a move.
      * IDEAL:   The combined effectiveness should equal the primary effectiveness.
      */
    @Test
    void calculateEffectivenessReturnsOneOnNoneType() {
        Pokemon defender = new Abra("Defender"); // Abra has a single type: Psychic
        defender.setTypeSecondary(Type.NONE);
        Move move = new Tackle(); // Normal type move
        float primaryEffectiveness = Attack.calculateEffectiveness(defender.getTypePrimary(), move);
        float secondaryEffectiveness = Attack.calculateEffectiveness(defender.getTypeSecondary(), move);
        float combinedEffectiveness = primaryEffectiveness * secondaryEffectiveness;
        assertEquals(combinedEffectiveness, primaryEffectiveness, "If secondary type is NONE, combined effectiveness should equal primary effectiveness.");
    }
}
