package pokemonGame;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import static org.junit.jupiter.api.Assertions.*;
import java.util.stream.Stream;
import org.junit.jupiter.params.provider.Arguments;

import pokemonGame.mons.Abra;
import pokemonGame.mons.Bulbasaur;
import pokemonGame.mons.Electrode;
import pokemonGame.mons.Slowbro;
import pokemonGame.moves.*;

class AttackTest {

    private Attack attack;

    // --- Effectiveness ---

    /*
     * CHECKS:  calculateEffectiveness() returns 2.0f for a super-effective matchup
     * HOW: Uses a parameterized test with multiple super-effective pairs to verify that the type
     *  chart is correctly implemented for all known super-effective relationships, rather than relying on a single example.
     * 
     * 3/21/2026: Added parameterized test with multiple super-effective pairs to increase coverage 
     * and confidence in the type chart implementation.
     */

    static Stream<Arguments> provideSuperEffectivePairs() {
        return Stream.of(
            // Normal is super effective against nothing
            // Fire is super effective against Grass, Ice, Bug, and Steel
            Arguments.of("Grass", new Flamethrower()),
            Arguments.of("Ice", new Flamethrower()),
            Arguments.of("Bug", new Flamethrower()),
            Arguments.of("Steel", new Flamethrower()),
            // Water is super effective against Fire, Ground, and Rock
            Arguments.of("Fire", new Surf()),
            Arguments.of("Ground", new Surf()),
            Arguments.of("Rock", new Surf()),
            // Electric is super effective against Water and Flying
            Arguments.of("Water", new ThunderShock()),
            Arguments.of("Flying", new ThunderShock()),
            // Grass is super effective against Water, Ground, and Rock
            Arguments.of("Water", new RazorLeaf()),
            Arguments.of("Ground", new RazorLeaf()),
            Arguments.of("Rock", new RazorLeaf()),
            // Ice is super effective against Grass, Ground, Flying, and Dragon
            Arguments.of("Grass", new IceBeam()),
            Arguments.of("Ground", new IceBeam()),
            Arguments.of("Flying", new IceBeam()),
            Arguments.of("Dragon", new IceBeam()),
            // Fighting is super effective against Normal, Rock, Steel, Ice, and Dark
            Arguments.of("Normal", new Submission()), 
            Arguments.of("Rock", new Submission()), 
            Arguments.of("Steel", new Submission()), 
            Arguments.of("Ice", new Submission()),
            Arguments.of("Dark", new Submission()),
            // Poison is super effective against Grass and Fairy
            Arguments.of("Grass", new PoisonSting()),
            Arguments.of("Fairy", new PoisonSting()),
            // Ground is super effective against Fire, Electric, Poison, Rock, and Steel
            Arguments.of("Fire", new Dig()),
            Arguments.of("Poison", new Dig()),
            Arguments.of("Rock", new Dig()),
            Arguments.of("Steel", new Dig()),
            Arguments.of("Electric", new Dig()),
            // Flying is super effective against Fighting, Bug, and Grass
            Arguments.of("Fighting", new Fly()),
            Arguments.of("Bug", new Fly()),
            Arguments.of("Grass", new Fly()),
            // Psychic is super effective against Fighting and Poison
            Arguments.of("Fighting", new Psychic()),
            Arguments.of("Poison", new Psychic()),
            // Bug is super effective against Grass, Psychic, and Dark
            Arguments.of("Grass", new Twineedle()),
            Arguments.of("Psychic", new Twineedle()),
            Arguments.of("Dark", new Twineedle()),          
            // Rock is super effective against Fire, Ice, Flying, and Bug
            Arguments.of("Fire", new RockThrow()),
            Arguments.of("Ice", new RockThrow()),
            Arguments.of("Flying", new RockThrow()),
            Arguments.of("Bug", new RockThrow()),
            // Ghost is super effective against Psychic and Ghost
            Arguments.of("Psychic", new Lick()),
            Arguments.of("Ghost", new Lick()),
            // Dragon is super effective against Dragon
            Arguments.of("Dragon", new DragonRage())
            // Steel is super effective against Ice, Rock, and Fairy (No Steel Moves in Gen 1)
            // Arguments.of("Ice", new IronTail()),
            // Arguments.of("Rock", new IronTail()),
            // Arguments.of("Fairy", new IronTail()),
            // Dark is super effective against Psychic and Ghost (No Dark Moves in Gen 1)
            // Arguments.of("Psychic", new Bite()),
            // Arguments.of("Ghost", new Bite()),
            // Fairy is super effective against Fighting, Dragon, and Dark (No Fairy Moves in Gen 1)
            // Arguments.of("Fighting", new Moonblast()),
            // Arguments.of("Dragon", new Moonblast()),
            // Arguments.of("Dark", new Moonblast())
        );
    }

