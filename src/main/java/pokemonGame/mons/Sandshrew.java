package pokemonGame.mons;
import pokemonGame.Pokemon;
import pokemonGame.LearnsetEntry;
import java.util.List;

public class Sandshrew extends Pokemon {

    private static final List<LearnsetEntry> LEARNSET = new java.util.ArrayList<>();
    static {
        // Learnset will be populated manually later
    }

    public Sandshrew(String name) {
        super(
            "Sandshrew",
            27,
            "Ground",
            null,
            5,
            50,
            75,
            85,
            20,
            30,
            40
        );

        this.setName(name);

        int[] evYield = {0, 0, 1, 0, 0, 0}; // Sandshrew yields 1 EV point in Defense when defeated
        this.setEvYield(evYield);
        this.generateRandomIVs();
        this.calculateCurrentStats();
    }

    @Override
    public List<LearnsetEntry> getLearnset() {
        return LEARNSET;
    }
}
