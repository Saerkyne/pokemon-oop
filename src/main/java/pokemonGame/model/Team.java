package pokemonGame.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// TODO: MDL-7 — Remove this service-layer import. Model should not depend on service layer. Used only in @see Javadoc.
import pokemonGame.service.TeamService;

import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

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
    private List<Pokemon> teamAsList; // List of Pokémon in this team, up to 6
    
    public static final int MAX_TEAM_SIZE = 6;

    public Team(String teamName) {
        this.teamName = teamName;
        this.teamAsList = new ArrayList<>(); // Initialize the Pokémon list
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

    public List<Pokemon> getTeamAsList() {
        return Collections.unmodifiableList(teamAsList); // Return an unmodifiable view to prevent external modification
    }

    // TODO: MDL-6 — Store defensive copy: this.teamAsList = new ArrayList<>(teamAsList). Caller can mutate list after passing it in.
    public void setTeamAsList(List<Pokemon> teamAsList) {
        this.teamAsList = teamAsList;
    }

    public int getTeamSize() {
        return teamAsList.size();
    }

    public Pokemon getTeamSlot(int teamSlotIndex) {
        if (teamSlotIndex < 0 || teamSlotIndex >= teamAsList.size()) {
            LOGGER.warn("Invalid team slot index: {}. Valid range is 0-{}.", teamSlotIndex, teamAsList.size() - 1);
            return null;
        }
        return teamAsList.get(teamSlotIndex);
    }

    public void setTeamSlot(int teamSlotIndex, Pokemon pokemon) {
        if (teamSlotIndex < 0 || teamSlotIndex >= MAX_TEAM_SIZE) {
            LOGGER.warn("Invalid team slot index: {}. Valid range is 0-{}.", teamSlotIndex, MAX_TEAM_SIZE - 1);
            return;
        }

        // Sync teamAsList: pad with nulls if the list hasn't grown to this index yet,
        // then set the element. ArrayList.set() requires the index to already exist;
        // ArrayList.add() appends to the end. By padding first, we ensure set() works.
        while (teamAsList.size() <= teamSlotIndex) {
            teamAsList.add(null);
        }
        teamAsList.set(teamSlotIndex, pokemon);
        if (pokemon != null) {
            pokemon.setCurrentTeamSlotIndex(teamSlotIndex);
        }
    }   

    public void add(Pokemon pokemon) {
        if (teamAsList != null && teamAsList.size() < MAX_TEAM_SIZE) {
            teamAsList.add(pokemon);
        } else {
            LOGGER.warn("Cannot add Pokémon to team. Team is full or teamAsList is not initialized.");
        }
    }

    public void remove(Pokemon pokemon) {
        if (teamAsList != null && teamAsList.contains(pokemon)) {
            teamAsList.remove(pokemon);
        } else {
            LOGGER.warn("Cannot remove Pokémon from team. Pokémon not found in team or teamAsList is not initialized.");
        }
    }

    public boolean isFull() {
        return teamAsList != null && teamAsList.size() >= MAX_TEAM_SIZE;
    }
}
