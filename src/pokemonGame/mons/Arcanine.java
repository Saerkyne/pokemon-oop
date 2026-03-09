package pokemonGame.mons;
import pokemonGame.Pokemon;
import pokemonGame.LearnsetEntry;
import java.util.List;

public class Arcanine extends Pokemon {

    private static final List<LearnsetEntry> LEARNSET = new java.util.ArrayList<>();
    static {
        // Learnset will be populated manually later
    }

    public Arcanine(String name) {
        super(
            "Arcanine",
            59,
            "Fire",
            null,
            5,
            90,
            110,
            80,
            100,
            80,
            95
        );

        this.setName(name);

        int[] evYield = {0, 2, 0, 0, 0, 0}; // Arcanine yields 2 EV points in Attack when defeated
        this.setEvYield(evYield);
        this.generateRandomIVs();
        this.calculateCurrentStats();
    }

    @Override
    public List<LearnsetEntry> getLearnset() {
        return LEARNSET;
    }
}
