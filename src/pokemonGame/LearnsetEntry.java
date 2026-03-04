package pokemonGame;

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
}
