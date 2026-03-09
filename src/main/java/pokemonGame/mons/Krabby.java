package pokemonGame.mons;
import pokemonGame.Pokemon;
import pokemonGame.LearnsetEntry;
import java.util.List;

public class Krabby extends Pokemon {

    private static final List<LearnsetEntry> LEARNSET = new java.util.ArrayList<>();
    static {
        // Learnset will be populated manually later
    }

    public Krabby(String name) {
        super(
            "Krabby",
            98,
            "Water",
            null,
            5,
            30,
            105,
            90,
            25,
            25,
            50
        );

        this.setName(name);

        int[] evYield = {0, 1, 0, 0, 0, 0}; // Krabby yields 1 EV point in Attack when defeated
        this.setEvYield(evYield);
        this.generateRandomIVs();
        this.calculateCurrentStats();
    }

    @Override
    public List<LearnsetEntry> getLearnset() {
        return LEARNSET;
    }
}
