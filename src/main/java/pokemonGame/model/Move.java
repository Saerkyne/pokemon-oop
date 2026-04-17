package pokemonGame.model;
import pokemonGame.battle.Attack;
import pokemonGame.core.TypeChart.Category;
import pokemonGame.core.TypeChart.Type;

/**
 * Abstract base class for all Pokémon moves. Each concrete subclass (in
 * {@code pokemonGame.moves}) represents one move and passes its fixed stats
 * (name, power, type, category, accuracy, PP) to this constructor.
 *
 * <p>Move objects are immutable game-rule data. Per-instance PP tracking
 * is handled by {@link MoveSlot}.</p>
 *
 * @see MoveSlot
 * @see Attack
 */
public abstract class Move {
    
    private final String moveName;
    private final int movePower;
    private final Type moveType;
    private final Category moveCategory;
    private final int accuracy;
    private final int maxPp;

    protected Move(String moveName, int movePower, Type moveType, Category moveCategory, int accuracy, int maxPp) {
        this.moveName = moveName;
        this.movePower = movePower;
        this.moveType = moveType;
        this.moveCategory = moveCategory;
        this.accuracy = accuracy;
        this.maxPp = maxPp;
    }

    public String getMoveName() {
        return moveName;
    }
    
    public int getMovePower() {
        return movePower;
    }

    public Type getMoveType() {
        return moveType;
    }

    public Category getMoveCategory() {
        return moveCategory;
    }

    public int getAccuracy() {
        return accuracy;
    }

    public int getMaxPp() {
        return maxPp;
    }
}