package pokemonGame;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.sql.Timestamp;

/*
 * NOTE ON BATTLE'S ROLE: This class stores IDs (trainer1Id, trainer1ActivePokemonId, etc.)
 * which map directly to database columns — good for persistence. But TurnManager needs
 * live Trainer, Pokemon, and Move objects to call methods like getCurrentSpeed(),
 * setCurrentHP(), etc.
 *
 * BattleService bridges the gap: it loads this Battle from BattleCRUD (gets the IDs),
 * then hydrates live objects via TrainerCRUD.getTrainerByDbId() and PokemonCRUD, and
 * passes both the Battle and the live objects to TurnManager. Battle is the
 * persistence-friendly state container; TurnManager works with the live domain objects.
 */
public class Battle {
    private static final Logger LOGGER = LoggerFactory.getLogger(Battle.class);

    private int battleId;
    private int trainer1Id;
    private int trainer2Id;
    private int trainer1ActivePokemonId;
    private int trainer2ActivePokemonId;
    private Timestamp startTime;
    private Timestamp updateTime;
    private int winningTrainerId;

    public static enum Status {
        PENDING, TEAM_SETUP, ACTIVE, FINISHED
    }

    private Status status;

    public Battle() {
        // Constructor for Battle class, if needed
        
    }

    // Setters 
    public void setBattleId(int battleId) {
        this.battleId = battleId;
    }

    public void setTrainer1Id(int trainer1Id) {
        this.trainer1Id = trainer1Id;
    }

    public void setTrainer2Id(int trainer2Id) {
        this.trainer2Id = trainer2Id;
    }

    public void setTrainer1ActivePokemonId(int trainer1ActivePokemonId) {
        this.trainer1ActivePokemonId = trainer1ActivePokemonId;
    }

    public void setTrainer2ActivePokemonId(int trainer2ActivePokemonId) {
        this.trainer2ActivePokemonId = trainer2ActivePokemonId;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public void setStartTime(Timestamp startTime) {
        this.startTime = startTime;
    }

    public void setUpdateTime(Timestamp updateTime) {
        this.updateTime = updateTime;
    }

    public void setWinningTrainerId(int winningTrainerId) {
        this.winningTrainerId = winningTrainerId;
    }

    // Getters
    public int getBattleId() {
        return battleId;
    }

    public int getTrainer1Id() {
        return trainer1Id;
    }

    public int getTrainer2Id() {
        return trainer2Id;
    }

    public int getTrainer1ActivePokemonId() {
        return trainer1ActivePokemonId;
    }

    public int getTrainer2ActivePokemonId() {
        return trainer2ActivePokemonId;
    }

    public Status getStatus() {
        return status;
    }

    public Timestamp getStartTime() {
        return startTime;
    }

    public Timestamp getUpdateTime() {
        return updateTime;
    }

    public int getWinningTrainerId() {
        return winningTrainerId;
    }

    /*
     * SUGGESTION: Two small improvements here.
     *
     * 1. Typo: "Pokmeon" → "Pokemon" in the method name.
     *
     * 2. This returns a Pokemon[] with nulls in fainted slots (e.g., [Bulbasaur, null, Pikachu]).
     *    Callers then have to check for null at every index. Returning a List<Pokemon>
     *    containing only the alive ones is easier to work with:
     *
     *      public List<Pokemon> getAllRemainingPokemon(Trainer trainer) {
     *          return trainer.getTeam().stream()
     *              .filter(p -> !p.getIsFainted())
     *              .toList();   // Java 16+ Stream.toList()
     *      }
     *
     *    The caller can check .isEmpty() to see if the trainer has lost, and
     *    iterate without null checks. The logging can stay if you want it.
     *
     * 3. This method takes a Trainer but Battle stores trainer IDs, not Trainer
     *    objects. That's fine — BattleService will hydrate the Trainer from the
     *    DB and pass it in. Just be aware that this method won't be callable
     *    from Battle alone; it needs the caller to provide the live Trainer.
     */
    public Pokemon[] getAllRemainingPokemon(Trainer trainer) {
        Pokemon[] remainingPokemon = new Pokemon[trainer.getTeam().size()];
        LOGGER.info("Getting all remaining Pokémon for trainer {}...", trainer.getName());
        trainer.getTeam().forEach(pokemon -> {
            if (!pokemon.getIsFainted()) {
                LOGGER.info("{} ({}) is still able to battle with {} HP left.", pokemon.getNickname(), pokemon.getSpecies(), pokemon.getCurrentHP());
                remainingPokemon[trainer.getTeam().indexOf(pokemon)] = pokemon;
            } else {
                LOGGER.info("{} ({}) has fainted and cannot battle.", pokemon.getNickname(), pokemon.getSpecies());
            }
        });
        return remainingPokemon;
    }

    // Calculations
    
    /*
     * SUGGESTION: This method is a pass-through — it takes a Pokemon and returns
     * pokemon.getCurrentSpeed(). The caller (TurnManager.getFirstAction) could
     * just call pokemon.getCurrentSpeed() directly.
     *
     * The old checkSpeed() compared two Pokémon and returned the faster one.
     * This new version only returns one Pokémon's speed, so the comparison
     * happens in TurnManager instead — which is fine, but it makes this wrapper
     * unnecessary. Consider removing it and calling getCurrentSpeed() directly
     * in TurnManager:
     *
     *   int trainer1Speed = trainer1Action.getCurrentPokemon().getCurrentSpeed();
     *   int trainer2Speed = trainer2Action.getCurrentPokemon().getCurrentSpeed();
     *
     * Keeping Battle focused on state (IDs, status, timestamps) and letting
     * TurnManager own all turn-resolution logic is cleaner separation.
     */
    public int checkSpeed(Pokemon pokemon1) {
        int pokemon1Speed = pokemon1.getCurrentSpeed();
        return pokemon1Speed;
    }

    public static boolean checkFainted(Pokemon pokemon) {
        if (pokemon.getCurrentHP() <= 0) {
            pokemon.setIsFainted(true);
            return pokemon.getIsFainted();
        }
        return pokemon.getIsFainted();
    }


}
