package pokemonGame;
import java.io.Console;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MoveSlot {

    private static final Logger LOGGER = LoggerFactory.getLogger(MoveSlot.class);
    private final Move move; // Immutable reference to the move assigned to this slot
    private int currentPP; // Mutable current PP for the move

    public MoveSlot(Move move) {
        this.move = move;
        this.currentPP = move.getMaxPp(); // Initialize current PP to max PP of the move
    }

    public boolean use() {
        if (currentPP > 0) {
            currentPP--; // Decrease PP by 1 when the move is used
            return true;
        } else {
            LOGGER.info("No PP left for move: {}", move.getMoveName());
            return false;
        }
    }

    public void restore() {
        currentPP = move.getMaxPp(); // Restore PP to max PP of the move
    }

    public int getCurrentPP() {
        return currentPP;
    }

    public Move getMove() {
        return move;
    }

    public static void teachMoveFromLearnset(Pokemon p) {
        Console console = System.console();
        if (console == null) {
            LOGGER.error("No console available. Cannot teach move.");
            return;
        }
        List<LearnsetEntry> eligible = LearnsetEntry.getEligibleMoves(p);

        if (eligible.isEmpty()) {
            LOGGER.info("{} has no new moves to learn right now.", p.getNickname());
            return;
        }

        LOGGER.info("Pick a move for {} to learn:", p.getNickname());
        for (int i = 0; i < eligible.size(); i++) {
            LearnsetEntry e = eligible.get(i);
            LOGGER.info("  {}: {} ({} {})", i + 1, e.getMove().getMoveName(), e.getSource(), e.getParameter());
        }

        LOGGER.info("Choice (1-{}): ", eligible.size());
        int choice = Integer.parseInt(console.readLine());
        if (choice < 1 || choice > eligible.size()) {
            LOGGER.warn("Invalid choice. No move learned.");
            return;
        }

        Move picked = eligible.get(choice - 1).getMove();

        // If the moveset isn't full, just add it directly
        if (p.addMove(picked)) {
            LOGGER.info("{} learned {}!", p.getNickname(), picked.getMoveName());
            return;
        }

        // Moveset is full — ask which move to replace
        LOGGER.info("{} already knows 4 moves.", p.getNickname());
        LOGGER.info("Replace a move with {}? (yes/no)", picked.getMoveName());
        String response = console.readLine();
        if (!response.equalsIgnoreCase("yes")) {
            LOGGER.info("{} did not learn {}.", p.getNickname(), picked.getMoveName());
            return;
        }

        LOGGER.info("Which move should be forgotten?");
        for (int i = 0; i < p.getMoveset().size(); i++) {
            LOGGER.info("  {}: {}", i + 1, p.getMoveset().get(i).getMove().getMoveName());
        }
        LOGGER.info("Choice (1-4): ");
        int slot = Integer.parseInt(console.readLine());
        if (p.replaceMove(slot - 1, picked)) {
            LOGGER.info("{} forgot a move and learned {}!", p.getNickname(), picked.getMoveName());
        } else {
            LOGGER.warn("Invalid slot. Move not learned.");
        }
    }

}
