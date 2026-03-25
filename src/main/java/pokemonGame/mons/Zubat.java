package pokemonGame.mons;
import pokemonGame.Pokemon;
import pokemonGame.LearnsetEntry;
import pokemonGame.moves.*;
import java.util.List;

public class Zubat extends Pokemon {

    private static final List<LearnsetEntry> LEARNSET = new java.util.ArrayList<>();
    static {
        LEARNSET.add(new LearnsetEntry(new LeechLife(), LearnsetEntry.Source.LEVEL, 1));
        LEARNSET.add(new LearnsetEntry(new Supersonic(), LearnsetEntry.Source.LEVEL, 10));
        LEARNSET.add(new LearnsetEntry(new Bite(), LearnsetEntry.Source.LEVEL, 15));
        LEARNSET.add(new LearnsetEntry(new ConfuseRay(), LearnsetEntry.Source.LEVEL, 21));
        LEARNSET.add(new LearnsetEntry(new WingAttack(), LearnsetEntry.Source.LEVEL, 28));
        LEARNSET.add(new LearnsetEntry(new Haze(), LearnsetEntry.Source.LEVEL, 36));

        LEARNSET.add(new LearnsetEntry(new RazorWind(), LearnsetEntry.Source.TM, 2));
        LEARNSET.add(new LearnsetEntry(new Whirlwind(), LearnsetEntry.Source.TM, 4));
        LEARNSET.add(new LearnsetEntry(new Toxic(), LearnsetEntry.Source.TM, 6));
        LEARNSET.add(new LearnsetEntry(new TakeDown(), LearnsetEntry.Source.TM, 9));
        LEARNSET.add(new LearnsetEntry(new DoubleEdge(), LearnsetEntry.Source.TM, 10));
        LEARNSET.add(new LearnsetEntry(new Rage(), LearnsetEntry.Source.TM, 20));
        LEARNSET.add(new LearnsetEntry(new MegaDrain(), LearnsetEntry.Source.TM, 21));
        LEARNSET.add(new LearnsetEntry(new Mimic(), LearnsetEntry.Source.TM, 31));
        LEARNSET.add(new LearnsetEntry(new DoubleTeam(), LearnsetEntry.Source.TM, 32));
        LEARNSET.add(new LearnsetEntry(new Bide(), LearnsetEntry.Source.TM, 34));
        LEARNSET.add(new LearnsetEntry(new Swift(), LearnsetEntry.Source.TM, 39));
        LEARNSET.add(new LearnsetEntry(new Rest(), LearnsetEntry.Source.TM, 44));
        LEARNSET.add(new LearnsetEntry(new Substitute(), LearnsetEntry.Source.TM, 50));
    }

    public Zubat(String nickname) {
        super(
            "Zubat",
            41,
            "Poison",
            "Flying",
            5,
            40,
            45,
            35,
            30,
            40,
            55
        );

        this.setNickname(nickname);

        int[] evYield = {0, 0, 0, 0, 0, 1}; // Zubat yields 1 EV point in Speed when defeated
        this.setEvYield(evYield);
        this.generateRandomIVs();
        this.calculateCurrentStats();
    }

    @Override
    public List<LearnsetEntry> getLearnset() {
        return LEARNSET;
    }
}
