package pokemonGame.mons;
import pokemonGame.Pokemon;
import pokemonGame.LearnsetEntry;
import java.util.List;

public class Tentacruel extends Pokemon {

    private static final List<LearnsetEntry> LEARNSET = new java.util.ArrayList<>();
    static {
        // Learnset will be populated manually later
    }

    public Tentacruel(String name) {
        super(
            "Tentacruel",
            73,
            "Water",
            "Poison",
            5,
            80,
            70,
            65,
            80,
            120,
            100
        );

        this.setName(name);

        int[] evYield = {0, 0, 0, 0, 2, 0}; // Tentacruel yields 2 EV points in Special Defense when defeated
        this.setEvYield(evYield);
        this.generateRandomIVs();
        this.calculateCurrentStats();
    }

    @Override
    public List<LearnsetEntry> getLearnset() {
        return LEARNSET;
    }
}
