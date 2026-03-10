package pokemonGame.mons;
import pokemonGame.Pokemon;
import pokemonGame.LearnsetEntry;
import java.util.List;

public class Nidorina extends Pokemon {

    private static final List<LearnsetEntry> LEARNSET = new java.util.ArrayList<>();
    static {
        // Learnset will be populated manually later
    }

    public Nidorina(String name) {
        super(
            "Nidorina",
            30,
            "Poison",
            null,
            5,
            70,
            62,
            67,
            55,
            55,
            56
        );

        this.setName(name);

        int[] evYield = {2, 0, 0, 0, 0, 0}; // Nidorina yields 2 EV points in HP when defeated
        this.setEvYield(evYield);
        this.generateRandomIVs();
        this.calculateCurrentStats();
    }

    @Override
    public List<LearnsetEntry> getLearnset() {
        return LEARNSET;
    }
}
