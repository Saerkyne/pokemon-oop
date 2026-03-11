package pokemonGame;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import static org.junit.jupiter.api.Assertions.*;

import pokemonGame.mons.Abra;
import pokemonGame.mons.Electrode;
import pokemonGame.mons.Slowbro;
import pokemonGame.moves.Psychic;
import pokemonGame.moves.MegaPunch;

class BattleTest {

    private Trainer player;
    private Trainer opponent;
    private Pokemon fastPokemon;   // Electrode: 150 base speed
    private Pokemon slowPokemon;   // Slowbro: 30 base speed

    @BeforeEach
    void setUp() {
        player = new Trainer("Ash");
        opponent = new Trainer("Gary");
        fastPokemon = new Electrode("Electrode");
        slowPokemon = new Slowbro("Slowbro");
        fastPokemon.setLevel(50);
        slowPokemon.setLevel(50);
    }

    // --- dealDamage ---

    @Test
    void dealDamageReducesDefenderHP() {
        Pokemon attacker = new Abra("Attacker");
        Pokemon defender = new Abra("Defender");
        attacker.setLevel(50);
        defender.setLevel(50);

        int hpBefore = defender.getCurrentHP();
        Battle.dealDamage(attacker, defender, new Psychic());
        assertTrue(defender.getCurrentHP() < hpBefore,
                "Defender's HP should decrease after taking damage");
    }

    // IDEAL BEHAVIOR: After overkill damage, HP should be clamped at exactly 0.
    // CURRENT BEHAVIOR: setCurrentHP accepts any int value, so HP goes negative.
    // WHY THIS MATTERS: Negative HP is meaningless in the game and can cause
    // confusing side effects (e.g., display bugs, incorrect damage-taken totals).
    // TODO: Remove @Disabled when setCurrentHP or dealDamage clamps HP at 0.
    @Disabled("KNOWN LIMITATION: HP is not clamped — setCurrentHP allows negative values")
    @Test
    void dealDamage_hpShouldBeClampedAtZero() {
        Pokemon attacker = new Abra("Attacker");
        Pokemon defender = new Abra("Defender");
        attacker.setLevel(100);
        defender.setLevel(5);

        for (int i = 0; i < 10; i++) {
            Battle.dealDamage(attacker, defender, new Psychic());
        }
        assertEquals(0, defender.getCurrentHP(),
                "HP should be clamped at 0 after overkill damage, never negative");
    }

    @Test
    void dealDamageWithPhysicalMove() {
        Pokemon attacker = new Abra("Attacker");
        Pokemon defender = new Abra("Defender");
        attacker.setLevel(50);
        defender.setLevel(50);

        int hpBefore = defender.getCurrentHP();
        Battle.dealDamage(attacker, defender, new MegaPunch());
        assertTrue(defender.getCurrentHP() < hpBefore,
                "Physical move should also reduce HP");
    }

    @Test
    void dealDamage_alwaysDealsPositiveDamage() {
        // Verify that a damaging move always reduces the defender's HP by at least 1.
        // (Renamed from "ExactHPReduction" — that name was misleading because
        // the test only checks damage > 0, not an exact value.  An exact value
        // isn't possible to assert here because the damage formula includes a
        // random factor between 217/255 and 255/255.)
        Pokemon attacker = new Abra("Attacker");
        Pokemon defender = new Abra("Defender");
        attacker.setLevel(50);
        defender.setLevel(50);

        int hpBefore = defender.getCurrentHP();
        Battle.dealDamage(attacker, defender, new Psychic());
        int hpAfter = defender.getCurrentHP();
        int hpLost = hpBefore - hpAfter;
        assertTrue(hpLost > 0, "HP lost should be positive (damage was dealt)");
    }

    // --- checkSpeed ---

    @Test
    void fasterPokemonTrainerGoesFirst() {
        player.addPokemonToTeam(fastPokemon);
        opponent.addPokemonToTeam(slowPokemon);

        Trainer first = Battle.checkSpeed(player, opponent);
        assertSame(player, first,
                "Trainer with faster lead Pokémon should go first");
    }

