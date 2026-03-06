package pokemonGame.mons;
import pokemonGame.Pokemon;
import pokemonGame.LearnsetEntry;
import java.util.List;

public class Graveler extends Pokemon {

    private static final List<LearnsetEntry> LEARNSET = new java.util.ArrayList<>();
    static {
        // Learnset will be populated manually later
    }

    public Graveler(String name) {
        super("Graveler", 75, "Rock", "Ground",
            5, 55, 95, 115,
            45, 45, 35);

        this.setName(name);

        int[] evYield = {0, 0, 2, 0, 0, 0}; // Graveler yields 2 EV points in Defense when defeated
        this.setEvYield(evYield);
        this.generateRandomIVs();
        this.calculateCurrentStats();
    }

    @Override
    public List<LearnsetEntry> getLearnset() {
        return LEARNSET;
    }
}
