package pokemonGame.moves;
import pokemonGame.core.TypeChart.Category;
import pokemonGame.core.TypeChart.Type;
import pokemonGame.model.Move;

public class LeechSeed extends Move {
    public static final LeechSeed INSTANCE = new LeechSeed();

    public LeechSeed() {
        super("Leech Seed", 0, Type.GRASS, Category.STATUS, 90, 10);
        // == Special Effect (Not Yet Implemented) ==
        // Seeds the target, draining 1/8 of its max HP each turn
        // and restoring that amount to the user. Does not affect
        // Grass-type Pokémon.
    }
}
