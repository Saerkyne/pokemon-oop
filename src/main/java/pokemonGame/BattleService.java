package pokemonGame;

import pokemonGame.db.BattleCRUD;

public class BattleService {

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

    public static boolean checkFainted(Pokemon pokemon) {
        return pokemon.getCurrentHP() <= 0;
    }  

    public static boolean createBattle(int trainer1Id, int trainer2Id) {
        // Implementation to create a new battle in the database and return the battle ID
        // We need to check for an existing active battle for this trainer matchup

        
        BattleCRUD battleCrud = new BattleCRUD();
        if (battleCrud.getActiveBattleForTrainerMatchup(trainer1Id, trainer2Id) != null) {
            // There is already an active battle for this trainer matchup
            return false;
        }

        // Create a new battle and set its status to "Pending" until both trainers have submitted their teams and are ready to start
        battleCrud.createBattle(trainer1Id, trainer2Id, "PENDING");
        return true;

        // Issue a challenge to the opponent trainer (e.g., via Discord DM or in-app notification)

        
    }

}
