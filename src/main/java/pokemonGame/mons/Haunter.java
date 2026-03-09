package pokemonGame.mons;
import pokemonGame.Pokemon;
import pokemonGame.LearnsetEntry;
import java.util.List;

public class Haunter extends Pokemon {

    private static final List<LearnsetEntry> LEARNSET = new java.util.ArrayList<>();
    static {
        // Learnset will be populated manually later
    }

    public Haunter(String name) {
        super(
            "Haunter",
            93,
            "Ghost",
            "Poison",
            5,
            45,
            50,
            45,
            115,
            55,
            95
        );

        this.setName(name);

        int[] evYield = {0, 0, 0, 2, 0, 0}; // Haunter yields 2 EV points in Special Attack when defeated
        this.setEvYield(evYield);
        this.generateRandomIVs();
        this.calculateCurrentStats();
    }

    @Override
    public List<LearnsetEntry> getLearnset() {
        return LEARNSET;
    }
}
