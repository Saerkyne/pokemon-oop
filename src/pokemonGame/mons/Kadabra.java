package pokemonGame.mons;
import pokemonGame.Pokemon;
import pokemonGame.LearnsetEntry;
import java.util.List;

public class Kadabra extends Pokemon {

    private static final List<LearnsetEntry> LEARNSET = new java.util.ArrayList<>();
    static {
        // Learnset will be populated manually later
    }

    public Kadabra(String name) {
        super(
            "Kadabra",
            64,
            "Psychic",
            null,
            5,
            40,
            35,
            30,
            120,
            70,
            105
        );

        this.setName(name);

        int[] evYield = {0, 0, 0, 2, 0, 0}; // Kadabra yields 2 EV points in Special Attack when defeated
        this.setEvYield(evYield);
        this.generateRandomIVs();
        this.calculateCurrentStats();
    }

    @Override
    public List<LearnsetEntry> getLearnset() {
        return LEARNSET;
    }
}
