package com.defacto34.croparia;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonWriter;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.resource.DirectoryResourcePack;
import net.minecraft.resource.ResourcePack;

import java.io.*;
import java.nio.file.Path;

public class ResourcePackHandler {
    public static final ResourcePackHandler INSTANCE = new ResourcePackHandler();

    private final Path resourcePath = FabricLoader.getInstance().getConfigDir().resolve("croparia/resource");
    private final Path blockStateModelPath = resourcePath.resolve("assets/croparia/blockstates");
    private final Path itemModelPath = resourcePath.resolve("assets/croparia/models/item");
    private final Path langEnUsPath = resourcePath.resolve("assets/croparia/lang/en_us.json");
    private final DirectoryResourcePack resourcePack = new DirectoryResourcePack("croparia", resourcePath, true);
    private final Gson gson = new GsonBuilder().create();
    private final String indent = "    ";

    public ResourcePackHandler() {
        this.addMeta(this.generateMetaFile());
    }

    private JsonObject generateMetaFile() {
        return new JsonObject();
    }

    private void writeJsonOrPass(Path path, JsonElement element) {
        File file = path.toFile();
        if (file.exists()) {
            return;
        }
        this.writeJson(element, file);
    }

    private void writeJson(JsonElement element, File file) {
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        try (FileWriter writer = new FileWriter(file)) {
            JsonWriter jsonWriter = new JsonWriter(writer);
            jsonWriter.setIndent(this.indent);
            gson.toJson(element, writer);
            jsonWriter.close();
        } catch (Exception ignored) {
        }
    }


    public ResourcePack getResourcePack() {
        return this.resourcePack;
    }

    public void addMeta(JsonObject json) {
        Path path = this.resourcePath.resolve("pack.mcmeta");
        this.writeJsonOrPass(path, json);
    }

    public void addBlockStateModel(String name, JsonObject json) {
        Path path = this.blockStateModelPath.resolve(name + ".json");
        this.writeJsonOrPass(path, json);
    }

    public void addItemModel(String name, JsonObject json) {
        Path path = this.itemModelPath.resolve(name + ".json");
        this.writeJsonOrPass(path, json);
    }

    public void addLangEnUs(JsonObject json) {
        this.writeJson(json, this.langEnUsPath.toFile());
    }
}
