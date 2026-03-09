package pokemonGame.mons;
import pokemonGame.Pokemon;
import pokemonGame.LearnsetEntry;
import java.util.List;

public class Ninetales extends Pokemon {

    private static final List<LearnsetEntry> LEARNSET = new java.util.ArrayList<>();
    static {
        // Learnset will be populated manually later
    }

    public Ninetales(String name) {
        super("Ninetales", 38, "Fire", null,
            5, 73, 76, 75,
            81, 100, 100);

        this.setName(name);

        int[] evYield = {0, 0, 0, 0, 1, 1}; // Ninetales yields 1 EV point in Special Defense and 1 EV point in Speed when defeated
        this.setEvYield(evYield);
        this.generateRandomIVs();
        this.calculateCurrentStats();
    }

    @Override
    public List<LearnsetEntry> getLearnset() {
        return LEARNSET;
    }
}
