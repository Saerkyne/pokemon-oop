package pokemonGame.moves;
import pokemonGame.core.TypeChart.Category;
import pokemonGame.core.TypeChart.Type;
import pokemonGame.model.Move;

public class ThunderWave extends Move {
    public static final ThunderWave INSTANCE = new ThunderWave();

    public ThunderWave() {
        super("ThunderWave", 0, Type.ELECTRIC, Category.STATUS, 0, 20);
        // == Special Effect (Not Yet Implemented) ==
        // Paralyzes the target. Does not affect Electric-type or
        // Ground-type Pokémon.
    }

}
