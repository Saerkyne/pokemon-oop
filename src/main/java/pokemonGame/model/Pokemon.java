package pokemonGame.model;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.Collections;
import java.util.EnumSet;
import java.util.concurrent.ThreadLocalRandom;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pokemonGame.core.EvManager;
import pokemonGame.core.Natures;
import pokemonGame.core.Stat;
import pokemonGame.core.StatCalculator;
import pokemonGame.core.TypeChart.StatusCondition;
import pokemonGame.core.TypeChart.Type;
import pokemonGame.species.PokeSpecies;


/**
 * Represents a single Pokémon instance with species data, individual stats
 * (IVs, EVs, nature), a moveset of up to 4 {@link MoveSlot}s, and mutable
 * battle state (current HP, faint flag). Species subclasses in
 * {@code pokemonGame.mons} extend this class and provide base stats and
 * learnsets.
 *
 * <p>Derived stats (currentAttack, maxHP, etc.) are recalculated via
 * {@link StatCalculator} whenever base inputs change — they are never
 * stored in the database.</p>
 *
 * @see PokeSpecies
 * @see StatCalculator
 * @see Natures
 */
// TODO [🟡 IMPORTANT | review 2026-04-20]: God class (~700 lines) mixes persistence IDs, 
// runtime battle state, moveset mgmt, and EV mutation gating. Why: one file touches every layer 
// (model/db/battle); tight coupling blocks unit tests and makes changes ripple. 
// Fix: extract `Stats` record (HP/Atk/Def/SpA/SpD/Spd), `IVs` record, `EVs` record, and `Moveset` wrapper; 
// leave Pokemon as thin aggregate.
public class Pokemon {

    private static final Logger LOGGER = LoggerFactory.getLogger(Pokemon.class);
    private static final StackWalker EV_CALLER_WALKER = StackWalker.getInstance(StackWalker.Option.RETAIN_CLASS_REFERENCE);

