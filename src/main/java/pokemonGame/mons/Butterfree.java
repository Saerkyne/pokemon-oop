package pokemonGame.mons;
import pokemonGame.Pokemon;
import pokemonGame.LearnsetEntry;
import pokemonGame.moves.*;
import java.util.List;

public class Butterfree extends Pokemon {

    private static final List<LearnsetEntry> LEARNSET = new java.util.ArrayList<>();
    static {
        // Level up moves
        LEARNSET.add(new LearnsetEntry(Confusion.INSTANCE, LearnsetEntry.Source.LEVEL, 1));
        LEARNSET.add(new LearnsetEntry(Confusion.INSTANCE, LearnsetEntry.Source.LEVEL, 12));
        LEARNSET.add(new LearnsetEntry(PoisonPowder.INSTANCE, LearnsetEntry.Source.LEVEL, 15));
        LEARNSET.add(new LearnsetEntry(StunSpore.INSTANCE, LearnsetEntry.Source.LEVEL, 16));
        LEARNSET.add(new LearnsetEntry(SleepPowder.INSTANCE, LearnsetEntry.Source.LEVEL, 17));
        LEARNSET.add(new LearnsetEntry(Supersonic.INSTANCE, LearnsetEntry.Source.LEVEL, 21));
        LEARNSET.add(new LearnsetEntry(Whirlwind.INSTANCE, LearnsetEntry.Source.LEVEL, 26));
        LEARNSET.add(new LearnsetEntry(Psybeam.INSTANCE, LearnsetEntry.Source.LEVEL, 32));

        // Pre-evolution moves
        LEARNSET.add(new LearnsetEntry(Tackle.INSTANCE, LearnsetEntry.Source.PRE_EVOLUTION, 1));
        LEARNSET.add(new LearnsetEntry(StringShot.INSTANCE, LearnsetEntry.Source.PRE_EVOLUTION, 1));
        LEARNSET.add(new LearnsetEntry(Harden.INSTANCE, LearnsetEntry.Source.PRE_EVOLUTION, 1));

        // TM moves
        LEARNSET.add(new LearnsetEntry(RazorWind.INSTANCE, LearnsetEntry.Source.TM, 2));
        LEARNSET.add(new LearnsetEntry(Whirlwind.INSTANCE, LearnsetEntry.Source.TM, 4));
        LEARNSET.add(new LearnsetEntry(Toxic.INSTANCE, LearnsetEntry.Source.TM, 6));
        LEARNSET.add(new LearnsetEntry(TakeDown.INSTANCE, LearnsetEntry.Source.TM, 9));
        LEARNSET.add(new LearnsetEntry(DoubleEdge.INSTANCE, LearnsetEntry.Source.TM, 10));
        LEARNSET.add(new LearnsetEntry(HyperBeam.INSTANCE, LearnsetEntry.Source.TM, 15));
        LEARNSET.add(new LearnsetEntry(Rage.INSTANCE, LearnsetEntry.Source.TM, 20));
        LEARNSET.add(new LearnsetEntry(MegaDrain.INSTANCE, LearnsetEntry.Source.TM, 21));
        LEARNSET.add(new LearnsetEntry(SolarBeam.INSTANCE, LearnsetEntry.Source.TM, 22));
        LEARNSET.add(new LearnsetEntry(Psychic.INSTANCE, LearnsetEntry.Source.TM, 29));
        LEARNSET.add(new LearnsetEntry(Teleport.INSTANCE, LearnsetEntry.Source.TM, 30));
        LEARNSET.add(new LearnsetEntry(Mimic.INSTANCE, LearnsetEntry.Source.TM, 31));
        LEARNSET.add(new LearnsetEntry(DoubleTeam.INSTANCE, LearnsetEntry.Source.TM, 32));
        LEARNSET.add(new LearnsetEntry(Reflect.INSTANCE, LearnsetEntry.Source.TM, 33));
        LEARNSET.add(new LearnsetEntry(Bide.INSTANCE, LearnsetEntry.Source.TM, 34));
        LEARNSET.add(new LearnsetEntry(Swift.INSTANCE, LearnsetEntry.Source.TM, 39));
        LEARNSET.add(new LearnsetEntry(Rest.INSTANCE, LearnsetEntry.Source.TM, 44));
        LEARNSET.add(new LearnsetEntry(Psywave.INSTANCE, LearnsetEntry.Source.TM, 46));
        LEARNSET.add(new LearnsetEntry(Substitute.INSTANCE, LearnsetEntry.Source.TM, 50));
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
