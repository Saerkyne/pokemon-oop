package pokemonGame.moves;
import pokemonGame.Move;

public class SoftBoiled extends Move {
    public static final SoftBoiled INSTANCE = new SoftBoiled();

    public SoftBoiled() {
        super("Soft-Boiled", 0, "Normal", "Status", 0, 10);
        // == Special Effect (Not Yet Implemented) ==
        // Restores up to 50% of the user's max HP.
        // Accuracy: 0 is a placeholder — PokemonDB lists no accuracy (self-targeting).
    }
}
