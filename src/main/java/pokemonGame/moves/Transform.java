package pokemonGame.moves;
import pokemonGame.Move;

public class Transform extends Move {
    public Transform() {
        super("Transform", 0, "Normal", "Status", 0, 10);
        // == Special Effect (Not Yet Implemented) ==
        // User transforms into the target, copying its species, type,
        // moves (each set to 5 PP), stats, and stat changes.
        // Accuracy: 0 is a placeholder — PokemonDB lists no accuracy (always succeeds).
    }
}
