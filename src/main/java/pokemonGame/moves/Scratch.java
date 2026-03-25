package pokemonGame.moves;
import pokemonGame.Move;


public class Scratch extends Move{
    public static final Scratch INSTANCE = new Scratch();

    public Scratch() {
        super("Scratch", 40, "Normal", "Physical", 100, 35);
        // == Special Effect ==
        // No additional effect.
    }
}
