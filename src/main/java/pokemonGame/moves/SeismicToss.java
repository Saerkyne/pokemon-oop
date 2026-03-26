package pokemonGame.moves;
import pokemonGame.Move;
import pokemonGame.TypeChart.Type;
import pokemonGame.TypeChart.Category;

public class SeismicToss extends Move {
    public static final SeismicToss INSTANCE = new SeismicToss();

    public SeismicToss() {
        super("Seismic Toss", 0, Type.FIGHTING, Category.PHYSICAL, 100, 20);
        // == Special Effect (Not Yet Implemented) ==
        // Deals damage equal to the user's level.
        // Power: 0 is a placeholder — PokemonDB lists no base power (level-based damage).
    }
}
