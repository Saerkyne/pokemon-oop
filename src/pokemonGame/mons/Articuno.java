package pokemonGame.mons;
import pokemonGame.Pokemon;
import pokemonGame.LearnsetEntry;
import java.util.List;

public class Articuno extends Pokemon {

    private static final List<LearnsetEntry> LEARNSET = new java.util.ArrayList<>();
    static {
        // Learnset will be populated manually later
    }

    public Articuno(String name) {
        super(
            "Articuno",
            144,
            "Ice",
            "Flying",
            5,
            90,
            85,
            100,
            95,
            125,
            85
        );

        this.setName(name);

        int[] evYield = {0, 0, 0, 0, 3, 0}; // Articuno yields 3 EV points in Special Defense when defeated
        this.setEvYield(evYield);
        this.generateRandomIVs();
        this.calculateCurrentStats();
    }

    @Override
    public List<LearnsetEntry> getLearnset() {
        return LEARNSET;
    }
}
