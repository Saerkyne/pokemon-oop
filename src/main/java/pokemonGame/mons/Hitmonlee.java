package pokemonGame.mons;
import pokemonGame.Pokemon;
import pokemonGame.LearnsetEntry;
import java.util.List;

public class Hitmonlee extends Pokemon {

    private static final List<LearnsetEntry> LEARNSET = new java.util.ArrayList<>();
    static {
        // Learnset will be populated manually later
    }

    public Hitmonlee(String name) {
        super(
            "Hitmonlee",
            106,
            "Fighting",
            null,
            5,
            50,
            120,
            53,
            35,
            110,
            87
        );

        this.setName(name);

        int[] evYield = {0, 2, 0, 0, 0, 0}; // Hitmonlee yields 2 EV points in Attack when defeated
        this.setEvYield(evYield);
        this.generateRandomIVs();
        this.calculateCurrentStats();
    }

    @Override
    public List<LearnsetEntry> getLearnset() {
        return LEARNSET;
    }
}
