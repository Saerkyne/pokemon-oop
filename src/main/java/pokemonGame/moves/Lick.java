package pokemonGame.moves;
import pokemonGame.Move;
import pokemonGame.TypeChart.Type;
import pokemonGame.TypeChart.Category;

public class Lick extends Move {
    public static final Lick INSTANCE = new Lick();

    public Lick() {
        super("Lick", 30, Type.GHOST, Category.PHYSICAL, 100, 30);
        // == Special Effect (Not Yet Implemented) ==
        // 30% chance to paralyze the target.
    }
}
