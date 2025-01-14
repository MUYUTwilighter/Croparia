package cool.muyucloud.croparia.generator;

import com.google.gson.JsonObject;
import cool.muyucloud.croparia.CropariaIf;
import cool.muyucloud.croparia.data.crop.Crop;
import cool.muyucloud.croparia.registry.Crops;
import cool.muyucloud.croparia.util.TagUtil;
import cool.muyucloud.croparia.util.pack.DataPackHandler;
import net.minecraft.resources.ResourceLocation;

import java.util.HashMap;
import java.util.Map;

public class ItemTagGenerator {
    private static final Map<ResourceLocation, JsonObject> TAGS = new HashMap<>();

    public static void init() {
        for (Crop crop : Crops.CROPS) {
            addSeed(crop);
            addFruit(crop);
        }
        TAGS.forEach(DataPackHandler.INSTANCE::addItemTag);
    }

    protected static void addFruit(Crop crop) {
        JsonObject tag = getTag(ResourceLocation.tryParse("c:fruits"));
        TagUtil.addValue(tag, crop.getFruitId().toString());
    }

    protected static void addSeed(Crop crop) {
        JsonObject tag = getTag(CropariaIf.of("magic_seeds"));
        TagUtil.addValue(tag, crop.getSeedId().toString());
    }

    protected static JsonObject getTag(ResourceLocation id) {
        JsonObject tag = TAGS.get(id);
        if (tag == null) tag = TagUtil.create();
        TAGS.put(id, tag);
        return tag;
    }
}
