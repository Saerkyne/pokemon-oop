package pokemonGame.service;

import pokemonGame.battle.BattleAction;
import pokemonGame.battle.TurnManager;
import pokemonGame.db.BattleCRUD;
import pokemonGame.model.Battle;


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

    public int createBattle(int trainer1Id, int trainer2Id, int trainer1TeamId) {

        // Check for existing active or pending battle between these two trainers to prevent duplicates
        int pendingBattleId = battleCrud.getPendingBattleForTrainerMatchup(trainer1Id, trainer2Id);
        int activeBattleId = battleCrud.getActiveBattleForTrainerMatchup(trainer1Id, trainer2Id);
        if (pendingBattleId != -1 || activeBattleId != -1) {
            return -1;
        }

        // Create a new battle with status "Pending" until the opponent accepts and submits their team
        int createdBattleId = battleCrud.createBattle(trainer1Id, trainer2Id, Battle.Status.PENDING.name(), trainer1TeamId, -1);
        return createdBattleId;
    }

    // Overloaded method to handle challenge acceptance with both teams ready
    public int createBattle(int trainer1Id, int trainer2Id, int trainer1TeamId, int trainer2TeamId) {

        // Check for existing active battle for this matchup to prevent duplicates
        int activeBattleId = battleCrud.getActiveBattleForTrainerMatchup(trainer1Id, trainer2Id);
        if (activeBattleId != -1) {
            // There is already an active battle for this trainer matchup
            return -1;
        }

        // Create a new battle with status "Active" since both teams are ready
        int createdBattleId = battleCrud.createBattle(trainer1Id, trainer2Id, Battle.Status.ACTIVE.name(), trainer1TeamId, trainer2TeamId);
        

        // Issue a challenge to the opponent trainer (e.g., via Discord DM or in-app notification)

        return createdBattleId; // Return the battle ID if a valid battle was created, or -1 if creation failed
    }

    

}
