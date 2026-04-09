package pokemonGame.service;

import java.io.Console;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pokemonGame.model.LearnsetEntry;
import pokemonGame.model.Move;
import pokemonGame.model.MoveSlot;
import pokemonGame.model.Pokemon;
import pokemonGame.model.LearnsetEntry.Source;
import pokemonGame.moves.PokeMove;

public class MoveSlotService {

    public static final Logger LOGGER = LoggerFactory.getLogger(MoveSlotService.class);


/**
 * Orchestrates move lifecycle: validating move slots, executing moves,
 * and updating move states. This service would interact with the MoveSlot model to track PP usage,
 * and ensure moves are executed according to game rules.
 * Needed functionality:
 * - teaching moves from a Pokemon's learnset
 * - validating move slots before execution
 * - executing moves and updating PP accordingly
 * - restoring PP when needed
 * - handling swapping moves in a Pokemon's moveset
 *
 * @see MoveSlot
 * @see Move
 * @see LearnsetEntry
 * @see PokeMove
 */

    public static void teachMoveFromLearnset(Pokemon p) {
        // This needs to be converted to Discord interaction for functionality
        Console console = System.console();
        if (console == null) {
            LOGGER.error("No console available. Cannot teach move.");
            return;
        }
        
        List<LearnsetEntry> eligible = MoveSlotService.getEligibleMoves(p);

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
        for (int i = 0; i < p.getMoveSet().size(); i++) {
            LOGGER.info("  {}: {}", i + 1, p.getMoveSet().get(i).getMove().getMoveName());
        }
        LOGGER.info("Choice (1-4): ");
        int slot = Integer.parseInt(console.readLine());
        if (p.replaceMove(slot - 1, picked)) {
            LOGGER.info("{} forgot a move and learned {}!", p.getNickname(), picked.getMoveName());
        } else {
            LOGGER.warn("Invalid slot. Move not learned.");
        }
    }

    public static void teachMove(Pokemon p, Move move) {
        if (p.addMove(move)) {
            LOGGER.info("{} learned {}!", p.getNickname(), move.getMoveName());
            return;
        }
    }

    public static Move getMoveByName(String moveName) {
        return PokeMove.fromString(moveName).createMove();
    }
    
    public static boolean use(MoveSlot slot) {
        int currentPP = slot.getCurrentPP();
        if (currentPP > 0) {
            slot.setCurrentPP(currentPP - 1); // Decrease PP by 1 when the move is used
            return true;
        } else {
            LOGGER.info("No PP left for move: {}", slot.getMove().getMoveName());
            return false;
        }
    }

    public static void restore(MoveSlot slot) {
         // Restore PP to max PP of the move
        slot.setCurrentPP(slot.getMove().getMaxPp());
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
            // Level-up moves are only eligible at or below the Pokémon's level;
            // TM/HM moves (and other non-LEVEL sources) are always eligible.
            if (e.getSource() == Source.LEVEL && p.getLevel() < e.getParameter()) {
                continue;
            }
            // Skip moves the Pokémon already knows
            boolean alreadyKnown = p.getMoveSet().stream()
                    .anyMatch(m -> m.getMove().getMoveName().equals(e.getMove().getMoveName()));
            if (alreadyKnown) {
                continue;
            }
            eligible.add(e);
        }
        return eligible;
    }


}
