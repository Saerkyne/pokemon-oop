package pokemonGame;
import java.util.ArrayList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.Collections;
import java.util.List;

// Creates a Trainer object in memory based on information entered from a user
// or data pulled from the database.

public class Trainer {
    private static final Logger LOGGER = LoggerFactory.getLogger(Trainer.class);
    private String name;
    private List<Pokemon> team;
    private int dbId; // this is unused until the database creates it; we have set/get for it
    private long discordId; // this is unused until we add the discord bot; we have set/get for it

    public Trainer(String name) {
        this.name = name;
        this.team = new ArrayList<Pokemon>();
    }

    public int getDbId() {
        return dbId;
    }

    public void setDbId(int dbId) {
        this.dbId = dbId;
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

    public List<Pokemon> getTeam() {
        
        return Collections.unmodifiableList(team);
    }

    public void setName(String name) {
        this.name = name;
    }

    

    // Add single pokemon to team
    public boolean addPokemonToTeam(Pokemon pokemon) {

        if (team.size() < 6) {
            team.add(pokemon);
            LOGGER.info("Added {} ({}) to {}'s team.", pokemon.getNickname(), pokemon.getSpecies(), this.name);
            return true;
        } else {
            LOGGER.warn("Team is full! Cannot add more Pokémon.");
            return false;
        }
    }

    // Remove single pokemon from team
    public boolean removePokemonFromTeam(Pokemon pokemon) {
        if (team.remove(pokemon)) {
            LOGGER.info("Removed {} ({}) from {}'s team.", pokemon.getNickname(), pokemon.getSpecies(), this.name);
            return true;
        } else {
            LOGGER.warn("{} is not on {}'s team. Cannot remove.", pokemon.getNickname(), this.name);
            return false;
        }
    }

}
