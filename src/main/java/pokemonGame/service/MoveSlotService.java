package pokemonGame.service;

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
import pokemonGame.db.MoveCRUD;

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

    

    public static void teachMove(Pokemon p, Move move) {

        if (!getEligibleMoves(p).stream().anyMatch(e -> e.getMove().getMoveName().equalsIgnoreCase(move.getMoveName()))) {
            return; // Move is not eligible to be learned, so we exit without making changes
        }

        MoveCRUD moveCRUD = new MoveCRUD();

        try{
            if (moveCRUD.insertMoveForPokemon(p.getPokemonDbId(), p.getMoveSet().size(), move.getMoveName(), move.getMaxPp()) == -1) {
                LOGGER.error("Failed to insert move {} into DB for Pokémon {}.", move.getMoveName(), p.getNickname());
                return; // Exit without modifying the in-memory moveset if DB insert fails
            } else {
            LOGGER.info("Move {} successfully inserted into DB for Pokémon {}.", move.getMoveName(), p.getNickname());
            p.addMove(move);
            }
        } catch (Exception e) {
            LOGGER.error("Failed to insert move {} into DB for Pokémon {}: {}", move.getMoveName(), p.getNickname(), e.getMessage());
            throw new RuntimeException("Failed to teach move due to database error.");
        }
        
    }

    public static Move getMoveByName(String moveName) {
        return PokeMove.fromString(moveName).createMove();
    }
    
    public static boolean use(Pokemon pokemon, MoveSlot slot) {
        int currentPP = slot.getCurrentPP();
        if (currentPP <= 0) {
            LOGGER.info("No PP left for move: {}", slot.getMove().getMoveName());
            return false;
        }

        int slotIndex = pokemon.getMoveSet().indexOf(slot);
        if (slotIndex == -1) {
            LOGGER.error("MoveSlot for {} not found in {}'s moveset.", slot.getMove().getMoveName(), pokemon.getNickname());
            return false;
        }

        int newPP = currentPP - 1;
        MoveCRUD moveCRUD = new MoveCRUD();
        if (moveCRUD.updatePP(pokemon.getPokemonDbId(), slotIndex, newPP) == -1) {
            LOGGER.error("Failed to persist PP update for {} on {}.", slot.getMove().getMoveName(), pokemon.getNickname());
            return false;
        }
        slot.setCurrentPP(newPP);
        return true;
    }

    public static void restore(Pokemon pokemon, MoveSlot slot) {
        int slotIndex = pokemon.getMoveSet().indexOf(slot);
        if (slotIndex == -1) {
            LOGGER.error("MoveSlot for {} not found in {}'s moveset.", slot.getMove().getMoveName(), pokemon.getNickname());
            return;
        }

        int maxPP = slot.getMove().getMaxPp();
        MoveCRUD moveCRUD = new MoveCRUD();
        if (moveCRUD.updatePP(pokemon.getPokemonDbId(), slotIndex, maxPP) == -1) {
            LOGGER.error("Failed to persist PP restore for {} on {}.", slot.getMove().getMoveName(), pokemon.getNickname());
            return;
        }
        slot.setCurrentPP(maxPP);
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
