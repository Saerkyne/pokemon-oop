package pokemonGame.mons;
import pokemonGame.Pokemon;
import pokemonGame.LearnsetEntry;
import java.util.List;

public class Jigglypuff extends Pokemon {

    private static final List<LearnsetEntry> LEARNSET = new java.util.ArrayList<>();
    static {
        // Learnset will be populated manually later
    }

    public Jigglypuff(String name) {
        super("Jigglypuff", 39, "Normal", "Fairy",
            5, 115, 45, 20,
            45, 25, 20);

        this.setName(name);

        int[] evYield = {2, 0, 0, 0, 0, 0}; // Jigglypuff yields 2 EV points in HP when defeated
        this.setEvYield(evYield);
        this.generateRandomIVs();
        this.calculateCurrentStats();
    }

    @Override
    public List<LearnsetEntry> getLearnset() {
        return LEARNSET;
    }
}
