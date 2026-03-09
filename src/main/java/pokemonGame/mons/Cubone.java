package pokemonGame.mons;
import pokemonGame.Pokemon;
import pokemonGame.LearnsetEntry;
import java.util.List;

public class Cubone extends Pokemon {

    private static final List<LearnsetEntry> LEARNSET = new java.util.ArrayList<>();
    static {
        // Learnset will be populated manually later
    }

    public Cubone(String name) {
        super(
            "Cubone",
            104,
            "Ground",
            null,
            5,
            50,
            50,
            95,
            40,
            50,
            35
        );

        this.setName(name);

        int[] evYield = {0, 0, 1, 0, 0, 0}; // Cubone yields 1 EV point in Defense when defeated
        this.setEvYield(evYield);
        this.generateRandomIVs();
        this.calculateCurrentStats();
    }

    @Override
    public List<LearnsetEntry> getLearnset() {
        return LEARNSET;
    }
}
