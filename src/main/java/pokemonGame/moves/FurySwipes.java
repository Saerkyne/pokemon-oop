package pokemonGame.moves;
import pokemonGame.Move;

public class FurySwipes extends Move {
    public static final FurySwipes INSTANCE = new FurySwipes();

    public FurySwipes() {
        super("Fury Swipes", 18, "Normal", "Physical", 80, 15);
        // == Special Effect (Not Yet Implemented) ==
        // Hits 2-5 times in one turn.
    }
}
