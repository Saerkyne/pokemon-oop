package pokemonGame;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import pokemonGame.mons.Abra;
import pokemonGame.moves.Psychic;
import java.util.List;

class LearnsetEntryTest {

    // --- Constructor / getters ---

    @Test
    void constructorStoresMove() {
        Move psychic = new Psychic();
        LearnsetEntry entry = new LearnsetEntry(psychic, LearnsetEntry.Source.TM, 29);
        assertSame(psychic, entry.getMove());
    }

    @Test
    void constructorStoresSource() {
        LearnsetEntry entry = new LearnsetEntry(new Psychic(), LearnsetEntry.Source.TM, 29);
        assertEquals(LearnsetEntry.Source.TM, entry.getSource());
    }

    @Test
    void constructorStoresParameter() {
        LearnsetEntry entry = new LearnsetEntry(new Psychic(), LearnsetEntry.Source.LEVEL, 16);
        assertEquals(16, entry.getParameter());
    }

    // --- getEligibleMoves ---

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

    @Test
    void eligibleMovesCountDecreaseAfterLearning() {
        Pokemon abra = new Abra("Test Abra");
        List<LearnsetEntry> before = LearnsetEntry.getEligibleMoves(abra);
        int countBefore = before.size();

        // Learn a move — may appear under multiple sources (e.g. LEVEL + TM),
        // so all entries for that move name get filtered out
        abra.addMove(before.get(0).getMove());

        List<LearnsetEntry> after = LearnsetEntry.getEligibleMoves(abra);
        assertTrue(after.size() < countBefore,
                "Eligible move count should decrease after learning a move");
    }

    @Test
    void allSourceEnumsExist() {
        // Verify all expected Source enum values are present
        assertNotNull(LearnsetEntry.Source.LEVEL);
        assertNotNull(LearnsetEntry.Source.TM);
        assertNotNull(LearnsetEntry.Source.HM);
        assertNotNull(LearnsetEntry.Source.EGG);
        assertNotNull(LearnsetEntry.Source.TUTOR);
    }
}
