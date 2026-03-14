package pokemonGame;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/*
 * Unit tests for {@link TypeChart#getEffectiveness(String, String)}.
 *
 * Each test verifies a single type matchup in one of four outcome categories:
 *   - Super effective (2.0f)      — attacker has a type advantage
 *   - Not very effective (0.5f)   — attacker is resisted
 *   - Immune (0.0f)               — defender is completely immune
 *   - Neutral (1.0f)              — no special relationship
 *
 * CLASS-LEVEL IMPROVEMENT: All tests are individually small and structurally
 * identical (call getEffectiveness, assert a float). Replacing the entire class
 * with a single @ParameterizedTest fed by a CSV or method source would eliminate
 * boilerplate, make it trivial to add new type entries, and instantly highlight
 * any coverage gaps in the chart.
 */
class TypeChartTest {

    private final TypeChart chart = new TypeChart();

    // --- Super effective (2.0) ---

    /*
     * CHECKS:  Fire is super effective (2.0f) against Grass-type defenders.
     * HOW:     Calls getEffectiveness("Fire", "Grass") and asserts the result is 2.0f.
     * IMPROVE: Parameterize with all super-effective Fire matchups (Fire→Grass,
     *          Fire→Ice, Fire→Bug, Fire→Steel) to reach complete coverage for Fire.
     */
    @Test
    void fireSuperEffectiveAgainstGrass() {
        assertEquals(2.0f, chart.getEffectiveness("Fire", "Grass"));
    }

    /*
     * CHECKS:  Water is super effective (2.0f) against Fire-type defenders.
     * HOW:     Calls getEffectiveness("Water", "Fire") and asserts 2.0f.
     * IMPROVE: Also verify the inverse (Fire→Water = 0.5f) to confirm the chart is
     *          asymmetric, not accidentally mirrored.
     */
    @Test
    void waterSuperEffectiveAgainstFire() {
        assertEquals(2.0f, chart.getEffectiveness("Water", "Fire"));
    }

    /*
     * CHECKS:  Electric is super effective (2.0f) against Water-type defenders.
     * HOW:     Calls getEffectiveness("Electric", "Water") and asserts 2.0f.
     * IMPROVE: Test the corresponding immunity (Electric→Ground = 0.0f) in the same
     *          test to document the full Electric offensive profile.
     */
    @Test
    void electricSuperEffectiveAgainstWater() {
        assertEquals(2.0f, chart.getEffectiveness("Electric", "Water"));
    }

    /*
     * CHECKS:  Ground is super effective (2.0f) against Electric-type defenders.
     * HOW:     Calls getEffectiveness("Ground", "Electric") and asserts 2.0f.
     * IMPROVE: Pair with groundHasNoEffectOnFlying (0.0f) to give a complete picture
     *          of Ground's offensive effectiveness range.
     */
    @Test
    void groundSuperEffectiveAgainstElectric() {
        assertEquals(2.0f, chart.getEffectiveness("Ground", "Electric"));
    }

    /*
     * CHECKS:  Ice is super effective (2.0f) against Dragon-type defenders.
     * HOW:     Calls getEffectiveness("Ice", "Dragon") and asserts 2.0f.
     * IMPROVE: Also test Ice against Water (0.5f) and Ice against Ice (0.5f) to
     *          document the full Ice offensive spread in one place.
     */
    @Test
    void iceSuperEffectiveAgainstDragon() {
        assertEquals(2.0f, chart.getEffectiveness("Ice", "Dragon"));
    }

    /*
     * CHECKS:  Fighting is super effective (2.0f) against Normal-type defenders.
     * HOW:     Calls getEffectiveness("Fighting", "Normal") and asserts 2.0f.
     * IMPROVE: Verify fightingHasNoEffectOnGhost (0.0f) alongside this to document
     *          the complete Fighting vs Ghost/Normal contrast.
     */
    @Test
    void fightingSuperEffectiveAgainstNormal() {
        assertEquals(2.0f, chart.getEffectiveness("Fighting", "Normal"));
    }

    /*
     * CHECKS:  Psychic is super effective (2.0f) against Poison-type defenders.
     * HOW:     Calls getEffectiveness("Psychic", "Poison") and asserts 2.0f.
     * IMPROVE: Confirm psychicHasNoEffectOnDark (0.0f) alongside this; the contrast
     *          between super-effective vs immunity on the same attacker type is
     *          important for gameplay correctness.
     */
    @Test
    void psychicSuperEffectiveAgainstPoison() {
        assertEquals(2.0f, chart.getEffectiveness("Psychic", "Poison"));
    }

