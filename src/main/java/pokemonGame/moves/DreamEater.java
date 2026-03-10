package pokemonGame.moves;
import pokemonGame.Move;

public class DreamEater extends Move {
    public DreamEater() {
        super("Dream Eater", 100, "Psychic", "Special", 100, 15);
        // == Special Effect (Not Yet Implemented) ==
        // Only works on sleeping targets. Fails if the target is awake.
        // User recovers 50% of the damage dealt.
    }
}
