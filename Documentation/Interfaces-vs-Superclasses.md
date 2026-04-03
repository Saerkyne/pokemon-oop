# Interfaces vs. Superclasses in Java

**Date:** 2026-04-03  
**Audience:** Developers getting comfortable with Java and OOP  
**Context:** This project uses both patterns — `Pokemon` and `Move` are superclasses; `BattleAction` is a sealed interface.

---

## The One-Sentence Version

A **superclass** says "here is a thing — you can make more specific versions of it."  
An **interface** says "here is a capability — any class can promise to have it."

---

## Part 1: Superclasses (What You Already Know)

When `Bulbasaur extends Pokemon`, Bulbasaur **inherits** everything Pokemon has — fields, methods, constructors (via `super()`). Bulbasaur *is a* Pokemon. It gets HP, stats, a moveset, and all the methods for free.

```java
// Superclass — provides shared state AND behavior
public class Pokemon {
    private int currentHP;
    private int level;
    
    public void takeDamage(int amount) {
        this.currentHP -= amount;
    }
}

// Subclass — inherits fields and methods, adds specifics
public class Bulbasaur extends Pokemon {
    public Bulbasaur(String name) {
        super("Bulbasaur", 1, "Grass", "Poison", 5, 45, 49, 49, 65, 65, 45);
    }
}
```

Key traits of superclasses:

- **Shared state.** The superclass has fields (`currentHP`, `level`) that subclasses inherit. There's one copy of these fields per instance, managed by the superclass.
- **Shared behavior.** Methods like `takeDamage()` are written once in `Pokemon` and work for all 151 species. Subclasses can override them, but they don't have to.
- **Constructor chaining.** Subclasses call `super(...)` to set up the parent's state before adding their own.
- **Single inheritance.** A class can only extend ONE superclass. `Bulbasaur extends Pokemon` — it can't also extend `Move`. This is Java's biggest constraint on class inheritance.

### When to use a superclass

Use inheritance when subclasses genuinely **are** a more specific version of the parent, and they need to **share state and implementation**. All Pokémon have HP, all Pokémon can take damage, all Pokémon have stats — that's a textbook case for a superclass.

---

## Part 2: Interfaces (The Contract)

An interface has no state (no fields that store data). It just declares: "any class implementing me must provide these methods." It's a promise, not a thing.

```java
// Interface — a contract, no state
public interface Drawable {
    void draw();    // no body — implementing classes must provide one
}
```

Any class can implement it:

```java
public class Circle implements Drawable {
    public void draw() {
        System.out.println("Drawing a circle");
    }
}

public class TextBox implements Drawable {
    public void draw() {
        System.out.println("Drawing a text box");
    }
}
```

`Circle` and `TextBox` have nothing in common — one is a shape, one is a UI widget. But both can be drawn. The interface captures that shared *capability* without claiming they're related things.

Key traits of interfaces:

