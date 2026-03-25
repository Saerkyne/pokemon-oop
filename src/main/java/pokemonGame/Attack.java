package pokemonGame;
import java.util.Random;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Attack {

    private static final Logger LOGGER = LoggerFactory.getLogger(Attack.class);
    private static final Random RNG = new Random();

    
    
    public float calculateEffectiveness(String defenderType, Move move) {
        String moveType = move.getType();
        float effectiveness;

        if (defenderType == null || defenderType.equals("None")) {
            effectiveness = 1.0f; // Neutral effectiveness if no secondary type
        } else {
            effectiveness = TypeChart.getEffectiveness(moveType, defenderType);
        }

        LOGGER.info("Effectiveness of move '{}' against type '{}': {}", moveType, defenderType, effectiveness);
        return effectiveness;
    }

    public int randomInt(int min, int max) {
        return RNG.nextInt((max - min) + 1) + min;
    }

    // Non RBY crit formula, using attacker speed and opponent speed to determine crit chance,
    //  with a base 4.17% crit chance at 0 speed difference, 
    // increasing up to a maximum of 15% crit chance at a speed difference of 100 or more.
    // Another possible formula is critChance = (sqrt(attackerSpeed) / 1.2) + 4), 
    // which gives diminishing returns on crit chance as speed increases, but 
    // the linear formula is simpler and easier to understand and doesn't factor in
    // the defender's speed.
    public boolean calculateCriticalHit(Pokemon attacker, Pokemon defender) {
        int attackerSpeed = attacker.getCurrentSpeed();
        int defenderSpeed = defender.getCurrentSpeed();
        int critChance = 417; // Base crit chance of 4.17% (417/10000)
        critChance = 417 + (attackerSpeed - defenderSpeed) * 83; // Increase crit chance by 0.83% for each point of speed difference
        critChance = Math.min(1500, Math.max(417, critChance)); // Cap crit chance between 4.17% and 15%
        int randomValue = randomInt(1, 10000); // Random value between 1 and 10000
        boolean isCritical = randomValue <= critChance; // Crit occurs if random value is less than or equal to crit chance
        LOGGER.info("Critical hit calculation: attackerSpeed={}, defenderSpeed={}, critChance={}, randomValue={}, isCritical={}", attackerSpeed, defenderSpeed, critChance, randomValue, isCritical);
        return isCritical;
        
    }

    public int calculateDamage(Pokemon attacker, Pokemon defender, Move move) {
        // Placeholder for damage calculation logic
        int damage = 0;
        if ("Status".equals(move.getMoveCategory())) {
            LOGGER.info("Move '{}' is a status move and does not deal damage.", move.getMoveName());
            return damage; // Status moves do not deal damage
        }
        boolean stab = false; // Placeholder for Same Type Attack Bonus calculation

        int randomFactor = randomInt(217, 255); // Random factor between 217/255 and 255/255

        float effectivenessPrimary = calculateEffectiveness(defender.getTypePrimary(), move);
        
        float effectivenessSecondary = calculateEffectiveness(defender.getTypeSecondary(), move);
        
        float combinedEffectiveness = effectivenessPrimary * effectivenessSecondary;
        LOGGER.info("Combined effectiveness: {}", combinedEffectiveness);

        if (move.getType().equals(attacker.getTypePrimary()) || move.getType().equals(attacker.getTypeSecondary())) {
            stab = true;
            LOGGER.info("STAB applied for move '{}' used by '{}'", move.getMoveName(), attacker.getNickname());
            
        } else {
            LOGGER.info("No STAB for move '{}' used by '{}'", move.getMoveName(), attacker.getNickname());
        }

        LOGGER.info("Effectiveness message: {}", "It" + (combinedEffectiveness == 0.0 ? " had no effect..." : combinedEffectiveness < 1 ? "'s not very effective..." : combinedEffectiveness > 1 ? "'s super effective!" : "'s effective."));


        int level = attacker.getLevel();
        int power = move.getMovePower();
        int attackStat = attacker.getAttackStatForMove(move);
        int defenseStat = defender.getDefenseStatForMove(move);

        LOGGER.info("Attacker level: {}", level);
        LOGGER.info("Move power: {}", power);
        LOGGER.info("Attacker attack stat: {}", attackStat);
        LOGGER.info("Defender defense stat: {}", defenseStat);
   
      
        // Damage calculation, step by step

        // Crit damage modifiers
        // RBY has a bad crit formula, that negates stat stages. This is going to "fix" that issue, preventing a crit from ignoring stat changes but still giving a 2x damage bonus.
        if (calculateCriticalHit(attacker, defender)) {
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
        LOGGER.info("Damage range: " + minDamage + " - " + maxDamage);

        return damage;

        

    }
}
