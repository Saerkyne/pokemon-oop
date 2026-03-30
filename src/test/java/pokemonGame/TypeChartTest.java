package pokemonGame;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import static org.junit.jupiter.api.Assertions.*;

import java.util.stream.Stream;

import pokemonGame.TypeChart.Type;

/*
 * Unit tests for {@link TypeChart#getEffectiveness(String, String)}.
 *
 * Each test verifies a single type matchup in one of four outcome categories:
 *   - Super effective (2.0f)      — attacker has a type advantage
 *   - Not very effective (0.5f)   — attacker is resisted
 *   - Immune (0.0f)               — defender is completely immune
 *   - Neutral (1.0f)              — no special relationship
 */
class TypeChartTest {

    // TypeChart is now a static utility class — call methods directly

    // --- Super effective (2.0) ---
    static Stream<Arguments> provideSuperEffectivePairs() {
        return Stream.of(
            // Normal is super effective against nothing
            // Fire is super effective against Grass, Ice, Bug, and Steel
            Arguments.of(Type.GRASS, Type.FIRE),
            Arguments.of(Type.ICE, Type.FIRE),
            Arguments.of(Type.BUG, Type.FIRE),
            Arguments.of(Type.STEEL, Type.FIRE),
            // Water is super effective against Fire, Ground, and Rock
            Arguments.of(Type.FIRE, Type.WATER),
            Arguments.of(Type.GROUND, Type.WATER),
            Arguments.of(Type.ROCK, Type.WATER),
            // Electric is super effective against Water and Flying
            Arguments.of(Type.WATER, Type.ELECTRIC),
            Arguments.of(Type.FLYING, Type.ELECTRIC),
            // Grass is super effective against Water, Ground, and Rock
            Arguments.of(Type.WATER, Type.GRASS),
            Arguments.of(Type.GROUND, Type.GRASS),
            Arguments.of(Type.ROCK, Type.GRASS),
            // Ice is super effective against Grass, Ground, Flying, and Dragon
            Arguments.of(Type.GRASS, Type.ICE),
            Arguments.of(Type.GROUND, Type.ICE),
            Arguments.of(Type.FLYING, Type.ICE),
            Arguments.of(Type.DRAGON, Type.ICE),
            // Fighting is super effective against Normal, Rock, Steel, Ice, and Dark
            Arguments.of(Type.NORMAL, Type.FIGHTING), 
            Arguments.of(Type.ROCK, Type.FIGHTING), 
            Arguments.of(Type.STEEL, Type.FIGHTING), 
            Arguments.of(Type.ICE, Type.FIGHTING),
            Arguments.of(Type.DARK, Type.FIGHTING),
            // Poison is super effective against Grass and Fairy
            Arguments.of(Type.GRASS, Type.POISON),
            Arguments.of(Type.FAIRY, Type.POISON),
            // Ground is super effective against Fire, Electric, Poison, Rock, and Steel
            Arguments.of(Type.FIRE, Type.GROUND),
            Arguments.of(Type.POISON, Type.GROUND),
            Arguments.of(Type.ROCK, Type.GROUND),
            Arguments.of(Type.STEEL, Type.GROUND),
            Arguments.of(Type.ELECTRIC, Type.GROUND),
            // Flying is super effective against Fighting, Bug, and Grass
            Arguments.of(Type.FIGHTING, Type.FLYING),
            Arguments.of(Type.BUG, Type.FLYING),
            Arguments.of(Type.GRASS, Type.FLYING),
            // Psychic is super effective against Fighting and Poison
            Arguments.of(Type.FIGHTING, Type.PSYCHIC),
            Arguments.of(Type.POISON, Type.PSYCHIC),
            // Bug is super effective against Grass, Psychic, and Dark
            Arguments.of(Type.GRASS, Type.BUG),
            Arguments.of(Type.PSYCHIC, Type.BUG),
            Arguments.of(Type.DARK, Type.BUG),          
            // Rock is super effective against Fire, Ice, Flying, and Bug
            Arguments.of(Type.FIRE, Type.ROCK),
            Arguments.of(Type.ICE, Type.ROCK),
            Arguments.of(Type.FLYING, Type.ROCK),
            Arguments.of(Type.BUG, Type.ROCK),
            // Ghost is super effective against Psychic and Ghost
            Arguments.of(Type.PSYCHIC, Type.GHOST),
            Arguments.of(Type.GHOST, Type.GHOST),
            // Dragon is super effective against Dragon
            Arguments.of(Type.DRAGON, Type.DRAGON)
            // Steel is super effective against Ice, Rock, and Fairy (No Steel Moves in Gen 1)
            // Arguments.of(Type.ICE, Type.STEEL),
            // Arguments.of(Type.ROCK, Type.STEEL),
            // Arguments.of(Type.FAIRY, Type.STEEL),
            // Dark is super effective against Psychic and Ghost (No Dark Moves in Gen 1)
            // Arguments.of(Type.PSYCHIC, Type.DARK),
            // Arguments.of(Type.GHOST, Type.DARK),
            // Fairy is super effective against Fighting, Dragon, and Dark (No Fairy Moves in Gen 1)
            // Arguments.of(Type.FIGHTING, Type.FAIRY),
            // Arguments.of(Type.DRAGON, Type.FAIRY),
            // Arguments.of(Type.DARK, Type.FAIRY)
        );
    }

