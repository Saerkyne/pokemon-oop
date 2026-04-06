package pokemonGame.species;

import pokemonGame.moves.*;
import pokemonGame.core.Stat;
import pokemonGame.core.StatCalculator;
import pokemonGame.core.TypeChart.Type;
import pokemonGame.model.LearnsetEntry;
import pokemonGame.model.Pokemon;
import pokemonGame.model.LearnsetEntry.Source;

import java.util.List;

public class Weedle extends Pokemon {

    private static final List<LearnsetEntry> LEARNSET = new java.util.ArrayList<>();
    static {
        // Level-up moves
        LEARNSET.add(
            new LearnsetEntry(
                PoisonSting.INSTANCE,
                Source.LEVEL,
                1
            )
        );
        LEARNSET.add(
            new LearnsetEntry(
                StringShot.INSTANCE,
                Source.LEVEL,
                1
            )
        );
    }

    public Weedle(String nickname) {
        super(
            PokeSpecies.WEEDLE,
            13,
            Type.BUG,
            Type.POISON,
            5,
            40,
            35,
            30,
            20,
            20,
            50
        );

        this.setNickname(nickname);
        this.setEvYield(Stat.SPEED, 1);
        this.generateRandomIVs();
        StatCalculator.calculateAllStats(this);
    }

    @Override
    public List<LearnsetEntry> getLearnset() {
        return LEARNSET;
    }
}
