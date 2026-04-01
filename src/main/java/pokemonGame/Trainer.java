package pokemonGame;
import java.util.ArrayList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pokemonGame.db.TrainerCRUD;

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

    // Set active pokemon in team. Should default to the first pokemon added to the team, but this can be used to change the active pokemon if needed (e.g. for switch action).
    public void setActivePokemon(Pokemon activePokemon) {
        if (team.contains(activePokemon)) {
            team.remove(activePokemon);
            team.add(0, activePokemon); // Move the active Pokémon to the front of the list
            LOGGER.info("{} is now the active Pokémon for {}.", activePokemon.getNickname(), this.name);
        } else {
            LOGGER.warn("{} is not on {}'s team. Cannot set as active Pokémon.", activePokemon.getNickname(), this.name);
        }
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

    public static int createTrainer(String name, long discordID, String discordUsername) {
        // This method can be used to create a trainer and set up their team
        // Create a Trainer and ask for their name
        TrainerCRUD trainerCRUDSearch = new TrainerCRUD();
        Trainer trainerSearch = trainerCRUDSearch.getTrainerByDiscordId(discordID);
        
        if (trainerSearch != null) {
            LOGGER.info("Welcome back, {}!", trainerSearch.getName());
            return -1; // Return -1 to indicate that a new trainer was not created
        } else {
            LOGGER.info("No existing trainer found with Discord ID: {}", discordID);
            LOGGER.info("Creating a new trainer profile for {}...", name);
        
        
            Trainer trainer = new Trainer(name);

            TrainerCRUD trainerCRUDCreate = new TrainerCRUD();
            int trainerId = trainerCRUDCreate.createDBTrainer(discordID, discordUsername, name);
            trainer.setDbId(trainerId);

            LOGGER.info("{}, welcome to the world of Pokemon!", trainer.getName());

            System.out.println();
            System.out.println();
            System.out.println();

            System.out.println("You can have up to 6 Pokemon on your team. Let's start by choosing your first Pokemon!");

            return 1; // Return 1 to indicate that a new trainer was created
        }
    }

}
