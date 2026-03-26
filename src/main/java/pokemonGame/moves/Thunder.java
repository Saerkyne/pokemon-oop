package pokemonGame.moves;
import pokemonGame.Move;
import pokemonGame.TypeChart.Type;
import pokemonGame.TypeChart.Category;

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
