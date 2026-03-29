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
