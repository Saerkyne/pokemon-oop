package pokemonGame;
public abstract class Move {
    private String moveName;
    private int movePower;
    //moveTypes are the elemental types of the move, such as fire, water, grass, etc.
    private String moveType;
    //moveCategory can be physical, special, or status
    private String moveCategory;
    private int accuracy;
    private int maxPp;

    protected Move(String moveName, int movePower, String moveType, String moveCategory, int accuracy, int maxPp) {
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

    public String getType() {
        return moveType;
    }

    public String getMoveCategory() {
        return moveCategory;
    }

    public int getAccuracy() {
        return accuracy;
    }

    public int getMaxPp() {
        return maxPp;
    }
}