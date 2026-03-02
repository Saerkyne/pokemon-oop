package pokemonGame;
import pokemonGame.mons.*;
import pokemonGame.moves.*;

public class App {
    public static void main(String[] args) throws Exception {
        Pokemon pikachu = new Pikachu();
        pikachu.addMove(new QuickAttack());
        pikachu.addMove(new Thunder());
        pikachu.addMove(new Scratch());
        pikachu.addMove(new LightBall());
        pikachu.addMove(new Ember());
        System.out.println(pikachu.name);
        System.out.println(pikachu.getMoveset().get(0).moveName);
        System.out.println(pikachu.getMoveset().get(1).moveName);
        System.out.println(pikachu.getMoveset().get(2).moveName);
        System.out.println(pikachu.getMoveset().get(3).moveName);
    }
}
