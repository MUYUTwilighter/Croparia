package com.defacto34.croparia;

import com.google.gson.Gson;
import com.google.gson.stream.JsonWriter;

import java.io.*;
import java.nio.file.Path;

public class ConfigIoProcessor {
    private static final Gson gson = new Gson();

    public static Config load(Path path) {
        File file = path.toFile();
        Config out = new Config();
        if (isBad(file)) {
            return out;
        }
        try (Reader reader = new FileReader(file)) {
            out = gson.fromJson(reader, Config.class);
        } catch (Exception ignored) {
        }
        out = out == null ? new Config() : out;
        dump(out, path);
        return out;
    }

    public static void dump(Config config, Path path) {
        File file = path.toFile();
        if (isBad(file)) {
            return;
        }
        try (FileWriter fileWriter = new FileWriter(file)) {
            JsonWriter writer = new JsonWriter(fileWriter);
            writer.setIndent("    ");
            gson.toJson(config, Config.class, writer);
        } catch (Exception ignored) {}
    }

    /**
     * Make sure the path is a file and create if the file not exists
     *
     * @return false if file is OK and ready to get processed
     * */
    private static boolean isBad(File file) {
        File parent = file.getParentFile();
        if (!parent.exists()) {
            parent.mkdirs();
        }
        if (!file.exists()) {
            try {
                file.createNewFile();
                return false;
            } catch (IOException ignored) {
                return true;
            }
        }
        return !file.isFile();
    }
}
