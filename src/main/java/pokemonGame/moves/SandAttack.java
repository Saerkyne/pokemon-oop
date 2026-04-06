package pokemonGame.moves;
import pokemonGame.core.TypeChart.Category;
import pokemonGame.core.TypeChart.Type;
import pokemonGame.model.Move;

public class SandAttack extends Move {
    public static final SandAttack INSTANCE = new SandAttack();

    public SandAttack() {
        super("Sand Attack", 0, Type.GROUND, Category.STATUS, 100, 15);
        // == Special Effect (Not Yet Implemented) ==
        // Lowers the target's accuracy by 1 stage.
    }
}
