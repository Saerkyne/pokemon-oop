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

public class Weezing extends Pokemon {

    private static final List<LearnsetEntry> LEARNSET = new java.util.ArrayList<>();
    static {
        // Level-up moves
        LEARNSET.add(
            new LearnsetEntry(
                Sludge.INSTANCE,
                Source.LEVEL,
                1
            )
        );
        LEARNSET.add(
            new LearnsetEntry(
                Smog.INSTANCE,
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
                Sludge.INSTANCE,
                Source.LEVEL,
                32
            )
        );
        LEARNSET.add(
            new LearnsetEntry(
                Smokescreen.INSTANCE,
                Source.LEVEL,
                39
            )
        );
        LEARNSET.add(
            new LearnsetEntry(
                SelfDestruct.INSTANCE,
                Source.LEVEL,
                43
            )
        );
        LEARNSET.add(
            new LearnsetEntry(
                Haze.INSTANCE,
                Source.LEVEL,
                49
            )
        );
        LEARNSET.add(
            new LearnsetEntry(
                Explosion.INSTANCE,
                Source.LEVEL,
                53
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
                Thunderbolt.INSTANCE,
                Source.TM,
                24
            )
        );
        LEARNSET.add(
            new LearnsetEntry(
                Thunder.INSTANCE,
                Source.TM,
                25
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
                SelfDestruct.INSTANCE,
                Source.TM,
                36
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
                Rest.INSTANCE,
                Source.TM,
                44
            )
        );
        LEARNSET.add(
            new LearnsetEntry(
                Explosion.INSTANCE,
                Source.TM,
                47
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

    public Weezing(String nickname) {
        super(
            PokeSpecies.WEEZING,
            110,
            Type.POISON,
            Type.NONE,
            5,
            65,
            90,
            120,
            85,
            70,
            60
        );

        this.setNickname(nickname);
        this.setEvYield(Stat.DEFENSE, 2);
        this.generateRandomIVs();
        StatCalculator.calculateAllStats(this);
    }

    @Override
    public List<LearnsetEntry> getLearnset() {
        return LEARNSET;
    }
}
