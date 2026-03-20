package pokemonGame.mons;
import pokemonGame.Pokemon;
import pokemonGame.LearnsetEntry;
import pokemonGame.moves.*;
import java.util.List;

public class Oddish extends Pokemon {

    private static final List<LearnsetEntry> LEARNSET = new java.util.ArrayList<>();
    static {
        LEARNSET.add(new LearnsetEntry(new Absorb(), LearnsetEntry.Source.LEVEL, 1));
        LEARNSET.add(new LearnsetEntry(new PoisonPowder(), LearnsetEntry.Source.LEVEL, 15));
        LEARNSET.add(new LearnsetEntry(new StunSpore(), LearnsetEntry.Source.LEVEL, 17));
        LEARNSET.add(new LearnsetEntry(new SleepPowder(), LearnsetEntry.Source.LEVEL, 19));
        LEARNSET.add(new LearnsetEntry(new Acid(), LearnsetEntry.Source.LEVEL, 24));
        LEARNSET.add(new LearnsetEntry(new PetalDance(), LearnsetEntry.Source.LEVEL, 33));
        LEARNSET.add(new LearnsetEntry(new SolarBeam(), LearnsetEntry.Source.LEVEL, 46));

        LEARNSET.add(new LearnsetEntry(new Cut(), LearnsetEntry.Source.HM, 1));

        LEARNSET.add(new LearnsetEntry(new SwordsDance(), LearnsetEntry.Source.TM, 3));
        LEARNSET.add(new LearnsetEntry(new Toxic(), LearnsetEntry.Source.TM, 6));
        LEARNSET.add(new LearnsetEntry(new TakeDown(), LearnsetEntry.Source.TM, 9));
        LEARNSET.add(new LearnsetEntry(new DoubleEdge(), LearnsetEntry.Source.TM, 10));
        LEARNSET.add(new LearnsetEntry(new Rage(), LearnsetEntry.Source.TM, 20));
        LEARNSET.add(new LearnsetEntry(new MegaDrain(), LearnsetEntry.Source.TM, 21));
        LEARNSET.add(new LearnsetEntry(new SolarBeam(), LearnsetEntry.Source.TM, 22));
        LEARNSET.add(new LearnsetEntry(new Mimic(), LearnsetEntry.Source.TM, 31));
        LEARNSET.add(new LearnsetEntry(new DoubleTeam(), LearnsetEntry.Source.TM, 32));
        LEARNSET.add(new LearnsetEntry(new Reflect(), LearnsetEntry.Source.TM, 33));
        LEARNSET.add(new LearnsetEntry(new Bide(), LearnsetEntry.Source.TM, 34));
        LEARNSET.add(new LearnsetEntry(new Rest(), LearnsetEntry.Source.TM, 44));
        LEARNSET.add(new LearnsetEntry(new Substitute(), LearnsetEntry.Source.TM, 50));
    }

    public Oddish(String name) {
        super(
            "Oddish",
            43,
            "Grass",
            "Poison",
            5,
            45,
            50,
            55,
            75,
            65,
            30
        );

        this.setName(name);

        int[] evYield = {0, 0, 0, 1, 0, 0}; // Oddish yields 1 EV point in Special Attack when defeated
        this.setEvYield(evYield);
        this.generateRandomIVs();
        this.calculateCurrentStats();
    }

    @Override
    public List<LearnsetEntry> getLearnset() {
        return LEARNSET;
    }
}
