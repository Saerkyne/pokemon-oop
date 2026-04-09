package pokemonGame.modelTests;


import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import java.util.stream.Stream;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.stream.Collectors;
import java.util.List;
import java.util.ArrayList;

import pokemonGame.model.Move;
import pokemonGame.model.MoveSlot;

/**
 * Unit tests for the MoveSlot class, which wraps a Move with mutable PP tracking. 
 * Each Pokémon's moveset is represented by an array of MoveSlot objects, allowing for individual PP management.
 * 
 * @see MoveSlot
 * @see Move
 */

class MoveSlotTest {

    public static final Logger LOGGER = LoggerFactory.getLogger(MoveSlotTest.class);

    private static Set<Class<?>> getTestedClassesUsingClassLoader(String packageName) {
        InputStream stream = ClassLoader.getSystemClassLoader()
            .getResourceAsStream(packageName.replaceAll("[.]", "/"));
        BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
        return reader.lines()
            .filter(line -> line.endsWith(".class"))
            .map(line -> getClass(line, packageName))
            .collect(Collectors.toSet());
    }

    private static Class<?> getClass(String className, String packageName) {
        try {
            return Class.forName(packageName + "." + className.substring(0, className.lastIndexOf('.')));
        } catch (ClassNotFoundException e) {
            LOGGER.error("Class not found: {}", className, e);
            return null;
        }
    }

    static Stream<Arguments> provideMovesForInitialization() {
        Set<Class<?>> moveClasses = getTestedClassesUsingClassLoader("pokemonGame.moves");
        List<Arguments> argumentsList = new ArrayList<>();

        for (Class<?> moveClass : moveClasses) {
            try {
                Move moveInstance = (Move) moveClass.getDeclaredConstructor().newInstance();
                argumentsList.add(Arguments.of(moveInstance, moveInstance.getMaxPp()));
            } catch (Exception e) {
                LOGGER.error("Error instantiating move class: {}", moveClass.getName(), e);
                continue; // Skip this class if it cannot be instantiated
            }
        }
        return argumentsList.stream();

    }

    // =========================================================================
    // --- Test Cases for MoveSlot Initialization ---
    // =========================================================================

    /* CHECKS:  The MoveSlot constructor correctly initializes the current PP 
     *          to the move's max PP and references the correct Move.
     */
    @ParameterizedTest
    @MethodSource("provideMovesForInitialization")
    void testMoveSlotInitialization(Move move, int expectedMaxPp) {
        MoveSlot moveSlot = new MoveSlot(move);
        assertEquals(moveSlot.getCurrentPP(), expectedMaxPp, "Initial PP should be equal to the move's max PP");
        assertEquals(moveSlot.getMove(), move, "MoveSlot should reference the correct Move");
    }
}
