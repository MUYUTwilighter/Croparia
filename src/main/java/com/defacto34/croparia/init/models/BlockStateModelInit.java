package com.defacto34.croparia.init.models;

import com.defacto34.croparia.ResourcePackHandler;
import com.defacto34.croparia.api.crop.Crop;
import com.defacto34.croparia.api.crop.CropariaCrops;
import com.google.gson.JsonObject;

/** NOT COMPLETED!<br/>
 * Add default BlockState json for crops
 * */
public class BlockStateModelInit {
    public static void registerCrop(Crop crop) {
        String cropName = crop.cropName;
        CropariaCrops crops = (CropariaCrops) crop.cropBlock;

        JsonObject root = new JsonObject();
        JsonObject variants = new JsonObject();
        for (int i = 0; i <= crops.getMaxAge(); ++i) {
            JsonObject age = new JsonObject();
            age.addProperty("model", "croparia:block/crop_stage" + i);
            variants.add("age=" + i, age);
        }
        root.add("variants", variants);

        String fileName = "block_crop_" + cropName;
        ResourcePackHandler.INSTANCE.addBlockStateModel(fileName, root);
    }
}
