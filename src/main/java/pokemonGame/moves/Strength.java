package pokemonGame.moves;
import pokemonGame.Move;
import pokemonGame.TypeChart.Type;
import pokemonGame.TypeChart.Category;

public class Strength extends Move {
    public static final Strength INSTANCE = new Strength();

    public Strength() {
        super("Strength", 15, Type.NORMAL, Category.PHYSICAL, 80, 15);
        // == Special Effect ==
        // No additional effect.
    }
}
