package com.defacto34.croparia.handler.pack;

import com.google.gson.JsonObject;
import net.minecraft.resource.ResourcePackSource;
import net.minecraft.resource.ResourceType;

import java.io.File;
import java.nio.file.Path;

public class DataPackHandler extends PackHandler {
    public static final DataPackHandler INSTANCE = new DataPackHandler();

    private final Path fruitToResourceRecipePath = PACKS_DIR.resolve("data/croparia/recipes/crafting/resource");
    private final AlwaysEnabledFileResourcePackProvider datapack = new AlwaysEnabledFileResourcePackProvider(
        PACKS_DIR, ResourceType.SERVER_DATA, ResourcePackSource.BUILTIN);

    public AlwaysEnabledFileResourcePackProvider getDatapack() {
        return datapack;
    }

    public void addFruitToResourceRecipe(String name, JsonObject recipe) {
        File file = fruitToResourceRecipePath.resolve(name + ".json").toFile();
        this.writeJson(recipe, file);
    }
}
