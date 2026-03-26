package pokemonGame.moves;
import pokemonGame.Move;
import pokemonGame.TypeChart.Type;
import pokemonGame.TypeChart.Category;

public class LeechLife extends Move {
    public static final LeechLife INSTANCE = new LeechLife();

    public LeechLife() {
        super("Leech Life", 80, Type.BUG, Category.PHYSICAL, 100, 10);
        // == Special Effect (Not Yet Implemented) ==
        // User recovers 50% of the damage dealt to the target.
    }
}
