package pokemonGame;
import java.util.Dictionary;
import java.util.Hashtable;

public class TypeChart {
    String moveType;
    String pokemonTypePrimary;
    String pokemonTypeSecondary;
    float effectiveness;
    //Row is attack type, column is pokemon type, value is effectiveness multiplier
    float[][] typeChart;
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

    public float getEffectiveness(String moveType, String pokemonType) {
        Dictionary<String, Integer> typeIndices = new Hashtable<String, Integer>();
        typeIndices.put("Normal", 0);
        typeIndices.put("Fire", 1);
        typeIndices.put("Water", 2);
        typeIndices.put("Electric", 3);
        typeIndices.put("Grass", 4);
        typeIndices.put("Ice", 5);
        typeIndices.put("Fighting", 6);
        typeIndices.put("Poison", 7);
        typeIndices.put("Ground", 8);
        typeIndices.put("Flying", 9);
        typeIndices.put("Psychic", 10);
        typeIndices.put("Bug", 11);
        typeIndices.put("Rock", 12);
        typeIndices.put("Ghost", 13);
        typeIndices.put("Dragon", 14);
        typeIndices.put("Dark", 15);
        typeIndices.put("Steel", 16);
        typeIndices.put("Fairy", 17);

        System.out.println("Move Type: " + moveType);
        System.out.println("Pokemon Type: " + pokemonType);

        int moveIndex = typeIndices.get(moveType);
        int pokemonIndex = typeIndices.get(pokemonType);

        return typeChart[moveIndex][pokemonIndex];
    }
}
