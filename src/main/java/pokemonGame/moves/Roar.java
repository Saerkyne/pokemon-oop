package pokemonGame.moves;
import pokemonGame.Move;
import pokemonGame.TypeChart.Type;
import pokemonGame.TypeChart.Category;

public class Roar extends Move {
    public static final Roar INSTANCE = new Roar();

    public Roar() {
        super("Roar", 0, Type.NORMAL, Category.STATUS, 0, 20);
        // == Special Effect (Not Yet Implemented) ==
        // Forces the target to switch out in trainer battles.
        // Priority -6. Sound-based move.
        // Accuracy: 0 is a placeholder — PokemonDB lists no accuracy (bypasses accuracy checks).
    }
}
