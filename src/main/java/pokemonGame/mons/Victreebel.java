package pokemonGame.mons;
import pokemonGame.Pokemon;
import pokemonGame.LearnsetEntry;
import java.util.List;

public class Victreebel extends Pokemon {

    private static final List<LearnsetEntry> LEARNSET = new java.util.ArrayList<>();
    static {
        // Learnset will be populated manually later
    }

    public Victreebel(String name) {
        super("Victreebel", 71, "Grass", "Poison",
            5, 80, 105, 65,
            100, 70, 70);

        this.setName(name);

        int[] evYield = {0, 3, 0, 0, 0, 0}; // Victreebel yields 3 EV points in Attack when defeated
        this.setEvYield(evYield);
        this.generateRandomIVs();
        this.calculateCurrentStats();
    }

    @Override
    public List<LearnsetEntry> getLearnset() {
        return LEARNSET;
    }
}