    /*
     * CHECKS:  Grass is super effective (2.0f) against Water-type defenders.
     * HOW:     Calls getEffectiveness("Grass", "Water") and asserts 2.0f.
     * IMPROVE: Also test Grass→Fire (0.5f) to document the full Grass attack spread.
     */
    @Test
    void grassSuperEffectiveAgainstWater() {
        assertEquals(2.0f, chart.getEffectiveness("Grass", "Water"));
    }

    /*
     * CHECKS:  Fairy is super effective (2.0f) against Dragon-type defenders.
     * HOW:     Calls getEffectiveness("Fairy", "Dragon") and asserts 2.0f.
     * IMPROVE: Verify the corresponding immunity in dragonHasNoEffectOnFairy (0.0f)
     *          to document the asymmetric Fairy↔Dragon relationship in one place.
     */
    @Test
    void fairySuperEffectiveAgainstDragon() {
        assertEquals(2.0f, chart.getEffectiveness("Fairy", "Dragon"));
    }

    /*
     * CHECKS:  Rock is super effective (2.0f) against Fire-type defenders.
     * HOW:     Calls getEffectiveness("Rock", "Fire") and asserts 2.0f.
     * IMPROVE: Parameterize with all Rock super-effective targets (Fire, Ice, Flying,
     *          Bug) to ensure the full Rock offensive chart is covered.
     */
    @Test
    void rockSuperEffectiveAgainstFire() {
        assertEquals(2.0f, chart.getEffectiveness("Rock", "Fire"));
    }

    /*
     * CHECKS:  Ghost is super effective (2.0f) against Ghost-type defenders (a
     *          same-type matchup that is super effective, unlike most same-type pairs).
     * HOW:     Calls getEffectiveness("Ghost", "Ghost") and asserts 2.0f.
     * IMPROVE: Pair with ghostHasNoEffectOnNormal (0.0f) to document the complete
     *          Ghost offensive profile in one test.
     */
    @Test
    void ghostSuperEffectiveAgainstGhost() {
        assertEquals(2.0f, chart.getEffectiveness("Ghost", "Ghost"));
    }

    // --- Not very effective (0.5) ---

    /*
     * CHECKS:  Water is not very effective (0.5f) against Water-type defenders (same-
     *          type resistance).
     * HOW:     Calls getEffectiveness("Water", "Water") and asserts 0.5f.
     * IMPROVE: Verify that Water→Fire is 2.0f in the same test to document both ends
     *          of the Water offensive range.
     */
    @Test
    void waterNotVeryEffectiveAgainstWater() {
        assertEquals(0.5f, chart.getEffectiveness("Water", "Water"));
    }

    /*
     * CHECKS:  Fire is not very effective (0.5f) against Water-type defenders.
     * HOW:     Calls getEffectiveness("Fire", "Water") and asserts 0.5f.
     * IMPROVE: Also assert the reverse (Water→Fire = 2.0f) to confirm the matchup is
     *          correctly asymmetric and not accidentally symmetric.
     */
    @Test
    void fireNotVeryEffectiveAgainstWater() {
        assertEquals(0.5f, chart.getEffectiveness("Fire", "Water"));
    }

    /*
     * CHECKS:  Grass is not very effective (0.5f) against Fire-type defenders.
     * HOW:     Calls getEffectiveness("Grass", "Fire") and asserts 0.5f.
     * IMPROVE: Test the inverse (Fire→Grass = 2.0f, covered by fireSuperEffectiveAgainstGrass)
     *          to document the full Grass↔Fire relationship in one place.
     */
    @Test
    void grassNotVeryEffectiveAgainstFire() {
        assertEquals(0.5f, chart.getEffectiveness("Grass", "Fire"));
    }

    /*
     * CHECKS:  Normal is not very effective (0.5f) against Rock-type defenders.
     * HOW:     Calls getEffectiveness("Normal", "Rock") and asserts 0.5f.
     * IMPROVE: Pair with normalHasNoEffectOnGhost (0.0f) to document the range of
     *          Normal's defensive disadvantages in one place.
     */
    @Test
    void normalNotVeryEffectiveAgainstRock() {
        assertEquals(0.5f, chart.getEffectiveness("Normal", "Rock"));
    }

    /*
     * CHECKS:  Steel is not very effective (0.5f) against Water-type defenders.
     * HOW:     Calls getEffectiveness("Steel", "Water") and asserts 0.5f.
     * IMPROVE: Parameterize the Steel resistance tests to cover all types that resist
     *          Steel (Water, Fire, Steel, Electric) for comprehensive coverage.
     */
    @Test
    void steelNotVeryEffectiveAgainstWater() {
        assertEquals(0.5f, chart.getEffectiveness("Steel", "Water"));
    }

