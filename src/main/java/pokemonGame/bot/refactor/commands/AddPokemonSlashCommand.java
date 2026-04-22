package pokemonGame.bot.refactor.commands;

import pokemonGame.bot.refactor.SlashCommandHandler;
import pokemonGame.bot.refactor.SlashCommandSupport;
import pokemonGame.model.Trainer;
import pokemonGame.model.Team;
import pokemonGame.model.Pokemon;
import pokemonGame.service.TrainerService;
import pokemonGame.service.TeamService;
import pokemonGame.service.PokemonService;
import pokemonGame.species.PokeSpecies;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pokemonGame.bot.refactor.SlashCommandContext;

public final class AddPokemonSlashCommand extends SlashCommandSupport implements SlashCommandHandler{
    
    private static final Logger LOGGER = LoggerFactory.getLogger(AddPokemonSlashCommand.class);
    
    private final TeamService teamService;
    private final TrainerService trainerService;
    private final PokemonService pokemonService;

    public AddPokemonSlashCommand(TeamService teamService, TrainerService trainerService, PokemonService pokemonService) {
        this.teamService = teamService;
        this.trainerService = trainerService;
        this.pokemonService = pokemonService;
    }

    @Override
    public String commandName() {
        return "addpokemon";
    }

    @Override
    public void handle(SlashCommandContext context) {
        context.event().deferReply().queue();

        Optional<Trainer> trainer = requireTrainer(context, trainerService, "No existing trainer found. Create one with /createtrainer!");
        if (trainer.isEmpty()) {
            return;
        }

        Optional<Team> team = requireTeam(context, teamService, trainer.get(), "teamname", "No team information entered.","No existing team found. Create one with /createteam!");
        if (team.isEmpty()) {
            return;
        }

        Optional<String> pokemonSpecies = requireStringOption(
            context,
            "species",
            "You must provide a Pokémon species");
        if (pokemonSpecies.isEmpty()) {
            return;
        }

        PokeSpecies species = PokeSpecies.getSpeciesByString(pokemonSpecies.get());
        if (species == null) {
            replyEphemeral(context, "Sorry, that Pokemon hasn't been discovered yet!");
            return;
        }


        String nickname = Optional.ofNullable(context.event().getOption("nickname"))
            .map(option -> option.getAsString())
            .filter(value -> !value.isBlank())
            .orElse(species.getDisplayName());


        LOGGER.info(
            "Recieved slash command; '{}' with content: '{} the {}'' from user: {} (ID: {})",
            context.commandName(),
            nickname,
            species.getDisplayName(),
            context.user().getName(),
            context.user().getId()
        );

        Pokemon createAttempt = pokemonService.createPokemon(
            species,
            nickname,
            trainer.get()
        );

        if (createAttempt == null) {
            replyEphemeral(context, "Failed to create Pokémon. Please try again.");
            return;
        }


        int slotIndex = teamService.addPokemonToTeam(team.get(), createAttempt);
        if (slotIndex == -1) {
            replyEphemeral(context, "Sorry, there was an error adding that Pokémon to your team!");
            return;
        }
        if (slotIndex == -3) {
            replyEphemeral(context, "Your team is full! Cannot add more Pokémon.");
            return;
        }
        reply(context, "Successfully added " + createAttempt.getNickname()
            + " to your team in slot " + (slotIndex + 1) + "!");
        return;
    }

}

