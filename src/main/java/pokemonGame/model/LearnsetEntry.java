package pokemonGame.model;

/**
 * Associates a {@link Move} with how and when a Pokémon can learn it.
 * Each species' learnset is a list of LearnsetEntry objects defined in
 * the species subclass. The {@link Source} enum indicates the learning
 * method (level-up, TM, etc.) and the parameter holds the detail
 * (level number, TM number).
 *
 * @see Move
 * @see Pokemon
 */
// TODO [📚 LEARNING | review 2026-04-20]: Textbook record candidate. Why: all fields final, pure value type, no behavior beyond accessors. Records exist precisely for this (Java 16+) and eliminate ~25 lines of boilerplate. Fix: `public record LearnsetEntry(Move move, Source source, int parameter) { public enum Source { LEVEL, TM, EGG, TUTOR, HM, PRE_EVOLUTION } }` — 152 files can keep using getMove()/getSource()/getParameter() via record accessors (same names).
public class LearnsetEntry {
    // Egg and Tutor moves don't exist in Gen 1, but we can still use the same
    //  class to represent them for future generations if added.
    // The parameter field can be used for different purposes depending on the source:
    // - For LEVEL, it indicates the level at which the move is learned.
    // - For TM/HM, it indicates the TM/HM number.
    // - For EGG, TUTOR, PRE_EVOLUTION, it can be set to 0, 1, or ignored.
    public enum Source { LEVEL, TM, EGG, TUTOR, HM, PRE_EVOLUTION /* … */ }

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

    
}
