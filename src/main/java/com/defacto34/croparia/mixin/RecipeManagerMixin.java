//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.defacto34.croparia.mixin;

import com.defacto34.croparia.Croparia;
import com.defacto34.croparia.api.crop.Crop;
import com.defacto34.croparia.init.CropInit;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;

import java.util.Map;

import com.google.gson.JsonObject;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.item.Item;
import net.minecraft.recipe.RecipeManager;
import net.minecraft.registry.Registries;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.Identifier;
import net.minecraft.util.profiler.Profiler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({RecipeManager.class})
public class RecipeManagerMixin {
    @Inject(
            method = {"apply*"},
            at = {@At("HEAD")}
    )
    public void interceptApply(Map<Identifier, JsonElement> map, ResourceManager resourceManager, Profiler profiler, CallbackInfo info) {
        CropInit.recipes.forEach(
                (jsonObject) -> map.putIfAbsent(
                        new Identifier(
                                Croparia.MOD_ID,
                                jsonObject.getAsJsonObject("result").get("item").getAsString().replaceFirst("croparia:", "")),
                        (new Gson()).fromJson(jsonObject, JsonElement.class)
                )
        );
        boolean bpLoaded = FabricLoader.getInstance().isModLoaded("botanypots");
        if (bpLoaded) {
            CropInit.cropList.forEach(
                    crop -> {
                        Identifier identifier = new Identifier("botanypots:croparia/seed/" + crop.cropName);
                        JsonObject recipe = this.genBotanyPotRecipe(crop);
                        map.putIfAbsent(identifier, recipe);
                    }
            );
        }
    }

    @Unique
    private JsonObject genBotanyPotRecipe(Crop crop) {
        JsonObject root = new JsonObject();

        root.addProperty("type", "botanypots:crop");

        JsonObject seed = new JsonObject();
        seed.addProperty("item", "croparia:seed_crop_" + crop.cropName);
        root.add("seed", seed);

        JsonArray categories = new JsonArray();
        categories.add("dirt");
        categories.add("farmland");
        root.add("categories", categories);

        root.addProperty("growthTicks", 1200);

        JsonObject display = new JsonObject();
        display.addProperty("type", "botanypots:aging");
        display.addProperty("block", "croparia:block_crop_" + crop.cropName);
        root.add("display", display);

        JsonArray drops = new JsonArray();
        JsonObject fruitDrop = new JsonObject();
        fruitDrop.addProperty("chance", 1.00f);
        JsonObject fruitOutput = new JsonObject();
        fruitOutput.addProperty("item", "croparia:fruit_" + crop.cropName);
        fruitDrop.add("output", fruitOutput);
        drops.add(fruitDrop);
        JsonObject seedDrop = new JsonObject();
        seedDrop.addProperty("chance", 0.01f);
        JsonObject seedOutput = new JsonObject();
        seedOutput.addProperty("item", "croparia:seed_crop_" + crop.cropName);
        seedDrop.add("output", seedOutput);
        drops.add(seedDrop);
        root.add("drops", drops);

        return root;
    }
}
