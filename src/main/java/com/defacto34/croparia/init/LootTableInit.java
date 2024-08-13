package com.defacto34.croparia.init;

import com.defacto34.croparia.api.crop.Crop;
import com.defacto34.croparia.api.crop.CropariaCrops;
import com.defacto34.croparia.handler.pack.DataPackHandler;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.jetbrains.annotations.NotNull;

public class LootTableInit {
    public static void registerCrop(Crop crop) {
        DataPackHandler.INSTANCE.addCropBlockLootTable("block_crop_" + crop.cropName, generateRawLootTable(crop));
    }

    public static JsonObject generateRawLootTable(Crop crop) {
        // Assemble pools
        JsonArray pools = new JsonArray();
        pools.add(getSeedPool(crop));
        pools.add(getFruitPool(crop));
        // Assemble root
        JsonObject root = new JsonObject();
        root.addProperty("type", "minecraft:block");
        root.add("pools", pools);
        return root;
    }

    private static @NotNull JsonObject getSeedPool(Crop crop) {
        // Assemble seed entry
        JsonObject seedEntry = new JsonObject();
        seedEntry.addProperty("type", "minecraft:item");
        seedEntry.addProperty("name", "croparia:seed_crop_" + crop.cropName);
        // Assemble seed entries
        JsonArray seedEntries = new JsonArray();
        seedEntries.add(seedEntry);
        // Assemble seed pool
        JsonObject seedPool = new JsonObject();
        seedPool.addProperty("rolls", 1);
        seedPool.add("entries", seedEntries);
        return seedPool;
    }

    private static @NotNull JsonObject getFruitPool(Crop crop) {
        // Assemble fruit entry
        JsonObject fruitEntry = new JsonObject();
        fruitEntry.addProperty("type", "minecraft:item");
        fruitEntry.addProperty("name", "croparia:fruit_" + crop.cropName);
        fruitEntry.add("conditions", getFruitConditions(crop));
        // Assemble fruit entries
        JsonArray fruitEntries = new JsonArray();
        fruitEntries.add(fruitEntry);
        // Assemble fruit pool
        JsonObject fruitPool = new JsonObject();
        fruitPool.addProperty("rolls", 1);
        fruitPool.add("entries", fruitEntries);
        return fruitPool;
    }

    private static @NotNull JsonArray getFruitConditions(Crop crop) {
        // Assemble fruit condition properties
        JsonObject fruitConditionProperties = new JsonObject();
        fruitConditionProperties.addProperty("age", String.valueOf(((CropariaCrops) crop.cropBlock).getMaxAge()));
        // Assemble fruit condition
        JsonObject fruitCondition = new JsonObject();
        fruitCondition.addProperty("condition", "minecraft:block_state_property");
        fruitCondition.addProperty("block", "croparia:block_crop_" + crop.cropName);
        fruitCondition.add("properties", fruitConditionProperties);
        // Assemble fruit conditions
        JsonArray fruitConditions = new JsonArray();
        fruitConditions.add(fruitCondition);
        return fruitConditions;
    }
}
