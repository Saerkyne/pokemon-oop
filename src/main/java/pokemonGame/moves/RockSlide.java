package pokemonGame.moves;
import pokemonGame.Move;

public class RockSlide extends Move {
    public static final RockSlide INSTANCE = new RockSlide();

    public RockSlide() {
        super("Rock Slide", 75, "Rock", "Physical", 90, 10);
        // == Special Effect (Not Yet Implemented) ==
        // 30% chance to make the target flinch.
    }
}
