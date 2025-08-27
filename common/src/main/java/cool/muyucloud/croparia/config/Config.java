package cool.muyucloud.croparia.config;

import dev.architectury.platform.Platform;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

@SuppressWarnings("unused")
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
    private Path filePath;
    @NotNull
    private Boolean override;
    @NotNull
    private Boolean infusor;
    @NotNull
    private Boolean ritual;
    @NotNull
    private Integer fruitUse;
    @NotNull
    private Integer autoReload;
    @NotNull
    private Integer soakAttempts;
    @NotNull
    private final List<ResourceLocation> cropBlackList;
    private final List<String> modBlackList;

    /**
     * Default config
     */
    public Config() {
        this.filePath = Platform.getGameFolder().resolve("croparia");
        this.autoReload = 20;
        this.override = true;
        this.fruitUse = 2;
        this.infusor = true;
        this.ritual = true;
        this.soakAttempts = 1;
        this.cropBlackList = new ArrayList<>();
        this.modBlackList = new ArrayList<>();
    }

    /**
     * Deserialize config
     */
    public Config(RawConfig raw) {
        this.filePath = parsePath(raw.filePath()).orElse(Platform.getGameFolder().resolve("croparia"));
        this.autoReload = raw.autoReload() != null ? raw.autoReload() : 20;
        this.override = raw.override() != null ? raw.override() : true;
        this.fruitUse = raw.fruitUse() != null ? raw.fruitUse() : 2;
        this.infusor = raw.infusor() != null ? raw.infusor() : true;
        this.ritual = raw.ritual() != null ? raw.ritual() : true;
        this.soakAttempts = raw.soakAttempts() != null ? raw.soakAttempts() : 1;
        this.cropBlackList = new ArrayList<>();
        this.modBlackList = new ArrayList<>();
        this.setBlackList(raw.blacklist());
    }

    public RawConfig toRaw() {
        return new RawConfig(resolvePath(filePath), override, infusor, ritual, autoReload, soakAttempts, fruitUse, this.getBlacklist());
    }

    public @NotNull Path getFilePath() {
        return filePath;
    }

    public void setFilePath(@NotNull Path filePath) {
        this.filePath = filePath;
    }

    public @NotNull Integer getAutoReload() {
        return autoReload;
    }

    public void setAutoReload(@NotNull Integer autoReload) {
        this.autoReload = autoReload;
    }

    public @NotNull Boolean getOverride() {
        return override;
    }

    public void setOverride(@NotNull Boolean override) {
        this.override = override;
    }

    public @NotNull Integer getFruitUse() {
        return fruitUse;
    }

    public void setFruitUse(@NotNull Integer fruitUse) {
        this.fruitUse = fruitUse;
    }

    public @NotNull Boolean getInfusor() {
        return infusor;
    }

    public void setInfusor(@NotNull Boolean infusor) {
        this.infusor = infusor;
    }

    public @NotNull Integer getSoakAttempts() {
        return soakAttempts;
    }

    public void setSoakAttempts(@NotNull Integer soakAttempts) {
        this.soakAttempts = soakAttempts;
    }

    public @NotNull Boolean getRitual() {
        return ritual;
    }

    public void setRitual(@NotNull Boolean ritual) {
        this.ritual = ritual;
    }

    public @NotNull List<ResourceLocation> getCropBlackList() {
        return cropBlackList;
    }

    public @NotNull List<String> getModBlackList() {
        return modBlackList;
    }

    public List<String> getBlacklist() {
        List<String> blacklist = new ArrayList<>(this.getCropBlackList().size() + this.getModBlackList().size());
        for (ResourceLocation id : this.getCropBlackList()) {
            blacklist.add(id.toString());
        }
        for (String token : this.getModBlackList()) {
            blacklist.add("@" + token);
        }
        return blacklist;
    }

    public void setBlackList(@NotNull List<String> blacklist) {
        this.getCropBlackList().clear();
        this.getModBlackList().clear();
        for (String token : blacklist) {
            if (token.startsWith("@")) {
                this.getModBlackList().add(token.substring(1));
            } else {
                ResourceLocation id = ResourceLocation.tryParse(token);
                if (id != null) this.getCropBlackList().add(id);
            }
        }
    }

    public boolean isCropValid(ResourceLocation id) {
        for (ResourceLocation e : this.getCropBlackList()) {
            if (e.equals(id)) {
                return false;
            }
        }
        return true;
    }

    public boolean isModValid(String mod) {
        for (String token : this.getModBlackList()) {
            if (Pattern.compile(token).matcher(mod).matches()) {
                return false;
            }
        }
        return true;
    }
}
