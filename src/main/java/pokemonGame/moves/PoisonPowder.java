package pokemonGame.moves;
import pokemonGame.core.TypeChart.Category;
import pokemonGame.core.TypeChart.Type;
import pokemonGame.model.Move;

public class PoisonPowder extends Move {
    public static final PoisonPowder INSTANCE = new PoisonPowder();

    public PoisonPowder() {
        super("Poison Powder", 0, Type.POISON, Category.STATUS, 75, 35);
        // == Special Effect (Not Yet Implemented) ==
        // Poisons the target. Does not affect Grass-type or
        // Poison-type Pokemon. Powder move — blocked by Overcoat.
    }
}
