package pokemonGame.mons;
import pokemonGame.Pokemon;
import pokemonGame.LearnsetEntry;
import java.util.List;

public class Kabuto extends Pokemon {

    private static final List<LearnsetEntry> LEARNSET = new java.util.ArrayList<>();
    static {
        // Learnset will be populated manually later
    }

    public Kabuto(String name) {
        super(
            "Kabuto",
            140,
            "Rock",
            "Water",
            5,
            30,
            80,
            90,
            55,
            45,
            55
        );

        this.setName(name);

        int[] evYield = {0, 0, 1, 0, 0, 0}; // Kabuto yields 1 EV point in Defense when defeated
        this.setEvYield(evYield);
        this.generateRandomIVs();
        this.calculateCurrentStats();
    }

    @Override
    public List<LearnsetEntry> getLearnset() {
        return LEARNSET;
    }
}
