package com.defacto34.croparia.init.models;

import com.defacto34.croparia.ResourcePackHandler;
import com.defacto34.croparia.api.crop.Crop;
import com.google.gson.JsonObject;

public class ItemModelInit {
    public static void registerCrop(Crop crop) {
        String cropName = crop.cropName;
        // Fruit
        JsonObject fruit = new JsonObject();
        fruit.addProperty("parent", "minecraft:item/generated");
        JsonObject fruitTextures = new JsonObject();
        fruitTextures.addProperty("layer0", "croparia:item/fruit_crop");
        fruitTextures.addProperty("layer1", "croparia:item/fruit_crop_overlay");
        fruit.add("textures", fruitTextures);
        String fruitFileName = "fruit_" + cropName;
        ResourcePackHandler.INSTANCE.addItemModel(fruitFileName, fruit);
        // Seed
        JsonObject seed = new JsonObject();
        seed.addProperty("parent", "minecraft:item/generated");
        JsonObject seedTextures = new JsonObject();
        seedTextures.addProperty("layer0", "croparia:item/seed_crop");
        seed.add("textures", seedTextures);
        String seedFileName = "seed_crop_" + cropName;
        ResourcePackHandler.INSTANCE.addItemModel(seedFileName, seed);
    }
}
