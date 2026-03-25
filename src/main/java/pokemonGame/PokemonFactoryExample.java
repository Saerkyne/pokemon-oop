package pokemonGame;

import pokemonGame.mons.*;
import java.util.HashMap;
import java.util.Map;
import java.util.ServiceLoader;
import java.util.function.Function;

/**
 * EDUCATIONAL EXAMPLE — Two alternatives to the 151-case switch statement in
 * {@link Pokemon#createPokemon(String, String, Trainer)}.
 *
 * Both approaches achieve the same goal: given a species name string, produce
 * the correct Pokemon subclass instance — but without a giant switch that must
 * be edited every time a new species is added.
 *
 * This class is NOT wired into the game. It exists purely as a reference for
 * understanding the patterns. You can run its main() method to see both
 * approaches in action.
 */
public class PokemonFactoryExample {

    // =========================================================================
    // APPROACH 1: Map<String, Function<String, Pokemon>> registry
    // =========================================================================
    //
    // Instead of a switch statement that maps species names to constructor calls,
    // we store those mappings in a Map (dictionary). The key is the species name,
    // and the value is a Function that accepts a nickname String and returns a
    // new Pokemon.
    //
    // KEY CONCEPTS:
    //
    //   Function<String, Pokemon>
    //     This is a "functional interface" from java.util.function. It represents
    //     any operation that takes one input (String) and produces one output
    //     (Pokemon). We use it here so the map's values can hold constructor
    //     references — each one knows how to build a specific species.
    //
    //   Bulbasaur::new
    //     This is a "method reference" — shorthand for (name) -> new Bulbasaur(name).
    //     Since every species constructor takes a single String (the nickname),
    //     they all match the Function<String, Pokemon> signature automatically.
    //
    // WHY THIS IS BETTER THAN A SWITCH:
    //   Adding a new species only requires one line: REGISTRY.put("newspecies", NewSpecies::new);
    //   The factory method itself (createWithRegistry) never changes.
    //
    // SELF-REGISTRATION CAVEAT:
    //   In theory, each species could register itself from its own static block:
    //       static { REGISTRY.put("abra", Abra::new); }
    //   However, Java loads classes LAZILY — a class's static block only runs
    //   when that class is first referenced by running code. In a plain Java app
    //   (no framework scanning), if nothing mentions Abra before the factory is
    //   called, Abra.class is never loaded and its static block never runs.
    //   The registry would be empty.
    //
    //   Self-registration works in frameworks like Spring that scan and load
    //   classes automatically at startup. In plain Java, you either need a
    //   central list (the static block below), classpath scanning (ClassGraph),
    //   or ServiceLoader (Approach 2) to discover species.

    /**
     * The registry: a map from lowercase species name to a constructor function.
     * "static final" means this map is created once when the class loads and is
     * shared by all callers — similar to how TypeChart stores its data.
     */
    private static final Map<String, Function<String, Pokemon>> REGISTRY = new HashMap<>();

    /*
     * A "static initializer block" runs once when the JVM first loads this class.
     * We populate the registry here. In a full implementation, all 151 species
     * would be registered. Only a few are shown for brevity.
     */
    static {
        REGISTRY.put("bulbasaur",  Bulbasaur::new);
        REGISTRY.put("charmander", Charmander::new);
        REGISTRY.put("squirtle",   Squirtle::new);
        REGISTRY.put("pikachu",    Pikachu::new);
        // ... all other species would be registered here in the same pattern.
    }

    /**
     * Factory method using the registry approach.
     *
     * Compare this to the current 151-case switch in Pokemon.createPokemon() —
     * this method body never needs to change when species are added; only the
     * static block above does.
     *
     * @param species  the species name (case-insensitive), e.g. "Bulbasaur"
     * @param nickname the nickname for the created Pokemon
     * @return a new Pokemon of the requested species, or null if not found
     */
    public static Pokemon createWithRegistry(String species, String nickname) {
        // Look up the constructor function for this species name.
        // Map.get() returns null if the key isn't present.
        Function<String, Pokemon> constructor = REGISTRY.get(species.toLowerCase());

        if (constructor == null) {
            System.out.println("Unknown species: " + species);
            return null;
        }

        // .apply() calls the function — in this case, it invokes the species
        // constructor with the nickname as its argument.
        // For example, if species is "bulbasaur", this is equivalent to:
        //     new Bulbasaur(nickname)
        return constructor.apply(nickname);
    }


