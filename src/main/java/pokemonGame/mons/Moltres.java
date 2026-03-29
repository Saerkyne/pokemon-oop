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

public class Moltres extends Pokemon {

    private static final List<LearnsetEntry> LEARNSET = new java.util.ArrayList<>();
    static {
        // Level Up Moves
        LEARNSET.add(
            new LearnsetEntry(
                FireSpin.INSTANCE,
                Source.LEVEL,
                1
            )
        );
        LEARNSET.add(
            new LearnsetEntry(
                Peck.INSTANCE,
                Source.LEVEL,
                1
            )
        );
        LEARNSET.add(
            new LearnsetEntry(
                Leer.INSTANCE,
                Source.LEVEL,
                51
            )
        );
        LEARNSET.add(
            new LearnsetEntry(
                Agility.INSTANCE,
                Source.LEVEL,
                55
            )
        );
        LEARNSET.add(
            new LearnsetEntry(
                SkyAttack.INSTANCE,
                Source.LEVEL,
                60
            )
        );

        // HM Moves
        LEARNSET.add(
            new LearnsetEntry(
                Fly.INSTANCE,
                Source.HM,
                2
            )
        );

        // TM Moves
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

    public Moltres(String nickname) {
        super(
            PokeSpecies.MOLTRES,
            146,
            Type.FIRE,
            Type.FLYING,
            5,
            90,
            100,
            90,
            125,
            85,
            90
        );

        this.setNickname(nickname);
        this.setEvYield(Stat.SPECIAL_ATTACK, 3); // Moltres yields 3 EV points in Sp. Atk when defeated
        this.generateRandomIVs();
        StatCalculator.calculateAllStats(this);
    }

    @Override
    public List<LearnsetEntry> getLearnset() {
        return LEARNSET;
    }
}
