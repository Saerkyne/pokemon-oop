package pokemonGame.moves;
import pokemonGame.core.TypeChart.Category;
import pokemonGame.core.TypeChart.Type;
import pokemonGame.model.Move;

public class Psychic extends Move {
    public static final Psychic INSTANCE = new Psychic();

    public Psychic() {
        super("Psychic", 90, Type.PSYCHIC, Category.SPECIAL, 100, 10);
        // == Special Effect (Not Yet Implemented) ==
        // 10% chance to lower the target's Sp. Def by 1 stage.
    }
}
