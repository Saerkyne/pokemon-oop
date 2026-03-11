package pokemonGame;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class TypeChartTest {

    private final TypeChart chart = new TypeChart();

    // --- Super effective (2.0) ---

    @Test
    void fireSuperEffectiveAgainstGrass() {
        assertEquals(2.0f, chart.getEffectiveness("Fire", "Grass"));
    }

    @Test
    void waterSuperEffectiveAgainstFire() {
        assertEquals(2.0f, chart.getEffectiveness("Water", "Fire"));
    }

    @Test
    void electricSuperEffectiveAgainstWater() {
        assertEquals(2.0f, chart.getEffectiveness("Electric", "Water"));
    }

    @Test
    void groundSuperEffectiveAgainstElectric() {
        assertEquals(2.0f, chart.getEffectiveness("Ground", "Electric"));
    }

    @Test
    void iceSuperEffectiveAgainstDragon() {
        assertEquals(2.0f, chart.getEffectiveness("Ice", "Dragon"));
    }

    @Test
    void fightingSuperEffectiveAgainstNormal() {
        assertEquals(2.0f, chart.getEffectiveness("Fighting", "Normal"));
    }

    @Test
    void psychicSuperEffectiveAgainstPoison() {
        assertEquals(2.0f, chart.getEffectiveness("Psychic", "Poison"));
    }

    @Test
    void grassSuperEffectiveAgainstWater() {
        assertEquals(2.0f, chart.getEffectiveness("Grass", "Water"));
    }

    @Test
    void fairySuperEffectiveAgainstDragon() {
        assertEquals(2.0f, chart.getEffectiveness("Fairy", "Dragon"));
    }

    @Test
    void rockSuperEffectiveAgainstFire() {
        assertEquals(2.0f, chart.getEffectiveness("Rock", "Fire"));
    }

    @Test
    void ghostSuperEffectiveAgainstGhost() {
        assertEquals(2.0f, chart.getEffectiveness("Ghost", "Ghost"));
    }

    // --- Not very effective (0.5) ---

    @Test
    void waterNotVeryEffectiveAgainstWater() {
        assertEquals(0.5f, chart.getEffectiveness("Water", "Water"));
    }

    @Test
    void fireNotVeryEffectiveAgainstWater() {
        assertEquals(0.5f, chart.getEffectiveness("Fire", "Water"));
    }

    @Test
    void grassNotVeryEffectiveAgainstFire() {
        assertEquals(0.5f, chart.getEffectiveness("Grass", "Fire"));
    }

    @Test
    void normalNotVeryEffectiveAgainstRock() {
        assertEquals(0.5f, chart.getEffectiveness("Normal", "Rock"));
    }

    @Test
    void steelNotVeryEffectiveAgainstWater() {
        assertEquals(0.5f, chart.getEffectiveness("Steel", "Water"));
    }

    @Test
    void poisonNotVeryEffectiveAgainstGround() {
        assertEquals(0.5f, chart.getEffectiveness("Poison", "Ground"));
    }

    // --- Immunities (0.0) ---

    @Test
    void normalHasNoEffectOnGhost() {
        assertEquals(0.0f, chart.getEffectiveness("Normal", "Ghost"));
    }

    @Test
    void groundHasNoEffectOnFlying() {
        assertEquals(0.0f, chart.getEffectiveness("Ground", "Flying"));
    }

    @Test
    void electricHasNoEffectOnGround() {
        assertEquals(0.0f, chart.getEffectiveness("Electric", "Ground"));
    }

    @Test
    void ghostHasNoEffectOnNormal() {
        assertEquals(0.0f, chart.getEffectiveness("Ghost", "Normal"));
    }

    @Test
    void psychicHasNoEffectOnDark() {
        assertEquals(0.0f, chart.getEffectiveness("Psychic", "Dark"));
    }

    @Test
    void fightingHasNoEffectOnGhost() {
        assertEquals(0.0f, chart.getEffectiveness("Fighting", "Ghost"));
    }

    @Test
    void dragonHasNoEffectOnFairy() {
        assertEquals(0.0f, chart.getEffectiveness("Dragon", "Fairy"));
    }

    @Test
    void normalHasNoEffectOnGhostAndViceVersa() {
        assertEquals(0.0f, chart.getEffectiveness("Normal", "Ghost"));
        assertEquals(0.0f, chart.getEffectiveness("Ghost", "Normal"));
    }

    // --- Neutral (1.0) ---

    @Test
    void normalNeutralAgainstWater() {
        assertEquals(1.0f, chart.getEffectiveness("Normal", "Water"));
    }

    @Test
    void fireNeutralAgainstNormal() {
        assertEquals(1.0f, chart.getEffectiveness("Fire", "Normal"));
    }

    @Test
    void waterNeutralAgainstNormal() {
        assertEquals(1.0f, chart.getEffectiveness("Water", "Normal"));
    }

    @Test
    void fightingNeutralAgainstWater() {
        assertEquals(1.0f, chart.getEffectiveness("Fighting", "Water"));
    }

    // --- Same-type matchups ---

    @Test
    void normalVsNormalIsNeutral() {
        assertEquals(1.0f, chart.getEffectiveness("Normal", "Normal"));
    }

    @Test
    void fireVsFireIsNotVeryEffective() {
        assertEquals(0.5f, chart.getEffectiveness("Fire", "Fire"));
    }

    @Test
    void dragonVsDragonIsSuperEffective() {
        assertEquals(2.0f, chart.getEffectiveness("Dragon", "Dragon"));
    }
}
