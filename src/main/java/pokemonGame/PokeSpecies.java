package pokemonGame;

public enum PokeSpecies {
    ABRA("Abra"),
    AERODACTYL("Aerodactyl"),
    ALAKAZAM("Alakazam"),
    ARBOK("Arbok"),
    ARCANINE("Arcanine"),
    ARTICUNO("Articuno"),
    BEEDRILL("Beedrill"),
    BELLSPROUT("Bellsprout"),
    BLASTOISE("Blastoise"),
    BULBASAUR("Bulbasaur"),
    BUTTERFREE("Butterfree"),
    CATERPIE("Caterpie"),
    CHANSEY("Chansey"),
    CHARIZARD("Charizard"),;

    private final String displayName;
    PokeSpecies(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
