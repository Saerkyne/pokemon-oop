package pokemonGame;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.Collections;
import pokemonGame.TypeChart.Type;


// =============================================================================================
// CODE REVIEW NOTE — "God Class" Decomposition (2026-03-25 Review, Item #20)
//
// At ~950 lines, Pokemon.java handles too many distinct responsibilities:
//   1. Identity & species data (species, nickname, dex index, types)
//   2. Base stats, IVs, EVs, and current stats (storage + cap enforcement)
//   3. Stat calculation formulas (calcMaxHP, calcCurrentStat, calculateCurrentStats)
//   4. Moveset management (addMove, replaceMove, isMovesetFull)
//   5. Game-logic modifiers (levelUp, healToFull, applyNature)
//   6. The static createPokemon() factory (151-case switch — now commented out)
//
// The "God class" anti-pattern emerges when one class accumulates so many responsibilities
// that it becomes the central hub everything depends on. The problem isn't just line count —
// it's that changes to EV logic, stat formulas, moveset rules, or the factory can all
// break each other because they share the same class scope and private state.
//
// PROPOSED DECOMPOSITION (three candidates, ordered by impact):
//
// ── Candidate 1: Extract a StatCalculator utility class ──
//    Move calcMaxHP(), calcCurrentStat(), and calculateCurrentStats() into a new
//    StatCalculator class with static methods. These are pure functions — they take
//    numbers in and return numbers out, with no dependency on Pokemon's internal state
//    beyond what's passed as arguments. Pokemon would call:
//        this.maxHP = StatCalculator.calcMaxHP(hpBase, level, ivHp, evHp);
//    WHY: Stat formulas are the most "extractable" piece because they're already
//    stateless. Pulling them out makes them independently testable and reusable
//    (e.g., a "what-if" damage preview could call StatCalculator without needing
//    a full Pokemon instance). This is the Single Responsibility Principle in action:
//    "a class should have only one reason to change," and stat formula tweaks
//    shouldn't require editing the same file as moveset management.
//
// ── Candidate 2: Move the factory into PokemonFactory ──
//    The createPokemon() switch statement (currently commented out) is a textbook
//    case for the Factory pattern — and PokemonFactory.java already exists with a
//    reflection-based approach. Whether you use the switch or reflection, the factory
//    is a *creation* concern, not a *behavior* concern of Pokemon itself. Keeping it
//    separate means adding/removing species never touches the core Pokemon class.
//    This is already partially done — PokemonFactory exists and works. The commented-out
//    switch in this file can be safely removed once PokemonFactory is the sole entry point.
//
// ── Candidate 3: Extract an EvManager value object ──
//    The six evXxx fields, evTotal, the evCapper() helper, all six setEvXxx() methods,
//    all six addEvXxx() methods, and checkEvTotals() form a self-contained subsystem
//    with its own invariants (per-stat max 252, total max 510). Wrapping this in an
//    EvManager class would encapsulate those rules in one place:
//        public class EvManager {
//            private int hp, attack, defense, spAttack, spDefense, speed;
//            private int total;
//            public void set(Stat stat, int value) { ... }
//            public void add(Stat stat, int amount) { ... }
//            public int get(Stat stat) { ... }
//        }
//    Pokemon would hold a single `private EvManager evs;` field.
//    WHY: This eliminates the repetitive setter/adder methods (there are 12 near-identical
//    methods right now) by using the Stat enum as a key, and it makes the 252/510 cap
//    logic impossible to accidentally bypass — it's all behind EvManager's API.
//    This pattern is called a "Value Object" — a small class whose identity is defined
//    by its data rather than a database ID. It's a natural fit for EV bundles.
//
// WHAT TO DO NOW: Nothing urgent. At ~950 lines the class is manageable, and premature
// extraction can hurt readability in a learning project. But as features grow (status
// effects, abilities, held items), watch for the line count and responsibility list
// expanding. When you next touch stat calculation *or* EV logic *or* moveset handling
// for a feature, that's a natural time to extract that piece into its own class.
// =============================================================================================
public class Pokemon {

    private static final Logger LOGGER = LoggerFactory.getLogger(Pokemon.class);

