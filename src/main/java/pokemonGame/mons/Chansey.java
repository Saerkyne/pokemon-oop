package pokemonGame.mons;
import pokemonGame.Pokemon;
import pokemonGame.LearnsetEntry;
import java.util.List;

public class Chansey extends Pokemon {

    private static final List<LearnsetEntry> LEARNSET = new java.util.ArrayList<>();
    static {
        // Learnset will be populated manually later
    }

    public Chansey(String name) {
        super(
            "Chansey",
            113,
            "Normal",
            null,
            5,
            250,
            5,
            5,
            35,
            105,
            50
        );

        this.setName(name);

        int[] evYield = {2, 0, 0, 0, 0, 0}; // Chansey yields 2 EV points in HP when defeated
        this.setEvYield(evYield);
        this.generateRandomIVs();
        this.calculateCurrentStats();
    }

    @Override
    public List<LearnsetEntry> getLearnset() {
        return LEARNSET;
    }
}
