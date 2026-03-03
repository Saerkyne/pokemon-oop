package pokemonGame;

public class TypeChart {
    int moveType;
    int pokemonType;
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
}
