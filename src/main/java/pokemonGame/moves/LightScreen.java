package pokemonGame.moves;
import pokemonGame.Move;

public class LightScreen extends Move {
    public LightScreen() {
        super("Light Screen", 0, "Psychic", "Status", 0, 30);
        // == Special Effect (Not Yet Implemented) ==
        // Reduces special damage taken by the user's team by 50% for 5 turns.
        // Accuracy: 0 is a placeholder — PokemonDB lists no accuracy (self-targeting).
    }
}
