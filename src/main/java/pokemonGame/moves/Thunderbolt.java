package pokemonGame.moves;
import pokemonGame.Move;

public class Thunderbolt extends Move {
    public static final Thunderbolt INSTANCE = new Thunderbolt();

    public Thunderbolt() {
        super("Thunderbolt", 90, "Electric", "Special", 100, 15);
        // == Special Effect (Not Yet Implemented) ==
        // 10% chance to paralyze the target.
    }
}
