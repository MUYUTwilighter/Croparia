package cool.muyucloud.croparia.data.config;

import cool.muyucloud.croparia.data.crop.Crop;
import dev.architectury.platform.Platform;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

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

    @NotNull
    private Path cropPath;
    @NotNull
    private Path packPath;
    @NotNull
    private Path dumpPath;
    @NotNull
    private Boolean override;
    @NotNull
    private Boolean fruitUse;
    @NotNull
    private Boolean infusor;
    @NotNull
    private Boolean ritual;
    @NotNull
    private List<String> blacklist;

    /**
     * Default config
     */
    public Config() {
        this.cropPath = Platform.getGameFolder().resolve("crops");
        this.packPath = Platform.getGameFolder().resolve("config/croparia");
        this.dumpPath = Platform.getGameFolder().resolve("croparia");
        this.override = true;
        this.fruitUse = true;
        this.infusor = true;
        this.ritual = true;
        this.blacklist = new ArrayList<>();
    }

    /**
     * Deserialize config
     */
    public Config(RawConfig raw) {
        this.cropPath = parsePath(raw.cropPath).orElse(Platform.getGameFolder().resolve("crops"));
        this.packPath = parsePath(raw.packPath).orElse(Platform.getGameFolder().resolve("config/croparia"));
        this.dumpPath = Platform.getGameFolder().resolve("croparia");
        this.override = raw.override != null ? raw.override : true;
        this.fruitUse = raw.fruitUse != null ? raw.fruitUse : true;
        this.infusor = raw.infusor != null ? raw.infusor : true;
        this.ritual = raw.ritual != null ? raw.ritual : true;
        this.blacklist = raw.blacklist != null ? raw.blacklist : new ArrayList<>();
    }

    public RawConfig toRaw() {
        return new RawConfig(resolvePath(cropPath), resolvePath(packPath), resolvePath(dumpPath), override, fruitUse, infusor, ritual, blacklist);
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

    public @NotNull Path getDumpPath() {
        return dumpPath;
    }

    public void setDumpPath(@NotNull Path dumpPath) {
        this.dumpPath = dumpPath;
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
