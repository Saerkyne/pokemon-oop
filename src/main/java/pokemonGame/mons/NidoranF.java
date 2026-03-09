package pokemonGame.mons;
import pokemonGame.Pokemon;
import pokemonGame.LearnsetEntry;
import java.util.List;

public class NidoranF extends Pokemon {

    private static final List<LearnsetEntry> LEARNSET = new java.util.ArrayList<>();
    static {
        // Learnset will be populated manually later
    }

    public NidoranF(String name) {
        super("NidoranF", 29, "Poison", null,
            5, 55, 47, 52,
            40, 40, 41);

        this.setName(name);

        int[] evYield = {1, 0, 0, 0, 0, 0}; // NidoranF yields 1 EV point in HP when defeated
        this.setEvYield(evYield);
        this.generateRandomIVs();
        this.calculateCurrentStats();
    }

    @Override
    public List<LearnsetEntry> getLearnset() {
        return LEARNSET;
    }
}
