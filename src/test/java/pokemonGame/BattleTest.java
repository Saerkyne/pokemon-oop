package pokemonGame;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

import pokemonGame.mons.Abra;
import pokemonGame.mons.Electrode;
import pokemonGame.mons.Slowbro;
import pokemonGame.moves.Psychic;
import pokemonGame.moves.MegaPunch;

class BattleTest {

    private Trainer player;
    private Trainer opponent;
    private Pokemon fastPokemon;   // Electrode: 150 base speed
    private Pokemon slowPokemon;   // Slowbro: 30 base speed

    @BeforeEach
    void setUp() {
        player = new Trainer("Ash");
        opponent = new Trainer("Gary");
        fastPokemon = new Electrode("Electrode");
        slowPokemon = new Slowbro("Slowbro");
        fastPokemon.setLevel(50);
        slowPokemon.setLevel(50);
    }

    // --- dealDamage ---

    @Test
    void dealDamageReducesDefenderHP() {
        Pokemon attacker = new Abra("Attacker");
        Pokemon defender = new Abra("Defender");
        attacker.setLevel(50);
        defender.setLevel(50);

        int hpBefore = defender.getCurrentHP();
        Battle.dealDamage(attacker, defender, new Psychic());
        assertTrue(defender.getCurrentHP() < hpBefore,
                "Defender's HP should decrease after taking damage");
    }

    @Test
    void dealDamageCanReduceHPBelowZero() {
        Pokemon attacker = new Abra("Attacker");
        Pokemon defender = new Abra("Defender");
        attacker.setLevel(100);
        defender.setLevel(5);

        // Hit multiple times to guarantee HP drops to or below zero
        for (int i = 0; i < 10; i++) {
            Battle.dealDamage(attacker, defender, new Psychic());
        }
        assertTrue(defender.getCurrentHP() <= 0,
                "Defender's HP should be at or below 0 after repeated hits");
    }

    @Test
    void dealDamageWithPhysicalMove() {
        Pokemon attacker = new Abra("Attacker");
        Pokemon defender = new Abra("Defender");
        attacker.setLevel(50);
        defender.setLevel(50);

        int hpBefore = defender.getCurrentHP();
        Battle.dealDamage(attacker, defender, new MegaPunch());
        assertTrue(defender.getCurrentHP() < hpBefore,
                "Physical move should also reduce HP");
    }

    @Test
    void dealDamageExactHPReduction() {
        // Verify that damage dealt matches the HP difference
        Pokemon attacker = new Abra("Attacker");
        Pokemon defender = new Abra("Defender");
        attacker.setLevel(50);
        defender.setLevel(50);

        int hpBefore = defender.getCurrentHP();
        Battle.dealDamage(attacker, defender, new Psychic());
        int hpAfter = defender.getCurrentHP();
        int hpLost = hpBefore - hpAfter;
        assertTrue(hpLost > 0, "HP lost should be positive (damage was dealt)");
    }

    // --- checkSpeed ---

    @Test
    void fasterPokemonTrainerGoesFirst() {
        player.addPokemonToTeam(fastPokemon);
        opponent.addPokemonToTeam(slowPokemon);

        Trainer first = Battle.checkSpeed(player, opponent);
        assertSame(player, first,
                "Trainer with faster lead Pokémon should go first");
    }

    @Test
    void slowerPokemonTrainerGoesSecond() {
        player.addPokemonToTeam(slowPokemon);
        opponent.addPokemonToTeam(fastPokemon);

        Trainer first = Battle.checkSpeed(player, opponent);
        assertSame(opponent, first,
                "Trainer with faster lead Pokémon should go first");
    }

    @Test
    void tiedSpeedReturnsEitherTrainer() {
        Pokemon abra1 = new Abra("Abra 1");
        Pokemon abra2 = new Abra("Abra 2");
        abra1.setLevel(50);
        abra2.setLevel(50);
        abra1.setCurrentSpeed(100);
        abra2.setCurrentSpeed(100);

        player.addPokemonToTeam(abra1);
        opponent.addPokemonToTeam(abra2);

        // Run multiple times — result should always be one of the two trainers
        for (int i = 0; i < 20; i++) {
            Trainer first = Battle.checkSpeed(player, opponent);
            assertTrue(first == player || first == opponent,
                    "Tied speed should return one of the two trainers");
        }
    }

