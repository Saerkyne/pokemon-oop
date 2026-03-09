package pokemonGame;
import java.util.Map;
import java.util.HashMap;

public class TypeChart {
    //Row is attack type, column is pokemon type, value is effectiveness multiplier
    private float[][] typeChart;
    public TypeChart() {
        typeChart = new float[][]{
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
    }


    private static final Map<String, Integer> TYPE_INDICES = new HashMap<>();
        static {
        TYPE_INDICES.put("Normal", 0);
        TYPE_INDICES.put("Fire", 1);
        TYPE_INDICES.put("Water", 2);
        TYPE_INDICES.put("Electric", 3);
        TYPE_INDICES.put("Grass", 4);
        TYPE_INDICES.put("Ice", 5);
        TYPE_INDICES.put("Fighting", 6);
        TYPE_INDICES.put("Poison", 7);
        TYPE_INDICES.put("Ground", 8);
        TYPE_INDICES.put("Flying", 9);
        TYPE_INDICES.put("Psychic", 10);
        TYPE_INDICES.put("Bug", 11);
        TYPE_INDICES.put("Rock", 12);
        TYPE_INDICES.put("Ghost", 13);
        TYPE_INDICES.put("Dragon", 14);
        TYPE_INDICES.put("Dark", 15);
        TYPE_INDICES.put("Steel", 16);
        TYPE_INDICES.put("Fairy", 17);
        }


    public float getEffectiveness(String moveType, String pokemonType) {

        int moveIndex = TYPE_INDICES.get(moveType);
        int pokemonIndex = TYPE_INDICES.get(pokemonType);

        return typeChart[moveIndex][pokemonIndex];
    }
}
