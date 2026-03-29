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

public class Venonat extends Pokemon {

    private static final List<LearnsetEntry> LEARNSET = new java.util.ArrayList<>();
    static {
        // Level-up moves
        LEARNSET.add(
            new LearnsetEntry(
                Disable.INSTANCE,
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
                PoisonPowder.INSTANCE,
                Source.LEVEL,
                24
            )
        );
        LEARNSET.add(
            new LearnsetEntry(
                LeechLife.INSTANCE,
                Source.LEVEL,
                27
            )
        );
        LEARNSET.add(
            new LearnsetEntry(
                StunSpore.INSTANCE,
                Source.LEVEL,
                30
            )
        );
        LEARNSET.add(
            new LearnsetEntry(
                Psybeam.INSTANCE,
                Source.LEVEL,
                35
            )
        );
        LEARNSET.add(
            new LearnsetEntry(
                SleepPowder.INSTANCE,
                Source.LEVEL,
                38
            )
        );
        LEARNSET.add(
            new LearnsetEntry(
                Psychic.INSTANCE,
                Source.LEVEL,
                43
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
                Psychic.INSTANCE,
                Source.TM,
                29
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

    public Venonat(String nickname) {
        super(
            PokeSpecies.VENONAT,
            48,
            Type.BUG,
            Type.POISON,
            5,
            60,
            55,
            50,
            40,
            55,
            45
        );

        this.setNickname(nickname);
        this.setEvYield(Stat.SPECIAL_DEFENSE, 1);
        this.generateRandomIVs();
        StatCalculator.calculateAllStats(this);
    }

    @Override
    public List<LearnsetEntry> getLearnset() {
        return LEARNSET;
    }
}
