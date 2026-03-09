package pokemonGame.mons;
import pokemonGame.Pokemon;
import pokemonGame.LearnsetEntry;
import java.util.List;

public class Goldeen extends Pokemon {

    private static final List<LearnsetEntry> LEARNSET = new java.util.ArrayList<>();
    static {
        // Learnset will be populated manually later
    }

    public Goldeen(String name) {
        super(
            "Goldeen",
            118,
            "Water",
            null,
            5,
            45,
            67,
            60,
            35,
            50,
            63
        );

        this.setName(name);

        int[] evYield = {0, 1, 0, 0, 0, 0}; // Goldeen yields 1 EV point in Attack when defeated
        this.setEvYield(evYield);
        this.generateRandomIVs();
        this.calculateCurrentStats();
    }

    @Override
    public List<LearnsetEntry> getLearnset() {
        return LEARNSET;
    }
}
