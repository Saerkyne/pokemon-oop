package pokemonGame.moves;
import pokemonGame.Move;
import pokemonGame.TypeChart.Type;
import pokemonGame.TypeChart.Category;

public class StunSpore extends Move {
    public static final StunSpore INSTANCE = new StunSpore();

    public StunSpore() {
        super("Stun Spore", 0, Type.GRASS, Category.STATUS, 75, 30);
        // == Special Effect (Not Yet Implemented) ==
        // Paralyzes the target. Does not affect Electric-type or
        // Grass-type Pokemon. Powder move — blocked by Overcoat.
    }
}
