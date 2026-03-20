package pokemonGame.mons;
import pokemonGame.Pokemon;
import pokemonGame.LearnsetEntry;
import pokemonGame.moves.*;
import java.util.List;

public class Nidoking extends Pokemon {

    private static final List<LearnsetEntry> LEARNSET = new java.util.ArrayList<>();
    static {
        LEARNSET.add(new LearnsetEntry(new HornAttack(), LearnsetEntry.Source.LEVEL, 1));
        LEARNSET.add(new LearnsetEntry(new PoisonSting(), LearnsetEntry.Source.LEVEL, 1));
        LEARNSET.add(new LearnsetEntry(new Tackle(), LearnsetEntry.Source.LEVEL, 1));
        LEARNSET.add(new LearnsetEntry(new Thrash(), LearnsetEntry.Source.LEVEL, 1));
        LEARNSET.add(new LearnsetEntry(new HornAttack(), LearnsetEntry.Source.LEVEL, 8));
        LEARNSET.add(new LearnsetEntry(new PoisonSting(), LearnsetEntry.Source.LEVEL, 14));
        LEARNSET.add(new LearnsetEntry(new Thrash(), LearnsetEntry.Source.LEVEL, 23));

        LEARNSET.add(new LearnsetEntry(new DoubleKick(), LearnsetEntry.Source.PRE_EVOLUTION, 0));
        LEARNSET.add(new LearnsetEntry(new FocusEnergy(), LearnsetEntry.Source.PRE_EVOLUTION, 0));
        LEARNSET.add(new LearnsetEntry(new FuryAttack(), LearnsetEntry.Source.PRE_EVOLUTION, 0));
        LEARNSET.add(new LearnsetEntry(new HornDrill(), LearnsetEntry.Source.PRE_EVOLUTION, 0));
        LEARNSET.add(new LearnsetEntry(new Leer(), LearnsetEntry.Source.PRE_EVOLUTION, 0));

        LEARNSET.add(new LearnsetEntry(new Surf(), LearnsetEntry.Source.HM, 3));
        LEARNSET.add(new LearnsetEntry(new Strength(), LearnsetEntry.Source.HM, 4));

        LEARNSET.add(new LearnsetEntry(new MegaPunch(), LearnsetEntry.Source.TM, 1));
        LEARNSET.add(new LearnsetEntry(new MegaKick(), LearnsetEntry.Source.TM, 5));
        LEARNSET.add(new LearnsetEntry(new Toxic(), LearnsetEntry.Source.TM, 6));
        LEARNSET.add(new LearnsetEntry(new HornDrill(), LearnsetEntry.Source.TM, 7));
        LEARNSET.add(new LearnsetEntry(new BodySlam(), LearnsetEntry.Source.TM, 8));
        LEARNSET.add(new LearnsetEntry(new TakeDown(), LearnsetEntry.Source.TM, 9));
        LEARNSET.add(new LearnsetEntry(new DoubleEdge(), LearnsetEntry.Source.TM, 10));
        LEARNSET.add(new LearnsetEntry(new BubbleBeam(), LearnsetEntry.Source.TM, 11));
        LEARNSET.add(new LearnsetEntry(new WaterGun(), LearnsetEntry.Source.TM, 12));
        LEARNSET.add(new LearnsetEntry(new IceBeam(), LearnsetEntry.Source.TM, 13));
        LEARNSET.add(new LearnsetEntry(new Blizzard(), LearnsetEntry.Source.TM, 14));
        LEARNSET.add(new LearnsetEntry(new HyperBeam(), LearnsetEntry.Source.TM, 15));
        LEARNSET.add(new LearnsetEntry(new PayDay(), LearnsetEntry.Source.TM, 16));
        LEARNSET.add(new LearnsetEntry(new Submission(), LearnsetEntry.Source.TM, 17));
        LEARNSET.add(new LearnsetEntry(new Counter(), LearnsetEntry.Source.TM, 18));
        LEARNSET.add(new LearnsetEntry(new SeismicToss(), LearnsetEntry.Source.TM, 19));
        LEARNSET.add(new LearnsetEntry(new Rage(), LearnsetEntry.Source.TM, 20));
        LEARNSET.add(new LearnsetEntry(new Thunderbolt(), LearnsetEntry.Source.TM, 24));
        LEARNSET.add(new LearnsetEntry(new Thunder(), LearnsetEntry.Source.TM, 25));
        LEARNSET.add(new LearnsetEntry(new Earthquake(), LearnsetEntry.Source.TM, 26));
        LEARNSET.add(new LearnsetEntry(new Fissure(), LearnsetEntry.Source.TM, 27));
        LEARNSET.add(new LearnsetEntry(new Mimic(), LearnsetEntry.Source.TM, 31));
        LEARNSET.add(new LearnsetEntry(new DoubleTeam(), LearnsetEntry.Source.TM, 32));
        LEARNSET.add(new LearnsetEntry(new Reflect(), LearnsetEntry.Source.TM, 33));
        LEARNSET.add(new LearnsetEntry(new Bide(), LearnsetEntry.Source.TM, 34));
        LEARNSET.add(new LearnsetEntry(new FireBlast(), LearnsetEntry.Source.TM, 38));
        LEARNSET.add(new LearnsetEntry(new SkullBash(), LearnsetEntry.Source.TM, 40));
        LEARNSET.add(new LearnsetEntry(new Rest(), LearnsetEntry.Source.TM, 44));
        LEARNSET.add(new LearnsetEntry(new RockSlide(), LearnsetEntry.Source.TM, 48));
        LEARNSET.add(new LearnsetEntry(new Substitute(), LearnsetEntry.Source.TM, 50));
    }

    public Nidoking(String name) {
        super(
            "Nidoking",
            34,
            "Poison",
            "Ground",
            5,
            81,
            102,
            77,
            85,
            75,
            85
        );

        this.setName(name);

        int[] evYield = {0, 3, 0, 0, 0, 0}; // Nidoking yields 3 EV points in Attack when defeated
        this.setEvYield(evYield);
        this.generateRandomIVs();
        this.calculateCurrentStats();
    }

    @Override
    public List<LearnsetEntry> getLearnset() {
        return LEARNSET;
    }
}
