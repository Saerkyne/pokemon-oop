package pokemonGame.mons;
import pokemonGame.Pokemon;
import pokemonGame.LearnsetEntry;
import java.util.List;

public class Vaporeon extends Pokemon {

    private static final List<LearnsetEntry> LEARNSET = new java.util.ArrayList<>();
    static {
        // Learnset will be populated manually later
    }

    public Vaporeon(String name) {
        super("Vaporeon", 134, "Water", null,
            5, 130, 65, 60,
            110, 95, 65);

        this.setName(name);

        int[] evYield = {2, 0, 0, 0, 0, 0}; // Vaporeon yields 2 EV points in HP when defeated
        this.setEvYield(evYield);
        this.generateRandomIVs();
        this.calculateCurrentStats();
    }

    @Override
    public List<LearnsetEntry> getLearnset() {
        return LEARNSET;
    }
}
