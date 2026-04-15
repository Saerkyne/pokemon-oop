package pokemonGame.serviceTests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import pokemonGame.model.Pokemon;
import pokemonGame.species.Abra;
import pokemonGame.model.Move;
import pokemonGame.db.MoveCRUD;
import pokemonGame.moves.Psychic;
import pokemonGame.service.MoveSlotService;

class MoveSlotServiceTest {

    private Pokemon testAbra;
    private Move psychicMove;
    
    @BeforeEach
    void setUp() {
        testAbra = new Abra("TestAbra");
        psychicMove = new Psychic();
    }

    @Test
    void testTeachMove() {
        MoveSlotService moveSlotService = new MoveSlotService(new MoveCRUD());
        moveSlotService.teachMove(testAbra, psychicMove);
    }
}
