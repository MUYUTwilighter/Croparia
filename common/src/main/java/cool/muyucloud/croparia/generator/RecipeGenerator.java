package cool.muyucloud.croparia.generator;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import cool.muyucloud.croparia.annotation.PostGen;
import cool.muyucloud.croparia.data.crop.Crop;
import cool.muyucloud.croparia.registry.CropariaItems;
import cool.muyucloud.croparia.registry.Crops;
import cool.muyucloud.croparia.util.TagUtil;
import cool.muyucloud.croparia.util.pack.DataPackHandler;
import dev.architectury.platform.Platform;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.resources.ResourceLocation;

import java.util.Objects;

public class RecipeGenerator {
    @PostGen
    public static void init() {
        for (Crop crop : Crops.CROPS) {
            createSeed(crop);
            createFruitToMaterial(crop);
        }
        if (Platform.isModLoaded("thermal_cultivation")) {
            Crops.CROPS.forEach(RecipeGenerator::createThermalIsolator);
        }
        if (Platform.isModLoaded("botanypots")) {
            Crops.CROPS.forEach(RecipeGenerator::createBotanyPot);
        }
    }

    public static void createSeed(Crop crop) {
        ResourceLocation id = ResourceLocation.tryParse("croparia:crafting/seed/" + crop.getName());
        JsonObject root = new JsonObject();

        root.addProperty("type", "minecraft:crafting_shaped");

        JsonObject croparia = new JsonObject();
        croparia.addProperty("item", CropariaItems.getCroparia(crop.getTier()).getId().toString());
        JsonObject material = new JsonObject();
        material.addProperty(crop.isTag() ? "tag" : "item", crop.getMaterial().toString());
        JsonObject seed = new JsonObject();
        seed.addProperty("tag", TagUtil.compatId("seeds").toString());
        JsonObject keys = new JsonObject();
        keys.add("C", croparia);
        keys.add("M", material);
        keys.add("S", seed);
        root.add("translationKey", keys);

        JsonArray pattern = new JsonArray();
        pattern.add("MSM");
        pattern.add("SCS");
        pattern.add("MSM");
        root.add("pattern", pattern);

        JsonObject result = new JsonObject();
        result.addProperty("item", crop.getSeedId().toString());
        result.addProperty("count", 1);
        root.add("result", result);

        DataPackHandler.INSTANCE.addRecipe(Objects.requireNonNull(id), root);
    }

    @PostGen
    public static void createFruitToMaterial(Crop crop) {
        ResourceLocation id = ResourceLocation.tryParse("croparia:crafting/material/" + crop.getName());
        JsonObject root = new JsonObject();

        root.addProperty("type", "minecraft:crafting_shapeless");

        JsonArray ingredients = new JsonArray();
        JsonObject ingredient = new JsonObject();
        ingredient.addProperty("item", crop.getFruitId().toString());
        ingredients.add(ingredient);
        root.add("ingredients", ingredients);

        JsonObject result = new JsonObject();
        result.addProperty("item", Objects.requireNonNull(crop.getMaterialItem().arch$registryName()).toString());
        result.addProperty("count", 2);
        root.add("result", result);

        DataPackHandler.INSTANCE.addRecipe(Objects.requireNonNull(id), root);
    }

    public static void createThermalIsolator(Crop crop) {
        ResourceLocation id = ResourceLocation.tryParse("croparia:thermal/insolator/" + crop.getName());
        JsonObject root = new JsonObject();
        root.addProperty("type", "thermal:insolator");

        JsonArray ingredients = new JsonArray();
        JsonObject ingredient = new JsonObject();
        ingredient.addProperty("item", crop.getSeedId().toString());
        ingredients.add(ingredient);
        root.add("ingredients", ingredients);

        JsonArray results = new JsonArray();
        JsonObject resultFruit = new JsonObject();
        resultFruit.addProperty("item", crop.getFruitId().toString());
        resultFruit.addProperty("chance", 1.0F);
        results.add(resultFruit);
        JsonObject resultSeed = new JsonObject();
        resultSeed.addProperty("item", crop.getSeedId().toString());
        resultSeed.addProperty("chance", 1.0F);
        results.add(resultSeed);
        root.add("result", results);

        root.addProperty("energy_mod", 3.0F);
        root.addProperty("experience", 0.0F);

        DataPackHandler.INSTANCE.addRecipe(Objects.requireNonNull(id), root);
    }

    public static void createBotanyPot(Crop crop) {
        ResourceLocation id = ResourceLocation.tryParse("croparia:botanypots/" + crop.getName());
        JsonObject root = new JsonObject();

        root.addProperty("type", "botanypots:crop");

        JsonObject seed = new JsonObject();
        seed.addProperty("item", crop.getSeedId().toString());
        root.add("seed", seed);

        JsonArray categories = new JsonArray();
        categories.add("crop");
        categories.add("farmland");
        root.add("categories", categories);

        root.addProperty("growthTicks", 1200);

        JsonObject display = new JsonObject();
        display.addProperty("type", "botanypots:aging");
        display.addProperty("block", crop.getBlockId().toString());
        root.add("display", display);

        JsonArray drops = new JsonArray();
        JsonObject drop = new JsonObject();
        drop.addProperty("chance", 1.0F);
        drop.addProperty("minRolls", 1);
        drop.addProperty("maxRolls", 1);
        JsonObject output = new JsonObject();
        output.addProperty("item", crop.getFruitId().toString());
        drop.add("output", output);
        drops.add(drop);
        root.add("drops", drops);

        DataPackHandler.INSTANCE.addRecipe(Objects.requireNonNull(id), root);
    }
}
