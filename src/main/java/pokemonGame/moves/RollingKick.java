package pokemonGame.moves;
import pokemonGame.core.TypeChart.Category;
import pokemonGame.core.TypeChart.Type;
import pokemonGame.model.Move;

public class RollingKick extends Move {
    public static final RollingKick INSTANCE = new RollingKick();

    public RollingKick() {
        super("Rolling Kick", 60, Type.FIGHTING, Category.PHYSICAL, 85, 15);
        // == Special Effect (Not Yet Implemented) ==
        // 30% chance to make the target flinch.
    }
}
