package pokemonGame.mons;
import pokemonGame.Pokemon;
import pokemonGame.LearnsetEntry;
import java.util.List;

public class Aerodactyl extends Pokemon {

    private static final List<LearnsetEntry> LEARNSET = new java.util.ArrayList<>();
    static {
        // Learnset will be populated manually later
    }

    public Aerodactyl(String name) {
        super(
            "Aerodactyl",
            142,
            "Rock",
            "Flying",
            5,
            80,
            105,
            65,
            60,
            75,
            130
        );

        this.setName(name);

        int[] evYield = {0, 0, 0, 0, 0, 2}; // Aerodactyl yields 2 EV points in Speed when defeated
        this.setEvYield(evYield);
        this.generateRandomIVs();
        this.calculateCurrentStats();
    }

    @Override
    public List<LearnsetEntry> getLearnset() {
        return LEARNSET;
    }
}
