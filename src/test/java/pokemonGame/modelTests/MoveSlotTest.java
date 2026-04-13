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
import pokemonGame.service.MoveSlotService;
import pokemonGame.species.Abra;
import pokemonGame.model.Pokemon;

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
     * HOW:     Create a MoveSlot with a given Move and verify that the current PP 
     *          is set to the move's max PP and that the MoveSlot references the correct Move.
     * IDEAL:   The test should pass for all Move classes in the pokemonGame.moves package, 
     *          ensuring that the MoveSlot constructor works correctly for any Move.
     */
    @ParameterizedTest
    @MethodSource("provideMovesForInitialization")
    void testMoveSlotInitialization(Move move, int expectedMaxPp) {
        MoveSlot moveSlot = new MoveSlot(move);
        assertEquals(moveSlot.getCurrentPP(), expectedMaxPp, "Initial PP should be equal to the move's max PP");
        assertEquals(moveSlot.getMove(), move, "MoveSlot should reference the correct Move");
    }

    // =========================================================================
    // --- use() and restore() method tests ---
    // =========================================================================

    /*
     * CHECKS:  The use() method correctly decrements the current PP of the MoveSlot.
     *          The restore() method correctly restores the current PP to the move's max PP.
     * HOW:     Create a MoveSlot with a given Move, call use() multiple times, and verify that the current PP decreases accordingly.
     *          Then call restore() and verify that the current PP is reset to the move's max PP.
     * IDEAL:   The test should pass for all Move classes in the pokemonGame.moves package, 
     *          ensuring that the use() and restore() methods work correctly for any Move.
     */
    @ParameterizedTest
    @MethodSource("provideMovesForInitialization")
    void testUse(Move move, int expectedMaxPp) {
        Pokemon abra = new Abra("testAbra");
        abra.addMove(move);
        for (int i = 0; i < expectedMaxPp; i++) {
            boolean result = MoveSlotService.use(abra, abra.getMoveSet().get(0));
            assertEquals(result, true, "use() should return true when PP is available");
            assertEquals(abra.getMoveSet().get(0).getCurrentPP(), expectedMaxPp - (i + 1), "Current PP should decrement by 1 after use()");
        }
        // After using all PP, use() should return false
        boolean result = MoveSlotService.use(abra, abra.getMoveSet().get(0));
        assertEquals(result, false, "use() should return false when no PP is left");
        assertEquals(abra.getMoveSet().get(0).getCurrentPP(), 0, "Current PP should be 0 after using all PP");
    }

    /*
     * CHECKS:  The restore() method correctly restores the current PP to the move's max PP after it has been used.
     * HOW:     Create a MoveSlot with a given Move, call use() multiple times to deplete the PP, 
     *          then call restore() and verify that the current PP is reset to the move's max PP.
     * IDEAL:   The test should pass for all Move classes in the pokemonGame.moves package, 
     *          ensuring that the restore() method works correctly for any Move.
     */
    @ParameterizedTest
    @MethodSource("provideMovesForInitialization")
    void testRestore(Move move, int expectedMaxPp) {
        Pokemon abra = new Abra("testAbra");
        abra.addMove(move);
        // Deplete the PP
        for (int i = 0; i < expectedMaxPp; i++) {
            MoveSlotService.use(abra, abra.getMoveSet().get(0));
        }
        // Restore the PP
        MoveSlotService.restore(abra, abra.getMoveSet().get(0));
        assertEquals(abra.getMoveSet().get(0).getCurrentPP(), expectedMaxPp, "Current PP should be restored to the move's max PP after calling restore()");
    }
}
