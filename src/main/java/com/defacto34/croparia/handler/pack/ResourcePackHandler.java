package com.defacto34.croparia.handler.pack;

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

public class ResourcePackHandler extends PackHandler {
    public static final ResourcePackHandler INSTANCE = new ResourcePackHandler();

    private final Path blockStateModelPath = PACKS_DIR.resolve("assets/croparia/blockstates");
    private final Path itemModelPath = PACKS_DIR.resolve("assets/croparia/models/item");
    private final Path langEnUsPath = PACKS_DIR.resolve("assets/croparia/lang/en_us.json");
    private final DirectoryResourcePack resourcePack = new DirectoryResourcePack("croparia", PACKS_DIR, true);

    public ResourcePack getResourcePack() {
        return this.resourcePack;
    }

    public void addBlockStateModel(String name, JsonObject json) {
        Path path = this.blockStateModelPath.resolve(name + ".json");
        this.writeJson(json, path.toFile());
    }

    public void addItemModel(String name, JsonObject json) {
        Path path = this.itemModelPath.resolve(name + ".json");
        this.writeJson(json, path.toFile());
    }

    public void addLangEnUs(JsonObject json) {
        this.writeJson(json, this.langEnUsPath.toFile());
    }
}
