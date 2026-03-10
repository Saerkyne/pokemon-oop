package pokemonGame;

public class Battle {
    public static void main(String[] args) {
        
    }

    public static void dealDamage(Pokemon attacker, Pokemon defender, Move move) {
        // Calculate damage based on move power, attacker's stats, defender's stats, type effectiveness, etc.
        // This is a placeholder for the actual damage calculation logic.
        Attack attack = new Attack();
        int damage = attack.calculateDamage(attacker, defender, move);

        // Apply damage to the defender
        defender.setCurrentHP(defender.getCurrentHP() - damage);

        // Print out the result of the attack
        System.out.println(attacker.getName() + " used " + move.getMoveName() + "!");
        System.out.println(defender.getName() + " took " + damage + " damage and has " + defender.getCurrentHP() + " HP left.");
    }

    public static void enterBattleState(Trainer player, Trainer opponent) {
        // Placeholder for battle state logic, such as turn order, move selection, etc.
        int player1Speed = player.getTeam().get(0).getCurrentSpeed();
        int player2Speed = opponent.getTeam().get(0).getCurrentSpeed();

        if (player1Speed > player2Speed) {
            System.out.println(player.getName() + " goes first!");
            // Player's turn logic
        } else if (player2Speed > player1Speed) {
            System.out.println(opponent.getName() + " goes first!");
            // Opponent's turn logic
        } else {
            System.out.println("It's a tie in speed! Randomly determining who goes first...");
            // Randomly determine who goes first
        }
    }

}
