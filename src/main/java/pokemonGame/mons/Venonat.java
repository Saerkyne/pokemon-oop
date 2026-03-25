package pokemonGame.mons;
import pokemonGame.Pokemon;
import pokemonGame.LearnsetEntry;
import pokemonGame.moves.*;
import java.util.List;

public class Venonat extends Pokemon {

    private static final List<LearnsetEntry> LEARNSET = new java.util.ArrayList<>();
    static {
        LEARNSET.add(new LearnsetEntry(Disable.INSTANCE, LearnsetEntry.Source.LEVEL, 1));
        LEARNSET.add(new LearnsetEntry(Tackle.INSTANCE, LearnsetEntry.Source.LEVEL, 1));
        LEARNSET.add(new LearnsetEntry(PoisonPowder.INSTANCE, LearnsetEntry.Source.LEVEL, 24));
        LEARNSET.add(new LearnsetEntry(LeechLife.INSTANCE, LearnsetEntry.Source.LEVEL, 27));
        LEARNSET.add(new LearnsetEntry(StunSpore.INSTANCE, LearnsetEntry.Source.LEVEL, 30));
        LEARNSET.add(new LearnsetEntry(Psybeam.INSTANCE, LearnsetEntry.Source.LEVEL, 35));
        LEARNSET.add(new LearnsetEntry(SleepPowder.INSTANCE, LearnsetEntry.Source.LEVEL, 38));
        LEARNSET.add(new LearnsetEntry(Psychic.INSTANCE, LearnsetEntry.Source.LEVEL, 43));

        LEARNSET.add(new LearnsetEntry(Toxic.INSTANCE, LearnsetEntry.Source.TM, 6));
        LEARNSET.add(new LearnsetEntry(TakeDown.INSTANCE, LearnsetEntry.Source.TM, 9));
        LEARNSET.add(new LearnsetEntry(DoubleEdge.INSTANCE, LearnsetEntry.Source.TM, 10));
        LEARNSET.add(new LearnsetEntry(Rage.INSTANCE, LearnsetEntry.Source.TM, 20));
        LEARNSET.add(new LearnsetEntry(MegaDrain.INSTANCE, LearnsetEntry.Source.TM, 21));
        LEARNSET.add(new LearnsetEntry(SolarBeam.INSTANCE, LearnsetEntry.Source.TM, 22));
        LEARNSET.add(new LearnsetEntry(Psychic.INSTANCE, LearnsetEntry.Source.TM, 29));
        LEARNSET.add(new LearnsetEntry(Mimic.INSTANCE, LearnsetEntry.Source.TM, 31));
        LEARNSET.add(new LearnsetEntry(DoubleTeam.INSTANCE, LearnsetEntry.Source.TM, 32));
        LEARNSET.add(new LearnsetEntry(Reflect.INSTANCE, LearnsetEntry.Source.TM, 33));
        LEARNSET.add(new LearnsetEntry(Bide.INSTANCE, LearnsetEntry.Source.TM, 34));
        LEARNSET.add(new LearnsetEntry(Rest.INSTANCE, LearnsetEntry.Source.TM, 44));
        LEARNSET.add(new LearnsetEntry(Psywave.INSTANCE, LearnsetEntry.Source.TM, 46));
        LEARNSET.add(new LearnsetEntry(Substitute.INSTANCE, LearnsetEntry.Source.TM, 50));
    }

    public Venonat(String nickname) {
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

        this.setNickname(nickname);

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
