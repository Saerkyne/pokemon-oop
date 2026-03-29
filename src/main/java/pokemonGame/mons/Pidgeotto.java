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

public class Pidgeotto extends Pokemon {

    private static final List<LearnsetEntry> LEARNSET = new java.util.ArrayList<>();
    static {
        // Level-up moves
        LEARNSET.add(
            new LearnsetEntry(
                Gust.INSTANCE,
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
                SandAttack.INSTANCE,
                Source.LEVEL,
                5
            )
        );
        LEARNSET.add(
            new LearnsetEntry(
                QuickAttack.INSTANCE,
                Source.LEVEL,
                12
            )
        );
        LEARNSET.add(
            new LearnsetEntry(
                Whirlwind.INSTANCE,
                Source.LEVEL,
                21
            )
        );
        LEARNSET.add(
            new LearnsetEntry(
                WingAttack.INSTANCE,
                Source.LEVEL,
                31
            )
        );
        LEARNSET.add(
            new LearnsetEntry(
                Agility.INSTANCE,
                Source.LEVEL,
                40
            )
        );
        LEARNSET.add(
            new LearnsetEntry(
                MirrorMove.INSTANCE,
                Source.LEVEL,
                49
            )
        );

        // HM moves
        LEARNSET.add(
            new LearnsetEntry(
                Fly.INSTANCE,
                Source.HM,
                2
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
                Swift.INSTANCE,
                Source.TM,
                39
            )
        );
        LEARNSET.add(
            new LearnsetEntry(
                SkyAttack.INSTANCE,
                Source.TM,
                43
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

    public Pidgeotto(String nickname) {
        super(
            PokeSpecies.PIDGEOTTO,
            17,
            Type.NORMAL,
            Type.FLYING,
            5,
            63,
            60,
            55,
            50,
            50,
            71
        );

        this.setNickname(nickname);
        this.setEvYield(Stat.SPEED, 2);
        this.generateRandomIVs();
        StatCalculator.calculateAllStats(this);
    }

    @Override
    public List<LearnsetEntry> getLearnset() {
        return LEARNSET;
    }
}
