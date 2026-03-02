package pokemonGame;
import pokemonGame.mons.*;
import pokemonGame.moves.*;

public class App {
    public static void main(String[] args) throws Exception {
        Pokemon pikachu = new Pikachu();
        pikachu.moveset.add(new QuickAttack());
        pikachu.moveset.add(new Scratch());
        pikachu.moveset.add(new Ember());
        pikachu.moveset.add(new LightBall());
        System.out.println(pikachu.name);
        System.out.println(pikachu.moveset.get(0).moveName);
        System.out.println(pikachu.moveset.get(1).moveName);
        System.out.println(pikachu.moveset.get(2).moveName);
        System.out.println(pikachu.moveset.get(3).moveName);

        pikachu.moveset.add(new Thunder());
        System.out.println(pikachu.moveset.get(4).moveName);
    }
}
