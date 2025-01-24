package cool.muyucloud.croparia.kubejs;

import cool.muyucloud.croparia.CropariaIf;
import cool.muyucloud.croparia.generator.DataGenerator;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.function.Consumer;

@SuppressWarnings("unused")
public class DataGeneratorCreator {
    private static final Set<DataGenerator> GENERATOR_CACHE = new HashSet<>();

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