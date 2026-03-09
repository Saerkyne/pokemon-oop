package pokemonGame.mons;
import pokemonGame.Pokemon;
import pokemonGame.LearnsetEntry;
import java.util.List;

public class Gastly extends Pokemon {

    private static final List<LearnsetEntry> LEARNSET = new java.util.ArrayList<>();
    static {
        // Learnset will be populated manually later
    }

    public Gastly(String name) {
        super(
            "Gastly",
            92,
            "Ghost",
            "Poison",
            5,
            30,
            35,
            30,
            100,
            35,
            80
        );

        this.setName(name);

        int[] evYield = {0, 0, 0, 1, 0, 0}; // Gastly yields 1 EV point in Special Attack when defeated
        this.setEvYield(evYield);
        this.generateRandomIVs();
        this.calculateCurrentStats();
    }

    @Override
    public List<LearnsetEntry> getLearnset() {
        return LEARNSET;
    }
}
