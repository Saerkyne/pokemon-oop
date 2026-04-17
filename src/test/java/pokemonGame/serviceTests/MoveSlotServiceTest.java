package pokemonGame.serviceTests;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.List;

import pokemonGame.model.Pokemon;
import pokemonGame.species.Abra;
import pokemonGame.model.Move;
import pokemonGame.db.MoveCRUD;
import pokemonGame.db.PokemonCRUD;
import pokemonGame.db.TeamCRUD;
import pokemonGame.db.TrainerCRUD;
import pokemonGame.moves.Psychic;
import pokemonGame.service.MoveSlotService;
import pokemonGame.service.TeamService;
import pokemonGame.service.TrainerService;
import pokemonGame.model.Trainer;
import pokemonGame.model.Team;
import pokemonGame.db.DatabaseSetup;

class MoveSlotServiceTest {

    private Pokemon testAbra;
    private MoveCRUD moveCRUD = new MoveCRUD();
    private TrainerCRUD trainerCRUD = new TrainerCRUD();
    private TeamCRUD teamCRUD = new TeamCRUD();
    private MoveSlotService moveSlotService = new MoveSlotService(moveCRUD);
    private PokemonCRUD pokemonCRUD = new PokemonCRUD(moveSlotService);
    private TrainerService trainerService = new TrainerService(trainerCRUD);
    private TeamService teamService = new TeamService(teamCRUD, pokemonCRUD, trainerService);
    
    @BeforeEach
    void setUp() {
        // Create a test Trainer
        Trainer testTrainer = trainerService.createTrainer("Test Trainer", 123456789L, "TestTrainer#1234");
        int trainerDbId = testTrainer.getTrainerDbId();
        // Create a test Team for the Trainer
        Team testTeam = teamService.createTeam(trainerDbId, "Test Team");
        // Create a test Pokemon (Abra) and add it to the Team
        Pokemon testAbra = new Abra("Test Abra");
        testAbra.setTrainer(testTrainer);
        
        teamService.addPokemonToTeam(testTeam, testAbra);
        this.testAbra = testAbra;
    }
    @AfterEach
    void tearDown() {
        // Clean up the database after each test
        DatabaseSetup.deleteAllData();
    }

    @Test
    void testTeachMove() {
        Move psychicMove = new Psychic();
        moveSlotService.teachMove(testAbra, psychicMove);
        List<String[]> currentMoves = moveSlotService.getCurrentDbMoves(testAbra.getPokemonDbId());
        // Verify that the move was taught successfully
        boolean moveFound = currentMoves.stream().anyMatch(move -> move[0].equals(psychicMove.getMoveName()));
        assertTrue(moveFound);
    }
}
