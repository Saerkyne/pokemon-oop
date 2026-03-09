package pokemonGame.mons;
import pokemonGame.Pokemon;
import pokemonGame.LearnsetEntry;
import java.util.List;

public class Koffing extends Pokemon {

    private static final List<LearnsetEntry> LEARNSET = new java.util.ArrayList<>();
    static {
        // Learnset will be populated manually later
    }

    public Koffing(String name) {
        super(
            "Koffing",
            109,
            "Poison",
            null,
            5,
            40,
            65,
            95,
            60,
            45,
            35
        );

        this.setName(name);

        int[] evYield = {0, 0, 1, 0, 0, 0}; // Koffing yields 1 EV point in Defense when defeated
        this.setEvYield(evYield);
        this.generateRandomIVs();
        this.calculateCurrentStats();
    }

    @Override
    public List<LearnsetEntry> getLearnset() {
        return LEARNSET;
    }
}
