//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.defacto34.croparia.init;

import com.defacto34.croparia.Croparia;
import com.defacto34.croparia.api.crop.Crop;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonWriter;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;

import java.io.File;
import java.io.FileWriter;
import java.nio.file.Path;

public class RecipesInit {
    private static final Gson gson = new Gson();

    public RecipesInit() {
    }

    public static void registerRecipes() {
    }

    public static void dumpFruitRecipes() {
        Path path = Path.of(Croparia.CONFIG.getGenRecipe());
        File dir = path.toFile();
        if (!dir.exists()) {
            dir.mkdirs();
        }
        if (dir.isFile()) {
            return;
        }
        CropInit.cropList.forEach(crop -> {
            JsonObject recipe = genFruitRecipe(crop);
            String fileName = "fruit_" + crop.cropName + ".json";
            File file = path.resolve(fileName).toFile();
            try {
                if (!file.exists()) {
                    file.createNewFile();
                }
                FileWriter fileWriter = new FileWriter(file);
                JsonWriter writer = new JsonWriter(fileWriter);
                writer.setIndent("    ");
                gson.toJson(recipe, writer);
                writer.close();
            } catch (Exception ignored) {
            }
        });
    }

    private static JsonObject genFruitRecipe(Crop crop) {
        JsonObject root = new JsonObject();

        root.addProperty("type", "minecraft:crafting_shapeless");

        JsonArray ingredients = new JsonArray();
        JsonObject ingredient = new JsonObject();
        ingredient.addProperty("item", "croparia:fruit_" + crop.cropName);
        ingredients.add(ingredient);
        root.add("ingredients", ingredients);

        JsonObject result = new JsonObject();
        Item resultItem = crop.tag == null ? crop.material : Croparia.getItemFromTag(new Identifier(crop.tag)).getItem();
        Identifier resultId = Registries.ITEM.getId(resultItem);
        result.addProperty("item", resultId.toString());
        result.addProperty("count", 2);
        root.add("result", result);

        return root;
    }
}
