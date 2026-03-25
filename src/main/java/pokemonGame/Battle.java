package pokemonGame;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Battle {
    private static final Logger LOGGER = LoggerFactory.getLogger(Battle.class);

    public Battle() {
        // Constructor for Battle class, if needed
    }

    public static void dealDamage(Pokemon attacker, Pokemon defender, Move move) {
        // Calculate damage based on move power, attacker's stats, defender's stats, type effectiveness, etc.
        // This is a placeholder for the actual damage calculation logic.
        Attack attack = new Attack();
        int damage = attack.calculateDamage(attacker, defender, move);

        // Check for damage amount, clamp to 0 if it would go negative
        if (damage > defender.getCurrentHP()) {
            damage = defender.getCurrentHP();
        }
        
        // Apply damage to the defender
        defender.setCurrentHP(defender.getCurrentHP() - damage);

        // Print out the result of the attack
        LOGGER.info("{} used {}!", attacker.getNickname(), move.getMoveName());

        if (!checkFainted(defender)) {
            LOGGER.info("{} took {} damage and has {} HP left.", defender.getNickname(), damage, defender.getCurrentHP());
        } else {
            LOGGER.info("{} took {} damage and has fainted!", defender.getNickname(), damage);

        }

        
    }

    public static void enterBattleState(Trainer player, Trainer opponent) {
        // Placeholder for battle state logic, such as turn order, move selection, etc.
        // For now, we will just check speed to determine who goes first and 
        // return the trainer who goes first.
        Trainer speedCheck = checkSpeed(player, opponent);
        // Additional battle logic would go here, such as handling turns, 
        // checking for fainted Pokemon, etc.

        LOGGER.info("Battle has started between {} and {}!", player.getName(), opponent.getName());
        LOGGER.info("{} will go first!", speedCheck.getName());

        for (Pokemon pokemon : player.getTeam()) {
            Boolean checkFainted = checkFainted(pokemon);
        
            if (checkFainted) {
                LOGGER.info("{} has fainted and cannot battle!", pokemon.getNickname());
            } else {
                LOGGER.info("{} is ready to battle!", pokemon.getNickname());
            }          
        }
        

        
    }

    public static void startTurn(Trainer player, Trainer opponent) {
        // Placeholder for turn logic, such as move selection, damage calculation, etc.
    }

    
    public static Trainer checkSpeed(Trainer player, Trainer opponent) {
        int player1Speed = player.getTeam().get(0).getCurrentSpeed();
        int player2Speed = opponent.getTeam().get(0).getCurrentSpeed();

        if (player1Speed > player2Speed) {
            return player;
        } else if (player2Speed > player1Speed) {
            return opponent;
        } else {
            // Randomly determine who goes first
            if (Math.random() < 0.5) {
                // Player's turn logic
                return player;
            } else {
                // Opponent's turn logic
                return opponent;
            }
        }
    }

    public static Boolean checkFainted(Pokemon pokemon) {
        if (pokemon.getCurrentHP() <= 0) {
            pokemon.setIsFainted(true);
            return pokemon.getIsFainted();
        }
        return pokemon.getIsFainted();
    }
}
