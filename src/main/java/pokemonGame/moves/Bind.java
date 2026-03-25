package pokemonGame.moves;
import pokemonGame.Move;

public class Bind extends Move {
    public static final Bind INSTANCE = new Bind();

    public Bind() {
        super("Bind", 15, "Normal",
        "Physical", 85, 20);
        // == Special Effect (Not Yet Implemented) ==
        // Traps the target for 4-5 turns, dealing 1/8 of its max HP per turn.
    }

}
