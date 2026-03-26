package pokemonGame.moves;
import pokemonGame.Move;
import pokemonGame.TypeChart.Type;
import pokemonGame.TypeChart.Category;

public class Psywave extends Move {
    public static final Psywave INSTANCE = new Psywave();

    public Psywave() {
        super("Psywave", 0, Type.PSYCHIC, Category.SPECIAL, 100, 15);
        // == Special Effect (Not Yet Implemented) ==
        // Deals random damage between 50% and 150% of the user's level.
        // Power: 0 is a placeholder — PokemonDB lists no base power (variable damage).
    }
}
