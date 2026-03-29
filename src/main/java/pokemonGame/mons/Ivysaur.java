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

public class Ivysaur extends Pokemon {

    private static final List<LearnsetEntry> LEARNSET = new java.util.ArrayList<>();
    static {
        // Level Up Moves
        LEARNSET.add(
            new LearnsetEntry(
                Growl.INSTANCE,
                Source.LEVEL,
                1
            )
        );
        LEARNSET.add(
            new LearnsetEntry(
                LeechSeed.INSTANCE,
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
                LeechSeed.INSTANCE,
                Source.LEVEL,
                7
            )
        );
        LEARNSET.add(
            new LearnsetEntry(
                VineWhip.INSTANCE,
                Source.LEVEL,
                13
            )
        );
        LEARNSET.add(
            new LearnsetEntry(
                PoisonPowder.INSTANCE,
                Source.LEVEL,
                22
            )
        );
        LEARNSET.add(
            new LearnsetEntry(
                RazorLeaf.INSTANCE,
                Source.LEVEL,
                30
            )
        );
        LEARNSET.add(
            new LearnsetEntry(
                Growth.INSTANCE,
                Source.LEVEL,
                38
            )
        );
        LEARNSET.add(
            new LearnsetEntry(
                SleepPowder.INSTANCE,
                Source.LEVEL,
                46
            )
        );
        LEARNSET.add(
            new LearnsetEntry(
                SolarBeam.INSTANCE,
                Source.LEVEL,
                54
            )
        );

        // HM Moves
        LEARNSET.add(
            new LearnsetEntry(
                Cut.INSTANCE,
                Source.HM,
                1
            )
        );

        // TM Moves
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

    public Ivysaur(String nickname) {
        super(
            PokeSpecies.IVYSAUR,
            2,
            Type.GRASS,
            Type.POISON,
            5,
            60,
            62,
            63,
            80,
            80,
            60
        );

        this.setNickname(nickname);
        this.setEvYield(Stat.SPECIAL_ATTACK, 1);  // Ivysaur yields 1 EV point in Special Attack when defeated
        this.setEvYield(Stat.SPECIAL_DEFENSE, 1); // ... and 1 EV point in Special Defense when defeated
        this.generateRandomIVs();
        StatCalculator.calculateAllStats(this);
    }

    @Override
    public List<LearnsetEntry> getLearnset() {
        return LEARNSET;
    }
}

