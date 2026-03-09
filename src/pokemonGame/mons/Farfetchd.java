package pokemonGame.mons;
import pokemonGame.Pokemon;
import pokemonGame.LearnsetEntry;
import java.util.List;

public class Farfetchd extends Pokemon {

    private static final List<LearnsetEntry> LEARNSET = new java.util.ArrayList<>();
    static {
        // Learnset will be populated manually later
    }

    public Farfetchd(String name) {
        super(
            "Farfetch'd",
            83,
            "Normal",
            "Flying",
            5,
            52,
            90,
            55,
            58,
            62,
            60
        );

        this.setName(name);

        int[] evYield = {0, 1, 0, 0, 0, 0}; // Farfetch'd yields 1 EV point in Attack when defeated
        this.setEvYield(evYield);
        this.generateRandomIVs();
        this.calculateCurrentStats();
    }

    @Override
    public List<LearnsetEntry> getLearnset() {
        return LEARNSET;
    }
}
