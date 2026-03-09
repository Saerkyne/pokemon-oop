package pokemonGame.mons;
import pokemonGame.Pokemon;
import pokemonGame.LearnsetEntry;
import java.util.List;

public class Electabuzz extends Pokemon {

    private static final List<LearnsetEntry> LEARNSET = new java.util.ArrayList<>();
    static {
        // Learnset will be populated manually later
    }

    public Electabuzz(String name) {
        super(
            "Electabuzz",
            125,
            "Electric",
            null,
            5,
            65,
            83,
            57,
            95,
            85,
            105
        );

        this.setName(name);

        int[] evYield = {0, 0, 0, 0, 0, 2}; // Electabuzz yields 2 EV points in Speed when defeated
        this.setEvYield(evYield);
        this.generateRandomIVs();
        this.calculateCurrentStats();
    }

    @Override
    public List<LearnsetEntry> getLearnset() {
        return LEARNSET;
    }
}
