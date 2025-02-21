package cool.muyucloud.croparia.api.generator;

import com.google.gson.JsonObject;
import cool.muyucloud.croparia.CropariaIf;
import cool.muyucloud.croparia.api.crop.Crop;
import cool.muyucloud.croparia.api.crop.Crops;
import cool.muyucloud.croparia.api.generator.pack.DataPackHandler;
import cool.muyucloud.croparia.util.TagUtil;
import net.minecraft.resources.ResourceLocation;

import java.util.HashMap;
import java.util.Map;

public class ItemTagGenerator {
    private static final Map<ResourceLocation, JsonObject> TAGS = new HashMap<>();

    public static void init() {
        Crops.forEachCrop(crop -> {
            addFruit(crop);
            addSeed(crop);
        });
        TAGS.forEach(DataPackHandler.INSTANCE::addItemTag);
    }

    protected static void addFruit(Crop crop) {
        JsonObject tag = getTag(ResourceLocation.tryParse("c:fruits"));
        TagUtil.addValue(tag, crop.getFruitId().toString());
    }

    protected static void addSeed(Crop crop) {
        JsonObject tag = getTag(CropariaIf.of("crop_seeds"));
        TagUtil.addValue(tag, crop.getSeedId().toString());
    }

    protected static JsonObject getTag(ResourceLocation id) {
        JsonObject tag = TAGS.get(id);
        if (tag == null) tag = TagUtil.create();
        TAGS.put(id, tag);
        return tag;
    }
}
