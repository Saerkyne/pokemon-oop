# Java Code Review Guide

Java Review Focus Areas: New features in Java 17/21, Spring Boot 3 best practices, concurrent programming (Virtual Threads), JPA performance optimization, and code maintainability.

## Table of Contents

- [Modern Java Features (17/21+)](#modern-java-features-1721)
- [Stream API & Optional](#stream-api--optional)
- [Spring Boot Best Practices](#spring-boot-best-practices)
- [JPA & Database Performance](#jpa-and-database-performance)
- [Concurrency & Virtual Threads](#concurrency-and-virtual-threads)
- [Lombok Usage Guidelines](#lombok-usage-guidelines)
- [Exception Handling](#exception-handling)
- [Testing Guidelines](#testing-guidelines)
- [Review Checklist](#review-checklist)

---

## Modern Java Features (17/21+)

### Records

```java
// ❌ Traditional POJO/DTO: Excessive boilerplate code
public class UserDto {
    private final String name;
    private final int age;

    public UserDto(String name, int age) {
        this.name = name;
        this.age = age;
    }
    // getters, equals, hashCode, toString...
}

// ✅ Using Records: Concise, immutable, semantically clear
public record UserDto(String name, int age) {
    // Compact constructor for validation
    public UserDto {
        if (age < 0) throw new IllegalArgumentException("Age cannot be negative");
    }
}
```

### Switch Expressions and Pattern Matching

```java
// ❌ Traditional Switch: Prone to missing 'break' statements; verbose and error-prone
String type = "";
switch (obj) {
    case Integer i: // Java 16+
        type = String.format("int %d", i);
        break;
    case String s:
        type = String.format("string %s", s);
        break;
    default:
        type = "unknown";
}

// ✅ Switch Expressions: No fall-through risks; enforces a return value
String type = switch (obj) {
    case Integer i -> "int %d".formatted(i);
    case String s  -> "string %s".formatted(s);
    case null      -> "null value"; // Java 21: Handles null values
    default        -> "unknown";
};
```

### Text Blocks

```java
// ❌ Concatenating SQL/JSON strings
String json = "{\n" +
              "  \"name\": \"Alice\",\n" +
              "  \"age\": 20\n" +
              "}";

// ✅ Using Text Blocks: What you see is what you get (WYSIWYG)
String json = """
    {
      "name": "Alice",
      "age": 20
    }
    """;
```

---

## Stream API & Optional

### Avoid Misusing Stream

```java
// ❌ Simple loops don't need Stream (performance overhead + poor readability)
items.stream().forEach(item -> {
    process(item);
});

// ✅ Use for-each for simple scenarios
for (var item : items) {
    process(item);
}

// ❌ Extremely complex Stream chains
List<Dto> result = list.stream()
    .filter(...)
    .map(...)
    .peek(...)
    .sorted(...)
    .collect(...); // Hard to debug

// ✅ Split into meaningful steps
var filtered = list.stream().filter(...).toList();
// ...
```

### Correct Usage of Optional

```java
// ❌ Using Optional as a parameter or field (serialization issues, increased complexity)
public void process(Optional<String> name) { ... }
public class User {
    private Optional<String> email; // Not recommended
}

// ✅ Optional should only be used as a return value
public Optional<User> findUser(String id) { ... }

// ❌ Using isPresent() + get() with Optional
Optional<User> userOpt = findUser(id);
if (userOpt.isPresent()) {
    return userOpt.get().getName();
} else {
    return "Unknown";
}

// ✅ Using functional API
return findUser(id)
    .map(User::getName)
    .orElse("Unknown");
```

---

## Spring Boot Best Practices

### Dependency Injection (DI)

```java
// ❌ Field Injection (@Autowired)
// Disadvantages: Hard to test (requires reflection), hides excessive dependencies, and lacks immutability
@Service
public class UserService {
    @Autowired
    private UserRepository userRepo;
}

// ✅ Constructor Injection
// Advantages: Dependencies are explicit, easy to unit test (Mock), fields can be final
@Service
public class UserService {
    private final UserRepository userRepo;

    public UserService(UserRepository userRepo) {
        this.userRepo = userRepo;
    }
}
// 💡 Tip: Combine with Lombok @RequiredArgsConstructor to simplify code, but be cautious of circular dependencies
```

### Configuration Management

```java
// ❌ Hard-coded configuration values
@Service
public class PaymentService {
    private String apiKey = "sk_live_12345";
}

// ❌ Directly using @Value scattered throughout the code
@Value("${app.payment.api-key}")
private String apiKey;

// ✅ Using @ConfigurationProperties for type-safe configuration
@ConfigurationProperties(prefix = "app.payment")
public record PaymentProperties(String apiKey, int timeout, String url) {}
```

---

## JPA And Database Performance

### N+1 Query Problem

```java
// ❌ FetchType.EAGER or triggering lazy loading in a loop
// Entity definition
@Entity
public class User {
    @OneToMany(fetch = FetchType.EAGER) // Dangerous!
    private List<Order> orders;
}

// Business code
List<User> users = userRepo.findAll(); // 1 SQL
for (User user : users) {
    // If Lazy, this will trigger N SQL queries
    System.out.println(user.getOrders().size());
}

// ✅ Using @EntityGraph or JOIN FETCH
@Query("SELECT u FROM User u JOIN FETCH u.orders")
List<User> findAllWithOrders();
```

### Transaction Management

```java
// ❌ Starting a transaction in the Controller layer (database connection occupied for too long)
// ❌ Adding @Transactional to private methods (AOP does not work)
@Transactional
private void saveInternal() { ... }

// ✅ Adding @Transactional to public methods in the Service layer
// ✅ Explicitly marking read operations as readOnly = true (performance optimization)
@Service
public class UserService {
    @Transactional(readOnly = true)
    public User getUser(Long id) { ... }

    @Transactional
    public void createUser(UserDto dto) { ... }
}
```

### Entity Design

```java
// ❌ Using Lombok @Data in Entity
// @Data generates equals/hashCode including all fields, which may trigger lazy loading causing performance issues or exceptions
@Entity
@Data
public class User { ... }

// ✅ Only use @Getter, @Setter
// ✅ Custom equals/hashCode (usually based on ID)
@Entity
@Getter
@Setter
public class User {
    @Id
    private Long id;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        return id != null && id.equals(((User) o).id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
```

---

## Concurrency and Virtual Threads

### Virtual Threads (Java 21+)

```java
// ❌ Traditional thread pool handling a large number of I/O blocking tasks (resource exhaustion)
ExecutorService executor = Executors.newFixedThreadPool(100);

// ✅ Using virtual threads for I/O intensive tasks (high throughput)
// Spring Boot 3.2+ enable: spring.threads.virtual.enabled=true
ExecutorService executor = Executors.newVirtualThreadPerTaskExecutor();

// In virtual threads, blocking operations (like DB queries, HTTP requests) almost do not consume OS thread resources
```

### Thread Safety

```java
// ❌ SimpleDateFormat is not thread-safe
private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

// ✅ Using DateTimeFormatter (Java 8+)
private static final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");

// ❌ HashMap in a multi-threaded environment may cause infinite loops or data loss
// ✅ Using ConcurrentHashMap
Map<String, String> cache = new ConcurrentHashMap<>();
```

---

## Lombok Usage Guidelines

```java
// ❌ Misusing @Builder can lead to missing required field validation
@Builder
public class Order {
    private String id; // Required
    private String note; // Optional
}
// Caller might miss id: Order.builder().note("hi").build();

// ✅ For critical business objects, it's recommended to manually write Builder or constructor to ensure invariants
// Or add validation logic in the build() method (Lombok @Builder.Default, etc.)
```

---

## Exception Handling

### Global Exception Handling

```java
// ❌ Using try-catch everywhere to swallow exceptions or just log them
try {
    userService.create(user);
} catch (Exception e) {
    e.printStackTrace(); // Should not be used in production
    // return null; // Swallowing the exception, upper layer won't know what happened
}

// ✅ Custom exceptions + @ControllerAdvice (Spring Boot 3 ProblemDetail)
public class UserNotFoundException extends RuntimeException { ... }

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(UserNotFoundException.class)
    public ProblemDetail handleNotFound(UserNotFoundException e) {
        return ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, e.getMessage());
    }
}
```

---

## Testing Guidelines

### Unit Tests vs Integration Tests

```java
// ❌ Unit tests depending on real database or external services
@SpringBootTest // Starts the entire context, slow
public class UserServiceTest { ... }

// ✅ Unit tests using Mockito
@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    @Mock UserRepository repo;
    @InjectMocks UserService service;

    @Test
    void shouldCreateUser() { ... }
}

// ✅ Integration tests using Testcontainers
@Testcontainers
@SpringBootTest
class UserRepositoryTest {
    @Container
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:15");
    // ...
}
```

---

## Review Checklist

### Basics & Conventions

- [ ] Follow Java 17/21 new features (Switch expressions, Records, Text Blocks)
- [ ] Avoid using deprecated classes (Date, Calendar, SimpleDateFormat)
- [ ] Prefer Stream API or Collections methods for collection operations
- [ ] Use Optional only for return values, not for fields or parameters

### Spring Boot

- [ ] Use constructor injection instead of @Autowired field injection
- [ ] Configuration properties use @ConfigurationProperties
- [ ] Controller has a single responsibility, business logic is delegated to Service
- [ ] Global exception handling uses @ControllerAdvice / ProblemDetail

### Database & Transactions

- [ ] Read-only transactions are marked with `@Transactional(readOnly = true)`
- [ ] Check for N+1 queries (EAGER fetch or loop calls)
- [ ] Entity classes do not use @Data, correctly implement equals/hashCode
- [ ] Database indexes cover query conditions

### Concurrency & Performance

- [ ] I/O intensive tasks consider virtual threads?
- [ ] Thread-safe classes are used correctly (ConcurrentHashMap vs HashMap)
- [ ] Lock granularity is reasonable? Avoid I/O operations inside locks

### Maintainability

- [ ] Critical business logic has sufficient unit tests
- [ ] Logging is appropriate (use Slf4j, avoid System.out)
- [ ] Magic values are extracted as constants or enums
