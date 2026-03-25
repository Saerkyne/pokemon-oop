package pokemonGame.moves;
import pokemonGame.Move;

public class Teleport extends Move {
    public static final Teleport INSTANCE = new Teleport();

    public Teleport() {
        super("Teleport", 0, "Psychic", "Status", 0, 20);
        // == Special Effect (Not Yet Implemented) ==
        // Flees from wild battles.
        // In trainer battles, switches the user out. Priority -6.
        // Accuracy: 0 is a placeholder — PokemonDB lists no accuracy (self-targeting).
    }
}
