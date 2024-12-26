package cool.muyucloud.generator;

import com.google.gson.JsonElement;
import cool.muyucloud.data.crop.Crop;
import net.minecraft.resources.ResourceLocation;

import java.util.Map;

public class RecipeGenerator {
    private static final String fruitToMaterialPath = "crafting/resource";
    private static final Map<ResourceLocation, JsonElement> recipes = Map.of();

    public static void flush() {

    }

    public static void createSeed(Crop crop) {
    }

    public static void createFruitToMaterial(Crop crop) {

    }
}
