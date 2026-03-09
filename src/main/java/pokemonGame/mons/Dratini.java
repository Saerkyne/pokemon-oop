package pokemonGame.mons;
import pokemonGame.Pokemon;
import pokemonGame.LearnsetEntry;
import java.util.List;

public class Dratini extends Pokemon {

    private static final List<LearnsetEntry> LEARNSET = new java.util.ArrayList<>();
    static {
        // Learnset will be populated manually later
    }

    public Dratini(String name) {
        super(
            "Dratini",
            147,
            "Dragon",
            null,
            5,
            41,
            64,
            45,
            50,
            50,
            50
        );

        this.setName(name);

        int[] evYield = {0, 1, 0, 0, 0, 0}; // Dratini yields 1 EV point in Attack when defeated
        this.setEvYield(evYield);
        this.generateRandomIVs();
        this.calculateCurrentStats();
    }

    @Override
    public List<LearnsetEntry> getLearnset() {
        return LEARNSET;
    }
}
