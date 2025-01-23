package cool.muyucloud.croparia.kubejs;

import cool.muyucloud.croparia.CropariaIf;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.FileWriter;
import java.nio.file.Path;
import java.util.Collection;

public class DataGeneratorCreator {
    public static void create(
        @NotNull String filename, @Nullable Boolean override, @Nullable Boolean enabled, @NotNull String path,
        @Nullable String dependency, @NotNull Collection<String> crops, @NotNull String template
    ) {
        Path parent = CropariaIf.CONFIG.getPackPath().resolve("generators").resolve(filename);
        if (!parent.toFile().isDirectory() && !parent.toFile().mkdirs()) {
            CropariaIf.LOGGER.error("Failed to create directory {}, generator {} is skipped", parent, filename);
        }
        Path filePath = parent.resolve(filename);
        override = override == null || override;
        if (filePath.toFile().isFile() && !override) {
            CropariaIf.LOGGER.debug("Generator \"{}\" already exists, skipped due to override=false", filename);
            return;
        }
        StringBuilder builder = new StringBuilder();
        if (enabled != null) {
            builder.append(createMeta("enabled", enabled.toString())).append("\n");
        }
        builder.append(createMeta("path", path)).append("\n");
        if (dependency != null) {
            builder.append(createMeta("dependency", dependency)).append("\n");
        }
        if (!crops.isEmpty()) {
            builder.append(createMeta(
                "crops",
                crops.stream().reduce((accumulate, s) -> accumulate + ", " + s).orElseThrow(
                    () -> new IllegalArgumentException("Illegal format of crops %s".formatted(crops))
                )
            )).append("\n");
        }
        template = template.trim();
        if (template.isEmpty()) {
            CropariaIf.LOGGER.error("Empty template, generator {} is skipped", filename);
            return;
        }
        builder.append(template);
        try (FileWriter writer = new FileWriter(parent.resolve(filename).toFile())) {
            writer.write(builder.toString());
        } catch (Throwable t) {
            CropariaIf.LOGGER.error("Failed to create generator \"%s\"".formatted(filename), t);
        }
    }

    protected static String createMeta(String meta, String value) {
        value = value.replace("\r", "").replace("\n", "").trim();
        return "@%s=%s".formatted(meta, value);
    }
}