    @ParameterizedTest
    @MethodSource("provideSuperEffectivePairs")
    void superEffectiveReturns2(String defendingType, Move move) {
        float eff = attack.calculateEffectiveness(defendingType, move);
        assertEquals(2.0f, eff);
    }

    /*
     * CHECKS:  calculateEffectiveness() returns 0.5f for a resisted matchup
     * HOW: Uses a parameterized test with multiple not-very-effective pairs to verify that all known
     *  resistances are correctly implemented in the type chart, rather than relying on a single example.
     *
     * 3/21/2026: Added parameterized test with multiple not-very-effective pairs to increase coverage
     * and confidence in the type chart implementation.
     */

    static Stream<Arguments> provideNotVeryEffectivePairs() {
        return Stream.of(
            // Normal is not very effective against Rock and Steel
            Arguments.of("Rock", new Cut()),
            Arguments.of("Steel", new Cut()),
            // Fire is not very effective against Fire, Water, Rock, and Dragon
            Arguments.of("Fire", new Flamethrower()),
            Arguments.of("Water", new Flamethrower()),
            Arguments.of("Rock", new Flamethrower()),
            Arguments.of("Dragon", new Flamethrower()),
            // Water is not very effective against Water, Grass, and Dragon
            Arguments.of("Water", new Surf()),
            Arguments.of("Grass", new Surf()),
            Arguments.of("Dragon", new Surf()),
            // Electric is not very effective against Electric, Grass, and Dragon
            Arguments.of("Electric", new ThunderShock()),
            Arguments.of("Grass", new ThunderShock()),
            Arguments.of("Dragon", new ThunderShock()),
            // Grass is not very effective against Fire, Grass, Poison, Flying, Bug, Dragon, and Steel
            Arguments.of("Fire", new RazorLeaf()),
            Arguments.of("Grass", new RazorLeaf()),
            Arguments.of("Poison", new RazorLeaf()),
            Arguments.of("Flying", new RazorLeaf()),
            Arguments.of("Bug", new RazorLeaf()),
            Arguments.of("Dragon", new RazorLeaf()),
            Arguments.of("Steel", new RazorLeaf()),
            // Ice is not very effective against Fire, Water, Ice, and Steel
            Arguments.of("Fire", new IceBeam()),
            Arguments.of("Water", new IceBeam()),
            Arguments.of("Ice", new IceBeam()),
            Arguments.of("Steel", new IceBeam()),
            // Fighting is not very effective against Poison, Flying, Psychic, Bug, and Fairy
            Arguments.of("Poison", new Submission()),
            Arguments.of("Flying", new Submission()),
            Arguments.of("Psychic", new Submission()),
            Arguments.of("Bug", new Submission()),
            Arguments.of("Fairy", new Submission()),
            // Poison is not very effective against Poison, Ground, Rock, and Ghost
            Arguments.of("Poison", new PoisonSting()),
            Arguments.of("Ground", new PoisonSting()),
            Arguments.of("Rock", new PoisonSting()),
            Arguments.of("Ghost", new PoisonSting()),
            // Ground is not very effective against Bug and Grass
            Arguments.of("Bug", new Dig()),
            Arguments.of("Grass", new Dig()),
            // Flying is not very effective against Electric, Rock, and Steel
            Arguments.of("Electric", new Fly()),
            Arguments.of("Rock", new Fly()),
            Arguments.of("Steel", new Fly()),
            // Psychic is not very effective against Psychic and Steel
            Arguments.of("Psychic", new Psychic()),
            Arguments.of("Steel", new Psychic()),
            // Bug is not very effective against Fire, Fighting, Poison, Flying, Ghost, Steel, and Fairy
            Arguments.of("Fire", new Twineedle()),
            Arguments.of("Fighting", new Twineedle()),
            Arguments.of("Poison", new Twineedle()),
            Arguments.of("Flying", new Twineedle()),
            Arguments.of("Ghost", new Twineedle()),
            Arguments.of("Steel", new Twineedle()),
            Arguments.of("Fairy", new Twineedle()),
            // Rock is not very effective against Fighting, Ground, and Steel
            Arguments.of("Fighting", new RockThrow()),
            Arguments.of("Ground", new RockThrow()),
            Arguments.of("Steel", new RockThrow()),
            // Ghost is not very effective against Dark
            Arguments.of("Dark", new Lick()),
            // Dragon is not very effective against Steel
            Arguments.of("Steel", new DragonRage())
            // Dark is not very effective against Fighting, Dark, and Fairy (No Dark Moves in Gen 1)
            // Arguments.of("Fighting", new Bite()),
            // Arguments.of("Dark", new Bite()),
            // Arguments.of("Fairy", new Bite()),
            // Steel is not very effective against Fire, Water, Electric, and Steel (No Steel Moves in Gen 1)
            // Arguments.of("Fire", new IronTail()),
            // Arguments.of("Water", new IronTail()),
            // Arguments.of("Electric", new IronTail()),
            // Arguments.of("Steel", new IronTail()),
            // Fairy is not very effective against Fire, Poison, and Steel (No Fairy Moves in Gen 1)
            // Arguments.of("Fire", new Moonblast()),
            // Arguments.of("Poison", new Moonblast()),
            // Arguments.of("Steel", new Moonblast()
        );
    }
    
