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
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;

public class RecipesInit {
    private static final Gson gson = new Gson();

    public static JsonObject genRawSeedRecipe(Crop crop) {
        // Assemble key
        JsonObject key = new JsonObject();
        key.addProperty("R", (crop.material == null ? "#" : "") + crop.resource);
        key.addProperty("S", "minecraft:wheat_seeds");
        key.addProperty("C", "croparia:croparia" + (crop.tier == 1 ? "" : crop.tier));

        // Assemble pattern
        JsonArray pattern = new JsonArray();
        pattern.add("RSR");
        pattern.add("SCS");
        pattern.add("RSR");

        // Assemble result
        JsonObject result = new JsonObject();
        result.addProperty("count", 1);
        result.addProperty("id", "croparia:seed_crop_" + crop.cropName);

        // Assemble root
        JsonObject root = new JsonObject();
        root.addProperty("type", "minecraft:crafting_shaped");
        root.add("pattern", pattern);
        root.add("result", result);
        root.add("key", key);

        return root;
    }

    public static JsonObject genFruitRawRecipe(Crop crop) {
        JsonObject root = new JsonObject();

        root.addProperty("type", "minecraft:crafting_shapeless");

        JsonArray ingredients = new JsonArray();
        ingredients.add("croparia:fruit_" + crop.cropName);
        root.add("ingredients", ingredients);

        JsonObject result = new JsonObject();
        Item resultItem = crop.material != Items.AIR ? crop.material : crop.tag != null ? Croparia.getItemFromTag(Identifier.of(crop.tag)).getItem() : Items.AIR;
        Identifier resultId = Registries.ITEM.getId(resultItem);
        result.addProperty("id", resultId.toString());
        result.addProperty("count", Math.min(2, resultItem.getMaxCount()));
        root.add("result", result);

        return root;
    }
}
