package cool.muyucloud.croparia.kubejs;

import cool.muyucloud.croparia.CropariaIf;
import cool.muyucloud.croparia.generator.DataGenerator;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;

@SuppressWarnings("unused")
public class DataGeneratorCreator {
    private static final Map<Integer, DataGenerator> GENERATOR_CACHE = new HashMap<>();

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
        GENERATOR_CACHE.put(generator.hashCode(), generator);
    }

    public static void clearCache() {
        GENERATOR_CACHE.clear();
    }

    public static void dumpInto(BiConsumer<Integer, DataGenerator> consumer) {
        GENERATOR_CACHE.forEach(consumer);
    }
}