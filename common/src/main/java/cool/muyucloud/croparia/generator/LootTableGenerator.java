package cool.muyucloud.croparia.generator;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import cool.muyucloud.croparia.CropariaIf;
import cool.muyucloud.croparia.data.crop.Crop;
import cool.muyucloud.croparia.registry.Crops;
import cool.muyucloud.croparia.util.pack.DataPackHandler;
import net.minecraft.resources.ResourceLocation;

public class LootTableGenerator {
    public static void init() {
        Crops.forEachCrop(LootTableGenerator::addCropBlock);
    }

    public static void addCropBlock(Crop crop) {
        ResourceLocation id = CropariaIf.of("blocks/" + crop.getBlockId().getPath());
        // 1. Pools
        // 1.1 Seed Pool
        // 1.1.1 Seed entry
        JsonObject seedEntry = new JsonObject();
        seedEntry.addProperty("type", "minecraft:item");
        seedEntry.addProperty("name", "croparia:seed_crop_" + crop.getName());
        // 1.1.2 Assemble seed entries
        JsonArray seedEntries = new JsonArray();
        seedEntries.add(seedEntry);
        // 1.1.3 Assemble seed pool
        JsonObject seedPool = new JsonObject();
        seedPool.addProperty("rolls", 1);
        seedPool.add("entries", seedEntries);

        // 1.2 Fruit Pool
        // 1.2.1 Fruit conditions
        // 1.2.1.1 Assemble fruit condition properties
        JsonObject fruitConditionProperties = new JsonObject();
        fruitConditionProperties.addProperty("age", String.valueOf(crop.getCropBlock().getMaxAge()));
        // 1.2.1.2 Assemble fruit condition
        JsonObject fruitCondition = new JsonObject();
        fruitCondition.addProperty("condition", "minecraft:block_state_property");
        fruitCondition.addProperty("block", "croparia:block_crop_" + crop.getName());
        fruitCondition.add("properties", fruitConditionProperties);
        // 1.2.1.3 Assemble fruit conditions
        JsonArray fruitConditions = new JsonArray();
        fruitConditions.add(fruitCondition);
        // 1.2.2 Assemble fruit entry
        // 1.2.2.1 Assemble fruit entry
        JsonObject fruitEntry = new JsonObject();
        fruitEntry.addProperty("type", "minecraft:item");
        fruitEntry.addProperty("name", "croparia:fruit_" + crop.getName());
        fruitEntry.add("conditions", fruitConditions);
        // 1.2.2.2 Assemble fruit entries
        JsonArray fruitEntries = new JsonArray();
        fruitEntries.add(fruitEntry);
        // 1.2.3 Assemble fruit pool
        JsonObject fruitPool = new JsonObject();
        fruitPool.addProperty("rolls", 1);
        fruitPool.add("entries", fruitEntries);

        // 1.3 Assemble pools
        JsonArray pools = new JsonArray();
        pools.add(seedPool);
        pools.add(fruitPool);
        // Assemble root
        JsonObject root = new JsonObject();
        root.addProperty("type", "minecraft:block");
        root.add("pools", pools);

        DataPackHandler.INSTANCE.addLootTable(id, root);
    }
}
