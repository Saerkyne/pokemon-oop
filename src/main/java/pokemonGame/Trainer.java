package pokemonGame;
import java.util.ArrayList;

// Creates a Trainer object in memory based on information entered from a user
// or data pulled from the database.

public class Trainer {
    private String name;
    private ArrayList<Pokemon> team;
    private int DBid; // this is unused until the database creates it; we have set/get for it
    private long discordId; // this is unused until we add the discord bot; we have set/get for it

    public Trainer(String name) {
        this.name = name;
        this.team = new ArrayList<Pokemon>();
    }

    public int getDBId() {
        return DBid;
    }

    public void setDBId(int DBid) {
        this.DBid = DBid;
    }

    public long getDiscordId() {
        return discordId;
    }

    public void setDiscordId(long discordId) {
        this.discordId = discordId;
    }

    public String getName() {
        return name;
    }

    public ArrayList<Pokemon> getTeam() {
        
        return team;
    }

    public void setName(String name) {
        this.name = name;
    }

    

    // Add single pokemon to team
    public void addPokemonToTeam(Pokemon pokemon) {

        if (team.size() < 6) {
            team.add(pokemon);
            System.out.println("Added " + pokemon.getName() + " (" + pokemon.getSpecies() + ") to " + this.name + "'s team.");
            
        } else {
            System.out.println("Team is full! Cannot add more Pokémon.");
        }
    }

}
