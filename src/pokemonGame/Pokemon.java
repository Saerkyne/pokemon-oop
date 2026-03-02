package pokemonGame;
import java.util.ArrayList;


public class Pokemon {
    String name;
    int index;
    String type;
    public ArrayList<Move> moveset;

    protected Pokemon(String name, int index, String type) {
        this.name = name;
        this.index = index;
        this.type = type;
        this.moveset = new ArrayList<Move>(4);
    }
}
