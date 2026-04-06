package pokemonGame.moves;
import pokemonGame.core.TypeChart.Category;
import pokemonGame.core.TypeChart.Type;
import pokemonGame.model.Move;

public class Whirlwind extends Move {
    public static final Whirlwind INSTANCE = new Whirlwind();

    public Whirlwind() {
        super("Whirlwind", 0, Type.NORMAL, Category.STATUS, 0, 20);
        // == Special Effect (Not Yet Implemented) ==
        // Forces the target to switch out in trainer battles.
        // Priority -6.
        // Accuracy: 0 is a placeholder — PokemonDB lists no accuracy (bypasses accuracy checks).
    }
}
