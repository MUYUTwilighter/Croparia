package cool.muyucloud.croparia.config;

import com.google.gson.Gson;
import com.google.gson.stream.JsonWriter;
import cool.muyucloud.croparia.CropariaIf;
import cool.muyucloud.croparia.api.crop.Crop;
import cool.muyucloud.croparia.api.generator.pack.PackHandler;
import dev.architectury.platform.Platform;
import net.minecraft.FileUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.FileWriter;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

public class Config {
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

    private Path cropPath;
    private Path packPath;
    private Path dumpPath;
    private Boolean autoReload;
    private Boolean override;
    private Boolean fruitUse;
    private Boolean infusor;
    private Boolean ritual;
    private List<String> blacklist;

    /**
     * Default config
     */
    public Config() {
        setCropPath(Platform.getGameFolder().resolve("crops"));
        setPackPath(Platform.getGameFolder().resolve("config/croparia"));
        setDumpPath(Platform.getGameFolder().resolve("croparia"));
        setAutoReload(true);
        setOverride(true);
        setFruitUse(true);
        setInfusor(true);
        setRitual(true);
        setBlacklist(new ArrayList<>());
    }

    /**
     * Deserialize config
     */
    public Config(RawConfig raw) {
        this.setCropPath(parsePath(raw.cropPath()).orElse(Platform.getGameFolder().resolve("crops")));
        this.setPackPath(parsePath(raw.packPath()).orElse(Platform.getGameFolder().resolve("config/croparia")));
        this.setDumpPath(Platform.getGameFolder().resolve("croparia"));
        this.setAutoReload(raw.autoReload() != null ? raw.autoReload() : true);
        this.setOverride(raw.override() != null ? raw.override() : true);
        this.setFruitUse(raw.fruitUse() != null ? raw.fruitUse() : true);
        this.setInfusor(raw.infusor() != null ? raw.infusor() : true);
        this.setRitual(raw.ritual() != null ? raw.ritual() : true);
        this.setBlacklist(raw.blacklist() != null ? raw.blacklist() : new ArrayList<>());
    }

    public RawConfig toRaw() {
        return new RawConfig(resolvePath(cropPath), resolvePath(packPath), resolvePath(dumpPath), autoReload, override, fruitUse, infusor, ritual, blacklist);
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
        try {
            FileUtil.createDirectoriesSafe(packPath);
            JsonWriter writer = new JsonWriter(new FileWriter(packPath.resolve("pack.mcmeta").toFile()));
            new Gson().toJson(PackHandler.META, writer);
            writer.close();
            this.packPath = packPath;
        } catch (Throwable t) {
            CropariaIf.LOGGER.error("Failed to set pack path", t);
        }
    }

    public @NotNull Path getDumpPath() {
        return dumpPath;
    }

    public void setDumpPath(@NotNull Path dumpPath) {
        this.dumpPath = dumpPath;
    }

    public @NotNull Boolean getAutoReload() {
        return autoReload;
    }

    public void setAutoReload(@NotNull Boolean autoReload) {
        this.autoReload = autoReload;
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

    public @NotNull List<String> getBlacklist() {
        return blacklist;
    }

    public void setBlacklist(@NotNull List<String> blacklist) {
        this.blacklist = blacklist;
    }

    public boolean inBlacklist(Crop crop) {
        return this.blacklist.contains(crop.getName());
    }

    public boolean inBlacklist(@NotNull String cropName, @NotNull String mod) {
        for (String pattern : blacklist) {
            if (pattern.startsWith("@")) {
                pattern = pattern.substring(1);
                Pattern modPattern = Pattern.compile(pattern);
                if (modPattern.matcher(mod).matches()) {
                    return true;
                }
                continue;
            }
            if (Pattern.compile(pattern).matcher(cropName).matches()) {
                return true;
            }
        }
        return false;
    }
}
