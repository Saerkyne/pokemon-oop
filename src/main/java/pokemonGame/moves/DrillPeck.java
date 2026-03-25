package pokemonGame.moves;
import pokemonGame.Move;

public class DrillPeck extends Move {
    public static final DrillPeck INSTANCE = new DrillPeck();

    public DrillPeck() {
        super("Drill Peck", 80, "Flying", "Physical", 100, 20);
        // == Special Effect ==
        // No additional effect.
    }
}
