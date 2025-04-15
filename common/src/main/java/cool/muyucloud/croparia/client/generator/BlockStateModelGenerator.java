package cool.muyucloud.croparia.client.generator;

import com.google.gson.JsonObject;
import cool.muyucloud.croparia.api.crop.Crop;
import cool.muyucloud.croparia.api.generator.pack.ResourcePackHandler;
import cool.muyucloud.croparia.registry.Crops;

public class BlockStateModelGenerator {
    public static void init() {
        Crops.forEachCrop(BlockStateModelGenerator::addCrop);
    }

    public static void addCrop(Crop crop) {
        String type = crop.getType().getModelName();
        JsonObject root = new JsonObject();
        JsonObject variants = new JsonObject();
        for (int i = 0; i <= 4; i++) {
            JsonObject age = new JsonObject();
            // resources/assets/croparia/models/block/crop_stage{i}.json
            age.addProperty("model", "croparia:block/crop_stage" + i);
            variants.add("age=" + i, age);
        }
        for (int i = 5; i <= 7; i++) {
            JsonObject age = new JsonObject();
            // resources/assets/croparia/models/block/{type}_stage{i}.json
            age.addProperty("model", "croparia:block/%s_stage%s".formatted(type, i));
            variants.add("age=" + i, age);
        }
        root.add("variants", variants);
        ResourcePackHandler.INSTANCE.addBlockStateModel(crop.getBlockId(), root);
    }
}
