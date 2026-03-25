package pokemonGame.moves;
import pokemonGame.Move;

public class AcidArmor extends Move {
    public static final AcidArmor INSTANCE = new AcidArmor();

    public AcidArmor() {
        super("Acid Armor", 0, "Poison",
        "Status", 0, 20);
        // == Special Effect (Not Yet Implemented) ==
        // Raises the user's Defense by 2 stages.
    }

}
