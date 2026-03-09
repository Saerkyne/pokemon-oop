package pokemonGame.mons;
import pokemonGame.Pokemon;
import pokemonGame.LearnsetEntry;
import java.util.List;

public class Omanyte extends Pokemon {

    private static final List<LearnsetEntry> LEARNSET = new java.util.ArrayList<>();
    static {
        // Learnset will be populated manually later
    }

    public Omanyte(String name) {
        super("Omanyte", 138, "Rock", "Water",
            5, 35, 40, 100,
            90, 55, 35);

        this.setName(name);

        int[] evYield = {0, 0, 1, 0, 0, 0}; // Omanyte yields 1 EV point in Defense when defeated
        this.setEvYield(evYield);
        this.generateRandomIVs();
        this.calculateCurrentStats();
    }

    @Override
    public List<LearnsetEntry> getLearnset() {
        return LEARNSET;
    }
}
