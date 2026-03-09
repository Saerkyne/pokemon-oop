package pokemonGame.mons;
import pokemonGame.Pokemon;
import pokemonGame.LearnsetEntry;
import java.util.List;

public class Lapras extends Pokemon {

    private static final List<LearnsetEntry> LEARNSET = new java.util.ArrayList<>();
    static {
        // Learnset will be populated manually later
    }

    public Lapras(String name) {
        super("Lapras", 131, "Water", "Ice",
            5, 130, 85, 80,
            85, 95, 60);

        this.setName(name);

        int[] evYield = {2, 0, 0, 0, 0, 0}; // Lapras yields 2 EV points in HP when defeated
        this.setEvYield(evYield);
        this.generateRandomIVs();
        this.calculateCurrentStats();
    }

    @Override
    public List<LearnsetEntry> getLearnset() {
        return LEARNSET;
    }
}
