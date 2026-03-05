package pokemonGame.mons;
import pokemonGame.*;
import pokemonGame.moves.*;
import java.util.List;
import java.util.ArrayList;


public class Pikachu extends Pokemon {

    // Define Pikachu's learnset
    private static final List<LearnsetEntry> LEARNSET = new ArrayList<>();
    static {
        LEARNSET.add(new LearnsetEntry(new ThunderShock(), LearnsetEntry.Source.LEVEL, 1));
        LEARNSET.add(new LearnsetEntry(new QuickAttack(), LearnsetEntry.Source.LEVEL, 16));
        LEARNSET.add(new LearnsetEntry(new ThunderWave(), LearnsetEntry.Source.LEVEL, 9));
        LEARNSET.add(new LearnsetEntry(new Swift(), LearnsetEntry.Source.LEVEL, 26));
        LEARNSET.add(new LearnsetEntry(new Agility(), LearnsetEntry.Source.LEVEL, 33));
        LEARNSET.add(new LearnsetEntry(new Thunder(), LearnsetEntry.Source.LEVEL, 43));
        // …other moves…
    }


    // Constructor
    public Pikachu() {

        // Call the superclass constructor to initialize Pikachu's base stats and type
        super("Pikachu", 25, "Electric", "None", 100, 55, 40, 50, 50, 90, 90);
    }

    // instance accessor used in polymorphic contexts
    @Override
    public List<LearnsetEntry> getLearnset() {
        return LEARNSET;
    }

}
