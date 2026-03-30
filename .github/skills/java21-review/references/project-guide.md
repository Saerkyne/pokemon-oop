# Java 21 Project Guide Reference

Template and guidelines for producing project architecture guides. This reference defines the structure, tone, and constraints for project planning output.

---

## Output Constraints

- **No implementation code.** The guide produces outlines, diagrams, and explanations — not `.java` files.
- **Java is the center.** Other technologies (databases, libraries, formats) are recommended as supporting tools, never as replacements for Java logic.
- **Target audience: junior devs.** Explain architectural decisions. Don't assume familiarity with design patterns — name them and say why they fit.
- **Two diagram formats required:** Every flow or architecture diagram must appear as both a Mermaid code block AND a plain English walkthrough.

---

## Guide Structure

Follow this structure for every project guide. Adapt section depth to the project's complexity.

### 1. Project Overview

Restate the project goal in clear, jargon-free terms. Include:

- What the application does from a user's perspective
- What kind of application it is (CLI tool, web API, Discord bot, desktop app, etc.)
- Key assumptions made from the description (state these explicitly so the user can correct them)

### 2. Recommended Java 21 Features

For each recommended feature, explain:

- **What it is** (1-2 sentence definition)
- **Where it fits in this project** (specific use case)
- **Why it helps** (what problem it solves or what it simplifies)

Features to consider for every project:

| Feature | When to Recommend |
| --------- | ------------------- |
| **Records** | Project has data-transfer objects, configuration, or immutable value types |
| **Sealed classes/interfaces** | Project has a known, fixed set of subtypes (shapes, commands, event types, status codes) |
| **Pattern matching (`switch`, `instanceof`)** | Project needs to branch on type or value; replaces if/else chains and visitor pattern |
| **Text blocks** | Project embeds SQL, JSON, HTML, or multi-line strings |
| **Enhanced switch (arrow syntax)** | Any switch statement — always recommend over traditional syntax |
| **Virtual threads** | Project does I/O-bound work (DB queries, HTTP calls, file processing) with potential concurrency |
| **Sequenced collections** | Project works with ordered data and needs first/last access or reversed views |
| **`var` local type inference** | Local variables where the type is obvious from the right-hand side |
| **`Stream.toList()`** | Any stream pipeline collecting to a list |
| **`String.formatted()`** | String formatting — cleaner than `String.format()` |

Only recommend features that genuinely fit. Don't force virtual threads into a single-threaded CLI tool.

### 3. Architecture / Component Breakdown

Provide a **layered architecture diagram** showing:

- The major layers (UI/presentation, domain/logic, persistence/data, external integrations)
- Key packages and their responsibilities
- Data flow direction between layers
- Which layer owns which concerns

#### Mermaid Diagram

Use a `graph TD` (top-down) or `graph LR` (left-right) diagram:

```
Example structure (adapt to the actual project):

graph TD
    A[Presentation Layer] --> B[Domain / Logic Layer]
    B --> C[Persistence / Data Layer]
    B --> D[External APIs]
    
    subgraph Presentation
        A1[CLI / Discord Bot / Web Controller]
    end
    
    subgraph Domain
        B1[Core Entities]
        B2[Business Rules / Services]
    end
    
    subgraph Persistence
        C1[DAO / Repository Classes]
        C2[Database]
    end
```

#### Plain English Numbered Walkthrough

After every Mermaid diagram, provide a numbered walkthrough:

> **How the architecture works:**

> 1. The **Presentation Layer** receives user input (commands, HTTP requests, etc.) and passes it to the Domain Layer. It never touches the database directly.
> 2. The **Domain Layer** contains all business logic — rules, calculations, validations. It doesn't know how data is stored or how users interact. This makes it testable in isolation.
> 3. The **Persistence Layer** handles reading from and writing to the database. It translates between domain objects and database rows.
> 4. Data flows downward (presentation → domain → persistence) for commands, and upward for queries.

### 4. Flow Diagrams

For the main user workflows (2-4 key flows), provide:

#### Mermaid Sequence or Flowchart Diagram

