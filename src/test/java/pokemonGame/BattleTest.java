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

    /*
     * CHECKS:  Battle.dealDamage() reduces the defending Pokémon's current HP.
     * HOW:     Records the defender's HP before the call, then asserts getCurrentHP()
     *          is strictly less than the recorded value after the call.
     * IMPROVE: This only verifies HP decreases by at least 1, not by how much. A more
     *          precise test would also assert the exact damage value (given a seeded or
     *          deterministic damage formula) to catch formula regressions.
     */
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

    /*
     * CHECKS:  After enough damage to far exceed the defender's max HP, the defender's
     *          currentHP should be clamped at exactly 0 (not go negative).
     * HOW:     A level-100 attacker deals damage 10 times to a level-5 defender, then
     *          asserts getCurrentHP() equals 0.
     * IMPROVE: Once HP clamping is implemented in setCurrentHP() or dealDamage(),
     *          remove @Disabled. Also add a single-hit overkill test to confirm the
     *          clamp applies from any single call, not just accumulated damage.
     */
    // IDEAL BEHAVIOR: After overkill damage, HP should be clamped at exactly 0.
    // CURRENT BEHAVIOR: setCurrentHP accepts any int value, so HP goes negative.
    // WHY THIS MATTERS: Negative HP is meaningless in the game and can cause
    // confusing side effects (e.g., display bugs, incorrect damage-taken totals).
    // TODO: Remove @Disabled when setCurrentHP or dealDamage clamps HP at 0.
    // @Disabled("KNOWN LIMITATION: HP is not clamped — setCurrentHP allows negative values")
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

    /*
     * CHECKS:  A Physical move (MegaPunch) also reduces the defender's HP, confirming
     *          both Physical and Special damage categories are handled by dealDamage.
     * HOW:     Records HP before calling dealDamage with MegaPunch, then asserts HP
     *          decreased.
     * IMPROVE: This test only verifies "HP decreased", not which stats were used.
     *          A stronger test would assert that Attack/Defense stats were used for
     *          MegaPunch and SpAtk/SpDef for Psychic, verifying the category routing
     *          logic inside dealDamage.
     */
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

    /*
     * CHECKS:  dealDamage() always reduces the defender's HP by at least 1 (i.e., the
     *          damage formula never produces a zero or negative result for a damaging move).
     * HOW:     Records HP before and after one call to dealDamage with Psychic on two
     *          equal-level Abra, then asserts hpLost > 0.
     * IMPROVE: Run multiple trials to account for any random factor in the formula.
     *          Also test with a high-defense defender to verify the result is still > 0
     *          rather than rounded down to 0 by integer math.
     */
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

    /*
     * CHECKS:  Battle.checkSpeed() returns the trainer whose lead Pokémon has higher
     *          current speed.
     * HOW:     Player's lead is Electrode (150 base speed), opponent's lead is Slowbro
     *          (30 base speed). Asserts checkSpeed returns the player.
     * IMPROVE: Explicitly assert the current speed values being compared (e.g.,
     *          fastPokemon.getCurrentSpeed() > slowPokemon.getCurrentSpeed()) before
     *          the checkSpeed call, making the precondition visible and preventing
     *          silent test setup failures from masking the real assertion.
     */
    @Test
    void fasterPokemonTrainerGoesFirst() {
        player.addPokemonToTeam(fastPokemon);
        opponent.addPokemonToTeam(slowPokemon);

        Trainer first = Battle.checkSpeed(player, opponent);
        assertSame(player, first,
                "Trainer with faster lead Pokémon should go first");
    }

    /*
     * CHECKS:  checkSpeed() returns the opponent when the opponent's lead Pokémon is
     *          faster than the player's lead (inverse of fasterPokemonTrainerGoesFirst).
     * HOW:     Player's lead is Slowbro, opponent's lead is Electrode. Asserts
     *          checkSpeed returns the opponent.
     * IMPROVE: These two symmetrical tests could be combined into a single
     *          @ParameterizedTest to reduce duplication while preserving full coverage
     *          of both orderings.
     */
    @Test
    void slowerPokemonTrainerGoesSecond() {
        player.addPokemonToTeam(slowPokemon);
        opponent.addPokemonToTeam(fastPokemon);

        Trainer first = Battle.checkSpeed(player, opponent);
        assertSame(opponent, first,
                "Trainer with faster lead Pokémon should go first");
    }

    /*
     * CHECKS:  When both lead Pokémon have identical current speed, checkSpeed()
     *          returns one of the two trainers (not null or a third party).
     * HOW:     Sets both Abra instances to the same current speed (100), runs 20
     *          trials, and asserts each result is either player or opponent.
     * IMPROVE: 20 trials is enough to confirm neither null nor an unexpected value is
     *          returned, but not enough to confirm both trainers are reachable.
     *          The companion test tiedSpeedIsRandom covers the randomness aspect.
     */
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

    /*
     * CHECKS:  When speed is tied, checkSpeed() eventually returns each trainer at
     *          least once, confirming the tie-breaking is non-deterministic.
     * HOW:     Calls checkSpeed up to 100 times with equal-speed leads, tracking which
     *          trainer was returned; asserts both were returned at least once.
     * IMPROVE: 100 trials gives a high probability (>99.9%) of seeing both outcomes,
     *          but the test is technically flaky. Injecting a seeded RNG or using a
     *          statistical test would make it fully deterministic.
     */
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

    /*
     * CHECKS:  Battle.checkFainted() returns true when the Pokémon's currentHP is
     *          exactly 0 (the faint boundary).
     * HOW:     Sets HP to 0 via setCurrentHP(0), calls checkFainted, asserts true.
     * IMPROVE: Pair with a setCurrentHP(1) assertion in the same test to explicitly
     *          document the faint/alive boundary is at 0, not at some other value.
     */
    @Test
    void pokemonWithZeroHPIsFainted() {
        Pokemon abra = new Abra("Faintee");
        abra.setCurrentHP(0);
        assertTrue(Battle.checkFainted(abra),
                "Pokémon with 0 HP should be fainted");
    }

    /*
     * CHECKS:  checkFainted() also returns true when HP has gone below 0 (a defensive
     *          check since HP should ideally be clamped at 0 by dealDamage).
     * HOW:     Sets HP to -5 and asserts checkFainted returns true.
     * IMPROVE: Once HP clamping is implemented (see dealDamage_hpShouldBeClampedAtZero),
     *          this test becomes a safety-net edge case. Until then it documents the
     *          current real-world behavior. Consider adding a comment clarifying that
     *          negative HP is a known limitation, not an intended feature.
     */
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

    /*
     * CHECKS:  checkFainted() returns false when the Pokémon has full (positive) HP.
     * HOW:     Creates a level-50 Abra (ensuring HP > 0 via a precondition assert),
     *          calls checkFainted, asserts false.
     * IMPROVE: Test with multiple HP values (1, 50, maxHP - 1) to confirm positive HP
     *          is never treated as fainted regardless of the specific value.
     */
    @Test
    void pokemonWithPositiveHPIsNotFainted() {
        Pokemon abra = new Abra("Healthy");
        abra.setLevel(50);
        assertTrue(abra.getCurrentHP() > 0, "Precondition: HP should be positive");
        assertFalse(Battle.checkFainted(abra),
                "Pokémon with positive HP should not be fainted");
    }

    /*
     * CHECKS:  The edge case: exactly 1 HP is NOT fainted, confirming the faint
     *          threshold is at 0 (not 1).
     * HOW:     Sets HP to 1, calls checkFainted, asserts false.
     * IMPROVE: This is a critical boundary test. Adding the 0 HP assertion in the same
     *          test would explicitly document the "1 HP = alive, 0 HP = fainted"
     *          boundary contract in one place.
     */
    @Test
    void pokemonWith1HPIsNotFainted() {
        Pokemon abra = new Abra("Barely Alive");
        abra.setCurrentHP(1);
        assertFalse(Battle.checkFainted(abra),
                "Pokémon with 1 HP should not be fainted");
    }

    /*
     * CHECKS:  checkFainted() sets the isFainted flag to true when HP is 0, not only
     *          returning true but also mutating the Pokémon's state.
     * HOW:     Verifies isFainted starts false, sets HP to 0, calls checkFainted, then
     *          asserts getIsFainted() is true.
     * IMPROVE: The flag-setting and the return-value behaviors are two separate concerns.
     *          Splitting into "flag is set when fainted" and "flag is not set when alive"
     *          (already covered by checkFaintedDoesNotSetFlagWhenAlive) would keep each
     *          test focused on one observable effect.
     */
    @Test
    void checkFaintedSetsIsFaintedFlag() {
        Pokemon abra = new Abra("Faintee");
        assertFalse(abra.getIsFainted(), "Precondition: isFainted should start false");
        abra.setCurrentHP(0);
        Battle.checkFainted(abra);
        assertTrue(abra.getIsFainted(),
                "checkFainted should set the isFainted flag when HP <= 0");
    }

    /*
     * CHECKS:  checkFainted() does NOT set the isFainted flag when the Pokémon is alive
     *          (HP > 0).
     * HOW:     Creates a level-50 Abra (positive HP), calls checkFainted, asserts
     *          getIsFainted() is still false.
     * IMPROVE: Test after HP has been partially reduced (but still > 0) to confirm the
     *          flag is not prematurely set at any HP above 0.
     */
    @Test
    void checkFaintedDoesNotSetFlagWhenAlive() {
        Pokemon abra = new Abra("Alive");
        abra.setLevel(50);
        Battle.checkFainted(abra);
        assertFalse(abra.getIsFainted(),
                "checkFainted should not set isFainted when HP > 0");
    }

    // --- enterBattleState ---

    /*
     * CHECKS:  Battle.enterBattleState() runs to completion without throwing an
     *          exception when both trainers have valid, non-empty teams (smoke test).
     * HOW:     Adds one Pokémon each to player and opponent, then wraps the call in
     *          assertDoesNotThrow.
     * IMPROVE: This only confirms no exception is thrown. A more functional test would
     *          assert specific state changes after enterBattleState (e.g., both lead
     *          Pokémon have current stats initialized, HP equals maxHP, etc.).
     */
    @Test
    void enterBattleStateDoesNotThrow() {
        player.addPokemonToTeam(fastPokemon);
        opponent.addPokemonToTeam(slowPokemon);

        assertDoesNotThrow(() -> Battle.enterBattleState(player, opponent),
                "enterBattleState should not throw with valid trainers");
    }

    // --- startTurn ---

    /*
     * CHECKS:  Battle.startTurn() runs to completion without throwing an exception
     *          when both trainers have valid lead Pokémon (smoke test).
     * HOW:     Adds one Pokémon each to both trainers, wraps the call in
     *          assertDoesNotThrow.
     * IMPROVE: Like enterBattleStateDoesNotThrow, this only checks for the absence of
     *          exceptions. Expanding to assert post-turn state (e.g., at least one
     *          Pokémon's HP changed, a move was selected) would convert this from a
     *          smoke test into a functional test.
     */
    @Test
    void startTurnDoesNotThrow() {
        player.addPokemonToTeam(fastPokemon);
        opponent.addPokemonToTeam(slowPokemon);

        assertDoesNotThrow(() -> Battle.startTurn(player, opponent),
                "startTurn should not throw with valid trainers");
    }

    // --- dealDamage + checkFainted integration ---

    /*
     * CHECKS:  Integration test: repeatedly dealing damage eventually causes the
     *          defender to faint (HP <= 0, isFainted flag = true).
     * HOW:     A level-100 attacker deals Psychic damage to a level-5 defender in a
     *          loop until checkFainted returns true, then asserts both HP <= 0 and
     *          getIsFainted() is true.
     * IMPROVE: The loop has no iteration cap, so a bug where HP never decreases would
     *          produce an infinite loop in CI rather than a timed-out test. Adding a
     *          maximum iteration count (e.g., 1000) and asserting fail() if the limit
     *          is reached would give a clearer failure message.
     */
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

    /*
     * CHECKS:  enterBattleState() should reject (throw IllegalStateException) when a
     *          trainer's entire team is fainted, preventing an invalid battle state.
     *          (Currently @Disabled because this validation is not yet implemented.)
     * HOW:     Faint a Pokémon, add it as the only team member, then assert that
     *          enterBattleState throws IllegalStateException.
     * IMPROVE: Implement team-health validation in enterBattleState and remove the
     *          @Disabled. Also test with a mixed team (some fainted, some healthy) to
     *          confirm the battle is allowed as long as at least one Pokémon is alive.
     */
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

    /*
     * CHECKS:  Baseline regression: dealDamage() does NOT decrement PP, because it
     *          accepts a raw Move object rather than a MoveSlot.
     * HOW:     Records PP before the call, calls dealDamage with the move from slot 0,
     *          and asserts PP is unchanged afterward.
     * IMPROVE: In a complete battle system, PP should be decremented when a move is
     *          used. Once the turn pipeline works with MoveSlot (Phase 3), this test
     *          should be inverted to assert PP decreases by 1.
     */
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

    /*
     * CHECKS:  A single massive-advantage hit (level 100 vs level 5) can faint the
     *          defender in one shot, confirming dealDamage and checkFainted work
     *          together end-to-end.
     * HOW:     Calls dealDamage once with a level-100 Abra attacking a level-5 Abra,
     *          then asserts checkFainted returns true.
     * IMPROVE: This test relies on the specific stat spread of Abra at both levels.
     *          If base stats are changed, a one-hit faint might no longer be guaranteed.
     *          Adding a comment explaining why a single hit is expected to faint the
     *          defender would help future maintainers understand the assumption.
     */
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

    /*
     * CHECKS:  checkFainted() is idempotent for an alive Pokémon: repeated calls
     *          always return false without corrupting state.
     * HOW:     Calls checkFainted 5 times on a healthy level-50 Abra, asserting false
     *          each time, then confirms the isFainted flag is still false.
     * IMPROVE: Also verify that other Pokémon state (HP, stats) is unchanged after
     *          multiple checkFainted calls, confirming the method has no unintended
     *          side effects.
     */
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

    /*
     * CHECKS:  checkFainted() is idempotent for a fainted Pokémon: repeated calls
     *          always return true without error or state corruption.
     * HOW:     Sets HP to 0, calls checkFainted 5 times asserting true each time,
     *          then confirms the isFainted flag remains true.
     * IMPROVE: Verify that HP remains at 0 (not driven further negative or reset)
     *          after multiple checkFainted calls. This guards against accidental
     *          state mutation inside checkFainted.
     */
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

    /*
     * CHECKS:  checkFainted() should clear the isFainted flag when HP is restored
     *          above 0 after a faint (the "revival" case). Currently @Disabled because
     *          this logic is not yet implemented.
     * HOW:     Faints a Pokémon (HP = 0), restores HP to 50, calls checkFainted again,
     *          and asserts isFainted is reset to false.
     * IMPROVE: Implement revival flag-clearing in checkFainted and remove @Disabled.
     *          Also test that a revived Pokémon can re-enter battle and deal damage,
     *          ensuring the full revival flow works end-to-end.
     */
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

    /*
     * CHECKS:  checkSpeed() uses the first Pokémon in the team (index 0) for the speed
     *          comparison, not a bench Pokémon or a team-wide average.
     * HOW:     Player's lead is Slowbro but has Electrode on the bench; opponent's lead
     *          is Electrode with Slowbro on the bench. Asserts opponent goes first
     *          because their lead Pokémon is faster.
     * IMPROVE: Also test with a three-Pokémon team where the fastest Pokémon is at
     *          index 2, confirming only index 0 is compared, not the fastest member.
     */
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

    /*
     * CHECKS:  dealDamage() never heals the defender: the defender's HP after the
     *          call is always <= HP before the call.
     * HOW:     Records HP before and after one dealDamage call with Psychic on equal-
     *          level Abra, then asserts hpAfter <= hpBefore.
     * IMPROVE: This test passes even if HP is unchanged (0 damage). Asserting
     *          hpAfter < hpBefore (strict decrease) would also verify that the move
     *          actually lands and deals damage, combining this check with
     *          dealDamage_alwaysDealsPositiveDamage.
     */
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