    @ParameterizedTest
    @MethodSource("provideNotVeryEffectivePairs")
    void notVeryEffectiveReturnsHalf(String defenderType, Move move) {
        float eff = attack.calculateEffectiveness(defenderType, move);
        assertEquals(0.5f, eff);
    }

    /*
     * CHECKS:  calculateEffectiveness() returns 0.0f when the attacking type is
     *          completely immune to the defending type.
     * HOW:     Uses a parameterized test with multiple immune pairs to verify that all
     *          known immunities are correctly implemented in the type chart.
     * 3/21/2026: Added multiple immune pairs to increase coverage and confidence in the type chart implementation.
     */

    static Stream<Arguments> provideImmunePairs() {
        return Stream.of(
            // Ghost is immune to Normal and Fighting
            Arguments.of("Ghost", new Cut()),
            Arguments.of("Ghost", new Submission()),
            // Normal is immune to Ghost
            Arguments.of("Normal", new Lick()),
            // Ground is immune to Electric
            Arguments.of("Ground", new ThunderShock()),
            // Flying is immune to Ground
            Arguments.of("Flying", new Dig()),
            // Dark is immune to Psychic
            Arguments.of("Dark", new Psychic()),
            // Steel is immune to Poison
            Arguments.of("Steel", new PoisonSting()),
            // Fairy is immune to Dragon
            Arguments.of("Fairy", new DragonRage())
        );
    }

    @ParameterizedTest
    @MethodSource("provideImmunePairs")
    void immunityReturnsZero(String defenderType, Move move) {
        float eff = attack.calculateEffectiveness(defenderType, move);
        assertEquals(0.0f, eff);
    }

    /*
     * CHECKS:  calculateEffectiveness() returns 1.0f for a neutral matchup where the
     *          attacking type has no special relationship with the defending type.
     * HOW:     Uses a parameterized test with multiple neutral pairs to verify that all
     *          known neutral interactions are correctly implemented in the type chart.
     *
     * 3/21/2026: Added multiple neutral pairs to increase coverage and confidence in the type chart implementation.
     */

