package pokemonGame.moves;
import pokemonGame.Move;
import pokemonGame.TypeChart.Type;
import pokemonGame.TypeChart.Category;

public class SandAttack extends Move {
    public static final SandAttack INSTANCE = new SandAttack();

    public SandAttack() {
        super("Sand Attack", 0, Type.GROUND, Category.STATUS, 100, 15);
        // == Special Effect (Not Yet Implemented) ==
        // Lowers the target's accuracy by 1 stage.
    }
}
