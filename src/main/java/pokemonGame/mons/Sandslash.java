package pokemonGame.mons;
import pokemonGame.Pokemon;
import pokemonGame.LearnsetEntry;
import java.util.List;

public class Sandslash extends Pokemon {

    private static final List<LearnsetEntry> LEARNSET = new java.util.ArrayList<>();
    static {
        // Learnset will be populated manually later
    }

    public Sandslash(String name) {
        super(
            "Sandslash",
            28,
            "Ground",
            null,
            5,
            75,
            100,
            110,
            45,
            55,
            65
        );

        this.setName(name);

        int[] evYield = {0, 0, 2, 0, 0, 0}; // Sandslash yields 2 EV points in Defense when defeated
        this.setEvYield(evYield);
        this.generateRandomIVs();
        this.calculateCurrentStats();
    }

    @Override
    public List<LearnsetEntry> getLearnset() {
        return LEARNSET;
    }
}