    static Stream<Arguments> provideNeutralPairs() {
        return Stream.of(
            // Normal is neutral against all types except Rock, Steel, and Ghost
            Arguments.of("Fire", new Cut()),
            Arguments.of("Water", new Cut()),
            Arguments.of("Electric", new Cut()),
            Arguments.of("Grass", new Cut()),
            Arguments.of("Ice", new Cut()),
            Arguments.of("Fighting", new Cut()),
            Arguments.of("Poison", new Cut()),
            Arguments.of("Ground", new Cut()),
            Arguments.of("Flying", new Cut()),
            Arguments.of("Psychic", new Cut()),
            Arguments.of("Bug", new Cut()),
            Arguments.of("Dragon", new Cut()),
            Arguments.of("Dark", new Cut()),
            Arguments.of("Fairy", new Cut()),
            // Fire is neutral against Normal, Electric, Fighting, Poison, Flying, Psychic, Ghost, Dark, and Fairy
            Arguments.of("Normal", new Flamethrower()),
            Arguments.of("Electric", new Flamethrower()),
            Arguments.of("Fighting", new Flamethrower()),
            Arguments.of("Poison", new Flamethrower()),
            Arguments.of("Flying", new Flamethrower()),
            Arguments.of("Psychic", new Flamethrower()),
            Arguments.of("Ghost", new Flamethrower()),
            Arguments.of("Dark", new Flamethrower()),
            Arguments.of("Fairy", new Flamethrower()),
            // Water is neutral against Normal, Electric, Ice, Fighting, Poison, Flying, Psychic, Bug, Ghost, Dark, Steel, and Fairy
            Arguments.of("Normal", new Surf()),
            Arguments.of("Electric", new Surf()),
            Arguments.of("Ice", new Surf()),
            Arguments.of("Fighting", new Surf()),
            Arguments.of("Poison", new Surf()),
            Arguments.of("Flying", new Surf()),
            Arguments.of("Psychic", new Surf()),
            Arguments.of("Bug", new Surf()),
            Arguments.of("Ghost", new Surf()),
            Arguments.of("Dark", new Surf()),
            Arguments.of("Steel", new Surf()),
            Arguments.of("Fairy", new Surf()),
            // Electric is neutral against Normal, Fire, Ice, Fighting, Poison, Psychic, Bug, Rock, Ghost, Dark, Steel, and Fairy
            Arguments.of("Normal", new ThunderShock()),
            Arguments.of("Fire", new ThunderShock()),
            Arguments.of("Ice", new ThunderShock()),
            Arguments.of("Fighting", new ThunderShock()),
            Arguments.of("Poison", new ThunderShock()),
            Arguments.of("Psychic", new ThunderShock()),
            Arguments.of("Bug", new ThunderShock()),
            Arguments.of("Rock", new ThunderShock()),
            Arguments.of("Ghost", new ThunderShock()),
            Arguments.of("Dark", new ThunderShock()),
            Arguments.of("Steel", new ThunderShock()),
            Arguments.of("Fairy", new ThunderShock()),
            // Grass is neutral against Normal, Electric, Ice, Fighting, Psychic, Ghost, Dark, and Fairy
            Arguments.of("Normal", new RazorLeaf()),
            Arguments.of("Electric", new RazorLeaf()),
            Arguments.of("Ice", new RazorLeaf()),
            Arguments.of("Fighting", new RazorLeaf()),
            Arguments.of("Psychic", new RazorLeaf()),
            Arguments.of("Ghost", new RazorLeaf()),
            Arguments.of("Dark", new RazorLeaf()),
            Arguments.of("Fairy", new RazorLeaf()),
            // Ice is neutral against Normal, Electric, Fighting, Poison, Psychic, Bug, Rock, Ghost, Dark, and Fairy
            Arguments.of("Normal", new IceBeam()),
            Arguments.of("Electric", new IceBeam()),
            Arguments.of("Fighting", new IceBeam()),
            Arguments.of("Poison", new IceBeam()),
            Arguments.of("Psychic", new IceBeam()),
            Arguments.of("Bug", new IceBeam()),
            Arguments.of("Rock", new IceBeam()),
            Arguments.of("Ghost", new IceBeam()),
            Arguments.of("Dark", new IceBeam()),
            Arguments.of("Fairy", new IceBeam()),
            // Fighting is neutral against Fire, Water, Electric, Grass, Fighting, Ground, and Dragon
            Arguments.of("Fire", new Submission()),
            Arguments.of("Water", new Submission()),
            Arguments.of("Electric", new Submission()),
            Arguments.of("Grass", new Submission()),
            Arguments.of("Fighting", new Submission()),
            Arguments.of("Ground", new Submission()),
            Arguments.of("Dragon", new Submission()),
            // Poison is neutral against Normal, Fire, Water, Electric, Ice, Fighting, Flying, Psychic, Bug, Dragon, and Dark
            Arguments.of("Normal", new PoisonSting()),
            Arguments.of("Fire", new PoisonSting()),
            Arguments.of("Water", new PoisonSting()),
            Arguments.of("Electric", new PoisonSting()),
            Arguments.of("Ice", new PoisonSting()),
            Arguments.of("Fighting", new PoisonSting()),
            Arguments.of("Flying", new PoisonSting()),
            Arguments.of("Psychic", new PoisonSting()),
            Arguments.of("Bug", new PoisonSting()),
            Arguments.of("Dragon", new PoisonSting()),
            Arguments.of("Dark", new PoisonSting()),
            // Ground is neutral against Normal, Water, Ice, Fighting, Ground, Psychic, Ghost, Dragon, Dark, and Fairy
            Arguments.of("Normal", new Dig()),
            Arguments.of("Water", new Dig()),
            Arguments.of("Ice", new Dig()),
            Arguments.of("Fighting", new Dig()),
            Arguments.of("Ground", new Dig()),
            Arguments.of("Psychic", new Dig()),
            Arguments.of("Ghost", new Dig()),
            Arguments.of("Dragon", new Dig()),
            Arguments.of("Dark", new Dig()),
            Arguments.of("Fairy", new Dig()),
            // Flying is neutral against Normal, Fire, Water, Ice, Poison, Ground, Flying, Psychic, Ghost, Dragon, Dark, and Fairy
            Arguments.of("Normal", new Fly()),
            Arguments.of("Fire", new Fly()),
            Arguments.of("Water", new Fly()),
            Arguments.of("Ice", new Fly()),
            Arguments.of("Poison", new Fly()),
            Arguments.of("Ground", new Fly()),
            Arguments.of("Flying", new Fly()),
            Arguments.of("Psychic", new Fly()),
            Arguments.of("Ghost", new Fly()),
            Arguments.of("Dragon", new Fly()),
            Arguments.of("Dark", new Fly()),
            Arguments.of("Fairy", new Fly()),
            // Psychic is neutral against Normal, Fire, Water, Electric, Grass, Ice, Ground, Flying, Bug, Rock, Ghost, Dragon, and Fairy
            Arguments.of("Normal", new Psychic()),
            Arguments.of("Fire", new Psychic()),
            Arguments.of("Water", new Psychic()),
            Arguments.of("Electric", new Psychic()),
            Arguments.of("Grass", new Psychic()),
            Arguments.of("Ice", new Psychic()),
            Arguments.of("Ground", new Psychic()),
            Arguments.of("Flying", new Psychic()),
            Arguments.of("Bug", new Psychic()),
            Arguments.of("Rock", new Psychic()),
            Arguments.of("Ghost", new Psychic()),
            Arguments.of("Dragon", new Psychic()),
            Arguments.of("Fairy", new Psychic()),
            // Bug is neutral against Normal, Water, Electric, Ice, Ground, Bug, Rock, and Dragon
            Arguments.of("Normal", new Twineedle()),
            Arguments.of("Water", new Twineedle()),
            Arguments.of("Electric", new Twineedle()),
            Arguments.of("Ice", new Twineedle()),
            Arguments.of("Ground", new Twineedle()),
            Arguments.of("Bug", new Twineedle()),
            Arguments.of("Rock", new Twineedle()),
            Arguments.of("Dragon", new Twineedle()),
            // Rock is neutral against Normal, Water, Electric, Grass, Poison, Psychic, Rock, Ghost, Dragon, Dark, and Fairy
            Arguments.of("Normal", new RockThrow()),
            Arguments.of("Water", new RockThrow()),
            Arguments.of("Electric", new RockThrow()),
            Arguments.of("Grass", new RockThrow()),
            Arguments.of("Poison", new RockThrow()),
            Arguments.of("Psychic", new RockThrow()),
            Arguments.of("Rock", new RockThrow()),
            Arguments.of("Ghost", new RockThrow()),
            Arguments.of("Dragon", new RockThrow()),
            Arguments.of("Dark", new RockThrow()),
            Arguments.of("Fairy", new RockThrow()),
            // Ghost is neutral against Fire, Water, Electric, Grass, Ice, Fighting, Poison, Ground, Flying, Bug, Rock, Dragon, Steel, and Fairy
            Arguments.of("Fire", new Lick()),
            Arguments.of("Water", new Lick()),
            Arguments.of("Electric", new Lick()),
            Arguments.of("Grass", new Lick()),
            Arguments.of("Ice", new Lick()),
            Arguments.of("Fighting", new Lick()),
            Arguments.of("Poison", new Lick()),
            Arguments.of("Ground", new Lick()),
            Arguments.of("Flying", new Lick()),
            Arguments.of("Bug", new Lick()),
            Arguments.of("Rock", new Lick()),
            Arguments.of("Dragon", new Lick()),
            Arguments.of("Steel", new Lick()),
            Arguments.of("Fairy", new Lick()),
            // Dragon is neutral against Normal, Fire, Water, Electric, Grass, Ice, Fighting, Poison, Ground, Flying Psychic, Bug, Rock, Ghost, and Dark
            Arguments.of("Normal", new DragonRage()),
            Arguments.of("Fire", new DragonRage()),
            Arguments.of("Water", new DragonRage()),
            Arguments.of("Electric", new DragonRage()),
            Arguments.of("Grass", new DragonRage()),
            Arguments.of("Ice", new DragonRage()),
            Arguments.of("Fighting", new DragonRage()),
            Arguments.of("Poison", new DragonRage()),
            Arguments.of("Ground", new DragonRage()),
            Arguments.of("Flying", new DragonRage()),
            Arguments.of("Psychic", new DragonRage()),
            Arguments.of("Bug", new DragonRage()),
            Arguments.of("Rock", new DragonRage()),
            Arguments.of("Ghost", new DragonRage()),
            Arguments.of("Dark", new DragonRage()),
            // Dark is neutral against Normal, Fire, Water, Electric, Grass, Ice, Poison, Ground, Flying, Bug, Rock, Dragon, and Steel
            //Arguments.of("Normal", new Bite()),
            //Arguments.of("Fire", new Bite()),
            //Arguments.of("Water", new Bite()),
            //Arguments.of("Electric", new Bite()),
            //Arguments.of("Grass", new Bite()),
            //Arguments.of("Ice", new Bite()),
            //Arguments.of("Poison", new Bite()),
            //Arguments.of("Ground", new Bite()),
            //Arguments.of("Flying", new Bite()),
            //Arguments.of("Bug", new Bite()),
            //Arguments.of("Rock", new Bite()),
            //Arguments.of("Dragon", new Bite()),
            //Arguments.of("Steel", new Bite()),
            // Steel is neutral against Normal, Grass, Fighting, Poison, Ground, Flying, Psychic, Bug, Ghost, Dragon, and Dark
            // Arguments.of("Normal", new IronTail()),
            // Arguments.of("Grass", new IronTail()),
            // Arguments.of("Fighting", new IronTail()),
            // Arguments.of("Poison", new IronTail()),
            // Arguments.of("Ground", new IronTail()),
            // Arguments.of("Flying", new IronTail()),
            // Arguments.of("Psychic", new IronTail()),
            // Arguments.of("Bug", new IronTail()),
            // Arguments.of("Ghost", new IronTail()),
            // Arguments.of("Dragon", new IronTail()),
            // Arguments.of("Dark", new IronTail()),
            // Fairy is neutral against Normal, Water, Electric, Ice, Ground, Flying, Psychic, Bug, Rock, Ghost, and Fairy
            // Arguments.of("Normal", new Moonblast()),
            // Arguments.of("Water", new Moonblast()),
            // Arguments.of("Electric", new Moonblast()),
            // Arguments.of("Ice", new Moonblast()),
            // Arguments.of("Ground", new Moonblast()),
            // Arguments.of("Flying", new Moonblast()),
            // Arguments.of("Psychic", new Moonblast()),
            // Arguments.of("Bug", new Moonblast()),
            // Arguments.of("Rock", new Moonblast()),
            // Arguments.of("Ghost", new Moonblast()),
            // Arguments.of("Fairy", new Moonblast()),
            // Unrecognized type should default to neutral
            Arguments.of(null, new MegaPunch())
        );
    }

