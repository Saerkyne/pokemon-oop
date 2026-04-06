package pokemonGame.moves;
import pokemonGame.core.TypeChart.Category;
import pokemonGame.core.TypeChart.Type;
import pokemonGame.model.Move;

public class BoneClub extends Move {
    public static final BoneClub INSTANCE = new BoneClub();

    public BoneClub() {
        super("Bone Club", 65, Type.GROUND,
        Category.PHYSICAL, 85, 20);
        // == Special Effect (Not Yet Implemented) ==
        // 10% chance to make the target flinch.
    }

}
