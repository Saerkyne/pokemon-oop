package pokemonGame.moves;
import pokemonGame.Move;
import pokemonGame.TypeChart.Type;
import pokemonGame.TypeChart.Category;

public class ThunderWave extends Move {
    public static final ThunderWave INSTANCE = new ThunderWave();

    public ThunderWave() {
        super("ThunderWave", 0, Type.ELECTRIC, Category.STATUS, 0, 20);
        // == Special Effect (Not Yet Implemented) ==
        // Paralyzes the target. Does not affect Electric-type or
        // Ground-type Pokémon.
    }

}
