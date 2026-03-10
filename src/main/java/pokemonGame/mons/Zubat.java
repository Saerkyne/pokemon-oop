package pokemonGame.mons;
import pokemonGame.Pokemon;
import pokemonGame.LearnsetEntry;
import java.util.List;

public class Zubat extends Pokemon {

    private static final List<LearnsetEntry> LEARNSET = new java.util.ArrayList<>();
    static {
        // Learnset will be populated manually later
    }

    public Zubat(String name) {
        super(
            "Zubat",
            41,
            "Poison",
            "Flying",
            5,
            40,
            45,
            35,
            30,
            40,
            55
        );

        this.setName(name);

        int[] evYield = {0, 0, 0, 0, 0, 1}; // Zubat yields 1 EV point in Speed when defeated
        this.setEvYield(evYield);
        this.generateRandomIVs();
        this.calculateCurrentStats();
    }

    @Override
    public List<LearnsetEntry> getLearnset() {
        return LEARNSET;
    }
}
