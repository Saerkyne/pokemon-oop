package pokemonGame.model;

import pokemonGame.service.TrainerService;

/**
 * Represents a trainer (player) with a name, Discord identity, database ID,
 * and an in-memory team of up to 6 {@link Pokemon}. Maps to the
 * {@code trainers} table. The team loaded in memory is whichever team is
 * currently relevant (e.g. for a battle or {@code /checkteam} display).
 *
 * @see Pokemon
 * @see Team
 * @see TrainerService
 */
public class Trainer {
    private String trainerName;
    private Team team;// In-memory team of up to 6 Pokémon. The first Pokémon in the list is the active Pokémon.
    private int trainerDbId; // this is unused until the database creates it; we have set/get for it
    private long discordId; // this is unused until we add the discord bot; we have set/get for it

    public Trainer(String trainerName) {
        this.trainerName = trainerName;
    }

    public int getTrainerDbId() {
        return trainerDbId;
    }

    public void setTrainerDbId(int trainerDbId) {
        this.trainerDbId = trainerDbId;
    }

    public long getDiscordId() {
        return discordId;
    }

    public void setDiscordId(long discordId) {
        this.discordId = discordId;
    }

    public String getTrainerName() {
        return trainerName;
    }

    public void createTeam(String teamName) {
        this.team = new Team(teamName);
    }

    public void setTrainerName(String trainerName) {
        this.trainerName = trainerName;
    }

    public Team getTeam(String teamName) {
        if (team != null && team.getTeamName().equals(teamName)) {
            return team;
        } else {
            return null; // or throw an exception if you prefer
        }

    }

    public void setTeam(Team team) {
        this.team = team;
    }

}
