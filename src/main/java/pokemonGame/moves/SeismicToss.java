package pokemonGame.moves;
import pokemonGame.Move;

public class SeismicToss extends Move {
    public static final SeismicToss INSTANCE = new SeismicToss();

    public SeismicToss() {
        super("Seismic Toss", 0, "Fighting", "Physical", 100, 20);
        // == Special Effect (Not Yet Implemented) ==
        // Deals damage equal to the user's level.
        // Power: 0 is a placeholder — PokemonDB lists no base power (level-based damage).
    }
}
