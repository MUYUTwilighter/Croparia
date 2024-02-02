package com.defacto34.croparia.init;

import com.defacto34.croparia.Croparia;
import com.defacto34.croparia.api.crop.Crop;
import com.defacto34.croparia.api.crop.CropariaCrops;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.resource.Resource;
import net.minecraft.resource.ResourcePack;
import net.minecraft.util.Identifier;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

/** NOT COMPLETED!<br/>
 * Add default BlockState json for crops
 * */
public class BlockStateInit {
    public static Map<Identifier, Resource> BLOCK_STATES = new HashMap<>();
    public static Path RESOURCE_PATH = FabricLoader.getInstance().getConfigDir().resolve("croparia/resources/assets/croparia/blockstates");
    public static ResourcePack RESOURCE_PACK;

    public static void registerCrop(Crop crop) {
        String cropName = crop.cropName;
        CropariaCrops crops = (CropariaCrops) crop.cropBlock;

        StringBuilder builder = new StringBuilder();
        builder.append("{").append('\n');
        builder.append("    \"variants\": {").append('\n');
        for (int i = 0; i < crops.getMaxAge(); ++i) {
            builder.append("        \"age=").append(i).append("\": {").append('\n');
            builder.append("            \"model\": \"croparia:block/crop_stage\" + i + \"\"").append('\n');
            builder.append("        }");
            if ((i + 1) < crops.getMaxAge()) {
                builder.append(",");
            }
            builder.append('\n');
        }
        builder.append("    }").append('\n');
        builder.append("}").append('\n');
        String content = builder.toString();

        Identifier identifier = new Identifier(Croparia.MOD_ID, "blockstates/block_crop_" + cropName);
//        Resource resource = new Resource();
    }

    public static void registerCrops() {
        File file = new File(RESOURCE_PATH.toUri());
        if (file.exists()) {
            file.mkdirs();
        }
        try {
            for (Crop crop : CropInit.cropList) {
                registerCrop(crop);
            }
        } catch (Exception ignored) {}
    }
}
