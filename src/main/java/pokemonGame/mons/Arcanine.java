package pokemonGame.mons;
import pokemonGame.Pokemon;
import pokemonGame.moves.*;
import pokemonGame.LearnsetEntry;
import java.util.List;

public class Arcanine extends Pokemon {

    private static final List<LearnsetEntry> LEARNSET = new java.util.ArrayList<>();
    static {
        // Level Up Moves
        LEARNSET.add(new LearnsetEntry(Ember.INSTANCE, LearnsetEntry.Source.LEVEL, 1));
        LEARNSET.add(new LearnsetEntry(Leer.INSTANCE, LearnsetEntry.Source.LEVEL, 1));
        LEARNSET.add(new LearnsetEntry(Roar.INSTANCE, LearnsetEntry.Source.LEVEL, 1));
        LEARNSET.add(new LearnsetEntry(TakeDown.INSTANCE, LearnsetEntry.Source.LEVEL, 1));
        // Pre-Evolution Moves (from Growlithe)
        LEARNSET.add(new LearnsetEntry(Bite.INSTANCE, LearnsetEntry.Source.PRE_EVOLUTION, 1));
        LEARNSET.add(new LearnsetEntry(Agility.INSTANCE, LearnsetEntry.Source.PRE_EVOLUTION, 1));
        LEARNSET.add(new LearnsetEntry(Flamethrower.INSTANCE, LearnsetEntry.Source.PRE_EVOLUTION, 1));
        // TM Moves
        LEARNSET.add(new LearnsetEntry(Toxic.INSTANCE, LearnsetEntry.Source.TM, 6));
        LEARNSET.add(new LearnsetEntry(BodySlam.INSTANCE, LearnsetEntry.Source.TM, 8));
        LEARNSET.add(new LearnsetEntry(TakeDown.INSTANCE, LearnsetEntry.Source.TM, 9));
        LEARNSET.add(new LearnsetEntry(DoubleEdge.INSTANCE, LearnsetEntry.Source.TM, 10));
        LEARNSET.add(new LearnsetEntry(HyperBeam.INSTANCE, LearnsetEntry.Source.TM, 15));
        LEARNSET.add(new LearnsetEntry(Rage.INSTANCE, LearnsetEntry.Source.TM, 20));
        LEARNSET.add(new LearnsetEntry(DragonRage.INSTANCE, LearnsetEntry.Source.TM, 23));
        LEARNSET.add(new LearnsetEntry(Dig.INSTANCE, LearnsetEntry.Source.TM, 28));
        LEARNSET.add(new LearnsetEntry(Teleport.INSTANCE, LearnsetEntry.Source.TM, 30));
        LEARNSET.add(new LearnsetEntry(Mimic.INSTANCE, LearnsetEntry.Source.TM, 31));
        LEARNSET.add(new LearnsetEntry(DoubleTeam.INSTANCE, LearnsetEntry.Source.TM, 32));
        LEARNSET.add(new LearnsetEntry(Reflect.INSTANCE, LearnsetEntry.Source.TM, 33));
        LEARNSET.add(new LearnsetEntry(Bide.INSTANCE, LearnsetEntry.Source.TM, 34));
        LEARNSET.add(new LearnsetEntry(FireBlast.INSTANCE, LearnsetEntry.Source.TM, 38));
        LEARNSET.add(new LearnsetEntry(Swift.INSTANCE, LearnsetEntry.Source.TM, 39));
        LEARNSET.add(new LearnsetEntry(SkullBash.INSTANCE, LearnsetEntry.Source.TM, 40));
        LEARNSET.add(new LearnsetEntry(Rest.INSTANCE, LearnsetEntry.Source.TM, 44));
        LEARNSET.add(new LearnsetEntry(Substitute.INSTANCE, LearnsetEntry.Source.TM, 50));
    }

    public Arcanine(String nickname) {
        super(
            "Arcanine",
            59,
            "Fire",
            null,
            5,
            90,
            110,
            80,
            100,
            80,
            95
        );

        this.setNickname(nickname);

        int[] evYield = {0, 2, 0, 0, 0, 0}; // Arcanine yields 2 EV points in Attack when defeated
        this.setEvYield(evYield);
        this.generateRandomIVs();
        this.calculateCurrentStats();
    }

    @Override
    public List<LearnsetEntry> getLearnset() {
        return LEARNSET;
    }
}
