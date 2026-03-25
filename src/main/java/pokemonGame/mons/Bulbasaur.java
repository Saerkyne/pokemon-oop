package pokemonGame.mons;
import pokemonGame.Pokemon;
import pokemonGame.moves.*;
import pokemonGame.LearnsetEntry;
import java.util.List;

public class Bulbasaur extends Pokemon {

    // each species keeps its own catalog; we expose it via an instance method override
    private static final List<LearnsetEntry> LEARNSET = new java.util.ArrayList<>();
    static {
        // Level up Moves
        LEARNSET.add(new LearnsetEntry(Tackle.INSTANCE, LearnsetEntry.Source.LEVEL, 1));
        LEARNSET.add(new LearnsetEntry(Growl.INSTANCE, LearnsetEntry.Source.LEVEL, 1));
        LEARNSET.add(new LearnsetEntry(LeechSeed.INSTANCE, LearnsetEntry.Source.LEVEL, 7));
        LEARNSET.add(new LearnsetEntry(VineWhip.INSTANCE, LearnsetEntry.Source.LEVEL, 13));
        LEARNSET.add(new LearnsetEntry(PoisonPowder.INSTANCE, LearnsetEntry.Source.LEVEL, 20));
        LEARNSET.add(new LearnsetEntry(RazorLeaf.INSTANCE, LearnsetEntry.Source.LEVEL, 27));
        LEARNSET.add(new LearnsetEntry(Growth.INSTANCE, LearnsetEntry.Source.LEVEL, 34));
        LEARNSET.add(new LearnsetEntry(SleepPowder.INSTANCE, LearnsetEntry.Source.LEVEL, 41));
        LEARNSET.add(new LearnsetEntry(SolarBeam.INSTANCE, LearnsetEntry.Source.LEVEL, 48));

        // HM Moves
        LEARNSET.add(new LearnsetEntry(Cut.INSTANCE, LearnsetEntry.Source.HM, 1));

        // TM Moves
        LEARNSET.add(new LearnsetEntry(SwordsDance.INSTANCE, LearnsetEntry.Source.TM, 3));
        LEARNSET.add(new LearnsetEntry(Toxic.INSTANCE, LearnsetEntry.Source.TM, 6));
        LEARNSET.add(new LearnsetEntry(BodySlam.INSTANCE, LearnsetEntry.Source.TM, 8));
        LEARNSET.add(new LearnsetEntry(TakeDown.INSTANCE, LearnsetEntry.Source.TM, 9));
        LEARNSET.add(new LearnsetEntry(DoubleEdge.INSTANCE, LearnsetEntry.Source.TM, 10));
        LEARNSET.add(new LearnsetEntry(Rage.INSTANCE, LearnsetEntry.Source.TM, 20));
        LEARNSET.add(new LearnsetEntry(MegaDrain.INSTANCE, LearnsetEntry.Source.TM, 21));
        LEARNSET.add(new LearnsetEntry(SolarBeam.INSTANCE, LearnsetEntry.Source.TM, 22));
        LEARNSET.add(new LearnsetEntry(Mimic.INSTANCE, LearnsetEntry.Source.TM, 31));
        LEARNSET.add(new LearnsetEntry(DoubleTeam.INSTANCE, LearnsetEntry.Source.TM, 32));
        LEARNSET.add(new LearnsetEntry(Reflect.INSTANCE, LearnsetEntry.Source.TM, 33));
        LEARNSET.add(new LearnsetEntry(Bide.INSTANCE, LearnsetEntry.Source.TM, 34));
        LEARNSET.add(new LearnsetEntry(Rest.INSTANCE, LearnsetEntry.Source.TM, 44));
        LEARNSET.add(new LearnsetEntry(Substitute.INSTANCE, LearnsetEntry.Source.TM, 50));
    }

    // Constructor
    public Bulbasaur(String nickname) {
        super(
            "Bulbasaur",
            1,
            "Grass",
            "Poison",
            5,
            45,
            49,
            49,
            65,
            65,
            45
        );

        this.setNickname(nickname);

        int[] evYield = {0, 0, 0, 1, 0, 0}; // Bulbasaur yields 1 EV point in Special Attack when defeated
        this.setEvYield(evYield);
        this.generateRandomIVs();
        this.calculateCurrentStats();
    }

    // instance accessor used in polymorphic contexts
    @Override
    public List<LearnsetEntry> getLearnset() {
        return LEARNSET;
    }
}
