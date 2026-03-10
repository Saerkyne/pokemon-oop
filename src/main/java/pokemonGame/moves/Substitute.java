package pokemonGame.moves;
import pokemonGame.Move;

public class Substitute extends Move {
    public Substitute() {
        super("Substitute", 0, "Normal", "Status", 0, 10);
        // == Special Effect (Not Yet Implemented) ==
        // User sacrifices 25% of its max HP to create a decoy that
        // absorbs damage until it breaks.
        // Accuracy: 0 is a placeholder — PokemonDB lists no accuracy (self-targeting).
    }
}
