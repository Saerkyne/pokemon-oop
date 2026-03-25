package pokemonGame.moves;
import pokemonGame.Move;

public class Bite extends Move {
    public static final Bite INSTANCE = new Bite();

    public Bite() {
        super("Bite", 60, "Dark",
        "Physical", 100, 25);
        // == Special Effect (Not Yet Implemented) ==
        // 30% chance to make the target flinch.
    }

}
