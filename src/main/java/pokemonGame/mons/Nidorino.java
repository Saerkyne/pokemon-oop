package pokemonGame.mons;
import pokemonGame.Pokemon;
import pokemonGame.LearnsetEntry;
import java.util.List;

public class Nidorino extends Pokemon {

    private static final List<LearnsetEntry> LEARNSET = new java.util.ArrayList<>();
    static {
        // Learnset will be populated manually later
    }

    public Nidorino(String name) {
        super("Nidorino", 33, "Poison", null,
            5, 61, 72, 57,
            55, 55, 65);

        this.setName(name);

        int[] evYield = {0, 2, 0, 0, 0, 0}; // Nidorino yields 2 EV points in Attack when defeated
        this.setEvYield(evYield);
        this.generateRandomIVs();
        this.calculateCurrentStats();
    }

    @Override
    public List<LearnsetEntry> getLearnset() {
        return LEARNSET;
    }
}
