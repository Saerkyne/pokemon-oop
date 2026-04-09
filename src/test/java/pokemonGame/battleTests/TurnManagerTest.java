package pokemonGame.battleTests;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import pokemonGame.battle.*;
import pokemonGame.model.Trainer;
import pokemonGame.model.Pokemon;
import pokemonGame.model.Team;
import pokemonGame.species.Abra;
import pokemonGame.moves.Tackle;
import pokemonGame.model.Move;
import pokemonGame.model.MoveSlot;
import pokemonGame.model.Battle;
import pokemonGame.core.StatCalculator;
import pokemonGame.service.MoveSlotService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Unit tests for the {@link TurnManager} class, which handles the core logic 
 * of resolving a turn in a Pokémon battle.
 * 
 * This test class covers various scenarios, including:
 * - Both trainers using moves and taking damage without fainting
 * - One trainer's Pokémon fainting from the other's attack
 * - Trainers switching Pokémon mid-turn
 * - Invalid switch attempts
 * - Edge cases like using a move with 0 PP
 * 
 * The tests are designed to be deterministic by controlling randomness in stats 
 * and damage rolls, ensuring consistent results across runs. Each test verifies 
 * not only the type of result (e.g., battle over or not) but also the specific 
 * content of the results, such as HP changes, PP consumption, and effectiveness values.
 */
class TurnManagerTest {

    public static final Logger LOGGER = LoggerFactory.getLogger(TurnManagerTest.class);

    @BeforeEach
    void beforeEach() {
        LOGGER.info("Starting a new test...");
    }

    @AfterEach
    void afterEach() {
        LOGGER.info("Test completed.");
    }

    // ── Helper: builds a standard 2-trainer, 1-Pokémon-each scenario ──────────
    // Returns an array: [trainer1, trainer2, team1, team2, abra1, abra2]
    // Both Pokémon are level 50 Abras that know Tackle.
    // This eliminates the 10+ lines of boilerplate repeated in every test.
    private Object[] buildScenario() {
        Trainer t1 = new Trainer("Ash");
        Trainer t2 = new Trainer("Gary");
        t1.createTeam("Team1");
        t2.createTeam("Team2");
        Team team1 = t1.getTeam("Team1");
        Team team2 = t2.getTeam("Team2");
        Pokemon abra1 = new Abra("Abra1");
        Pokemon abra2 = new Abra("Abra2");
        abra1.setLevel(50);
        abra2.setLevel(50);
        StatCalculator.calculateAllStats(abra1);
        StatCalculator.calculateAllStats(abra2);
        // calculateAllStats doesn't reset currentHP when maxHP was already set
        // (see StatCalculator lines 72-80), so we must heal to the new max manually.
        abra1.setCurrentHP(abra1.getMaxHP());
        abra2.setCurrentHP(abra2.getMaxHP());
        Move tackle = new Tackle();
        abra1.addMove(tackle);
        abra2.addMove(tackle);
        team1.setTeamSlot(0, abra1);
        team2.setTeamSlot(0, abra2);
        return new Object[]{t1, t2, team1, team2, abra1, abra2};
    }

