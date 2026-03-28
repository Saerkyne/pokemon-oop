package pokemonGame.mons;

import pokemonGame.Pokemon;
import pokemonGame.moves.*;
import pokemonGame.PokeSpecies;
import pokemonGame.LearnsetEntry;
import pokemonGame.LearnsetEntry.Source;
import pokemonGame.TypeChart.Type;
import pokemonGame.Stat;
import java.util.List;

public class Ponyta extends Pokemon {

    private static final List<LearnsetEntry> LEARNSET = new java.util.ArrayList<>();
    static {
        // Level-up moves
        LEARNSET.add(
            new LearnsetEntry(
                Ember.INSTANCE,
                Source.LEVEL,
                1
            )
        );
        LEARNSET.add(
            new LearnsetEntry(
                TailWhip.INSTANCE,
                Source.LEVEL,
                30
            )
        );
        LEARNSET.add(
            new LearnsetEntry(
                Stomp.INSTANCE,
                Source.LEVEL,
                32
            )
        );
        LEARNSET.add(
            new LearnsetEntry(
                Growl.INSTANCE,
                Source.LEVEL,
                35
            )
        );
        LEARNSET.add(
            new LearnsetEntry(
                FireSpin.INSTANCE,
                Source.LEVEL,
                39
            )
        );
        LEARNSET.add(
            new LearnsetEntry(
                TakeDown.INSTANCE,
                Source.LEVEL,
                43
            )
        );
        LEARNSET.add(
            new LearnsetEntry(
                Agility.INSTANCE,
                Source.LEVEL,
                48
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
                HornDrill.INSTANCE,
                Source.TM,
                7
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

    public Ponyta(String nickname) {
        super(
            PokeSpecies.PONYTA,
            77,
            Type.FIRE,
            Type.NONE,
            5,
            50,
            85,
            55,
            65,
            65,
            90
        );

        this.setNickname(nickname);
        this.setEvYield(Stat.SPEED, 1);
        this.generateRandomIVs();
        this.calculateCurrentStats();
    }

    @Override
    public List<LearnsetEntry> getLearnset() {
        return LEARNSET;
    }
}
