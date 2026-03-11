package pokemonGame;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class Pokemon {

    // Initialize attributes for all Pokemon
    private String species;
    private String name;
    private int index;
    private String typePrimary;
    private String typeSecondary;
    private int level;
    private int hpBase;  
    private int attackBase; 
    private int defenseBase; 
    private int specialAttackBase; 
    private int specialDefenseBase;
    private int speedBase;
    private int MaxHP;
    private int currentHP;
    private int currentAttack;
    private int currentDefense;
    private int currentSpecialAttack;
    private int currentSpecialDefense;
    private int currentSpeed;
    private int ivHp;
    private int ivAttack;
    private int ivDefense;
    private int ivSpecialAttack;
    private int ivSpecialDefense;
    private int ivSpeed;
    private int evHp; // This must be 252 or lower for each Pokemon
    private int evAttack; // This must be 252 or lower for each Pokemon
    private int evDefense; // This must be 252 or lower for each Pokemon
    private int evSpecialAttack; // This must be 252 or lower for each Pokemon
    private int evSpecialDefense; // This must be 252 or lower for each Pokemon
    private int evSpeed; // This must be 252 or lower for each Pokemon
    private int evTotal; // This must be 510 or lower for each Pokemon.
    private int expYield; // This is the amount of experience points a Pokemon yields when defeated in battle
    private int currentExp = 0; // This is the current amount of experience points a Pokemon has, which increases when it defeats other Pokemon in battle
    private int[] evYield; // This array holds the EV yield for each stat when this Pokemon is defeated in battle, 
    // in the order of HP, Attack, Defense, Special Attack, Special Defense, Speed
    private Natures nature;
    private String[] statusConditions; // This array holds any status conditions currently affecting the Pokemon (e.g. "Paralyzed", "Burned")
    private Boolean isFainted = false; // This boolean indicates whether the Pokemon has fainted (current HP is 0 or less)
    private final ArrayList<MoveSlot> moveset;
    private static final String[] STAT_NAMES = {"HP", "Attack", "Defense", "Special Attack", "Special Defense", "Speed"};
    
    // each individual Pokémon object keeps its own learnset reference; most species override
    // the accessor to return a shared static list, but the base class provides an empty list
    protected List<LearnsetEntry> learnset = new ArrayList<>();

    // Constructors - One is a basic constructor with default stats, the other overloads to allow for custom stats
    
    protected Pokemon(String species, int index, String typePrimary, String typeSecondary) {
        this.species = species;
        this.name = species; // Default name is the same as species
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
        this.evHp = 0;
        this.evAttack = 0;
        this.evDefense = 0;
        this.evSpecialAttack = 0;
        this.evSpecialDefense = 0;
        this.evSpeed = 0;
        this.moveset = new ArrayList<MoveSlot>(4);
        // assign a random nature immediately
        Natures.assignRandom(this);
    }

    protected Pokemon(String species, int index, String typePrimary, String typeSecondary, int level, int hp, int attack, int defense, int specialAttack, int specialDefense, int speed) {
        this.species = species;
        this.name = species; // Default name is the same as species
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
        this.evHp = 0;
        this.evAttack = 0;
        this.evDefense = 0;
        this.evSpecialAttack = 0;
        this.evSpecialDefense = 0;
        this.evSpeed = 0;
        this.moveset = new ArrayList<MoveSlot>(4);
        // random nature for custom‑stat constructor as well
        Natures.assignRandom(this);
    }


    // --- Moveset management (no I/O — callers handle user interaction) ---

    /**
     * Returns true when the moveset already has 4 moves and any new move
     * would require replacing an existing one.
     */
    public boolean isMovesetFull() {
        return moveset.size() >= 4;
    }

    /**
     * Add a move to the moveset.  Only succeeds when there is an open slot
     * (fewer than 4 moves).  Returns true if the move was added, false if
     * the moveset is full (caller should use {@link #replaceMove} instead).
     */
    public boolean addMove(Move move) {
        if (moveset.size() >= 4) {
            return false; // Moveset full — caller must decide which slot to replace
        }
        moveset.add(new MoveSlot(move));
        return true;
    }

    /**
     * Replace the move in the given slot (0-based index) with a new move.
     * Returns true on success, false if the slot index is out of range.
     */
    public boolean replaceMove(int slot, Move newMove) {
        if (slot < 0 || slot >= moveset.size()) {
            return false;
        }
        moveset.set(slot, new MoveSlot(newMove));
        return true;
    }


    // Getters for attributes
    public ArrayList<MoveSlot> getMoveset() {
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

    public String getSpecies() {
        return species;
    }

    public Boolean getIsFainted() {
        return isFainted;
    }

    public int getIndex() {
        return index;
    }

    public String[] getStatusConditions() {
        return statusConditions;
    }

    public int getLevel() {
        return level;
    }

    public int getCurrentExp() {
        return currentExp;
    }

    public int getExpYield() {
        return expYield;
    }

    public Natures getNature() {
        return nature;
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

    public int getMaxHP() {
        return MaxHP;
    }

    public int getCurrentHP() {
        return currentHP;
    }

    public int getCurrentAttack() {
        return currentAttack;
    }

    public int getCurrentDefense() {
        return currentDefense;
    }

    public int getCurrentSpecialAttack() {
        return currentSpecialAttack;
    }

    public int getCurrentSpecialDefense() {
        return currentSpecialDefense;
    }

    public int getCurrentSpeed() {
        return currentSpeed;
    }

    public int getIvHp() {
        return ivHp;
    }

    public int getIvAttack() {
        return ivAttack;
    }

    public int getIvDefense() {
        return ivDefense;
    }

    public int getIvSpecialAttack() {
        return ivSpecialAttack;
    }

    public int getIvSpecialDefense() {
        return ivSpecialDefense;
    }

    public int getIvSpeed() {
        return ivSpeed;
    }

    public int getEvHp() {
        return evHp;
    }

    public int getEvAttack() {
        return evAttack;
    }

    public int getEvDefense() {
        return evDefense;
    }

    public int getEvSpecialAttack() {
        return evSpecialAttack;
    }

    public int getEvSpecialDefense() {
        return evSpecialDefense;
    }

    public int getEvSpeed() {
        return evSpeed;
    }

    public int getEvTotal() {
        return evTotal;
    }

    public String[] getEvYield() {
        String[] stringYield = new String[evYield.length];
        for (int i = 0; i < evYield.length; i++) {
            stringYield[i] = STAT_NAMES[i] + ": " + evYield[i];
        }
        
        return stringYield;
    }

    // Special Methods to get the appropriate attack or defense stat based on the move's category (Physical, Special, or Status)
    
    public int getAttackStatForMove(Move move) {
        if ("Physical".equals(move.getMoveCategory())) {
            int physAttack = getCurrentAttack();
            return physAttack; 
        } else if ("Special".equals(move.getMoveCategory())) {
            int specAttack = getCurrentSpecialAttack();
            return specAttack; 
        } else {
            return 0; // Status moves don't use attack stats
        }
    }

    public int getDefenseStatForMove(Move move) {
        if ("Physical".equals(move.getMoveCategory())) {
            int physDefense = getCurrentDefense();
            return physDefense; 
        } else if ("Special".equals(move.getMoveCategory())) {
            int specDefense = getCurrentSpecialDefense();
            return specDefense; 
        } else {
            return 0; // Status moves don't use defense stats
        }
    }

    // Direct Setters for attributes
    public void setLevel(int level) {
        this.level = level;
        calculateCurrentStats();
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setNature(Natures nature) {
        this.nature = nature;
    }

    public void setIsFainted(Boolean isFainted) {
        this.isFainted = isFainted;
    }

    public void setStatusConditions(String[] statusConditions) {
        this.statusConditions = statusConditions;
    }   
    
    public void setHpBase(int hp) {
        this.hpBase = hp;
        calculateCurrentStats();
    }   

    public void setAttackBase(int attack) {
        this.attackBase = attack;
        calculateCurrentStats();
    }

    public void setDefenseBase(int defense) {
        this.defenseBase = defense;
        calculateCurrentStats();
    }

    public void setSpecialAttackBase(int specialAttack) {
        this.specialAttackBase = specialAttack;
        calculateCurrentStats();
    }   

    public void setSpecialDefenseBase(int specialDefense) {
        this.specialDefenseBase = specialDefense;
        calculateCurrentStats();
    }

    public void setSpeedBase(int speed) {
        this.speedBase = speed;
        calculateCurrentStats();
    }

    public void setMaxHP(int MaxHP) {
        this.MaxHP = MaxHP;
    }

    public void setCurrentHP(int currentHP) {
        this.currentHP = currentHP;
    }

    public void setCurrentAttack(int currentAttack) {
        this.currentAttack = currentAttack;
    }

    public void setCurrentDefense(int currentDefense) {
        this.currentDefense = currentDefense;
    }

    public void setCurrentSpecialAttack(int currentSpecialAttack) {
        this.currentSpecialAttack = currentSpecialAttack;
    }

    public void setCurrentSpecialDefense(int currentSpecialDefense) {
        this.currentSpecialDefense = currentSpecialDefense;
    }

    public void setCurrentSpeed(int currentSpeed) {
        this.currentSpeed = currentSpeed;
    }

    public void setCurrentExp(int currentExp) {
        this.currentExp = currentExp;
    }   

    public void setIvHp(int ivHp) {
        this.ivHp = ivHp;
    }

    public void setIvAttack(int ivAttack) {
        this.ivAttack = ivAttack;
    }

    public void setIvDefense(int ivDefense) {
        this.ivDefense = ivDefense;
    }

    public void setIvSpecialAttack(int ivSpecialAttack) {
        this.ivSpecialAttack = ivSpecialAttack;
    }

    public void setIvSpecialDefense(int ivSpecialDefense) {
        this.ivSpecialDefense = ivSpecialDefense;
    }

    public void setIvSpeed(int ivSpeed) {
        this.ivSpeed = ivSpeed;
    }

    public void setEvHp(int addedEvHp) {
        if (evTotal >= 510) return;
        int roomTotal = 510 - evTotal;
        int roomStat = 252 - evHp;
        int actual = Math.min(addedEvHp, Math.min(roomTotal, roomStat));
        if (actual <= 0) return;
        this.evHp += actual;
        this.evTotal += actual;
    }

    public void setEvAttack(int addedEvAttack) {
        if (evTotal >= 510) return;
        int roomTotal = 510 - evTotal;
        int roomStat = 252 - evAttack;
        int actual = Math.min(addedEvAttack, Math.min(roomTotal, roomStat));
        if (actual <= 0) return;
        this.evAttack += actual;
        this.evTotal += actual;
    }

    public void setEvDefense(int addedEvDefense) {
        if (evTotal >= 510) return;
        int roomTotal = 510 - evTotal;
        int roomStat = 252 - evDefense;
        int actual = Math.min(addedEvDefense, Math.min(roomTotal, roomStat));
        if (actual <= 0) return;
        this.evDefense += actual;
        this.evTotal += actual;
    }

    public void setEvSpecialAttack(int addedEvSpecialAttack) {
        if (evTotal >= 510) return;
        int roomTotal = 510 - evTotal;
        int roomStat = 252 - evSpecialAttack;
        int actual = Math.min(addedEvSpecialAttack, Math.min(roomTotal, roomStat));
        if (actual <= 0) return;
        this.evSpecialAttack += actual;
        this.evTotal += actual;
    }

    public void setEvSpecialDefense(int addedEvSpecialDefense) {
        if (evTotal >= 510) return;
        int roomTotal = 510 - evTotal;
        int roomStat = 252 - evSpecialDefense;
        int actual = Math.min(addedEvSpecialDefense, Math.min(roomTotal, roomStat));
        if (actual <= 0) return;
        this.evSpecialDefense += actual;
        this.evTotal += actual;
    }

    public void setEvSpeed(int addedEvSpeed) {
        if (evTotal >= 510) return;
        int roomTotal = 510 - evTotal;
        int roomStat = 252 - evSpeed;
        int actual = Math.min(addedEvSpeed, Math.min(roomTotal, roomStat));
        if (actual <= 0) return;
        this.evSpeed += actual;
        this.evTotal += actual;
    }

    public void setEvYield(int[] evYield) {
        this.evYield = evYield;
    }

    // Modifiers for attributes
    public void levelUp() {
        this.level++;
        this.calculateCurrentStats();
        // Placeholder for actual stat increases on level up
    }


    // Methods for calculations
    public int calcMaxHP(int hpBase, int level, int ivHp, int ev) {

        // Calculating the actual HP of a Pokemon requires the base HP stat, the Pokemon's level, 
        // and its individual values (IVs) and effort values (EVs).
        // The formula is: HP = ((((2 * baseHP) + IV + (EV / 4)) * Level) / 100) + Level + 10
        // We want an integer, so we are going to do the calculations in steps to ensure we don't lose 
        // precision until the end when we can round down to an integer.
        int calcHP = ev / 4;
        calcHP = (2 * hpBase + ivHp + calcHP);
        calcHP = calcHP * level;
        calcHP = calcHP / 100;
        calcHP = calcHP + level + 10;
        return calcHP;
    }

    public int calcCurrentStat(int baseStat, int level, int iv, int ev, double natureModifier) {
        // The formula for calculating the current value of a non-HP stat is: 
        // Stat = (((((2 * baseStat) + IV + (EV/4)) * Level) / 100) + 5) * Nature
        // For now, we will ignore the Nature modifier and just calculate the stat without it.
        // A 1 will be used as a placeholder for the Nature modifier in the future when that is implemented.
        int calcStat = ev / 4;
        calcStat = (2 * baseStat + iv + calcStat);
        calcStat = calcStat * level;
        calcStat = calcStat / 100;
        calcStat = calcStat + 5;
        calcStat = (int) Math.floor(calcStat * natureModifier);
        return calcStat;
    }

    // Method for generating a random IV value between 0 and 31 for each stat
    public void generateRandomIVs() {

        Random rand = new Random();
        this.ivHp = rand.nextInt(32);
        this.ivAttack = rand.nextInt(32);
        this.ivDefense = rand.nextInt(32);
        this.ivSpecialAttack = rand.nextInt(32);
        this.ivSpecialDefense = rand.nextInt(32);
        this.ivSpeed = rand.nextInt(32);
    }


    // Wrapper method for stat calculation
    public void calculateCurrentStats() {
        this.MaxHP = calcMaxHP(getHpBaseStat(), getLevel(), getIvHp(), getEvHp());
        this.currentAttack = calcCurrentStat(getAttackBaseStat(), getLevel(), getIvAttack(), getEvAttack(), getNature().modifierFor(Stat.ATTACK));
        this.currentDefense = calcCurrentStat(getDefenseBaseStat(), getLevel(), getIvDefense(), getEvDefense(), getNature().modifierFor(Stat.DEFENSE));
        this.currentSpecialAttack = calcCurrentStat(getSpecialAttackBaseStat(), getLevel(), getIvSpecialAttack(), getEvSpecialAttack(), getNature().modifierFor(Stat.SPECIAL_ATTACK));
        this.currentSpecialDefense = calcCurrentStat(getSpecialDefenseBaseStat(), getLevel(), getIvSpecialDefense(), getEvSpecialDefense(), getNature().modifierFor(Stat.SPECIAL_DEFENSE));
        this.currentSpeed = calcCurrentStat(getSpeedBaseStat(), getLevel(), getIvSpeed(), getEvSpeed(), getNature().modifierFor(Stat.SPEED));
        this.currentHP = this.MaxHP; // Heal to full HP whenever stats are recalculated (e.g. on level up)
        // I know that this may cause issues, review the best place for this later on. 
    }

    /**
     * Apply a random nature to this Pokémon.  This is just a convenience wrapper
     * around {@link Natures#assignRandom} so callers can write
     * <code>p.applyNature()</code> when they don't care which nature is chosen.
     */
    public void applyNature() {
        Natures.assignRandom(this);
    }

    /**
     * Return the learnset associated with this Pokémon instance.  Species classes should
     * override this method (typically returning a static list) so that calling
     * <code>somePokemon.getLearnset()</code> yields the correct catalog for that
     * species.  The default implementation returns an (initially empty) instance
     * list, which allows subclasses that do not define learnsets to compile.
     */
    public List<LearnsetEntry> getLearnset() {
        return learnset;
    }
      
}
