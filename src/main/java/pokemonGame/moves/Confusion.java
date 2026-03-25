package pokemonGame.moves;
import pokemonGame.Move;

public class Confusion extends Move {
    public static final Confusion INSTANCE = new Confusion();

    public Confusion() {
        super("Confusion", 50, "Psychic",
        "Special", 100, 25);
        // == Special Effect (Not Yet Implemented) ==
        // 10% chance to confuse the target.
    }
}
