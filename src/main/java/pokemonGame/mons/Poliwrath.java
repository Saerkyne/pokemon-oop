package pokemonGame.mons;
import pokemonGame.Pokemon;
import pokemonGame.LearnsetEntry;
import pokemonGame.moves.*;
import java.util.List;

public class Poliwrath extends Pokemon {

    private static final List<LearnsetEntry> LEARNSET = new java.util.ArrayList<>();
    static {
        LEARNSET.add(new LearnsetEntry(new BodySlam(), LearnsetEntry.Source.LEVEL, 1));
        LEARNSET.add(new LearnsetEntry(new DoubleSlap(), LearnsetEntry.Source.LEVEL, 1));
        LEARNSET.add(new LearnsetEntry(new Hypnosis(), LearnsetEntry.Source.LEVEL, 1));
        LEARNSET.add(new LearnsetEntry(new WaterGun(), LearnsetEntry.Source.LEVEL, 1));
        LEARNSET.add(new LearnsetEntry(new Hypnosis(), LearnsetEntry.Source.LEVEL, 16));
        LEARNSET.add(new LearnsetEntry(new WaterGun(), LearnsetEntry.Source.LEVEL, 19));

        LEARNSET.add(new LearnsetEntry(new Amnesia(), LearnsetEntry.Source.PRE_EVOLUTION, 0));
        LEARNSET.add(new LearnsetEntry(new Bubble(), LearnsetEntry.Source.PRE_EVOLUTION, 0));
        LEARNSET.add(new LearnsetEntry(new HydroPump(), LearnsetEntry.Source.PRE_EVOLUTION, 0));

        LEARNSET.add(new LearnsetEntry(new Surf(), LearnsetEntry.Source.HM, 3));
        LEARNSET.add(new LearnsetEntry(new Strength(), LearnsetEntry.Source.HM, 4));

        LEARNSET.add(new LearnsetEntry(new MegaPunch(), LearnsetEntry.Source.TM, 1));
        LEARNSET.add(new LearnsetEntry(new MegaKick(), LearnsetEntry.Source.TM, 5));
        LEARNSET.add(new LearnsetEntry(new Toxic(), LearnsetEntry.Source.TM, 6));
        LEARNSET.add(new LearnsetEntry(new BodySlam(), LearnsetEntry.Source.TM, 8));
        LEARNSET.add(new LearnsetEntry(new TakeDown(), LearnsetEntry.Source.TM, 9));
        LEARNSET.add(new LearnsetEntry(new DoubleEdge(), LearnsetEntry.Source.TM, 10));
        LEARNSET.add(new LearnsetEntry(new BubbleBeam(), LearnsetEntry.Source.TM, 11));
        LEARNSET.add(new LearnsetEntry(new WaterGun(), LearnsetEntry.Source.TM, 12));
        LEARNSET.add(new LearnsetEntry(new IceBeam(), LearnsetEntry.Source.TM, 13));
        LEARNSET.add(new LearnsetEntry(new Blizzard(), LearnsetEntry.Source.TM, 14));
        LEARNSET.add(new LearnsetEntry(new HyperBeam(), LearnsetEntry.Source.TM, 15));
        LEARNSET.add(new LearnsetEntry(new Submission(), LearnsetEntry.Source.TM, 17));
        LEARNSET.add(new LearnsetEntry(new Counter(), LearnsetEntry.Source.TM, 18));
        LEARNSET.add(new LearnsetEntry(new SeismicToss(), LearnsetEntry.Source.TM, 19));
        LEARNSET.add(new LearnsetEntry(new Rage(), LearnsetEntry.Source.TM, 20));
        LEARNSET.add(new LearnsetEntry(new Earthquake(), LearnsetEntry.Source.TM, 26));
        LEARNSET.add(new LearnsetEntry(new Fissure(), LearnsetEntry.Source.TM, 27));
        LEARNSET.add(new LearnsetEntry(new Psychic(), LearnsetEntry.Source.TM, 29));
        LEARNSET.add(new LearnsetEntry(new Mimic(), LearnsetEntry.Source.TM, 31));
        LEARNSET.add(new LearnsetEntry(new DoubleTeam(), LearnsetEntry.Source.TM, 32));
        LEARNSET.add(new LearnsetEntry(new Bide(), LearnsetEntry.Source.TM, 34));
        LEARNSET.add(new LearnsetEntry(new Metronome(), LearnsetEntry.Source.TM, 35));
        LEARNSET.add(new LearnsetEntry(new SkullBash(), LearnsetEntry.Source.TM, 40));
        LEARNSET.add(new LearnsetEntry(new Rest(), LearnsetEntry.Source.TM, 44));
        LEARNSET.add(new LearnsetEntry(new Psywave(), LearnsetEntry.Source.TM, 46));
        LEARNSET.add(new LearnsetEntry(new Substitute(), LearnsetEntry.Source.TM, 50));
    }

    public Poliwrath(String nickname) {
        super(
            "Poliwrath",
            62,
            "Water",
            "Fighting",
            5,
            90,
            95,
            95,
            70,
            90,
            70
        );

        this.setNickname(nickname);

        int[] evYield = {0, 0, 3, 0, 0, 0}; // Poliwrath yields 3 EV points in Defense when defeated
        this.setEvYield(evYield);
        this.generateRandomIVs();
        this.calculateCurrentStats();
    }

    @Override
    public List<LearnsetEntry> getLearnset() {
        return LEARNSET;
    }
}
