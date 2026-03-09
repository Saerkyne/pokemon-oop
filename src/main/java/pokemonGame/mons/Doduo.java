package pokemonGame.mons;
import pokemonGame.Pokemon;
import pokemonGame.LearnsetEntry;
import java.util.List;

public class Doduo extends Pokemon {

    private static final List<LearnsetEntry> LEARNSET = new java.util.ArrayList<>();
    static {
        // Learnset will be populated manually later
    }

    public Doduo(String name) {
        super(
            "Doduo",
            84,
            "Normal",
            "Flying",
            5,
            35,
            85,
            45,
            35,
            35,
            75
        );

        this.setName(name);

        int[] evYield = {0, 1, 0, 0, 0, 0}; // Doduo yields 1 EV point in Attack when defeated
        this.setEvYield(evYield);
        this.generateRandomIVs();
        this.calculateCurrentStats();
    }

    @Override
    public List<LearnsetEntry> getLearnset() {
        return LEARNSET;
    }
}
