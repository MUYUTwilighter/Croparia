//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.defacto34.croparia;

import com.defacto34.croparia.api.CropFileReader;
import com.defacto34.croparia.core.item.relics.HornPlenty;
import com.defacto34.croparia.core.recipes.InfusorRecipe.Type;
import com.defacto34.croparia.core.recipes.InfusorRecipeSerializer;
import com.defacto34.croparia.core.recipes.RitualRecipeSerializer;
import com.defacto34.croparia.core.util.CropariaCauldronInteraction;
import com.defacto34.croparia.init.*;
import com.defacto34.croparia.worldgen.gen.OreGen;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientLifecycleEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
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
import net.minecraft.resource.ResourcePack;
import net.minecraft.resource.ResourceType;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Optional;

public class Croparia implements ModInitializer {
    public static String MOD_ID = "croparia";
    public static final Logger LOGGER = LoggerFactory.getLogger("croparia");
    public static final RegistryKey<ItemGroup> MAIN;
    public static final RegistryKey<ItemGroup> CROP;

    public Croparia() {
    }

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
        Registry.register(Registries.RECIPE_TYPE, new Identifier(MOD_ID, "infusor_recipe"), Type.INSTANCE);
        Registry.register(Registries.RECIPE_SERIALIZER, RitualRecipeSerializer.ID, RitualRecipeSerializer.INSTANCE);
        Registry.register(Registries.RECIPE_TYPE, new Identifier(MOD_ID, "ritual_recipe"), com.defacto34.croparia.core.recipes.RitualRecipe.Type.INSTANCE);
        RecipesInit.registerRecipes();
        HornPlenty.initFood();
        CropariaCauldronInteraction.bootStrap();
        LOGGER.info("Hello from Croparia");
    }

    public static void sendMessage(PlayerEntity player, String string) {
        player.sendMessage(Text.of(string), true);
    }

    public static JsonObject createShapedRecipeJson(ArrayList<Character> keys, ArrayList<Identifier> items, ArrayList<String> type, ArrayList<String> pattern, Identifier output) {
        JsonObject json = new JsonObject();
        json.addProperty("type", "minecraft:crafting_shaped");
        JsonArray jsonArray = new JsonArray();
        jsonArray.add(pattern.get(0));
        jsonArray.add(pattern.get(1));
        jsonArray.add(pattern.get(2));
        json.add("pattern", jsonArray);
        JsonObject keyList = new JsonObject();

        for(int i = 0; i < keys.size(); ++i) {
            JsonObject individualKey = new JsonObject();
            individualKey.addProperty(type.get(i), items.get(i).toString());
            keyList.add(String.valueOf(keys.get(i)), individualKey);
        }

        json.add("key", keyList);
        JsonObject result = new JsonObject();
        result.addProperty("item", output.toString());
        result.addProperty("count", 1);
        json.add("result", result);
        return json;
    }

    public static ItemStack getItemFromTag(Identifier tag) {
        TagKey<Item> TAG = TagKey.of(RegistryKeys.ITEM, tag);
        Optional<RegistryEntryList.Named<Item>> list = Registries.ITEM.getEntryList(TAG);
        return list.isPresent() ? ((Item)((RegistryEntryList.Named)list.get()).get(0).value()).getDefaultStack() : Items.APPLE.getDefaultStack();
    }

    static {
        MAIN = RegistryKey.of(RegistryKeys.ITEM_GROUP, new Identifier(MOD_ID, "main"));
        CROP = RegistryKey.of(RegistryKeys.ITEM_GROUP, new Identifier(MOD_ID, "crop"));
    }
}
