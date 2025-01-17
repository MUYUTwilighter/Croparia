package cool.muyucloud.croparia.data.config;

import java.util.List;

public record RawConfig(String cropPath, String packPath, Boolean override, Boolean fruitUse, Boolean infusor,
                        Boolean ritual, Boolean cauldron, Boolean compatGen, List<String> blacklist) {
}
