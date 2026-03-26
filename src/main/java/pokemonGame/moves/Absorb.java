package pokemonGame.moves;
import pokemonGame.Move;
import pokemonGame.TypeChart.Type;
import pokemonGame.TypeChart.Category;

public class Absorb extends Move {
    // Flyweight singleton — Move objects are immutable, so one shared instance is safe.
    // MoveSlot handles per-Pokémon mutable state (current PP, etc.).
    public static final Absorb INSTANCE = new Absorb();

    public Absorb() {
        super("Absorb", 20, Type.GRASS, Category.SPECIAL, 100, 25);
        // == Special Effect (Not Yet Implemented) ==
        // User recovers 50% of the damage dealt to the target.
    }

}