    @ParameterizedTest
    @MethodSource("provideNeutralPairs")
    void neutralEffectivenessReturns1(String defenderType, Move move) {
        float eff = attack.calculateEffectiveness(defenderType, move);
        assertEquals(1.0f, eff);
    }

    /*
     * CHECKS:  calculateEffectiveness() handles a null defending type gracefully,
     *          returning 1.0f (neutral) instead of throwing a NullPointerException.
     * HOW:     Passes null as the defender type alongside a Psychic move and asserts
     *          the result is 1.0f.
     * IMPROVE: Consider whether null is a valid state at all. If the domain guarantees
     *          types are never null, the contract could be tightened to assert that an
     *          exception is thrown, making the null-handling policy explicit.
     */
    @Test
    void nullDefenderTypeReturnsNeutral() {
        Move psychic = new Psychic();
        float eff = attack.calculateEffectiveness(null, psychic);
        assertEquals(1.0f, eff,
                "Null defender type should be treated as neutral (1.0)");
    }

    /*
     * CHECKS:  calculateEffectiveness() treats the sentinel string "None" (used for a
     *          missing secondary type) as neutral, returning 1.0f.
     * HOW:     Passes "None" as the defender type and asserts the result is 1.0f.
     * IMPROVE: Define "None" as a named constant rather than a magic string so this
     *          test is not fragile to renaming. Also verify that other edge-case
     *          strings (empty string, whitespace) behave consistently.
     */
    @Test
    void noneDefenderTypeReturnsNeutral() {
        Move psychic = new Psychic();
        float eff = attack.calculateEffectiveness("None", psychic);
        assertEquals(1.0f, eff,
                "\"None\" defender type should be treated as neutral (1.0)");
    }

