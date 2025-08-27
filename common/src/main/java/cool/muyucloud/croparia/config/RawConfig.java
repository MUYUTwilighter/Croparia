package cool.muyucloud.croparia.config;

import java.util.List;

public record RawConfig(String cropPath, String packPath, String dumpPath, Boolean override, Boolean infusor,
                        Boolean ritual, Integer autoReload, Integer soakAttempts, Integer fruitUse,
                        List<String> blacklist) {
}
