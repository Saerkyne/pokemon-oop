package pokemonGame.mons;
import pokemonGame.Pokemon;
import pokemonGame.LearnsetEntry;
import pokemonGame.moves.*;
import java.util.List;

public class Shellder extends Pokemon {

    private static final List<LearnsetEntry> LEARNSET = new java.util.ArrayList<>();
    static {
        LEARNSET.add(new LearnsetEntry(Tackle.INSTANCE, LearnsetEntry.Source.LEVEL, 1));
        LEARNSET.add(new LearnsetEntry(Withdraw.INSTANCE, LearnsetEntry.Source.LEVEL, 1));
        LEARNSET.add(new LearnsetEntry(Supersonic.INSTANCE, LearnsetEntry.Source.LEVEL, 18));
        LEARNSET.add(new LearnsetEntry(Clamp.INSTANCE, LearnsetEntry.Source.LEVEL, 23));
        LEARNSET.add(new LearnsetEntry(AuroraBeam.INSTANCE, LearnsetEntry.Source.LEVEL, 30));
        LEARNSET.add(new LearnsetEntry(Leer.INSTANCE, LearnsetEntry.Source.LEVEL, 39));
        LEARNSET.add(new LearnsetEntry(IceBeam.INSTANCE, LearnsetEntry.Source.LEVEL, 50));

        LEARNSET.add(new LearnsetEntry(Surf.INSTANCE, LearnsetEntry.Source.HM, 3));

        LEARNSET.add(new LearnsetEntry(Toxic.INSTANCE, LearnsetEntry.Source.TM, 6));
        LEARNSET.add(new LearnsetEntry(TakeDown.INSTANCE, LearnsetEntry.Source.TM, 9));
        LEARNSET.add(new LearnsetEntry(DoubleEdge.INSTANCE, LearnsetEntry.Source.TM, 10));
        LEARNSET.add(new LearnsetEntry(BubbleBeam.INSTANCE, LearnsetEntry.Source.TM, 11));
        LEARNSET.add(new LearnsetEntry(WaterGun.INSTANCE, LearnsetEntry.Source.TM, 12));
        LEARNSET.add(new LearnsetEntry(IceBeam.INSTANCE, LearnsetEntry.Source.TM, 13));
        LEARNSET.add(new LearnsetEntry(Blizzard.INSTANCE, LearnsetEntry.Source.TM, 14));
        LEARNSET.add(new LearnsetEntry(Rage.INSTANCE, LearnsetEntry.Source.TM, 20));
        LEARNSET.add(new LearnsetEntry(Teleport.INSTANCE, LearnsetEntry.Source.TM, 30));
        LEARNSET.add(new LearnsetEntry(Mimic.INSTANCE, LearnsetEntry.Source.TM, 31));
        LEARNSET.add(new LearnsetEntry(DoubleTeam.INSTANCE, LearnsetEntry.Source.TM, 32));
        LEARNSET.add(new LearnsetEntry(Reflect.INSTANCE, LearnsetEntry.Source.TM, 33));
        LEARNSET.add(new LearnsetEntry(Bide.INSTANCE, LearnsetEntry.Source.TM, 34));
        LEARNSET.add(new LearnsetEntry(SelfDestruct.INSTANCE, LearnsetEntry.Source.TM, 36));
        LEARNSET.add(new LearnsetEntry(Swift.INSTANCE, LearnsetEntry.Source.TM, 39));
        LEARNSET.add(new LearnsetEntry(Rest.INSTANCE, LearnsetEntry.Source.TM, 44));
        LEARNSET.add(new LearnsetEntry(Explosion.INSTANCE, LearnsetEntry.Source.TM, 47));
        LEARNSET.add(new LearnsetEntry(TriAttack.INSTANCE, LearnsetEntry.Source.TM, 49));
        LEARNSET.add(new LearnsetEntry(Substitute.INSTANCE, LearnsetEntry.Source.TM, 50));
    }

    public Shellder(String nickname) {
        super(
            "Shellder",
            90,
            "Water",
            null,
            5,
            30,
            65,
            100,
            45,
            25,
            40
        );

        this.setNickname(nickname);

        int[] evYield = {0, 0, 1, 0, 0, 0}; // Shellder yields 1 EV point in Defense when defeated
        this.setEvYield(evYield);
        this.generateRandomIVs();
        this.calculateCurrentStats();
    }

    @Override
    public List<LearnsetEntry> getLearnset() {
        return LEARNSET;
    }
}
