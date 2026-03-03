package pokemonGame;
import java.util.ArrayList;


public class Pokemon {
    String name;
    int index;
    String typePrimary;
    String typeSecondary;
    int level;
    int hpBase; // also used as stat exp value 
    int attackBase; // also used as stat exp value
    int defenseBase; // also used as stat exp value
    int specialAttackBase; // also used as stat exp value
    int specialDefenseBase; // also used as stat exp value
    int speedBase; // also used as stat exp value
    int statExpHP;
    int statExpAttack;
    int statExpDefense;
    int statExpSpecialAttack;
    int statExpSpecialDefense;
    int statExpSpeed;
    private final ArrayList<Move> moveset;


    protected Pokemon(String name, int index, String typePrimary, String typeSecondary) {
        this.name = name;
        this.index = index;
        this.typePrimary = typePrimary;
        this.typeSecondary = typeSecondary;
        this.level = 5;
        this.attackBase = 50;
        this.defenseBase = 50;
        this.specialAttackBase = 50;
        this.specialDefenseBase = 50;
        this.hpBase = 50;
        this.speedBase = 50;
        this.statExpHP = 0;
        this.statExpAttack = 0;
        this.statExpDefense = 0;
        this.statExpSpecialAttack = 0;
        this.statExpSpecialDefense = 0;
        this.statExpSpeed = 0;
        this.moveset = new ArrayList<Move>(4);
    }

    protected Pokemon(String name, int index, String typePrimary, String typeSecondary, int level, int hp, int attack, int defense, int specialAttack, int specialDefense, int speed) {
        this.name = name;
        this.index = index;
        this.typePrimary = typePrimary;
        this.typeSecondary = typeSecondary;
        this.level = level;
        this.hpBase = hp;
        this.attackBase = attack;
        this.defenseBase = defense;
        this.specialAttackBase = specialAttack;
        this.specialDefenseBase = specialDefense;
        this.speedBase = speed;
        this.statExpHP = 0;
        this.statExpAttack = 0;
        this.statExpDefense = 0;
        this.statExpSpecialAttack = 0;
        this.statExpSpecialDefense = 0;
        this.statExpSpeed = 0;
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

    public int statExpCalc(int baseStat, int level, int statExp) {
        int modifiedStat = (int) Math.floor(Math.min(Math.ceil(Math.sqrt(statExp)), 255) * (level / 400));
        return modifiedStat;
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

    public String getName() {
        return name;
    }

    public int getLevel() {
        return level;
    }

    public int getHpBaseStat() {
        return hpBase;
    }   
    
    public int getAttackBaseStat() {
        return attackBase;
    }

    public int getDefenseBaseStat() {
        return defenseBase;
    }

    public int getSpecialAttackBaseStat() {
        return specialAttackBase;
    }

    public int getSpecialDefenseBaseStat() {
        return specialDefenseBase;
    }

    public int getSpeedBaseStat() {
        return speedBase;
    }

    public int getAttackStatForMove(Move move) {
        if (move.getMoveCategory() == "Physical") {
            int physAttack = getAttackBaseStat();
            return physAttack; 
        } else if (move.getMoveCategory() == "Special") {
            int specAttack = getSpecialAttackBaseStat();
            return specAttack; 
        } else {
            return 0; // Status moves don't use attack stats
        }
    }

    public int getDefenseStatForMove(Move move) {
        if (move.getMoveCategory() == "Physical") {
            int physDefense = getDefenseBaseStat();
            return physDefense; 
        } else if (move.getMoveCategory() == "Special") {
            int specDefense = getSpecialDefenseBaseStat();
            return specDefense; 
        } else {
            return 0; // Status moves don't use defense stats
        }
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public void levelUp() {
        this.level++;
        // Placeholder for actual stat increases on level up
    }

    public void setHp(int hp) {
        this.hpBase = hp;
    }   

    public void setAttack(int attack) {
        this.attackBase = attack;
    }

    public void setDefense(int defense) {
        this.defenseBase = defense;
    }

    public void setSpecialAttack(int specialAttack) {
        this.specialAttackBase = specialAttack;
    }   

    public void setSpecialDefense(int specialDefense) {
        this.specialDefenseBase = specialDefense;
    }

    public void setSpeed(int speed) {
        this.speedBase = speed;
    }

    public ArrayList<Integer> getStatExpValues(Pokemon pokemon) {
        ArrayList<Integer> statExpValues = new ArrayList<>();
        statExpValues.add(pokemon.statExpHP);
        statExpValues.add(pokemon.statExpAttack);
        statExpValues.add(pokemon.statExpDefense);
        statExpValues.add(pokemon.statExpSpecialAttack);
        statExpValues.add(pokemon.statExpSpecialDefense);
        statExpValues.add(pokemon.statExpSpeed);
        return statExpValues;
    }

    public void gainStatExp(Pokemon pokemon, int hpExp, int attackExp, int defenseExp, int specialAttackExp, int specialDefenseExp, int speedExp) {
        pokemon.statExpHP += hpExp;
        pokemon.statExpAttack += attackExp;
        pokemon.statExpDefense += defenseExp;
        pokemon.statExpSpecialAttack += specialAttackExp;
        pokemon.statExpSpecialDefense += specialDefenseExp;
        pokemon.statExpSpeed += speedExp;
    }

      
}
