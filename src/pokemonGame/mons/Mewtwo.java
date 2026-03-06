package pokemonGame.mons;
import pokemonGame.Pokemon;
import pokemonGame.LearnsetEntry;
import pokemonGame.moves.*;
import java.util.ArrayList;
import java.util.List;

public class Mewtwo extends Pokemon {
    
    // Define Mewtwo's learnset
    private static final List<LearnsetEntry> LEARNSET = new ArrayList<>();
    static {
        LEARNSET.add(new LearnsetEntry(new Confusion(), LearnsetEntry.Source.LEVEL, 1));
        LEARNSET.add(new LearnsetEntry(new MegaPunch(), LearnsetEntry.Source.TM, 1));
        LEARNSET.add(new LearnsetEntry(new Strength(), LearnsetEntry.Source.HM, 4));
        LEARNSET.add(new LearnsetEntry(new Psychic(), LearnsetEntry.Source.LEVEL, 66));
        LEARNSET.add(new LearnsetEntry(new Swift(), LearnsetEntry.Source.LEVEL, 1));
        LEARNSET.add(new LearnsetEntry(new Disable(), LearnsetEntry.Source.LEVEL, 1));
        LEARNSET.add(new LearnsetEntry(new Recover(), LearnsetEntry.Source.LEVEL, 70));
        LEARNSET.add(new LearnsetEntry(new Barrier(), LearnsetEntry.Source.LEVEL, 63));
        LEARNSET.add(new LearnsetEntry(new Mist(), LearnsetEntry.Source.TM, 75));
        LEARNSET.add(new LearnsetEntry(new Amnesia(), LearnsetEntry.Source.LEVEL, 81));
        // …other moves…
    }
    
    
    // Constructor
    public Mewtwo(String name) {
        
        // Call the superclass constructor to initialize Mewtwo's base stats and type
        super("Mewtwo", 150, "Psychic", null,
        5, 106, 110, 90, 154, 
        90, 130);

        this.setName(name);

        int[] evYield = {0, 0, 0, 3, 0, 0}; // Mewtwo yields 3 EV points in Special Attack when defeated
        this.setEvYield(evYield);
        this.generateRandomIVs();
        this.calculateCurrentStats();
    }

    // instance accessor used in polymorphic contexts
    @Override
    public List<LearnsetEntry> getLearnset() {
        return LEARNSET;
    }

}
