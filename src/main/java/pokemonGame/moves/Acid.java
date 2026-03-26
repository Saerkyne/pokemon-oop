package pokemonGame.moves;
import pokemonGame.Move;
import pokemonGame.TypeChart.Type;
import pokemonGame.TypeChart.Category;

public class Acid extends Move {
    public static final Acid INSTANCE = new Acid();

    public Acid() {
        super("Acid", 40, Type.POISON, Category.SPECIAL, 100, 30);
        
        // == Special Effect (Not Yet Implemented) ==
        // 10% chance to lower the target's Sp. Def by 1 stage.
    }

}
