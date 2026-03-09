package pokemonGame.mons;
import pokemonGame.Pokemon;
import pokemonGame.LearnsetEntry;
import java.util.List;

public class Growlithe extends Pokemon {

    private static final List<LearnsetEntry> LEARNSET = new java.util.ArrayList<>();
    static {
        // Learnset will be populated manually later
    }

    public Growlithe(String name) {
        super(
            "Growlithe",
            58,
            "Fire",
            null,
            5,
            55,
            70,
            45,
            70,
            50,
            60
        );

        this.setName(name);

        int[] evYield = {0, 1, 0, 0, 0, 0}; // Growlithe yields 1 EV point in Attack when defeated
        this.setEvYield(evYield);
        this.generateRandomIVs();
        this.calculateCurrentStats();
    }

    @Override
    public List<LearnsetEntry> getLearnset() {
        return LEARNSET;
    }
}
