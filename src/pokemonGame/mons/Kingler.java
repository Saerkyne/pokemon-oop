package pokemonGame.mons;
import pokemonGame.Pokemon;
import pokemonGame.LearnsetEntry;
import java.util.List;

public class Kingler extends Pokemon {

    private static final List<LearnsetEntry> LEARNSET = new java.util.ArrayList<>();
    static {
        // Learnset will be populated manually later
    }

    public Kingler(String name) {
        super(
            "Kingler",
            99,
            "Water",
            null,
            5,
            55,
            130,
            115,
            50,
            50,
            75
        );

        this.setName(name);

        int[] evYield = {0, 2, 0, 0, 0, 0}; // Kingler yields 2 EV points in Attack when defeated
        this.setEvYield(evYield);
        this.generateRandomIVs();
        this.calculateCurrentStats();
    }

    @Override
    public List<LearnsetEntry> getLearnset() {
        return LEARNSET;
    }
}
