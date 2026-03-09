package pokemonGame.mons;
import pokemonGame.Pokemon;
import pokemonGame.LearnsetEntry;
import java.util.List;

public class Rhydon extends Pokemon {

    private static final List<LearnsetEntry> LEARNSET = new java.util.ArrayList<>();
    static {
        // Learnset will be populated manually later
    }

    public Rhydon(String name) {
        super("Rhydon", 112, "Ground", "Rock",
            5, 105, 130, 120,
            45, 45, 40);

        this.setName(name);

        int[] evYield = {0, 2, 0, 0, 0, 0}; // Rhydon yields 2 EV points in Attack when defeated
        this.setEvYield(evYield);
        this.generateRandomIVs();
        this.calculateCurrentStats();
    }

    @Override
    public List<LearnsetEntry> getLearnset() {
        return LEARNSET;
    }
}
