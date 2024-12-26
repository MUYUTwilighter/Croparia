package cool.muyucloud.data.config;

import com.google.gson.Gson;
import dev.architectury.platform.Platform;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.FileWriter;
import java.nio.file.Path;
import java.util.Optional;

public class Config {
    public static final Gson GSON = new Gson();
    public static final Path CONFIG_PATH = Platform.getGameFolder().resolve("config/croparia.json");

    public static @NotNull Optional<Path> parsePath(@Nullable String path) {
        if (path == null) {
            return Optional.empty();
        }
        Path p = Path.of(path);
        if (p.isAbsolute()) {
            return Optional.of(p);
        } else {
            return Optional.of(Platform.getGameFolder().resolve(path));
        }
    }

    public static @NotNull String resolvePath(@NotNull Path path) {
        Path normalizedPath = path.normalize();
        Path normalizedBasePath = Platform.getGameFolder().normalize();

        if (normalizedPath.startsWith(normalizedBasePath)) {
            return normalizedBasePath.relativize(normalizedPath).toString();
        } else {
            return normalizedPath.toAbsolutePath().toString();
        }
    }

    @NotNull
    private Path cropPath;
    @NotNull
    private Path packPath;
    @NotNull
    private Boolean override;
    @NotNull
    private Boolean fruitUse;

    /**
     * Default config
     * */
    public Config() {
        this.cropPath = Platform.getGameFolder().resolve("crops");
        this.packPath = Platform.getGameFolder().resolve("config/croparia");
        this.override = true;
        this.fruitUse = true;
    }

    public Config(RawConfig raw) {
        this.cropPath = parsePath(raw.cropPath()).orElse(Platform.getGameFolder().resolve("crops"));
        this.packPath = parsePath(raw.packPath()).orElse(Platform.getGameFolder().resolve("config/croparia"));
        this.override = raw.override() != null ? raw.override() : true;
        this.fruitUse = raw.fruitUse() != null ? raw.fruitUse() : true;
    }

    public RawConfig toRaw() {
        return new RawConfig(resolvePath(cropPath), resolvePath(packPath), override, fruitUse);
    }

    public void save() {
        try (FileWriter writer = new FileWriter(CONFIG_PATH.toFile())) {
            GSON.toJson(this.toRaw(), writer);
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    public @NotNull Path getCropPath() {
        return cropPath;
    }

    public void setCropPath(@NotNull Path cropPath) {
        this.cropPath = cropPath;
    }

    public @NotNull Path getPackPath() {
        return packPath;
    }

    public void setPackPath(@NotNull Path packPath) {
        this.packPath = packPath;
    }

    public @NotNull Boolean getOverride() {
        return override;
    }

    public void setOverride(@NotNull Boolean override) {
        this.override = override;
    }

    public @NotNull Boolean getFruitUse() {
        return fruitUse;
    }

    public void setFruitUse(@NotNull Boolean fruitUse) {
        this.fruitUse = fruitUse;
    }
}
