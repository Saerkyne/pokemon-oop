package pokemonGame;
import java.util.ArrayList;



// Create a Trainer class to represent a Pokémon trainer.  This can hold information 
// about the trainer's name, their team of Pokémon, and any other relevant details.
public class Trainer {
    private String name;
    private ArrayList<Pokemon> team;

    public Trainer(String name) {
        this.name = name;
        this.team = new ArrayList<Pokemon>();
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
        } else {
            System.out.println("Team is full! Cannot add more Pokémon.");
        }
    }

}
