package pokemonGame;
import pokemonGame.TypeChart.Type;
import pokemonGame.TypeChart.Category;

public abstract class Move {
    private String moveName;
    private int movePower;
    private Type moveType;
    private Category moveCategory;
    private int accuracy;
    private int maxPp;

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