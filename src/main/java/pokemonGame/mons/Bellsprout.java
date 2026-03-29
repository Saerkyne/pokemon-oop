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

public class Bellsprout extends Pokemon {

    private static final List<LearnsetEntry> LEARNSET = new java.util.ArrayList<>();
    static {
        // Level up moves
        LEARNSET.add(
            new LearnsetEntry(
                VineWhip.INSTANCE,
                Source.LEVEL,
                1
            )
        );
        LEARNSET.add(
            new LearnsetEntry(
                Growth.INSTANCE,
                Source.LEVEL,
                1
            )
        );
        LEARNSET.add(
            new LearnsetEntry(
                Wrap.INSTANCE,
                Source.LEVEL,
                13
            )
        );
        LEARNSET.add(
            new LearnsetEntry(
                PoisonPowder.INSTANCE,
                Source.LEVEL,
                15
            )
        );
        LEARNSET.add(
            new LearnsetEntry(
                SleepPowder.INSTANCE,
                Source.LEVEL,
                18
            )
        );
        LEARNSET.add(
            new LearnsetEntry(
                StunSpore.INSTANCE,
                Source.LEVEL,
                21
            )
        );
        LEARNSET.add(
            new LearnsetEntry(
                Acid.INSTANCE,
                Source.LEVEL,
                26
            )
        );
        LEARNSET.add(
            new LearnsetEntry(
                RazorLeaf.INSTANCE,
                Source.LEVEL,
                33
            )
        );
        LEARNSET.add(
            new LearnsetEntry(
                Slam.INSTANCE,
                Source.LEVEL,
                42
            )
        );

        // HM moves
        LEARNSET.add(
            new LearnsetEntry(
                Cut.INSTANCE,
                Source.HM,
                1
            )
        );

        // TM moves
        LEARNSET.add(
            new LearnsetEntry(
                SwordsDance.INSTANCE,
                Source.TM,
                3
            )
        );
        LEARNSET.add(
            new LearnsetEntry(
                Toxic.INSTANCE,
                Source.TM,
                6
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
                Rage.INSTANCE,
                Source.TM,
                20
            )
        );
        LEARNSET.add(
            new LearnsetEntry(
                MegaDrain.INSTANCE,
                Source.TM,
                21
            )
        );
        LEARNSET.add(
            new LearnsetEntry(
                SolarBeam.INSTANCE,
                Source.TM,
                22
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

    public Bellsprout(String nickname) {
        super(
            PokeSpecies.BELLSPROUT,
            69,
            Type.GRASS,
            Type.POISON,
            5,
            50,
            75,
            35,
            70,
            30,
            40
        );

        this.setNickname(nickname);
        this.setEvYield(Stat.ATTACK, 1); // Bellsprout yields 1 EV point in Attack when defeated
        this.generateRandomIVs();
        StatCalculator.calculateAllStats(this);
    }

    @Override
    public List<LearnsetEntry> getLearnset() {
        return LEARNSET;
    }
}