    /*
     * CHECKS:  Poison is not very effective (0.5f) against Ground-type defenders.
     * HOW:     Calls getEffectiveness("Poison", "Ground") and asserts 0.5f.
     * IMPROVE: Also verify Poison→Steel (0.0f / immune) to document the full Poison
     *          offensive profile, including both resistance and immunity.
     */
    @Test
    void poisonNotVeryEffectiveAgainstGround() {
        assertEquals(0.5f, chart.getEffectiveness("Poison", "Ground"));
    }

    // --- Immunities (0.0) ---

    /*
     * CHECKS:  Normal-type moves have no effect (0.0f) on Ghost-type defenders
     *          (complete immunity).
     * HOW:     Calls getEffectiveness("Normal", "Ghost") and asserts 0.0f.
     * IMPROVE: Verify ghostHasNoEffectOnNormal (0.0f) alongside this to document that
     *          the Normal↔Ghost immunity is bidirectional.
     */
    @Test
    void normalHasNoEffectOnGhost() {
        assertEquals(0.0f, chart.getEffectiveness("Normal", "Ghost"));
    }

    /*
     * CHECKS:  Ground-type moves have no effect (0.0f) on Flying-type defenders.
     * HOW:     Calls getEffectiveness("Ground", "Flying") and asserts 0.0f.
     * IMPROVE: Pair with groundSuperEffectiveAgainstElectric (2.0f) to document the
     *          full range of Ground's offensive effectiveness.
     */
    @Test
    void groundHasNoEffectOnFlying() {
        assertEquals(0.0f, chart.getEffectiveness("Ground", "Flying"));
    }

    /*
     * CHECKS:  Electric-type moves have no effect (0.0f) on Ground-type defenders.
     * HOW:     Calls getEffectiveness("Electric", "Ground") and asserts 0.0f.
     * IMPROVE: Confirm the inverse (Ground→Electric = 2.0f) to highlight the
     *          asymmetric Electric↔Ground relationship in one place.
     */
    @Test
    void electricHasNoEffectOnGround() {
        assertEquals(0.0f, chart.getEffectiveness("Electric", "Ground"));
    }

    /*
     * CHECKS:  Ghost-type moves have no effect (0.0f) on Normal-type defenders.
     * HOW:     Calls getEffectiveness("Ghost", "Normal") and asserts 0.0f.
     * IMPROVE: Pair with normalHasNoEffectOnGhost to document the bidirectional
     *          Normal↔Ghost immunity (covered in normalHasNoEffectOnGhostAndViceVersa).
     */
    @Test
    void ghostHasNoEffectOnNormal() {
        assertEquals(0.0f, chart.getEffectiveness("Ghost", "Normal"));
    }

    /*
     * CHECKS:  Psychic has no effect (0.0f) on Dark-type defenders (an immunity
     *          introduced in Generation II).
     * HOW:     Calls getEffectiveness("Psychic", "Dark") and asserts 0.0f.
     * IMPROVE: Pair with psychicSuperEffectiveAgainstPoison (2.0f) to document the
     *          contrast between Psychic's best and worst matchups.
     */
    @Test
    void psychicHasNoEffectOnDark() {
        assertEquals(0.0f, chart.getEffectiveness("Psychic", "Dark"));
    }

    /*
     * CHECKS:  Fighting-type moves have no effect (0.0f) on Ghost-type defenders.
     * HOW:     Calls getEffectiveness("Fighting", "Ghost") and asserts 0.0f.
     * IMPROVE: Pair with fightingSuperEffectiveAgainstNormal (2.0f) to show the full
     *          Fighting vs Normal/Ghost contrast in one place.
     */
    @Test
    void fightingHasNoEffectOnGhost() {
        assertEquals(0.0f, chart.getEffectiveness("Fighting", "Ghost"));
    }

    /*
     * CHECKS:  Dragon-type moves have no effect (0.0f) on Fairy-type defenders
     *          (an immunity introduced in Generation VI).
     * HOW:     Calls getEffectiveness("Dragon", "Fairy") and asserts 0.0f.
     * IMPROVE: Pair with fairySuperEffectiveAgainstDragon (2.0f) to document the
     *          asymmetric Dragon↔Fairy relationship in one place.
     */
    @Test
    void dragonHasNoEffectOnFairy() {
        assertEquals(0.0f, chart.getEffectiveness("Dragon", "Fairy"));
    }

