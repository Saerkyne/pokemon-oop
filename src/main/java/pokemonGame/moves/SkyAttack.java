package pokemonGame.moves;
import pokemonGame.core.TypeChart.Category;
import pokemonGame.core.TypeChart.Type;
import pokemonGame.model.Move;

public class SkyAttack extends Move {
    public static final SkyAttack INSTANCE = new SkyAttack();

    public SkyAttack() {
        super("Sky Attack", 140, Type.FLYING, Category.PHYSICAL, 90, 5);
        // == Special Effect (Not Yet Implemented) ==
        // Charges on turn 1, attacks on turn 2.
        // 30% chance to make the target flinch. High critical-hit ratio.
    }
}