    @ParameterizedTest
    @MethodSource("provideSuperEffectivePairs")
    void testSuperEffectivePairs(Type defendType, Type attackType) {
        assertEquals(2.0f, TypeChart.getEffectiveness(attackType, defendType));
    }

    

    static Stream<Arguments> provideNotVeryEffectivePairs() {
        return Stream.of(
            // Normal is not very effective against Rock and Steel
            Arguments.of(Type.ROCK, Type.NORMAL),
            Arguments.of(Type.STEEL, Type.NORMAL),
            // Fire is not very effective against Fire, Water, Rock, and Dragon
            Arguments.of(Type.FIRE, Type.FIRE),
            Arguments.of(Type.WATER, Type.FIRE),
            Arguments.of(Type.ROCK, Type.FIRE),
            Arguments.of(Type.DRAGON, Type.FIRE),
            // Water is not very effective against Water, Grass, and Dragon
            Arguments.of(Type.WATER, Type.WATER),
            Arguments.of(Type.GRASS, Type.WATER),
            Arguments.of(Type.DRAGON, Type.WATER),
            // Electric is not very effective against Electric, Grass, and Dragon
            Arguments.of(Type.ELECTRIC, Type.ELECTRIC),
            Arguments.of(Type.GRASS, Type.ELECTRIC),
            Arguments.of(Type.DRAGON, Type.ELECTRIC),
            // Grass is not very effective against Fire, Grass, Poison, Flying, Bug, Dragon, and Steel
            Arguments.of(Type.FIRE, Type.GRASS),
            Arguments.of(Type.GRASS, Type.GRASS),
            Arguments.of(Type.POISON, Type.GRASS),
            Arguments.of(Type.FLYING, Type.GRASS),
            Arguments.of(Type.BUG, Type.GRASS),
            Arguments.of(Type.DRAGON, Type.GRASS),
            Arguments.of(Type.STEEL, Type.GRASS),
            // Ice is not very effective against Fire, Water, Ice, and Steel
            Arguments.of(Type.FIRE, Type.ICE),
            Arguments.of(Type.WATER, Type.ICE),
            Arguments.of(Type.ICE, Type.ICE),
            Arguments.of(Type.STEEL, Type.ICE),
            // Fighting is not very effective against Poison, Flying, Psychic, Bug, and Fairy
            Arguments.of(Type.POISON, Type.FIGHTING),
            Arguments.of(Type.FLYING, Type.FIGHTING),
            Arguments.of(Type.PSYCHIC, Type.FIGHTING),
            Arguments.of(Type.BUG, Type.FIGHTING),
            Arguments.of(Type.FAIRY, Type.FIGHTING),
            // Poison is not very effective against Poison, Ground, Rock, and Ghost
            Arguments.of(Type.POISON, Type.POISON),
            Arguments.of(Type.GROUND, Type.POISON),
            Arguments.of(Type.ROCK, Type.POISON),
            Arguments.of(Type.GHOST, Type.POISON),
            // Ground is not very effective against Bug and Grass
            Arguments.of(Type.BUG, Type.GROUND),
            Arguments.of(Type.GRASS, Type.GROUND),
            // Flying is not very effective against Electric, Rock, and Steel
            Arguments.of(Type.ELECTRIC, Type.FLYING),
            Arguments.of(Type.ROCK, Type.FLYING),
            Arguments.of(Type.STEEL, Type.FLYING),
            // Psychic is not very effective against Psychic and Steel
            Arguments.of(Type.PSYCHIC, Type.PSYCHIC),
            Arguments.of(Type.STEEL, Type.PSYCHIC),
            // Bug is not very effective against Fire, Fighting, Poison, Flying, Ghost, Steel, and Fairy
            Arguments.of(Type.FIRE, Type.BUG),
            Arguments.of(Type.FIGHTING, Type.BUG),
            Arguments.of(Type.POISON, Type.BUG),
            Arguments.of(Type.FLYING, Type.BUG),
            Arguments.of(Type.GHOST, Type.BUG),
            Arguments.of(Type.STEEL, Type.BUG),
            Arguments.of(Type.FAIRY, Type.BUG),
            // Rock is not very effective against Fighting, Ground, and Steel
            Arguments.of(Type.FIGHTING, Type.ROCK),
            Arguments.of(Type.GROUND, Type.ROCK),
            Arguments.of(Type.STEEL, Type.ROCK),
            // Ghost is not very effective against Dark
            Arguments.of(Type.DARK, Type.GHOST),
            // Dragon is not very effective against Steel
            Arguments.of(Type.STEEL, Type.DRAGON)
            // Dark is not very effective against Fighting, Dark, and Fairy (No Dark Moves in Gen 1)
            // Arguments.of(Type.FIGHTING, Type.DARK),
            // Arguments.of(Type.DARK, Type.DARK),
            // Arguments.of(Type.FAIRY, Type.DARK),
            // Steel is not very effective against Fire, Water, Electric, and Steel (No Steel Moves in Gen 1)
            // Arguments.of(Type.FIRE, Type.STEEL),
            // Arguments.of(Type.WATER, Type.STEEL),
            // Arguments.of(Type.ELECTRIC, Type.STEEL),
            // Arguments.of(Type.STEEL, Type.STEEL),
            // Fairy is not very effective against Fire, Poison, and Steel (No Fairy Moves in Gen 1)
            // Arguments.of(Type.FIRE, Type.FAIRY),
            // Arguments.of(Type.POISON, Type.FAIRY),
            // Arguments.of(Type.STEEL, Type.FAIRY)
        );
    }
    
