package pokemonGame.moves;
import pokemonGame.Move;

public class HornAttack extends Move {
    public static final HornAttack INSTANCE = new HornAttack();

    public HornAttack() {
        super("Horn Attack", 65, "Normal", "Physical", 100, 25);
        // == Special Effect ==
        // No additional effect.
    }
}
