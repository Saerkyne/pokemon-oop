package pokemonGame;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

// @Retention(RetentionPolicy.RUNTIME) tells Java to keep this annotation
// available at runtime, so reflection (and ClassGraph) can read it.
// Without this, annotations are discarded after compilation and invisible
// to your scanning code.
@Retention(RetentionPolicy.RUNTIME)
public @interface SpeciesAliases {
    // "value" is a special name in annotations — it lets you write
    // @SpeciesAliases({"nidoran f", "nidoranf"}) instead of the longer
    // @SpeciesAliases(value = {"nidoran f", "nidoranf"})
    String[] value();
}