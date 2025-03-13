package cool.muyucloud.croparia.api.generator;

import com.google.gson.JsonObject;
import cool.muyucloud.croparia.api.crop.Crop;
import cool.muyucloud.croparia.registry.Crops;
import cool.muyucloud.croparia.util.TagUtil;
import cool.muyucloud.croparia.api.generator.pack.DataPackHandler;
import net.minecraft.resources.ResourceLocation;

import java.util.HashMap;
import java.util.Map;

public class BlockTagGenerator {
    private static final Map<ResourceLocation, JsonObject> TAGS = new HashMap<>();

    public static void init() {
        Crops.forEachCrop(crop -> {
            addCropBlock(crop);
            addBeeGrowable(crop);
        });
        TAGS.forEach(DataPackHandler.INSTANCE::addBlockTag);
    }

    private static void addCropBlock(Crop crop) {
        JsonObject tag = getTag(ResourceLocation.tryParse("minecraft:crops"));
        TagUtil.addValue(tag, crop.getBlockId().toString());
    }

    private static void addBeeGrowable(Crop crop) {
        JsonObject tag = getTag(ResourceLocation.tryParse("minecraft:bee_growables"));
        TagUtil.addValue(tag, crop.getBlockId().toString());
    }

    protected static JsonObject getTag(ResourceLocation id) {
        JsonObject tag = TAGS.get(id);
        if (tag == null) tag = TagUtil.create();
        TAGS.put(id, tag);
        return tag;
    }
}
