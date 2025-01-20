package cool.muyucloud.croparia.data.config;

import java.util.List;

public class RawConfig {
    public String cropPath, packPath, dumpPath;
    public Boolean override, fruitUse, infusor, ritual;
    public List<String> blacklist;

    public RawConfig(String cropPath, String packPath, String dumpPath, Boolean override, Boolean fruitUse, Boolean infusor, Boolean ritual, List<String> blacklist) {
        this.cropPath = cropPath;
        this.packPath = packPath;
        this.dumpPath = dumpPath;
        this.override = override;
        this.fruitUse = fruitUse;
        this.infusor = infusor;
        this.ritual = ritual;
        this.blacklist = blacklist;
    }
}
