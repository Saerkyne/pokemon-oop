package pokemonGame.mons;
import pokemonGame.Pokemon;
import pokemonGame.moves.*;
import pokemonGame.LearnsetEntry;
import java.util.List;

public class Articuno extends Pokemon {

    private static final List<LearnsetEntry> LEARNSET = new java.util.ArrayList<>();
    static {
        // Level up moves
        LEARNSET.add(new LearnsetEntry(new IceBeam(), LearnsetEntry.Source.LEVEL, 1));
        LEARNSET.add(new LearnsetEntry(new Peck(), LearnsetEntry.Source.LEVEL, 1));
        LEARNSET.add(new LearnsetEntry(new Blizzard(), LearnsetEntry.Source.LEVEL, 51));
        LEARNSET.add(new LearnsetEntry(new Agility(), LearnsetEntry.Source.LEVEL, 55));
        LEARNSET.add(new LearnsetEntry(new Mist(), LearnsetEntry.Source.LEVEL, 60));

        // HM moves
        LEARNSET.add(new LearnsetEntry(new Fly(), LearnsetEntry.Source.HM, 2));

        // TM Moves
        LEARNSET.add(new LearnsetEntry(new RazorWind(), LearnsetEntry.Source.TM, 2));
        LEARNSET.add(new LearnsetEntry(new Whirlwind(), LearnsetEntry.Source.TM, 4));
        LEARNSET.add(new LearnsetEntry(new Toxic(), LearnsetEntry.Source.TM, 6));
        LEARNSET.add(new LearnsetEntry(new HyperBeam(), LearnsetEntry.Source.TM, 15));
        
    }

    public Articuno(String name) {
        super(
            "Articuno",
            144,
            "Ice",
            "Flying",
            5,
            90,
            85,
            100,
            95,
            125,
            85
        );

        this.setName(name);

        int[] evYield = {0, 0, 0, 0, 3, 0}; // Articuno yields 3 EV points in Special Defense when defeated
        this.setEvYield(evYield);
        this.generateRandomIVs();
        this.calculateCurrentStats();
    }

    @Override
    public List<LearnsetEntry> getLearnset() {
        return LEARNSET;
    }
}
