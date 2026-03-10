package pokemonGame.mons;
import pokemonGame.Pokemon;
import pokemonGame.LearnsetEntry;
import java.util.List;

public class Staryu extends Pokemon {

    private static final List<LearnsetEntry> LEARNSET = new java.util.ArrayList<>();
    static {
        // Learnset will be populated manually later
    }

    public Staryu(String name) {
        super(
            "Staryu",
            120,
            "Water",
            null,
            5,
            30,
            45,
            55,
            70,
            55,
            85
        );

        this.setName(name);

        int[] evYield = {0, 0, 0, 0, 0, 1}; // Staryu yields 1 EV point in Speed when defeated
        this.setEvYield(evYield);
        this.generateRandomIVs();
        this.calculateCurrentStats();
    }

    @Override
    public List<LearnsetEntry> getLearnset() {
        return LEARNSET;
    }
}
