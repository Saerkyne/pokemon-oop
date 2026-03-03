package pokemonGame;
import pokemonGame.mons.*;
import pokemonGame.moves.*;

public class App {
    public static void main(String[] args) throws Exception {
        // Create some Pokemon
        Pokemon pikachu = new Pikachu();
        Pokemon gengar = new Gengar();

        pikachu.addMove(new Thunder());
        pikachu.addMove(new QuickAttack());
        pikachu.addMove(new PoisonSting());
        pikachu.addMove(new Crunch());


        //Iterate through Pikachu's moves and calculate effectiveness against Gengar instead of hardcoded attacks
        for (Move move : pikachu.getMoveset()) {
            System.out.println("Calculating effectiveness of " + move.moveName + " against Gengar...");
            String moveType = move.moveType;
            System.out.println("Move Type: " + moveType);
            String pokemonTypePrimary = gengar.getTypePrimary();
            System.out.println("Gengar's primary type: " + pokemonTypePrimary);
            try {
                String pokemonTypeSecondary = gengar.getTypeSecondary();
                System.out.println("Gengar's secondary type: " + pokemonTypeSecondary);
            } catch (Exception e) {
                System.out.println("Gengar has no secondary type.");

            }
            TypeChart typeChart = new TypeChart();
            float effectivenessPrimary = typeChart.getEffectiveness(moveType, pokemonTypePrimary);
            System.out.println("Effectiveness against primary type: " + effectivenessPrimary);
            float effectivenessSecondary = 1f; // Default value if no secondary type
            try {
                String pokemonTypeSecondary = gengar.getTypeSecondary();
                effectivenessSecondary = typeChart.getEffectiveness(moveType, pokemonTypeSecondary);
            } catch (Exception e) {
                System.out.println("No secondary type to calculate effectiveness against.");
            }
            System.out.println("Effectiveness against secondary type: " + effectivenessSecondary);
            System.out.println("Pikachu used " + move.moveName + " on Gengar!");
            if (effectivenessPrimary > 1 || effectivenessSecondary > 1) {
                System.out.println("It's super effective!");
            } else if (effectivenessPrimary < 1 && effectivenessSecondary < 1) {
                System.out.println("It's not very effective...");
            } else if (effectivenessPrimary == 0 || effectivenessSecondary == 0) {
                System.out.println("It has no effect.");
            } else {
                System.out.println("It's effective.");  
            }
        }
    }
}
