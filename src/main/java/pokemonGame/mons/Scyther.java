package pokemonGame.mons;
import pokemonGame.Pokemon;
import pokemonGame.LearnsetEntry;
import pokemonGame.moves.*;
import java.util.List;

public class Scyther extends Pokemon {

    private static final List<LearnsetEntry> LEARNSET = new java.util.ArrayList<>();
    static {
        LEARNSET.add(new LearnsetEntry(new QuickAttack(), LearnsetEntry.Source.LEVEL, 1));
        LEARNSET.add(new LearnsetEntry(new Leer(), LearnsetEntry.Source.LEVEL, 17));
        LEARNSET.add(new LearnsetEntry(new FocusEnergy(), LearnsetEntry.Source.LEVEL, 20));
        LEARNSET.add(new LearnsetEntry(new DoubleTeam(), LearnsetEntry.Source.LEVEL, 24));
        LEARNSET.add(new LearnsetEntry(new Slash(), LearnsetEntry.Source.LEVEL, 29));
        LEARNSET.add(new LearnsetEntry(new SwordsDance(), LearnsetEntry.Source.LEVEL, 35));
        LEARNSET.add(new LearnsetEntry(new Agility(), LearnsetEntry.Source.LEVEL, 42));

        LEARNSET.add(new LearnsetEntry(new Cut(), LearnsetEntry.Source.HM, 1));

        LEARNSET.add(new LearnsetEntry(new SwordsDance(), LearnsetEntry.Source.TM, 3));
        LEARNSET.add(new LearnsetEntry(new Toxic(), LearnsetEntry.Source.TM, 6));
        LEARNSET.add(new LearnsetEntry(new TakeDown(), LearnsetEntry.Source.TM, 9));
        LEARNSET.add(new LearnsetEntry(new DoubleEdge(), LearnsetEntry.Source.TM, 10));
        LEARNSET.add(new LearnsetEntry(new HyperBeam(), LearnsetEntry.Source.TM, 15));
        LEARNSET.add(new LearnsetEntry(new Rage(), LearnsetEntry.Source.TM, 20));
        LEARNSET.add(new LearnsetEntry(new Mimic(), LearnsetEntry.Source.TM, 31));
        LEARNSET.add(new LearnsetEntry(new DoubleTeam(), LearnsetEntry.Source.TM, 32));
        LEARNSET.add(new LearnsetEntry(new Bide(), LearnsetEntry.Source.TM, 34));
        LEARNSET.add(new LearnsetEntry(new Swift(), LearnsetEntry.Source.TM, 39));
        LEARNSET.add(new LearnsetEntry(new SkullBash(), LearnsetEntry.Source.TM, 40));
        LEARNSET.add(new LearnsetEntry(new Rest(), LearnsetEntry.Source.TM, 44));
        LEARNSET.add(new LearnsetEntry(new Substitute(), LearnsetEntry.Source.TM, 50));
    }

    public Scyther(String name) {
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

        this.setName(name);

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
