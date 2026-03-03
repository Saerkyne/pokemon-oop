package pokemonGame;
import java.util.ArrayList;


public class Pokemon {
    String name;
    int index;
    String typePrimary;
    String typeSecondary;
    int level;
    private final ArrayList<Move> moveset;

    protected Pokemon(String name, int index, String typePrimary) {
        this.name = name;
        this.index = index;
        this.typePrimary = typePrimary;
        this.level = 5;
        this.moveset = new ArrayList<Move>(4);

    }

    protected Pokemon(String name, int index, String typePrimary, String typeSecondary) {
        this.name = name;
        this.index = index;
        this.typePrimary = typePrimary;
        this.typeSecondary = typeSecondary;
        this.level = 5;
        this.moveset = new ArrayList<Move>(4);
    }

    public void addMove(Move move) {
        if (moveset.size() >= 4) {
            System.out.println("A Pokemon can only have 4 moves in its moveset.");
            System.out.println("Would you like to replace one of the existing moves with " + move.moveName + "? (yes/no)");
            String response = System.console().readLine();
            if (response.equals("yes")) {
                System.out.println("Which move would you like to replace? (1-4)");
                for (int i = 0; i < moveset.size(); i++) {
                    System.out.println((i + 1) + ": " + moveset.get(i).moveName);
                }
                int moveToReplace = Integer.parseInt(System.console().readLine());
                if (moveToReplace >= 1 && moveToReplace <= 4) {
                    moveset.set(moveToReplace - 1, move);
                } else {
                    System.out.println("Invalid move number. Move not added.");
                }
            }
        } else {
            moveset.add(move);
        }
    }

    public ArrayList<Move> getMoveset() {
        return moveset;
    }

    public String getTypePrimary() {
        return typePrimary;
    }

    public String getTypeSecondary() {
        return typeSecondary;
    }

      
}
