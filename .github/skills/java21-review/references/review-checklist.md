# Java 21 Review Checklist

Systematic checklist for reviewing Java code. Work through each section. Not every check applies to every file — skip what's not relevant, but don't skip a section without considering it.

---

## 1. Java 21 Modern Features

These checks look for places where older Java patterns can be replaced with cleaner Java 21 alternatives. The goal isn't to force new syntax everywhere — it's to identify places where modern features genuinely improve clarity, safety, or conciseness.

### Records

- [ ] Are there classes that are pure data carriers (all fields final, no mutation, just getters/equals/hashCode/toString)?
  - **Why it matters:** Records eliminate boilerplate and signal "this is immutable data." When a junior dev sees a record, they immediately know it's a value type — no need to audit for hidden mutation.
  - **Example opportunity:** A class with 3+ final fields, a constructor that assigns them all, and generated getters → candidate for `record`.
  - **When NOT to use:** If the class has mutable state, inheritance needs, or complex initialization logic, a record doesn't fit.

### Sealed Classes & Interfaces

- [ ] Are there class hierarchies where the set of subtypes is known and fixed?
  - **Why it matters:** `sealed` tells the compiler (and the reader) exactly which subclasses exist. This enables exhaustive `switch` pattern matching — the compiler warns if you miss a case.
  - **Example opportunity:** An abstract `Shape` with only `Circle`, `Rectangle`, `Triangle` → `sealed interface Shape permits Circle, Rectangle, Triangle`.

### Pattern Matching

- [ ] Are there `instanceof` checks followed by casts?
  - **Fix:** Use pattern matching for `instanceof` (Java 16+): `if (obj instanceof String s)` — eliminates the cast and reduces error risk.
- [ ] Are there chains of `if/else if` checking types or values?
  - **Fix:** Consider `switch` with pattern matching (Java 21): `case String s -> ...`
- [ ] Are there `switch` statements using traditional `case:/break` syntax?
  - **Fix:** Use switch expressions with arrow syntax: `case X -> value;` — no fall-through risk, more readable.
- [ ] Do `switch` expressions over sealed types cover all cases without a `default`?
  - **Why it matters:** Omitting `default` on sealed types means the compiler enforces exhaustiveness — if a new subtype is added, every switch breaks at compile time, forcing you to handle it.

### Text Blocks

- [ ] Are there multi-line strings built with `+` concatenation or `\n`?
  - **Fix:** Use text blocks (`"""..."""`) for SQL queries, JSON, multi-line messages, etc.

### Enhanced Switch

- [ ] Are there traditional `switch` statements with `case:/break`?
  - **Why it matters:** Traditional switch has fall-through by default — forgetting `break` is one of the most common Java bugs. Arrow syntax (`->`) eliminates this entire class of bugs.

### Virtual Threads (if applicable)