    @Test
    void slowerPokemonTrainerGoesSecond() {
        player.addPokemonToTeam(slowPokemon);
        opponent.addPokemonToTeam(fastPokemon);

        Trainer first = Battle.checkSpeed(player, opponent);
        assertSame(opponent, first,
                "Trainer with faster lead Pokémon should go first");
    }

    @Test
    void tiedSpeedReturnsEitherTrainer() {
        Pokemon abra1 = new Abra("Abra 1");
        Pokemon abra2 = new Abra("Abra 2");
        abra1.setLevel(50);
        abra2.setLevel(50);
        abra1.setCurrentSpeed(100);
        abra2.setCurrentSpeed(100);

        player.addPokemonToTeam(abra1);
        opponent.addPokemonToTeam(abra2);

        // Run multiple times — result should always be one of the two trainers
        for (int i = 0; i < 20; i++) {
            Trainer first = Battle.checkSpeed(player, opponent);
            assertTrue(first == player || first == opponent,
                    "Tied speed should return one of the two trainers");
        }
    }

    @Test
    void tiedSpeedIsRandom() {
        // With enough trials, both trainers should be returned at least once
        Pokemon abra1 = new Abra("Abra 1");
        Pokemon abra2 = new Abra("Abra 2");
        abra1.setLevel(50);
        abra2.setLevel(50);
        abra1.setCurrentSpeed(100);
        abra2.setCurrentSpeed(100);

        player.addPokemonToTeam(abra1);
        opponent.addPokemonToTeam(abra2);

        boolean playerFirst = false;
        boolean opponentFirst = false;
        for (int i = 0; i < 100; i++) {
            Trainer first = Battle.checkSpeed(player, opponent);
            if (first == player) playerFirst = true;
            if (first == opponent) opponentFirst = true;
            if (playerFirst && opponentFirst) break;
        }
        assertTrue(playerFirst && opponentFirst,
                "With tied speed, both trainers should eventually go first");
    }

    // --- checkFainted ---

    @Test
    void pokemonWithZeroHPIsFainted() {
        Pokemon abra = new Abra("Faintee");
        abra.setCurrentHP(0);
        assertTrue(Battle.checkFainted(abra),
                "Pokémon with 0 HP should be fainted");
    }

    // NOTE: In ideal code, HP should never be negative (see dealDamage_hpShouldBeClampedAtZero).
    // This test verifies that checkFainted defensively handles the case where HP
    // has already gone negative due to the missing HP clamp. Think of it as a
    // safety-net test — good to have, even though the triggering state shouldn't occur.
    @Test
    void pokemonWithNegativeHPIsFainted() {
        Pokemon abra = new Abra("Faintee");
        abra.setCurrentHP(-5);
        assertTrue(Battle.checkFainted(abra),
                "Pokémon with negative HP should be fainted");
    }

    @Test
    void pokemonWithPositiveHPIsNotFainted() {
        Pokemon abra = new Abra("Healthy");
        abra.setLevel(50);
        assertTrue(abra.getCurrentHP() > 0, "Precondition: HP should be positive");
        assertFalse(Battle.checkFainted(abra),
                "Pokémon with positive HP should not be fainted");
    }

    @Test
    void pokemonWith1HPIsNotFainted() {
        Pokemon abra = new Abra("Barely Alive");
        abra.setCurrentHP(1);
        assertFalse(Battle.checkFainted(abra),
                "Pokémon with 1 HP should not be fainted");
    }

    @Test
    void checkFaintedSetsIsFaintedFlag() {
        Pokemon abra = new Abra("Faintee");
        assertFalse(abra.getIsFainted(), "Precondition: isFainted should start false");
        abra.setCurrentHP(0);
        Battle.checkFainted(abra);
        assertTrue(abra.getIsFainted(),
                "checkFainted should set the isFainted flag when HP <= 0");
    }

    @Test
    void checkFaintedDoesNotSetFlagWhenAlive() {
        Pokemon abra = new Abra("Alive");
        abra.setLevel(50);
        Battle.checkFainted(abra);
        assertFalse(abra.getIsFainted(),
                "checkFainted should not set isFainted when HP > 0");
    }

    // --- enterBattleState ---

