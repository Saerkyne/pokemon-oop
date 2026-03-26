package pokemonGame.moves;
import pokemonGame.Move;
import pokemonGame.TypeChart.Type;
import pokemonGame.TypeChart.Category;

public class EggBomb extends Move {
    public static final EggBomb INSTANCE = new EggBomb();

    public EggBomb() {
        super("Egg Bomb", 100, Type.NORMAL, Category.PHYSICAL, 75, 10);
        // == Special Effect ==
        // No additional effect.
    }
}