- [ ] Is the code spawning platform threads for I/O-bound tasks?
  - **Fix:** Consider `Thread.ofVirtual().start(...)` or `Executors.newVirtualThreadPerTaskExecutor()` for I/O-heavy work (database calls, HTTP requests, file I/O).
  - **When NOT to use:** CPU-bound computation, code that relies on `ThreadLocal` in complex ways, or `synchronized` blocks that do I/O inside them (virtual threads can't unmount from carriers inside `synchronized`).

### Sequenced Collections (Java 21)

- [ ] Is code using `list.get(list.size() - 1)` to get the last element?
  - **Fix:** Use `list.getLast()` and `list.getFirst()` from the `SequencedCollection` interface.
- [ ] Is code manually reversing iteration order?
  - **Fix:** Use `collection.reversed()` for a reversed view.

### Other Modern APIs

- [ ] String formatting: using `String.format()` where `.formatted()` would be cleaner?
- [ ] `var` usage: is `var` used where the type is obvious from the right-hand side? Is it avoided where the type isn't obvious?
- [ ] `Stream.toList()`: using `.collect(Collectors.toList())` instead of the simpler `.toList()` (Java 16+)?

---

## 2. Security

These checks catch vulnerabilities that can lead to data breaches, injection attacks, or unauthorized access.

### SQL Injection

- [ ] Are ALL database queries using prepared statements with `?` placeholders?
  - **Why it matters:** String concatenation in SQL lets attackers inject arbitrary SQL. This is the #1 web application vulnerability (OWASP Top 10). Prepared statements separate code from data — the database driver handles escaping.
  - **Red flag:** Any line that builds SQL with `+` or `String.format()` using user input.

### Input Validation

- [ ] Is user input validated before processing?
  - Check: null checks, length limits, format validation, range checks
  - **Why it matters:** Unvalidated input is the root cause of most security vulnerabilities. Validate at system boundaries (where data enters your code).

### Credential Handling

- [ ] Are secrets (API keys, passwords, tokens) hardcoded in source files?
  - **Fix:** Use environment variables, a secrets manager, or a `.env` file excluded from version control.
- [ ] Are credentials logged or included in error messages?

### Deserialization

- [ ] Is `ObjectInputStream` used to deserialize untrusted data?
  - **Why it matters:** Java deserialization can execute arbitrary code. Use JSON (Jackson/Gson) instead.

### Path Traversal

- [ ] Are file paths constructed from user input without sanitization?
  - **Fix:** Validate that resolved paths stay within expected directories. Use `Path.normalize()` and check prefixes.

### Access Control

- [ ] Are sensitive operations protected by authorization checks?
- [ ] Is the principle of least privilege followed (minimal permissions)?

---

## 3. Performance & Efficiency

### Resource Management

- [ ] Are `Connection`, `PreparedStatement`, `ResultSet`, streams, and other `AutoCloseable` resources in try-with-resources?
  - **Why it matters:** Leaked resources (especially DB connections) cause gradual degradation and eventual crashes. Try-with-resources guarantees cleanup even when exceptions occur.
  - **Red flag:** Any `Connection` or `Statement` opened without try-with-resources.

### Collection Choices

- [ ] Is the right collection type used?
  - `ArrayList` for indexed access, `LinkedList` almost never (benchmark before choosing it)
  - `HashMap` for key-value lookup, `TreeMap` only when sorted order is needed
  - `HashSet` for uniqueness checks, `EnumSet` for enum flags
  - **Why it matters:** Wrong collection choice can mean O(n) where O(1) was available.

### Algorithm Complexity

- [ ] Are there nested loops over the same collection (potential O(n²))?
- [ ] Could a `Map` or `Set` replace a linear search?
- [ ] Are there repeated computations that could be cached or moved outside loops?

### Object Creation

- [ ] Are objects created inside tight loops when they could be reused or created once?
- [ ] Are there unnecessary boxing/unboxing operations (e.g., using `Integer` where `int` suffices)?
  - **Why it matters:** Wrapper types involve heap allocation and garbage collection pressure. Use primitives unless nullability is required.

### String Performance

- [ ] Is string concatenation used inside loops?
  - **Fix:** Use `StringBuilder` for building strings in loops. Single-line concatenation is fine (the compiler optimizes it).
- [ ] Are logger calls using string concatenation instead of parameterized messages?
  - **Fix:** `logger.info("User {} logged in", username)` — avoids building the string when the log level is disabled.

### Database Performance

- [ ] Are there N+1 query patterns (one query to get IDs, then one query per ID)?
- [ ] Are database calls made inside loops when a batch query would work?
- [ ] Are connections opened per query when a connection pool would be more efficient?

---

## 4. Code Style & Readability

### Naming

- [ ] Do class names use `PascalCase`? Methods and variables `camelCase`? Constants `UPPER_SNAKE_CASE`?
- [ ] Do names describe what something IS or DOES, not how it's implemented?
  - **Bad:** `doStuff()`, `data`, `temp`, `x`
  - **Good:** `calculateDamage()`, `trainerTeam`, `maxSlots`, `criticalHitChance`
- [ ] Are boolean methods/variables named as questions? (`isEmpty`, `hasTeam`, `canLearnMove`)

### Encapsulation

- [ ] Are fields `private` with getters/setters where needed?
  - **Why it matters:** Public fields let any code change internal state without validation. Encapsulation lets you add checks later (e.g., "HP can't go below 0") without breaking callers.
- [ ] Do getters return defensive copies for mutable collections?
  - **Fix:** Return `List.copyOf(list)`, `Collections.unmodifiableList(list)`, or `list.clone()` instead of the internal reference.

### Method Design

- [ ] Are methods short enough to understand at a glance (rough guide: under 30 lines)?
- [ ] Does each method do ONE thing?
- [ ] Are there methods with more than 3–4 parameters?
  - **Fix:** Consider a parameter object (possibly a record) or builder pattern.

### Error Handling

- [ ] Are exceptions caught at the right level (not too broad, not too narrow)?
  - **Red flag:** `catch (Exception e)` or `catch (Throwable t)` — too broad. Catch specific exceptions.
- [ ] Are caught exceptions logged or rethrown, never silently swallowed?
  - **Red flag:** Empty `catch` blocks.
- [ ] Are error messages helpful? Do they include context about what failed?

### Comments

- [ ] Do comments explain WHY, not WHAT?
  - **Good:** `// Speed stat determines turn order, not damage` (explains design decision)
  - **Bad:** `// Set level to 5` on a line that says `setLevel(5)` (restates the obvious)
- [ ] Is there dead commented-out code that should be removed?

### Consistency

- [ ] Is the same pattern used for the same task throughout the codebase?
  - If enhanced switch is used in one place, are other switches updated too?
  - If parameterized logging is used in one class, are other classes consistent?

---

## 5. Education Opportunities

These aren't issues — they're places to highlight a Java 21 feature or fundamental concept.

### Design Patterns in Action

- [ ] Can you point out where a pattern is already used well? (factory, strategy, template method, etc.)
- [ ] Is there a place where introducing a pattern would simplify the code?

### Java 21 "Did You Know?"

- [ ] Are there places to mention `SequencedCollection`, `String.formatted()`, or other new APIs that a junior dev might not know exist?
- [ ] Can you explain WHY Java added a feature? (e.g., "Records exist because 90% of Java classes were just data holders with 50 lines of boilerplate")

### OOP Principles

- [ ] Can you reinforce SOLID principles where they're demonstrated or violated?
- [ ] Is there a good example of polymorphism, encapsulation, or abstraction to call out?

### Testing

- [ ] Are tests present for the reviewed code?
- [ ] Do tests cover edge cases and not just the happy path?
- [ ] Are test names descriptive? (`should_returnZero_when_listIsEmpty` > `test1`)
