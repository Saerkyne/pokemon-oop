package pokemonGame.mons;
import pokemonGame.Pokemon;
import pokemonGame.LearnsetEntry;
import java.util.List;

public class Ponyta extends Pokemon {

    private static final List<LearnsetEntry> LEARNSET = new java.util.ArrayList<>();
    static {
        // Learnset will be populated manually later
    }

    public Ponyta(String name) {
        super("Ponyta", 77, "Fire", null,
            5, 50, 85, 55,
            65, 65, 90);

        this.setName(name);

        int[] evYield = {0, 0, 0, 0, 0, 1}; // Ponyta yields 1 EV point in Speed when defeated
        this.setEvYield(evYield);
        this.generateRandomIVs();
        this.calculateCurrentStats();
    }

    @Override
    public List<LearnsetEntry> getLearnset() {
        return LEARNSET;
    }
}