    @Test
    void enterBattleStateDoesNotThrow() {
        player.addPokemonToTeam(fastPokemon);
        opponent.addPokemonToTeam(slowPokemon);

        assertDoesNotThrow(() -> Battle.enterBattleState(player, opponent),
                "enterBattleState should not throw with valid trainers");
    }

    // --- startTurn ---

    @Test
    void startTurnDoesNotThrow() {
        player.addPokemonToTeam(fastPokemon);
        opponent.addPokemonToTeam(slowPokemon);

        assertDoesNotThrow(() -> Battle.startTurn(player, opponent),
                "startTurn should not throw with valid trainers");
    }

    // --- dealDamage + checkFainted integration ---

    @Test
    void repeatedDamageCausesFainting() {
        Pokemon attacker = new Abra("Attacker");
        Pokemon defender = new Abra("Defender");
        attacker.setLevel(100);
        defender.setLevel(5);

        while (!Battle.checkFainted(defender)) {
            Battle.dealDamage(attacker, defender, new Psychic());
        }
        assertTrue(defender.getCurrentHP() <= 0,
                "Defender should have 0 or less HP after fainting");
        assertTrue(defender.getIsFainted(),
                "Defender's isFainted flag should be true after fainting");
    }

    // --- fainted Pokemon should not be able to enter battle ---

    // FALSE POSITIVE FIX: The original version of this test only checked that
    // the isFainted flag persisted after calling enterBattleState. That always
    // passes because the flag is never cleared — it didn't prove the system
    // *prevented* a fainted Pokémon from entering battle.
    //
    // IDEAL BEHAVIOR: enterBattleState should reject a trainer whose entire
    // team is fainted (e.g., throw IllegalStateException).
    // CURRENT BEHAVIOR: enterBattleState prints a message but proceeds.
    // TODO: Remove @Disabled when enterBattleState validates team health.
    @Disabled("KNOWN LIMITATION: enterBattleState does not validate team health")
    @Test
    void enterBattleState_shouldRejectTrainerWithAllFaintedPokemon() {
        Pokemon faintedPokemon = new Abra("Faintee");
        faintedPokemon.setCurrentHP(0);
        Battle.checkFainted(faintedPokemon);
        assertTrue(faintedPokemon.getIsFainted(), "Precondition: Pokémon should be fainted");

        Trainer trainerWithFainted = new Trainer("Trainer");
        trainerWithFainted.addPokemonToTeam(faintedPokemon);
        Trainer validOpponent = new Trainer("Opponent");
        validOpponent.addPokemonToTeam(new Abra("Healthy"));

        assertThrows(IllegalStateException.class,
                () -> Battle.enterBattleState(trainerWithFainted, validOpponent),
                "Should reject battle when a trainer has no usable Pokémon");
    }

    // --- Baseline regression tests for Phase 1 ---

    @Test
    void dealDamage_doesNotConsumeMoveSlotPP() {
        // BASELINE: Battle.dealDamage takes a raw Move object, not a MoveSlot.
        // This means PP is never decremented during damage dealing.
        //
        // KNOWN LIMITATION: In a complete battle system, PP should be consumed
        // when a move is used. This will require the turn pipeline (Phase 3)
        // to work with MoveSlot instead of (or in addition to) raw Move objects.
        // TODO (Phase 3): Change this test to assert PP decreases by 1.
        Pokemon attacker = new Abra("Attacker");
        attacker.setLevel(50);
        attacker.addMove(new Psychic());
        Pokemon defender = new Abra("Defender");
        defender.setLevel(50);

        int ppBefore = attacker.getMoveset().get(0).getCurrentPP();
        Battle.dealDamage(attacker, defender, attacker.getMoveset().get(0).getMove());
        int ppAfter = attacker.getMoveset().get(0).getCurrentPP();

        assertEquals(ppBefore, ppAfter,
                "PP should be unchanged — dealDamage currently takes raw Move, not MoveSlot");
    }

