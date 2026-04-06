package pokemonGame.moves;
import pokemonGame.core.TypeChart.Category;
import pokemonGame.core.TypeChart.Type;
import pokemonGame.model.Move;

public class Stomp extends Move {
    public static final Stomp INSTANCE = new Stomp();

    public Stomp() {
        super("Stomp", 65, Type.NORMAL, Category.PHYSICAL, 100, 20);
        // == Special Effect (Not Yet Implemented) ==
        // 30% chance to make the target flinch.
        // Deals double damage to a target that has used Minimize.
    }
}
