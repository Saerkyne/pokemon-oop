package pokemonGame;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import pokemonGame.mons.Abra;
import java.util.List;

class LearnsetEntryTest {

    @Test
    void eligibleMovesExcludesAlreadyKnownMoves() {
        Pokemon abra = new Abra("Test Abra");
        List<LearnsetEntry> before = LearnsetEntry.getEligibleMoves(abra);

        // Learn the first eligible move
        Move firstMove = before.get(0).getMove();
        abra.addMove(firstMove);

        List<LearnsetEntry> after = LearnsetEntry.getEligibleMoves(abra);
        boolean stillContains = after.stream()
                .anyMatch(e -> e.getMove().getMoveName().equals(firstMove.getMoveName()));
        assertFalse(stillContains,
                "A move the Pokémon already knows should not appear in eligible moves");
    }

    @Test
    void tmMovesAlwaysEligibleRegardlessOfLevel() {
        Pokemon abra = new Abra("Test Abra"); // Level 5
        List<LearnsetEntry> eligible = LearnsetEntry.getEligibleMoves(abra);
        boolean hasTmMove = eligible.stream()
                .anyMatch(e -> e.getSource() == LearnsetEntry.Source.TM);
        assertTrue(hasTmMove,
                "TM moves should always be eligible regardless of level");
    }

    @Test
    void hmMovesAlwaysEligibleRegardlessOfLevel() {
        Pokemon abra = new Abra("Test Abra"); // Level 5
        List<LearnsetEntry> eligible = LearnsetEntry.getEligibleMoves(abra);
        boolean hasHmMove = eligible.stream()
                .anyMatch(e -> e.getSource() == LearnsetEntry.Source.HM);
        assertTrue(hasHmMove,
                "HM moves should always be eligible regardless of level");
    }

    @Test
    void highLevelMovesFilteredOutAtLowLevel() {
        Pokemon abra = new Abra("Test Abra"); // Level 5
        List<LearnsetEntry> eligible = LearnsetEntry.getEligibleMoves(abra);
        for (LearnsetEntry e : eligible) {
            if (e.getSource() == LearnsetEntry.Source.LEVEL) {
                assertTrue(e.getParameter() <= abra.getLevel(),
                        "Level-up move " + e.getMove().getMoveName()
                                + " (level " + e.getParameter()
                                + ") should not be eligible at level " + abra.getLevel());
            }
        }
    }

    @Test
    void levelUpMovesUnlockWhenLevelIncreases() {
        Pokemon abra = new Abra("Test Abra"); // Level 5
        int eligibleAtLevel5 = (int) LearnsetEntry.getEligibleMoves(abra).stream()
                .filter(e -> e.getSource() == LearnsetEntry.Source.LEVEL)
                .count();

        abra.setLevel(100); // Max level — all level-up moves should be available
        int eligibleAtLevel100 = (int) LearnsetEntry.getEligibleMoves(abra).stream()
                .filter(e -> e.getSource() == LearnsetEntry.Source.LEVEL)
                .count();

        assertTrue(eligibleAtLevel100 >= eligibleAtLevel5,
                "More level-up moves should be eligible at higher levels");
    }

    @Test
    void nullPokemonReturnsEmptyList() {
        List<LearnsetEntry> eligible = LearnsetEntry.getEligibleMoves(null);
        assertNotNull(eligible);
        assertTrue(eligible.isEmpty());
    }
}
