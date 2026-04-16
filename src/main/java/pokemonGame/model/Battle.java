package pokemonGame.model;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pokemonGame.battle.TurnManager;
// TODO: MDL-7 — Remove this service-layer import. Model should not depend on service layer. Used only in @see Javadoc.
import pokemonGame.service.BattleService;

import java.time.LocalDateTime;
import java.util.List;

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

/**
 * Persistence-friendly state container for a battle between two trainers.
 * Stores database IDs for participants, active Pokémon, team references,
 * status, and turn count. Maps directly to the {@code battles} table.
 *
 * <p>This class holds IDs, not live domain objects. {@link BattleService}
 * hydrates the corresponding {@link Trainer} and {@link Pokemon} objects
 * before passing them to {@link TurnManager} for turn resolution.</p>
 *
 * @see BattleService
 * @see TurnManager
 */
public class Battle {
    private static final Logger LOGGER = LoggerFactory.getLogger(Battle.class);

    private int battleId;
    private int trainer1Id;
    private int trainer2Id;
    private int trainer1ActivePokemonId;
    private int trainer2ActivePokemonId;
    private int team1Id;
    private int team2Id;
    private LocalDateTime startTime;
    private LocalDateTime updateTime;
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

    public void setTeam1Id(int team1Id) {
        this.team1Id = team1Id;
    }

    public void setTeam2Id(int team2Id) {
        this.team2Id = team2Id;
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

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public void setUpdateTime(LocalDateTime updateTime) {
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

    public int getTeam1Id() {
        return team1Id;
    }

    public int getTeam2Id() {
        return team2Id;
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

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public LocalDateTime getUpdateTime() {
        return updateTime;
    }

    public int getWinningTrainerId() {
        return winningTrainerId;
    }

    // TODO: MDL-8 — trainer.getTeam() can return null → NPE. Add null check before chaining.
    public List<Pokemon> getAllRemainingPokemon(Trainer trainer, Team team) {
        LOGGER.info("Getting all remaining Pokemon for trainer: {}", trainer.getTrainerName());
        return trainer.getTeam(team.getTeamName()).getTeamAsList().stream()
            .filter(p -> !p.getIsFainted())
            .toList();
    }


}
