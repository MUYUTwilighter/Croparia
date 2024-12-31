package cool.muyucloud.generator;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import cool.muyucloud.CropariaIf;
import cool.muyucloud.data.crop.Crop;
import cool.muyucloud.registry.Crops;
import cool.muyucloud.util.pack.DataPackHandler;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class TagGenerator {
    private static final Map<ResourceLocation, JsonObject> TAGS = new HashMap<>();

    public static void init() {
        for (Crop crop : Crops.CROPS) {
            addSeed(crop);
            addFruit(crop);
            addCropBlock(crop);
        }
        TAGS.forEach(DataPackHandler.INSTANCE::addTag);
    }

    public static void addFruit(Crop crop) {
        JsonObject tag =  getTag(CropariaIf.of("items/fruits"));
        addValue(tag, crop.getFruitId().toString());
    }

    public static void addCropBlock(Crop crop) {
        JsonObject tag =  getTag(ResourceLocation.tryParse("minecraft:blocks/crops"));
        addValue(tag, Objects.requireNonNull(crop.getCropBlock().arch$registryName()).toString());
    }

    public static void addSeed(Crop crop) {
        JsonObject tag =  getTag(ResourceLocation.tryParse("minecraft:items/seeds"));
        addValue(tag, crop.getSeedId().toString());
    }

    private static JsonObject getTag(ResourceLocation id) {
        JsonObject tag = TAGS.get(id);
        if (tag == null) tag = initTag();
        TAGS.put(id, tag);
        return tag;
    }

    private static JsonObject initTag() {
        JsonObject root = new JsonObject();
        root.addProperty("replace", false);
        root.add("values", new JsonArray());
        return root;
    }

    private static void addValue(@NotNull JsonObject tag, @NotNull String value) {
        JsonArray values = GsonHelper.getAsJsonArray(tag, "values");
        values.add(value);
    }
}
