package pokemonGame.moves;
import pokemonGame.Move;

public class Psychic extends Move {
    public static final Psychic INSTANCE = new Psychic();

    public Psychic() {
        super("Psychic", 90, "Psychic", "Special", 100, 10);
        // == Special Effect (Not Yet Implemented) ==
        // 10% chance to lower the target's Sp. Def by 1 stage.
    }
}
