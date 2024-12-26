package cool.muyucloud.data.config;

import com.google.gson.Gson;
import cool.muyucloud.CropariaIf;
import dev.architectury.platform.Platform;

import java.io.FileReader;
import java.io.FileWriter;
import java.nio.file.Path;

public class ConfigFileHandler {
    public static final Gson GSON = new Gson();
    public static final Path CONFIG_PATH = Platform.getGameFolder().resolve("config/croparia.json");

    public static void save(Config config) {
        try (FileWriter writer = new FileWriter(CONFIG_PATH.toFile())) {
            GSON.toJson(config.toRaw(), writer);
        } catch (Throwable e) {
            CropariaIf.LOGGER.error("Failed to save config", e);
        }
    }

    public static Config load() {
        try (FileReader reader = new FileReader(CONFIG_PATH.toFile())) {
            return new Config(GSON.fromJson(reader, RawConfig.class));
        } catch (Exception e) {
            CropariaIf.LOGGER.warn("Config file not found or could not be read, creating a new one");
            return new Config();
        }
    }
}