    /*
     * CHECKS:  The Normal↔Ghost immunity is bidirectional: Normal→Ghost = 0.0f AND
     *          Ghost→Normal = 0.0f.
     * HOW:     Calls getEffectiveness with both orderings and asserts both equal 0.0f.
     * IMPROVE: This test duplicates assertions already in normalHasNoEffectOnGhost and
     *          ghostHasNoEffectOnNormal. Consider removing it and documenting the
     *          bidirectional contract only in those two focused tests to avoid
     *          assertion duplication.
     */
    @Test
    void normalHasNoEffectOnGhostAndViceVersa() {
        assertEquals(0.0f, chart.getEffectiveness("Normal", "Ghost"));
        assertEquals(0.0f, chart.getEffectiveness("Ghost", "Normal"));
    }

    // --- Neutral (1.0) ---

    /*
     * CHECKS:  Normal has a neutral (1.0f) matchup against Water (no type relationship
     *          between Normal and Water).
     * HOW:     Calls getEffectiveness("Normal", "Water") and asserts 1.0f.
     * IMPROVE: Verify that unrecognized type strings also return 1.0 as a default
     *          fallback, strengthening the boundary-value coverage for neutral results.
     */
    @Test
    void normalNeutralAgainstWater() {
        assertEquals(1.0f, chart.getEffectiveness("Normal", "Water"));
    }

    /*
     * CHECKS:  Fire has a neutral (1.0f) matchup against Normal-type defenders.
     * HOW:     Calls getEffectiveness("Fire", "Normal") and asserts 1.0f.
     * IMPROVE: Use a parameterized approach to cover all neutral Fire matchups, rather
     *          than individual tests for each "no relationship" type pair.
     */
    @Test
    void fireNeutralAgainstNormal() {
        assertEquals(1.0f, chart.getEffectiveness("Fire", "Normal"));
    }

    /*
     * CHECKS:  Water has a neutral (1.0f) matchup against Normal-type defenders.
     * HOW:     Calls getEffectiveness("Water", "Normal") and asserts 1.0f.
     * IMPROVE: Consider combining neutral tests into a single parameterized test to
     *          reduce boilerplate across all neutral matchup checks.
     */
    @Test
    void waterNeutralAgainstNormal() {
        assertEquals(1.0f, chart.getEffectiveness("Water", "Normal"));
    }

    /*
     * CHECKS:  Fighting has a neutral (1.0f) matchup against Water-type defenders.
     * HOW:     Calls getEffectiveness("Fighting", "Water") and asserts 1.0f.
     * IMPROVE: Same as waterNeutralAgainstNormal — consolidate neutral tests into a
     *          parameterized test to eliminate repetitive structure.
     */
    @Test
    void fightingNeutralAgainstWater() {
        assertEquals(1.0f, chart.getEffectiveness("Fighting", "Water"));
    }

    // --- Same-type matchups ---

    /*
     * CHECKS:  Normal vs Normal is neutral (1.0f) — same-type matchup where neither
     *          STAB advantage nor a same-type resistance applies (Normal has no same-
     *          type resistance, unlike most types).
     * HOW:     Calls getEffectiveness("Normal", "Normal") and asserts 1.0f.
     * IMPROVE: Contrast with fireVsFireIsNotVeryEffective (0.5f) and
     *          dragonVsDragonIsSuperEffective (2.0f) to document that same-type
     *          matchups are not uniformly neutral or resistant.
     */
    @Test
    void normalVsNormalIsNeutral() {
        assertEquals(1.0f, chart.getEffectiveness("Normal", "Normal"));
    }

    /*
     * CHECKS:  Fire vs Fire is not very effective (0.5f) — most types resist their own
     *          type.
     * HOW:     Calls getEffectiveness("Fire", "Fire") and asserts 0.5f.
     * IMPROVE: Parameterize this with other same-type resistant matchups (Water→Water,
     *          Grass→Grass, Electric→Electric) to confirm the pattern holds broadly.
     */
    @Test
    void fireVsFireIsNotVeryEffective() {
        assertEquals(0.5f, chart.getEffectiveness("Fire", "Fire"));
    }

    /*
     * CHECKS:  Dragon vs Dragon is super effective (2.0f) — unusually, Dragon resists
     *          most types but is weak to its own type.
     * HOW:     Calls getEffectiveness("Dragon", "Dragon") and asserts 2.0f.
     * IMPROVE: Also assert Dragon→Fairy = 0.0f in the same test to document the
     *          complete Dragon offensive coverage and the notable Fairy immunity edge
     *          case.
     */
    @Test
    void dragonVsDragonIsSuperEffective() {
        assertEquals(2.0f, chart.getEffectiveness("Dragon", "Dragon"));
    }
}