- **No shared state.** An interface can't have `private int hp`. It doesn't store data. (It can have constants — `public static final` — but those aren't instance state.)
- **Multiple implementation.** A class can implement many interfaces: `class Pikachu extends Pokemon implements Serializable, Comparable<Pokemon>`. No limit. This is the big advantage over superclasses.
- **Default methods (Java 8+).** Interfaces can provide method bodies with the `default` keyword, but these are shared *behavior* without shared *state* — they can't access private fields because there aren't any.
- **Defines a type.** You can use an interface as a variable type: `Drawable item = new Circle();`. Code that works with `Drawable` doesn't need to know whether it's a Circle or TextBox.

### When to use an interface

Use an interface when different, unrelated classes share a **capability** or **role**, but don't share internal structure. "Can be drawn," "can be compared," "can be serialized" — these are capabilities, not identities.

---

## Part 3: Side-by-Side Comparison

| | Superclass (`extends`) | Interface (`implements`) |
| --- | --- | --- |
| **What it represents** | "Is a" relationship (Bulbasaur *is a* Pokémon) | "Can do" relationship (Circle *can be* drawn) |
| **State (fields)** | Yes — subclasses inherit fields | No — no instance fields |
| **Method bodies** | Yes — full method implementations | Only `default` methods (no access to state) |
| **Constructor** | Yes — `super(...)` chaining | No constructors |
| **How many?** | One superclass only (`extends` one thing) | Many interfaces (`implements A, B, C`) |
| **Shared code** | Subclasses inherit it automatically | Each implementing class writes its own (unless `default`) |
| **Best for** | Family of related types sharing structure | Unrelated types sharing a capability |

### A real-world analogy

Think of a **superclass** like a biological family. Dogs and wolves are both *Canidae* — they share DNA, bone structure, and behavior. A wolf is a specific kind of Canid, just like Bulbasaur is a specific kind of Pokémon.

Think of an **interface** like a certification. A plumber and an electrician are very different professionals, but both might be "Licensed" — that's a shared capability, not a shared identity. In Java: `class Plumber implements Licensed` and `class Electrician implements Licensed`.

---

## Part 4: Why Not Just Use a Superclass for BattleAction?

You might wonder: "I used a superclass for `Pokemon` and `Move`. Why not do the same for `BattleAction`?"

```java
// Hypothetical: BattleAction as a superclass
public abstract class BattleAction {
    private Trainer trainer;
    private Pokemon activePokemon;
    
    public abstract void execute(Battle battle);
}

public class MoveAction extends BattleAction { ... }
public class SwitchAction extends BattleAction { ... }
```

This *would work*. But there are three reasons the interface fits better here:

### Reason 1: No meaningful shared state

`Pokemon` has dozens of fields that every species shares — HP, stats, level, nature, EVs, IVs, moveset. The superclass manages all of that, and subclasses benefit enormously from inheriting it.

`BattleAction` has... a trainer and an active Pokémon. That's it. And those are just references that get passed in at construction time — there's no complex state management, no calculation, no mutation. When shared state is this thin, an interface communicates the intent more honestly: "these types have the same shape" rather than "these types share complex machinery."

### Reason 2: Records can't extend classes

Java records (like `MoveAction` and `SwitchAction`) can implement interfaces but **cannot extend classes**. This is a hard language rule. Since records are the ideal fit for immutable data carriers (which is what BattleActions are), using an interface is the only option that works with records.

```java
// ✅ This works — record implementing an interface
public record MoveAction(Trainer trainer, Pokemon pokemon, int moveSlotIndex)
    implements BattleAction { }

// ❌ This is a compile error — records cannot extend classes
public record MoveAction(Trainer trainer, Pokemon pokemon, int moveSlotIndex)
    extends BattleAction { }   // ERROR: no extends clause allowed for records
```

### Reason 3: Sealed interfaces enable exhaustive pattern matching

This is the biggest reason. When you write:

```java
public sealed interface BattleAction permits MoveAction, SwitchAction { }
```

And then in TurnManager:

```java
switch (action) {
    case MoveAction ma   -> resolveMove(ma, defender);
    case SwitchAction sa -> resolveSwitch(sa);
}
```

The compiler **guarantees** you've handled every type. If you later add `ItemAction` to the permits list, every switch that doesn't handle it becomes a compile error.

You can make a class hierarchy `sealed` too (`public sealed class X permits A, B`), but you'd lose the ability to use records — and the superclass would be carrying state that doesn't need to be there.

---

## Part 5: When Does This Decision Come Up?

Here's a quick decision guide:

```flowchart
Do the subtypes share complex state (many fields, internal logic)?
├── YES → Superclass (like Pokemon with HP, stats, EVs, IVs, nature...)
└── NO  → Does each subtype carry different data?
    ├── YES → Sealed interface + records (like BattleAction → MoveAction, SwitchAction)
    └── NO  → Regular interface (like Comparable, Serializable — just a capability)
```

### Examples from this project

| Type | Pattern | Why |
| ------ | --------- | ----- |
| `Pokemon` → `Bulbasaur`, `Charmander`... | **Superclass** | All 151 share HP, stats, EVs, IVs, moveset, level-up logic. Massive shared state. |
| `Move` → `Tackle`, `Flamethrower`... | **Superclass** | All moves share name, power, type, category, accuracy, PP. Moderate shared state. |
| `BattleAction` → `MoveAction`, `SwitchAction` | **Sealed interface + records** | Minimal shared state. Each variant carries different fields. Immutable. Benefits from exhaustive pattern matching. |

---

## Part 6: Abstract Classes — The Middle Ground

You'll sometimes see `abstract class` used as a middle ground between superclasses and interfaces. An abstract class *can* have state (fields) and *can* have abstract methods that subclasses must implement — but it still follows single inheritance (one `extends` only).

```java
public abstract class Shape {
    private String color;           // state — inherited by all shapes
    
    public String getColor() {      // concrete method — shared behavior
        return color;
    }
    
    public abstract double area();  // abstract — each shape must implement
}

public class Circle extends Shape {
    private double radius;
    
    public double area() {
        return Math.PI * radius * radius;
    }
}
```

`Move` in this project is actually an `abstract` class — it has shared fields (name, power, type, accuracy, PP) and all individual moves like `Tackle` extend it and pass their specific values via `super(...)`. Same concept.

**When to use abstract class vs. interface:**

- Abstract class: shared state + some methods subclasses must override. Still limited to single inheritance.
- Interface: no shared state, just a contract. Can be combined with other interfaces. Can work with records.
- Sealed interface: same as interface, but with a fixed, compiler-known set of implementations. Best for "one of N types" situations like BattleAction.

---

## Further Reading

These resources explain the same concepts from different angles:

- [Oracle Java Tutorials — Interfaces](https://docs.oracle.com/javase/tutorial/java/IandI/createinterface.html) — the official tutorial on interfaces vs. abstract classes
- [Oracle Java Tutorials — Inheritance](https://docs.oracle.com/javase/tutorial/java/IandI/subclasses.html) — the official tutorial on class inheritance
- [JEP 409: Sealed Classes](https://openjdk.org/jeps/409) — the proposal that added sealed classes/interfaces to Java, with motivation and examples
- [JEP 395: Records](https://openjdk.org/jeps/395) — the proposal that added records, explaining what problems they solve
- [Baeldung — Sealed Classes and Interfaces](https://www.baeldung.com/java-sealed-classes-interfaces) — tutorial-style walkthrough with code examples
