package pokemonGame.moves;
import pokemonGame.Move;

public class RollingKick extends Move {
    public static final RollingKick INSTANCE = new RollingKick();

    public RollingKick() {
        super("Rolling Kick", 60, "Fighting", "Physical", 85, 15);
        // == Special Effect (Not Yet Implemented) ==
        // 30% chance to make the target flinch.
    }
}
