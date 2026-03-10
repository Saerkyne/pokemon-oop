package pokemonGame.mons;
import pokemonGame.Pokemon;
import pokemonGame.LearnsetEntry;
import java.util.List;

public class Muk extends Pokemon {

    private static final List<LearnsetEntry> LEARNSET = new java.util.ArrayList<>();
    static {
        // Learnset will be populated manually later
    }

    public Muk(String name) {
        super(
            "Muk",
            89,
            "Poison",
            null,
            5,
            105,
            105,
            75,
            65,
            100,
            50
        );

        this.setName(name);

        int[] evYield = {1, 1, 0, 0, 0, 0}; // Muk yields 1 EV point in HP and 1 EV point in Attack when defeated
        this.setEvYield(evYield);
        this.generateRandomIVs();
        this.calculateCurrentStats();
    }

    @Override
    public List<LearnsetEntry> getLearnset() {
        return LEARNSET;
    }
}