    // --- Damage calculation ---

    /*
     * CHECKS:  calculateDamage() never returns a negative value, even for a single
     *          call between equal-level opponents.
     * HOW:     Creates two level-50 Abra instances, calls calculateDamage once with a
     *          Psychic move, and asserts the result is >= 0.
     * IMPROVE: A single trial may theoretically produce 0 (e.g., if a miss mechanic
     *          is added). Running multiple trials and asserting all results are >= 0
     *          would give stronger assurance. Also test a high-defense target to
     *          confirm no integer underflow occurs in the formula.
     */
    @Test
    void damageIsNonNegative() {
        Pokemon attacker = new Abra("Attacker");
        Pokemon defender = new Abra("Defender");
        attacker.setLevel(50);
        defender.setLevel(50);

        int damage = attack.calculateDamage(attacker, defender, new Psychic());
        assertTrue(damage >= 0, "Damage should never be negative");
    }

    /*
     * CHECKS:  A higher-level attacker deals more average damage than a lower-level
     *          attacker against the same defender using the same move.
     * HOW:     Runs 50 trials each for a level-10 and a level-80 Abra, sums total
     *          damage for each, and asserts the high-level total exceeds the low-level
     *          total.
     * IMPROVE: 50 trials may occasionally fail due to the random damage roll (statistically
     *          flaky). Increasing to 500+ trials or seeding the RNG (if supported) makes
     *          the test deterministic and eliminates false negatives.
     */
    @Test
    void higherLevelDealsMoreDamage() {
        Pokemon lowLevel = new Abra("Low");
        Pokemon highLevel = new Abra("High");
        Pokemon defender = new Abra("Defender");
        defender.setLevel(50);
        lowLevel.setLevel(10);
        highLevel.setLevel(80);

        Move psychic = new Psychic();

        // Run multiple times to account for random factor, check averages
        int lowTotal = 0;
        int highTotal = 0;
        int trials = 50;
        for (int i = 0; i < trials; i++) {
            lowTotal += attack.calculateDamage(lowLevel, defender, psychic);
            highTotal += attack.calculateDamage(highLevel, defender, psychic);
        }
        assertTrue(highTotal > lowTotal,
                "Higher level Pokémon should deal more damage on average");
    }

