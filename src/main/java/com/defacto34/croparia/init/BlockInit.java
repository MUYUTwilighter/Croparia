//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.defacto34.croparia.init;

import com.defacto34.croparia.Croparia;
import com.defacto34.croparia.api.crop.Crop;
import com.defacto34.croparia.api.crop.CropariaCrops;
import com.defacto34.croparia.core.block.Greenhouse;
import com.defacto34.croparia.core.block.Infusor;
import com.defacto34.croparia.core.block.RitualStand;
import com.defacto34.croparia.core.item.GreenHouseBlockItem;
import com.defacto34.croparia.core.util.CropariaCauldronInteraction;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.block.*;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.intprovider.UniformIntProvider;
import net.minecraft.world.biome.Biome;
import org.jetbrains.annotations.NotNull;

public class BlockInit {
    public static final Block ELEMENTAL_STONE = registerBlockWithItem("elemental_stone", new Block(AbstractBlock.Settings.create().strength(1.0F, 1.0F).requiresTool()));
    public static final Block ELEMATILIUS_ORE = registerBlockWithItem("elematilius_ore", new ExperienceDroppingBlock(UniformIntProvider.create(0, 2), AbstractBlock.Settings.create().strength(1.0F, 1.0F).requiresTool()));
    public static final Block DEEPSLATE_ELEMATILIUS_ORE;
    public static final Block GREENHOUSE;
    public static final Block RITUAL_STAND;
    public static final Block INFUSOR;
    public static final Block CAULDRON;
    public static final Block WATER_CAULDRON;
    public static final Block FIRE_CAULDRON;
    public static final Block EARTH_CAULDRON;
    public static final Block AIR_CAULDRON;

    public BlockInit() {
    }

    private static Block registerGreenhouse(String name, Block block) {
        Item item = new GreenHouseBlockItem(block, new Item.Settings());
        Registry.register(Registries.ITEM, Identifier.of(Croparia.MOD_ID, name), item);
        ItemGroupEvents.modifyEntriesEvent(Croparia.MAIN).register((entries) -> {
            entries.add(item);
        });
        return registerBlock(name, block);
    }

    public static void registerCrop(@NotNull Crop crop) {
        crop.cropBlock = registerBlock("block_crop_" + crop.cropName, new CropariaCrops(crop));
    }

    public static Block registerBlock(String name, Block block) {
        return Registry.register(Registries.BLOCK, Identifier.of(Croparia.MOD_ID, name), block);
    }

    public static Block registerBlockWithItem(String name, Block block) {
        registerBlockItem(name, block);
        return registerBlock(name, block);
    }

    private static Item registerBlockItem(String name, Block block) {
        Item item = new BlockItem(block, new Item.Settings());
        ItemGroupEvents.modifyEntriesEvent(Croparia.MAIN).register((entries) -> {
            entries.add(item);
        });
        return Registry.register(Registries.ITEM, Identifier.of(Croparia.MOD_ID, name), item);
    }

    public static void registerBlocks() {
    }

    static {
        DEEPSLATE_ELEMATILIUS_ORE = registerBlockWithItem("deepslate_elematilius_ore", new ExperienceDroppingBlock(UniformIntProvider.create(0, 2), AbstractBlock.Settings.copy(ELEMATILIUS_ORE).mapColor(MapColor.DEEPSLATE_GRAY).strength(1.0F, 1.0F).sounds(BlockSoundGroup.DEEPSLATE).requiresTool()));
        GREENHOUSE = registerGreenhouse("greenhouse", new Greenhouse(AbstractBlock.Settings.create().strength(1.0F, 1.0F).ticksRandomly().luminance((state) -> 8)));
        RITUAL_STAND = registerBlockWithItem("ritual_stand", new RitualStand());
        INFUSOR = registerBlockWithItem("infusor", new Infusor());
        CAULDRON = registerBlock("elematilius_cauldron", new LeveledCauldronBlock(Biome.Precipitation.RAIN, CropariaCauldronInteraction.ELEMATILIUS, AbstractBlock.Settings.copy(Blocks.CAULDRON)));
        WATER_CAULDRON = registerBlock("water_cauldron", new LeveledCauldronBlock(Biome.Precipitation.RAIN, CropariaCauldronInteraction.WATER_CAULDRON, AbstractBlock.Settings.copy(Blocks.CAULDRON)));
        FIRE_CAULDRON = registerBlock("fire_cauldron", new LeveledCauldronBlock(Biome.Precipitation.RAIN, CropariaCauldronInteraction.FIRE_CAULDRON, AbstractBlock.Settings.copy(Blocks.CAULDRON)));
        EARTH_CAULDRON = registerBlock("earth_cauldron", new LeveledCauldronBlock(Biome.Precipitation.RAIN, CropariaCauldronInteraction.EARTH_CAULDRON, AbstractBlock.Settings.copy(Blocks.CAULDRON)));
        AIR_CAULDRON = registerBlock("air_cauldron", new LeveledCauldronBlock(Biome.Precipitation.RAIN, CropariaCauldronInteraction.AIR_CAULDRON, AbstractBlock.Settings.copy(Blocks.CAULDRON)));
    }
}
