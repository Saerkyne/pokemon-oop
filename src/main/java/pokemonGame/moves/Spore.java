package pokemonGame.moves;
import pokemonGame.Move;

public class Spore extends Move {
    public static final Spore INSTANCE = new Spore();

    public Spore() {
        super("Spore", 0, "Grass", "Status", 100, 15);
        // == Special Effect (Not Yet Implemented) ==
        // Puts the target to sleep. Does not affect Grass-type Pokemon.
        // Powder move — blocked by Overcoat.
    }
}
