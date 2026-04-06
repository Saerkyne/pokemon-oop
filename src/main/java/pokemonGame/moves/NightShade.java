package pokemonGame.moves;
import pokemonGame.core.TypeChart.Category;
import pokemonGame.core.TypeChart.Type;
import pokemonGame.model.Move;

public class NightShade extends Move {
    public static final NightShade INSTANCE = new NightShade();

    public NightShade() {
        super("Night Shade", 0, Type.GHOST, Category.SPECIAL, 100, 15);
        // == Special Effect (Not Yet Implemented) ==
        // Deals damage equal to the user's level.
        // Power: 0 is a placeholder — PokemonDB lists no base power (level-based damage).
    }
}
