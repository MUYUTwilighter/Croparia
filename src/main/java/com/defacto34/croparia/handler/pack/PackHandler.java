package com.defacto34.croparia.handler.pack;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonWriter;
import net.fabricmc.loader.api.FabricLoader;

import java.io.File;
import java.io.FileWriter;
import java.nio.file.Path;

public class PackHandler {
    public static final Path PACKS_DIR = FabricLoader.getInstance().getConfigDir().resolve("croparia");
    public static final Gson gson = new Gson();
    public static final String indent = "    ";
    public static Boolean INITIALIZED = false;

    public PackHandler() {
        if (!INITIALIZED) {
            INITIALIZED = true;
            this.addMetaFile();
        }
    }

    protected void addMetaFile() {
        Path path = PACKS_DIR.resolve("pack.mcmeta");
        this.writeJson(gson.toJsonTree(this.generateMetaFile()), path.toFile());
    }

    protected JsonObject generateMetaFile() {
        JsonObject root = new JsonObject();
        JsonObject pack = new JsonObject();
        pack.addProperty("pack_format", 9);
        pack.addProperty("description",
            "Croparia mandatory pack in %s.\nPlease do not modify data / assets folders!".formatted(PACKS_DIR));
        root.add("pack", pack);
        return root;
    }

    protected void writeJsonOrPass(Path path, JsonElement element) {
        File file = path.toFile();
        if (file.exists()) {
            return;
        }
        this.writeJson(element, file);
    }

    protected void writeJson(JsonElement element, File file) {
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        try (FileWriter writer = new FileWriter(file)) {    // FileWriter will auto create the file if it doesn't exist
            JsonWriter jsonWriter = new JsonWriter(writer);
            jsonWriter.setIndent(this.indent);
            gson.toJson(element, writer);
            jsonWriter.close();
        } catch (Exception ignored) {
        }
    }
}
