package pokemonGame;
public abstract class Move {
    String moveName;
    int movePower;
    //moveTypes are the elemental types of the move, such as fire, water, grass, etc.
    String moveType;
    //moveCategory can be physical, special, or status
    String moveCategory;

    protected Move(String moveName, int movePower, String moveType, String moveCategory) {
        this.moveName = moveName;
        this.movePower = movePower;
        this.moveType = moveType;
        this.moveCategory = moveCategory;
    }
}