package pokemonGame.mons;
import pokemonGame.Pokemon;
import pokemonGame.LearnsetEntry;
import java.util.List;

public class NidoranM extends Pokemon {

    private static final List<LearnsetEntry> LEARNSET = new java.util.ArrayList<>();
    static {
        // Learnset will be populated manually later
    }

    public NidoranM(String name) {
        super("NidoranM", 32, "Poison", null,
            5, 46, 57, 40,
            40, 40, 50);

        this.setName(name);

        int[] evYield = {0, 1, 0, 0, 0, 0}; // NidoranM yields 1 EV point in Attack when defeated
        this.setEvYield(evYield);
        this.generateRandomIVs();
        this.calculateCurrentStats();
    }

    @Override
    public List<LearnsetEntry> getLearnset() {
        return LEARNSET;
    }
}
