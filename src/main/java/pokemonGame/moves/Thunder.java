package pokemonGame.moves;
import pokemonGame.core.TypeChart.Category;
import pokemonGame.core.TypeChart.Type;
import pokemonGame.model.Move;

public class Thunder extends Move {
    public static final Thunder INSTANCE = new Thunder();

    public Thunder() {
        super("Thunder", 120, Type.ELECTRIC, Category.SPECIAL, 70, 10);
        // == Special Effect (Not Yet Implemented) ==
        // 30% chance to paralyze the target. Can hit Pokémon using Fly.
        // In rain, bypasses accuracy checks. In harsh sunlight,
        // accuracy drops to 50%.
    }

}
