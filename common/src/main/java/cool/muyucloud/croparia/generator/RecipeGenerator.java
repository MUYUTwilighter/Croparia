package cool.muyucloud.croparia.generator;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import cool.muyucloud.croparia.CropariaIf;
import cool.muyucloud.croparia.annotation.PostGen;
import cool.muyucloud.croparia.data.crop.Crop;
import cool.muyucloud.croparia.registry.CropariaItems;
import cool.muyucloud.croparia.registry.Crops;
import cool.muyucloud.croparia.util.TagUtil;
import cool.muyucloud.croparia.util.pack.DataPackHandler;
import net.minecraft.resources.ResourceLocation;

import java.util.Objects;

public class RecipeGenerator {
    @PostGen
    public static void init() {
        for (Crop crop : Crops.CROPS) {
            createSeed(crop);
            createFruitToMaterial(crop);
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
        seed.addProperty("tag", CropariaIf.MOD_ID + ":seed_ingredient");
        JsonObject keys = new JsonObject();
        keys.add("C", croparia);
        keys.add("M", material);
        keys.add("S", seed);
        root.add("key", keys);

        JsonArray pattern = new JsonArray();
        pattern.add("MSM");
        pattern.add("SCS");
        pattern.add("MSM");
        root.add("pattern", pattern);

        JsonObject result = new JsonObject();
        result.addProperty("id", crop.getSeedId().toString());
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
        result.addProperty("id", Objects.requireNonNull(crop.getMaterialItem().arch$registryName()).toString());
        result.addProperty("count", Math.max(crop.getMaterialItem().getDefaultMaxStackSize(), 1));
        root.add("result", result);

        DataPackHandler.INSTANCE.addRecipe(Objects.requireNonNull(id), root);
    }
}
