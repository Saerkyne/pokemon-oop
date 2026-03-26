package pokemonGame.moves;
import pokemonGame.Move;
import pokemonGame.TypeChart.Type;
import pokemonGame.TypeChart.Category;

public class Rest extends Move {
    public static final Rest INSTANCE = new Rest();

    public Rest() {
        super("Rest", 0, Type.PSYCHIC, Category.STATUS, 0, 10);
        // == Special Effect (Not Yet Implemented) ==
        // User fully restores its HP but falls asleep for 2 turns.
        // Accuracy: 0 is a placeholder — PokemonDB lists no accuracy (self-targeting).
    }
}
