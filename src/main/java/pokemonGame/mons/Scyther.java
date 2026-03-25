package pokemonGame.mons;
import pokemonGame.Pokemon;
import pokemonGame.LearnsetEntry;
import pokemonGame.moves.*;
import java.util.List;

public class Scyther extends Pokemon {

    private static final List<LearnsetEntry> LEARNSET = new java.util.ArrayList<>();
    static {
        LEARNSET.add(new LearnsetEntry(QuickAttack.INSTANCE, LearnsetEntry.Source.LEVEL, 1));
        LEARNSET.add(new LearnsetEntry(Leer.INSTANCE, LearnsetEntry.Source.LEVEL, 17));
        LEARNSET.add(new LearnsetEntry(FocusEnergy.INSTANCE, LearnsetEntry.Source.LEVEL, 20));
        LEARNSET.add(new LearnsetEntry(DoubleTeam.INSTANCE, LearnsetEntry.Source.LEVEL, 24));
        LEARNSET.add(new LearnsetEntry(Slash.INSTANCE, LearnsetEntry.Source.LEVEL, 29));
        LEARNSET.add(new LearnsetEntry(SwordsDance.INSTANCE, LearnsetEntry.Source.LEVEL, 35));
        LEARNSET.add(new LearnsetEntry(Agility.INSTANCE, LearnsetEntry.Source.LEVEL, 42));

        LEARNSET.add(new LearnsetEntry(Cut.INSTANCE, LearnsetEntry.Source.HM, 1));

        LEARNSET.add(new LearnsetEntry(SwordsDance.INSTANCE, LearnsetEntry.Source.TM, 3));
        LEARNSET.add(new LearnsetEntry(Toxic.INSTANCE, LearnsetEntry.Source.TM, 6));
        LEARNSET.add(new LearnsetEntry(TakeDown.INSTANCE, LearnsetEntry.Source.TM, 9));
        LEARNSET.add(new LearnsetEntry(DoubleEdge.INSTANCE, LearnsetEntry.Source.TM, 10));
        LEARNSET.add(new LearnsetEntry(HyperBeam.INSTANCE, LearnsetEntry.Source.TM, 15));
        LEARNSET.add(new LearnsetEntry(Rage.INSTANCE, LearnsetEntry.Source.TM, 20));
        LEARNSET.add(new LearnsetEntry(Mimic.INSTANCE, LearnsetEntry.Source.TM, 31));
        LEARNSET.add(new LearnsetEntry(DoubleTeam.INSTANCE, LearnsetEntry.Source.TM, 32));
        LEARNSET.add(new LearnsetEntry(Bide.INSTANCE, LearnsetEntry.Source.TM, 34));
        LEARNSET.add(new LearnsetEntry(Swift.INSTANCE, LearnsetEntry.Source.TM, 39));
        LEARNSET.add(new LearnsetEntry(SkullBash.INSTANCE, LearnsetEntry.Source.TM, 40));
        LEARNSET.add(new LearnsetEntry(Rest.INSTANCE, LearnsetEntry.Source.TM, 44));
        LEARNSET.add(new LearnsetEntry(Substitute.INSTANCE, LearnsetEntry.Source.TM, 50));
    }

    public Scyther(String nickname) {
        super(
            "Scyther",
            123,
            "Bug",
            "Flying",
            5,
            70,
            110,
            80,
            55,
            80,
            105
        );

        this.setNickname(nickname);

        int[] evYield = {0, 1, 0, 0, 0, 0}; // Scyther yields 1 EV point in Attack when defeated
        this.setEvYield(evYield);
        this.generateRandomIVs();
        this.calculateCurrentStats();
    }

    @Override
    public List<LearnsetEntry> getLearnset() {
        return LEARNSET;
    }
}
