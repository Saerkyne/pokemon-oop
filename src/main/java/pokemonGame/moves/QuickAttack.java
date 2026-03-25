package pokemonGame.moves;
import pokemonGame.Move;


public class QuickAttack extends Move {
    public static final QuickAttack INSTANCE = new QuickAttack();

    public QuickAttack() {
        super("Quick Attack", 40, "Normal", "Physical", 100, 30);
        // == Special Effect (Not Yet Implemented) ==
        // Priority +1. This move always goes before most other moves.
    }
}
