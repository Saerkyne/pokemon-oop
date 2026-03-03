package pokemonGame;
import pokemonGame.mons.*;
import pokemonGame.moves.*;

public class App {
    public static void main(String[] args) throws Exception {
        // Create some Pokemon
        Pokemon pikachu = new Pikachu();
        Pokemon charmander = new Charmander();

        pikachu.addMove(new Thunder());
        charmander.addMove(new Ember());

        //pikachu uses Thunder on Charmander
        String moveType = pikachu.getMoveset().get(0).moveType;
        String  pokemonType = charmander.getType();
        TypeChart typeChart = new TypeChart();
        float effectiveness = typeChart.getEffectiveness(moveType, pokemonType);
        System.out.println("Pikachu used Thunder on Charmander!");
        if (effectiveness > 1) {
            System.out.println("It's super effective!");
        } else if (effectiveness < 1) {
            System.out.println("It's not very effective...");
        } else {
            System.out.println("It's effective.");

        }

        pikachu.addMove(new IronTail());

        //Pikachu uses Iron Tail on Charmander
        moveType = pikachu.getMoveset().get(1).moveType;
        pokemonType = charmander.getType();
        effectiveness = typeChart.getEffectiveness(moveType, pokemonType);
        System.out.println("Pikachu used Iron Tail on Charmander!");
        if (effectiveness > 1) {
            System.out.println("It's super effective!");
        } else if (effectiveness < 1) {
            System.out.println("It's not very effective...");
        } else {
            System.out.println("It's effective.");  
        }
    }
}
