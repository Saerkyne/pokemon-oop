package pokemonGame;

public class Battle {
    public static void main(String[] args) {
        
    }

    public static void dealDamage(Pokemon attacker, Pokemon defender, Move move) {
        // Calculate damage based on move power, attacker's stats, defender's stats, type effectiveness, etc.
        // This is a placeholder for the actual damage calculation logic.
        Attack attack = new Attack();
        int damage = attack.calculateDamage(attacker, defender, move);

        // Check for damage amount, clamp to 0 if it would go negative
        try {
            if ((defender.getCurrentHP() - damage) < 0) {
                throw new IllegalArgumentException("Damage cannot send HP negative. Setting defender HP to 0.");
            }
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            damage = defender.getCurrentHP();
        }
        
        // Apply damage to the defender
        defender.setCurrentHP(defender.getCurrentHP() - damage);

        // Print out the result of the attack
        System.out.println(attacker.getNickname() + " used " + move.getMoveName() + "!");

        if (!checkFainted(defender)) {
            System.out.println(defender.getNickname() + " took " + damage + " damage and has " + defender.getCurrentHP() + " HP left.");
        } else {
            System.out.println(defender.getNickname() + " took " + damage + " damage and has fainted!");

        }

        
    }

    public static void enterBattleState(Trainer player, Trainer opponent) {
        // Placeholder for battle state logic, such as turn order, move selection, etc.
        // For now, we will just check speed to determine who goes first and 
        // return the trainer who goes first.
        Trainer speedCheck = checkSpeed(player, opponent);
        // Additional battle logic would go here, such as handling turns, 
        // checking for fainted Pokemon, etc.

        System.out.println("Battle has started between " + player.getName() + " and " + opponent.getName() + "!");
        System.out.println(speedCheck.getName() + " will go first!");

        for (Pokemon pokemon : player.getTeam()) {
            Boolean checkFainted = checkFainted(pokemon);
        
            if (checkFainted) {
                System.out.println(pokemon.getNickname() + " has fainted and cannot battle!");
            } else {
                System.out.println(pokemon.getNickname() + " is ready to battle!");
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
