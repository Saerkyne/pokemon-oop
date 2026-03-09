package pokemonGame.mons;
import pokemonGame.Pokemon;
import pokemonGame.LearnsetEntry;
import java.util.List;

public class Magnemite extends Pokemon {

    private static final List<LearnsetEntry> LEARNSET = new java.util.ArrayList<>();
    static {
        // Learnset will be populated manually later
    }

    public Magnemite(String name) {
        super("Magnemite", 81, "Electric", "Steel",
            5, 25, 35, 70,
            95, 55, 45);

        this.setName(name);

        int[] evYield = {0, 0, 0, 1, 0, 0}; // Magnemite yields 1 EV point in Special Attack when defeated
        this.setEvYield(evYield);
        this.generateRandomIVs();
        this.calculateCurrentStats();
    }

    @Override
    public List<LearnsetEntry> getLearnset() {
        return LEARNSET;
    }
}
