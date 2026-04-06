package pokemonGame.moves;
import pokemonGame.core.TypeChart.Category;
import pokemonGame.core.TypeChart.Type;
import pokemonGame.model.Move;

public class Rest extends Move {
    public static final Rest INSTANCE = new Rest();

    public Rest() {
        super("Rest", 0, Type.PSYCHIC, Category.STATUS, 0, 10);
        // == Special Effect (Not Yet Implemented) ==
        // User fully restores its HP but falls asleep for 2 turns.
        // Accuracy: 0 is a placeholder — PokemonDB lists no accuracy (self-targeting).
    }
}
