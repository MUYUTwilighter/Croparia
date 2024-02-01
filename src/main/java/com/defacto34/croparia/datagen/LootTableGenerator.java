//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.defacto34.croparia.datagen;

import com.defacto34.croparia.Croparia;
import com.defacto34.croparia.api.crop.CropariaCrops;
import com.defacto34.croparia.init.CropInit;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.SimpleFabricLootTableProvider;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.item.Item;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.condition.BlockStatePropertyLootCondition;
import net.minecraft.loot.condition.LootCondition;
import net.minecraft.loot.context.LootContextTypes;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.function.ApplyBonusLootFunction;
import net.minecraft.predicate.StatePredicate.Builder;
import net.minecraft.util.Identifier;

import java.util.function.BiConsumer;

public class LootTableGenerator extends SimpleFabricLootTableProvider {
    public LootTableGenerator(FabricDataOutput output) {
        super(output, LootContextTypes.BLOCK);
    }

    public void accept(BiConsumer<Identifier, LootTable.Builder> biConsumer) {
        CropInit.cropList.forEach((crop) -> {
            biConsumer.accept(new Identifier(Croparia.MOD_ID, "blocks/block_crop_" + crop.cropName), this.cropDrops(crop.fruit, crop.seed, BlockStatePropertyLootCondition.builder(crop.cropBlock).properties(Builder.create().exactMatch(CropariaCrops.AGE, 7))));
        });
    }

    public LootTable.Builder cropDrops(Item product, Item seeds, LootCondition.Builder condition) {
        return LootTable.builder().pool(LootPool.builder().with(ItemEntry.builder(product).conditionally(condition).alternatively(ItemEntry.builder(seeds)))).pool(LootPool.builder().conditionally(condition).with(ItemEntry.builder(seeds).apply(ApplyBonusLootFunction.binomialWithBonusCount(Enchantments.FORTUNE, 0.5714286F, 3))));
    }
}
