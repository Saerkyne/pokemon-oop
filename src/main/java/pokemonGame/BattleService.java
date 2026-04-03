package pokemonGame;

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

}
