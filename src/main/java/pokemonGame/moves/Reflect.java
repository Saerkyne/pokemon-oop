package pokemonGame.moves;
import pokemonGame.Move;
import pokemonGame.TypeChart.Type;
import pokemonGame.TypeChart.Category;

public class Reflect extends Move {
    public static final Reflect INSTANCE = new Reflect();

    public Reflect() {
        super("Reflect", 0, Type.PSYCHIC, Category.STATUS, 0, 20);
        // == Special Effect (Not Yet Implemented) ==
        // Reduces physical damage taken by the user's team by 50% for 5 turns.
        // Accuracy: 0 is a placeholder — PokemonDB lists no accuracy (self-targeting).
    }
}