    @Test
    void singleMassiveHitCanCauseFainting() {
        // A level 100 attacker hitting a level 5 defender should cause fainting
        // in a single hit (Psychic has 90 base power, Abra has low HP/defenses).
        Pokemon attacker = new Abra("Attacker");
        Pokemon defender = new Abra("Defender");
        attacker.setLevel(100);
        defender.setLevel(5);

        Battle.dealDamage(attacker, defender, new Psychic());
        assertTrue(Battle.checkFainted(defender),
                "A massive level-advantage hit should faint a low-level Pokémon");
    }

    @Test
    void checkFainted_isIdempotentForAlivePokemon() {
        // Calling checkFainted multiple times on a healthy Pokémon should
        // always return false and never corrupt state.
        Pokemon abra = new Abra("Healthy");
        abra.setLevel(50);

        for (int i = 0; i < 5; i++) {
            assertFalse(Battle.checkFainted(abra),
                    "checkFainted should consistently return false for alive Pokémon");
        }
        assertFalse(abra.getIsFainted(), "isFainted flag should remain false");
    }

    @Test
    void checkFainted_isIdempotentForFaintedPokemon() {
        // Calling checkFainted multiple times on a fainted Pokémon should
        // always return true and not cause errors.
        Pokemon abra = new Abra("Faintee");
        abra.setCurrentHP(0);

        for (int i = 0; i < 5; i++) {
            assertTrue(Battle.checkFainted(abra),
                    "checkFainted should consistently return true for fainted Pokémon");
        }
        assertTrue(abra.getIsFainted(), "isFainted flag should remain true");
    }

    // IDEAL BEHAVIOR: If a Pokémon is "revived" (HP restored above 0 after fainting),
    // checkFainted should return false and clear the isFainted flag.
    // CURRENT BEHAVIOR: checkFainted only ever sets isFainted to true; it never
    // resets it to false, so a revived Pokémon remains permanently flagged as fainted.
    // TODO: Remove @Disabled when checkFainted handles the revival case.
    @Disabled("KNOWN LIMITATION: checkFainted never clears isFainted flag after HP is restored")
    @Test
    void checkFainted_shouldClearFlagWhenHPRestoredAboveZero() {
        Pokemon abra = new Abra("Revivee");
        abra.setCurrentHP(0);
        Battle.checkFainted(abra);
        assertTrue(abra.getIsFainted(), "Precondition: should be fainted at 0 HP");

        // "Revive" the Pokémon by restoring HP above 0
        abra.setCurrentHP(50);
        Battle.checkFainted(abra);
        assertFalse(abra.getIsFainted(),
                "isFainted should be cleared when HP is restored above 0");
    }

    @Test
    void checkSpeed_usesLeadPokemonNotTeamAverage() {
        // Verify speed comparison uses the first Pokémon in the team (index 0),
        // not any other team member or aggregate.
        Pokemon slow1 = new Slowbro("Lead Slow");
        Pokemon fast1 = new Electrode("Bench Fast");
        slow1.setLevel(50);
        fast1.setLevel(50);

        Pokemon fast2 = new Electrode("Lead Fast");
        Pokemon slow2 = new Slowbro("Bench Slow");
        fast2.setLevel(50);
        slow2.setLevel(50);

        // Player's lead is slow, but has a fast bench Pokémon
        player.addPokemonToTeam(slow1);
        player.addPokemonToTeam(fast1);
        // Opponent's lead is fast, but has a slow bench Pokémon
        opponent.addPokemonToTeam(fast2);
        opponent.addPokemonToTeam(slow2);

        Trainer first = Battle.checkSpeed(player, opponent);
        assertSame(opponent, first,
                "Speed check should use lead Pokémon (index 0), not bench/average");
    }

    @Test
    void dealDamage_damageIsNonNegative() {
        // Verify that dealDamage never heals the defender (damage is >= 0).
        // This could theoretically happen if the damage formula produced a
        // negative result due to integer underflow or a formula bug.
        Pokemon attacker = new Abra("Attacker");
        Pokemon defender = new Abra("Defender");
        attacker.setLevel(50);
        defender.setLevel(50);

        int hpBefore = defender.getCurrentHP();
        Battle.dealDamage(attacker, defender, new Psychic());
        assertTrue(defender.getCurrentHP() <= hpBefore,
                "Defender HP should never increase after being attacked");
    }
}
