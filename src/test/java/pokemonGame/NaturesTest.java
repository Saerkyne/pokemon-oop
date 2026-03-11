package pokemonGame;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import pokemonGame.mons.Abra;

class NaturesTest {

    // --- Modifier values ---

    @Test
    void adamantBoostsAttack() {
        assertEquals(1.1, Natures.ADAMANT.modifierFor(Stat.ATTACK), 0.001);
    }

    @Test
    void adamantLowersSpecialAttack() {
        assertEquals(0.9, Natures.ADAMANT.modifierFor(Stat.SPECIAL_ATTACK), 0.001);
    }

    @Test
    void adamantNeutralForDefense() {
        assertEquals(1.0, Natures.ADAMANT.modifierFor(Stat.DEFENSE), 0.001);
    }

    @Test
    void modestBoostsSpecialAttack() {
        assertEquals(1.1, Natures.MODEST.modifierFor(Stat.SPECIAL_ATTACK), 0.001);
    }

    @Test
    void modestLowersAttack() {
        assertEquals(0.9, Natures.MODEST.modifierFor(Stat.ATTACK), 0.001);
    }

    @Test
    void timidBoostsSpeed() {
        assertEquals(1.1, Natures.TIMID.modifierFor(Stat.SPEED), 0.001);
    }

    @Test
    void timidLowersAttack() {
        assertEquals(0.9, Natures.TIMID.modifierFor(Stat.ATTACK), 0.001);
    }

    @Test
    void boldBoostsDefense() {
        assertEquals(1.1, Natures.BOLD.modifierFor(Stat.DEFENSE), 0.001);
    }

    @Test
    void boldLowersAttack() {
        assertEquals(0.9, Natures.BOLD.modifierFor(Stat.ATTACK), 0.001);
    }

    @Test
    void calmBoostsSpecialDefense() {
        assertEquals(1.1, Natures.CALM.modifierFor(Stat.SPECIAL_DEFENSE), 0.001);
    }

    @Test
    void jollyBoostsSpeedLowersSpecialAttack() {
        assertEquals(1.1, Natures.JOLLY.modifierFor(Stat.SPEED), 0.001);
        assertEquals(0.9, Natures.JOLLY.modifierFor(Stat.SPECIAL_ATTACK), 0.001);
    }

    // --- Neutral natures ---

    @Test
    void bashfulIsNeutralForAllStats() {
        for (Stat stat : Stat.values()) {
            assertEquals(1.0, Natures.BASHFUL.modifierFor(stat), 0.001,
                    "Bashful should be neutral for " + stat);
        }
    }

    @Test
    void hardyIsNeutralForAllStats() {
        for (Stat stat : Stat.values()) {
            assertEquals(1.0, Natures.HARDY.modifierFor(stat), 0.001,
                    "Hardy should be neutral for " + stat);
        }
    }

    @Test
    void docileIsNeutralForAllStats() {
        for (Stat stat : Stat.values()) {
            assertEquals(1.0, Natures.DOCILE.modifierFor(stat), 0.001,
                    "Docile should be neutral for " + stat);
        }
    }

    @Test
    void quirkyIsNeutralForAllStats() {
        for (Stat stat : Stat.values()) {
            assertEquals(1.0, Natures.QUIRKY.modifierFor(stat), 0.001,
                    "Quirky should be neutral for " + stat);
        }
    }

    @Test
    void seriousIsNeutralForAllStats() {
        for (Stat stat : Stat.values()) {
            assertEquals(1.0, Natures.SERIOUS.modifierFor(stat), 0.001,
                    "Serious should be neutral for " + stat);
        }
    }

    // --- Display name ---

    @Test
    void displayNameMatchesExpected() {
        assertEquals("Adamant", Natures.ADAMANT.getDisplayName());
        assertEquals("Modest", Natures.MODEST.getDisplayName());
        assertEquals("Timid", Natures.TIMID.getDisplayName());
        assertEquals("Hardy", Natures.HARDY.getDisplayName());
    }

    // --- All 25 natures exist ---

    @Test
    void thereare25Natures() {
        assertEquals(25, Natures.values().length,
                "There should be exactly 25 natures");
    }

    // --- fromString lookup ---

    @Test
    void fromStringFindsAdamant() {
        assertEquals(Natures.ADAMANT, Natures.fromString("Adamant"));
    }

    @Test
    void fromStringIsCaseInsensitive() {
        assertEquals(Natures.BOLD, Natures.fromString("bold"));
        assertEquals(Natures.BOLD, Natures.fromString("BOLD"));
    }

    @Test
    void fromStringMatchesEnumName() {
        assertEquals(Natures.ADAMANT, Natures.fromString("ADAMANT"));
    }

    @Test
    void fromStringThrowsForUnknownNature() {
        assertThrows(IllegalArgumentException.class, () -> Natures.fromString("NotANature"));
    }

    // --- Random nature ---

    @Test
    void randomReturnsAValidNature() {
        Natures nature = Natures.random();
        assertNotNull(nature);
    }

    @Test
    void randomReturnsVariety() {
        // Over many calls, we should see more than one nature
        java.util.Set<Natures> seen = new java.util.HashSet<>();
        for (int i = 0; i < 100; i++) {
            seen.add(Natures.random());
        }
        assertTrue(seen.size() > 1,
                "random() should produce more than one nature over 100 calls");
    }

    // --- assignTo / assignRandom ---

    @Test
    void assignToSetsNatureOnPokemon() {
        Pokemon abra = new Abra("Test Abra");
        Natures.ADAMANT.assignTo(abra);
        assertEquals(Natures.ADAMANT, abra.getNature());
    }

    @Test
    void assignRandomSetsNonNullNature() {
        Pokemon abra = new Abra("Test Abra");
        Natures.assignRandom(abra);
        assertNotNull(abra.getNature());
    }
}