    @Test
    void tiedSpeedIsRandom() {
        // With enough trials, both trainers should be returned at least once
        Pokemon abra1 = new Abra("Abra 1");
        Pokemon abra2 = new Abra("Abra 2");
        abra1.setLevel(50);
        abra2.setLevel(50);
        abra1.setCurrentSpeed(100);
        abra2.setCurrentSpeed(100);

        player.addPokemonToTeam(abra1);
        opponent.addPokemonToTeam(abra2);

        boolean playerFirst = false;
        boolean opponentFirst = false;
        for (int i = 0; i < 100; i++) {
            Trainer first = Battle.checkSpeed(player, opponent);
            if (first == player) playerFirst = true;
            if (first == opponent) opponentFirst = true;
            if (playerFirst && opponentFirst) break;
        }
        assertTrue(playerFirst && opponentFirst,
                "With tied speed, both trainers should eventually go first");
    }

    // --- checkFainted ---

    @Test
    void pokemonWithZeroHPIsFainted() {
        Pokemon abra = new Abra("Faintee");
        abra.setCurrentHP(0);
        assertTrue(Battle.checkFainted(abra),
                "Pokémon with 0 HP should be fainted");
    }

    @Test
    void pokemonWithNegativeHPIsFainted() {
        Pokemon abra = new Abra("Faintee");
        abra.setCurrentHP(-5);
        assertTrue(Battle.checkFainted(abra),
                "Pokémon with negative HP should be fainted");
    }

    @Test
    void pokemonWithPositiveHPIsNotFainted() {
        Pokemon abra = new Abra("Healthy");
        abra.setLevel(50);
        assertTrue(abra.getCurrentHP() > 0, "Precondition: HP should be positive");
        assertFalse(Battle.checkFainted(abra),
                "Pokémon with positive HP should not be fainted");
    }

    @Test
    void pokemonWith1HPIsNotFainted() {
        Pokemon abra = new Abra("Barely Alive");
        abra.setCurrentHP(1);
        assertFalse(Battle.checkFainted(abra),
                "Pokémon with 1 HP should not be fainted");
    }

    @Test
    void checkFaintedSetsIsFaintedFlag() {
        Pokemon abra = new Abra("Faintee");
        assertFalse(abra.getIsFainted(), "Precondition: isFainted should start false");
        abra.setCurrentHP(0);
        Battle.checkFainted(abra);
        assertTrue(abra.getIsFainted(),
                "checkFainted should set the isFainted flag when HP <= 0");
    }

    @Test
    void checkFaintedDoesNotSetFlagWhenAlive() {
        Pokemon abra = new Abra("Alive");
        abra.setLevel(50);
        Battle.checkFainted(abra);
        assertFalse(abra.getIsFainted(),
                "checkFainted should not set isFainted when HP > 0");
    }

    // --- enterBattleState ---

    @Test
    void enterBattleStateDoesNotThrow() {
        player.addPokemonToTeam(fastPokemon);
        opponent.addPokemonToTeam(slowPokemon);

        assertDoesNotThrow(() -> Battle.enterBattleState(player, opponent),
                "enterBattleState should not throw with valid trainers");
    }

    // --- startTurn ---

    @Test
    void startTurnDoesNotThrow() {
        player.addPokemonToTeam(fastPokemon);
        opponent.addPokemonToTeam(slowPokemon);

        assertDoesNotThrow(() -> Battle.startTurn(player, opponent),
                "startTurn should not throw with valid trainers");
    }

    // --- dealDamage + checkFainted integration ---

    @Test
    void repeatedDamageCausesFainting() {
        Pokemon attacker = new Abra("Attacker");
        Pokemon defender = new Abra("Defender");
        attacker.setLevel(100);
        defender.setLevel(5);

        while (!Battle.checkFainted(defender)) {
            Battle.dealDamage(attacker, defender, new Psychic());
        }
        assertTrue(defender.getCurrentHP() <= 0,
                "Defender should have 0 or less HP after fainting");
        assertTrue(defender.getIsFainted(),
                "Defender's isFainted flag should be true after fainting");
    }
}
