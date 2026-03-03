package pokemonGame;

public abstract class Attack {
    float effectivenessPrimaryType;
    float effectivenessSecondaryType;

    TypeChart typeChart = new TypeChart();
    public float calculateEffectiveness(Pokemon defender, Move move) {
        String moveType = move.getType();
        String defenderPrimaryType = defender.getTypePrimary();
        String defenderSecondaryType = defender.getTypeSecondary();

        effectivenessPrimaryType = typeChart.getEffectiveness(moveType, defenderPrimaryType);
        System.out.println("Effectiveness against primary type (" + defenderPrimaryType + "): " + effectivenessPrimaryType);
        try {
            effectivenessSecondaryType = typeChart.getEffectiveness(moveType, defenderSecondaryType);
        } catch (Exception e) {
            effectivenessSecondaryType = 1.0f;
        }

        System.out.println("Effectiveness against secondary type (" + defenderSecondaryType + "): " + effectivenessSecondaryType);
        return effectivenessPrimaryType * effectivenessSecondaryType;
    }
}
