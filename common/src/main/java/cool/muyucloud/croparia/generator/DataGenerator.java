package cool.muyucloud.croparia.generator;

import cool.muyucloud.croparia.CropariaIf;
import cool.muyucloud.croparia.data.PlaceHolder;
import cool.muyucloud.croparia.data.crop.Crop;
import dev.architectury.platform.Platform;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public record DataGenerator(boolean enabled, @NotNull String path, @Nullable String dependency, @NotNull String template) {
    public void generate(@NotNull Crop crop, @NotNull Path root) {
        if (!this.enabled()) {
            return;
        }
        if (dependency != null && !Platform.isModLoaded(dependency)) {
            return;
        }
        Path path = root.resolve(replace(this.path(), crop));
        File parent = path.getParent().toFile();
        if (!parent.isDirectory()) {
            parent.mkdirs();
        }
        String replaced = replace(this.template(), crop);
        try (FileWriter writer = new FileWriter(path.toFile())) {
            writer.write(replaced);
        } catch (Throwable e) {
            CropariaIf.LOGGER.error("Failed to generate data for crop \"%s\", template path: \"%s\"".formatted(crop.getName(), this.path()), e);
        }
    }

    public static @NotNull Optional<DataGenerator> read(@NotNull Path file) {
        try {
            return Optional.of(read(Files.readString(file)));
        } catch (Throwable e) {
            CropariaIf.LOGGER.error("Invalid data generator file \"%s\"".formatted(file), e);
        }
        return Optional.empty();
    }

    /**
     * Read data generator definition from string.<br/>
     * <p>
     * Format:<br/>
     * <pre>
     * {@code
     * @enabled=true
     * @path=data/croparia/recipes/crafting/material/{name}.json
     * {
     *     "type": "minecraft:crafting_shapeless",
     *     "ingredients": [
     *         {
     *             "item": "{fruit}"
     *         }
     *     ],
     *     "result": {
     *         "id": "{result}",
     *         "count": 2
     *     }
     * }
     * }
     * </pre>
     */
    public static @NotNull DataGenerator read(@NotNull String content) throws RuntimeException {
        String[] lines = content.split("\n");
        StringBuilder builder = new StringBuilder();
        Map<String, String> meta = readMeta(content);
        boolean enabled = Boolean.parseBoolean(meta.getOrDefault("enabled", "true"));
        String path = meta.getOrDefault("path", "");
        String dependency = meta.getOrDefault("dependency", null);
        // template
        for (int i = meta.size(); i < lines.length; i++) {
            builder.append(lines[i]).append("\n");
        }
        assert builder.isEmpty() : "Empty template content";
        String template = builder.toString();
        return new DataGenerator(enabled, path, dependency, template);
    }

    public static Map<String, String> readMeta(String content) {
        Map<String, String> map = new HashMap<>();
        for (String line : content.split("\n")) {
            if (line.startsWith("@")) {
                String[] split = line.split("=");
                if (split.length == 2) {
                    map.put(split[0].substring(1), split[1].replace("\r", "").replace("\n", ""));
                }
            } else {
                break;
            }
        }
        return map;
    }

    @Override
    public @NotNull String toString() {
        return "@enabled=" + this.enabled() + "\n" + "@path=" + this.path() + "\n" + this.template();
    }

    public @NotNull String replace(@NotNull String template, @NotNull Crop crop) {
        for (Map.Entry<Pattern, PlaceHolder> entry : crop.placeholders().entrySet()) {
            Pattern pattern = entry.getKey();
            Matcher matcher = pattern.matcher(template);
            while (matcher.find()) {
                String matched = matcher.group();
                template = template.replace(matched, entry.getValue().process(matched));
            }
        }
        return template;
    }

    @Override
    public int hashCode() {
        return this.path().hashCode();
    }
}
