# Pokemon-OOP — AI Context Map

> **Stack:** raw-http | none | unknown | java

> 0 routes | 0 models | 0 components | 0 lib files | 0 env vars | 1 middleware | 901 import links
> **Token savings:** this file is ~1,300 tokens. Without it, AI exploration would cost ~9,400 tokens. **Saves ~8,100 tokens per conversation.**

---

# Middleware

## custom
- KarateChop — `src/main/java/pokemonGame/moves/KarateChop.java`

---

# Dependency Graph

## Most Imported Files (change these carefully)

- `src/main/java/pokemonGame/model/Pokemon.java` — imported by **173** files
- `src/main/java/pokemonGame/model/Move.java` — imported by **172** files
- `src/main/java/pokemonGame/core/StatCalculator.java` — imported by **157** files
- `src/main/java/pokemonGame/core/Stat.java` — imported by **157** files
- `src/main/java/pokemonGame/model/LearnsetEntry.java` — imported by **152** files
- `src/main/java/pokemonGame/model/Trainer.java` — imported by **13** files
- `src/main/java/pokemonGame/model/Team.java` — imported by **9** files
- `src/main/java/pokemonGame/species/Abra.java` — imported by **7** files
- `src/main/java/pokemonGame/model/Battle.java` — imported by **4** files
- `src/main/java/pokemonGame/service/BattleService.java` — imported by **4** files
- `src/main/java/pokemonGame/species/PokeSpecies.java` — imported by **4** files
- `src/main/java/pokemonGame/core/EvManager.java` — imported by **4** files
- `src/main/java/pokemonGame/core/Natures.java` — imported by **4** files
- `src/main/java/pokemonGame/model/MoveSlot.java` — imported by **3** files
- `src/main/java/pokemonGame/db/TrainerCRUD.java` — imported by **3** files
- `src/main/java/pokemonGame/species/PokemonFactory.java` — imported by **3** files
- `src/main/java/pokemonGame/core/TypeChart.java` — imported by **2** files
- `src/main/java/pokemonGame/db/TeamCRUD.java` — imported by **2** files
- `src/main/java/pokemonGame/db/DatabaseSetup.java` — imported by **2** files
- `src/main/java/pokemonGame/service/TrainerService.java` — imported by **2** files

## Import Map (who imports what)

- `src/main/java/pokemonGame/model/Pokemon.java` ← `src/main/java/pokemonGame/battle/Attack.java`, `src/main/java/pokemonGame/battle/BattleAction.java`, `src/main/java/pokemonGame/battle/MoveAction.java`, `src/main/java/pokemonGame/battle/SwitchAction.java`, `src/main/java/pokemonGame/battle/TurnManager.java` +168 more
- `src/main/java/pokemonGame/model/Move.java` ← `src/main/java/pokemonGame/battle/Attack.java`, `src/main/java/pokemonGame/battle/MoveAction.java`, `src/main/java/pokemonGame/battle/TurnManager.java`, `src/main/java/pokemonGame/moves/Absorb.java`, `src/main/java/pokemonGame/moves/Acid.java` +167 more
- `src/main/java/pokemonGame/core/StatCalculator.java` ← `src/main/java/pokemonGame/bot/SlashExample.java`, `src/main/java/pokemonGame/db/PokemonCRUD.java`, `src/main/java/pokemonGame/model/Pokemon.java`, `src/main/java/pokemonGame/species/Abra.java`, `src/main/java/pokemonGame/species/Aerodactyl.java` +152 more
- `src/main/java/pokemonGame/core/Stat.java` ← `src/main/java/pokemonGame/db/PokemonCRUD.java`, `src/main/java/pokemonGame/model/Pokemon.java`, `src/main/java/pokemonGame/species/Abra.java`, `src/main/java/pokemonGame/species/Aerodactyl.java`, `src/main/java/pokemonGame/species/Alakazam.java` +152 more
- `src/main/java/pokemonGame/model/LearnsetEntry.java` ← `src/main/java/pokemonGame/species/Abra.java`, `src/main/java/pokemonGame/species/Aerodactyl.java`, `src/main/java/pokemonGame/species/Alakazam.java`, `src/main/java/pokemonGame/species/Arbok.java`, `src/main/java/pokemonGame/species/Arcanine.java` +147 more
- `src/main/java/pokemonGame/model/Trainer.java` ← `src/main/java/pokemonGame/battle/BattleAction.java`, `src/main/java/pokemonGame/battle/MoveAction.java`, `src/main/java/pokemonGame/battle/SwitchAction.java`, `src/main/java/pokemonGame/battle/TurnManager.java`, `src/main/java/pokemonGame/battle/TurnResult.java` +8 more
- `src/main/java/pokemonGame/model/Team.java` ← `src/main/java/pokemonGame/battle/BattleAction.java`, `src/main/java/pokemonGame/battle/MoveAction.java`, `src/main/java/pokemonGame/battle/SwitchAction.java`, `src/main/java/pokemonGame/battle/TurnManager.java`, `src/main/java/pokemonGame/bot/AutoCompleteBot.java` +4 more
- `src/main/java/pokemonGame/species/Abra.java` ← `src/test/java/pokemonGame/battleTests/AttackTest.java`, `src/test/java/pokemonGame/battleTests/TurnManagerTest.java`, `src/test/java/pokemonGame/coreTests/EvAdderTest.java`, `src/test/java/pokemonGame/coreTests/EvSetterTest.java`, `src/test/java/pokemonGame/coreTests/NaturesTest.java` +2 more
- `src/main/java/pokemonGame/model/Battle.java` ← `src/main/java/pokemonGame/battle/TurnManager.java`, `src/main/java/pokemonGame/db/BattleCRUD.java`, `src/main/java/pokemonGame/service/BattleService.java`, `src/test/java/pokemonGame/battleTests/TurnManagerTest.java`
- `src/main/java/pokemonGame/service/BattleService.java` ← `src/main/java/pokemonGame/battle/TurnManager.java`, `src/main/java/pokemonGame/bot/SlashExample.java`, `src/main/java/pokemonGame/db/BattleTurnCRUD.java`, `src/main/java/pokemonGame/model/Battle.java`

---

_Generated by [codesight](https://github.com/Houseofmvps/codesight) — see your codebase clearly_