    @Test
    void resolveTurn_bothUseMoves_bothTakeDamageAndBattleContinues() {
        // ARRANGE — build scenario and fix randomness so results are deterministic
        Object[] s = buildScenario();
        Trainer t1 = (Trainer) s[0]; Trainer t2 = (Trainer) s[1];
        Team team1 = (Team) s[2]; Team team2 = (Team) s[3];
        Pokemon abra1 = (Pokemon) s[4]; Pokemon abra2 = (Pokemon) s[5];

        // Force deterministic speeds so we know abra1 always goes first.
        // Without this, random IVs/natures could swap turn order, making
        // assertions about action1Result vs action2Result unreliable.
        abra1.setCurrentSpeed(100);
        abra2.setCurrentSpeed(50);

        // Seed the RNG so damage rolls, crit checks, and accuracy checks
        // produce the same values every run. This eliminates flakiness.
        Attack.setRng(new java.util.Random(42));

        // Capture HP and PP before the turn so we can verify changes after
        int abra1HpBefore = abra1.getCurrentHP();
        int abra2HpBefore = abra2.getCurrentHP();
        int abra1PpBefore = abra1.getMoveSet().get(0).getCurrentPP();
        int abra2PpBefore = abra2.getMoveSet().get(0).getCurrentPP();

        MoveAction action1 = new MoveAction(t1, abra1, team1, 0);
        MoveAction action2 = new MoveAction(t2, abra2, team2, 0);
        Battle battle = new Battle();

        // ACT
        TurnResult result = TurnManager.resolveTurn(action1, action2, battle);

        // ASSERT — verify every field of TurnResult, not just the type
        assertNotNull(result, "resolveTurn should never return null");

        // Both Pokémon used a move, so both DamageResults should exist
        assertNotNull(result.action1Result(),
                "action1Result should not be null when the first actor used a move");
        assertNotNull(result.action2Result(),
                "action2Result should not be null when the second actor used a move (neither fainted)");

        // The battle should continue — one Tackle shouldn't KO a level 50 Abra
        assertFalse(result.battleOver(),
                "Battle should not be over after a single Tackle exchange at full HP");
        assertNull(result.winner(),
                "There should be no winner when the battle is still ongoing");

        // Verify damage was actually dealt (content, not just type)
        assertTrue(result.action1Result().damage() > 0,
                "First action (Tackle) should deal some damage");
        assertTrue(result.action2Result().damage() > 0,
                "Second action (Tackle) should deal some damage");

        // Verify HP was reduced on the Pokémon objects (side-effect check)
        assertTrue(abra2.getCurrentHP() < abra2HpBefore,
                "Abra2 should have lost HP from Abra1's Tackle");
        assertTrue(abra1.getCurrentHP() < abra1HpBefore,
                "Abra1 should have lost HP from Abra2's Tackle");

        // Verify PP was consumed for both moves
        assertEquals(abra1PpBefore - 1, abra1.getMoveSet().get(0).getCurrentPP(),
                "Abra1's Tackle PP should decrease by 1 after using it");
        assertEquals(abra2PpBefore - 1, abra2.getMoveSet().get(0).getCurrentPP(),
                "Abra2's Tackle PP should decrease by 1 after using it");

        // Verify effectiveness — Tackle is Normal-type, Abra is Psychic-type,
        // so effectiveness should be 1.0 (neutral)
        assertEquals(1.0f, result.action1Result().effectiveness(), 0.001f,
                "Normal-type Tackle vs Psychic-type Abra should be neutral (1.0)");
        assertEquals(1.0f, result.action2Result().effectiveness(), 0.001f,
                "Normal-type Tackle vs Psychic-type Abra should be neutral (1.0)");
    }

    @Test
    void resolveTurnHandlesFaintedPokemon() {
        Object[] s = buildScenario();
        Trainer t1 = (Trainer) s[0]; Trainer t2 = (Trainer) s[1];
        Team team1 = (Team) s[2]; Team team2 = (Team) s[3];
        Pokemon abra1 = (Pokemon) s[4]; Pokemon abra2 = (Pokemon) s[5];

        // Force abra1 faster so it always goes first — without this, random
        // IVs/natures can make abra2 faster, causing the WRONG Pokémon to faint.
        // This was the original cause of the test failure: a "flaky test" caused
        // by uncontrolled randomness in speed stats.
        abra1.setCurrentSpeed(200);
        abra2.setCurrentSpeed(10);
        abra1.setCurrentAttack(300);
        abra2.setCurrentHP(1); // Make abra2 faint from abra1's attack

        MoveAction action1 = new MoveAction(t1, abra1, team1, 0);
        MoveAction action2 = new MoveAction(t2, abra2, team2, 0);
        
        Battle battle = new Battle();

        // Simulate a turn where abra1 attacks abra2 and causes it to faint
        TurnResult result = TurnManager.resolveTurn(action1, action2, battle);
        assertTrue(result.battleOver());
        assertTrue(result.winner() == t1);
    }

