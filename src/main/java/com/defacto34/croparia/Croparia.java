//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.defacto34.croparia;

import com.defacto34.croparia.api.CropFileReader;
import com.defacto34.croparia.api.crop.Crop;
import com.defacto34.croparia.core.item.relics.HornPlenty;
import com.defacto34.croparia.core.recipes.InfusorRecipe.Type;
import com.defacto34.croparia.core.recipes.InfusorRecipeSerializer;
import com.defacto34.croparia.core.recipes.RitualRecipeSerializer;
import com.defacto34.croparia.core.util.CropariaCauldronInteraction;
import com.defacto34.croparia.handler.pack.DataPackHandler;
import com.defacto34.croparia.init.*;
import com.defacto34.croparia.worldgen.gen.OreGen;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntryList;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.Path;
import java.util.Optional;

public class Croparia implements ModInitializer {
    public static String MOD_ID = "croparia";
    public static final Logger LOGGER = LoggerFactory.getLogger("croparia");
    public static final RegistryKey<ItemGroup> MAIN;
    public static final RegistryKey<ItemGroup> CROP;
    public static final Path CONFIG_PATH = FabricLoader.getInstance().getConfigDir().resolve("croparia/config.json");
    public static Config CONFIG = ConfigIoProcessor.load(CONFIG_PATH);

    public void onInitialize() {
        CropFileReader.main();
        CropInit.registerCrops();
        BlockInit.registerBlocks();
        ItemInit.registerItems();
        BlockEntityInit.onInitialize();
        Registry.register(Registries.ITEM_GROUP, MAIN, FabricItemGroup.builder().icon(() -> new ItemStack(ItemInit.ELEMATILIUS)).displayName(Text.translatable("itemGroup." + MOD_ID + ".main")).build());
        Registry.register(Registries.ITEM_GROUP, CROP, FabricItemGroup.builder().icon(() -> new ItemStack(CropInit.DIAMOND.seed)).displayName(Text.translatable("itemGroup." + MOD_ID + ".crop")).build());
        OreGen.generateOres();
        Registry.register(Registries.RECIPE_SERIALIZER, InfusorRecipeSerializer.ID, InfusorRecipeSerializer.INSTANCE);
        Registry.register(Registries.RECIPE_TYPE, Identifier.of(MOD_ID, "infusor_recipe"), Type.INSTANCE);
        Registry.register(Registries.RECIPE_SERIALIZER, RitualRecipeSerializer.ID, RitualRecipeSerializer.INSTANCE);
        Registry.register(Registries.RECIPE_TYPE, Identifier.of(MOD_ID, "ritual_recipe"), com.defacto34.croparia.core.recipes.RitualRecipe.Type.INSTANCE);
        HornPlenty.initFood();
        CropariaCauldronInteraction.bootStrap();
        ServerLifecycleEvents.SERVER_STARTED.register((server) -> dataPostGen());
        ServerLifecycleEvents.START_DATA_PACK_RELOAD.register((server, manager) -> CONFIG = ConfigIoProcessor.load(CONFIG_PATH));
        ServerLifecycleEvents.END_DATA_PACK_RELOAD.register((server, resourceManager, success) -> dataPostGen());
        LOGGER.info("Hello from Croparia");
    }

    public static void sendMessage(PlayerEntity player, String string) {
        player.sendMessage(Text.of(string), true);
    }

    public static ItemStack getItemFromTag(Identifier tag) {
        TagKey<Item> TAG = TagKey.of(RegistryKeys.ITEM, tag);
        Optional<RegistryEntryList.Named<Item>> list = Registries.ITEM.getEntryList(TAG);
        return list.isPresent() ? list.get().get(0).value().getDefaultStack() : Items.APPLE.getDefaultStack();
    }

    public static void dataPostGen() {
        if (!CONFIG.getPostDataGen()) {
            return;
        }
        // Generate fruit to resource recipes
        // See also: com.defacto34.croparia.mixin.ResourcePackManagerMixin
        Thread thread = new Thread(() -> {
            DataPackHandler handler = DataPackHandler.INSTANCE;
            for (Crop crop : CropInit.cropList) {
                handler.addFruitToResourceRecipe(crop.cropName, RecipesInit.genFruitRawRecipe(crop));
            }
        });
        thread.start();
    }

    static {
        MAIN = RegistryKey.of(RegistryKeys.ITEM_GROUP, Identifier.of(MOD_ID, "main"));
        CROP = RegistryKey.of(RegistryKeys.ITEM_GROUP, Identifier.of(MOD_ID, "crop"));
    }
}
