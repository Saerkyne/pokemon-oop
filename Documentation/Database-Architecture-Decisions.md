# Database Architecture Decisions — Pokemon OOP

What belongs in the database vs. what stays as Java code for the Discord bot use case.

---

## Table of Contents

1. [The Rule of Thumb](#the-rule-of-thumb)
2. [Stays as Java Classes (Game Rules / Logic)](#stays-as-java-classes-game-rules--logic)
3. [Moves to the Database (Per-Player Instance State)](#moves-to-the-database-per-player-instance-state)
4. [Database Table Designs](#database-table-designs)
5. [What About Species Data and Learnsets?](#what-about-species-data-and-learnsets)
6. [The Load/Save Pattern](#the-loadsave-pattern)
7. [Summary](#summary)

---

## The Rule of Thumb

**Reference data that defines the game rules** stays in code. **Instance state that's unique to each player's Pokémon/Trainer** goes in the database.

Ask yourself: *"Does this value change because a specific player did something?"*
- **No** → it's a game rule. Keep it in Java.
- **Yes** → it's instance state. Store it in the database.

---

## Stays as Java Classes (Game Rules / Logic)

These are *static definitions* that never change at runtime. They define what a Bulbasaur **is**, not a specific player's Bulbasaur.

| Class | Why It Stays |
|---|---|
| **`Attack`** | Pure stateless calculator. There's no data to store — just formulas. |
| **`Battle`** | Turn-by-turn logic. Battle state is ephemeral (lives in memory during a fight, discarded when it ends). |
| **`TypeChart`** | 18×18 float matrix — small, fixed, read-only. Hardcoding is faster and simpler than a database round-trip on every damage calculation. |
| **`Natures`** (enum) | 25 entries, never changes. Enum gives you compile-time type safety. |
| **`Stat`** (enum) | 6 values. Pure code concept. |
| **`Move`** subclasses | Each move's power/type/accuracy/PP are fixed game data. ~165 classes, but they're all tiny and immutable. A database lookup for every Tackle would add latency for no benefit. |

### Why Not Put Moves or Type Charts in the Database?

You *could*, but consider: these values are defined by the game's rules and literally never change while the application runs. Loading them from a database means slower startup, more failure modes (what if the DB is down?), and no compile-time safety. Hardcoded constants are the right call for rule data in a game.

---

## Moves to the Database (Per-Player Instance State)

This is data that's **unique to each player** and must **survive** between bot sessions.

---

## Database Table Designs

### `trainers` — One Row Per Discord User

```sql
CREATE TABLE trainers (
    trainer_id   SERIAL PRIMARY KEY,
    discord_id   BIGINT UNIQUE NOT NULL,
    name         VARCHAR(50) NOT NULL
);
```

Example data:

```
trainer_id | discord_id  | name
-----------+-------------+-------
1          | 28374918273 | Red
2          | 91827364510 | Blue
```

### `pokemon_instances` — Each Specific Pokémon a Player Owns

```sql
CREATE TABLE pokemon_instances (
    instance_id  SERIAL PRIMARY KEY,
    trainer_id   INTEGER NOT NULL REFERENCES trainers(trainer_id),
    species      VARCHAR(50) NOT NULL,
    nickname     VARCHAR(50) NOT NULL,
    level        SMALLINT NOT NULL DEFAULT 5,
    nature       VARCHAR(20) NOT NULL,
    iv_hp        SMALLINT NOT NULL,
    iv_attack    SMALLINT NOT NULL,
    iv_defense   SMALLINT NOT NULL,
    iv_sp_attack SMALLINT NOT NULL,
    iv_sp_defense SMALLINT NOT NULL,
    iv_speed     SMALLINT NOT NULL,
    ev_hp        SMALLINT NOT NULL DEFAULT 0,
    ev_attack    SMALLINT NOT NULL DEFAULT 0,
    ev_defense   SMALLINT NOT NULL DEFAULT 0,
    ev_sp_attack SMALLINT NOT NULL DEFAULT 0,
    ev_sp_defense SMALLINT NOT NULL DEFAULT 0,
    ev_speed     SMALLINT NOT NULL DEFAULT 0,
    current_hp   INTEGER NOT NULL,
    current_exp  INTEGER NOT NULL DEFAULT 0,
    is_fainted   BOOLEAN NOT NULL DEFAULT FALSE
);
```

This stores the *randomly generated, mutable* per-instance data: IVs (rolled once at creation), EVs (accumulated over time), level, current HP, nature, and faint status.

Example data:

```
instance_id | trainer_id | species    | nickname   | level | nature  | iv_hp | iv_atk | ... | ev_hp | ev_atk | ... | current_hp | is_fainted
------------+------------+------------+------------+-------+---------+-------+--------+-----+-------+--------+-----+------------+-----------
1           | 1          | Bulbasaur  | Bulby      | 7     | Adamant | 28    | 31     | ... | 12    | 4      | ... | 42         | false
2           | 1          | Charmander | Charmander | 5     | Jolly   | 15    | 22     | ... | 0     | 0      | ... | 38         | false
```

### `pokemon_movesets` — Which Moves Each Instance Knows + Current PP

```sql
CREATE TABLE pokemon_movesets (
    instance_id  INTEGER NOT NULL REFERENCES pokemon_instances(instance_id),
    slot_index   SMALLINT NOT NULL,
    move_name    VARCHAR(50) NOT NULL,
    current_pp   SMALLINT NOT NULL,
    PRIMARY KEY (instance_id, slot_index)
);
```

The `move_name` column acts as a key to look up the corresponding Java `Move` object at load time. The move's stats (power, type, etc.) still come from the Java class — only the PP consumption is per-instance.

Example data:

```
instance_id | slot_index | move_name  | current_pp
------------+------------+------------+-----------
1           | 0          | Tackle     | 35
1           | 1          | Growl      | 40
1           | 2          | Vine Whip  | 22
```

### `trainer_teams` — Which Pokémon Are in the Active Party (and in What Order)

```sql
CREATE TABLE trainer_teams (
    trainer_id   INTEGER NOT NULL REFERENCES trainers(trainer_id),
    slot_index   SMALLINT NOT NULL,
    instance_id  INTEGER NOT NULL REFERENCES pokemon_instances(instance_id),
    PRIMARY KEY (trainer_id, slot_index)
);
```

Example data:

```
trainer_id | slot_index | instance_id
-----------+------------+------------
1          | 0          | 1
1          | 1          | 2
```

---

## What About Species Data and Learnsets?

This is the most interesting decision. Right now every species is a Java subclass (`Bulbasaur.java`, `Charmander.java`, etc.) that hardcodes base stats and learnsets. There are two viable paths:

### Option A — Keep Species in Java (Recommended for Now)

- Each species stays as a Java class with hardcoded base stats and learnset.
- When loading a Pokémon from the database, use the `species` column to instantiate the right class, then overwrite the instance fields (IVs, EVs, level, nature, HP) from the database row.
- **Pro:** Compile-time safety, no startup DB queries, matches the current architecture.
- **Con:** Adding a new species means writing a new Java file.

### Option B — Move Species to the Database (More Advanced, Consider Later)

- Store base stats, types, EV yield, and learnsets in database tables.
- Pokémon become generic `Pokemon` objects populated entirely from DB rows.
- **Pro:** Add new species without recompiling, data-driven design.
- **Con:** Lose compile-time checking, need startup caching to avoid constant DB hits, significantly more complex.

### Why Option A Is the Right Choice Now

Option A is the right choice because the project is a learning codebase. The separate class files make each species visible and debuggable, and the Java compiler catches mistakes (typos in type names, missing stats) that a database would silently accept. Option B is a valid long-term evolution once you're comfortable with both Java and SQL independently.

---

## The Load/Save Pattern

Here's how the Java classes and database work together at runtime:

### Loading a Pokémon from the Database

```
STARTUP:
  Database row: { species: "Bulbasaur", level: 7, nature: "Adamant", iv_hp: 28, ... }
       ↓
  Java: new Bulbasaur("Bulby")        ← gets base stats, learnset from the class
       ↓
  Java: pokemon.setLevel(7)           ← overwrite with saved instance data
        pokemon.setNature(Natures.ADAMANT)
        pokemon.setIVs(28, 31, ...)
        pokemon.setEVs(12, 4, ...)
        pokemon.calculateCurrentStats() ← derived stats recomputed from the real values
       ↓
  Result: fully hydrated Pokemon object in memory
```

### Saving a Pokémon After a Battle

```
SAVE (after battle):
  Java object → extract mutable fields → UPDATE pokemon_instances
                                           SET level = 8,
                                               ev_atk = 8,
                                               current_hp = 35,
                                               current_exp = 120,
                                               is_fainted = false
                                         WHERE instance_id = 1
```

### Why Derived Stats Are Not Stored

**Derived stats** (`currentAttack`, `currentDefense`, `MaxHP`, etc.) are **not stored** in the database. They're recalculated from base stats + IVs + EVs + nature + level every time you load. Storing derived data creates a risk of the database disagreeing with the formula — a common source of bugs called **data denormalization inconsistency**.

For example, if you store `MaxHP = 42` in the database but then change the HP calculation formula in Java, every previously saved Pokémon now has a stale `MaxHP` that doesn't match what the formula would produce. By recomputing on load, the formula is always the single source of truth.

---

## Summary

| Data | Where | Why |
|---|---|---|
| Type chart (18×18) | Java `TypeChart` | Fixed rules, tiny, read-only |
| Natures (25 entries) | Java `Natures` enum | Fixed rules, type-safe |
| Move definitions (~165) | Java `Move` subclasses | Fixed rules, immutable |
| Species base stats | Java species subclasses | Fixed rules, compile-time safety |
| Learnsets | Java species subclasses | Fixed rules, tied to species |
| Damage/battle formulas | Java `Attack`/`Battle` | Pure logic, no data |
| **Trainer identity** | **Database** | Per-player, persistent |
| **Pokémon instances** (IVs, EVs, level, nature, HP) | **Database** | Per-instance, mutable, must survive restarts |
| **Movesets + current PP** | **Database** | Per-instance, mutable |
| **Team composition** | **Database** | Per-player, mutable |
| Derived stats (currentAttack, MaxHP...) | **Neither** — recalculated on load | Avoid storing values computable from other stored values |
