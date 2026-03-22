package pokemonGame;
import java.util.ArrayList;



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