    // Initialize attributes for all Pokemon
    private Trainer trainer; // This is the trainer that this Pokemon belongs to; it is set when the Pokemon is added to a trainer's team
    private int pokemonDbId; // This can be used to link the Pokémon to the database record
    private int currentTeamId; // This can be used to link the Pokémon to a specific team in the database, if needed
    private int currentTeamSlotIndex; // This can be used to track which slot (1-6) the Pokémon is currently occupying in the team, if needed
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
    // STATUS CONDITIONS — current implementation uses an array.
    //
    // ENUMSET ALTERNATIVE (recommended when status effects are implemented):
    // An EnumSet is a Set backed by a bitmask — perfect for tracking multiple
    // simultaneous conditions (a Pokémon can be poisoned AND burned at once).
    // This is different from Type or Category, which are single-value fields
    // (a Pokémon has exactly one primary type, a move has exactly one category).
    // StatusCondition needs a *collection* because multiple can be active.
    //
    // Field:   private EnumSet<StatusCondition> statusConditions = EnumSet.noneOf(StatusCondition.class);
    //
    // Add:     statusConditions.add(StatusCondition.BURN);         // no-op if already present
    // Remove:  statusConditions.remove(StatusCondition.POISON);    // no-op if absent
    // Check:   statusConditions.contains(StatusCondition.FREEZE);  // O(1) bit check, no loop
    // Clear:   statusConditions.clear();                           // heal all conditions
    //
    // Getter (return unmodifiable view to protect encapsulation):
    //   public Set<StatusCondition> getStatusConditions() {
    //       return Collections.unmodifiableSet(statusConditions);
    //   }
    //
    // Setter (replace entirely — e.g. when loading from DB):
    //   public void setStatusConditions(EnumSet<StatusCondition> conditions) {
    //       this.statusConditions = conditions;
    //   }
    private EnumSet<StatusCondition> statusConditions = EnumSet.noneOf(StatusCondition.class);
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
        this.moveset = new ArrayList<MoveSlot>(4);
        // random nature for custom‑stat constructor as well
        Natures.assignRandom(this);
    }


    // ========================
    // ===     GETTERS      ===
    // ========================

    // Getters for attributes
    public List<MoveSlot> getMoveSet() {
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
        return currentHP <= 0;
    }

    public int getDexIndex() {
        return dexIndex;
    }

    public int getPokemonDbId() {
        return pokemonDbId;
    }

    public Set<StatusCondition> getStatusConditions() {
        return Collections.unmodifiableSet(statusConditions);
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

    public int getStat(Stat stat) {
       return switch (stat) {
            case HP -> getMaxHP();
            case ATTACK -> getCurrentAttack();
            case DEFENSE -> getCurrentDefense();
            case SPECIAL_ATTACK -> getCurrentSpecialAttack();
            case SPECIAL_DEFENSE -> getCurrentSpecialDefense();
            case SPEED -> getCurrentSpeed();
        };
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
        return trainer.getTrainerDbId();
    }

    public int getCurrentTeamId() {
        return currentTeamId;
    }

    public int getCurrentTeamSlotIndex() {
        return currentTeamSlotIndex;
    }

    // Special Methods to get the appropriate attack or defense stat based on the move's category (Physical, Special, or Status)
    
    public int getAttackStatForMove(Move move) {
        return switch (move.getMoveCategory()) {
            case PHYSICAL -> getCurrentAttack();
            case SPECIAL -> getCurrentSpecialAttack();
            case STATUS -> 0;
        };
    }

    public int getDefenseStatForMove(Move move) {
        return switch (move.getMoveCategory()) {
            case PHYSICAL -> getCurrentDefense();
            case SPECIAL -> getCurrentSpecialDefense();
            case STATUS -> 0;
        };
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
        if (level < 1 || level > 100) {
            throw new IllegalArgumentException("Level must be 1..100, got " + level);
        }

        this.level = level;
    }

    public void setTypePrimary(Type typePrimary) {
        this.typePrimary = typePrimary;
    }

    public void setTypeSecondary(Type typeSecondary) {
        this.typeSecondary = typeSecondary;
    }

    public void setTrainer(Trainer trainer) {
        this.trainer = trainer;
    }

    public void setPokemonDbId(int pokemonDbId) {
        this.pokemonDbId = pokemonDbId;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public void setNature(Natures nature) {
        this.nature = nature;
    }

    public void setStatusConditions(EnumSet<StatusCondition> conditions) {
        this.statusConditions = (conditions == null) ? EnumSet.noneOf(StatusCondition.class) : EnumSet.copyOf(conditions);
    }
    
    public void setHpBase(int hp) {
        this.hpBase = hp;
    }   

    public void setAttackBase(int attack) {
        this.attackBase = attack;
    }

    public void setDefenseBase(int defense) {
        this.defenseBase = defense;
    }

    public void setSpecialAttackBase(int specialAttack) {
        this.specialAttackBase = specialAttack;
    }   

    public void setSpecialDefenseBase(int specialDefense) {
        this.specialDefenseBase = specialDefense;
    }

    public void setSpeedBase(int speed) {
        this.speedBase = speed;
    }

    public void setMaxHP(int maxHP) {
        if (maxHP < 1) {
            throw new IllegalArgumentException("maxHP must be >= 1, got " + maxHP);
        }
        this.maxHP = maxHP;
    }

    public void setCurrentHP(int currentHP) {
        if (currentHP < 0) {
            this.currentHP = 0;
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
        if (ivHp < 0 || ivHp > 31) {
            throw new IllegalArgumentException("IV value must be between 0 and 31");
        }
        this.ivHp = ivHp;
    }

    public void setIvAttack(int ivAttack) {
        if (ivAttack < 0 || ivAttack > 31) {
            throw new IllegalArgumentException("IV value must be between 0 and 31");
        }
        this.ivAttack = ivAttack;
    }

    public void setIvDefense(int ivDefense) {
        if (ivDefense < 0 || ivDefense > 31) {
            throw new IllegalArgumentException("IV value must be between 0 and 31");
        }
        this.ivDefense = ivDefense;
    }

    public void setIvSpecialAttack(int ivSpecialAttack) {
        if (ivSpecialAttack < 0 || ivSpecialAttack > 31) {
            throw new IllegalArgumentException("IV value must be between 0 and 31");
        }
        this.ivSpecialAttack = ivSpecialAttack;
    }

    public void setIvSpecialDefense(int ivSpecialDefense) {
        if (ivSpecialDefense < 0 || ivSpecialDefense > 31) {
            throw new IllegalArgumentException("IV value must be between 0 and 31");
        }
        this.ivSpecialDefense = ivSpecialDefense;
    }

    public void setIvSpeed(int ivSpeed) {
        if (ivSpeed < 0 || ivSpeed > 31) {
            throw new IllegalArgumentException("IV value must be between 0 and 31");
        }
        this.ivSpeed = ivSpeed;
    }

    public int getEvHp() { return evHp; }
    public int getEvAttack() { return evAttack; }
    public int getEvDefense() { return evDefense; }
    public int getEvSpecialAttack() { return evSpecialAttack; }
    public int getEvSpecialDefense() { return evSpecialDefense; }
    public int getEvSpeed() { return evSpeed; }
    public int getEvTotal() { return evTotal; }

    private static void requireEvManagerCaller() {
        Class<?> caller = EV_CALLER_WALKER.walk(frames -> frames
            .map(StackWalker.StackFrame::getDeclaringClass)
            .filter(clazz -> clazz != Pokemon.class)
            .findFirst()
            .orElse(null));
        if (!EvManager.class.equals(caller)) {
            throw new IllegalCallerException("Use EvManager to mutate EV values.");
        }
    }

    public void setEvHp(int evHp) {
        requireEvManagerCaller();
        this.evHp = evHp;
    }

    public void setEvAttack(int evAttack) {
        requireEvManagerCaller();
        this.evAttack = evAttack;
    }

    public void setEvDefense(int evDefense) {
        requireEvManagerCaller();
        this.evDefense = evDefense;

    }

    public void setEvSpecialAttack(int evSpecialAttack) {
        requireEvManagerCaller();
        this.evSpecialAttack = evSpecialAttack;
    }

    public void setEvSpecialDefense(int evSpecialDefense) {
        requireEvManagerCaller();
        this.evSpecialDefense = evSpecialDefense;
    }

    public void setEvSpeed(int evSpeed) {
        requireEvManagerCaller();
        this.evSpeed = evSpeed;
    }

    public void setEvTotal(int evTotal) {
        requireEvManagerCaller();
        this.evTotal = evTotal;
    }

    // Sets a single stat's EV yield by index (0=HP, 1=Atk, 2=Def, 3=SpAtk, 4=SpDef, 5=Spd).
    // This lets subclasses override only the non-zero yields without rebuilding the whole array,
    // since the superclass constructor already initializes evYield to all zeros..
    public void setEvYield(Stat stat, int value) {
        Objects.requireNonNull(stat);
        if (value < 0 || value > 3) {
            throw new IllegalArgumentException("EV yield per stat is 0..3");
        }

        this.evYield[stat.ordinal()] = value;
    }

    
    public void addExp(int exp) {
        if (exp < 0) {
            throw new IllegalArgumentException("exp must be non-negative");
        }
        this.currentExp += exp;
        // Placeholder for level up logic when currentExp exceeds the threshold for the next level
    }

    public void setCurrentTeamId(int currentTeamId) {
        this.currentTeamId = currentTeamId;
    }

    public void setCurrentTeamSlotIndex(int currentTeamSlotIndex) {
        this.currentTeamSlotIndex = currentTeamSlotIndex;
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
    // ===   GAME LOGIC / MODIFIERS     ===
    // ====================================

    // Modifiers for attributes
    public void levelUp() {
        this.level++;
        StatCalculator.calculateAllStats(this);
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

    public boolean healToFull() {
        this.currentHP = this.maxHP;
        return true;
    }  



    // Method for generating a random IV value between 0 and 31 for each stat
    public void generateRandomIVs() {

        this.ivHp = ThreadLocalRandom.current().nextInt(32);
        this.ivAttack = ThreadLocalRandom.current().nextInt(32);
        this.ivDefense = ThreadLocalRandom.current().nextInt(32);
        this.ivSpecialAttack = ThreadLocalRandom.current().nextInt(32);
        this.ivSpecialDefense = ThreadLocalRandom.current().nextInt(32);
        this.ivSpeed = ThreadLocalRandom.current().nextInt(32);
    }


   

    
      
}
