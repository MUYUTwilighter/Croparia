package com.defacto34.croparia.init;

import com.defacto34.croparia.handler.pack.ResourcePackHandler;
import com.google.gson.JsonObject;

public class LangEnUsInit {
    public static void registerCrops() {
        JsonObject root = new JsonObject();
        CropInit.cropList.forEach(crop -> {
            String cropName = crop.cropName;
            String formattedCropName = cropName.substring(0, 1).toUpperCase() + cropName.substring(1);
            root.addProperty("block.croparia.block_crop_" + cropName, formattedCropName + " Seeds");
            root.addProperty("item.croparia.fruit_" + cropName, formattedCropName + " Fruit");
        });
        ResourcePackHandler.INSTANCE.addLangEnUs(root);
    }
}
