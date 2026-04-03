package pokemonGame;

public record TurnResult(DamageResult action1Result, DamageResult action2Result, boolean battleOver, Trainer winner) {
    
}
