package cool.muyucloud.croparia.client.generator;

import com.google.gson.JsonObject;
import cool.muyucloud.croparia.api.crop.Crop;
import cool.muyucloud.croparia.api.crop.Crops;
import cool.muyucloud.croparia.api.generator.pack.ResourcePackHandler;

public class ItemModelGenerator {
    public static void init() {
        Crops.forEachCrop(crop -> {
            addFruit(crop);
            addSeed(crop);
        });
    }

    public static void addFruit(Crop crop) {
        String type = crop.getType().getModelName();
        JsonObject fruit = new JsonObject();
        fruit.addProperty("parent", "minecraft:item/generated");
        JsonObject fruitTextures = new JsonObject();
        // resources/assets/croparia/textures/item/fruit_{type}.png
        fruitTextures.addProperty("layer0", "croparia:item/fruit_%s".formatted(type));
        // resources/assets/croparia/textures/item/fruit_{type}_overlay.png
        fruitTextures.addProperty("layer1", "croparia:item/fruit_%s_overlay".formatted(type));
        fruit.add("textures", fruitTextures);
        ResourcePackHandler.INSTANCE.addItemModel(crop.getFruitId(), fruit);
    }

    public static void addSeed(Crop crop) {
        JsonObject seed = new JsonObject();
        seed.addProperty("parent", "minecraft:item/generated");
        JsonObject seedTextures = new JsonObject();
        seedTextures.addProperty("layer0", "croparia:item/seed_crop");
        seed.add("textures", seedTextures);
        ResourcePackHandler.INSTANCE.addItemModel(crop.getSeedId(), seed);
    }
}
