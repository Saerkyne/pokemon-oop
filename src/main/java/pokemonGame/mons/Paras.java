package pokemonGame.mons;

import pokemonGame.Pokemon;
import pokemonGame.moves.*;
import pokemonGame.PokeSpecies;
import pokemonGame.LearnsetEntry;
import pokemonGame.LearnsetEntry.Source;
import pokemonGame.TypeChart.Type;
import pokemonGame.Stat;
import java.util.List;

public class Paras extends Pokemon {

    private static final List<LearnsetEntry> LEARNSET = new java.util.ArrayList<>();
    static {
        // Level-up moves
        LEARNSET.add(
            new LearnsetEntry(
                Scratch.INSTANCE,
                Source.LEVEL,
                1
            )
        );
        LEARNSET.add(
            new LearnsetEntry(
                StunSpore.INSTANCE,
                Source.LEVEL,
                13
            )
        );
        LEARNSET.add(
            new LearnsetEntry(
                LeechLife.INSTANCE,
                Source.LEVEL,
                20
            )
        );
        LEARNSET.add(
            new LearnsetEntry(
                Spore.INSTANCE,
                Source.LEVEL,
                27
            )
        );
        LEARNSET.add(
            new LearnsetEntry(
                Slash.INSTANCE,
                Source.LEVEL,
                34
            )
        );
        LEARNSET.add(
            new LearnsetEntry(
                Growth.INSTANCE,
                Source.LEVEL,
                41
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

    public Paras(String nickname) {
        super(
            PokeSpecies.PARAS,
            46,
            Type.BUG,
            Type.GRASS,
            5,
            35,
            70,
            55,
            45,
            55,
            25
        );

        this.setNickname(nickname);
        this.setEvYield(Stat.ATTACK, 1);
        this.generateRandomIVs();
        this.calculateCurrentStats();
    }

    @Override
    public List<LearnsetEntry> getLearnset() {
        return LEARNSET;
    }
}
