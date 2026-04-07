package pokemonGame.dbTests;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pokemonGame.db.BattleCRUD;
import pokemonGame.db.DatabaseSetup;
import pokemonGame.service.TeamService;
import pokemonGame.service.TrainerService;
import pokemonGame.model.Trainer;
import pokemonGame.model.Team;
import pokemonGame.model.Pokemon;
import pokemonGame.model.Battle;
import pokemonGame.species.Abra;

class BattleCRUDTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(BattleCRUDTest.class);
    private BattleCRUD battleCRUD = new BattleCRUD();

    @BeforeEach
    void setUp() {
        // Clear all battle-related tables before each test to ensure a clean slate.
        // This is necessary because battle tests often rely on specific database states
        // and we want to avoid interference from leftover data from previous tests.
        DatabaseSetup.deleteAllData();
    }

    private Object[] buildScenario() {
        TrainerService trainerService = new TrainerService();
        Trainer trainerOne = trainerService.createTrainer("Ash", 1, "Ash");
        Trainer trainerTwo = trainerService.createTrainer("Misty", 2, "Misty");
        int trainerOneId = trainerOne.getTrainerDbId();
        int trainerTwoId = trainerTwo.getTrainerDbId();

        Pokemon pokemonOne = new Abra("Ash's Abra");
        pokemonOne.setTrainer(trainerOne);
        Pokemon pokemonTwo = new Abra("Misty's Abra");
        pokemonTwo.setTrainer(trainerTwo);

        Team teamOne = new Team("Ash's Team");
        Team teamTwo = new Team("Misty's Team");
        TeamService teamService = new TeamService();
        teamService.addPokemonToTeam(teamOne, pokemonOne);
        teamService.addPokemonToTeam(teamTwo, pokemonTwo);

        teamService.createTeam(trainerOneId, "Ash's Team");
        teamService.createTeam(trainerTwoId, "Misty's Team");
        int teamOneId = teamOne.getTeamDbId();
        int teamTwoId = teamTwo.getTeamDbId();
        return new Object[]{trainerOneId, trainerTwoId, teamOneId, teamTwoId};
    }

    @Test
    void testCreateAndRetrieveBattle() {
        // This test will create a battle record in the database and then attempt to retrieve it.
        // We will verify that the retrieved battle matches the one we created.
        Object[] scenario = buildScenario();
        int trainerOneId = (int) scenario[0];
        int trainerTwoId = (int) scenario[1];
        int teamOneId = (int) scenario[2];
        int teamTwoId = (int) scenario[3];


        // Create a new battle record
        int battleId = battleCRUD.createBattle(trainerOneId, trainerTwoId, "ACTIVE", teamOneId, teamTwoId);

        // Retrieve the battle record
        Battle retrievedBattle = battleCRUD.getBattleById(battleId);

        // Verify that the retrieved battle matches the created one
        assertNotNull(retrievedBattle);
        assertEquals(battleId, retrievedBattle.getBattleId());
        assertEquals(trainerOneId, retrievedBattle.getTrainer1Id());
        assertEquals(trainerTwoId, retrievedBattle.getTrainer2Id());
        assertEquals(teamOneId, retrievedBattle.getTeam1Id());
        assertEquals(teamTwoId, retrievedBattle.getTeam2Id());
    }

}
