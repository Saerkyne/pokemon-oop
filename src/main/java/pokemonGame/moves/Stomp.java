package pokemonGame.moves;
import pokemonGame.Move;
import pokemonGame.TypeChart.Type;
import pokemonGame.TypeChart.Category;

public class Stomp extends Move {
    public static final Stomp INSTANCE = new Stomp();

    public Stomp() {
        super("Stomp", 65, Type.NORMAL, Category.PHYSICAL, 100, 20);
        // == Special Effect (Not Yet Implemented) ==
        // 30% chance to make the target flinch.
        // Deals double damage to a target that has used Minimize.
    }
}
