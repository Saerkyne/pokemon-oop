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
import pokemonGame.TypeChart.Type;

class AttackTest {

    

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
            Arguments.of(Type.GRASS, new Flamethrower()),
            Arguments.of(Type.ICE, new Flamethrower()),
            Arguments.of(Type.BUG, new Flamethrower()),
            Arguments.of(Type.STEEL, new Flamethrower()),
            // Water is super effective against Fire, Ground, and Rock
            Arguments.of(Type.FIRE, new Surf()),
            Arguments.of(Type.GROUND, new Surf()),
            Arguments.of(Type.ROCK, new Surf()),
            // Electric is super effective against Water and Flying
            Arguments.of(Type.WATER, new ThunderShock()),
            Arguments.of(Type.FLYING, new ThunderShock()),
            // Grass is super effective against Water, Ground, and Rock
            Arguments.of(Type.WATER, new RazorLeaf()),
            Arguments.of(Type.GROUND, new RazorLeaf()),
            Arguments.of(Type.ROCK, new RazorLeaf()),
            // Ice is super effective against Grass, Ground, Flying, and Dragon
            Arguments.of(Type.GRASS, new IceBeam()),
            Arguments.of(Type.GROUND, new IceBeam()),
            Arguments.of(Type.FLYING, new IceBeam()),
            Arguments.of(Type.DRAGON, new IceBeam()),
            // Fighting is super effective against Normal, Rock, Steel, Ice, and Dark
            Arguments.of(Type.NORMAL, new Submission()), 
            Arguments.of(Type.ROCK, new Submission()), 
            Arguments.of(Type.STEEL, new Submission()), 
            Arguments.of(Type.ICE, new Submission()),
            Arguments.of(Type.DARK, new Submission()),
            // Poison is super effective against Grass and Fairy
            Arguments.of(Type.GRASS, new PoisonSting()),
            Arguments.of(Type.FAIRY, new PoisonSting()),
            // Ground is super effective against Fire, Electric, Poison, Rock, and Steel
            Arguments.of(Type.FIRE, new Dig()),
            Arguments.of(Type.POISON, new Dig()),
            Arguments.of(Type.ROCK, new Dig()),
            Arguments.of(Type.STEEL, new Dig()),
            Arguments.of(Type.ELECTRIC, new Dig()),
            // Flying is super effective against Fighting, Bug, and Grass
            Arguments.of(Type.FIGHTING, new Fly()),
            Arguments.of(Type.BUG, new Fly()),
            Arguments.of(Type.GRASS, new Fly()),
            // Psychic is super effective against Fighting and Poison
            Arguments.of(Type.FIGHTING, new Psychic()),
            Arguments.of(Type.POISON, new Psychic()),
            // Bug is super effective against Grass, Psychic, and Dark
            Arguments.of(Type.GRASS, new Twineedle()),
            Arguments.of(Type.PSYCHIC, new Twineedle()),
            Arguments.of(Type.DARK, new Twineedle()),          
            // Rock is super effective against Fire, Ice, Flying, and Bug
            Arguments.of(Type.FIRE, new RockThrow()),
            Arguments.of(Type.ICE, new RockThrow()),
            Arguments.of(Type.FLYING, new RockThrow()),
            Arguments.of(Type.BUG, new RockThrow()),
            // Ghost is super effective against Psychic and Ghost
            Arguments.of(Type.PSYCHIC, new Lick()),
            Arguments.of(Type.GHOST, new Lick()),
            // Dragon is super effective against Dragon
            Arguments.of(Type.DRAGON, new DragonRage())
            // Steel is super effective against Ice, Rock, and Fairy (No Steel Moves in Gen 1)
            // Arguments.of(Type.ICE, new IronTail()),
            // Arguments.of(Type.ROCK, new IronTail()),
            // Arguments.of(Type.FAIRY, new IronTail()),
            // Dark is super effective against Psychic and Ghost (No Dark Moves in Gen 1)
            // Arguments.of(Type.PSYCHIC, new Bite()),
            // Arguments.of(Type.GHOST, new Bite()),
            // Fairy is super effective against Fighting, Dragon, and Dark (No Fairy Moves in Gen 1)
            // Arguments.of(Type.FIGHTING, new Moonblast()),
            // Arguments.of(Type.DRAGON, new Moonblast()),
            // Arguments.of(Type.DARK, new Moonblast())
        );
    }

    @ParameterizedTest
    @MethodSource("provideSuperEffectivePairs")
    void superEffectiveReturns2(Type defendingType, Move move) {
        float eff = Attack.calculateEffectiveness(defendingType, move);
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
            Arguments.of(Type.ROCK, new Cut()),
            Arguments.of(Type.STEEL, new Cut()),
            // Fire is not very effective against Fire, Water, Rock, and Dragon
            Arguments.of(Type.FIRE, new Flamethrower()),
            Arguments.of(Type.WATER, new Flamethrower()),
            Arguments.of(Type.ROCK, new Flamethrower()),
            Arguments.of(Type.DRAGON, new Flamethrower()),
            // Water is not very effective against Water, Grass, and Dragon
            Arguments.of(Type.WATER, new Surf()),
            Arguments.of(Type.GRASS, new Surf()),
            Arguments.of(Type.DRAGON, new Surf()),
            // Electric is not very effective against Electric, Grass, and Dragon
            Arguments.of(Type.ELECTRIC, new ThunderShock()),
            Arguments.of(Type.GRASS, new ThunderShock()),
            Arguments.of(Type.DRAGON, new ThunderShock()),
            // Grass is not very effective against Fire, Grass, Poison, Flying, Bug, Dragon, and Steel
            Arguments.of(Type.FIRE, new RazorLeaf()),
            Arguments.of(Type.GRASS, new RazorLeaf()),
            Arguments.of(Type.POISON, new RazorLeaf()),
            Arguments.of(Type.FLYING, new RazorLeaf()),
            Arguments.of(Type.BUG, new RazorLeaf()),
            Arguments.of(Type.DRAGON, new RazorLeaf()),
            Arguments.of(Type.STEEL, new RazorLeaf()),
            // Ice is not very effective against Fire, Water, Ice, and Steel
            Arguments.of(Type.FIRE, new IceBeam()),
            Arguments.of(Type.WATER, new IceBeam()),
            Arguments.of(Type.ICE, new IceBeam()),
            Arguments.of(Type.STEEL, new IceBeam()),
            // Fighting is not very effective against Poison, Flying, Psychic, Bug, and Fairy
            Arguments.of(Type.POISON, new Submission()),
            Arguments.of(Type.FLYING, new Submission()),
            Arguments.of(Type.PSYCHIC, new Submission()),
            Arguments.of(Type.BUG, new Submission()),
            Arguments.of(Type.FAIRY, new Submission()),
            // Poison is not very effective against Poison, Ground, Rock, and Ghost
            Arguments.of(Type.POISON, new PoisonSting()),
            Arguments.of(Type.GROUND, new PoisonSting()),
            Arguments.of(Type.ROCK, new PoisonSting()),
            Arguments.of(Type.GHOST, new PoisonSting()),
            // Ground is not very effective against Bug and Grass
            Arguments.of(Type.BUG, new Dig()),
            Arguments.of(Type.GRASS, new Dig()),
            // Flying is not very effective against Electric, Rock, and Steel
            Arguments.of(Type.ELECTRIC, new Fly()),
            Arguments.of(Type.ROCK, new Fly()),
            Arguments.of(Type.STEEL, new Fly()),
            // Psychic is not very effective against Psychic and Steel
            Arguments.of(Type.PSYCHIC, new Psychic()),
            Arguments.of(Type.STEEL, new Psychic()),
            // Bug is not very effective against Fire, Fighting, Poison, Flying, Ghost, Steel, and Fairy
            Arguments.of(Type.FIRE, new Twineedle()),
            Arguments.of(Type.FIGHTING, new Twineedle()),
            Arguments.of(Type.POISON, new Twineedle()),
            Arguments.of(Type.FLYING, new Twineedle()),
            Arguments.of(Type.GHOST, new Twineedle()),
            Arguments.of(Type.STEEL, new Twineedle()),
            Arguments.of(Type.FAIRY, new Twineedle()),
            // Rock is not very effective against Fighting, Ground, and Steel
            Arguments.of(Type.FIGHTING, new RockThrow()),
            Arguments.of(Type.GROUND, new RockThrow()),
            Arguments.of(Type.STEEL, new RockThrow()),
            // Ghost is not very effective against Dark
            Arguments.of(Type.DARK, new Lick()),
            // Dragon is not very effective against Steel
            Arguments.of(Type.STEEL, new DragonRage())
            // Dark is not very effective against Fighting, Dark, and Fairy (No Dark Moves in Gen 1)
            // Arguments.of(Type.FIGHTING, new Bite()),
            // Arguments.of(Type.DARK, new Bite()),
            // Arguments.of(Type.FAIRY, new Bite()),
            // Steel is not very effective against Fire, Water, Electric, and Steel (No Steel Moves in Gen 1)
            // Arguments.of(Type.FIRE, new IronTail()),
            // Arguments.of(Type.WATER, new IronTail()),
            // Arguments.of(Type.ELECTRIC, new IronTail()),
            // Arguments.of(Type.STEEL, new IronTail()),
            // Fairy is not very effective against Fire, Poison, and Steel (No Fairy Moves in Gen 1)
            // Arguments.of(Type.FIRE, new Moonblast()),
            // Arguments.of(Type.POISON, new Moonblast()),
            // Arguments.of(Type.STEEL, new Moonblast()
        );
    }
    
    @ParameterizedTest
    @MethodSource("provideNotVeryEffectivePairs")
    void notVeryEffectiveReturnsHalf(Type defenderType, Move move) {
        float eff = Attack.calculateEffectiveness(defenderType, move);
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
            Arguments.of(Type.GHOST, new Cut()),
            Arguments.of(Type.GHOST, new Submission()),
            // Normal is immune to Ghost
            Arguments.of(Type.NORMAL, new Lick()),
            // Ground is immune to Electric
            Arguments.of(Type.GROUND, new ThunderShock()),
            // Flying is immune to Ground
            Arguments.of(Type.FLYING, new Dig()),
            // Dark is immune to Psychic
            Arguments.of(Type.DARK, new Psychic()),
            // Steel is immune to Poison
            Arguments.of(Type.STEEL, new PoisonSting()),
            // Fairy is immune to Dragon
            Arguments.of(Type.FAIRY, new DragonRage())
        );
    }

    @ParameterizedTest
    @MethodSource("provideImmunePairs")
    void immunityReturnsZero(Type defenderType, Move move) {
        float eff = Attack.calculateEffectiveness(defenderType, move);
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
            Arguments.of(Type.FIRE, new Cut()),
            Arguments.of(Type.WATER, new Cut()),
            Arguments.of(Type.ELECTRIC, new Cut()),
            Arguments.of(Type.GRASS, new Cut()),
            Arguments.of(Type.ICE, new Cut()),
            Arguments.of(Type.FIGHTING, new Cut()),
            Arguments.of(Type.POISON, new Cut()),
            Arguments.of(Type.GROUND, new Cut()),
            Arguments.of(Type.FLYING, new Cut()),
            Arguments.of(Type.PSYCHIC, new Cut()),
            Arguments.of(Type.BUG, new Cut()),
            Arguments.of(Type.DRAGON, new Cut()),
            Arguments.of(Type.DARK, new Cut()),
            Arguments.of(Type.FAIRY, new Cut()),
            // Fire is neutral against Normal, Electric, Fighting, Poison, Flying, Psychic, Ghost, Dark, and Fairy
            Arguments.of(Type.NORMAL, new Flamethrower()),
            Arguments.of(Type.ELECTRIC, new Flamethrower()),
            Arguments.of(Type.FIGHTING, new Flamethrower()),
            Arguments.of(Type.POISON, new Flamethrower()),
            Arguments.of(Type.FLYING, new Flamethrower()),
            Arguments.of(Type.PSYCHIC, new Flamethrower()),
            Arguments.of(Type.GHOST, new Flamethrower()),
            Arguments.of(Type.DARK, new Flamethrower()),
            Arguments.of(Type.FAIRY, new Flamethrower()),
            // Water is neutral against Normal, Electric, Ice, Fighting, Poison, Flying, Psychic, Bug, Ghost, Dark, Steel, and Fairy
            Arguments.of(Type.NORMAL, new Surf()),
            Arguments.of(Type.ELECTRIC, new Surf()),
            Arguments.of(Type.ICE, new Surf()),
            Arguments.of(Type.FIGHTING, new Surf()),
            Arguments.of(Type.POISON, new Surf()),
            Arguments.of(Type.FLYING, new Surf()),
            Arguments.of(Type.PSYCHIC, new Surf()),
            Arguments.of(Type.BUG, new Surf()),
            Arguments.of(Type.GHOST, new Surf()),
            Arguments.of(Type.DARK, new Surf()),
            Arguments.of(Type.STEEL, new Surf()),
            Arguments.of(Type.FAIRY, new Surf()),
            // Electric is neutral against Normal, Fire, Ice, Fighting, Poison, Psychic, Bug, Rock, Ghost, Dark, Steel, and Fairy
            Arguments.of(Type.NORMAL, new ThunderShock()),
            Arguments.of(Type.FIRE, new ThunderShock()),
            Arguments.of(Type.ICE, new ThunderShock()),
            Arguments.of(Type.FIGHTING, new ThunderShock()),
            Arguments.of(Type.POISON, new ThunderShock()),
            Arguments.of(Type.PSYCHIC, new ThunderShock()),
            Arguments.of(Type.BUG, new ThunderShock()),
            Arguments.of(Type.ROCK, new ThunderShock()),
            Arguments.of(Type.GHOST, new ThunderShock()),
            Arguments.of(Type.DARK, new ThunderShock()),
            Arguments.of(Type.STEEL, new ThunderShock()),
            Arguments.of(Type.FAIRY, new ThunderShock()),
            // Grass is neutral against Normal, Electric, Ice, Fighting, Psychic, Ghost, Dark, and Fairy
            Arguments.of(Type.NORMAL, new RazorLeaf()),
            Arguments.of(Type.ELECTRIC, new RazorLeaf()),
            Arguments.of(Type.ICE, new RazorLeaf()),
            Arguments.of(Type.FIGHTING, new RazorLeaf()),
            Arguments.of(Type.PSYCHIC, new RazorLeaf()),
            Arguments.of(Type.GHOST, new RazorLeaf()),
            Arguments.of(Type.DARK, new RazorLeaf()),
            Arguments.of(Type.FAIRY, new RazorLeaf()),
            // Ice is neutral against Normal, Electric, Fighting, Poison, Psychic, Bug, Rock, Ghost, Dark, and Fairy
            Arguments.of(Type.NORMAL, new IceBeam()),
            Arguments.of(Type.ELECTRIC, new IceBeam()),
            Arguments.of(Type.FIGHTING, new IceBeam()),
            Arguments.of(Type.POISON, new IceBeam()),
            Arguments.of(Type.PSYCHIC, new IceBeam()),
            Arguments.of(Type.BUG, new IceBeam()),
            Arguments.of(Type.ROCK, new IceBeam()),
            Arguments.of(Type.GHOST, new IceBeam()),
            Arguments.of(Type.DARK, new IceBeam()),
            Arguments.of(Type.FAIRY, new IceBeam()),
            // Fighting is neutral against Fire, Water, Electric, Grass, Fighting, Ground, and Dragon
            Arguments.of(Type.FIRE, new Submission()),
            Arguments.of(Type.WATER, new Submission()),
            Arguments.of(Type.ELECTRIC, new Submission()),
            Arguments.of(Type.GRASS, new Submission()),
            Arguments.of(Type.FIGHTING, new Submission()),
            Arguments.of(Type.GROUND, new Submission()),
            Arguments.of(Type.DRAGON, new Submission()),
            // Poison is neutral against Normal, Fire, Water, Electric, Ice, Fighting, Flying, Psychic, Bug, Dragon, and Dark
            Arguments.of(Type.NORMAL, new PoisonSting()),
            Arguments.of(Type.FIRE, new PoisonSting()),
            Arguments.of(Type.WATER, new PoisonSting()),
            Arguments.of(Type.ELECTRIC, new PoisonSting()),
            Arguments.of(Type.ICE, new PoisonSting()),
            Arguments.of(Type.FIGHTING, new PoisonSting()),
            Arguments.of(Type.FLYING, new PoisonSting()),
            Arguments.of(Type.PSYCHIC, new PoisonSting()),
            Arguments.of(Type.BUG, new PoisonSting()),
            Arguments.of(Type.DRAGON, new PoisonSting()),
            Arguments.of(Type.DARK, new PoisonSting()),
            // Ground is neutral against Normal, Water, Ice, Fighting, Ground, Psychic, Ghost, Dragon, Dark, and Fairy
            Arguments.of(Type.NORMAL, new Dig()),
            Arguments.of(Type.WATER, new Dig()),
            Arguments.of(Type.ICE, new Dig()),
            Arguments.of(Type.FIGHTING, new Dig()),
            Arguments.of(Type.GROUND, new Dig()),
            Arguments.of(Type.PSYCHIC, new Dig()),
            Arguments.of(Type.GHOST, new Dig()),
            Arguments.of(Type.DRAGON, new Dig()),
            Arguments.of(Type.DARK, new Dig()),
            Arguments.of(Type.FAIRY, new Dig()),
            // Flying is neutral against Normal, Fire, Water, Ice, Poison, Ground, Flying, Psychic, Ghost, Dragon, Dark, and Fairy
            Arguments.of(Type.NORMAL, new Fly()),
            Arguments.of(Type.FIRE, new Fly()),
            Arguments.of(Type.WATER, new Fly()),
            Arguments.of(Type.ICE, new Fly()),
            Arguments.of(Type.POISON, new Fly()),
            Arguments.of(Type.GROUND, new Fly()),
            Arguments.of(Type.FLYING, new Fly()),
            Arguments.of(Type.PSYCHIC, new Fly()),
            Arguments.of(Type.GHOST, new Fly()),
            Arguments.of(Type.DRAGON, new Fly()),
            Arguments.of(Type.DARK, new Fly()),
            Arguments.of(Type.FAIRY, new Fly()),
            // Psychic is neutral against Normal, Fire, Water, Electric, Grass, Ice, Ground, Flying, Bug, Rock, Ghost, Dragon, and Fairy
            Arguments.of(Type.NORMAL, new Psychic()),
            Arguments.of(Type.FIRE, new Psychic()),
            Arguments.of(Type.WATER, new Psychic()),
            Arguments.of(Type.ELECTRIC, new Psychic()),
            Arguments.of(Type.GRASS, new Psychic()),
            Arguments.of(Type.ICE, new Psychic()),
            Arguments.of(Type.GROUND, new Psychic()),
            Arguments.of(Type.FLYING, new Psychic()),
            Arguments.of(Type.BUG, new Psychic()),
            Arguments.of(Type.ROCK, new Psychic()),
            Arguments.of(Type.GHOST, new Psychic()),
            Arguments.of(Type.DRAGON, new Psychic()),
            Arguments.of(Type.FAIRY, new Psychic()),
            // Bug is neutral against Normal, Water, Electric, Ice, Ground, Bug, Rock, and Dragon
            Arguments.of(Type.NORMAL, new Twineedle()),
            Arguments.of(Type.WATER, new Twineedle()),
            Arguments.of(Type.ELECTRIC, new Twineedle()),
            Arguments.of(Type.ICE, new Twineedle()),
            Arguments.of(Type.GROUND, new Twineedle()),
            Arguments.of(Type.BUG, new Twineedle()),
            Arguments.of(Type.ROCK, new Twineedle()),
            Arguments.of(Type.DRAGON, new Twineedle()),
            // Rock is neutral against Normal, Water, Electric, Grass, Poison, Psychic, Rock, Ghost, Dragon, Dark, and Fairy
            Arguments.of(Type.NORMAL, new RockThrow()),
            Arguments.of(Type.WATER, new RockThrow()),
            Arguments.of(Type.ELECTRIC, new RockThrow()),
            Arguments.of(Type.GRASS, new RockThrow()),
            Arguments.of(Type.POISON, new RockThrow()),
            Arguments.of(Type.PSYCHIC, new RockThrow()),
            Arguments.of(Type.ROCK, new RockThrow()),
            Arguments.of(Type.GHOST, new RockThrow()),
            Arguments.of(Type.DRAGON, new RockThrow()),
            Arguments.of(Type.DARK, new RockThrow()),
            Arguments.of(Type.FAIRY, new RockThrow()),
            // Ghost is neutral against Fire, Water, Electric, Grass, Ice, Fighting, Poison, Ground, Flying, Bug, Rock, Dragon, Steel, and Fairy
            Arguments.of(Type.FIRE, new Lick()),
            Arguments.of(Type.WATER, new Lick()),
            Arguments.of(Type.ELECTRIC, new Lick()),
            Arguments.of(Type.GRASS, new Lick()),
            Arguments.of(Type.ICE, new Lick()),
            Arguments.of(Type.FIGHTING, new Lick()),
            Arguments.of(Type.POISON, new Lick()),
            Arguments.of(Type.GROUND, new Lick()),
            Arguments.of(Type.FLYING, new Lick()),
            Arguments.of(Type.BUG, new Lick()),
            Arguments.of(Type.ROCK, new Lick()),
            Arguments.of(Type.DRAGON, new Lick()),
            Arguments.of(Type.STEEL, new Lick()),
            Arguments.of(Type.FAIRY, new Lick()),
            // Dragon is neutral against Normal, Fire, Water, Electric, Grass, Ice, Fighting, Poison, Ground, Flying Psychic, Bug, Rock, Ghost, and Dark
            Arguments.of(Type.NORMAL, new DragonRage()),
            Arguments.of(Type.FIRE, new DragonRage()),
            Arguments.of(Type.WATER, new DragonRage()),
            Arguments.of(Type.ELECTRIC, new DragonRage()),
            Arguments.of(Type.GRASS, new DragonRage()),
            Arguments.of(Type.ICE, new DragonRage()),
            Arguments.of(Type.FIGHTING, new DragonRage()),
            Arguments.of(Type.POISON, new DragonRage()),
            Arguments.of(Type.GROUND, new DragonRage()),
            Arguments.of(Type.FLYING, new DragonRage()),
            Arguments.of(Type.PSYCHIC, new DragonRage()),
            Arguments.of(Type.BUG, new DragonRage()),
            Arguments.of(Type.ROCK, new DragonRage()),
            Arguments.of(Type.GHOST, new DragonRage()),
            Arguments.of(Type.DARK, new DragonRage()),
            // Dark is neutral against Normal, Fire, Water, Electric, Grass, Ice, Poison, Ground, Flying, Bug, Rock, Dragon, and Steel
            //Arguments.of(Type.NORMAL, new Bite()),
            //Arguments.of(Type.FIRE, new Bite()),
            //Arguments.of(Type.WATER, new Bite()),
            //Arguments.of(Type.ELECTRIC, new Bite()),
            //Arguments.of(Type.GRASS, new Bite()),
            //Arguments.of(Type.ICE, new Bite()),
            //Arguments.of(Type.POISON, new Bite()),
            //Arguments.of(Type.GROUND, new Bite()),
            //Arguments.of(Type.FLYING, new Bite()),
            //Arguments.of(Type.BUG, new Bite()),
            //Arguments.of(Type.ROCK, new Bite()),
            //Arguments.of(Type.DRAGON, new Bite()),
            //Arguments.of(Type.STEEL, new Bite()),
            // Steel is neutral against Normal, Grass, Fighting, Poison, Ground, Flying, Psychic, Bug, Ghost, Dragon, and Dark
            // Arguments.of(Type.NORMAL, new IronTail()),
            // Arguments.of(Type.GRASS, new IronTail()),
            // Arguments.of(Type.FIGHTING, new IronTail()),
            // Arguments.of(Type.POISON, new IronTail()),
            // Arguments.of(Type.GROUND, new IronTail()),
            // Arguments.of(Type.FLYING, new IronTail()),
            // Arguments.of(Type.PSYCHIC, new IronTail()),
            // Arguments.of(Type.BUG, new IronTail()),
            // Arguments.of(Type.GHOST, new IronTail()),
            // Arguments.of(Type.DRAGON, new IronTail()),
            // Arguments.of(Type.DARK, new IronTail()),
            // Fairy is neutral against Normal, Water, Electric, Ice, Ground, Flying, Psychic, Bug, Rock, Ghost, and Fairy
            // Arguments.of(Type.NORMAL, new Moonblast()),
            // Arguments.of(Type.WATER, new Moonblast()),
            // Arguments.of(Type.ELECTRIC, new Moonblast()),
            // Arguments.of(Type.ICE, new Moonblast()),
            // Arguments.of(Type.GROUND, new Moonblast()),
            // Arguments.of(Type.FLYING, new Moonblast()),
            // Arguments.of(Type.PSYCHIC, new Moonblast()),
            // Arguments.of(Type.BUG, new Moonblast()),
            // Arguments.of(Type.ROCK, new Moonblast()),
            // Arguments.of(Type.GHOST, new Moonblast()),
            // Arguments.of(Type.FAIRY, new Moonblast()),
            // Unrecognized type should default to neutral
            Arguments.of(Type.NONE, new Flamethrower()),    
            Arguments.of(null, new MegaPunch())
        );
    }

    @ParameterizedTest
    @MethodSource("provideNeutralPairs")
    void neutralEffectivenessReturns1(Type defenderType, Move move) {
        float eff = Attack.calculateEffectiveness(defenderType, move);
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
        float eff = Attack.calculateEffectiveness(null, psychic);
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
        float eff = Attack.calculateEffectiveness(Type.NONE, psychic);
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

        int damage = Attack.calculateDamage(attacker, defender, new Psychic());
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
            lowTotal += Attack.calculateDamage(lowLevel, defender, psychic);
            highTotal += Attack.calculateDamage(highLevel, defender, psychic);
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
            stabTotal += Attack.calculateDamage(abraAttacker, defender, psychic);
            noStabTotal += Attack.calculateDamage(bulbaAttacker, defender, psychic);
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
            superEffTotal += Attack.calculateDamage(attacker, weakDefender, psychic);
            resistTotal += Attack.calculateDamage(attacker, resistDefender, psychic);
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
            int val = Attack.randomInt(1, 10);
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
            assertEquals(5, Attack.randomInt(5, 5));
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
        boolean crit = Attack.calculateCriticalHit(attacker, defender);
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
            if (Attack.calculateCriticalHit(fast, slow)) fastCrits++;
            if (Attack.calculateCriticalHit(slow, fast)) slowCrits++;
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
            if (Attack.calculateCriticalHit(fast, slow)) crits++;
        }
        double critRate = (double) crits / trials;
        assertTrue(critRate <= 0.17,
                "Crit rate should not greatly exceed 15% cap, got " + (critRate * 100) + "%");
    }
}
