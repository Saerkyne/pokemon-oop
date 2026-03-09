package pokemonGame;

import java.util.ArrayList;
import java.util.List;

public class LearnsetEntry {
    public enum Source { LEVEL, TM, EGG, TUTOR, HM /* … */ }

    private final Move move;
    private final Source source;
    private final int parameter;   // e.g. level number or TM id

    public LearnsetEntry(Move move, Source source, int parameter) {
        this.move = move;
        this.source = source;
        this.parameter = parameter;
    }

    public Move getMove() {
        return move;
    }
    
    public Source getSource() {
        return source;
    } 

    public int getParameter() {
        return parameter;
    }

    /**
     * Return the subset of a Pokémon's learnset that it is currently eligible
     * to learn — moves at or below its level that it doesn't already know.
     * This is a pure query: no I/O, no side-effects.  The caller can present
     * the list however it likes (terminal, Discord embed, GUI) and then call
     * {@link Pokemon#addMove} or {@link Pokemon#replaceMove} with the choice.
     *
     * @param p the Pokémon whose eligible moves to retrieve
     * @return  a list of LearnsetEntry the Pokémon can learn right now
     */
    public static List<LearnsetEntry> getEligibleMoves(Pokemon p) {
        if (p == null) {
            return new ArrayList<>();
        }
        List<LearnsetEntry> catalog = p.getLearnset();
        List<LearnsetEntry> eligible = new ArrayList<>();

        for (LearnsetEntry e : catalog) {
            // Skip moves above the Pokémon's current level
            if (e.getSource() == Source.LEVEL && p.getLevel() < e.getParameter()) {
                continue;
            }
            // Skip moves the Pokémon already knows
            boolean alreadyKnown = p.getMoveset().stream()
                    .anyMatch(m -> m.getMove().getMoveName().equals(e.getMove().getMoveName()));
            if (alreadyKnown) {
                continue;
            }
            eligible.add(e);
        }
        return eligible;
    }
}
