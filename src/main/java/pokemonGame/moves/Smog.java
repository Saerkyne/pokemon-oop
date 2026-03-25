package pokemonGame.moves;
import pokemonGame.Move;

public class Smog extends Move {
    public static final Smog INSTANCE = new Smog();

    public Smog() {
        super("Smog", 30, "Poison", "Special", 70, 20);
        // == Special Effect (Not Yet Implemented) ==
        // 40% chance to poison the target.
    }
}
