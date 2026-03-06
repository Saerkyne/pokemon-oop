package pokemonGame.mons;
import pokemonGame.Pokemon;
import pokemonGame.moves.*;
import pokemonGame.LearnsetEntry;
import java.util.List;

public class Pikachu extends Pokemon {

    private static final List<LearnsetEntry> LEARNSET = new java.util.ArrayList<>();
    static {
        LEARNSET.add(new LearnsetEntry(new ThunderShock(), LearnsetEntry.Source.LEVEL, 1));
        LEARNSET.add(new LearnsetEntry(new QuickAttack(), LearnsetEntry.Source.LEVEL, 16));
        LEARNSET.add(new LearnsetEntry(new ThunderWave(), LearnsetEntry.Source.LEVEL, 9));
        LEARNSET.add(new LearnsetEntry(new Swift(), LearnsetEntry.Source.LEVEL, 26));
        LEARNSET.add(new LearnsetEntry(new Agility(), LearnsetEntry.Source.LEVEL, 33));
        LEARNSET.add(new LearnsetEntry(new Thunder(), LearnsetEntry.Source.LEVEL, 43));
    }

    public Pikachu(String name) {
        super("Pikachu", 25, "Electric", null, 100, 55, 40, 50, 50, 90, 90);
        this.setName(name);

        int[] evYield = {0, 0, 0, 0, 0, 2}; // Pikachu yields 2 EV points in Speed when defeated
        this.setEvYield(evYield);
        this.generateRandomIVs();
        this.calculateCurrentStats();
    }

    @Override
    public List<LearnsetEntry> getLearnset() {
        return LEARNSET;
    }
}

