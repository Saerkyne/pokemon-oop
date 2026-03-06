package pokemonGame.mons;
import pokemonGame.Pokemon;
import pokemonGame.LearnsetEntry;
import java.util.List;

public class Dugtrio extends Pokemon {

    private static final List<LearnsetEntry> LEARNSET = new java.util.ArrayList<>();
    static {
        // Learnset will be populated manually later
    }

    public Dugtrio(String name) {
        super("Dugtrio", 51, "Ground", null,
            5, 35, 100, 50,
            50, 70, 120);

        this.setName(name);

        int[] evYield = {0, 0, 0, 0, 0, 2}; // Dugtrio yields 2 EV points in Speed when defeated
        this.setEvYield(evYield);
        this.generateRandomIVs();
        this.calculateCurrentStats();
    }

    @Override
    public List<LearnsetEntry> getLearnset() {
        return LEARNSET;
    }
}
