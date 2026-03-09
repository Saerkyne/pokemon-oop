package pokemonGame.mons;
import pokemonGame.Pokemon;
import pokemonGame.LearnsetEntry;
import java.util.List;

public class Rhyhorn extends Pokemon {

    private static final List<LearnsetEntry> LEARNSET = new java.util.ArrayList<>();
    static {
        // Learnset will be populated manually later
    }

    public Rhyhorn(String name) {
        super("Rhyhorn", 111, "Ground", "Rock",
            5, 80, 85, 95,
            30, 30, 25);

        this.setName(name);

        int[] evYield = {0, 0, 1, 0, 0, 0}; // Rhyhorn yields 1 EV point in Defense when defeated
        this.setEvYield(evYield);
        this.generateRandomIVs();
        this.calculateCurrentStats();
    }

    @Override
    public List<LearnsetEntry> getLearnset() {
        return LEARNSET;
    }
}