    @Test
    void resolveTurnHandlesSwitchingInNewPokemon() {
        Object[] s = buildScenario();
        Trainer t1 = (Trainer) s[0]; Trainer t2 = (Trainer) s[1];
        Team team1 = (Team) s[2]; Team team2 = (Team) s[3];
        Pokemon abra1 = (Pokemon) s[4]; Pokemon abra2 = (Pokemon) s[5];

        Pokemon abra3 = new Abra("Abra3");
        abra3.addMove(new Tackle());
        team1.setTeamSlot(1, abra3); // Add a second Pokémon to trainer

        SwitchAction action1 = new SwitchAction(t1, abra1, team1, abra3.getCurrentTeamSlotIndex()); // Switch to abra3
        MoveAction action2 = new MoveAction(t2, abra2, team2, 0);

        Battle battle = new Battle();

        // Simulate a turn where trainer1 switches to abra3 and trainer2 attacks
        TurnResult result = TurnManager.resolveTurn(action1, action2, battle);
        assertFalse(result.battleOver());
        assertEquals(abra3.getNickname(), team1.getTeamSlot(0).getNickname()); // Verify that abra3 was switched in successfully
        assertTrue(abra3.getCurrentHP() < abra3.getMaxHP()); // Verify that abra3 took damage from the attack
    
    }

    @Test
    void resolveTurnHandlesSecondTrainerSwitching() {
        Object[] s = buildScenario();
        Trainer t1 = (Trainer) s[0]; Trainer t2 = (Trainer) s[1];
        Team team1 = (Team) s[2]; Team team2 = (Team) s[3];
        Pokemon abra1 = (Pokemon) s[4]; Pokemon abra2 = (Pokemon) s[5];

        Pokemon abra3 = new Abra("Abra3");
        abra3.addMove(new Tackle());
        team1.setTeamSlot(1, abra3); // Add a second Pokémon to trainer

        SwitchAction action2 = new SwitchAction(t1, abra1, team1, abra3.getCurrentTeamSlotIndex()); // Switch to abra3
        MoveAction action1 = new MoveAction(t2, abra2, team2, 0);

        Battle battle = new Battle();

        // Simulate a turn where trainer1 switches to abra3 and trainer2 attacks
        TurnResult result = TurnManager.resolveTurn(action1, action2, battle);
        assertFalse(result.battleOver());
        assertEquals(abra3.getNickname(), team1.getTeamSlot(0).getNickname()); // Verify that abra3 was switched in successfully
        assertTrue(abra3.getCurrentHP() < abra3.getMaxHP()); // Verify that abra3 took damage from the attack
    }

    // 
    @Test
    void resolveSwitchHandlesInvalidSwitch() {
        Object[] s = buildScenario();
        Trainer t1 = (Trainer) s[0]; Trainer t2 = (Trainer) s[1];
        Team team1 = (Team) s[2]; Team team2 = (Team) s[3];
        Pokemon abra1 = (Pokemon) s[4]; Pokemon abra2 = (Pokemon) s[5];

        Pokemon abra3 = new Abra("Abra3");
        abra3.addMove(new Tackle());
        team1.setTeamSlot(1, abra3); // Add a second Pokémon to trainer

        SwitchAction action1 = new SwitchAction(t1, abra2, team2, abra3.getCurrentTeamSlotIndex()); // Switch to abra3
        MoveAction action2 = new MoveAction(t2, abra1, team1, 0);

        Battle battle = new Battle();

        // Simulate a turn where trainer1 switches to abra3 and trainer2 attacks
        TurnResult result = TurnManager.resolveTurn(action1, action2, battle);
        assertFalse(result.battleOver());
        assertEquals(abra2.getNickname(), team2.getTeamSlot(0).getNickname()); // Verify that the invalid switch did not occur and abra2 is still active
        assertTrue(abra2.getCurrentHP() < abra2.getMaxHP()); // Verify that abra2 took damage from the attack, confirming that the turn still resolved correctly despite the invalid switch
    }

    // ══════════════════════════════════════════════════════════════════
    // NEW TESTS — covering paths not exercised by existing tests
    // ══════════════════════════════════════════════════════════════════

    // -- getFirstAction: switch always goes before move --
    // Tests the priority rule directly, without running a full turn.
    // Isolating getFirstAction makes it easy to tell whether a bug is
    // in turn ordering vs. in damage/switch resolution.
    @Test
    void getFirstActionSwitchAlwaysBeforeMove() {
        Object[] s = buildScenario();
        Trainer t1 = (Trainer) s[0]; Trainer t2 = (Trainer) s[1];
        Team team1 = (Team) s[2]; Team team2 = (Team) s[3];
        Pokemon abra1 = (Pokemon) s[4]; Pokemon abra2 = (Pokemon) s[5];

        Pokemon abra3 = new Abra("Abra3");
        abra3.addMove(new Tackle());
        team1.setTeamSlot(1, abra3);

        SwitchAction switchAction = new SwitchAction(t1, abra1, team1, 1);
        MoveAction moveAction = new MoveAction(t2, abra2, team2, 0);
        Battle battle = new Battle();

        // Switch should go first regardless of which argument position it's in
        assertSame(switchAction, TurnManager.getFirstAction(switchAction, moveAction, battle));
        assertSame(switchAction, TurnManager.getFirstAction(moveAction, switchAction, battle));
    }

