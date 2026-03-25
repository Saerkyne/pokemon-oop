package pokemonGame.moves;
import pokemonGame.Move;

public class Blizzard extends Move {
    public static final Blizzard INSTANCE = new Blizzard();

    public Blizzard() {
        super("Blizzard", 110, "Ice",
        "Special", 70, 5);
        // == Special Effect (Not Yet Implemented) ==
        // 10% chance to freeze the target.
    }

}
