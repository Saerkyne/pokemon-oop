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

public class Butterfree extends Pokemon {

    private static final List<LearnsetEntry> LEARNSET = new java.util.ArrayList<>();
    static {
        // Level up moves
        LEARNSET.add(
            new LearnsetEntry(
                Confusion.INSTANCE,
                Source.LEVEL,
                1
            )
        );
        LEARNSET.add(
            new LearnsetEntry(
                Confusion.INSTANCE,
                Source.LEVEL,
                12
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
                StunSpore.INSTANCE,
                Source.LEVEL,
                16
            )
        );
        LEARNSET.add(
            new LearnsetEntry(
                SleepPowder.INSTANCE,
                Source.LEVEL,
                17
            )
        );
        LEARNSET.add(
            new LearnsetEntry(
                Supersonic.INSTANCE,
                Source.LEVEL,
                21
            )
        );
        LEARNSET.add(
            new LearnsetEntry(
                Whirlwind.INSTANCE,
                Source.LEVEL,
                26
            )
        );
        LEARNSET.add(
            new LearnsetEntry(
                Psybeam.INSTANCE,
                Source.LEVEL,
                32
            )
        );

        // Pre-evolution moves
        LEARNSET.add(
            new LearnsetEntry(
                Tackle.INSTANCE,
                Source.PRE_EVOLUTION,
                1
            )
        );
        LEARNSET.add(
            new LearnsetEntry(
                StringShot.INSTANCE,
                Source.PRE_EVOLUTION,
                1
            )
        );
        LEARNSET.add(
            new LearnsetEntry(
                Harden.INSTANCE,
                Source.PRE_EVOLUTION,
                1
            )
        );

        // TM moves
        LEARNSET.add(
            new LearnsetEntry(
                RazorWind.INSTANCE,
                Source.TM,
                2
            )
        );
        LEARNSET.add(
            new LearnsetEntry(
                Whirlwind.INSTANCE,
                Source.TM,
                4
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
                Psychic.INSTANCE,
                Source.TM,
                29
            )
        );
        LEARNSET.add(
            new LearnsetEntry(
                Teleport.INSTANCE,
                Source.TM,
                30
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
                Swift.INSTANCE,
                Source.TM,
                39
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
                Psywave.INSTANCE,
                Source.TM,
                46
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

    public Butterfree(String nickname) {
        super(
            PokeSpecies.BUTTERFREE,
            12,
            Type.BUG,
            Type.FLYING,
            5,
            60,
            45,
            50,
            90,
            80,
            70
        );

        this.setNickname(nickname);
        this.setEvYield(Stat.SPECIAL_ATTACK, 2); // Butterfree yields 2 EV points in Special Attack when defeated
        this.setEvYield(Stat.SPECIAL_DEFENSE, 1); // Butterfree yields 1 EV point in Special Defense when defeated
        this.generateRandomIVs();
        StatCalculator.calculateAllStats(this);
    }

    @Override
    public List<LearnsetEntry> getLearnset() {
        return LEARNSET;
    }
}
