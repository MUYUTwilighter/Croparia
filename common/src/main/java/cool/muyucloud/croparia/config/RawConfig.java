package cool.muyucloud.croparia.config;

import java.util.List;

public record RawConfig(String filePath, Boolean override, Boolean infusor, Boolean ritual,
                        Integer autoReload, Integer soakAttempts, Integer fruitUse,
                        List<String> blacklist) {
}
