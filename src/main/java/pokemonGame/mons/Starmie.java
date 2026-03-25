package pokemonGame.mons;
import pokemonGame.Pokemon;
import pokemonGame.LearnsetEntry;
import pokemonGame.moves.*;
import java.util.List;

public class Starmie extends Pokemon {

    private static final List<LearnsetEntry> LEARNSET = new java.util.ArrayList<>();
    static {
        LEARNSET.add(new LearnsetEntry(Harden.INSTANCE, LearnsetEntry.Source.LEVEL, 1));
        LEARNSET.add(new LearnsetEntry(Tackle.INSTANCE, LearnsetEntry.Source.LEVEL, 1));
        LEARNSET.add(new LearnsetEntry(WaterGun.INSTANCE, LearnsetEntry.Source.LEVEL, 1));

        LEARNSET.add(new LearnsetEntry(HydroPump.INSTANCE, LearnsetEntry.Source.PRE_EVOLUTION, 0));
        LEARNSET.add(new LearnsetEntry(LightScreen.INSTANCE, LearnsetEntry.Source.PRE_EVOLUTION, 0));
        LEARNSET.add(new LearnsetEntry(Minimize.INSTANCE, LearnsetEntry.Source.PRE_EVOLUTION, 0));
        LEARNSET.add(new LearnsetEntry(Recover.INSTANCE, LearnsetEntry.Source.PRE_EVOLUTION, 0));
        LEARNSET.add(new LearnsetEntry(Swift.INSTANCE, LearnsetEntry.Source.PRE_EVOLUTION, 0));

        LEARNSET.add(new LearnsetEntry(Surf.INSTANCE, LearnsetEntry.Source.HM, 3));
        LEARNSET.add(new LearnsetEntry(Flash.INSTANCE, LearnsetEntry.Source.HM, 5));

        LEARNSET.add(new LearnsetEntry(Toxic.INSTANCE, LearnsetEntry.Source.TM, 6));
        LEARNSET.add(new LearnsetEntry(TakeDown.INSTANCE, LearnsetEntry.Source.TM, 9));
        LEARNSET.add(new LearnsetEntry(DoubleEdge.INSTANCE, LearnsetEntry.Source.TM, 10));
        LEARNSET.add(new LearnsetEntry(BubbleBeam.INSTANCE, LearnsetEntry.Source.TM, 11));
        LEARNSET.add(new LearnsetEntry(WaterGun.INSTANCE, LearnsetEntry.Source.TM, 12));
        LEARNSET.add(new LearnsetEntry(IceBeam.INSTANCE, LearnsetEntry.Source.TM, 13));
        LEARNSET.add(new LearnsetEntry(Blizzard.INSTANCE, LearnsetEntry.Source.TM, 14));
        LEARNSET.add(new LearnsetEntry(HyperBeam.INSTANCE, LearnsetEntry.Source.TM, 15));
        LEARNSET.add(new LearnsetEntry(Rage.INSTANCE, LearnsetEntry.Source.TM, 20));
        LEARNSET.add(new LearnsetEntry(Thunderbolt.INSTANCE, LearnsetEntry.Source.TM, 24));
        LEARNSET.add(new LearnsetEntry(Thunder.INSTANCE, LearnsetEntry.Source.TM, 25));
        LEARNSET.add(new LearnsetEntry(Psychic.INSTANCE, LearnsetEntry.Source.TM, 29));
        LEARNSET.add(new LearnsetEntry(Teleport.INSTANCE, LearnsetEntry.Source.TM, 30));
        LEARNSET.add(new LearnsetEntry(Mimic.INSTANCE, LearnsetEntry.Source.TM, 31));
        LEARNSET.add(new LearnsetEntry(DoubleTeam.INSTANCE, LearnsetEntry.Source.TM, 32));
        LEARNSET.add(new LearnsetEntry(Reflect.INSTANCE, LearnsetEntry.Source.TM, 33));
        LEARNSET.add(new LearnsetEntry(Bide.INSTANCE, LearnsetEntry.Source.TM, 34));
        LEARNSET.add(new LearnsetEntry(Swift.INSTANCE, LearnsetEntry.Source.TM, 39));
        LEARNSET.add(new LearnsetEntry(SkullBash.INSTANCE, LearnsetEntry.Source.TM, 40));
        LEARNSET.add(new LearnsetEntry(Rest.INSTANCE, LearnsetEntry.Source.TM, 44));
        LEARNSET.add(new LearnsetEntry(ThunderWave.INSTANCE, LearnsetEntry.Source.TM, 45));
        LEARNSET.add(new LearnsetEntry(Psywave.INSTANCE, LearnsetEntry.Source.TM, 46));
        LEARNSET.add(new LearnsetEntry(TriAttack.INSTANCE, LearnsetEntry.Source.TM, 49));
        LEARNSET.add(new LearnsetEntry(Substitute.INSTANCE, LearnsetEntry.Source.TM, 50));
    }

    public Starmie(String nickname) {
        super(
            "Starmie",
            121,
            "Water",
            "Psychic",
            5,
            60,
            75,
            85,
            100,
            85,
            115
        );

        this.setNickname(nickname);

        int[] evYield = {0, 0, 0, 0, 0, 2}; // Starmie yields 2 EV points in Speed when defeated
        this.setEvYield(evYield);
        this.generateRandomIVs();
        this.calculateCurrentStats();
    }

    @Override
    public List<LearnsetEntry> getLearnset() {
        return LEARNSET;
    }
}
