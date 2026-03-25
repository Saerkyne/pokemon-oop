package pokemonGame.moves;
import pokemonGame.Move;

public class PoisonSting extends Move {
    public static final PoisonSting INSTANCE = new PoisonSting();

    public PoisonSting() {
        super("Poison Sting", 15, "Poison", "Physical", 100, 35);
        // == Special Effect (Not Yet Implemented) ==
        // 30% chance to poison the target.
    }
}
