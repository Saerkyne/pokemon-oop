package pokemonGame.moves;
import pokemonGame.Move;

public class Thunder extends Move {
    public static final Thunder INSTANCE = new Thunder();

    public Thunder() {
        super("Thunder", 120, "Electric", "Special", 70, 10);
        // == Special Effect (Not Yet Implemented) ==
        // 30% chance to paralyze the target. Can hit Pokémon using Fly.
        // In rain, bypasses accuracy checks. In harsh sunlight,
        // accuracy drops to 50%.
    }

}
