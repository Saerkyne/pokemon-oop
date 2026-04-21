package pokemonGame.battle;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pokemonGame.core.TypeChart;
import pokemonGame.core.TypeChart.Category;
import pokemonGame.core.TypeChart.Type;
import pokemonGame.model.Move;
import pokemonGame.model.Pokemon;

/**
 * Stateless damage calculator. Handles accuracy checks, critical-hit rolls,
 * type effectiveness (via {@link TypeChart}), STAB, and the random damage
 * factor. All methods are static — there is no instance state.
 *
 * <p>The critical-hit formula is custom: base 4.00% chance, scaling up by
 * 0.08% per point of speed advantage, capped at 20%.</p>
 */
public final class Attack {

    // Production calls stay thread-local. Tests can install a deterministic override
    // without caching ThreadLocalRandom from class-load thread.
    private static Random testRng;

    private Attack() {}

    private static final Logger LOGGER = LoggerFactory.getLogger(Attack.class);
    private static final int BASE_CRIT_CHANCE = 400; // Base crit chance of 4.00% (400/10000)
    private static final int MAX_CRIT_CHANCE = 2000; // Maximum crit chance of 20% (2000/10000)
    private static final int SPEED_CRIT_MULTIPLIER = 8; // Crit chance increases by 0.08% for each point of speed difference (8/10000)

    /**
     * Replaces the random number generator used by all Attack methods.
     * Intended for testing — pass a {@code Random} with a fixed seed to
     * make damage calculations, accuracy checks, and crit rolls
     * deterministic and reproducible.
     *
     * <p>Example: {@code Attack.setRng(new Random(42));}</p>
     *
     * @param random the Random instance to use, or {@code null} to restore default thread-local behavior
     */
    public static void setRng(Random random) {
        // Store only test override here. Real runtime randomness still comes from
        // ThreadLocalRandom.current() at point of use so each thread gets its own source.
        testRng = random;
    }

    // Centralize random access so tests can override one path and production code
    // still uses the current thread's ThreadLocalRandom instance.
    private static int nextIntInclusive(int min, int max) {
        if (testRng != null) {
            return testRng.nextInt((max - min) + 1) + min;
        }
        return ThreadLocalRandom.current().nextInt(min, max + 1);
    }

    public static boolean checkAccuracy(Pokemon attacker, Pokemon defender, Move move) {
        int accuracy = move.getAccuracy();
        if (accuracy >= 100) {
            LOGGER.info("Move '{}' has perfect accuracy and will always hit.", move.getMoveName());
            return true; // Moves with 100% or higher accuracy always hit
        }
        // Route accuracy through shared helper so seeded tests control hit/miss rolls too.
        int randomValue = nextIntInclusive(1, 100); // Random value between 1 and 100
        boolean isHit = randomValue <= accuracy; // Move hits if random value is less than or equal to accuracy
        LOGGER.info("Accuracy check for move '{}': accuracy={}, randomValue={}, isHit={}", move.getMoveName(), accuracy, randomValue, isHit);
        return isHit;
    }
    
    public static float calculateEffectiveness(Type defenderType, Move move) {
        Type moveType = move.getMoveType();
        float effectiveness;

        if (defenderType == null || defenderType.equals(Type.NONE)) {
            effectiveness = 1.0f; // Neutral effectiveness if no secondary type
        } else {
            effectiveness = TypeChart.getEffectiveness(moveType, defenderType);
        }

        LOGGER.info("Effectiveness of move '{}' against type '{}': {}", moveType, defenderType, effectiveness);
        return effectiveness;
    }

    public static int randomInt(int min, int max) {
        // Reuse same helper so damage rolls, crit rolls, and tests all read one RNG path.
        return nextIntInclusive(min, max);
    }

    // Non RBY crit formula, using attacker speed and opponent speed to determine crit chance,
    //  with a base 4.00% crit chance at 0 speed difference, 
    // increasing up to a maximum of 20% crit chance at a speed difference of 100 or more.
    // Another possible formula is critChance = (sqrt(attackerSpeed) / 1.2) + 4), 
    // which gives diminishing returns on crit chance as speed increases, but 
    // the linear formula is simpler and easier to understand and doesn't factor in
    // the defender's speed.
    public static boolean calculateCriticalHit(Pokemon attacker, Pokemon defender) {
        
        int attackerSpeed = attacker.getCurrentSpeed();
        int defenderSpeed = defender.getCurrentSpeed();
        int critChance = BASE_CRIT_CHANCE; // Base crit chance of 4.00% (400/10000)
        critChance = BASE_CRIT_CHANCE + ((attackerSpeed - defenderSpeed) * SPEED_CRIT_MULTIPLIER); // Increase crit chance by 0.08% for each point of speed difference
        critChance = Math.min(MAX_CRIT_CHANCE, Math.max(BASE_CRIT_CHANCE, critChance)); // Cap crit chance between 4.00% and 20%
        int randomValue = randomInt(1, 10000); // Random value between 1 and 10000
        boolean isCritical = randomValue <= critChance; // Crit occurs if random value is less than or equal to crit chance
        LOGGER.info("Critical hit calculation: attackerSpeed={}, defenderSpeed={}, critChance={}, randomValue={}, isCritical={}", attackerSpeed, defenderSpeed, critChance, randomValue, isCritical);
        return isCritical;
        
    }

