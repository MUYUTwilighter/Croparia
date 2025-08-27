package cool.muyucloud.croparia.config;

import com.google.gson.Gson;
import com.google.gson.stream.JsonWriter;
import cool.muyucloud.croparia.CropariaIf;
import dev.architectury.platform.Platform;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.nio.file.Path;

public class ConfigFileHandler {
    public static final Gson GSON = new Gson();
    public static final Path CONFIG_PATH = Platform.getGameFolder().resolve("config/croparia.json");

    public static void save(Config config) {
        CropariaIf.LOGGER.info("Saving config");
        File parent = CONFIG_PATH.getParent().toFile();
        if (!parent.exists() && !parent.mkdirs()) {
            throw new IllegalStateException("Failed to create config directory");
        }
        try (JsonWriter writer = new JsonWriter(new FileWriter(CONFIG_PATH.toFile()))) {
            writer.setIndent("  ");
            GSON.toJson(config.toRaw(), RawConfig.class, writer);
        } catch (Throwable e) {
            CropariaIf.LOGGER.error("Failed to save config", e);
        }
    }

    public static Config load() {
        Config config;
        try (FileReader reader = new FileReader(CONFIG_PATH.toFile())) {
            config = new Config(GSON.fromJson(reader, RawConfig.class));
        } catch (Exception e) {
            CropariaIf.LOGGER.warn("Config file not found or could not be read, creating a new one");
            config = new Config();
        }
        save(config);
        return config;
    }

    public static void reload(Config config) {
        CropariaIf.LOGGER.info("Loading config");
        Config newConfig = load();
        config.setFilePath(newConfig.getFilePath());
        config.setOverride(newConfig.getOverride());
        config.setFruitUse(newConfig.getFruitUse());
        config.setInfusor(newConfig.getInfusor());
        config.setRitual(newConfig.getRitual());
        config.setBlackList(newConfig.getBlacklist());
    }
}
