package pokemonGame.species;

import pokemonGame.moves.*;
import pokemonGame.core.Stat;
import pokemonGame.core.StatCalculator;
import pokemonGame.core.TypeChart.Type;
import pokemonGame.model.LearnsetEntry;
import pokemonGame.model.PokeSpecies;
import pokemonGame.model.Pokemon;
import pokemonGame.model.LearnsetEntry.Source;

import java.util.Collections;
import java.util.List;

public class Magikarp extends Pokemon {

    private static final List<LearnsetEntry> LEARNSET = new java.util.ArrayList<>();
    static {
        // Level Up Moves
        LEARNSET.add(
            new LearnsetEntry(
                Splash.INSTANCE,
                Source.LEVEL,
                1
            )
        );
        LEARNSET.add(
            new LearnsetEntry(
                Tackle.INSTANCE,
                Source.LEVEL,
                15
            )
        );
    }

    public Magikarp(String nickname) {
        super(
            PokeSpecies.MAGIKARP,
            129,
            Type.WATER,
            Type.NONE,
            5,
            20,
            10,
            55,
            15,
            20,
            80
        );

        this.setNickname(nickname);
        this.setEvYield(Stat.SPEED, 1); // Magikarp yields 1 EV point in Speed when defeated
        this.generateRandomIVs();
        StatCalculator.calculateAllStats(this);
    }

    @Override
    public List<LearnsetEntry> getLearnset() {
        return Collections.unmodifiableList(LEARNSET);
    }
}
