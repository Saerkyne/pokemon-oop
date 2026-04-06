package pokemonGame.moves;
import pokemonGame.core.TypeChart.Category;
import pokemonGame.core.TypeChart.Type;
import pokemonGame.model.Move;

public class DoubleTeam extends Move {
    public static final DoubleTeam INSTANCE = new DoubleTeam();

    public DoubleTeam() {
        super("Double Team", 0, Type.NORMAL, Category.STATUS, 0, 15);
        // == Special Effect (Not Yet Implemented) ==
        // Raises the user's evasion by 1 stage.
        // Accuracy: 0 is a placeholder — PokemonDB lists no accuracy (self-targeting).
    }
}
