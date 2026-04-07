# Testing the Bot (JDA) Layer

**Date:** April 6, 2026

## Overview

The bot layer (`pokemonGame.bot.*`) is the hardest layer to test because every method depends on JDA objects (`SlashCommandInteractionEvent`, `User`, `Member`) that are created by the Discord WebSocket — you can't just `new` them in a test.

The solution is to **make the handlers thin** so the testable logic lives elsewhere.

## The Problem: Thick Handlers

Currently, `SlashExample.onSlashCommandInteraction()` does everything:
1. Reads Discord input from the event
2. Calls service/CRUD methods
3. Makes business decisions (does the trainer exist? is the team set up?)
4. Formats and sends the Discord reply

This means you can't run *any* of that logic without a real Discord event object.

## The Solution: Thin Handler Pattern

### Step 1 — Extract Decision Logic into Service Methods

Move the business logic from each slash command case into a service method that takes **plain Java types** and returns a **plain result**:

```java
// In TrainerService (or a new CommandService):
public record CreateTrainerResult(boolean success, String message, Trainer trainer) {}

public CreateTrainerResult handleCreateTrainer(String name, long discordId, String discordUsername) {
    Trainer existing = getTrainerByDiscordId(discordId);
    if (existing != null) {
        return new CreateTrainerResult(false, "You already have a trainer!", null);
    }
    Trainer created = createTrainer(name, discordId, discordUsername);
    if (created == null) {
        return new CreateTrainerResult(false, "Failed to create trainer.", null);
    }
    return new CreateTrainerResult(true, "Trainer " + name + " created!", created);
}
```

### Step 2 — Slash Command Handler Becomes a Thin Adapter

The handler only parses Discord input, delegates to the service, and sends the reply:

```java
case "createtrainer":
    var result = trainerService.handleCreateTrainer(
        event.getOption("name").getAsString(), userId, user);
    event.reply(result.message()).setEphemeral(!result.success()).queue();
    return;
```

### Step 3 — Test the Service Method Without JDA

```java
@Test
void handleCreateTrainerReturnsSuccessMessage() {
    TrainerService service = new TrainerService();
    var result = service.handleCreateTrainer("Ash", 12345L, "ash_discord");
    assertTrue(result.success());
    assertEquals("Trainer Ash created!", result.message());
}
```

## What About Testing the JDA Wiring?

For the remaining thin handler (parse event → call service → reply), you have two options:

### Option A: Don't Unit-Test It

If the handler is just 3 lines (parse, delegate, reply), there's very little to go wrong. Manual testing via Discord is sufficient for this thin layer. This is a reasonable choice for a learning project.

### Option B: Mockito (Advanced)

You can mock `SlashCommandInteractionEvent` and verify that `event.reply()` was called with the right message. This requires adding Mockito to `pom.xml`:

```xml
<dependency>
    <groupId>org.mockito</groupId>
    <artifactId>mockito-core</artifactId>
    <version>5.11.0</version>
    <scope>test</scope>
</dependency>
```

Mockito lets you create fake objects that simulate JDA classes without a real Discord connection. This is valuable to learn but a bigger investment.

## Summary: Recommended Order of Work

| Step | Action | Benefit |
|------|--------|---------|
| 1 | Extract business logic from `SlashExample` into service methods with plain return types | Service methods become testable without JDA |
| 2 | Write tests for the service methods | High-value tests covering real business logic |
| 3 | Keep slash command handlers as thin adapters (3–5 lines each) | Minimal untested code remaining |
| 4 | (Optional) Add Mockito for handler wiring tests | Full coverage if desired |

## Key Principle

The architectural rule here is **separation of I/O from logic**:
- **Game-logic / service methods** → pure, accept params, return results, no JDA dependency → easily testable
- **Event handlers** → parse JDA input, call services, format output → thin, minimal logic, tested manually or with mocks
