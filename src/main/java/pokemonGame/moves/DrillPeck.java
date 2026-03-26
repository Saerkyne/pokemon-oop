package pokemonGame.moves;
import pokemonGame.Move;
import pokemonGame.TypeChart.Type;
import pokemonGame.TypeChart.Category;

public class DrillPeck extends Move {
    public static final DrillPeck INSTANCE = new DrillPeck();

    public DrillPeck() {
        super("Drill Peck", 80, Type.FLYING, Category.PHYSICAL, 100, 20);
        // == Special Effect ==
        // No additional effect.
    }
}
