package pokemonGame.moves;
import pokemonGame.Move;

public class DefenseCurl extends Move {
    public static final DefenseCurl INSTANCE = new DefenseCurl();

    public DefenseCurl() {
        super("Defense Curl", 0, "Normal", "Status", 0, 40);
        // == Special Effect (Not Yet Implemented) ==
        // Raises the user's Defense by 1 stage.
        // Doubles the power of the user's Rollout and Ice Ball.
        // Accuracy: 0 is a placeholder — PokemonDB lists no accuracy (self-targeting).
    }
}