    public static int calculateDamage(Pokemon attacker, Pokemon defender, Move move, boolean crit) {
        int damage = 0;
        if (move.getMoveCategory() == Category.STATUS) {
            // TODO(review 2026-04-20): Route STATUS moves through an effect pipeline instead of returning 0 damage only.
            // Right now sleep/paralysis/burn-style moves resolve as no-ops, which means many Gen 1 battle-control moves never affect battle state.
            LOGGER.info("Move '{}' is a status move and does not deal damage.", move.getMoveName());
            return damage; // Status moves do not deal damage
        }
        boolean stab = false; // Placeholder for Same Type Attack Bonus calculation

        int randomFactor = randomInt(217, 255); // Random factor between 217/255 and 255/255

        float effectivenessPrimary = calculateEffectiveness(defender.getTypePrimary(), move);
        
        float effectivenessSecondary = calculateEffectiveness(defender.getTypeSecondary(), move);
        
        float combinedEffectiveness = effectivenessPrimary * effectivenessSecondary;
        LOGGER.info("Combined effectiveness: {}", combinedEffectiveness);

        if (move.getMoveType().equals(attacker.getTypePrimary()) || move.getMoveType().equals(attacker.getTypeSecondary())) {
            stab = true;
            LOGGER.info("STAB applied for move '{}' used by '{}'", move.getMoveName(), attacker.getNickname());
            
        } else {
            LOGGER.info("No STAB for move '{}' used by '{}'", move.getMoveName(), attacker.getNickname());
        }

        if (combinedEffectiveness == 0.0) {
            LOGGER.info("The move '{}' had no effect on '{}'.", move.getMoveName(), defender.getNickname());
        } else if (combinedEffectiveness < 1.0) {
            LOGGER.info("The move '{}' was not very effective against '{}'.", move.getMoveName(), defender.getNickname());
        } else if (combinedEffectiveness > 1.0) {
            LOGGER.info("The move '{}' was super effective against '{}'.", move.getMoveName(), defender.getNickname());
        } else {
            LOGGER.info("The move '{}' was effective against '{}'.", move.getMoveName(), defender.getNickname());
        }
        

        int level = attacker.getLevel();
        int power = move.getMovePower();
        int attackStat = attacker.getAttackStatForMove(move);
        int defenseStat = Math.max(1, defender.getDefenseStatForMove(move));

        LOGGER.info("Attacker level: {}", level);
        LOGGER.info("Move power: {}", power);
        LOGGER.info("Attacker attack stat: {}", attackStat);
        LOGGER.info("Defender defense stat: {}", defenseStat);
   
      
        // Damage calculation, step by step

        // Crit damage modifiers
        // RBY has a bad crit formula, that negates stat stages. This is going to "fix" that issue, preventing a crit from ignoring stat changes but still giving a damage bonus.
        if (crit) {
            level = level * 2; // Bonus multiplier for critical hits
        }

        
        // Level factor calculation
        int levelCalc = (2 * level) / 5;
        levelCalc = levelCalc + 2;

        // Apply power and attack/defense ratio
        int baseDamage = (levelCalc * power * attackStat) / defenseStat;
        baseDamage = (baseDamage / 50);
        baseDamage = baseDamage + 2;

        int finalDamage = baseDamage;

        // Apply STAB
        if (stab) {
            finalDamage = (finalDamage * 3) / 2;
        }

        // Apply type effectiveness
        finalDamage = (int)(finalDamage * effectivenessPrimary);
        finalDamage = (int)(finalDamage * effectivenessSecondary);

        // Apply random factor
        damage = (finalDamage * randomFactor) / 255;
        int minDamage = (int)(finalDamage * 217) / 255;
        int maxDamage = (int)(finalDamage * 255) / 255;
        LOGGER.info("Damage range: {} - {}", minDamage, maxDamage);

        return damage;

        

    }
}
