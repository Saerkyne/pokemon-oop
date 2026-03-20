package pokemonGame.mons;
import pokemonGame.Pokemon;
import pokemonGame.LearnsetEntry;
import pokemonGame.moves.*;
import java.util.List;

public class Venonat extends Pokemon {

    private static final List<LearnsetEntry> LEARNSET = new java.util.ArrayList<>();
    static {
        LEARNSET.add(new LearnsetEntry(new Disable(), LearnsetEntry.Source.LEVEL, 1));
        LEARNSET.add(new LearnsetEntry(new Tackle(), LearnsetEntry.Source.LEVEL, 1));
        LEARNSET.add(new LearnsetEntry(new PoisonPowder(), LearnsetEntry.Source.LEVEL, 24));
        LEARNSET.add(new LearnsetEntry(new LeechLife(), LearnsetEntry.Source.LEVEL, 27));
        LEARNSET.add(new LearnsetEntry(new StunSpore(), LearnsetEntry.Source.LEVEL, 30));
        LEARNSET.add(new LearnsetEntry(new Psybeam(), LearnsetEntry.Source.LEVEL, 35));
        LEARNSET.add(new LearnsetEntry(new SleepPowder(), LearnsetEntry.Source.LEVEL, 38));
        LEARNSET.add(new LearnsetEntry(new Psychic(), LearnsetEntry.Source.LEVEL, 43));

        LEARNSET.add(new LearnsetEntry(new Toxic(), LearnsetEntry.Source.TM, 6));
        LEARNSET.add(new LearnsetEntry(new TakeDown(), LearnsetEntry.Source.TM, 9));
        LEARNSET.add(new LearnsetEntry(new DoubleEdge(), LearnsetEntry.Source.TM, 10));
        LEARNSET.add(new LearnsetEntry(new Rage(), LearnsetEntry.Source.TM, 20));
        LEARNSET.add(new LearnsetEntry(new MegaDrain(), LearnsetEntry.Source.TM, 21));
        LEARNSET.add(new LearnsetEntry(new SolarBeam(), LearnsetEntry.Source.TM, 22));
        LEARNSET.add(new LearnsetEntry(new Psychic(), LearnsetEntry.Source.TM, 29));
        LEARNSET.add(new LearnsetEntry(new Mimic(), LearnsetEntry.Source.TM, 31));
        LEARNSET.add(new LearnsetEntry(new DoubleTeam(), LearnsetEntry.Source.TM, 32));
        LEARNSET.add(new LearnsetEntry(new Reflect(), LearnsetEntry.Source.TM, 33));
        LEARNSET.add(new LearnsetEntry(new Bide(), LearnsetEntry.Source.TM, 34));
        LEARNSET.add(new LearnsetEntry(new Rest(), LearnsetEntry.Source.TM, 44));
        LEARNSET.add(new LearnsetEntry(new Psywave(), LearnsetEntry.Source.TM, 46));
        LEARNSET.add(new LearnsetEntry(new Substitute(), LearnsetEntry.Source.TM, 50));
    }

    public Venonat(String name) {
        super(
            "Venonat",
            48,
            "Bug",
            "Poison",
            5,
            60,
            55,
            50,
            40,
            55,
            45
        );

        this.setName(name);

        int[] evYield = {0, 0, 0, 0, 1, 0}; // Venonat yields 1 EV point in Special Defense when defeated
        this.setEvYield(evYield);
        this.generateRandomIVs();
        this.calculateCurrentStats();
    }

    @Override
    public List<LearnsetEntry> getLearnset() {
        return LEARNSET;
    }
}