    // =========================================================================
    // APPROACH 2: ServiceLoader (fully decoupled, no central registration)
    // =========================================================================
    //
    // ServiceLoader is a built-in Java mechanism for discovering implementations
    // of an interface at runtime, without the factory knowing about them at
    // compile time. It works by reading a configuration file that lists which
    // classes implement a given interface.
    //
    // HOW IT WORKS (step by step):
    //
    //   1. Define a "provider" interface that each species implements.
    //      The interface declares methods that identify the species and create
    //      instances of it. (See PokemonProvider below.)
    //
    //   2. Each species class implements the provider interface.
    //      For example, Bulbasaur would implement speciesName() to return
    //      "bulbasaur" and create(nickname) to return new Bulbasaur(nickname).
    //
    //   3. Register providers in a configuration file at:
    //        src/main/resources/META-INF/services/pokemonGame.PokemonFactoryExample$PokemonProvider
    //      This plain-text file lists the fully qualified class name of each
    //      provider, one per line. The JVM reads this file to know which classes
    //      to load.
    //
    //   4. ServiceLoader.load() reads that file and instantiates each listed
    //      class. The factory iterates over them to find the right species.
    //
    // WHY THIS IS THE MOST DECOUPLED APPROACH:
    //   The factory class doesn't import or reference any species class.
    //   Adding a new species means: create the class, add one line to the
    //   config file. The factory code and the factory class file are never
    //   touched.
    //
    // TRADEOFF:
    //   More setup (the config file, the provider interface) and harder to
    //   debug if something is misconfigured. For 151 known species that won't
    //   change, the Map registry (Approach 1) is simpler and sufficient.

    /**
     * The "provider" interface that each species would implement.
     *
     * "interface" defines a contract — any class that "implements" this
     * interface promises to provide concrete versions of these two methods.
     * This lets ServiceLoader work with species it has never seen before,
     * because it only needs to know about this interface, not every species.
     */
    public interface PokemonProvider {
        /** Returns the lowercase species name, e.g. "bulbasaur". */
        String speciesName();

        /** Creates a new Pokemon of this species with the given nickname. */
        Pokemon create(String nickname);
    }

    /**
     * Example provider for Bulbasaur. In a real implementation, this would
     * live inside Bulbasaur.java (or as a nested class within it), so each
     * species file is self-contained.
     */
    public static class BulbasaurProvider implements PokemonProvider {
        @Override
        public String speciesName() {
            return "bulbasaur";
        }

        @Override
        public Pokemon create(String nickname) {
            return new Bulbasaur(nickname);
        }
    }

    /**
     * Factory method using ServiceLoader.
     *
     * ServiceLoader.load() finds all classes registered as PokemonProvider
     * implementations (via the META-INF/services config file) and makes
     * them available for iteration. We loop through them looking for a
     * matching species name.
     *
     * @param species  the species name (case-insensitive)
     * @param nickname the nickname for the created Pokemon
     * @return a new Pokemon of the requested species, or null if not found
     */
    public static Pokemon createWithServiceLoader(String species, String nickname) {
        // ServiceLoader.load() returns an iterable of all registered
        // PokemonProvider implementations. Each one was instantiated by
        // calling its no-arg constructor.
        ServiceLoader<PokemonProvider> providers = ServiceLoader.load(PokemonProvider.class);

        for (PokemonProvider provider : providers) {
            if (provider.speciesName().equalsIgnoreCase(species)) {
                return provider.create(nickname);
            }
        }

        System.out.println("Unknown species: " + species);
        return null;
    }


    // =========================================================================
    // Demo — run this main() to see both approaches work
    // =========================================================================

    public static void main(String[] args) {
        // --- Approach 1: Map registry ---
        System.out.println("=== Approach 1: Map<String, Function<String, Pokemon>> ===");
        Pokemon bulba = createWithRegistry("bulbasaur", "Leafy");
        if (bulba != null) {
            System.out.println("Created: " + bulba.getNickname() + " (" + bulba.getSpecies() + ")");
        }

        Pokemon unknown = createWithRegistry("missingno", "Glitch");
        if (unknown != null) {
            System.out.println("Created: " + unknown.getNickname() + " (" + unknown.getSpecies() + ")");
        }
        // Prints "Unknown species: missingno"

        // --- Approach 2: ServiceLoader ---
        // Note: this will only work if you create the config file at
        //   src/main/resources/META-INF/services/pokemonGame.PokemonFactoryExample$PokemonProvider
        // containing the line:
        //   pokemonGame.PokemonFactoryExample$BulbasaurProvider
        System.out.println("\n=== Approach 2: ServiceLoader ===");
        Pokemon bulba2 = createWithServiceLoader("bulbasaur", "Sprout");
        if (bulba2 != null) {
            System.out.println("Created: " + bulba2.getNickname() + " (" + bulba2.getSpecies() + ")");
        } else {
            System.out.println("(ServiceLoader found no providers — config file not set up.)");
        }
    }
}