    /*
     * CHECKS:  STAB (Same-Type Attack Bonus) increases average damage when the
     *          attacker's type matches the move's type (Abra + Psychic = STAB)
     *          compared to a non-STAB attacker of equal base Special Attack.
     * HOW:     Normalizes Bulbasaur's base SpAtk to match Abra's and applies the same
     *          nature, then runs 100 trials each and asserts the STAB total is higher.
     * IMPROVE: Add an assertion before the loop confirming both Pokémon have identical
     *          getCurrentSpecialAttack() values, making the stat-normalization
     *          precondition explicit and catching setup bugs immediately.
     */
    @Test
    void stabIncreasesTotalDamage() {
        // Abra is Psychic type; Psychic is a Psychic-type move → STAB applies
        Pokemon abraAttacker = new Abra("STAB Attacker");
        // Bulbasaur is Grass/Poison type; Psychic is not Grass or Poison → no STAB
        Pokemon bulbaAttacker = new Bulbasaur("No STAB Attacker");
        Pokemon defender = new Abra("Defender");
        abraAttacker.setLevel(50);
        bulbaAttacker.setLevel(50);
        // Give both the same base special attack so we isolate the STAB effect
        bulbaAttacker.setSpecialAttackBase(abraAttacker.getSpecialAttackBaseStat());
        // Use the same nature to be fair
        bulbaAttacker.setNature(abraAttacker.getNature());
        bulbaAttacker.calculateCurrentStats();
        defender.setLevel(50);

        Move psychic = new Psychic();
        int stabTotal = 0;
        int noStabTotal = 0;
        int trials = 100;
        for (int i = 0; i < trials; i++) {
            stabTotal += attack.calculateDamage(abraAttacker, defender, psychic);
            noStabTotal += attack.calculateDamage(bulbaAttacker, defender, psychic);
        }
        assertTrue(stabTotal > noStabTotal,
                "STAB should increase damage on average (STAB total=" + stabTotal
                + " vs non-STAB total=" + noStabTotal + ")");
    }

    /*
     * CHECKS:  Super-effective hits (Psychic vs Poison/Bulbasaur = 2.0x) deal more
     *          average damage than resisted hits (Psychic vs Psychic/Abra = 0.5x)
     *          from the same attacker.
     * HOW:     Runs 100 trials against a weak target (Bulbasaur) and a resistant target
     *          (Abra), then asserts superEffTotal > resistTotal.
     * IMPROVE: The effectiveness ratio is 4:1 (2.0 vs 0.5). Asserting
     *          superEffTotal > 3 * resistTotal instead of merely > resistTotal would
     *          verify the full magnitude of the difference, catching bugs where the
     *          multiplier is applied partially rather than fully.
     */
    @Test
    void superEffectiveDealMoreDamage() {
        // Psychic vs Poison (Bulbasaur) = 2.0x effective
        // Psychic vs Psychic (Abra) = 0.5x effective
        Pokemon attacker = new Abra("Attacker");
        Pokemon weakDefender = new Bulbasaur("Weak Defender");
        Pokemon resistDefender = new Abra("Resist Defender");
        attacker.setLevel(50);
        weakDefender.setLevel(50);
        resistDefender.setLevel(50);

        Move psychic = new Psychic();
        int superEffTotal = 0;
        int resistTotal = 0;
        int trials = 100;
        for (int i = 0; i < trials; i++) {
            superEffTotal += attack.calculateDamage(attacker, weakDefender, psychic);
            resistTotal += attack.calculateDamage(attacker, resistDefender, psychic);
        }
        assertTrue(superEffTotal > resistTotal,
                "Super-effective hits should deal more damage on average");
    }

    // --- Random int helper ---

    /*
     * CHECKS:  randomInt(min, max) always returns a value within the inclusive range
     *          [min, max].
     * HOW:     Calls randomInt(1, 10) 100 times and asserts each result is >= 1 and
     *          <= 10.
     * IMPROVE: 100 trials do not guarantee full range coverage. A complementary test
     *          should verify that all values in [min, max] are actually reachable,
     *          ensuring the RNG is not accidentally narrowed or biased toward one end.
     *          Also test that randomInt with min > max either throws or is documented.
     */
    @Test
    void randomIntWithinBounds() {
        for (int i = 0; i < 100; i++) {
            int val = attack.randomInt(1, 10);
            assertTrue(val >= 1 && val <= 10,
                    "randomInt(1, 10) returned " + val + ", expected 1-10");
        }
    }

