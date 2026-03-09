package pokemonGame.mons;
import pokemonGame.Pokemon;
import pokemonGame.LearnsetEntry;
import java.util.List;

public class Snorlax extends Pokemon {

    private static final List<LearnsetEntry> LEARNSET = new java.util.ArrayList<>();
    static {
        // Learnset will be populated manually later
    }

    public Snorlax(String name) {
        super("Snorlax", 143, "Normal", null,
            5, 160, 110, 65,
            65, 110, 30);

        this.setName(name);

        int[] evYield = {2, 0, 0, 0, 0, 0}; // Snorlax yields 2 EV points in HP when defeated
        this.setEvYield(evYield);
        this.generateRandomIVs();
        this.calculateCurrentStats();
    }

    @Override
    public List<LearnsetEntry> getLearnset() {
        return LEARNSET;
    }
}
