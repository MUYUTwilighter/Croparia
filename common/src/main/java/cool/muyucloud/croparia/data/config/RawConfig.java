package cool.muyucloud.croparia.data.config;

import java.util.List;

public record RawConfig(String cropPath, String packPath, String dumpPath, Boolean override, Boolean fruitUse,
                        Boolean infusor, Boolean ritual, List<String> blacklist) {
}
