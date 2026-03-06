package pokemonGame.mons;
import pokemonGame.Pokemon;
import pokemonGame.LearnsetEntry;
import java.util.List;

public class Geodude extends Pokemon {

    private static final List<LearnsetEntry> LEARNSET = new java.util.ArrayList<>();
    static {
        // Learnset will be populated manually later
    }

    public Geodude(String name) {
        super("Geodude", 74, "Rock", "Ground",
            5, 40, 80, 100,
            30, 30, 20);

        this.setName(name);

        int[] evYield = {0, 0, 1, 0, 0, 0}; // Geodude yields 1 EV point in Defense when defeated
        this.setEvYield(evYield);
        this.generateRandomIVs();
        this.calculateCurrentStats();
    }

    @Override
    public List<LearnsetEntry> getLearnset() {
        return LEARNSET;
    }
}
