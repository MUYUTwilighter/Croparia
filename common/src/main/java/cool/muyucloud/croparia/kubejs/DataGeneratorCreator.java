package cool.muyucloud.croparia.kubejs;

import cool.muyucloud.croparia.CropariaIf;
import cool.muyucloud.croparia.generator.DataGenerator;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;

@SuppressWarnings("unused")
public class DataGeneratorCreator {
    private static final List<DataGenerator> GENERATOR_CACHE = new LinkedList<>();

    /**
     * Creates a new data generator.
     *
     * @param enabled    Whether the generator is enabled. If null, it will be treated as true.
     * @param path       The path to the generator.
     * @param dependency The dependency of the generator. If null, it will default to "minecraft".
     * @param crops      The list of crops for the generator.
     * @param template   The template for the generator.
     */
    public static void create(
        @Nullable Boolean enabled, @NotNull String path,
        @Nullable String dependency, @NotNull Collection<String> crops, @NotNull String template
    ) {
        enabled = enabled == null || enabled;
        dependency = dependency == null ? "minecraft" : dependency;
        template = template.trim();
        if (template.isEmpty()) {
            CropariaIf.LOGGER.error("Empty template, generator of path {} is skipped", path);
            return;
        }
        DataGenerator generator = new DataGenerator(enabled, path, dependency, crops, template);
        GENERATOR_CACHE.add(generator);
    }

    public static void flushInto(Consumer<DataGenerator> consumer) {
        GENERATOR_CACHE.forEach(consumer);
        GENERATOR_CACHE.clear();
    }
}