Use `sequenceDiagram` for interactions between components, or `flowchart TD` for decision logic:

```
Example flow (adapt to the actual project):

sequenceDiagram
    participant User
    participant Bot
    participant GameLogic
    participant Database

    User->>Bot: /command
    Bot->>GameLogic: processCommand(args)
    GameLogic->>Database: loadData()
    Database-->>GameLogic: data
    GameLogic-->>Bot: result
    Bot-->>User: response message
```

#### Plain English Walkthrough

> **What happens when a user runs `/command`:**
>
> 1. The user types the command in Discord (or CLI, or web form).
> 2. The bot layer parses the command and extracts arguments.
> 3. The game logic layer processes the request — no I/O here, just logic.
> 4. If data is needed, the persistence layer fetches it from the database.
> 5. The result flows back up through the layers to the user.

### 5. Suggested Implementation Order

Number the steps. For each step, explain:

- **What to build** (specific classes, packages, or features)
- **Why this order** (what this step enables for the next step)
- **Definition of done** (how the dev knows this step is complete — often "tests pass for X")

General order principle: **build from the inside out.**

1. **Domain entities first** — data classes, enums, value objects. These have no dependencies and are easiest to test.
2. **Core logic second** — business rules, calculations, validations that operate on domain entities.
3. **Persistence third** — DAO/repository classes that store and retrieve domain entities. Test with a real DB or in-memory DB.
4. **Presentation last** — user-facing layer that ties everything together. This is the thinnest layer.

### 6. Potential Pitfalls

List 5-10 common mistakes for this type of project. For each:

- **The pitfall** (what goes wrong)
- **Why it happens** (common cause)
- **How to avoid it** (specific guidance)

Universal pitfalls to consider:

| Pitfall | Why It Happens | Prevention |
| --------- | --------------- | ------------ |
| Mixing I/O into domain logic | Seems faster to put DB calls where data is needed | Enforce layers — domain classes never import `java.sql`, I/O libraries, or framework classes |
| Catching `Exception` everywhere | "It works" mentality | Catch specific exceptions. Empty catch blocks are always a bug. |
| Skipping tests for "simple" code | Underestimating edge cases | Write tests for domain logic from day one. Simple code gets complex fast. |
| Hardcoding configuration | Works on my machine | Use environment variables or config files from the start |
| Giant classes with 500+ lines | Features get added incrementally without refactoring | Single Responsibility Principle — one class, one job |
| Premature optimization | Guessing what's slow before measuring | Write clear code first, profile if actually slow. Java's JIT compiler is very good. |
| Ignoring `null` | Java has nullable references everywhere | Use `Optional` for return values, validate inputs at boundaries, consider `@Nullable`/`@NonNull` annotations |

### 7. Tool & Technology Recommendations

For each recommendation:

- **What it is** (tool/library/technology)
- **Why it fits this project** (specific use case)
- **How it integrates with Java** (Maven dependency, JDBC driver, etc.)
- **Alternatives** (if the recommendation doesn't fit, what else works)

Categories to consider:

| Category | When to Recommend | Examples |
| --- | ------------------- | ---------- |
| **Database** | Project needs persistent data | MariaDB/PostgreSQL (relational), SQLite (embedded), H2 (in-memory for tests) |
| **Data format** | Project exchanges structured data | Jackson (JSON), OpenCSV (CSV), JAXB (XML) |
| **HTTP client** | Project calls external APIs | `java.net.http.HttpClient` (built-in Java 11+), OkHttp |
| **Testing** | Always | JUnit 5, Mockito for mocks, AssertJ for fluent assertions |
| **Logging** | Always | SLF4J + Logback |
| **Build tool** | Always | Maven or Gradle (explain tradeoff) |
| **Connection pooling** | Project uses a database | HikariCP |
| **Other languages** | Project has a component better suited to another language | Python (ML/data analysis), JavaScript (web frontend), SQL (complex queries) — but Java stays central |

**Keep Java central.** If recommending another language or tool, explain how it connects back to the Java application (e.g., "Python for data analysis, called from Java via ProcessBuilder or a REST API, with results consumed as JSON in your Java domain layer").