    // Initialize attributes for all Pokemon
    private Trainer trainer; // This is the trainer that this Pokemon belongs to; it is set when the Pokemon is added to a trainer's team
    private int id; // This can be used to link the Pokémon to the database record
    private PokeSpecies species;
    private String nickname;
    private int dexIndex;
    private Type typePrimary;
    private Type typeSecondary;
    private int level;
    private int hpBase;  
    private int attackBase; 
    private int defenseBase; 
    private int specialAttackBase; 
    private int specialDefenseBase;
    private int speedBase;
    private int maxHP;
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
    private int[] evYield = new int[]{0, 0, 0, 0, 0, 0}; // This array holds the EV yield for each stat when this Pokemon is defeated in battle, 
    // in the order of HP, Attack, Defense, Special Attack, Special Defense, Speed
    private Natures nature;
    private String[] statusConditions = new String[0]; // This array holds any status conditions currently affecting the Pokemon (e.g. "Paralyzed", "Burned")
    private boolean isFainted = false; // This boolean indicates whether the Pokemon has fainted (current HP is 0 or less)
    private final ArrayList<MoveSlot> moveset;
    
    // each individual Pokémon object keeps its own learnset reference; most species override
    // the accessor to return a shared static list, but the base class provides an empty list
    protected List<LearnsetEntry> learnset = new ArrayList<>();

    // Constructors - One is a basic constructor with default stats, the other overloads to allow for custom stats
    
    protected Pokemon(PokeSpecies species, int index, Type typePrimary, Type typeSecondary) {
        this.species = species;
        this.nickname = species.getDisplayName(); // Default name is the same as species
        this.dexIndex = index;
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
        this.evYield = new int[]{0, 0, 0, 0, 0, 0}; // Default EV yield is 0 for all stats by default
        this.moveset = new ArrayList<MoveSlot>(4);
        // assign a random nature immediately
        Natures.assignRandom(this);
    }

    protected Pokemon(PokeSpecies species, int index, Type typePrimary, Type typeSecondary, int level, int hp, int attack, int defense, int specialAttack, int specialDefense, int speed) {
        this.species = species;
        this.nickname = species.getDisplayName(); // Default name is the same as species
        this.dexIndex = index;
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
        this.evYield = new int[]{0, 0, 0, 0, 0, 0}; // Default EV yield is 0 for all stats by default
        this.moveset = new ArrayList<MoveSlot>(4);
        // random nature for custom‑stat constructor as well
        Natures.assignRandom(this);
    }

    // Set static randomizer
    private static final Random random = new Random();


    // ========================
    // ===     GETTERS      ===
    // ========================

    // Getters for attributes
    public List<MoveSlot> getMoveset() {
        return Collections.unmodifiableList(moveset);
    }

    public Type getTypePrimary() {
        return typePrimary;
    }

    public Type getTypeSecondary() {
        return typeSecondary;
    }

    public String getNickname() {
        return nickname;
    }

    public PokeSpecies getSpecies() {
        return species;
    }

    public boolean getIsFainted() {
        return isFainted;
    }

    public int getDexIndex() {
        return dexIndex;
    }

    public int getId() {
        return id;
    }

