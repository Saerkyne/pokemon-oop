package pokemonGame.moves;
import pokemonGame.Move;

public class Sludge extends Move {
    public static final Sludge INSTANCE = new Sludge();

    public Sludge() {
        super("Sludge", 65, "Poison", "Special", 100, 20);
        // == Special Effect (Not Yet Implemented) ==
        // 30% chance to poison the target.
    }
}
