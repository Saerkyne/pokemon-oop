package pokemonGame;

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

    // --- Accuracy Tests ---

    @Test
    void checkAccuracy_PerfectAccuracy() {
        Pokemon attacker = new Abra("Attacker");
        Pokemon defender = new Abra("Defender");
        attacker.setLevel(50);
        defender.setLevel(50);
        Move move = new Swift();
        assertTrue(Attack.checkAccuracy(attacker, defender, move));
    }

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

    // --- Randomness Tests ---

    @Test
    void randomInt_WithinRange() {
        int min = 1;
        int max = 10;
        for (int i = 0; i < 1000; i++) {
            int randomValue = Attack.randomInt(min, max);
            assertTrue(randomValue >= min && randomValue <= max, "Random value should be between " + min + " and " + max);
        }
    }

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

    // --- Critical Hit Tests ---

    @Test
    void criticalHitReturnsBoolean() {
        Pokemon attacker = new Abra("Attacker");
        Pokemon defender = new Abra("Defender");
        attacker.setLevel(50);
        defender.setLevel(50);
        boolean isCritical = Attack.calculateCriticalHit(attacker, defender);
        assertTrue(isCritical == true || isCritical == false, "criticalHit should return a boolean value.");
    }

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
        assertTrue(critCount > 300 && critCount < 500, "With no speed difference, critical hits should occur at the base rate of around 4.17% (Ideally around 417 hits in 10000 attempts).");
    }

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

    // --- Calculate Damage Tests ---

    @Test
    void checkForStatusMove() {
        Pokemon attacker = new Abra("Attacker");
        Pokemon defender = new Abra("Defender");
        attacker.setLevel(50);
        defender.setLevel(50);
        Move move = new Growl(); // Status move with 0 power
        int damage = Attack.calculateDamage(attacker, defender, move);
        assertEquals(0, damage, "Status moves should not deal damage.");
    }

    @Test
    void calculateDamageReturnsNonNegative() {
        Pokemon attacker = new Abra("Attacker");
        Pokemon defender = new Abra("Defender");
        attacker.setLevel(50);
        defender.setLevel(50);
        Move move = new Tackle(); // Physical move with 40 power
        int damage = Attack.calculateDamage(attacker, defender, move);
        assertTrue(damage >= 0, "Damage should be a non-negative value.");
    }

    @Test
    void higherLevelDealsMoreDamage() {
        Pokemon lowLevelAttacker = new Abra("Low Level Attacker");
        Pokemon highLevelAttacker = new Abra("High Level Attacker");
        Pokemon defender = new Abra("Defender");
        lowLevelAttacker.setLevel(10);
        highLevelAttacker.setLevel(50);
        defender.setLevel(30);
        Move move = new Tackle(); // Physical move with 40 power
        int lowLevelDamage = Attack.calculateDamage(lowLevelAttacker, defender, move);
        int highLevelDamage = Attack.calculateDamage(highLevelAttacker, defender, move);
        assertTrue(highLevelDamage > lowLevelDamage, "Higher level attacker should deal more damage than lower level attacker.");
    }

    @Test
    void higherMovePowerDealsMoreDamage() {
        Pokemon attacker = new Abra("Attacker");
        Pokemon defender = new Abra("Defender");
        attacker.setLevel(50);
        defender.setLevel(50);
        Move lowPowerMove = new Tackle(); // Physical move with 40 power
        Move highPowerMove = new Thunder(); // Special move with 110 power  
        int lowPowerDamage = Attack.calculateDamage(attacker, defender, lowPowerMove);
        int highPowerDamage = Attack.calculateDamage(attacker, defender, highPowerMove);
        assertTrue(highPowerDamage > lowPowerDamage, "Higher power move should deal more damage than lower power move.");
    }

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
        int lowAttackDamage = Attack.calculateDamage(lowAttackAttacker, defender, move);
        int highAttackDamage = Attack.calculateDamage(highAttackAttacker, defender, move);
        assertTrue(highAttackDamage > lowAttackDamage, "Attacker with higher attack stat should deal more damage than attacker with lower attack stat.");
    }

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
        int lowDefenseDamage = Attack.calculateDamage(attacker, lowDefenseDefender, move);
        int highDefenseDamage = Attack.calculateDamage(attacker, highDefenseDefender, move);
        assertTrue(lowDefenseDamage > highDefenseDamage, "Defender with higher defense stat should take less damage than defender with lower defense stat.");
    }

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
        int lowSpAttackDamage = Attack.calculateDamage(lowSpAttackAttacker, defender, move);
        int highSpAttackDamage = Attack.calculateDamage(highSpAttackAttacker, defender, move);
        assertTrue(highSpAttackDamage > lowSpAttackDamage, "Attacker with higher special attack stat should deal more damage than attacker with lower special attack stat when using a special move.");
    }

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
        int lowSpDefenseDamage = Attack.calculateDamage(attacker, lowSpDefenseDefender, move);
        int highSpDefenseDamage = Attack.calculateDamage(attacker, highSpDefenseDefender, move);
        assertTrue(lowSpDefenseDamage > highSpDefenseDamage, "Defender with higher special defense stat should take less damage than defender with lower special defense stat when attacked with a special move.");
    }

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
        int lowSpAttackDamage = Attack.calculateDamage(lowSpAttackAttacker, defender, move); // should be within 23-28
        int highSpAttackDamage = Attack.calculateDamage(highSpAttackAttacker, defender, move); //should be within 23-28
        assertTrue(lowSpAttackDamage >= 23 && lowSpAttackDamage <= 28, "Damage should be within expected range for low special attack attacker.");
        assertTrue(highSpAttackDamage >= 23 && highSpAttackDamage <= 28, "Damage should be within expected range for high special attack attacker.");
    }

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
        int lowAttackDamage = Attack.calculateDamage(lowAttackAttacker, defender, move); // should be within 68-81
        int highAttackDamage = Attack.calculateDamage(highAttackAttacker, defender, move); // should be within 68-81
        assertTrue(lowAttackDamage >= 68 && lowAttackDamage <= 81, "Damage should be within expected range for low attack attacker.");
        assertTrue(highAttackDamage >= 68 && highAttackDamage <= 81, "Damage should be within expected range for high attack attacker.");
    }

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
        int lowDefenseDamage = Attack.calculateDamage(attacker, lowDefenseDefender, move); // should be within 45-54
        int highDefenseDamage = Attack.calculateDamage(attacker, highDefenseDefender, move); // should be within 45-54
        assertTrue(lowDefenseDamage >= 45 && lowDefenseDamage <= 54, "Damage should be within expected range for low defense defender.");
        assertTrue(highDefenseDamage >= 45 && highDefenseDamage <= 54, "Damage should be within expected range for high defense defender.");
    }

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
        int lowSpDefenseDamage = Attack.calculateDamage(attacker, lowSpDefenseDefender, move); // should be within 16-19
        int highSpDefenseDamage = Attack.calculateDamage(attacker, highSpDefenseDefender, move); // should be within 16-19
        assertTrue(lowSpDefenseDamage >= 16 && lowSpDefenseDamage <= 19, "Damage should be within expected range for low special defense defender.");
        assertTrue(highSpDefenseDamage >= 16 && highSpDefenseDamage <= 19, "Damage should be within expected range for high special defense defender.");
    }

    // --- Effectiveness Type Check Tests ---

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
