package com.defacto34.croparia.handler.pack;

import com.google.gson.JsonObject;
import net.minecraft.resource.ResourcePackSource;
import net.minecraft.resource.ResourceType;

import java.io.File;
import java.nio.file.Path;

public class DataPackHandler extends PackHandler {
    public static final DataPackHandler INSTANCE = new DataPackHandler();

    private final Path fruitToResourceRecipePath = PACKS_DIR.resolve("data/croparia/recipe/crafting/resource");
    private final Path cropBlockLootTablePath = PACKS_DIR.resolve("data/croparia/loot_table/blocks");
    private final AlwaysEnabledFileResourcePackProvider datapack = new AlwaysEnabledFileResourcePackProvider(
        PACKS_DIR, ResourceType.SERVER_DATA, ResourcePackSource.BUILTIN);

    public AlwaysEnabledFileResourcePackProvider getDatapack() {
        return datapack;
    }

    public void addFruitToResourceRecipe(String name, JsonObject recipe) {
        File file = fruitToResourceRecipePath.resolve(name + ".json").toFile();
        this.writeJson(recipe, file);
    }

    public void addCropBlockLootTable(String name, JsonObject table) {
        File file = cropBlockLootTablePath.resolve(name + ".json").toFile();
        this.writeJson(table, file);
    }
}
