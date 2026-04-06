package pokemonGame.moves;
import pokemonGame.core.TypeChart.Category;
import pokemonGame.core.TypeChart.Type;
import pokemonGame.model.Move;

public class FurySwipes extends Move {
    public static final FurySwipes INSTANCE = new FurySwipes();

    public FurySwipes() {
        super("Fury Swipes", 18, Type.NORMAL, Category.PHYSICAL, 80, 15);
        // == Special Effect (Not Yet Implemented) ==
        // Hits 2-5 times in one turn.
    }
}
