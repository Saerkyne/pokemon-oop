package pokemonGame.moves;
import pokemonGame.Move;

public class Roar extends Move {
    public Roar() {
        super("Roar", 0, "Normal", "Status", 0, 20);
        // == Special Effect (Not Yet Implemented) ==
        // Forces the target to switch out in trainer battles.
        // Priority -6. Sound-based move.
        // Accuracy: 0 is a placeholder — PokemonDB lists no accuracy (bypasses accuracy checks).
    }
}
