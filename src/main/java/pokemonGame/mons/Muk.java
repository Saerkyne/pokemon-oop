package pokemonGame.mons;
import pokemonGame.Pokemon;
import pokemonGame.LearnsetEntry;
import pokemonGame.moves.*;
import java.util.List;

public class Muk extends Pokemon {

    private static final List<LearnsetEntry> LEARNSET = new java.util.ArrayList<>();
    static {
        // Level up moves
        LEARNSET.add(new LearnsetEntry(Disable.INSTANCE, LearnsetEntry.Source.LEVEL, 1));
        LEARNSET.add(new LearnsetEntry(PoisonGas.INSTANCE, LearnsetEntry.Source.LEVEL, 1));
        LEARNSET.add(new LearnsetEntry(Pound.INSTANCE, LearnsetEntry.Source.LEVEL, 1));
        LEARNSET.add(new LearnsetEntry(PoisonGas.INSTANCE, LearnsetEntry.Source.LEVEL, 30));
        LEARNSET.add(new LearnsetEntry(Minimize.INSTANCE, LearnsetEntry.Source.LEVEL, 33));
        LEARNSET.add(new LearnsetEntry(Sludge.INSTANCE, LearnsetEntry.Source.LEVEL, 37));
        LEARNSET.add(new LearnsetEntry(Harden.INSTANCE, LearnsetEntry.Source.LEVEL, 45));
        LEARNSET.add(new LearnsetEntry(Screech.INSTANCE, LearnsetEntry.Source.LEVEL, 53));
        LEARNSET.add(new LearnsetEntry(AcidArmor.INSTANCE, LearnsetEntry.Source.LEVEL, 60));

        // TM moves
        LEARNSET.add(new LearnsetEntry(Toxic.INSTANCE, LearnsetEntry.Source.TM, 6));
        LEARNSET.add(new LearnsetEntry(BodySlam.INSTANCE, LearnsetEntry.Source.TM, 8));
        LEARNSET.add(new LearnsetEntry(HyperBeam.INSTANCE, LearnsetEntry.Source.TM, 15));
        LEARNSET.add(new LearnsetEntry(Rage.INSTANCE, LearnsetEntry.Source.TM, 20));
        LEARNSET.add(new LearnsetEntry(MegaDrain.INSTANCE, LearnsetEntry.Source.TM, 21));
        LEARNSET.add(new LearnsetEntry(Thunderbolt.INSTANCE, LearnsetEntry.Source.TM, 24));
        LEARNSET.add(new LearnsetEntry(Thunder.INSTANCE, LearnsetEntry.Source.TM, 25));
        LEARNSET.add(new LearnsetEntry(Mimic.INSTANCE, LearnsetEntry.Source.TM, 31));
        LEARNSET.add(new LearnsetEntry(DoubleTeam.INSTANCE, LearnsetEntry.Source.TM, 32));
        LEARNSET.add(new LearnsetEntry(Bide.INSTANCE, LearnsetEntry.Source.TM, 34));
        LEARNSET.add(new LearnsetEntry(SelfDestruct.INSTANCE, LearnsetEntry.Source.TM, 36));
        LEARNSET.add(new LearnsetEntry(FireBlast.INSTANCE, LearnsetEntry.Source.TM, 38));
        LEARNSET.add(new LearnsetEntry(Rest.INSTANCE, LearnsetEntry.Source.TM, 44));
        LEARNSET.add(new LearnsetEntry(Explosion.INSTANCE, LearnsetEntry.Source.TM, 47));
        LEARNSET.add(new LearnsetEntry(Substitute.INSTANCE, LearnsetEntry.Source.TM, 50));
    }

    public Muk(String nickname) {
        super(
            "Muk",
            89,
            "Poison",
            null,
            5,
            105,
            105,
            75,
            65,
            100,
            50
        );

        this.setNickname(nickname);

        int[] evYield = {1, 1, 0, 0, 0, 0}; // Muk yields 1 EV point in HP and 1 EV point in Attack when defeated
        this.setEvYield(evYield);
        this.generateRandomIVs();
        this.calculateCurrentStats();
    }

    @Override
    public List<LearnsetEntry> getLearnset() {
        return LEARNSET;
    }
}
