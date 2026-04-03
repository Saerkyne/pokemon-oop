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

    public TurnManager() {
      
    }

    public static TurnResult resolveTurn(BattleAction trainer1Action, BattleAction trainer2Action, Battle battle) {
        // Determine turn order
        BattleAction firstAction = getFirstAction(trainer1Action, trainer2Action, battle);
        BattleAction secondAction = (firstAction == trainer1Action) ? trainer2Action : trainer1Action;

        // Resolve first action
        Pokemon defender = (firstAction == trainer1Action) ? secondAction.activePokemon() : firstAction.activePokemon();
        DamageResult action1Result = null;
        if (firstAction instanceof MoveAction ma) {
            action1Result = resolveMove(ma, defender);
        } else if (firstAction instanceof SwitchAction sa) {
            resolveSwitch(sa);
        }

        // Check if defender fainted after first action
        boolean defenderFainted = BattleService.checkFainted(defender);
        boolean battleOver = false;
        Trainer winner = null;
        if (defenderFainted) {
            // Check if the trainer with the fainted Pokémon has any remaining Pokémon to switch in
            Trainer defendingTrainer = (firstAction == trainer1Action) ? secondAction.trainer() : firstAction.trainer();
            if (!defendingTrainer.getTeam().isEmpty()) {
                // If they have Pokémon left, the battle continues and they will switch in a new Pokémon on their next turn
                LOGGER.info("{} has fainted! {} has {} Pokémon left to switch in.", defender.getNickname(), defendingTrainer.getName(), defendingTrainer.getTeam().size());
            } else {
                // If they have no Pokémon left, the battle is over and the other trainer wins
                battleOver = true;
                winner = (firstAction == trainer1Action) ? firstAction.trainer() : secondAction.trainer();
                LOGGER.info("{} has fainted and {} has no Pokémon left to switch in! {} wins the battle!", defender.getNickname(), defendingTrainer.getName(), winner.getName());
            }
        }
        // Resolve second action only if defender is still alive
        DamageResult action2Result = null;
        if (!defenderFainted) {
            if (secondAction instanceof MoveAction ma) {
                action2Result = resolveMove(ma, secondAction.activePokemon());
            } else if (secondAction instanceof SwitchAction sa) {
                resolveSwitch(sa);
            }
        }

        // Construct and return TurnResult based on the outcomes of both actions
        return new TurnResult(action1Result, action2Result, battleOver, winner);
    }

  
    // Determines which trainer's action goes first based on the rules of Pokémon battles:
    // 1. If one trainer is switching and the other is using a move, the switching trainer goes first.
    // 2. If both trainers are using moves, the trainer with the faster active Pokémon goes first.
    // 3. If both active Pokémon have the same speed, the turn order is determined randomly.
    //
    // DESIGN NOTE: Keeping all priority logic in one method is the right call here.
    // Splitting into checkForSwitch() + a speed comparison elsewhere would mean:
    //   - checkForSwitch() returns a boolean, but you still need to figure out WHICH
    //     action is first, so you'd need a second method for that anyway.
    //   - Putting speed comparison inside resolveMove() mixes concerns — resolveMove()
    //     handles one action's execution (PP, accuracy, damage). Deciding turn order
    //     is a separate responsibility.
    //
    // This method reads as a clean priority chain:
    //   switches go first → faster speed goes first → coin flip on ties
    // One method, one decision, one BattleAction returned. Each rule is one if-block.
    //
    // If priority brackets are added later (Quick Attack has +1, Protect has +4, etc.),
    // they'd slot in naturally between the switch check and the speed check:
    //   switches → priority bracket comparison → speed comparison → coin flip
    public static BattleAction getFirstAction(BattleAction trainer1Action, BattleAction trainer2Action, Battle battle) {
        // Check for a switch action - those have ultimate priority
        if (trainer1Action instanceof SwitchAction || trainer2Action instanceof SwitchAction) {
            // one trainer is switching, so they go first regardless of speed
            if (trainer1Action instanceof SwitchAction) {
                return trainer1Action;
            } else {
                return trainer2Action;
            }
            // Priority comparisons would go here in an else-if block if we add priority brackets later

        } else {
            int trainer1Speed = trainer1Action.activePokemon().getCurrentSpeed();
            int trainer2Speed = trainer2Action.activePokemon().getCurrentSpeed();
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
    public static DamageResult resolveMove(MoveAction action, Pokemon defender) {
        // Check current PP for move actions before executing
        int currentPpFirstAction = action.getMoveSlot().getCurrentPP();
        
        // If the move has no PP left, it fails and the Pokémon uses Struggle or skips the turn (depending on your implementation)
        if (currentPpFirstAction <= 0) {
            LOGGER.info("{} tried to use {}, but it has no PP left!", action.activePokemon().getNickname(), action.getMove().getMoveName());
            // Handle PP failure (e.g., use Struggle, skip turn, etc.)
            return new DamageResult(0, 0, false, false, false);
        }

        // Execute the action (e.g., deal damage, switch Pokémon, etc.)
        // Reduce PP by 1 before executing the move to reflect the cost of using it, even if the move fails or the Pokémon faints as a result.
        action.getMoveSlot().use();
        int damageDealt = dealDamage(action.activePokemon(), defender, action.getMove());
        float effectiveness = Attack.calculateEffectiveness(defender.getTypePrimary(), action.getMove());
        boolean isCritical = Attack.calculateCriticalHit(action.activePokemon(), defender);
        boolean isHit = Attack.checkAccuracy(action.activePokemon(), defender, action.getMove());
        if (damageDealt > 0) {
            // Handle fainting and other side effects of the move here (e.g., status conditions, recoil damage, etc.)
            if (!BattleService.checkFainted(defender)) {
                LOGGER.info("{} took {} damage and has {} HP left.", defender.getNickname(), damageDealt, defender.getCurrentHP());
                
            } else {
                LOGGER.info("{} took {} damage and has fainted!", defender.getNickname(), damageDealt);
                
            }

            // return the damage result, including whether the move hit, was super effective, and if it was a critical hit
            return new DamageResult(damageDealt, effectiveness, isCritical, isHit, BattleService.checkFainted(defender));

            
        }
        return new DamageResult(0, 0, false, true, false); // Placeholder, replace with actual damage calculation
    }

    // We know this action is a switch, so we just need to swap the active Pokémon for the trainer performing the switch action.
    public static void resolveSwitch(SwitchAction action) {
        Trainer trainer = action.getTrainer();
        Pokemon newPokemon = action.getSwitchPokemon();
        trainer.setActivePokemon(newPokemon);
        LOGGER.info("{} switched to {}!", trainer.getName(), newPokemon.getNickname());
    }

    // Deal Damage
    public static int dealDamage(Pokemon attacker, Pokemon defender, Move move) {
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

        return damage;
        
    }
}
