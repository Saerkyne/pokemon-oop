package pokemonGame.mons;
import pokemonGame.Pokemon;
import pokemonGame.LearnsetEntry;
import java.util.List;

public class Tentacool extends Pokemon {

    private static final List<LearnsetEntry> LEARNSET = new java.util.ArrayList<>();
    static {
        // Learnset will be populated manually later
    }

    public Tentacool(String name) {
        super(
            "Tentacool",
            72,
            "Water",
            "Poison",
            5,
            40,
            40,
            35,
            50,
            100,
            70
        );

        this.setName(name);

        int[] evYield = {0, 0, 0, 0, 1, 0}; // Tentacool yields 1 EV point in Special Defense when defeated
        this.setEvYield(evYield);
        this.generateRandomIVs();
        this.calculateCurrentStats();
    }

    @Override
    public List<LearnsetEntry> getLearnset() {
        return LEARNSET;
    }
}
