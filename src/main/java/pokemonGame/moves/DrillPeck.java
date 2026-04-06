package pokemonGame.moves;
import pokemonGame.core.TypeChart.Category;
import pokemonGame.core.TypeChart.Type;
import pokemonGame.model.Move;

public class DrillPeck extends Move {
    public static final DrillPeck INSTANCE = new DrillPeck();

    public DrillPeck() {
        super("Drill Peck", 80, Type.FLYING, Category.PHYSICAL, 100, 20);
        // == Special Effect ==
        // No additional effect.
    }
}
