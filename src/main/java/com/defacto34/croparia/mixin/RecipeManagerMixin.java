//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.defacto34.croparia.mixin;

import com.defacto34.croparia.Croparia;
import com.defacto34.croparia.init.CropInit;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import java.util.Map;
import net.minecraft.recipe.RecipeManager;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.Identifier;
import net.minecraft.util.profiler.Profiler;
import org.spongepowered.asm.mixin.Mixin;
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
        CropInit.recipes.forEach((jsonObject) -> map.put(new Identifier(Croparia.MOD_ID, jsonObject.getAsJsonObject("result").get("item").getAsString().replaceFirst("croparia:", "")), (new Gson()).fromJson(jsonObject, JsonElement.class)));
    }
}
