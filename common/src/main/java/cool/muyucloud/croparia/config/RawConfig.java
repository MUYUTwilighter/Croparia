package cool.muyucloud.croparia.config;

import java.util.List;

public record RawConfig(String cropPath, String packPath, String dumpPath, Boolean autoReload, Boolean override, Boolean fruitUse,
                        Boolean infusor, Boolean ritual, Integer soakAttempts, List<String> blacklist) {
}
