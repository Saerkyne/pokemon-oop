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

        
        return null;
    }

      

    public boolean createBattle(int trainer1Id, int trainer2Id, Optional<Integer> trainer1TeamId, Optional<Integer> trainer2TeamId) {
        // Implementation to create a new battle in the database and return the battle ID
        // We need to check for an existing active battle for this trainer matchup

        
        if (battleCrud.getActiveBattleForTrainerMatchup(trainer1Id, trainer2Id) != null) {
            // There is already an active battle for this trainer matchup
            return false;
        }

        // Create a new battle and set its status to "Pending" until both trainers have submitted their teams and are ready to start
        battleCrud.createBattle(trainer1Id, trainer2Id, "PENDING", trainer1TeamId.orElse(-1), trainer2TeamId.orElse(-1));
        return true;

        // Issue a challenge to the opponent trainer (e.g., via Discord DM or in-app notification)

        
    }

    

}
