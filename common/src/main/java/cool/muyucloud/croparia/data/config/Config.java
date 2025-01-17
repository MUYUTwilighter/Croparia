package cool.muyucloud.croparia.data.config;

import com.google.gson.Gson;
import cool.muyucloud.croparia.data.crop.Crop;
import dev.architectury.platform.Platform;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.FileWriter;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
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
    @NotNull
    private Boolean infusor;
    @NotNull
    private Boolean ritual;
    @NotNull
    private Boolean cauldron;
    @NotNull
    private Boolean compatGen;
    @NotNull
    private List<String> blacklist;
    /**
     * Default config
     */
    public Config() {
        this.cropPath = Platform.getGameFolder().resolve("crops");
        this.packPath = Platform.getGameFolder().resolve("config/croparia");
        this.override = true;
        this.fruitUse = true;
        this.infusor = true;
        this.ritual = true;
        this.cauldron = true;
        this.compatGen = true;
        this.blacklist = new ArrayList<>();
    }

    /**
     * Deserialize config
     */
    public Config(RawConfig raw) {
        this.cropPath = parsePath(raw.cropPath()).orElse(Platform.getGameFolder().resolve("crops"));
        this.packPath = parsePath(raw.packPath()).orElse(Platform.getGameFolder().resolve("config/croparia"));
        this.override = raw.override() != null ? raw.override() : true;
        this.fruitUse = raw.fruitUse() != null ? raw.fruitUse() : true;
        this.infusor = raw.infusor() != null ? raw.infusor() : true;
        this.ritual = raw.ritual() != null ? raw.ritual() : true;
        this.cauldron = raw.cauldron() != null ? raw.cauldron() : true;
        this.compatGen = raw.compatGen() != null ? raw.compatGen() : true;
        this.blacklist = raw.blacklist() != null ? raw.blacklist() : new ArrayList<>();
    }

    public RawConfig toRaw() {
        return new RawConfig(resolvePath(cropPath), resolvePath(packPath), override, fruitUse, infusor, ritual, cauldron, compatGen, this.blacklist);
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

    public @NotNull Boolean getInfusor() {
        return infusor;
    }

    public void setInfusor(@NotNull Boolean infusor) {
        this.infusor = infusor;
    }

    public @NotNull Boolean getRitual() {
        return ritual;
    }

    public void setRitual(@NotNull Boolean ritual) {
        this.ritual = ritual;
    }

    public @NotNull Boolean getCauldron() {
        return cauldron;
    }

    public void setCauldron(@NotNull Boolean cauldron) {
        this.cauldron = cauldron;
    }

    public @NotNull Boolean getCompatGen() {
        return compatGen;
    }

    public void setCompatGen(@NotNull Boolean compatGen) {
        this.compatGen = compatGen;
    }

    public boolean inBlacklist(@NotNull Crop crop) {
        return blacklist.contains(crop.getName());
    }

    public boolean inBlacklist(@NotNull String cropName) {
        return blacklist.contains(cropName);
    }
}
