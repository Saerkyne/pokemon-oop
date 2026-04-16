package pokemonGame.core;

/**
 * 18×18 type effectiveness matrix and related enums ({@link Type},
 * {@link Category}, {@link StatusCondition}). All data is static and
 * immutable — lookups are array-indexed for speed.
 *
 * <p>The matrix is indexed by {@link Type#ordinal()}: row = attacking type,
 * column = defending type. Values are float multipliers
 * (0, 0.5, 1, or 2).</p>
 */
public final class TypeChart {

    private TypeChart() {} // Utility class — prevent instantiation

    //Row is attack type, column is pokemon type, value is effectiveness multiplier
    private static final float[][] TYPE_CHART = {
        {1,   1,   1,   1,   1,   1,   1,   1,   1,   1,   1,   1, .5f,   0,   1,   1, .5f,   1}, //Normal Attack
        {1, .5f, .5f,   1,   2,   2,   1,   1,   1,   1,   1,   2, .5f,   1, .5f,   1,   2,   1}, //Fire Attack
        {1,   2, .5f,   1, .5f,   1,   1,   1,   2,   1,   1,   1,   2,   1, .5f,   1,   1,   1}, //Water Attack
        {1,   1,   2, .5f, .5f,   1,   1,   1,   0,   2,   1,   1,   1,   1, .5f,   1,   1,   1}, //Electric Attack
        {1, .5f,   2,   1, .5f,   1,   1, .5f,   2, .5f,   1, .5f,   2,   1, .5f,   1, .5f,   1}, //Grass Attack
        {1, .5f, .5f,   1,   2, .5f,   1,   1,   2,   2,   1,   1,   1,   1,   2,   1, .5f,   1}, //Ice Attack
        {2,   1,   1,   1,   1,   2,   1, .5f,   1, .5f, .5f, .5f,   2,   0,   1,   2,   2, .5f}, //Fighting Attack
        {1,   1,   1,   1,   2,   1,   1, .5f, .5f,   1,   1,   1, .5f, .5f,   1,   1,   0,   2}, //Poison Attack
        {1,   2,   1,   2, .5f,   1,   1,   2,   1,   0,   1, .5f,   2,   1,   1,   1,   2,   1}, //Ground Attack
        {1,   1,   1, .5f,   2,   1,   2,   1,   1,   1,   1,   2, .5f,   1,   1,   1, .5f,   1}, //Flying Attack
        {1,   1,   1,   1,   1,   1,   2,   2,   1,   1, .5f,   1,   1,   1,   1,   0, .5f,   1}, //Psychic Attack
        {1, .5f,   1,   1,   2,   1, .5f, .5f,   1, .5f,   2,   1,   1, .5f,   1,   2, .5f, .5f}, //Bug Attack
        {1,   2,   1,   1,   1,   2, .5f,   1, .5f,   2,   1,   2,   1,   1,   1,   1, .5f,   1}, //Rock Attack
        {0,   1,   1,   1,   1,   1,   1,   1,   1,   1,   2,   1,   1,   2,   1, .5f,   1,   1}, //Ghost Attack
        {1,   1,   1,   1,   1,   1,   1,   1,   1,   1,   1,   1,   1,   1,   2,   1, .5f,   0}, //Dragon Attack
        {1,   1,   1,   1,   1,   1, .5f,   1,   1,   1,   2,   1,   1,   2,   1, .5f,   1, .5f}, //Dark Attack
        {1, .5f, .5f, .5f,   1,   2,   1,   1,   1,   1,   1,   1,   2,   1,   1,   1, .5f,   2}, //Steel Attack
        {1, .5f,   1,   1,   1,   1,   2, .5f,   1,   1,   1,   1,   1,   1,   2,   2, .5f,   1}  //Fairy Attack
    };

    public static enum Type {
        NORMAL, FIRE, WATER, ELECTRIC, GRASS, ICE, FIGHTING, POISON, GROUND, FLYING, PSYCHIC, BUG, ROCK, GHOST, DRAGON, DARK, STEEL, FAIRY, NONE
    }

    public static enum Category {
        PHYSICAL, SPECIAL, STATUS
    }

    public static enum StatusCondition {
        BURN, FREEZE, PARALYSIS, POISON, SLEEP
    }    


    public static float getEffectiveness(Type moveType, Type pokemonType) {

        if (pokemonType == Type.NONE || pokemonType == null) {
            return 1.0f; // Neutral effectiveness for NONE or null type
        }

        if (moveType == Type.NONE || moveType == null) {
            return 1.0f; // Neutral effectiveness for NONE or null move type
        }

        return TYPE_CHART[moveType.ordinal()][pokemonType.ordinal()];
    }
}
