package pokemonGame.mons;
import pokemonGame.Pokemon;
import pokemonGame.LearnsetEntry;
import java.util.List;

public class Pinsir extends Pokemon {

    private static final List<LearnsetEntry> LEARNSET = new java.util.ArrayList<>();
    static {
        // Learnset will be populated manually later
    }

    public Pinsir(String name) {
        super(
            "Pinsir",
            127,
            "Bug",
            null,
            5,
            65,
            125,
            100,
            55,
            70,
            85
        );

        this.setName(name);

        int[] evYield = {0, 2, 0, 0, 0, 0}; // Pinsir yields 2 EV points in Attack when defeated
        this.setEvYield(evYield);
        this.generateRandomIVs();
        this.calculateCurrentStats();
    }

    @Override
    public List<LearnsetEntry> getLearnset() {
        return LEARNSET;
    }
}
