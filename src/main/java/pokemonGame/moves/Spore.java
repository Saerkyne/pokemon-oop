package pokemonGame.moves;
import pokemonGame.Move;
import pokemonGame.TypeChart.Type;
import pokemonGame.TypeChart.Category;

public class Spore extends Move {
    public static final Spore INSTANCE = new Spore();

    public Spore() {
        super("Spore", 0, Type.GRASS, Category.STATUS, 100, 15);
        // == Special Effect (Not Yet Implemented) ==
        // Puts the target to sleep. Does not affect Grass-type Pokemon.
        // Powder move — blocked by Overcoat.
    }
}
