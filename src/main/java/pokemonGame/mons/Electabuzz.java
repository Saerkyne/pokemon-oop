package pokemonGame.mons;
import pokemonGame.Pokemon;
import pokemonGame.LearnsetEntry;
import pokemonGame.moves.*;
import java.util.List;

public class Electabuzz extends Pokemon {

    private static final List<LearnsetEntry> LEARNSET = new java.util.ArrayList<>();
    static {
        // Level up moves
        LEARNSET.add(new LearnsetEntry(new Leer(), LearnsetEntry.Source.LEVEL, 1));
        LEARNSET.add(new LearnsetEntry(new QuickAttack(), LearnsetEntry.Source.LEVEL, 1));
        LEARNSET.add(new LearnsetEntry(new ThunderShock(), LearnsetEntry.Source.LEVEL, 34));
        LEARNSET.add(new LearnsetEntry(new Screech(), LearnsetEntry.Source.LEVEL, 37));
        LEARNSET.add(new LearnsetEntry(new ThunderPunch(), LearnsetEntry.Source.LEVEL, 42));
        LEARNSET.add(new LearnsetEntry(new LightScreen(), LearnsetEntry.Source.LEVEL, 49));
        LEARNSET.add(new LearnsetEntry(new Thunder(), LearnsetEntry.Source.LEVEL, 54));

        // HM moves
        LEARNSET.add(new LearnsetEntry(new Strength(), LearnsetEntry.Source.HM, 4));
        LEARNSET.add(new LearnsetEntry(new Flash(), LearnsetEntry.Source.HM, 5));

        // TM moves
        LEARNSET.add(new LearnsetEntry(new MegaPunch(), LearnsetEntry.Source.TM, 1));
        LEARNSET.add(new LearnsetEntry(new MegaKick(), LearnsetEntry.Source.TM, 5));
        LEARNSET.add(new LearnsetEntry(new Toxic(), LearnsetEntry.Source.TM, 6));
        LEARNSET.add(new LearnsetEntry(new BodySlam(), LearnsetEntry.Source.TM, 8));
        LEARNSET.add(new LearnsetEntry(new TakeDown(), LearnsetEntry.Source.TM, 9));
        LEARNSET.add(new LearnsetEntry(new DoubleEdge(), LearnsetEntry.Source.TM, 10));
        LEARNSET.add(new LearnsetEntry(new HyperBeam(), LearnsetEntry.Source.TM, 15));
        LEARNSET.add(new LearnsetEntry(new Submission(), LearnsetEntry.Source.TM, 17));
        LEARNSET.add(new LearnsetEntry(new Counter(), LearnsetEntry.Source.TM, 18));
        LEARNSET.add(new LearnsetEntry(new SeismicToss(), LearnsetEntry.Source.TM, 19));
        LEARNSET.add(new LearnsetEntry(new Rage(), LearnsetEntry.Source.TM, 20));
        LEARNSET.add(new LearnsetEntry(new Thunderbolt(), LearnsetEntry.Source.TM, 24));
        LEARNSET.add(new LearnsetEntry(new Thunder(), LearnsetEntry.Source.TM, 25));
        LEARNSET.add(new LearnsetEntry(new Psychic(), LearnsetEntry.Source.TM, 29));
        LEARNSET.add(new LearnsetEntry(new Teleport(), LearnsetEntry.Source.TM, 30));
        LEARNSET.add(new LearnsetEntry(new Mimic(), LearnsetEntry.Source.TM, 31));
        LEARNSET.add(new LearnsetEntry(new DoubleTeam(), LearnsetEntry.Source.TM, 32));
        LEARNSET.add(new LearnsetEntry(new Reflect(), LearnsetEntry.Source.TM, 33));
        LEARNSET.add(new LearnsetEntry(new Bide(), LearnsetEntry.Source.TM, 34));
        LEARNSET.add(new LearnsetEntry(new Metronome(), LearnsetEntry.Source.TM, 35));
        LEARNSET.add(new LearnsetEntry(new Swift(), LearnsetEntry.Source.TM, 39));
        LEARNSET.add(new LearnsetEntry(new SkullBash(), LearnsetEntry.Source.TM, 40));
        LEARNSET.add(new LearnsetEntry(new Rest(), LearnsetEntry.Source.TM, 44));
        LEARNSET.add(new LearnsetEntry(new ThunderWave(), LearnsetEntry.Source.TM, 45));
        LEARNSET.add(new LearnsetEntry(new Psywave(), LearnsetEntry.Source.TM, 46));
        LEARNSET.add(new LearnsetEntry(new Substitute(), LearnsetEntry.Source.TM, 50));
    }

    public Electabuzz(String name) {
        super(
            "Electabuzz",
            125,
            "Electric",
            null,
            5,
            65,
            83,
            57,
            95,
            85,
            105
        );

        this.setName(name);

        int[] evYield = {0, 0, 0, 0, 0, 2}; // Electabuzz yields 2 EV points in Speed when defeated
        this.setEvYield(evYield);
        this.generateRandomIVs();
        this.calculateCurrentStats();
    }

    @Override
    public List<LearnsetEntry> getLearnset() {
        return LEARNSET;
    }
}
