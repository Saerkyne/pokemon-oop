package pokemonGame.moves;
import pokemonGame.Move;
import pokemonGame.TypeChart.Type;
import pokemonGame.TypeChart.Category;

public class PoisonSting extends Move {
    public static final PoisonSting INSTANCE = new PoisonSting();

    public PoisonSting() {
        super("Poison Sting", 15, Type.POISON, Category.PHYSICAL, 100, 35);
        // == Special Effect (Not Yet Implemented) ==
        // 30% chance to poison the target.
    }
}
