package pokemonGame.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Domain object representing a named team belonging to a {@link Trainer}.
 * Maps to one row in the {@code teams} table plus up to six related rows in
 * the {@code team_members} table. A trainer may have multiple teams; each
 * team holds up to 6 Pokémon.
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

    public void setTeamAsList(List<Pokemon> teamAsList) {
        this.teamAsList = new ArrayList<>(teamAsList); // Store a defensive copy to prevent external modification   
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

    // TODO [🟡 IMPORTANT | review 2026-04-20]: setTeamSlot pads teamAsList with nulls to reach the target index. Why: downstream callers iterate getTeamAsList() and hit NPE on null entries; `teamAsList.contains(null)` changes remove() semantics. Fix: switch internal storage to a fixed `Pokemon[MAX_TEAM_SIZE]` array with nullable slots, OR document the sparse-slot contract and have getTeamAsList() filter nulls before exposing.
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

    // TODO [🟡 IMPORTANT | review 2026-04-20]: add()/remove() fail silently with only a log warning. Why: callers cannot detect failure — bot layer then reports "added!" to Discord even when nothing happened. Fix: return boolean (true=success, false=no-op) OR throw IllegalStateException with context.
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
