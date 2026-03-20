package pokemonGame.mons;
import pokemonGame.Pokemon;
import pokemonGame.LearnsetEntry;
import pokemonGame.moves.*;
import java.util.List;

public class Pinsir extends Pokemon {

    private static final List<LearnsetEntry> LEARNSET = new java.util.ArrayList<>();
    static {
        LEARNSET.add(new LearnsetEntry(new ViseGrip(), LearnsetEntry.Source.LEVEL, 1));
        LEARNSET.add(new LearnsetEntry(new SeismicToss(), LearnsetEntry.Source.LEVEL, 25));
        LEARNSET.add(new LearnsetEntry(new Guillotine(), LearnsetEntry.Source.LEVEL, 30));
        LEARNSET.add(new LearnsetEntry(new FocusEnergy(), LearnsetEntry.Source.LEVEL, 36));
        LEARNSET.add(new LearnsetEntry(new Harden(), LearnsetEntry.Source.LEVEL, 43));
        LEARNSET.add(new LearnsetEntry(new Slash(), LearnsetEntry.Source.LEVEL, 49));
        LEARNSET.add(new LearnsetEntry(new SwordsDance(), LearnsetEntry.Source.LEVEL, 54));

        LEARNSET.add(new LearnsetEntry(new Cut(), LearnsetEntry.Source.HM, 1));
        LEARNSET.add(new LearnsetEntry(new Strength(), LearnsetEntry.Source.HM, 4));

        LEARNSET.add(new LearnsetEntry(new SwordsDance(), LearnsetEntry.Source.TM, 3));
        LEARNSET.add(new LearnsetEntry(new Toxic(), LearnsetEntry.Source.TM, 6));
        LEARNSET.add(new LearnsetEntry(new BodySlam(), LearnsetEntry.Source.TM, 8));
        LEARNSET.add(new LearnsetEntry(new TakeDown(), LearnsetEntry.Source.TM, 9));
        LEARNSET.add(new LearnsetEntry(new DoubleEdge(), LearnsetEntry.Source.TM, 10));
        LEARNSET.add(new LearnsetEntry(new HyperBeam(), LearnsetEntry.Source.TM, 15));
        LEARNSET.add(new LearnsetEntry(new Submission(), LearnsetEntry.Source.TM, 17));
        LEARNSET.add(new LearnsetEntry(new SeismicToss(), LearnsetEntry.Source.TM, 19));
        LEARNSET.add(new LearnsetEntry(new Rage(), LearnsetEntry.Source.TM, 20));
        LEARNSET.add(new LearnsetEntry(new Mimic(), LearnsetEntry.Source.TM, 31));
        LEARNSET.add(new LearnsetEntry(new DoubleTeam(), LearnsetEntry.Source.TM, 32));
        LEARNSET.add(new LearnsetEntry(new Bide(), LearnsetEntry.Source.TM, 34));
        LEARNSET.add(new LearnsetEntry(new Rest(), LearnsetEntry.Source.TM, 44));
        LEARNSET.add(new LearnsetEntry(new Substitute(), LearnsetEntry.Source.TM, 50));
    }

    public Pinsir(String name) {
        super(
            "Pinsir",
            127,
            "Bug",
            null,
            5,
            65,
            125,
            100,
            55,
            70,
            85
        );

        this.setName(name);

        int[] evYield = {0, 2, 0, 0, 0, 0}; // Pinsir yields 2 EV points in Attack when defeated
        this.setEvYield(evYield);
        this.generateRandomIVs();
        this.calculateCurrentStats();
    }

    @Override
    public List<LearnsetEntry> getLearnset() {
        return LEARNSET;
    }
}
