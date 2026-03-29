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

public class Horsea extends Pokemon {

    private static final List<LearnsetEntry> LEARNSET = new java.util.ArrayList<>();
    static {
        // Level Up Moves
        LEARNSET.add(
            new LearnsetEntry(
                Bubble.INSTANCE,
                Source.LEVEL,
                1
            )
        );
        LEARNSET.add(
            new LearnsetEntry(
                Smokescreen.INSTANCE,
                Source.LEVEL,
                19
            )
        );
        LEARNSET.add(
            new LearnsetEntry(
                Leer.INSTANCE,
                Source.LEVEL,
                24
            )
        );
        LEARNSET.add(
            new LearnsetEntry(
                WaterGun.INSTANCE,
                Source.LEVEL,
                30
            )
        );
        LEARNSET.add(
            new LearnsetEntry(
                Agility.INSTANCE,
                Source.LEVEL,
                37
            )
        );
        LEARNSET.add(
            new LearnsetEntry(
                HydroPump.INSTANCE,
                Source.LEVEL,
                45
            )
        );

        // HM Moves
        LEARNSET.add(
            new LearnsetEntry(
                Surf.INSTANCE,
                Source.HM,
                3
            )
        );

        // TM Moves
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
                BubbleBeam.INSTANCE,
                Source.TM,
                11
            )
        );
        LEARNSET.add(
            new LearnsetEntry(
                WaterGun.INSTANCE,
                Source.TM,
                12
            )
        );
        LEARNSET.add(
            new LearnsetEntry(
                IceBeam.INSTANCE,
                Source.TM,
                13
            )
        );
        LEARNSET.add(
            new LearnsetEntry(
                Blizzard.INSTANCE,
                Source.TM,
                14
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

    public Horsea(String nickname) {
        super(
            PokeSpecies.HORSEA,
            116,
            Type.WATER,
            Type.NONE,
            5,
            30,
            40,
            70,
            70,
            25,
            60
        );

        this.setNickname(nickname);
        this.setEvYield(Stat.SPECIAL_ATTACK, 1); // Horsea yields 1 EV point in Special Attack when defeated
        this.generateRandomIVs();
        StatCalculator.calculateAllStats(this);
    }

    @Override
    public List<LearnsetEntry> getLearnset() {
        return LEARNSET;
    }
}
