package pokemonGame.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pokemonGame.service.TeamService;

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
    private final Pokemon[] teamSlots = new Pokemon[MAX_TEAM_SIZE];
    
    public static final int MAX_TEAM_SIZE = 6;

    public Team(String teamName) {
        this.teamName = teamName;
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
        List<Pokemon> members = new ArrayList<>(MAX_TEAM_SIZE);
        for (Pokemon member : teamSlots) {
            if (member != null) {
                members.add(member);
            }
        }
        return Collections.unmodifiableList(members);
    }

    public int getTeamSize() {
        int size = 0;
        for (Pokemon member : teamSlots) {
            if (member != null) {
                size++;
            }
        }
        return size;
    }

    public Pokemon getTeamSlot(int teamSlotIndex) {
        if (teamSlotIndex < 0 || teamSlotIndex >= MAX_TEAM_SIZE) {
            LOGGER.warn("Invalid team slot index: {}. Valid range is 0-{}.", teamSlotIndex, MAX_TEAM_SIZE - 1);
            return null;
        }
        return teamSlots[teamSlotIndex];
    }

    
    public void setTeamSlot(int teamSlotIndex, Pokemon pokemon) {
        if (teamSlotIndex < 0 || teamSlotIndex >= MAX_TEAM_SIZE) {
            LOGGER.warn("Invalid team slot index: {}. Valid range is 0-{}.", teamSlotIndex, MAX_TEAM_SIZE - 1);
            return;
        }

        // Fixed-size array already has every slot allocated; direct assignment is enough.
        teamSlots[teamSlotIndex] = pokemon;
        if (pokemon != null) {
            pokemon.setCurrentTeamSlotIndex(teamSlotIndex);
        }
    }   

    public void add(Pokemon pokemon) {
        if (getTeamSize() < MAX_TEAM_SIZE) {
            setTeamSlot(getTeamSize(), pokemon);
        } else {
            LOGGER.warn("Cannot add Pokémon to team. Team is full.");
        }
    }

    public void remove(Pokemon pokemon) {
        for (int i = 0; i < MAX_TEAM_SIZE; i++) {
            if (teamSlots[i] == pokemon) {
                teamSlots[i] = null;
                pokemon.setCurrentTeamSlotIndex(-1);
                return;
            }
        }
        LOGGER.warn("Cannot remove Pokémon from team. Pokémon not found in team.");
    }

    public boolean isFull() {
        return getTeamSize() >= MAX_TEAM_SIZE;
    }
}
