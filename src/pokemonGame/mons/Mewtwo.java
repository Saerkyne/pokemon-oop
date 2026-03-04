package pokemonGame.mons;
import pokemonGame.moves.*;
import java.util.ArrayList;
import java.util.List;

import pokemonGame.LearnsetEntry;
import pokemonGame.Pokemon;

public class Mewtwo extends Pokemon {
    
    // Define Mewtwo's learnset
    private static final List<LearnsetEntry> LEARNSET = new ArrayList<>();
    static {
        LEARNSET.add(new LearnsetEntry(new Confusion(), LearnsetEntry.Source.LEVEL, 1));
        LEARNSET.add(new LearnsetEntry(new MegaPunch(), LearnsetEntry.Source.TM, 01));
        LEARNSET.add(new LearnsetEntry(new Strength(), LearnsetEntry.Source.HM, 04));
        // …other moves…
    }
    
    
    // Constructor
    public Mewtwo() {
        
        // Call the superclass constructor to initialize Mewtwo's base stats and type
        super("Mewtwo", 150, "Psychic", null, 70, 106, 110, 90, 154, 90, 130);
    }

    // Method to get Mewtwo's learnset
    public List<LearnsetEntry> getLearnset() {
        return LEARNSET;
    }

    

           

}
