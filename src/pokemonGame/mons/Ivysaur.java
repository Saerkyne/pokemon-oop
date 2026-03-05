package pokemonGame.mons;
import pokemonGame.Pokemon;
import pokemonGame.moves.*;
import pokemonGame.LearnsetEntry;
import java.util.List;

public class Ivysaur extends Pokemon {

    private static final List<LearnsetEntry> LEARNSET = new java.util.ArrayList<>();
    static {
        LEARNSET.add(new LearnsetEntry(new Tackle(), LearnsetEntry.Source.LEVEL, 1));
        LEARNSET.add(new LearnsetEntry(new Growl(), LearnsetEntry.Source.LEVEL, 1));
        LEARNSET.add(new LearnsetEntry(new LeechSeed(), LearnsetEntry.Source.LEVEL, 7));
        LEARNSET.add(new LearnsetEntry(new VineWhip(), LearnsetEntry.Source.LEVEL, 13));
    }

    public Ivysaur(String name) {
        super("Ivysaur", 2, "Grass", "Poison",
            5, 60, 62, 63,
            80, 80, 60);

        this.setName(name);

        int[] evYield = {0, 0, 0, 1, 1, 0}; // Ivysaur yields 1 EV point in Special Attack and 1 EV point in Special Defense when defeated
        this.setEvYield(evYield);
        this.generateRandomIVs();
        this.calculateCurrentStats();
    }

    @Override
    public List<LearnsetEntry> getLearnset() {
        return LEARNSET;
    }
}