    @ParameterizedTest
    @MethodSource("provideNotVeryEffectivePairs")
    void testNotVeryEffective(Type defendType, Type attackType) {
        assertEquals(0.5f, TypeChart.getEffectiveness(attackType, defendType));
    }

    // --- Immunities (0.0) ---

    static Stream<Arguments> provideImmunePairs() {
        return Stream.of(
            // Ghost is immune to Normal and Fighting
            Arguments.of(Type.GHOST, Type.NORMAL),
            Arguments.of(Type.GHOST, Type.FIGHTING),
            // Normal is immune to Ghost
            Arguments.of(Type.NORMAL, Type.GHOST),
            // Ground is immune to Electric
            Arguments.of(Type.GROUND, Type.ELECTRIC),
            // Flying is immune to Ground
            Arguments.of(Type.FLYING, Type.GROUND),
            // Dark is immune to Psychic
            Arguments.of(Type.DARK, Type.PSYCHIC),
            // Steel is immune to Poison
            Arguments.of(Type.STEEL, Type.POISON),
            // Fairy is immune to Dragon
            Arguments.of(Type.FAIRY, Type.DRAGON)
        );
    }

    @ParameterizedTest
    @MethodSource("provideImmunePairs")
    void immunityReturnsZero(Type defenderType, Type attackType) {
        assertEquals(0.0f, TypeChart.getEffectiveness(attackType, defenderType));
    }

