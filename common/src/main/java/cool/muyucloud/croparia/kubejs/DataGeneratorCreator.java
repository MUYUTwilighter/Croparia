package cool.muyucloud.croparia.kubejs;

import cool.muyucloud.croparia.CropariaIf;
import cool.muyucloud.croparia.generator.DataGenerator;
import cool.muyucloud.croparia.util.pack.DataPackHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;

@SuppressWarnings("unused")
public class DataGeneratorCreator {
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
        DataPackHandler.INSTANCE.addGenerator(new DataGenerator(enabled, path, dependency, crops, template));
    }
}