package pokemonGame.modelTests;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;
import pokemonGame.model.Battle;
import pokemonGame.model.Trainer;
import pokemonGame.model.Pokemon;
import pokemonGame.model.Team;
import pokemonGame.species.Pikachu;
import pokemonGame.species.Bulbasaur;
import pokemonGame.species.Staryu;
import java.util.List;

/**
 * Unit tests for the {@link Battle} class.
 * 
 * This tests should cover:
 * - Battle initialization with two trainers and their teams.
 * - Turn order determination based on Pokémon speed.
 * - Damage calculation and HP updates after attacks.
 * - Handling of Pokémon fainting and victory conditions.
 */



class BattleTest {

    /*
     * CHECKS: 
     */
    @Test
    void testBattleInitialization() {
        Battle battle = new Battle();
        battle.setBattleId(123);
    }

    @Test
    void testSetGetTrainerIds() {
        Battle battle = new Battle();
        battle.setTrainer1Id(1);
        battle.setTrainer2Id(2);
        assertTrue(battle.getTrainer1Id() == 1);
        assertTrue(battle.getTrainer2Id() == 2);
    }

    @Test
    void testSetGetTeamIds() {
        Battle battle = new Battle();
        battle.setTeam1Id(10);
        battle.setTeam2Id(20);
        assertTrue(battle.getTeam1Id() == 10);
        assertTrue(battle.getTeam2Id() == 20);
    }

    @Test
    void testSetGetActivePokemonIds() {
        Battle battle = new Battle();
        battle.setTrainer1ActivePokemonId(100);
        battle.setTrainer2ActivePokemonId(200);
        assertTrue(battle.getTrainer1ActivePokemonId() == 100);
        assertTrue(battle.getTrainer2ActivePokemonId() == 200);
    }

    @Test
    void testSetGetStatus() {
        Battle battle = new Battle();
        battle.setStatus(Battle.Status.PENDING);
        assertTrue(battle.getStatus() == Battle.Status.PENDING);
    }

    @Test
    void testSetGetStartTime() {
        Battle battle = new Battle();
        LocalDateTime now = LocalDateTime.now();
        battle.setStartTime(now);
        assertTrue(battle.getStartTime().equals(now));
    }

    @Test
    void testSetGetUpdateTime() {
        Battle battle = new Battle();
        LocalDateTime now = LocalDateTime.now();
        battle.setUpdateTime(now);
        assertTrue(battle.getUpdateTime().equals(now));
    }

    @Test
    void testSetGetWinningTrainerId() {
        Battle battle = new Battle();
        battle.setWinningTrainerId(1);
        assertTrue(battle.getWinningTrainerId() == 1);
    }

    @Test
    void testGetAllRemainingPokemon() {
            Battle battle = new Battle();
            // Setup trainers, teams, and Pokémon
            Trainer trainer1 = new Trainer("Ash");
            Trainer trainer2 = new Trainer("Misty");
            
            Team team1 = new Team("Team Ash");
            Team team2 = new Team("Team Misty");
            
            Pokemon pikachu = new Pikachu("Pikachu");
            Pokemon bulbasaur = new Bulbasaur("Bulbasaur");
            Pokemon staryu = new Staryu("Staryu");
            
            team1.add(pikachu);
            team1.add(bulbasaur);
            team2.add(staryu);
            
            trainer1.setTeam(team1);
            trainer2.setTeam(team2);
            
            battle.setTrainer1Id(1); // Assume trainer IDs are set correctly
            battle.setTrainer2Id(2);
            battle.setTeam1Id(10); // Assume team IDs are set correctly
            battle.setTeam2Id(20);


            // Get all remaining Pokémon in the battle
            List<Pokemon> remainingTrainer1Pokemon = battle.getAllRemainingPokemon(trainer1, team1);
            List<Pokemon> remainingTrainer2Pokemon = battle.getAllRemainingPokemon(trainer2, team2);
            
            // Assertions to check if the correct Pokémon are returned
            assertTrue(remainingTrainer1Pokemon.contains(pikachu));
            assertTrue(remainingTrainer1Pokemon.contains(bulbasaur));
            assertTrue(remainingTrainer2Pokemon.contains(staryu));
    }
}
