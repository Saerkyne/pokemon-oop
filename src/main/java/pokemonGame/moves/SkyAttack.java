package pokemonGame.moves;
import pokemonGame.Move;

public class SkyAttack extends Move {
    public static final SkyAttack INSTANCE = new SkyAttack();

    public SkyAttack() {
        super("Sky Attack", 140, "Flying", "Physical", 90, 5);
        // == Special Effect (Not Yet Implemented) ==
        // Charges on turn 1, attacks on turn 2.
        // 30% chance to make the target flinch. High critical-hit ratio.
    }
}