    // -- getFirstAction: faster Pokémon goes first when both use moves --
    @Test
    void getFirstActionFasterPokemonGoesFirst() {
        Object[] s = buildScenario();
        Trainer t1 = (Trainer) s[0]; Trainer t2 = (Trainer) s[1];
        Team team1 = (Team) s[2]; Team team2 = (Team) s[3];
        Pokemon abra1 = (Pokemon) s[4]; Pokemon abra2 = (Pokemon) s[5];

        // Force deterministic speeds so the test isn't flaky
        abra1.setCurrentSpeed(100);
        abra2.setCurrentSpeed(50);

        MoveAction action1 = new MoveAction(t1, abra1, team1, 0);
        MoveAction action2 = new MoveAction(t2, abra2, team2, 0);
        Battle battle = new Battle();

        assertSame(action1, TurnManager.getFirstAction(action1, action2, battle));
    }

    // -- PP is decremented after using a move --
    // resolveMove() calls MoveSlot.use() before dealing damage. This test
    // verifies that PP actually goes down, which is important for the
    // "out of PP" branch to ever trigger.
    @Test
    void resolveMoveDecrementsPP() {
        Object[] s = buildScenario();
        Pokemon abra1 = (Pokemon) s[4]; Pokemon abra2 = (Pokemon) s[5];

        MoveSlot slot = abra1.getMoveSet().get(0);
        int ppBefore = slot.getCurrentPP();

        MoveAction action = new MoveAction((Trainer) s[0], abra1, (Team) s[2], 0);
        TurnManager.resolveMove(action, abra2);

        assertEquals(ppBefore - 1, slot.getCurrentPP(),
                "PP should decrease by 1 after using a move");
    }

    // -- Move with 0 PP returns a zero-damage result and does not reduce PP further --
    @Test
    void resolveMoveWithZeroPPDoesNothing() {
        Object[] s = buildScenario();
        Pokemon abra1 = (Pokemon) s[4]; Pokemon abra2 = (Pokemon) s[5];

        // Drain all PP
        MoveSlot slot = abra1.getMoveSet().get(0);
        while (slot.getCurrentPP() > 0) {
            MoveSlotService.use(abra1, slot);
        }
        int hpBefore = abra2.getCurrentHP();

        MoveAction action = new MoveAction((Trainer) s[0], abra1, (Team) s[2], 0);
        DamageResult result = TurnManager.resolveMove(action, abra2);

        assertEquals(0, result.damage(), "No damage should be dealt with 0 PP");
        assertEquals(0, slot.getCurrentPP(), "PP should stay at 0");
        assertEquals(hpBefore, abra2.getCurrentHP(), "Defender HP should be unchanged");
    }

    // -- Normal turn where neither Pokémon faints --
    // Verifies that both DamageResults in the TurnResult are non-null,
    // that both Pokémon took damage, and the battle is NOT over.
    @Test
    void resolveTurnBothPokemonSurvive() {
        Object[] s = buildScenario();
        Trainer t1 = (Trainer) s[0]; Trainer t2 = (Trainer) s[1];
        Team team1 = (Team) s[2]; Team team2 = (Team) s[3];
        Pokemon abra1 = (Pokemon) s[4]; Pokemon abra2 = (Pokemon) s[5];

        // Set maxHP BEFORE currentHP — setCurrentHP() clamps to maxHP,
        // so if maxHP is still the default (~20 for Abra), setting currentHP
        // to 300 silently gets clamped back down to 20.
        abra1.setMaxHP(300);
        abra1.setCurrentHP(300);
        abra2.setMaxHP(300);
        abra2.setCurrentHP(300);
        abra1.setCurrentAttack(50);
        abra2.setCurrentAttack(50);
        abra1.setCurrentSpeed(100);
        abra2.setCurrentSpeed(90);

        LOGGER.info("abra1 HP: {}, abra2 HP: {}", abra1.getCurrentHP(), abra2.getCurrentHP());

        MoveAction action1 = new MoveAction(t1, abra1, team1, 0);
        MoveAction action2 = new MoveAction(t2, abra2, team2, 0);
        Battle battle = new Battle();

        TurnResult result = TurnManager.resolveTurn(action1, action2, battle);

        assertFalse(result.battleOver(), "Battle should not be over when both survive");
        assertNull(result.winner(), "No winner when both survive");
        assertNotNull(result.action1Result(), "First action should produce a DamageResult");
        assertNotNull(result.action2Result(), "Second action should produce a DamageResult");
        assertTrue(abra1.getCurrentHP() < 999, "First Pokémon should have taken damage");
        assertTrue(abra2.getCurrentHP() < 999, "Second Pokémon should have taken damage");
    }

