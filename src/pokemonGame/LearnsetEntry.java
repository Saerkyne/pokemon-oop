package pokemonGame;

import java.util.List;

import pokemonGame.mons.Mewtwo;

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

    public static void teachFromLearnset(Pokemon p) {
        List<LearnsetEntry> catalog = p instanceof Mewtwo ? Mewtwo.getLearnset() : null /* …other species… */;
        System.out.println("Pick a move to learn from the catalog:");
        for (int i = 0; i < catalog.size(); i++) {
            LearnsetEntry e = catalog.get(i);
            if (e.getSource() == Source.LEVEL && p.getLevel() < e.getParameter())
                continue;  // skip moves that are above the Pokemon's current level
            // skip moves that are already known
            if (p.getMoveset().stream().anyMatch(m -> m.getMoveName().equals(e.getMove().getMoveName())))
                continue;
            System.out.printf("%d: %s : %s %d%n",
                            i + 1,
                            e.getMove().getMoveName(),
                            e.getSource(), e.getParameter());
        }
        String line = System.console().readLine("Choice (1-%d): ", catalog.size());
        int choice = Integer.parseInt(line);
        if (choice >= 1 && choice <= catalog.size())
            p.addMove(catalog.get(choice - 1).getMove());
    }
}
