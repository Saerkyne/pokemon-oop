package pokemonGame;
import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import io.github.classgraph.ClassGraph;
import io.github.classgraph.ClassInfo;
import io.github.classgraph.ScanResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.Collections;
import java.util.Set;

public class PokemonFactory {

    private static final Logger LOGGER = LoggerFactory.getLogger(PokemonFactory.class);
    private static final Map<String, Function<String, Pokemon>> REGISTRY = new HashMap<>();

    static {
        try (ScanResult scan = new ClassGraph()
                .acceptPackages("pokemonGame.mons")
                .enableClassInfo()
                .scan()) {

            for (ClassInfo cls : scan.getSubclasses(Pokemon.class)) {
                try {
                    Class<? extends Pokemon> species = cls.loadClass().asSubclass(Pokemon.class);
                    Constructor<? extends Pokemon> ctor = species.getConstructor(String.class);
                    String key = species.getSimpleName().toLowerCase();

                    REGISTRY.put(key, name -> {
                        try { return ctor.newInstance(name); }
                        catch (Exception e) { throw new RuntimeException(e); }
                    });

                    SpeciesAliases aliases = species.getAnnotation(SpeciesAliases.class);
                    if (aliases != null) {
                        for (String alias : aliases.value()) {
                            REGISTRY.put(alias.toLowerCase(), name -> {
                                try { return ctor.newInstance(name); }
                                catch (Exception e) { throw new RuntimeException(e); }
                            });
                        }
                    }
                } catch (NoSuchMethodException e) {
                    // This species lacks a (String) constructor — skip it, don't abort the whole scan
                    LOGGER.warn("Skipping {} — no (String) constructor found.", cls.getSimpleName(), e);
                } catch (Exception e) {
                    LOGGER.warn("Skipping {} — unexpected error during registration.", cls.getSimpleName(), e);
                }
            }

            LOGGER.info("PokemonFactory registered {} species.", REGISTRY.size());
        } catch (Exception e) {
            LOGGER.error("ClassGraph scan failed — no species registered.", e);
        }
    }

    // ============================
    // ===   REGISTRY FACTORY   ===
    // ============================

    

    public static Pokemon createPokemonFromRegistry(String species, String nickname) {
        Function<String, Pokemon> constructor = REGISTRY.get(species.toLowerCase());
        
        if (constructor == null) {
            LOGGER.warn("Species not recognized in registry. Please choose a valid Pokémon species.");
            return null;
        }
        return constructor.apply(nickname);

    }

    // Getter for all registered species names (for autocomplete)
    public static Set<String> getSpeciesNames() {
        return Collections.unmodifiableSet(REGISTRY.keySet());
    }
}
