package pokemonGame;
import java.util.ArrayList;

import pokemonGame.db.TeamCRUD;
import pokemonGame.db.PokemonCRUD;



// Create a Trainer class to represent a Pokémon trainer.  This can hold information 
// about the trainer's name, their team of Pokémon, and any other relevant details.
public class Trainer {
    private String name;
    private ArrayList<Pokemon> team;
    private int id; // This can be used to link the trainer to the database record

    public Trainer(String name) {
        this.name = name;
        this.team = new ArrayList<Pokemon>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public ArrayList<Pokemon> getTeam() {
        
        return team;
    }

    public Pokemon getPokemonByInstanceId(int pokemonId) {
        PokemonCRUD pokemonCRUD = new PokemonCRUD();
        return pokemonCRUD.getSpecificDBPokemonForTrainer(this, pokemonId); // Fetch the specific Pokémon from the database
    }

    public void setName(String name) {
        this.name = name;
    }

    // Add single pokemon to team
    public void addPokemonToTeam(Pokemon pokemon) {

        TeamCRUD teamCRUD = new TeamCRUD();

        teamCRUD.addPokemonToDBTeam(this.id, pokemon.getId()); // Add the Pokémon to the database team table
        if (team.size() < 6) {
            team.add(pokemon);

        } else {
            System.out.println("Team is full! Cannot add more Pokémon.");
        }
    }

}
