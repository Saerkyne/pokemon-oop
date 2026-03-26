package pokemonGame.mons;

import pokemonGame.Pokemon;
import pokemonGame.moves.*;
import pokemonGame.PokeSpecies;
import pokemonGame.LearnsetEntry;
import pokemonGame.LearnsetEntry.Source;
import pokemonGame.TypeChart.Type;
import pokemonGame.Stat;
import java.util.List;

public class Charmeleon extends Pokemon {

    private static final List<LearnsetEntry> LEARNSET = new java.util.ArrayList<>();
    static {
        // Level up moves
        LEARNSET.add(
            new LearnsetEntry(
                Scratch.INSTANCE,
                Source.LEVEL,
                1
            )
        );
        LEARNSET.add(
            new LearnsetEntry(
                Growl.INSTANCE,
                Source.LEVEL,
                1
            )
        );
        LEARNSET.add(
            new LearnsetEntry(
                Ember.INSTANCE,
                Source.LEVEL,
                1
            )
        );
        LEARNSET.add(
            new LearnsetEntry(
                Ember.INSTANCE,
                Source.LEVEL,
                9
            )
        );
        LEARNSET.add(
            new LearnsetEntry(
                Leer.INSTANCE,
                Source.LEVEL,
                15
            )
        );
        LEARNSET.add(
            new LearnsetEntry(
                Rage.INSTANCE,
                Source.LEVEL,
                24
            )
        );
        LEARNSET.add(
            new LearnsetEntry(
                Slash.INSTANCE,
                Source.LEVEL,
                33
            )
        );
        LEARNSET.add(
            new LearnsetEntry(
                Flamethrower.INSTANCE,
                Source.LEVEL,
                42
            )
        );
        LEARNSET.add(
            new LearnsetEntry(
                FireSpin.INSTANCE,
                Source.LEVEL,
                56
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
        LEARNSET.add(
            new LearnsetEntry(
                Strength.INSTANCE,
                Source.HM,
                4
            )
        );

        // TM moves
        LEARNSET.add(
            new LearnsetEntry(
                MegaPunch.INSTANCE,
                Source.TM,
                1
            )
        );
        LEARNSET.add(
            new LearnsetEntry(
                SwordsDance.INSTANCE,
                Source.TM,
                3
            )
        );
        LEARNSET.add(
            new LearnsetEntry(
                MegaKick.INSTANCE,
                Source.TM,
                5
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
                Submission.INSTANCE,
                Source.TM,
                17
            )
        );
        LEARNSET.add(
            new LearnsetEntry(
                Counter.INSTANCE,
                Source.TM,
                18
            )
        );
        LEARNSET.add(
            new LearnsetEntry(
                SeismicToss.INSTANCE,
                Source.TM,
                19
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
                DragonRage.INSTANCE,
                Source.TM,
                23
            )
        );
        LEARNSET.add(
            new LearnsetEntry(
                Dig.INSTANCE,
                Source.TM,
                28
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

    public Charmeleon(String nickname) {
        super(
            PokeSpecies.CHARMELEON,
            5,
            Type.FIRE,
            Type.NONE,
            5,
            58,
            64,
            58,
            80,
            65,
            80
        );

        this.setNickname(nickname);
        this.setEvYield(Stat.SPECIAL_ATTACK, 1); // Charmeleon yields 1 EV point in Special Attack when defeated
        this.setEvYield(Stat.SPEED, 1); // Charmeleon yields 1 EV point in Speed when defeated
        this.generateRandomIVs();
        this.calculateCurrentStats();
    }

    @Override
    public List<LearnsetEntry> getLearnset() {
        return LEARNSET;
    }
}

