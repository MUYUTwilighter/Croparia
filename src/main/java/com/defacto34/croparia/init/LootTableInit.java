package com.defacto34.croparia.init;

import com.defacto34.croparia.Croparia;
import com.defacto34.croparia.api.crop.Crop;
import com.defacto34.croparia.api.crop.CropariaCrops;
import net.minecraft.loot.LootDataKey;
import net.minecraft.loot.LootDataType;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.condition.BlockStatePropertyLootCondition;
import net.minecraft.loot.context.LootContextTypes;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.entry.LeafEntry;
import net.minecraft.loot.provider.number.ConstantLootNumberProvider;
import net.minecraft.predicate.StatePredicate;
import net.minecraft.util.Identifier;

import java.util.HashMap;
import java.util.Map;

public class LootTableInit {
    public static Map<LootDataKey<?>, Object> LOOT_TABLES = new HashMap<>();

    public static void registerCrop(Crop crop) {
        // Seed pool
        LeafEntry.Builder<?> seedEntry = ItemEntry
                .builder(crop.seed);
        LootPool.Builder seedPool = LootPool.builder()
                .rolls(ConstantLootNumberProvider.create(1))
                .with(seedEntry);
        // Fruit pool
        // Condition: crop mature
        CropariaCrops cropBlock = (CropariaCrops) crop.cropBlock;
        StatePredicate.Builder predicateBuilder = StatePredicate.Builder.create()
                .exactMatch(CropariaCrops.AGE, cropBlock.getMaxAge());
        BlockStatePropertyLootCondition.Builder conditionBuilder = BlockStatePropertyLootCondition
                .builder(cropBlock)
                .properties(predicateBuilder);
        LeafEntry.Builder<?> fruitEntry = ItemEntry
                .builder(crop.fruit)
                .conditionally(conditionBuilder);
        LootPool.Builder fruitPool = LootPool.builder()
                .rolls(ConstantLootNumberProvider.create(1))
                .with(fruitEntry);
        // Loot table
        LootTable lootTable = LootTable.builder()
                .type(LootContextTypes.BLOCK)
                .pool(seedPool)
                .pool(fruitPool)
                .build();

        // Loot data key
        Identifier identifier = new Identifier(Croparia.MOD_ID, "blocks/block_crop_" + crop.cropName);
        LootDataKey<?> lootDataKey = new LootDataKey<>(LootDataType.LOOT_TABLES, identifier);
        LOOT_TABLES.put(lootDataKey, lootTable);
    }
}
