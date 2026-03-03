package pokemonGame;
import java.util.Random;

public abstract class Attack {
    float effectivenessPrimaryType;
    float effectivenessSecondaryType;

    TypeChart typeChart = new TypeChart();
    public float calculateEffectiveness(String defenderType, Move move) {
        String moveType = move.getType();
        float effectiveness;

        if (defenderType == null || defenderType.equals("None")) {
            effectiveness = 1.0f; // Neutral effectiveness if no secondary type
        } else {
            effectiveness = typeChart.getEffectiveness(moveType, defenderType);
        }

        return effectiveness;
    }

    public int randomInt(int min, int max) {
        Random rand = new Random();
        return rand.nextInt((max - min) + 1) + min;
    }

    public boolean calculateCriticalHit(Pokemon attacker) {
        int chance = randomInt(0, 255);
        int threshold = attacker.getSpeedBaseStat() / 2; // Example threshold based on speed stat

        if (chance > threshold) {
            System.out.println("A critical hit!");
            return true; // Return 2 for critical hit
        } else {
            return false; // Return 1 for normal hit
        }
    }

    public int calculateDamage(Pokemon attacker, Pokemon defender, Move move) {
        // Placeholder for damage calculation logic
        int damage = 0;
        boolean stab = false; // Placeholder for Same Type Attack Bonus calculation

        int randomFactor = randomInt(217, 255); // Random factor between 217/255 and 255/255

        float effectivenessPrimary = calculateEffectiveness(defender.getTypePrimary(), move);
        // System.out.println("Effectiveness against primary type (" + defender.getTypePrimary() + "): " + effectivenessPrimary);
        float effectivenessSecondary = calculateEffectiveness(defender.getTypeSecondary(), move);
        // System.out.println("Effectiveness against secondary type (" + defender.getTypeSecondary() + "): " + effectivenessSecondary);

        float combinedEffectiveness = effectivenessPrimary * effectivenessSecondary;
        // System.out.println("Combined effectiveness: " + combinedEffectiveness);

        if (move.getType() == attacker.getTypePrimary() || move.getType() == attacker.getTypeSecondary()) {
            stab = true;
            System.out.println("STAB applied!");
        } else {
            stab = false;
        }

        System.out.println("It" + (combinedEffectiveness == 0.0 ? " had no effect..." : combinedEffectiveness < 1 ? "'s not very effective..." : combinedEffectiveness > 1 ? "'s super effective!" : "'s effective."));


        int level = attacker.getLevel();
        int power = move.getMovePower();
        int attackStat = 168; //attacker.getAttackStatForMove(move);
        int defenseStat = 218; //defender.getDefenseStatForMove(move);

        System.out.println("Attacker level: " + level);
        System.out.println("Move power: " + power);
        System.out.println("Attacker attack stat: " + attackStat);
        System.out.println("Defender defense stat: " + defenseStat);
   
      
        // Damage calculation, step by step

        // Crit damage modifiers
        // RBY has a bad crit formula, that negates stat stages. This is going to "fix" that issue, preventing a crit from ignoring stat changes but still giving a 2x damage bonus.
        if (calculateCriticalHit(attacker)) {
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
        System.out.println("Damage range: " + minDamage + " - " + maxDamage);

        return damage;

        

    }
}
