package pokemonGame.mons;
import pokemonGame.Pokemon;
import pokemonGame.LearnsetEntry;
import java.util.List;

public class Wigglytuff extends Pokemon {

    private static final List<LearnsetEntry> LEARNSET = new java.util.ArrayList<>();
    static {
        // Learnset will be populated manually later
    }

    public Wigglytuff(String name) {
        super("Wigglytuff", 40, "Normal", "Fairy",
            5, 140, 70, 45,
            85, 50, 45);

        this.setName(name);

        int[] evYield = {3, 0, 0, 0, 0, 0}; // Wigglytuff yields 3 EV points in HP when defeated
        this.setEvYield(evYield);
        this.generateRandomIVs();
        this.calculateCurrentStats();
    }

    @Override
    public List<LearnsetEntry> getLearnset() {
        return LEARNSET;
    }
}
