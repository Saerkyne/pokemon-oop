package pokemonGame.mons;
import pokemonGame.Pokemon;
import pokemonGame.LearnsetEntry;
import pokemonGame.moves.*;
import java.util.List;

public class Pidgeotto extends Pokemon {

    private static final List<LearnsetEntry> LEARNSET = new java.util.ArrayList<>();
    static {
        LEARNSET.add(new LearnsetEntry(Gust.INSTANCE, LearnsetEntry.Source.LEVEL, 1));
        LEARNSET.add(new LearnsetEntry(SandAttack.INSTANCE, LearnsetEntry.Source.LEVEL, 1));
        LEARNSET.add(new LearnsetEntry(SandAttack.INSTANCE, LearnsetEntry.Source.LEVEL, 5));
        LEARNSET.add(new LearnsetEntry(QuickAttack.INSTANCE, LearnsetEntry.Source.LEVEL, 12));
        LEARNSET.add(new LearnsetEntry(Whirlwind.INSTANCE, LearnsetEntry.Source.LEVEL, 21));
        LEARNSET.add(new LearnsetEntry(WingAttack.INSTANCE, LearnsetEntry.Source.LEVEL, 31));
        LEARNSET.add(new LearnsetEntry(Agility.INSTANCE, LearnsetEntry.Source.LEVEL, 40));
        LEARNSET.add(new LearnsetEntry(MirrorMove.INSTANCE, LearnsetEntry.Source.LEVEL, 49));

        LEARNSET.add(new LearnsetEntry(Fly.INSTANCE, LearnsetEntry.Source.HM, 2));

        LEARNSET.add(new LearnsetEntry(RazorWind.INSTANCE, LearnsetEntry.Source.TM, 2));
        LEARNSET.add(new LearnsetEntry(Whirlwind.INSTANCE, LearnsetEntry.Source.TM, 4));
        LEARNSET.add(new LearnsetEntry(Toxic.INSTANCE, LearnsetEntry.Source.TM, 6));
        LEARNSET.add(new LearnsetEntry(TakeDown.INSTANCE, LearnsetEntry.Source.TM, 9));
        LEARNSET.add(new LearnsetEntry(DoubleEdge.INSTANCE, LearnsetEntry.Source.TM, 10));
        LEARNSET.add(new LearnsetEntry(Rage.INSTANCE, LearnsetEntry.Source.TM, 20));
        LEARNSET.add(new LearnsetEntry(Mimic.INSTANCE, LearnsetEntry.Source.TM, 31));
        LEARNSET.add(new LearnsetEntry(DoubleTeam.INSTANCE, LearnsetEntry.Source.TM, 32));
        LEARNSET.add(new LearnsetEntry(Reflect.INSTANCE, LearnsetEntry.Source.TM, 33));
        LEARNSET.add(new LearnsetEntry(Bide.INSTANCE, LearnsetEntry.Source.TM, 34));
        LEARNSET.add(new LearnsetEntry(Swift.INSTANCE, LearnsetEntry.Source.TM, 39));
        LEARNSET.add(new LearnsetEntry(SkyAttack.INSTANCE, LearnsetEntry.Source.TM, 43));
        LEARNSET.add(new LearnsetEntry(Rest.INSTANCE, LearnsetEntry.Source.TM, 44));
        LEARNSET.add(new LearnsetEntry(Substitute.INSTANCE, LearnsetEntry.Source.TM, 50));
    }

    public Pidgeotto(String nickname) {
        super(
            "Pidgeotto",
            17,
            "Normal",
            "Flying",
            5,
            63,
            60,
            55,
            50,
            50,
            71
        );

        this.setNickname(nickname);

        int[] evYield = {0, 0, 0, 0, 0, 2}; // Pidgeotto yields 2 EV points in Speed when defeated
        this.setEvYield(evYield);
        this.generateRandomIVs();
        this.calculateCurrentStats();
    }

    @Override
    public List<LearnsetEntry> getLearnset() {
        return LEARNSET;
    }
}
