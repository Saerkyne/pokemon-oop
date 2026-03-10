package pokemonGame.moves;
import pokemonGame.Move;

public class Sharpen extends Move {
    public Sharpen() {
        super("Sharpen", 0, "Normal", "Status", 0, 30);
        // == Special Effect (Not Yet Implemented) ==
        // Raises the user's Attack by 1 stage.
        // Accuracy: 0 is a placeholder — PokemonDB lists no accuracy (self-targeting).
    }
}
