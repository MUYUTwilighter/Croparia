package cool.muyucloud.croparia.kubejs;

import cool.muyucloud.croparia.api.crop.Crop;
import cool.muyucloud.croparia.api.crop.util.Color;
import cool.muyucloud.croparia.api.crop.util.CropDependencies;
import cool.muyucloud.croparia.api.crop.util.Material;
import cool.muyucloud.croparia.registry.DgRegistries;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Map;

@SuppressWarnings({"unused"})
public class CropUtil {
    /**
     * Add a simple custom crop.
     *
     * @param rawId           crop id
     * @param material        material id which the crop grows, could be item ID or item tag
     * @param color           int value of color
     * @param tier            tier
     * @param type            crop type that specifies the textures. See also {@link Crop#PRESET_TYPES}
     * @param rawDependencies translation translationKey for the crop, used for formatting item & block names.
     * @param translations    custom translations
     */
    public static void create(
        @NotNull String rawId, @NotNull String material, int color, int tier, @Nullable String type,
        @Nullable Map<String, String> rawDependencies, @Nullable Map<String, String> translations
    ) {
        type = type == null ? Crop.DEFAULT_TYPE : type;
        ResourceLocation id = ResourceLocation.parse(rawId);
        CropDependencies dependencies = rawDependencies == null ? null : new CropDependencies(rawDependencies);
        Crop crop = new Crop(id, new Material(material), new Color(color), tier, type, translations, dependencies);
        DgRegistries.CROPS.register(crop);
    }

    public static boolean modify() {
        return false;
    }
}