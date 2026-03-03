package pokemonGame;
public abstract class Move {
    String moveName;
    int movePower;
    //moveTypes are the elemental types of the move, such as fire, water, grass, etc.
    String moveType;
    //moveCategory can be physical, special, or status
    String moveCategory;
    int accuracy;
    int pp;

    protected Move(String moveName, int movePower, String moveType, String moveCategory, int accuracy, int pp) {
        this.moveName = moveName;
        this.movePower = movePower;
        this.moveType = moveType;
        this.moveCategory = moveCategory;
        this.accuracy = accuracy;
        this.pp = pp;
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

    public int getPp() {
        return pp;
    }
}