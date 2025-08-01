package cool.muyucloud.croparia.client.generator;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import cool.muyucloud.croparia.api.crop.Crop;

public class ItemModelGenerator {
//    public static void init() {
//        Crops.forEachCrop(crop -> {
//            addFruit(crop);
//            addSeed(crop);
//        });
//    }

    private static final JsonObject NONE_TINT = new JsonObject();

    static {
        NONE_TINT.addProperty("type", "minecraft:constant");
        NONE_TINT.addProperty("value", -1);
    }

    public static void addFruit(Crop crop) {
        String type = crop.getType();
        JsonObject root = new JsonObject();
        JsonObject model = new JsonObject();
        model.addProperty("type", "minecraft:model");
        model.addProperty("model", "croparia:item/template_fruit_%s".formatted(type));
        JsonArray tints = new JsonArray();
        tints.add(NONE_TINT);
        JsonObject layer1 = new JsonObject();
        layer1.addProperty("type", "minecraft:constant");
        layer1.addProperty("value", crop.getColor().getValue());
        tints.add(layer1);
        model.add("tints", tints);
        root.add("model", model);
//        ResourcePackHandler.INSTANCE.addItemDef(crop.getFruitId(), root);
    }

    public static void addSeed(Crop crop) {
        JsonObject root = new JsonObject();
        JsonObject model = new JsonObject();
        model.addProperty("type", "minecraft:model");
        model.addProperty("model", "croparia:item/template_seed");
        JsonArray tints = new JsonArray();
        JsonObject layer0 = new JsonObject();
        layer0.addProperty("type", "minecraft:constant");
        layer0.addProperty("value", crop.getColor().getValue());
        tints.add(layer0);
        model.add("tints", tints);
        root.add("model", model);
//        ResourcePackHandler.INSTANCE.addItemDef(crop.getSeedId(), root);
    }
}