    /*
     * CHECKS:  When min == max, randomInt() always returns that single value (the
     *          degenerate case of a range with exactly one element).
     * HOW:     Calls randomInt(5, 5) 20 times and asserts every result equals 5.
     * IMPROVE: Also test the inverted range (min > max), e.g. randomInt(10, 1), to
     *          confirm the method either throws an IllegalArgumentException or handles
     *          it gracefully rather than silently returning a misleading result.
     */
    @Test
    void randomIntSingleValueRange() {
        // When min == max, should always return that value
        for (int i = 0; i < 20; i++) {
            assertEquals(5, attack.randomInt(5, 5));
        }
    }

    // --- Critical hits ---

    /*
     * CHECKS:  calculateCriticalHit() completes without throwing an exception
     *          (smoke test — the boolean assertion inside is intentionally tautological;
     *          see inline comment for full explanation).
     * HOW:     Calls calculateCriticalHit on two level-50 Abra instances and uses a
     *          tautological assertTrue(crit || !crit) to satisfy JUnit's assertion
     *          requirement while letting the real check be "no exception thrown".
     * IMPROVE: Replace with a deterministic test by mocking or seeding the RNG to
     *          force a crit and then a non-crit, verifying each branch of the crit
     *          logic independently rather than relying on a smoke test.
     */
    @Test
    void criticalHitReturnsBooleanWithoutError() {
        // SMOKE TEST: The real purpose is to verify calculateCriticalHit runs
        // without throwing an exception. The assertion below is intentionally
        // tautological — (crit || !crit) is always true. We use it because
        // JUnit requires at least one assertion, and the meaningful check is
        // "no exception was thrown during execution."
        Pokemon attacker = new Abra("Attacker");
        Pokemon defender = new Abra("Defender");
        attacker.setLevel(50);
        defender.setLevel(50);
        boolean crit = attack.calculateCriticalHit(attacker, defender);
        assertTrue(crit || !crit); // tautology — the real test is no exception above
    }

    /*
     * CHECKS:  A Pokemon with much higher speed (Electrode, 150 base speed) has a
     *          statistically higher critical-hit rate than a slower one (Slowbro,
     *          30 base speed) over many trials.
     * HOW:     Runs 5 000 trials for each direction (fast attacks slow, slow attacks
     *          fast) and asserts the fast attacker's crit count is higher.
     * IMPROVE: 5 000 trials can still produce a close result due to random variance,
     *          making the test occasionally flaky. Increasing to 10 000+ trials or
     *          using a statistical confidence test (Z-test) would reduce flakiness.
     */
    @Test
    void critChanceHigherWhenFaster() {
        // Electrode has 150 base speed, Slowbro has 30 base speed
        // With a big speed advantage, crits should happen more often
        Pokemon fast = new Electrode("Fast");
        Pokemon slow = new Slowbro("Slow");
        fast.setLevel(50);
        slow.setLevel(50);

        int fastCrits = 0;
        int slowCrits = 0;
        int trials = 5000;
        for (int i = 0; i < trials; i++) {
            if (attack.calculateCriticalHit(fast, slow)) fastCrits++;
            if (attack.calculateCriticalHit(slow, fast)) slowCrits++;
        }
        assertTrue(fastCrits > slowCrits,
                "Faster Pokémon should crit more often (fast=" + fastCrits
                + " vs slow=" + slowCrits + " out of " + trials + ")");
    }

    /*
     * CHECKS:  The critical-hit rate never greatly exceeds the 15% formula cap, even
     *          with the maximum possible speed advantage.
     * HOW:     Counts crits over 10 000 trials with a level-100 Electrode vs a level-5
     *          Slowbro, then asserts the observed rate is <= 17% (a small tolerance
     *          above the 15% cap to account for normal statistical variation).
     * IMPROVE: The 17% threshold is somewhat arbitrary. A chi-squared or binomial
     *          confidence-interval test would provide a more statistically rigorous
     *          bound, reducing the risk of both false positives and false negatives.
     */
    @Test
    void critChanceNeverExceedsCap() {
        // The crit formula caps at 15% (1500/10000). Over 10,000 trials, the
        // observed rate should stay near 15%. We allow up to 17% to account
        // for normal statistical fluctuation, but anything higher than that
        // suggests the cap logic is broken.
        // (Previously this threshold was 20%, which was too loose — a 5%
        // margin above a 15% cap could hide real bugs.)
        Pokemon fast = new Electrode("Fast");
        Pokemon slow = new Slowbro("Slow");
        fast.setLevel(100);
        slow.setLevel(5);

        int crits = 0;
        int trials = 10000;
        for (int i = 0; i < trials; i++) {
            if (attack.calculateCriticalHit(fast, slow)) crits++;
        }
        double critRate = (double) crits / trials;
        assertTrue(critRate <= 0.17,
                "Crit rate should not greatly exceed 15% cap, got " + (critRate * 100) + "%");
    }
}
