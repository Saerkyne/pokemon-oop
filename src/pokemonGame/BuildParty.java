package pokemonGame;
import pokemonGame.mons.*;
import java.util.Scanner;

public class BuildParty {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Pokemon[] party = new Pokemon[6];

        for (int i = 0; i < party.length; i++) {
            System.out.println("Choose a Pokemon for slot " + (i + 1) + ":");
            System.out.println("1. Bulbasaur");
            System.out.println("2. Squirtle");
            System.out.println("3. Charmander");
            System.out.println("4. Pikachu");
            System.out.println("5. Eevee");
            System.out.println("6. Mewtwo");

            int choice = scanner.nextInt();
            switch (choice) {
                case 1:
                    party[i] = new Bulbasaur();
                    break;
                case 2:
                    party[i] = new Squirtle();
                    break;
                case 3:
                    party[i] = new Charmander();
                    break;
                case 4:
                    party[i] = new Pikachu();
                    break;
                case 5:
                    party[i] = new Eevee();
                    break;
                case 6:
                    party[i] = new Mewtwo();
                    break;
                default:
                    System.out.println("Invalid choice, assigning Pikachu by default.");
                    party[i] = new Pikachu();
            }
        }

        System.out.println("Your party:");
        for (Pokemon pokemon : party) {
            System.out.println(pokemon.getName() + " (" + pokemon.getTypePrimary() + "/" + pokemon.getTypeSecondary() + ")");
        }

        scanner.close();

    }
}
