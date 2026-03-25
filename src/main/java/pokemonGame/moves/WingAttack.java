package pokemonGame.moves;
import pokemonGame.Move;

public class WingAttack extends Move {
    public static final WingAttack INSTANCE = new WingAttack();

    public WingAttack() {
        super("Wing Attack", 60, "Flying", "Physical", 100, 35);
        // == Special Effect ==
        // No additional effect.
    }
}
