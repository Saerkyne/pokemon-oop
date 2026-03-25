package pokemonGame.moves;
import pokemonGame.Move;

public class BoneClub extends Move {
    public static final BoneClub INSTANCE = new BoneClub();

    public BoneClub() {
        super("Bone Club", 65, "Ground",
        "Physical", 85, 20);
        // == Special Effect (Not Yet Implemented) ==
        // 10% chance to make the target flinch.
    }

}
