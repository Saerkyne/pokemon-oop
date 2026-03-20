package pokemonGame.mons;
import pokemonGame.Pokemon;
import pokemonGame.LearnsetEntry;
import pokemonGame.moves.*;
import java.util.List;

public class Graveler extends Pokemon {

    private static final List<LearnsetEntry> LEARNSET = new java.util.ArrayList<>();
    static {
        // Level up moves
        LEARNSET.add(new LearnsetEntry(new DefenseCurl(), LearnsetEntry.Source.LEVEL, 1));
        LEARNSET.add(new LearnsetEntry(new Tackle(), LearnsetEntry.Source.LEVEL, 1));
        LEARNSET.add(new LearnsetEntry(new DefenseCurl(), LearnsetEntry.Source.LEVEL, 11));
        LEARNSET.add(new LearnsetEntry(new RockThrow(), LearnsetEntry.Source.LEVEL, 16));
        LEARNSET.add(new LearnsetEntry(new SelfDestruct(), LearnsetEntry.Source.LEVEL, 21));
        LEARNSET.add(new LearnsetEntry(new Harden(), LearnsetEntry.Source.LEVEL, 29));
        LEARNSET.add(new LearnsetEntry(new Earthquake(), LearnsetEntry.Source.LEVEL, 36));
        LEARNSET.add(new LearnsetEntry(new Explosion(), LearnsetEntry.Source.LEVEL, 43));

        // HM moves
        LEARNSET.add(new LearnsetEntry(new Strength(), LearnsetEntry.Source.HM, 4));

        // TM moves
        LEARNSET.add(new LearnsetEntry(new MegaPunch(), LearnsetEntry.Source.TM, 1));
        LEARNSET.add(new LearnsetEntry(new Toxic(), LearnsetEntry.Source.TM, 6));
        LEARNSET.add(new LearnsetEntry(new BodySlam(), LearnsetEntry.Source.TM, 8));
        LEARNSET.add(new LearnsetEntry(new TakeDown(), LearnsetEntry.Source.TM, 9));
        LEARNSET.add(new LearnsetEntry(new DoubleEdge(), LearnsetEntry.Source.TM, 10));
        LEARNSET.add(new LearnsetEntry(new Submission(), LearnsetEntry.Source.TM, 17));
        LEARNSET.add(new LearnsetEntry(new Counter(), LearnsetEntry.Source.TM, 18));
        LEARNSET.add(new LearnsetEntry(new SeismicToss(), LearnsetEntry.Source.TM, 19));
        LEARNSET.add(new LearnsetEntry(new Rage(), LearnsetEntry.Source.TM, 20));
        LEARNSET.add(new LearnsetEntry(new Earthquake(), LearnsetEntry.Source.TM, 26));
        LEARNSET.add(new LearnsetEntry(new Fissure(), LearnsetEntry.Source.TM, 27));
        LEARNSET.add(new LearnsetEntry(new Dig(), LearnsetEntry.Source.TM, 28));
        LEARNSET.add(new LearnsetEntry(new Mimic(), LearnsetEntry.Source.TM, 31));
        LEARNSET.add(new LearnsetEntry(new DoubleTeam(), LearnsetEntry.Source.TM, 32));
        LEARNSET.add(new LearnsetEntry(new Bide(), LearnsetEntry.Source.TM, 34));
        LEARNSET.add(new LearnsetEntry(new Metronome(), LearnsetEntry.Source.TM, 35));
        LEARNSET.add(new LearnsetEntry(new SelfDestruct(), LearnsetEntry.Source.TM, 36));
        LEARNSET.add(new LearnsetEntry(new FireBlast(), LearnsetEntry.Source.TM, 38));
        LEARNSET.add(new LearnsetEntry(new Rest(), LearnsetEntry.Source.TM, 44));
        LEARNSET.add(new LearnsetEntry(new Explosion(), LearnsetEntry.Source.TM, 47));
        LEARNSET.add(new LearnsetEntry(new RockSlide(), LearnsetEntry.Source.TM, 48));
        LEARNSET.add(new LearnsetEntry(new Substitute(), LearnsetEntry.Source.TM, 50));
    }

    public Graveler(String name) {
        super(
            "Graveler",
            75,
            "Rock",
            "Ground",
            5,
            55,
            95,
            115,
            45,
            45,
            35
        );

        this.setName(name);

        int[] evYield = {0, 0, 2, 0, 0, 0}; // Graveler yields 2 EV points in Defense when defeated
        this.setEvYield(evYield);
        this.generateRandomIVs();
        this.calculateCurrentStats();
    }

    @Override
    public List<LearnsetEntry> getLearnset() {
        return LEARNSET;
    }
}
