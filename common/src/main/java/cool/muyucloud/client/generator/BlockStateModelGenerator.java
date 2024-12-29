package cool.muyucloud.client.generator;

import com.google.gson.JsonObject;
import cool.muyucloud.data.crop.Crop;
import cool.muyucloud.registry.Crops;
import cool.muyucloud.util.pack.ResourcePackHandler;

public class BlockStateModelGenerator {
    public static void init() {
        for (Crop crop : Crops.CROPS) {
            addCrop(crop);
        }
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
