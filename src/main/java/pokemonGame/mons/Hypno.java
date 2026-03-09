package pokemonGame.mons;
import pokemonGame.Pokemon;
import pokemonGame.LearnsetEntry;
import java.util.List;

public class Hypno extends Pokemon {

    private static final List<LearnsetEntry> LEARNSET = new java.util.ArrayList<>();
    static {
        // Learnset will be populated manually later
    }

    public Hypno(String name) {
        super(
            "Hypno",
            97,
            "Psychic",
            null,
            5,
            85,
            73,
            70,
            73,
            115,
            67
        );

        this.setName(name);

        int[] evYield = {0, 0, 0, 0, 2, 0}; // Hypno yields 2 EV points in Special Defense when defeated
        this.setEvYield(evYield);
        this.generateRandomIVs();
        this.calculateCurrentStats();
    }

    @Override
    public List<LearnsetEntry> getLearnset() {
        return LEARNSET;
    }
}
