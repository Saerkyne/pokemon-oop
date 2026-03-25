package pokemonGame.moves;
import pokemonGame.Move;

public class FuryAttack extends Move {
    public static final FuryAttack INSTANCE = new FuryAttack();

    public FuryAttack() {
        super("Fury Attack", 15, "Normal", "Physical", 85, 20);
        // == Special Effect (Not Yet Implemented) ==
        // Hits 2-5 times in one turn.
    }
}
