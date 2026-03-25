package pokemonGame.mons;
import pokemonGame.Pokemon;
import pokemonGame.LearnsetEntry;
import pokemonGame.moves.*;
import java.util.List;

public class Butterfree extends Pokemon {

    private static final List<LearnsetEntry> LEARNSET = new java.util.ArrayList<>();
    static {
        // Level up moves
        LEARNSET.add(new LearnsetEntry(new Confusion(), LearnsetEntry.Source.LEVEL, 1));
        LEARNSET.add(new LearnsetEntry(new Confusion(), LearnsetEntry.Source.LEVEL, 12));
        LEARNSET.add(new LearnsetEntry(new PoisonPowder(), LearnsetEntry.Source.LEVEL, 15));
        LEARNSET.add(new LearnsetEntry(new StunSpore(), LearnsetEntry.Source.LEVEL, 16));
        LEARNSET.add(new LearnsetEntry(new SleepPowder(), LearnsetEntry.Source.LEVEL, 17));
        LEARNSET.add(new LearnsetEntry(new Supersonic(), LearnsetEntry.Source.LEVEL, 21));
        LEARNSET.add(new LearnsetEntry(new Whirlwind(), LearnsetEntry.Source.LEVEL, 26));
        LEARNSET.add(new LearnsetEntry(new Psybeam(), LearnsetEntry.Source.LEVEL, 32));

        // Pre-evolution moves
        LEARNSET.add(new LearnsetEntry(new Tackle(), LearnsetEntry.Source.PRE_EVOLUTION, 1));
        LEARNSET.add(new LearnsetEntry(new StringShot(), LearnsetEntry.Source.PRE_EVOLUTION, 1));
        LEARNSET.add(new LearnsetEntry(new Harden(), LearnsetEntry.Source.PRE_EVOLUTION, 1));

        // TM moves
        LEARNSET.add(new LearnsetEntry(new RazorWind(), LearnsetEntry.Source.TM, 2));
        LEARNSET.add(new LearnsetEntry(new Whirlwind(), LearnsetEntry.Source.TM, 4));
        LEARNSET.add(new LearnsetEntry(new Toxic(), LearnsetEntry.Source.TM, 6));
        LEARNSET.add(new LearnsetEntry(new TakeDown(), LearnsetEntry.Source.TM, 9));
        LEARNSET.add(new LearnsetEntry(new DoubleEdge(), LearnsetEntry.Source.TM, 10));
        LEARNSET.add(new LearnsetEntry(new HyperBeam(), LearnsetEntry.Source.TM, 15));
        LEARNSET.add(new LearnsetEntry(new Rage(), LearnsetEntry.Source.TM, 20));
        LEARNSET.add(new LearnsetEntry(new MegaDrain(), LearnsetEntry.Source.TM, 21));
        LEARNSET.add(new LearnsetEntry(new SolarBeam(), LearnsetEntry.Source.TM, 22));
        LEARNSET.add(new LearnsetEntry(new Psychic(), LearnsetEntry.Source.TM, 29));
        LEARNSET.add(new LearnsetEntry(new Teleport(), LearnsetEntry.Source.TM, 30));
        LEARNSET.add(new LearnsetEntry(new Mimic(), LearnsetEntry.Source.TM, 31));
        LEARNSET.add(new LearnsetEntry(new DoubleTeam(), LearnsetEntry.Source.TM, 32));
        LEARNSET.add(new LearnsetEntry(new Reflect(), LearnsetEntry.Source.TM, 33));
        LEARNSET.add(new LearnsetEntry(new Bide(), LearnsetEntry.Source.TM, 34));
        LEARNSET.add(new LearnsetEntry(new Swift(), LearnsetEntry.Source.TM, 39));
        LEARNSET.add(new LearnsetEntry(new Rest(), LearnsetEntry.Source.TM, 44));
        LEARNSET.add(new LearnsetEntry(new Psywave(), LearnsetEntry.Source.TM, 46));
        LEARNSET.add(new LearnsetEntry(new Substitute(), LearnsetEntry.Source.TM, 50));
    }

    public Butterfree(String nickname) {
        super(
            "Butterfree",
            12,
            "Bug",
            "Flying",
            5,
            60,
            45,
            50,
            90,
            80,
            70
        );

        this.setNickname(nickname);

        int[] evYield = {0, 0, 0, 2, 1, 0}; // Butterfree yields 2 EV points in Special Attack and 1 EV point in Special Defense when defeated
        this.setEvYield(evYield);
        this.generateRandomIVs();
        this.calculateCurrentStats();
    }

    @Override
    public List<LearnsetEntry> getLearnset() {
        return LEARNSET;
    }
}
