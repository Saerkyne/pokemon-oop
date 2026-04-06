package pokemonGame.moves;
import pokemonGame.core.TypeChart.Category;
import pokemonGame.core.TypeChart.Type;
import pokemonGame.model.Move;

public class RockSlide extends Move {
    public static final RockSlide INSTANCE = new RockSlide();

    public RockSlide() {
        super("Rock Slide", 75, Type.ROCK, Category.PHYSICAL, 90, 10);
        // == Special Effect (Not Yet Implemented) ==
        // 30% chance to make the target flinch.
    }
}
