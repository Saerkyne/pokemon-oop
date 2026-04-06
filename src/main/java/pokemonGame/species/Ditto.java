package pokemonGame.species;

import pokemonGame.moves.*;
import pokemonGame.core.Stat;
import pokemonGame.core.StatCalculator;
import pokemonGame.core.TypeChart.Type;
import pokemonGame.model.LearnsetEntry;
import pokemonGame.model.Pokemon;
import pokemonGame.model.LearnsetEntry.Source;

import java.util.List;

public class Ditto extends Pokemon {

    private static final List<LearnsetEntry> LEARNSET = new java.util.ArrayList<>();
    static {
        // Level up moves
        LEARNSET.add(
            new LearnsetEntry(
                Transform.INSTANCE,
                Source.LEVEL,
                1
            )
        );
    }

    public Ditto(String nickname) {
        super(
            PokeSpecies.DITTO,
            132,
            Type.NORMAL,
            Type.NONE,
            5,
            48,
            48,
            48,
            48,
            48,
            48
        );

        this.setNickname(nickname);

        this.setEvYield(Stat.HP, 1);
        this.generateRandomIVs();
        StatCalculator.calculateAllStats(this);
    }

    @Override
    public List<LearnsetEntry> getLearnset() {
        return LEARNSET;
    }
}
