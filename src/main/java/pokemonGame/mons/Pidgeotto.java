package pokemonGame.mons;
import pokemonGame.Pokemon;
import pokemonGame.LearnsetEntry;
import java.util.List;

public class Pidgeotto extends Pokemon {

    private static final List<LearnsetEntry> LEARNSET = new java.util.ArrayList<>();
    static {
        // Learnset will be populated manually later
    }

    public Pidgeotto(String name) {
        super("Pidgeotto", 17, "Normal", "Flying",
            5, 63, 60, 55,
            50, 50, 71);

        this.setName(name);

        int[] evYield = {0, 0, 0, 0, 0, 2}; // Pidgeotto yields 2 EV points in Speed when defeated
        this.setEvYield(evYield);
        this.generateRandomIVs();
        this.calculateCurrentStats();
    }

    @Override
    public List<LearnsetEntry> getLearnset() {
        return LEARNSET;
    }
}
