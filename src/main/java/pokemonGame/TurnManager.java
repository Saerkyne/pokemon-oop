package pokemonGame;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class TurnManager {

    private static final Logger LOGGER = LoggerFactory.getLogger(TurnManager.class);

    /*
     * SUGGESTION: Move this logic out of the constructor and into a method that
     * returns a TurnResult. Constructors should set up the object, not execute
     * the entire business operation. Problems with logic-in-constructor:
     *
     * 1. NOT TESTABLE — you can't call new TurnManager(...) and inspect what
     *    happened. The side effects (damage, faint, PP reduction) fire during
     *    construction, and there's no return value to assert against.
     *
     * 2. NO RETURN VALUE — the plan says resolveTurn() returns a TurnResult
     *    record. A constructor can only return the TurnManager instance itself.
     *
     * 3. NOT REUSABLE — if you ever need to resolve multiple turns (e.g., in
     *    tests), you'd create throwaway TurnManager objects just for the side
     *    effects.
     *
     * Recommended approach — make resolveTurn() a static method:
     *
     *   public static TurnResult resolveTurn(BattleAction action1, BattleAction action2) {
     *       BattleAction firstAction = getFirstAction(action1, action2);
     *       // ... resolve first action, then second ...
     *       return new TurnResult(result1, result2, battleOver, winner);
     *   }
     *
     * BattleService calls:  TurnResult result = TurnManager.resolveTurn(a1, a2);
     *
     * ALSO: This currently only resolves the FIRST action. The plan requires
     * both actions to resolve in one turn — the second action executes after
     * the first, unless the second Pokémon fainted. Add the second action
     * resolution after the first, with a faint check in between.
     */

    /*
     * SUGGESTION: Replace the string-based switch with pattern matching on the
     * sealed BattleAction type. Instead of:
     *
     *   switch (firstAction.getActionType()) {
     *       case "MOVE"   -> resolveMove(firstAction, defender);
     *       case "SWITCH" -> resolveSwitch(firstAction);
     *   }
     *
     * Use (after slimming down BattleAction — see BattleAction.java):
     *
     *   switch (firstAction) {
     *       case MoveAction ma   -> resolveMove(ma, defender);
     *       case SwitchAction sa -> resolveSwitch(sa);
     *   }
     *
     * Benefits:
     *   - Compile-time exhaustiveness: add a new action type and every switch
     *     that doesn't handle it becomes a compile error.
     *   - No string typo risk: "MVOE" compiles fine but never matches.
     *     MoveAction is a type — misspell it and the compiler catches it.
     *   - Direct field access: ma.moveSlotIndex() instead of
     *     firstAction.getMoveSlotIndex() through the interface.
     */
    public TurnManager(Battle battle, BattleAction trainer1Action, BattleAction trainer2Action) {

        BattleAction firstAction = getFirstAction(trainer1Action, trainer2Action, battle);
        Pokemon defender = (firstAction == trainer1Action) ? trainer2Action.getCurrentPokemon() : trainer1Action.getCurrentPokemon();

        
        switch (firstAction.getActionType()) {
            case "MOVE" -> {
                resolveMove(firstAction, defender);
                LOGGER.info("{} will go first with their move!", firstAction.getTrainer().getName());
            }
            case "SWITCH" -> {
                resolveSwitch(firstAction);
                LOGGER.info("{} will go first with their switch!", firstAction.getTrainer().getName());
            }
        }
        

                
    }

  
    // Determines which trainer's action goes first based on the rules of Pokémon battles:
    // 1. If one trainer is switching and the other is using a move, the switching trainer goes first.
    // 2. If both trainers are using moves, the trainer with the faster active Pokémon goes first.
    // 3. If both active Pokémon have the same speed, the turn order is determined randomly.
    public BattleAction getFirstAction(BattleAction trainer1Action, BattleAction trainer2Action, Battle battle) {
        // Check for a switch action - those have ultimate priority
        if (trainer1Action.getActionType().equals("SWITCH") || trainer2Action.getActionType().equals("SWITCH")) {
            // one trainer is switching, so they go first regardless of speed
            if (trainer1Action.getActionType().equals("SWITCH")) {
                return trainer1Action;
            } else {
                return trainer2Action;
            }

        } else {
            // SUGGESTION: battle.checkSpeed() is just a wrapper around pokemon.getCurrentSpeed().
            // Call getCurrentSpeed() directly to remove the unnecessary indirection:
            //   int trainer1Speed = trainer1Action.getCurrentPokemon().getCurrentSpeed();
            //   int trainer2Speed = trainer2Action.getCurrentPokemon().getCurrentSpeed();
            int trainer1Speed = battle.checkSpeed(trainer1Action.getCurrentPokemon());
            int trainer2Speed = battle.checkSpeed(trainer2Action.getCurrentPokemon());
            if (trainer1Speed > trainer2Speed) {
                return trainer1Action;
            } else if (trainer2Speed > trainer1Speed) {
                return trainer2Action;
            } else {
                // Speeds are equal, randomly determine turn order
                if (Math.random() < 0.5) {
                    return trainer1Action;
                } else {
                    return trainer2Action;
                }
            }
        }
    }

    // We know both trainers are using Moves, not switching. Speed has been checked, now we are resolving in the correct order.
    // This will get called twice per round - once for the first action, then again for the second action if the slower Pokémon is still alive after the first action resolves.
    public void resolveMove(BattleAction action, Pokemon defender) {
        // Check current PP for move actions before executing
        int currentPpFirstAction = action.getMoveSlot().getCurrentPP();
        
        // If the move has no PP left, it fails and the Pokémon uses Struggle or skips the turn (depending on your implementation)
        if (currentPpFirstAction <= 0) {
            LOGGER.info("{} tried to use {}, but it has no PP left!", action.getCurrentPokemon().getNickname(), action.getMove().getMoveName());
            // Handle PP failure (e.g., use Struggle, skip turn, etc.)
            return;
        }

        // Execute the action (e.g., deal damage, switch Pokémon, etc.)
        // Reduce PP by 1 before executing the move to reflect the cost of using it, even if the move fails or the Pokémon faints as a result.
        action.getMoveSlot().use();
        if (dealDamage(action.getCurrentPokemon(), defender, action.getMove())) {
            // Handle the case where the defender has fainted

        }
    }

    // We know this action is a switch, so we just need to swap the active Pokémon for the trainer performing the switch action.
    public void resolveSwitch(BattleAction action) {
        Trainer trainer = action.getTrainer();
        int switchPokemonId = action.getSwitchPokemonId();
        Pokemon newPokemon = trainer.getTeam().get(switchPokemonId);
        trainer.setActivePokemon(newPokemon);
        LOGGER.info("{} switched to {}!", trainer.getName(), newPokemon.getNickname());
    }

    // Deal Damage
    public static boolean dealDamage(Pokemon attacker, Pokemon defender, Move move) {
        // Calculate damage based on move power, attacker's stats, defender's stats, type effectiveness, etc.
        // This is a placeholder for the actual damage calculation logic.
        
        int damage = Attack.calculateDamage(attacker, defender, move);

        // Check for damage amount, clamp to 0 if it would go negative
        // We clamp at the setter too, to also set fained status if damage exceeds current HP,
        // but this is a safeguard against any future changes to the setter logic.
        if (damage > defender.getCurrentHP()) {
            damage = defender.getCurrentHP();
        }
        
        // Apply damage to the defender
        defender.setCurrentHP(defender.getCurrentHP() - damage);

        // Print out the result of the attack
        LOGGER.info("{} used {}!", attacker.getNickname(), move.getMoveName());

        if (!Battle.checkFainted(defender)) {
            LOGGER.info("{} took {} damage and has {} HP left.", defender.getNickname(), damage, defender.getCurrentHP());
            return false; // Defender is still alive
        } else {
            LOGGER.info("{} took {} damage and has fainted!", defender.getNickname(), damage);
            return true; // Defender has fainted
        }

        
    }
}
