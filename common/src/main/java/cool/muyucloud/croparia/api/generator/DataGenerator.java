package cool.muyucloud.croparia.api.generator;

import cool.muyucloud.croparia.CropariaIf;
import cool.muyucloud.croparia.api.crop.Crop;
import cool.muyucloud.croparia.api.crop.Crops;
import dev.architectury.platform.Platform;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public record DataGenerator(
    boolean enabled, @NotNull String path, @Nullable String dependency, @NotNull Collection<String> crops,
    @NotNull String template
) {
    public void generate(@NotNull Path root) {
        if (!this.enabled() || !Platform.isModLoaded(dependency)) {
            return;
        }
        if (this.crops.isEmpty()) {
            Crops.forEachCrop(crop -> this.generate(crop, root));
        } else {
            crops.forEach(name -> {
                Crop crop = Crops.forName(name);
                if (crop != null) {
                    this.generate(crop, root);
                } else {
                    CropariaIf.LOGGER.error("Crop \"{}\" not found for generator with path \"{}\"", name, this.path());
                }
            });
        }
    }

    private void generate(@NotNull Crop crop, @NotNull Path root) {
        Path path = root.resolve(replace(this.path(), crop));
        File parent = path.getParent().toFile();
        if (!parent.isDirectory() && !parent.mkdirs()) {
            CropariaIf.LOGGER.error("Failed to establish data pack directory, path: \"%s\"".formatted(parent.getAbsolutePath()));
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
        String dependency = meta.getOrDefault("dependency", "minecraft");
        List<String> crops = Arrays.stream(meta.getOrDefault("crops", "").split(",")).filter(crop -> !crop.isEmpty()).map(String::trim).toList();
        // template
        for (int i = meta.size(); i < lines.length; i++) {
            String line = lines[i].replace("\r", "");
            builder.append(line).append("\n");
        }
        if (builder.isEmpty()) throw new RuntimeException("Template is empty");
        String template = builder.toString().trim();
        return new DataGenerator(enabled, path, dependency, crops, template);
    }

    public static Map<String, String> readMeta(String content) {
        Map<String, String> map = new HashMap<>();
        for (String line : content.split("\n")) {
            line = line.trim();
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