    static Stream<Arguments> provideNeutralPairs() {
        return Stream.of(
            // Normal is neutral against all types except Rock, Steel, and Ghost
            Arguments.of(Type.FIRE, Type.NORMAL),
            Arguments.of(Type.WATER, Type.NORMAL),
            Arguments.of(Type.ELECTRIC, Type.NORMAL),
            Arguments.of(Type.GRASS, Type.NORMAL),
            Arguments.of(Type.ICE, Type.NORMAL),
            Arguments.of(Type.FIGHTING, Type.NORMAL),
            Arguments.of(Type.POISON, Type.NORMAL),
            Arguments.of(Type.GROUND, Type.NORMAL),
            Arguments.of(Type.FLYING, Type.NORMAL),
            Arguments.of(Type.PSYCHIC, Type.NORMAL),
            Arguments.of(Type.BUG, Type.NORMAL),
            Arguments.of(Type.DRAGON, Type.NORMAL),
            Arguments.of(Type.DARK, Type.NORMAL),
            Arguments.of(Type.FAIRY, Type.NORMAL),
            // Fire is neutral against Normal, Electric, Fighting, Poison, Flying, Psychic, Ghost, Dark, and Fairy
            Arguments.of(Type.NORMAL, Type.FIRE),
            Arguments.of(Type.ELECTRIC, Type.FIRE),
            Arguments.of(Type.FIGHTING, Type.FIRE),
            Arguments.of(Type.POISON, Type.FIRE),
            Arguments.of(Type.FLYING, Type.FIRE),
            Arguments.of(Type.PSYCHIC, Type.FIRE),
            Arguments.of(Type.GHOST, Type.FIRE),
            Arguments.of(Type.DARK, Type.FIRE),
            Arguments.of(Type.FAIRY, Type.FIRE),
            // Water is neutral against Normal, Electric, Ice, Fighting, Poison, Flying, Psychic, Bug, Ghost, Dark, Steel, and Fairy
            Arguments.of(Type.NORMAL, Type.WATER),
            Arguments.of(Type.ELECTRIC, Type.WATER),
            Arguments.of(Type.ICE, Type.WATER),
            Arguments.of(Type.FIGHTING, Type.WATER),
            Arguments.of(Type.POISON, Type.WATER),
            Arguments.of(Type.FLYING, Type.WATER),
            Arguments.of(Type.PSYCHIC, Type.WATER),
            Arguments.of(Type.BUG, Type.WATER),
            Arguments.of(Type.GHOST, Type.WATER),
            Arguments.of(Type.DARK, Type.WATER),
            Arguments.of(Type.STEEL, Type.WATER),
            Arguments.of(Type.FAIRY, Type.WATER),
            // Electric is neutral against Normal, Fire, Ice, Fighting, Poison, Psychic, Bug, Rock, Ghost, Dark, Steel, and Fairy
            Arguments.of(Type.NORMAL, Type.ELECTRIC),
            Arguments.of(Type.FIRE, Type.ELECTRIC),
            Arguments.of(Type.ICE, Type.ELECTRIC),
            Arguments.of(Type.FIGHTING, Type.ELECTRIC),
            Arguments.of(Type.POISON, Type.ELECTRIC),
            Arguments.of(Type.PSYCHIC, Type.ELECTRIC),
            Arguments.of(Type.BUG, Type.ELECTRIC),
            Arguments.of(Type.ROCK, Type.ELECTRIC),
            Arguments.of(Type.GHOST, Type.ELECTRIC),
            Arguments.of(Type.DARK, Type.ELECTRIC),
            Arguments.of(Type.STEEL, Type.ELECTRIC),
            Arguments.of(Type.FAIRY, Type.ELECTRIC),
            // Grass is neutral against Normal, Electric, Ice, Fighting, Psychic, Ghost, Dark, and Fairy
            Arguments.of(Type.NORMAL, Type.GRASS),
            Arguments.of(Type.ELECTRIC, Type.GRASS),
            Arguments.of(Type.ICE, Type.GRASS),
            Arguments.of(Type.FIGHTING, Type.GRASS),
            Arguments.of(Type.PSYCHIC, Type.GRASS),
            Arguments.of(Type.GHOST, Type.GRASS),
            Arguments.of(Type.DARK, Type.GRASS),
            Arguments.of(Type.FAIRY, Type.GRASS),
            // Ice is neutral against Normal, Electric, Fighting, Poison, Psychic, Bug, Rock, Ghost, Dark, and Fairy
            Arguments.of(Type.NORMAL, Type.ICE),
            Arguments.of(Type.ELECTRIC, Type.ICE),
            Arguments.of(Type.FIGHTING, Type.ICE),
            Arguments.of(Type.POISON, Type.ICE),
            Arguments.of(Type.PSYCHIC, Type.ICE),
            Arguments.of(Type.BUG, Type.ICE),
            Arguments.of(Type.ROCK, Type.ICE),
            Arguments.of(Type.GHOST, Type.ICE),
            Arguments.of(Type.DARK, Type.ICE),
            Arguments.of(Type.FAIRY, Type.ICE),
            // Fighting is neutral against Fire, Water, Electric, Grass, Fighting, Ground, and Dragon
            Arguments.of(Type.FIRE, Type.FIGHTING),
            Arguments.of(Type.WATER, Type.FIGHTING),
            Arguments.of(Type.ELECTRIC, Type.FIGHTING),
            Arguments.of(Type.GRASS, Type.FIGHTING),
            Arguments.of(Type.FIGHTING, Type.FIGHTING),
            Arguments.of(Type.GROUND, Type.FIGHTING),
            Arguments.of(Type.DRAGON, Type.FIGHTING),
            // Poison is neutral against Normal, Fire, Water, Electric, Ice, Fighting, Flying, Psychic, Bug, Dragon, and Dark
            Arguments.of(Type.NORMAL, Type.POISON),
            Arguments.of(Type.FIRE, Type.POISON),
            Arguments.of(Type.WATER, Type.POISON),
            Arguments.of(Type.ELECTRIC, Type.POISON),
            Arguments.of(Type.ICE, Type.POISON),
            Arguments.of(Type.FIGHTING, Type.POISON),
            Arguments.of(Type.FLYING, Type.POISON),
            Arguments.of(Type.PSYCHIC, Type.POISON),
            Arguments.of(Type.BUG, Type.POISON),
            Arguments.of(Type.DRAGON, Type.POISON),
            Arguments.of(Type.DARK, Type.POISON),
            // Ground is neutral against Normal, Water, Ice, Fighting, Ground, Psychic, Ghost, Dragon, Dark, and Fairy
            Arguments.of(Type.NORMAL, Type.GROUND),
            Arguments.of(Type.WATER, Type.GROUND),
            Arguments.of(Type.ICE, Type.GROUND),
            Arguments.of(Type.FIGHTING, Type.GROUND),
            Arguments.of(Type.GROUND, Type.GROUND),
            Arguments.of(Type.PSYCHIC, Type.GROUND),
            Arguments.of(Type.GHOST, Type.GROUND),
            Arguments.of(Type.DRAGON, Type.GROUND),
            Arguments.of(Type.DARK, Type.GROUND),
            Arguments.of(Type.FAIRY, Type.GROUND),
            // Flying is neutral against Normal, Fire, Water, Ice, Poison, Ground, Flying, Psychic, Ghost, Dragon, Dark, and Fairy
            Arguments.of(Type.NORMAL, Type.FLYING),
            Arguments.of(Type.FIRE, Type.FLYING),
            Arguments.of(Type.WATER, Type.FLYING),
            Arguments.of(Type.ICE, Type.FLYING),
            Arguments.of(Type.POISON, Type.FLYING),
            Arguments.of(Type.GROUND, Type.FLYING),
            Arguments.of(Type.FLYING, Type.FLYING),
            Arguments.of(Type.PSYCHIC, Type.FLYING),
            Arguments.of(Type.GHOST, Type.FLYING),
            Arguments.of(Type.DRAGON, Type.FLYING),
            Arguments.of(Type.DARK, Type.FLYING),
            Arguments.of(Type.FAIRY, Type.FLYING),
            // Psychic is neutral against Normal, Fire, Water, Electric, Grass, Ice, Ground, Flying, Bug, Rock, Ghost, Dragon, and Fairy
            Arguments.of(Type.NORMAL, Type.PSYCHIC),
            Arguments.of(Type.FIRE, Type.PSYCHIC),
            Arguments.of(Type.WATER, Type.PSYCHIC),
            Arguments.of(Type.ELECTRIC, Type.PSYCHIC),
            Arguments.of(Type.GRASS, Type.PSYCHIC),
            Arguments.of(Type.ICE, Type.PSYCHIC),
            Arguments.of(Type.GROUND, Type.PSYCHIC),
            Arguments.of(Type.FLYING, Type.PSYCHIC),
            Arguments.of(Type.BUG, Type.PSYCHIC),
            Arguments.of(Type.ROCK, Type.PSYCHIC),
            Arguments.of(Type.GHOST, Type.PSYCHIC),
            Arguments.of(Type.DRAGON, Type.PSYCHIC),
            Arguments.of(Type.FAIRY, Type.PSYCHIC),
            // Bug is neutral against Normal, Water, Electric, Ice, Ground, Bug, Rock, and Dragon
            Arguments.of(Type.NORMAL, Type.BUG),
            Arguments.of(Type.WATER, Type.BUG),
            Arguments.of(Type.ELECTRIC, Type.BUG),
            Arguments.of(Type.ICE, Type.BUG),
            Arguments.of(Type.GROUND, Type.BUG),
            Arguments.of(Type.BUG, Type.BUG),
            Arguments.of(Type.ROCK, Type.BUG),
            Arguments.of(Type.DRAGON, Type.BUG),
            // Rock is neutral against Normal, Water, Electric, Grass, Poison, Psychic, Rock, Ghost, Dragon, Dark, and Fairy
            Arguments.of(Type.NORMAL, Type.ROCK),
            Arguments.of(Type.WATER, Type.ROCK),
            Arguments.of(Type.ELECTRIC, Type.ROCK),
            Arguments.of(Type.GRASS, Type.ROCK),
            Arguments.of(Type.POISON, Type.ROCK),
            Arguments.of(Type.PSYCHIC, Type.ROCK),
            Arguments.of(Type.ROCK, Type.ROCK),
            Arguments.of(Type.GHOST, Type.ROCK),
            Arguments.of(Type.DRAGON, Type.ROCK),
            Arguments.of(Type.DARK, Type.ROCK),
            Arguments.of(Type.FAIRY, Type.ROCK),
            // Ghost is neutral against Fire, Water, Electric, Grass, Ice, Fighting, Poison, Ground, Flying, Bug, Rock, Dragon, Steel, and Fairy
            Arguments.of(Type.FIRE, Type.GHOST),
            Arguments.of(Type.WATER, Type.GHOST),
            Arguments.of(Type.ELECTRIC, Type.GHOST),
            Arguments.of(Type.GRASS, Type.GHOST),
            Arguments.of(Type.ICE, Type.GHOST),
            Arguments.of(Type.FIGHTING, Type.GHOST),
            Arguments.of(Type.POISON, Type.GHOST),
            Arguments.of(Type.GROUND, Type.GHOST),
            Arguments.of(Type.FLYING, Type.GHOST),
            Arguments.of(Type.BUG, Type.GHOST),
            Arguments.of(Type.ROCK, Type.GHOST),
            Arguments.of(Type.DRAGON, Type.GHOST),
            Arguments.of(Type.STEEL, Type.GHOST),
            Arguments.of(Type.FAIRY, Type.GHOST),
            // Dragon is neutral against Normal, Fire, Water, Electric, Grass, Ice, Fighting, Poison, Ground, Flying Psychic, Bug, Rock, Ghost, and Dark
            Arguments.of(Type.NORMAL, Type.DRAGON),
            Arguments.of(Type.FIRE, Type.DRAGON),
            Arguments.of(Type.WATER, Type.DRAGON),
            Arguments.of(Type.ELECTRIC, Type.DRAGON),
            Arguments.of(Type.GRASS, Type.DRAGON),
            Arguments.of(Type.ICE, Type.DRAGON),
            Arguments.of(Type.FIGHTING, Type.DRAGON),
            Arguments.of(Type.POISON, Type.DRAGON),
            Arguments.of(Type.GROUND, Type.DRAGON),
            Arguments.of(Type.FLYING, Type.DRAGON),
            Arguments.of(Type.PSYCHIC, Type.DRAGON),
            Arguments.of(Type.BUG, Type.DRAGON),
            Arguments.of(Type.ROCK, Type.DRAGON),
            Arguments.of(Type.GHOST, Type.DRAGON),
            Arguments.of(Type.DARK, Type.DRAGON),
            // Dark is neutral against Normal, Fire, Water, Electric, Grass, Ice, Poison, Ground, Flying, Bug, Rock, Dragon, and Steel
            //Arguments.of(Type.NORMAL, Type.DARK),
            //Arguments.of(Type.FIRE, Type.DARK),
            //Arguments.of(Type.WATER, Type.DARK),
            //Arguments.of(Type.ELECTRIC, Type.DARK),
            //Arguments.of(Type.GRASS, Type.DARK),
            //Arguments.of(Type.ICE, Type.DARK),
            //Arguments.of(Type.POISON, Type.DARK),
            //Arguments.of(Type.GROUND, Type.DARK),
            //Arguments.of(Type.FLYING, Type.DARK),
            //Arguments.of(Type.BUG, Type.DARK),
            //Arguments.of(Type.ROCK, Type.DARK),
            //Arguments.of(Type.DRAGON, Type.DARK),
            //Arguments.of(Type.STEEL, Type.DARK),
            // Steel is neutral against Normal, Grass, Fighting, Poison, Ground, Flying, Psychic, Bug, Ghost, Dragon, and Dark
            // Arguments.of(Type.NORMAL, Type.STEEL),
            // Arguments.of(Type.GRASS, Type.STEEL),
            // Arguments.of(Type.FIGHTING, Type.STEEL),
            // Arguments.of(Type.POISON, Type.STEEL),
            // Arguments.of(Type.GROUND, Type.STEEL),
            // Arguments.of(Type.FLYING, Type.STEEL),
            // Arguments.of(Type.PSYCHIC, Type.STEEL),
            // Arguments.of(Type.BUG, Type.STEEL),
            // Arguments.of(Type.GHOST, Type.STEEL),
            // Arguments.of(Type.DRAGON, Type.STEEL),
            // Arguments.of(Type.DARK, Type.STEEL),
            // Fairy is neutral against Normal, Water, Electric, Ice, Ground, Flying, Psychic, Bug, Rock, Ghost, and Fairy
            // Arguments.of(Type.NORMAL, Type.FAIRY),
            // Arguments.of(Type.WATER, Type.FAIRY),
            // Arguments.of(Type.ELECTRIC, Type.FAIRY),
            // Arguments.of(Type.ICE, Type.FAIRY),
            // Arguments.of(Type.GROUND, Type.FAIRY),
            // Arguments.of(Type.FLYING, Type.FAIRY),
            // Arguments.of(Type.PSYCHIC, Type.FAIRY),
            // Arguments.of(Type.BUG, Type.FAIRY),
            // Arguments.of(Type.ROCK, Type.FAIRY),
            // Arguments.of(Type.GHOST, Type.FAIRY),
            // Arguments.of(Type.FAIRY, Type.FAIRY),
            // Unrecognized type should default to neutral
            Arguments.of(Type.NONE, Type.FIRE),    
            Arguments.of(null, Type.NORMAL)
        );
    }

    @ParameterizedTest
    @MethodSource("provideNeutralPairs")
    void neutralEffectivenessReturns1(Type defenderType, Type attackType) {
        assertEquals(1.0f, TypeChart.getEffectiveness(attackType, defenderType));
    }
}
