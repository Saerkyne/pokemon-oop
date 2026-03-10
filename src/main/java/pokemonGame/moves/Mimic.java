package pokemonGame.moves;
import pokemonGame.Move;

public class Mimic extends Move {
    public Mimic() {
        super("Mimic", 0, "Normal", "Status", 0, 10);
        // == Special Effect (Not Yet Implemented) ==
        // Copies one of the target's moves; the user can use it
        // for the rest of the battle, replacing Mimic in its moveset.
        // Accuracy: 0 is a placeholder — PokemonDB lists no accuracy (always succeeds).
    }
}
