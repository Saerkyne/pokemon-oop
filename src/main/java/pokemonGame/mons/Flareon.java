package pokemonGame.mons;

import pokemonGame.Pokemon;
import pokemonGame.moves.*;
import pokemonGame.PokeSpecies;
import pokemonGame.LearnsetEntry;
import pokemonGame.LearnsetEntry.Source;
import pokemonGame.TypeChart.Type;
import pokemonGame.Stat;
import pokemonGame.StatCalculator;
import java.util.List;

public class Flareon extends Pokemon {

    private static final List<LearnsetEntry> LEARNSET = new java.util.ArrayList<>();
    static {
        // Level up moves
        LEARNSET.add(
            new LearnsetEntry(
                Ember.INSTANCE,
                Source.LEVEL,
                1
            )
        );
        LEARNSET.add(
            new LearnsetEntry(
                QuickAttack.INSTANCE,
                Source.LEVEL,
                1
            )
        );
        LEARNSET.add(
            new LearnsetEntry(
                SandAttack.INSTANCE,
                Source.LEVEL,
                1
            )
        );
        LEARNSET.add(
            new LearnsetEntry(
                Tackle.INSTANCE,
                Source.LEVEL,
                1
            )
        );
        LEARNSET.add(
            new LearnsetEntry(
                QuickAttack.INSTANCE,
                Source.LEVEL,
                27
            )
        );
        LEARNSET.add(
            new LearnsetEntry(
                Ember.INSTANCE,
                Source.LEVEL,
                31
            )
        );
        LEARNSET.add(
            new LearnsetEntry(
                TailWhip.INSTANCE,
                Source.LEVEL,
                37
            )
        );
        LEARNSET.add(
            new LearnsetEntry(
                Bite.INSTANCE,
                Source.LEVEL,
                40
            )
        );
        LEARNSET.add(
            new LearnsetEntry(
                Leer.INSTANCE,
                Source.LEVEL,
                42
            )
        );
        LEARNSET.add(
            new LearnsetEntry(
                FireSpin.INSTANCE,
                Source.LEVEL,
                44
            )
        );
        LEARNSET.add(
            new LearnsetEntry(
                Rage.INSTANCE,
                Source.LEVEL,
                48
            )
        );
        LEARNSET.add(
            new LearnsetEntry(
                Flamethrower.INSTANCE,
                Source.LEVEL,
                54
            )
        );

        // Pre-evolution moves
        LEARNSET.add(
            new LearnsetEntry(
                TakeDown.INSTANCE,
                Source.PRE_EVOLUTION,
                0
            )
        );

        // TM moves
        LEARNSET.add(
            new LearnsetEntry(
                Toxic.INSTANCE,
                Source.TM,
                6
            )
        );
        LEARNSET.add(
            new LearnsetEntry(
                BodySlam.INSTANCE,
                Source.TM,
                8
            )
        );
        LEARNSET.add(
            new LearnsetEntry(
                TakeDown.INSTANCE,
                Source.TM,
                9
            )
        );
        LEARNSET.add(
            new LearnsetEntry(
                DoubleEdge.INSTANCE,
                Source.TM,
                10
            )
        );
        LEARNSET.add(
            new LearnsetEntry(
                HyperBeam.INSTANCE,
                Source.TM,
                15
            )
        );
        LEARNSET.add(
            new LearnsetEntry(
                Rage.INSTANCE,
                Source.TM,
                20
            )
        );
        LEARNSET.add(
            new LearnsetEntry(
                Mimic.INSTANCE,
                Source.TM,
                31
            )
        );
        LEARNSET.add(
            new LearnsetEntry(
                DoubleTeam.INSTANCE,
                Source.TM,
                32
            )
        );
        LEARNSET.add(
            new LearnsetEntry(
                Reflect.INSTANCE,
                Source.TM,
                33
            )
        );
        LEARNSET.add(
            new LearnsetEntry(
                Bide.INSTANCE,
                Source.TM,
                34
            )
        );
        LEARNSET.add(
            new LearnsetEntry(
                FireBlast.INSTANCE,
                Source.TM,
                38
            )
        );
        LEARNSET.add(
            new LearnsetEntry(
                Swift.INSTANCE,
                Source.TM,
                39
            )
        );
        LEARNSET.add(
            new LearnsetEntry(
                SkullBash.INSTANCE,
                Source.TM,
                40
            )
        );
        LEARNSET.add(
            new LearnsetEntry(
                Rest.INSTANCE,
                Source.TM,
                44
            )
        );
        LEARNSET.add(
            new LearnsetEntry(
                Substitute.INSTANCE,
                Source.TM,
                50
            )
        );
    }

    public Flareon(String nickname) {
        super(
            PokeSpecies.FLAREON,
            136,
            Type.FIRE,
            Type.NONE,
            5,
            65,
            130,
            60,
            95,
            110,
            65
        );

        this.setNickname(nickname);
        this.setEvYield(Stat.ATTACK, 2);
        this.generateRandomIVs();
        StatCalculator.calculateAllStats(this);
    }

    @Override
    public List<LearnsetEntry> getLearnset() {
        return LEARNSET;
    }
}
