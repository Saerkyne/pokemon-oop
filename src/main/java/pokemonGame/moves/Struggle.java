package pokemonGame.moves;
import pokemonGame.Move;
import pokemonGame.TypeChart.Type;
import pokemonGame.TypeChart.Category;

public class Struggle extends Move {
    public static final Struggle INSTANCE = new Struggle();

    public Struggle() {
        super("Struggle", 50, Type.NORMAL, Category.PHYSICAL, 0, 1);
        // == Special Effect (Not Yet Implemented) ==
        // Used automatically when all other moves are out of PP.
        // User takes recoil damage equal to 25% of its max HP.
        // In Gen 4+, Struggle is typeless (ignores type effectiveness).
        // Accuracy: 0 is a placeholder — PokemonDB lists no accuracy (never misses).
    }
}
