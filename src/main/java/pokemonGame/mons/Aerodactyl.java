package pokemonGame.mons;
import pokemonGame.Pokemon;
import pokemonGame.moves.*;
import pokemonGame.LearnsetEntry;
import java.util.List;

public class Aerodactyl extends Pokemon {

    private static final List<LearnsetEntry> LEARNSET = new java.util.ArrayList<>();
    static {
         // Level Up Moves
        LEARNSET.add(new LearnsetEntry(Agility.INSTANCE, LearnsetEntry.Source.LEVEL, 1));
        LEARNSET.add(new LearnsetEntry(WingAttack.INSTANCE, LearnsetEntry.Source.LEVEL, 1));
        LEARNSET.add(new LearnsetEntry(Supersonic.INSTANCE, LearnsetEntry.Source.LEVEL, 33));
        LEARNSET.add(new LearnsetEntry(Bite.INSTANCE, LearnsetEntry.Source.LEVEL, 38));
        LEARNSET.add(new LearnsetEntry(TakeDown.INSTANCE, LearnsetEntry.Source.LEVEL, 45));
        LEARNSET.add(new LearnsetEntry(HyperBeam.INSTANCE, LearnsetEntry.Source.LEVEL, 54));
        // HM Moves
        LEARNSET.add(new LearnsetEntry(Fly.INSTANCE, LearnsetEntry.Source.HM, 2));
        // TM Moves
        LEARNSET.add(new LearnsetEntry(RazorWind.INSTANCE, LearnsetEntry.Source.TM, 2));
        LEARNSET.add(new LearnsetEntry(Whirlwind.INSTANCE, LearnsetEntry.Source.TM, 4));
        LEARNSET.add(new LearnsetEntry(Toxic.INSTANCE, LearnsetEntry.Source.TM, 6));
        LEARNSET.add(new LearnsetEntry(TakeDown.INSTANCE, LearnsetEntry.Source.TM, 9));
        LEARNSET.add(new LearnsetEntry(DoubleEdge.INSTANCE, LearnsetEntry.Source.TM, 10));
        LEARNSET.add(new LearnsetEntry(HyperBeam.INSTANCE, LearnsetEntry.Source.TM, 15));
        LEARNSET.add(new LearnsetEntry(Rage.INSTANCE, LearnsetEntry.Source.TM, 20));
        LEARNSET.add(new LearnsetEntry(DragonRage.INSTANCE, LearnsetEntry.Source.TM, 23));
        LEARNSET.add(new LearnsetEntry(Mimic.INSTANCE, LearnsetEntry.Source.TM, 31));
        LEARNSET.add(new LearnsetEntry(DoubleTeam.INSTANCE, LearnsetEntry.Source.TM, 32));
        LEARNSET.add(new LearnsetEntry(Reflect.INSTANCE, LearnsetEntry.Source.TM, 33));
        LEARNSET.add(new LearnsetEntry(Bide.INSTANCE, LearnsetEntry.Source.TM, 34));
        LEARNSET.add(new LearnsetEntry(FireBlast.INSTANCE, LearnsetEntry.Source.TM, 38));
        LEARNSET.add(new LearnsetEntry(Swift.INSTANCE, LearnsetEntry.Source.TM, 39));
        LEARNSET.add(new LearnsetEntry(SkyAttack.INSTANCE, LearnsetEntry.Source.TM, 43));
        LEARNSET.add(new LearnsetEntry(Rest.INSTANCE, LearnsetEntry.Source.TM, 44));
        LEARNSET.add(new LearnsetEntry(Substitute.INSTANCE, LearnsetEntry.Source.TM, 50));
    }

    public Aerodactyl(String nickname) {
        super(
            "Aerodactyl",
            142,
            "Rock",
            "Flying",
            5,
            80,
            105,
            65,
            60,
            75,
            130
        );

        this.setNickname(nickname);

        int[] evYield = {0, 0, 0, 0, 0, 2}; // Aerodactyl yields 2 EV points in Speed when defeated
        this.setEvYield(evYield);
        this.generateRandomIVs();
        this.calculateCurrentStats();
    }

    @Override
    public List<LearnsetEntry> getLearnset() {
        return LEARNSET;
    }
}
