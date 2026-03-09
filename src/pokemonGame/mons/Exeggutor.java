package pokemonGame.mons;
import pokemonGame.Pokemon;
import pokemonGame.LearnsetEntry;
import java.util.List;

public class Exeggutor extends Pokemon {

    private static final List<LearnsetEntry> LEARNSET = new java.util.ArrayList<>();
    static {
        // Learnset will be populated manually later
    }

    public Exeggutor(String name) {
        super(
            "Exeggutor",
            103,
            "Grass",
            "Psychic",
            5,
            95,
            95,
            85,
            125,
            75,
            55
        );

        this.setName(name);

        int[] evYield = {0, 0, 0, 2, 0, 0}; // Exeggutor yields 2 EV points in Special Attack when defeated
        this.setEvYield(evYield);
        this.generateRandomIVs();
        this.calculateCurrentStats();
    }

    @Override
    public List<LearnsetEntry> getLearnset() {
        return LEARNSET;
    }
}
