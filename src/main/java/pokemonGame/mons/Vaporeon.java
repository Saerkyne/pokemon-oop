package pokemonGame.mons;
import pokemonGame.Pokemon;
import pokemonGame.LearnsetEntry;
import pokemonGame.moves.*;
import java.util.List;

public class Vaporeon extends Pokemon {

    private static final List<LearnsetEntry> LEARNSET = new java.util.ArrayList<>();
    static {
        LEARNSET.add(new LearnsetEntry(new QuickAttack(), LearnsetEntry.Source.LEVEL, 1));
        LEARNSET.add(new LearnsetEntry(new SandAttack(), LearnsetEntry.Source.LEVEL, 1));
        LEARNSET.add(new LearnsetEntry(new Tackle(), LearnsetEntry.Source.LEVEL, 1));
        LEARNSET.add(new LearnsetEntry(new WaterGun(), LearnsetEntry.Source.LEVEL, 1));
        LEARNSET.add(new LearnsetEntry(new QuickAttack(), LearnsetEntry.Source.LEVEL, 27));
        LEARNSET.add(new LearnsetEntry(new WaterGun(), LearnsetEntry.Source.LEVEL, 31));
        LEARNSET.add(new LearnsetEntry(new TailWhip(), LearnsetEntry.Source.LEVEL, 37));
        LEARNSET.add(new LearnsetEntry(new Bite(), LearnsetEntry.Source.LEVEL, 40));
        LEARNSET.add(new LearnsetEntry(new AcidArmor(), LearnsetEntry.Source.LEVEL, 42));
        LEARNSET.add(new LearnsetEntry(new Haze(), LearnsetEntry.Source.LEVEL, 44));
        LEARNSET.add(new LearnsetEntry(new Mist(), LearnsetEntry.Source.LEVEL, 48));
        LEARNSET.add(new LearnsetEntry(new HydroPump(), LearnsetEntry.Source.LEVEL, 54));

        LEARNSET.add(new LearnsetEntry(new TakeDown(), LearnsetEntry.Source.PRE_EVOLUTION, 0));

        LEARNSET.add(new LearnsetEntry(new Surf(), LearnsetEntry.Source.HM, 3));

        LEARNSET.add(new LearnsetEntry(new Toxic(), LearnsetEntry.Source.TM, 6));
        LEARNSET.add(new LearnsetEntry(new BodySlam(), LearnsetEntry.Source.TM, 8));
        LEARNSET.add(new LearnsetEntry(new TakeDown(), LearnsetEntry.Source.TM, 9));
        LEARNSET.add(new LearnsetEntry(new DoubleEdge(), LearnsetEntry.Source.TM, 10));
        LEARNSET.add(new LearnsetEntry(new BubbleBeam(), LearnsetEntry.Source.TM, 11));
        LEARNSET.add(new LearnsetEntry(new WaterGun(), LearnsetEntry.Source.TM, 12));
        LEARNSET.add(new LearnsetEntry(new IceBeam(), LearnsetEntry.Source.TM, 13));
        LEARNSET.add(new LearnsetEntry(new Blizzard(), LearnsetEntry.Source.TM, 14));
        LEARNSET.add(new LearnsetEntry(new HyperBeam(), LearnsetEntry.Source.TM, 15));
        LEARNSET.add(new LearnsetEntry(new Rage(), LearnsetEntry.Source.TM, 20));
        LEARNSET.add(new LearnsetEntry(new Mimic(), LearnsetEntry.Source.TM, 31));
        LEARNSET.add(new LearnsetEntry(new DoubleTeam(), LearnsetEntry.Source.TM, 32));
        LEARNSET.add(new LearnsetEntry(new Reflect(), LearnsetEntry.Source.TM, 33));
        LEARNSET.add(new LearnsetEntry(new Bide(), LearnsetEntry.Source.TM, 34));
        LEARNSET.add(new LearnsetEntry(new Swift(), LearnsetEntry.Source.TM, 39));
        LEARNSET.add(new LearnsetEntry(new SkullBash(), LearnsetEntry.Source.TM, 40));
        LEARNSET.add(new LearnsetEntry(new Rest(), LearnsetEntry.Source.TM, 44));
        LEARNSET.add(new LearnsetEntry(new Substitute(), LearnsetEntry.Source.TM, 50));
    }

    public Vaporeon(String name) {
        super(
            "Vaporeon",
            134,
            "Water",
            null,
            5,
            130,
            65,
            60,
            110,
            95,
            65
        );

        this.setName(name);

        int[] evYield = {2, 0, 0, 0, 0, 0}; // Vaporeon yields 2 EV points in HP when defeated
        this.setEvYield(evYield);
        this.generateRandomIVs();
        this.calculateCurrentStats();
    }

    @Override
    public List<LearnsetEntry> getLearnset() {
        return LEARNSET;
    }
}
