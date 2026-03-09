package pokemonGame.mons;
import pokemonGame.Pokemon;
import pokemonGame.LearnsetEntry;
import java.util.List;

public class Gyarados extends Pokemon {

    private static final List<LearnsetEntry> LEARNSET = new java.util.ArrayList<>();
    static {
        // Learnset will be populated manually later
    }

    public Gyarados(String name) {
        super(
            "Gyarados",
            130,
            "Water",
            "Flying",
            5,
            95,
            125,
            79,
            60,
            100,
            81
        );

        this.setName(name);

        int[] evYield = {0, 2, 0, 0, 0, 0}; // Gyarados yields 2 EV points in Attack when defeated
        this.setEvYield(evYield);
        this.generateRandomIVs();
        this.calculateCurrentStats();
    }

    @Override
    public List<LearnsetEntry> getLearnset() {
        return LEARNSET;
    }
}
