package pokemonGame.moves;
import pokemonGame.Move;

public class PoisonPowder extends Move {
    public static final PoisonPowder INSTANCE = new PoisonPowder();

    public PoisonPowder() {
        super("Poison Powder", 0, "Poison", "Status", 75, 35);
        // == Special Effect (Not Yet Implemented) ==
        // Poisons the target. Does not affect Grass-type or
        // Poison-type Pokemon. Powder move — blocked by Overcoat.
    }
}
