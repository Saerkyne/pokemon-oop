package pokemonGame.mons;
import pokemonGame.Pokemon;
import pokemonGame.LearnsetEntry;
import java.util.List;

public class Pidgey extends Pokemon {

    private static final List<LearnsetEntry> LEARNSET = new java.util.ArrayList<>();
    static {
        // Learnset will be populated manually later
    }

    public Pidgey(String name) {
        super(
            "Pidgey",
            16,
            "Normal",
            "Flying",
            5,
            40,
            45,
            40,
            35,
            35,
            56
        );

        this.setName(name);

        int[] evYield = {0, 0, 0, 0, 0, 1}; // Pidgey yields 1 EV point in Speed when defeated
        this.setEvYield(evYield);
        this.generateRandomIVs();
        this.calculateCurrentStats();
    }

    @Override
    public List<LearnsetEntry> getLearnset() {
        return LEARNSET;
    }
}
