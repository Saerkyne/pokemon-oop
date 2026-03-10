package pokemonGame.mons;
import pokemonGame.Pokemon;
import pokemonGame.LearnsetEntry;
import java.util.List;

public class Pidgeot extends Pokemon {

    private static final List<LearnsetEntry> LEARNSET = new java.util.ArrayList<>();
    static {
        // Learnset will be populated manually later
    }

    public Pidgeot(String name) {
        super(
            "Pidgeot",
            18,
            "Normal",
            "Flying",
            5,
            83,
            80,
            75,
            70,
            70,
            101
        );

        this.setName(name);

        int[] evYield = {0, 0, 0, 0, 0, 3}; // Pidgeot yields 3 EV points in Speed when defeated
        this.setEvYield(evYield);
        this.generateRandomIVs();
        this.calculateCurrentStats();
    }

    @Override
    public List<LearnsetEntry> getLearnset() {
        return LEARNSET;
    }
}