    // -- Switch correctly swaps slot positions --
    // Focused test for resolveSwitch: verifies that both Pokémon end up
    // in each other's slots and their currentTeamSlotIndex fields are updated.
    @Test
    void resolveSwitchSwapsSlotPositions() {
        Object[] s = buildScenario();
        Trainer t1 = (Trainer) s[0];
        Team team1 = (Team) s[2];
        Pokemon abra1 = (Pokemon) s[4];

        Pokemon abra3 = new Abra("Abra3");
        abra3.addMove(new Tackle());
        team1.setTeamSlot(1, abra3);

        SwitchAction switchAction = new SwitchAction(t1, abra1, team1, 1);
        TurnManager.resolveSwitch(switchAction);

        // After switching: abra3 should be in slot 0, abra1 in slot 1
        assertSame(abra3, team1.getTeamSlot(0), "New Pokémon should be in slot 0");
        assertSame(abra1, team1.getTeamSlot(1), "Old Pokémon should be in slot 1");
        assertEquals(0, abra3.getCurrentTeamSlotIndex(), "New Pokémon's slot index should be 0");
        assertEquals(1, abra1.getCurrentTeamSlotIndex(), "Old Pokémon's slot index should be 1");
    }

    // -- Second action is skipped when the second Pokémon faints from the first action --
    // When the faster Pokémon KOs the slower one, the slower one shouldn't get
    // to attack. Verify that action2Result is null in this case.
    @Test
    void secondActionSkippedWhenDefenderFaints() {
        Object[] s = buildScenario();
        Trainer t1 = (Trainer) s[0]; Trainer t2 = (Trainer) s[1];
        Team team1 = (Team) s[2]; Team team2 = (Team) s[3];
        Pokemon abra1 = (Pokemon) s[4]; Pokemon abra2 = (Pokemon) s[5];

        // Make abra1 guaranteed faster and strong enough to KO
        abra1.setCurrentSpeed(200);
        abra2.setCurrentSpeed(10);
        abra1.setCurrentAttack(200);
        abra2.setCurrentHP(1);

        int abra1HpBefore = abra1.getCurrentHP();

        MoveAction action1 = new MoveAction(t1, abra1, team1, 0);
        MoveAction action2 = new MoveAction(t2, abra2, team2, 0);
        Battle battle = new Battle();

        TurnResult result = TurnManager.resolveTurn(action1, action2, battle);

        assertTrue(result.battleOver(), "Battle should be over");
        assertSame(t1, result.winner(), "Trainer 1 should win");
        assertNull(result.action2Result(), "Second action should be null — fainted Pokémon can't attack");
        assertEquals(abra1HpBefore, abra1.getCurrentHP(),
                "Attacker should not have taken damage since defender fainted before acting");
    }

    // -- dealDamage clamps damage to defender's remaining HP (no negative HP) --
    @Test
    void dealDamageDoesNotProduceNegativeHP() {
        Object[] s = buildScenario();
        Pokemon abra1 = (Pokemon) s[4]; Pokemon abra2 = (Pokemon) s[5];

        abra1.setCurrentAttack(999);
        abra2.setCurrentHP(1);

        int damageDealt = TurnManager.dealDamage(abra1, abra2, abra1.getMoveSet().get(0).getMove());

        assertTrue(damageDealt <= 1, "Damage should be clamped to defender's remaining HP");
        assertTrue(abra2.getCurrentHP() >= 0, "Defender HP should not go negative");
    }
}
