package pokemonGame.service;

import pokemonGame.battle.BattleAction;
import pokemonGame.battle.TurnManager;
import pokemonGame.db.BattleCRUD;
import pokemonGame.model.Battle;

import java.util.Optional;

/**
 * Orchestrates battle lifecycle: creating challenges, validating participants,
 * buffering asynchronous move submissions, delegating turn resolution to
 * {@link TurnManager}, and persisting state changes via the DAO layer.
 *
 * @see Battle
 * @see TurnManager
 */
public class BattleService {

    private final BattleCRUD battleCrud;

    public BattleService(BattleCRUD battleCrud) {
        this.battleCrud = battleCrud;
    }

    // TODO(review 2026-04-20): Replace null stub with a small raw-action-row -> BattleAction mapper.
    // This method needs a clear contract for whether the switch column stores slot index or Pokemon instance ID before battle resume can work reliably.
    public static BattleAction createActionFromDbRow(int trainerId, String actionType, int moveSlotIndex, int switchPokemonId) {
        // This method would query the database to get the necessary Trainer and Pokemon objects
        // based on the battleId and trainerId, then construct either a MoveAction or SwitchAction
        // depending on the actionType. The moveOrSwitchIndex would be used to determine which move
        // slot or team slot is being referenced.
        //
        // For example:
        // Trainer trainer = TrainerDAO.getTrainerById(trainerId);
        // Pokemon activePokemon = trainer.getActivePokemon();
        //
        // if (actionType.equals("MOVE")) {
        //     return new MoveAction(trainer, activePokemon, moveOrSwitchIndex);
        // } else if (actionType.equals("SWITCH")) {
        //     return new SwitchAction(trainer, activePokemon, moveOrSwitchIndex);
        // }
        //
        // This is just a placeholder to illustrate the concept.


        // TODO [🔴 BLOCKING | review 2026-04-20]: Stub returns null. Why: any caller depending on this method will silently receive null and NPE later, far from the cause. Fix: until implemented, `throw new UnsupportedOperationException("createActionFromDbRow not yet implemented");` so misuse is loud.
        return null;
    }

      

    public boolean createBattle(int trainer1Id, int trainer2Id, Optional<Integer> trainer1TeamId, Optional<Integer> trainer2TeamId) {
        // Implementation to create a new battle in the database and return the battle ID
        // We need to check for an existing active battle for this trainer matchup

        
        if (battleCrud.getActiveBattleForTrainerMatchup(trainer1Id, trainer2Id) != null) {
            // There is already an active battle for this trainer matchup
            return false;
        }

        // TODO [🔴 BLOCKING | review 2026-04-20]: Ignoring battleCrud.createBattle return value. Why: DAO failure returns without signal — method returns true even when insert failed. Caller has no battleId either. Fix: capture the returned ID, verify > 0, return int (battleId) or Optional<Integer>; propagate failure.
        // TODO [🟡 IMPORTANT | review 2026-04-20]: Duplicate check tests only (t1, t2) — matchup (t2, t1) is not prevented. Fix: also check `getActiveBattleForTrainerMatchup(trainer2Id, trainer1Id)` OR have DAO normalize (min,max) ordering.
        // TODO [🟢 SUGGESTION | review 2026-04-20]: Magic string "PENDING". Fix: use `Battle.Status.PENDING.name()` from the existing enum.
        // Create a new battle and set its status to "Pending" until both trainers have submitted their teams and are ready to start
        battleCrud.createBattle(trainer1Id, trainer2Id, "PENDING", trainer1TeamId.orElse(-1), trainer2TeamId.orElse(-1));
        return true;

        // Issue a challenge to the opponent trainer (e.g., via Discord DM or in-app notification)

        
    }

    

}