    public String[] getStatusConditions() {
        return statusConditions.clone();
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
        return maxHP;
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

    public int[] getEvYield() {
        return evYield.clone();
    }   

    public long getTrainerDiscordId() {
        if (trainer == null) {
            LOGGER.warn("Attempted to get trainer Discord ID for Pokémon with no trainer assigned. Throwing exception.");
            throw new IllegalStateException("Trainer not assigned to this Pokemon yet.");
        }
        return trainer.getDiscordId();
    }

    public int getTrainerDbId() {
        if (trainer == null) {
            LOGGER.warn("Attempted to get trainer DB ID for Pokémon with no trainer assigned. Throwing exception.");
            throw new IllegalStateException("Trainer not assigned to this Pokemon yet.");
        }
        return trainer.getDbId();
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

    /**
     * Return the learnset associated with this Pokémon instance.  Species classes should
     * override this method (typically returning a static list) so that calling
     * <code>somePokemon.getLearnset()</code> yields the correct catalog for that
     * species.  The default implementation returns an (initially empty) instance
     * list, which allows subclasses that do not define learnsets to compile.
     */
    public List<LearnsetEntry> getLearnset() {
        return Collections.unmodifiableList(learnset);
    }


    // ========================
    // ===     SETTERS      ===
    // ========================

    // Direct Setters for attributes
    public void setLevel(int level) {
        this.level = level;
        calculateCurrentStats();
    }

    public void setTrainer(Trainer trainer) {
        this.trainer = trainer;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
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

    public void setMaxHP(int maxHP) {
        this.maxHP = maxHP;
    }

    public void setCurrentHP(int currentHP) {
        if (currentHP < 0) {
            this.currentHP = 0;
            this.isFainted = true;
        } else if (currentHP > maxHP) {
            this.currentHP = maxHP;
        } else {
            this.currentHP = currentHP;
        }
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

    // EV setters - do not add, these overwrite values directly.  
    // The addEv methods below handle the logic of ensuring that EVs
    //  do not exceed the per-stat or total limits, so these setters 
    // should be used when you want to directly set a specific EV value 
    // (e.g. when loading from the database) and you are sure that the 
    // value being set is valid. These will check evTotal to ensure rule compliance,
    // but they will not modify existing EVs.

    // CODE REVIEW NOTE — EV Extraction Candidate
    // Everything from here through addEvSpeed() (and checkEvTotals below) is self-contained
    // EV management with its own invariant (per-stat ≤252, total ≤510). Notice how every
    // setter and adder repeats the same pattern — only the field name changes. An EvManager
    // class could replace all 12+ methods with two generic ones:
    //     evs.set(Stat.HP, value)   and   evs.add(Stat.HP, amount)
    // using the Stat enum as a key into an internal array or EnumMap. This is a practical
    // example of the DRY principle ("Don't Repeat Yourself") — when you see the same logic
    // copied with only a variable name changed, that's a signal to generalize.
    
    private int evCapper(int oldEvValue, int newEvValue) {
        int totalWithoutThis = this.evTotal - oldEvValue;
        int roomTotal = 510 - totalWithoutThis;
        int capped = Math.max(0, Math.min(newEvValue, Math.min(252, roomTotal)));
        return capped;
    }
    
    public void setEvHp(int evHp) {
        int cappedHp = evCapper(this.evHp, evHp);

        // Apply the new value and recalculate the total
        this.evHp = cappedHp;
        this.evTotal = this.evTotal - this.evHp + cappedHp;
    }

    public void setEvAttack(int evAttack) {
        int cappedAttack = evCapper(this.evAttack, evAttack);
        // Apply the new value and recalculate the total
        this.evAttack = cappedAttack;
        this.evTotal = this.evTotal - this.evAttack + cappedAttack;
    }

    public void setEvDefense(int evDefense) {
        int cappedDefense = evCapper(this.evDefense, evDefense);
        // Apply the new value and recalculate the total
        this.evDefense = cappedDefense;
        this.evTotal = this.evTotal - this.evDefense + cappedDefense;
    }

    public void setEvSpecialAttack(int evSpecialAttack) {
        int cappedSpecialAttack = evCapper(this.evSpecialAttack, evSpecialAttack);
        // Apply the new value and recalculate the total
        this.evSpecialAttack = cappedSpecialAttack;
        this.evTotal = this.evTotal - this.evSpecialAttack + cappedSpecialAttack;
    }

    public void setEvSpecialDefense(int evSpecialDefense) {
        int cappedSpecialDefense = evCapper(this.evSpecialDefense, evSpecialDefense);
        // Apply the new value and recalculate the total
        this.evSpecialDefense = cappedSpecialDefense;
        this.evTotal = this.evTotal - this.evSpecialDefense + cappedSpecialDefense;
    }

    public void setEvSpeed(int evSpeed) {
        int cappedSpeed = evCapper(this.evSpeed, evSpeed);
        // Apply the new value and recalculate the total
        this.evSpeed = cappedSpeed;
        this.evTotal = this.evTotal - this.evSpeed + cappedSpeed;
    }

    // Sets a single stat's EV yield by index (0=HP, 1=Atk, 2=Def, 3=SpAtk, 4=SpDef, 5=Spd).
    // This lets subclasses override only the non-zero yields without rebuilding the whole array,
    // since the superclass constructor already initializes evYield to all zeros.
    public void setEvYield(Stat stat, int value) {
        this.evYield[stat.ordinal()] = value;
    }

    // ==================
    // ===   ADDERS   ===
    // ==================

    public void addExp(int exp) {
        this.currentExp += exp;
        // Placeholder for level up logic when currentExp exceeds the threshold for the next level
    }

    private int evAdder(int oldEvValue, int addedEvValue) {
        int totalWithoutThis = this.evTotal - oldEvValue;
        int roomTotal = 510 - totalWithoutThis;
        int roomStat = 252 - oldEvValue;
        int actualAdd = Math.max(0, Math.min(addedEvValue, Math.min(roomStat, roomTotal)));
        return actualAdd;
    }
    
    public void addEvHp(int addedEvHp) {
        if (evTotal >= 510) return;
        int actual = evAdder(this.evHp, addedEvHp);
        if (actual <= 0) return;
        this.evHp += actual;
        this.evTotal += actual;
        calculateCurrentStats();
    }

    public void addEvAttack(int addedEvAttack) {
        if (evTotal >= 510) return;
        int actual = evAdder(this.evAttack, addedEvAttack);
        if (actual <= 0) return;
        this.evAttack += actual;
        this.evTotal += actual;
        calculateCurrentStats();
    }

    public void addEvDefense(int addedEvDefense) {
        if (evTotal >= 510) return;
        int actual = evAdder(this.evDefense, addedEvDefense);
        if (actual <= 0) return;
        this.evDefense += actual;
        this.evTotal += actual;
        calculateCurrentStats();
    }

    public void addEvSpecialAttack(int addedEvSpecialAttack) {
        if (evTotal >= 510) return;
        int actual = evAdder(this.evSpecialAttack, addedEvSpecialAttack);
        if (actual <= 0) return;
        this.evSpecialAttack += actual;
        this.evTotal += actual;
        calculateCurrentStats();
    }

    public void addEvSpecialDefense(int addedEvSpecialDefense) {
        if (evTotal >= 510) return;
        int actual = evAdder(this.evSpecialDefense, addedEvSpecialDefense);
        if (actual <= 0) return;
        this.evSpecialDefense += actual;
        this.evTotal += actual;
        calculateCurrentStats();
    }

    public void addEvSpeed(int addedEvSpeed) {
        if (evTotal >= 510) return;
        int actual = evAdder(this.evSpeed, addedEvSpeed);
        if (actual <= 0) return;
        this.evSpeed += actual;
        this.evTotal += actual;
        calculateCurrentStats();
    }


    // ====================================
    // ===     MOVESET MANAGEMENT       ===
    // ====================================

    // CODE REVIEW NOTE — Moveset Management
    // This section is relatively small (3 methods, ~30 lines) and tightly coupled to
    // Pokemon's moveset field. Extracting it now would be over-engineering. However,
    // if the moveset system grows (e.g., move relearning, move tutors, PP-restoring
    // items, TM/HM usage tracking), it could become a Moveset class that wraps the
    // ArrayList<MoveSlot> and owns the 4-slot limit, PP management, and replacement logic.
    // For now, keeping it here is the right call — extract when there's a concrete reason.

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


    // ====================================
    // ===     STAT CALCULATIONS        ===
    // ====================================

    // CODE REVIEW NOTE — StatCalculator Extraction Candidate
    // calcMaxHP() and calcCurrentStat() are pure functions — they only use their parameters,
    // not any instance fields. This makes them ideal candidates for a static utility class:
    //
    //     public final class StatCalculator {
    //         public static int calcMaxHP(int base, int level, int iv, int ev) { ... }
    //         public static int calcStat(int base, int level, int iv, int ev, double nature) { ... }
    //     }
    //
    // calculateCurrentStats() would stay here in Pokemon (it needs access to the fields),
    // but it would delegate to StatCalculator for the math. Benefits:
    //   - The formulas become independently unit-testable without constructing a Pokemon
    //   - Other code (e.g., a damage preview, a "what stats would I have at level X?" command)
    //     can call StatCalculator directly
    //   - Pokemon.java shrinks by ~30 lines and loses one responsibility
    //
    // This is the "Extract Method Object" refactoring pattern — when a group of methods in a
    // class are cohesive with each other but loosely coupled to the rest of the class, they
    // form a natural extraction boundary.

    // Methods for calculations
    public int calcMaxHP(int hpBase, int level, int ivHp, int ev) {

        // Calculating the actual HP of a Pokemon requires the base HP stat, the Pokemon's level, 
        // and its individual values (IVs) and effort values (EVs).
        // The formula is: HP = ((((2 * baseHP) + IV + (EV / 4)) * Level) / 100) + Level + 10
        // We want an integer, so we are going to do the calculations in steps to ensure we don't lose 
        // precision until the end when we can round down to an integer.
        int calcHP = (int) Math.floor(ev / 4.0);
        calcHP = (int) Math.floor((2 * hpBase) + ivHp + calcHP);
        calcHP = (int) Math.floor(calcHP * level);
        calcHP = (int) Math.floor(calcHP / 100.0);
        calcHP = (int) Math.floor(calcHP + level + 10);
        return calcHP;
    }

    public boolean healToFull() {
        this.currentHP = this.maxHP;
        return true;
    }   

    public int calcCurrentStat(int baseStat, int level, int iv, int ev, double natureModifier) {
        // The formula for calculating the current value of a non-HP stat is: 
        // Stat = (((((2 * baseStat) + IV + (EV/4)) * Level) / 100) + 5) * Nature
        // For now, we will ignore the Nature modifier and just calculate the stat without it.
        // A 1 will be used as a placeholder for the Nature modifier in the future when that is implemented.
        int calcStat = (int) Math.floor(ev / 4.0);
        calcStat = (int) Math.floor((2 * baseStat) + iv + calcStat);
        calcStat = (int) Math.floor(calcStat * level);
        calcStat = (int) Math.floor(calcStat / 100.0);
        calcStat = (int) Math.floor(calcStat + 5);
        calcStat = (int) Math.floor(calcStat * natureModifier);
        return calcStat;
    }

    // Method for generating a random IV value between 0 and 31 for each stat
    public void generateRandomIVs() {

        this.ivHp = random.nextInt(32);
        this.ivAttack = random.nextInt(32);
        this.ivDefense = random.nextInt(32);
        this.ivSpecialAttack = random.nextInt(32);
        this.ivSpecialDefense = random.nextInt(32);
        this.ivSpeed = random.nextInt(32);
    }


    // Wrapper method for stat calculation
    public void calculateCurrentStats() {
        int oldMaxHp = this.maxHP;
        this.maxHP = calcMaxHP(getHpBaseStat(), getLevel(), getIvHp(), getEvHp());
        this.currentAttack = calcCurrentStat(getAttackBaseStat(), getLevel(), getIvAttack(), getEvAttack(), getNature().modifierFor(Stat.ATTACK));
        this.currentDefense = calcCurrentStat(getDefenseBaseStat(), getLevel(), getIvDefense(), getEvDefense(), getNature().modifierFor(Stat.DEFENSE));
        this.currentSpecialAttack = calcCurrentStat(getSpecialAttackBaseStat(), getLevel(), getIvSpecialAttack(), getEvSpecialAttack(), getNature().modifierFor(Stat.SPECIAL_ATTACK));
        this.currentSpecialDefense = calcCurrentStat(getSpecialDefenseBaseStat(), getLevel(), getIvSpecialDefense(), getEvSpecialDefense(), getNature().modifierFor(Stat.SPECIAL_DEFENSE));
        this.currentSpeed = calcCurrentStat(getSpeedBaseStat(), getLevel(), getIvSpeed(), getEvSpeed(), getNature().modifierFor(Stat.SPEED)); 
        this.currentHP = this.currentHP + (this.maxHP - oldMaxHp); // Adjust current HP by the change in max HP to keep the same percentage of health
    }

    public boolean checkEvTotals() {
        int total = evHp + evAttack + evDefense + evSpecialAttack + evSpecialDefense + evSpeed;
        if (total > 510) {
            LOGGER.warn("Total EVs exceed the maximum of 510. Current total: " + total);
            return false;
        }
        if (evHp > 252 || evAttack > 252 || evDefense > 252 || evSpecialAttack > 252 || evSpecialDefense > 252 || evSpeed > 252) {
            LOGGER.warn("One or more EV stats exceed the maximum of 252. Current EVs - HP: " + evHp + ", Attack: " + evAttack + ", Defense: " + evDefense + ", Special Attack: " + evSpecialAttack + ", Special Defense: " + evSpecialDefense + ", Speed: " + evSpeed);
            return false;
        }
        return true;
    }


    // ====================================
    // ===   GAME LOGIC / MODIFIERS     ===
    // ====================================

    // Modifiers for attributes
    public void levelUp() {
        this.level++;
        this.calculateCurrentStats();
        // Placeholder for actual stat increases on level up
    }

    /**
     * Apply a random nature to this Pokémon.  This is just a convenience wrapper
     * around {@link Natures#assignRandom} so callers can write
     * <code>p.applyNature()</code> when they don't care which nature is chosen.
     */
    public void applyNature() {
        Natures.assignRandom(this);
    }


    // ====================================
    // ===     STATIC FACTORY METHOD    ===
    // ====================================

    // CODE REVIEW NOTE — Factory Already Extracted
    // This 151-case switch statement is now commented out because PokemonFactory.java handles
    // species creation via classpath scanning + reflection. This is the right move — the factory
    // is a "creation" concern, not a "behavior" concern of Pokemon. Having it here meant that
    // adding a new species required editing Pokemon.java, which violated the Open/Closed Principle
    // ("open for extension, closed for modification"). With PokemonFactory, you add a new species
    // subclass and it's auto-discovered — Pokemon.java never changes.
    //
    // Once PokemonFactory is confirmed as the sole creation path across the codebase (bot layer,
    // persistence layer, tests), this commented-out block can be deleted entirely to reduce clutter.

    // This method can be used to create a new Pokémon instance based on user input for species, level, and other attributes.  
    // It can return the newly created Pokémon instance.
    /*
    public static Pokemon createPokemon(String species, String name, Trainer trainer) {
        Pokemon createdMon = null;
        if (species == null) {
            LOGGER.error("No species selected. Please choose a valid Pokémon species.");
            return null;
        }

        if (name == null || name.isEmpty()) {
            name = Pattern.compile("^.").matcher(species).replaceFirst(m -> m.group().toUpperCase()); // Default name is the same as species if no nickname provided
        }

        switch (species.toLowerCase()) {
            case "bulbasaur": createdMon = new Bulbasaur(name); break;
            case "ivysaur": createdMon = new Ivysaur(name); break;
            case "venusaur": createdMon = new Venusaur(name); break;
            case "charmander": createdMon = new Charmander(name); break;
            case "charmeleon": createdMon = new Charmeleon(name); break;
            case "charizard": createdMon = new Charizard(name); break;
            case "squirtle": createdMon = new Squirtle(name); break;
            case "wartortle": createdMon = new Wartortle(name); break;
            case "blastoise": createdMon = new Blastoise(name); break;
            case "caterpie": createdMon = new Caterpie(name); break;
            case "metapod": createdMon = new Metapod(name); break;
            case "butterfree": createdMon = new Butterfree(name); break;
            case "weedle": createdMon = new Weedle(name); break;
            case "kakuna": createdMon = new Kakuna(name); break;
            case "beedrill": createdMon = new Beedrill(name); break;
            case "pidgey": createdMon = new Pidgey(name); break;
            case "pidgeotto": createdMon = new Pidgeotto(name); break;
            case "pidgeot": createdMon = new Pidgeot(name); break;
            case "rattata": createdMon = new Rattata(name); break;
            case "raticate": createdMon = new Raticate(name); break;
            case "spearow": createdMon = new Spearow(name); break;
            case "fearow": createdMon = new Fearow(name); break;
            case "ekans": createdMon = new Ekans(name); break;
            case "arbok": createdMon = new Arbok(name); break;
            case "pikachu": createdMon = new Pikachu(name); break;
            case "raichu": createdMon = new Raichu(name); break;
            case "sandshrew": createdMon = new Sandshrew(name); break;
            case "sandslash": createdMon = new Sandslash(name); break;
            case "nidoran f": case "nidoranf": createdMon = new NidoranF(name); break;
            case "nidorina": createdMon = new Nidorina(name); break;
            case "nidoqueen": createdMon = new Nidoqueen(name); break;
            case "nidoran m": case "nidoranm": createdMon = new NidoranM(name); break;
            case "nidorino": createdMon = new Nidorino(name); break;
            case "nidoking": createdMon = new Nidoking(name); break;
            case "clefairy": createdMon = new Clefairy(name); break;
            case "clefable": createdMon = new Clefable(name); break;
            case "vulpix": createdMon = new Vulpix(name); break;
            case "ninetales": createdMon = new Ninetales(name); break;
            case "jigglypuff": createdMon = new Jigglypuff(name); break;
            case "wigglytuff": createdMon = new Wigglytuff(name); break;
            case "zubat": createdMon = new Zubat(name); break;
            case "golbat": createdMon = new Golbat(name); break;
            case "oddish": createdMon = new Oddish(name); break;
            case "gloom": createdMon = new Gloom(name); break;
            case "vileplume": createdMon = new Vileplume(name); break;
            case "paras": createdMon = new Paras(name); break;
            case "parasect": createdMon = new Parasect(name); break;
            case "venonat": createdMon = new Venonat(name); break;
            case "venomoth": createdMon = new Venomoth(name); break;
            case "diglett": createdMon = new Diglett(name); break;
            case "dugtrio": createdMon = new Dugtrio(name); break;
            case "meowth": createdMon = new Meowth(name); break;
            case "persian": createdMon = new Persian(name); break;
            case "psyduck": createdMon = new Psyduck(name); break;
            case "golduck": createdMon = new Golduck(name); break;
            case "mankey": createdMon = new Mankey(name); break;
            case "primeape": createdMon = new Primeape(name); break;
            case "growlithe": createdMon = new Growlithe(name); break;
            case "arcanine": createdMon = new Arcanine(name); break;
            case "poliwag": createdMon = new Poliwag(name); break;
            case "poliwhirl": createdMon = new Poliwhirl(name); break;
            case "poliwrath": createdMon = new Poliwrath(name); break;
            case "abra": createdMon = new Abra(name); break;
            case "kadabra": createdMon = new Kadabra(name); break;
            case "alakazam": createdMon = new Alakazam(name); break;
            case "machop": createdMon = new Machop(name); break;
            case "machoke": createdMon = new Machoke(name); break;
            case "machamp": createdMon = new Machamp(name); break;
            case "bellsprout": createdMon = new Bellsprout(name); break;
            case "weepinbell": createdMon = new Weepinbell(name); break;
            case "victreebel": createdMon = new Victreebel(name); break;
            case "tentacool": createdMon = new Tentacool(name); break;
            case "tentacruel": createdMon = new Tentacruel(name); break;
            case "geodude": createdMon = new Geodude(name); break;
            case "graveler": createdMon = new Graveler(name); break;
            case "golem": createdMon = new Golem(name); break;
            case "ponyta": createdMon = new Ponyta(name); break;
            case "rapidash": createdMon = new Rapidash(name); break;
            case "slowpoke": createdMon = new Slowpoke(name); break;
            case "slowbro": createdMon = new Slowbro(name); break;
            case "magnemite": createdMon = new Magnemite(name); break;
            case "magneton": createdMon = new Magneton(name); break;
            case "farfetch'd": case "farfetchd": createdMon = new Farfetchd(name); break;
            case "doduo": createdMon = new Doduo(name); break;
            case "dodrio": createdMon = new Dodrio(name); break;
            case "seel": createdMon = new Seel(name); break;
            case "dewgong": createdMon = new Dewgong(name); break;
            case "grimer": createdMon = new Grimer(name); break;
            case "muk": createdMon = new Muk(name); break;
            case "shellder": createdMon = new Shellder(name); break;
            case "cloyster": createdMon = new Cloyster(name); break;
            case "gastly": createdMon = new Gastly(name); break;
            case "haunter": createdMon = new Haunter(name); break;
            case "gengar": createdMon = new Gengar(name); break;
            case "onix": createdMon = new Onix(name); break;
            case "drowzee": createdMon = new Drowzee(name); break;
            case "hypno": createdMon = new Hypno(name); break;
            case "krabby": createdMon = new Krabby(name); break;
            case "kingler": createdMon = new Kingler(name); break;
            case "voltorb": createdMon = new Voltorb(name); break;
            case "electrode": createdMon = new Electrode(name); break;
            case "exeggcute": createdMon = new Exeggcute(name); break;
            case "exeggutor": createdMon = new Exeggutor(name); break;
            case "cubone": createdMon = new Cubone(name); break;
            case "marowak": createdMon = new Marowak(name); break;
            case "hitmonlee": createdMon = new Hitmonlee(name); break;
            case "hitmonchan": createdMon = new Hitmonchan(name); break;
            case "lickitung": createdMon = new Lickitung(name); break;
            case "koffing": createdMon = new Koffing(name); break;
            case "weezing": createdMon = new Weezing(name); break;
            case "rhyhorn": createdMon = new Rhyhorn(name); break;
            case "rhydon": createdMon = new Rhydon(name); break;
            case "chansey": createdMon = new Chansey(name); break;
            case "tangela": createdMon = new Tangela(name); break;
            case "kangaskhan": createdMon = new Kangaskhan(name); break;
            case "horsea": createdMon = new Horsea(name); break;
            case "seadra": createdMon = new Seadra(name); break;
            case "goldeen": createdMon = new Goldeen(name); break;
            case "seaking": createdMon = new Seaking(name); break;
            case "staryu": createdMon = new Staryu(name); break;
            case "starmie": createdMon = new Starmie(name); break;
            case "mr. mime": case "mrmime": createdMon = new MrMime(name); break;
            case "scyther": createdMon = new Scyther(name); break;
            case "jynx": createdMon = new Jynx(name); break;
            case "electabuzz": createdMon = new Electabuzz(name); break;
            case "magmar": createdMon = new Magmar(name); break;
            case "pinsir": createdMon = new Pinsir(name); break;
            case "tauros": createdMon = new Tauros(name); break;
            case "magikarp": createdMon = new Magikarp(name); break;
            case "gyarados": createdMon = new Gyarados(name); break;
            case "lapras": createdMon = new Lapras(name); break;
            case "ditto": createdMon = new Ditto(name); break;
            case "eevee": createdMon = new Eevee(name); break;
            case "vaporeon": createdMon = new Vaporeon(name); break;
            case "jolteon": createdMon = new Jolteon(name); break;
            case "flareon": createdMon = new Flareon(name); break;
            case "porygon": createdMon = new Porygon(name); break;
            case "omanyte": createdMon = new Omanyte(name); break;
            case "omastar": createdMon = new Omastar(name); break;
            case "kabuto": createdMon = new Kabuto(name); break;
            case "kabutops": createdMon = new Kabutops(name); break;
            case "aerodactyl": createdMon = new Aerodactyl(name); break;
            case "snorlax": createdMon = new Snorlax(name); break;
            case "articuno": createdMon = new Articuno(name); break;
            case "zapdos": createdMon = new Zapdos(name); break;
            case "moltres": createdMon = new Moltres(name); break;
            case "dratini": createdMon = new Dratini(name); break;
            case "dragonair": createdMon = new Dragonair(name); break;
            case "dragonite": createdMon = new Dragonite(name); break;
            case "mewtwo": createdMon = new Mewtwo(name); break;
            case "mew": createdMon = new Mew(name); break;
            default:
                LOGGER.warn("Species not recognized. Please choose a valid Pokémon species.");
                return null;
        }

        createdMon.setTrainer(trainer); // Set the trainer for the created Pokémon
        return createdMon;
    }*/

    
      
}
