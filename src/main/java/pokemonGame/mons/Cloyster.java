package pokemonGame.mons;
import pokemonGame.Pokemon;
import pokemonGame.LearnsetEntry;
import java.util.List;

public class Cloyster extends Pokemon {

    private static final List<LearnsetEntry> LEARNSET = new java.util.ArrayList<>();
    static {
        // Learnset will be populated manually later
    }

    public Cloyster(String name) {
        super(
            "Cloyster",
            91,
            "Water",
            "Ice",
            5,
            50,
            95,
            180,
            85,
            45,
            70
        );

        this.setName(name);

        int[] evYield = {0, 0, 2, 0, 0, 0}; // Cloyster yields 2 EV points in Defense when defeated
        this.setEvYield(evYield);
        this.generateRandomIVs();
        this.calculateCurrentStats();
    }

    @Override
    public List<LearnsetEntry> getLearnset() {
        return LEARNSET;
    }
}
