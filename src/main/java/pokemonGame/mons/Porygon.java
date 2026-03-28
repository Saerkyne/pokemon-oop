package pokemonGame.mons;

import pokemonGame.Pokemon;
import pokemonGame.moves.*;
import pokemonGame.PokeSpecies;
import pokemonGame.LearnsetEntry;
import pokemonGame.LearnsetEntry.Source;
import pokemonGame.TypeChart.Type;
import pokemonGame.Stat;
import java.util.List;

public class Porygon extends Pokemon {

    private static final List<LearnsetEntry> LEARNSET = new java.util.ArrayList<>();
    static {
        // Level-up moves
        LEARNSET.add(
            new LearnsetEntry(
                Conversion.INSTANCE,
                Source.LEVEL,
                1
            )
        );
        LEARNSET.add(
            new LearnsetEntry(
                Sharpen.INSTANCE,
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
                Psybeam.INSTANCE,
                Source.LEVEL,
                23
            )
        );
        LEARNSET.add(
            new LearnsetEntry(
                Recover.INSTANCE,
                Source.LEVEL,
                28
            )
        );
        LEARNSET.add(
            new LearnsetEntry(
                Agility.INSTANCE,
                Source.LEVEL,
                35
            )
        );
        LEARNSET.add(
            new LearnsetEntry(
                TriAttack.INSTANCE,
                Source.LEVEL,
                42
            )
        );

        // HM moves
        LEARNSET.add(
            new LearnsetEntry(
                Flash.INSTANCE,
                Source.HM,
                5
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
                ThunderWave.INSTANCE,
                Source.TM,
                45
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
                TriAttack.INSTANCE,
                Source.TM,
                49
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

    public Porygon(String nickname) {
        super(
            PokeSpecies.PORYGON,
            137,
            Type.NORMAL,
            Type.NONE,
            5,
            65,
            60,
            70,
            85,
            75,
            40
        );

        this.setNickname(nickname);
        this.setEvYield(Stat.SPECIAL_ATTACK, 1);
        this.generateRandomIVs();
        this.calculateCurrentStats();
    }

    @Override
    public List<LearnsetEntry> getLearnset() {
        return LEARNSET;
    }
}
