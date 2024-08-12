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
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeManager;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;

import java.io.File;
import java.io.FileWriter;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class RecipesInit {
    private static final Gson gson = new Gson();

    public static JsonObject genFruitRawRecipe(Crop crop) {
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

    public static JsonObject createShapedRecipeJson(ArrayList<Character> keys, ArrayList<Identifier> items, ArrayList<String> type, ArrayList<String> pattern, Identifier output) {
        JsonObject json = new JsonObject();
        json.addProperty("type", "minecraft:crafting_shaped");
        JsonArray jsonArray = new JsonArray();
        jsonArray.add(pattern.get(0));
        jsonArray.add(pattern.get(1));
        jsonArray.add(pattern.get(2));
        json.add("pattern", jsonArray);
        JsonObject keyList = new JsonObject();

        for (int i = 0; i < keys.size(); ++i) {
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
}
