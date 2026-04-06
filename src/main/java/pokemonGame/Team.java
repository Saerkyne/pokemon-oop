package pokemonGame;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.ArrayList;

/**
 * Domain object representing a named team belonging to a {@link Trainer}.
 * Maps to a group of rows in the {@code trainer_teams} table sharing the
 * same {@code team_id}. A trainer may have multiple teams; each team holds
 * up to 6 Pokémon.
 *
 * @see Trainer
 * @see TeamService
 */
public class Team {

    private static final Logger LOGGER = LoggerFactory.getLogger(Team.class);

    private String teamName;
    private int teamDbId; // this is unused until the database creates it; we have set/get for it
    private int trainerDbId; // this is unused until we link the team to a trainer in the database; we have set/get for it
    private List<Pokemon> pokemonList; // List of Pokémon in this team, up to 6
    private Pokemon teamSlotOne; // The currently active Pokémon in this team (the first Pokémon in the list)
    private Pokemon teamSlotTwo; // The currently benched Pokémon in this team (the second Pokémon in the list)
    private Pokemon teamSlotThree; // The currently benched Pokémon in this team (the third Pokémon in the list)
    private Pokemon teamSlotFour; // The currently benched Pokémon in this team (the fourth Pokémon in the list)
    private Pokemon teamSlotFive; // The currently benched Pokémon in this team (the fifth Pokémon in the list)
    private Pokemon teamSlotSix; // The currently benched Pokémon in this team (the sixth Pokémon in the list)

    public static final int MAX_TEAM_SIZE = 6;

    public Team(String teamName) {
        this.teamName = teamName;
        this.pokemonList = new ArrayList<>(); // Initialize the Pokémon list
    }

    public int getTeamDbId() {
        return teamDbId;
    }

    public void setTeamDbId(int teamDbId) {
        this.teamDbId = teamDbId;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public int getTrainerDbId() {
        return trainerDbId;
    }

    public void setTrainerDbId(int trainerDbId) {
        this.trainerDbId = trainerDbId;
    }

    public List<Pokemon> getPokemonList() {
        return pokemonList;
    }

    public void setPokemonList(List<Pokemon> pokemonList) {
        this.pokemonList = pokemonList;
    }

    public Pokemon getTeamSlot(int teamSlotIndex) {
        switch (teamSlotIndex) {
            case 0: return teamSlotOne;
            case 1: return teamSlotTwo;
            case 2: return teamSlotThree;
            case 3: return teamSlotFour;
            case 4: return teamSlotFive;
            case 5: return teamSlotSix;
            default:
                LOGGER.warn("Invalid team slot index: {}. Valid range is 0-5.", teamSlotIndex);
                return null;
        }
    }

    public void setTeamSlotOne(Pokemon teamSlotOne) {
        this.teamSlotOne = teamSlotOne;
        if (pokemonList != null && !pokemonList.isEmpty()) {
            this.teamSlotOne = pokemonList.get(0); // Set the first Pokémon in the list as the active Pokémon
        } else {
            LOGGER.warn("Pokemon list is empty. Cannot set team slot one.");
        }
    }

    

    public void setTeamSlotTwo(Pokemon teamSlotTwo) {
        this.teamSlotTwo = teamSlotTwo;
        if (pokemonList != null && pokemonList.size() > 1) {
            this.teamSlotTwo = pokemonList.get(1); // Set the second Pokémon in the list as the second slot
        } else {
            LOGGER.warn("Pokemon list does not have enough Pokémon. Cannot set team slot two.");
        }
    }

    

    public void setTeamSlotThree(Pokemon teamSlotThree) {
        this.teamSlotThree = teamSlotThree;
        if (pokemonList != null && pokemonList.size() > 2) {
            this.teamSlotThree = pokemonList.get(2); // Set the third Pokémon in the list as the third slot
        } else {
            LOGGER.warn("Pokemon list does not have enough Pokémon. Cannot set team slot three.");
        }
    }

   

    public void setTeamSlotFour(Pokemon teamSlotFour) {
        this.teamSlotFour = teamSlotFour;
        if (pokemonList != null && pokemonList.size() > 3) {
            this.teamSlotFour = pokemonList.get(3); // Set the fourth Pokémon in the list as the fourth slot
        } else {
            LOGGER.warn("Pokemon list does not have enough Pokémon. Cannot set team slot four.");
        }
    }

    

    public void setTeamSlotFive(Pokemon teamSlotFive) {
        this.teamSlotFive = teamSlotFive;
        if (pokemonList != null && pokemonList.size() > 4) {
            this.teamSlotFive = pokemonList.get(4); // Set the fifth Pokémon in the list as the fifth slot
        } else {
            LOGGER.warn("Pokemon list does not have enough Pokémon. Cannot set team slot five.");
        }
    }

    

    public void setTeamSlotSix(Pokemon teamSlotSix) {
        this.teamSlotSix = teamSlotSix;
        if (pokemonList != null && pokemonList.size() > 5) {
            this.teamSlotSix = pokemonList.get(5); // Set the sixth Pokémon in the list as the sixth slot
        } else {
            LOGGER.warn("Pokemon list does not have enough Pokémon. Cannot set team slot six.");
        }
    }

    public int getTeamSize() {
        return pokemonList != null ? pokemonList.size() : 0;
    }   

    public boolean contains(Pokemon pokemon) {
        return pokemonList != null && pokemonList.contains(pokemon);
    }

    public void add(Pokemon pokemon) {
        if (pokemonList != null && pokemonList.size() < 6) {
            pokemonList.add(pokemon);
        } else {
            LOGGER.warn("Cannot add Pokémon to team. Team is full or pokemon list is not initialized.");
        }
    }

    public void remove(Pokemon pokemon) {
        if (pokemonList != null && pokemonList.contains(pokemon)) {
            pokemonList.remove(pokemon);
        } else {
            LOGGER.warn("Cannot remove Pokémon from team. Pokémon not found in team or pokemon list is not initialized.");
        }
    }

    public boolean isFull() {
        return pokemonList != null && pokemonList.size() >= MAX_TEAM_SIZE;
    }
}
