package pokemonGame;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

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
    void fromStringThrowsForUnknownNature() {
        assertThrows(IllegalArgumentException.class, () -> Natures.fromString("NotANature"));
    }

    // --- Random nature ---

    @Test
    void randomReturnsAValidNature() {
        Natures nature = Natures.random();
        assertNotNull(nature);
    }
}
