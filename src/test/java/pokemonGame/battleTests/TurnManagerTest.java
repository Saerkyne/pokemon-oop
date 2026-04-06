package pokemonGame.battleTests;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import pokemonGame.battle.*;
import pokemonGame.model.Trainer;
import pokemonGame.model.Pokemon;
import pokemonGame.model.Team;
import pokemonGame.species.Abra;
import pokemonGame.moves.Tackle;
import pokemonGame.model.Move;
import pokemonGame.model.Battle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class TurnManagerTest {

    public static final Logger LOGGER = LoggerFactory.getLogger(TurnManagerTest.class);

    @Test
    void resolveTurnReturnsTurnResult() {
        Trainer trainer1 = new Trainer("Ash");
        Trainer trainer2 = new Trainer("Gary");
        Pokemon abra1 = new Abra("Abra1");
        Pokemon abra2 = new Abra("Abra2");
        abra1.setLevel(50);
        abra2.setLevel(50);
        Move tackle = new Tackle();
        abra1.addMove(tackle);
        abra2.addMove(tackle);
        trainer1.createTeam("Team1");
        trainer2.createTeam("Team2");
        Team team1 = trainer1.getTeam("Team1");
        Team team2 = trainer2.getTeam("Team2");
        team1.setTeamSlotOne(abra1);
        team2.setTeamSlotOne(abra2);

        MoveAction action1 = new MoveAction(trainer1, abra1, team1, 0);
        MoveAction action2 = new MoveAction(trainer2, abra2, team2, 0);

        Battle battle = new Battle();

        TurnResult result = TurnManager.resolveTurn(action1, action2, battle);
        assertNotNull(result);
        assertTrue(result instanceof TurnResult);
    }



}
