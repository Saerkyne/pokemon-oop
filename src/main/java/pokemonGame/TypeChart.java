package pokemonGame;
import java.util.Map;
import java.util.HashMap;

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


    private static final Map<Type, Integer> TYPE_INDICES = new HashMap<>();
        static {
        TYPE_INDICES.put(Type.NORMAL, 0);
        TYPE_INDICES.put(Type.FIRE, 1);
        TYPE_INDICES.put(Type.WATER, 2);
        TYPE_INDICES.put(Type.ELECTRIC, 3);
        TYPE_INDICES.put(Type.GRASS, 4);
        TYPE_INDICES.put(Type.ICE, 5);
        TYPE_INDICES.put(Type.FIGHTING, 6);
        TYPE_INDICES.put(Type.POISON, 7);
        TYPE_INDICES.put(Type.GROUND, 8);
        TYPE_INDICES.put(Type.FLYING, 9);
        TYPE_INDICES.put(Type.PSYCHIC, 10);
        TYPE_INDICES.put(Type.BUG, 11);
        TYPE_INDICES.put(Type.ROCK, 12);
        TYPE_INDICES.put(Type.GHOST, 13);
        TYPE_INDICES.put(Type.DRAGON, 14);
        TYPE_INDICES.put(Type.DARK, 15);
        TYPE_INDICES.put(Type.STEEL, 16);
        TYPE_INDICES.put(Type.FAIRY, 17);
        }

    


    public static float getEffectiveness(Type moveType, Type pokemonType) {

        Integer moveIndex = TYPE_INDICES.get(moveType);
        Integer pokemonIndex = TYPE_INDICES.get(pokemonType);

        // Add error handling for invalid types
        if (moveIndex == null) throw new IllegalArgumentException
        ("Invalid move type: " + moveType);
        if (pokemonIndex == null) throw new IllegalArgumentException
        ("Invalid pokemon type: " + pokemonType);

        return TYPE_CHART[moveIndex][pokemonIndex];
    }
}
