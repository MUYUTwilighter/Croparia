package cool.muyucloud.croparia.api.crop;

import cool.muyucloud.croparia.registry.Crops;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Map;

/**
 * Data entity for crops<br/>
 * <p>
 * name: crop name<br/>
 * [Either#1] material: material which the crop grows, could be item ID or item tag.<br/>
 * [Either#1] tag: item tag of material which the crop grows, used for compatibility with Dalarion's crop format<br/>
 * color: int value of color<br/>
 * [Suggested] tier: tier, default {@code 0}<br/>
 * [Suggested] type: crop type that specifies the textures, default {@link CropType#CROP}. See also {@link CropType}<br/>
 * [Optional] translationKey: translation translationKey, default {@code "crop.croparia.{name}"},
 * used for convenience to format item & block names<br/>
 * [Optional] translations: custom translations,
 * default translation {@link Crop#parseDefaultTranslation(String name)} for {@code en_us}<br/>
 * [Optional] dependencies: mod dependencies. See also {@link Crops#shouldLoad}<br/>
 *
 * @see Crop
 */
@Nullable
public record RawCrop(String name, String material, String tag, String type, String translationKey, String color,
                      int tier,
                      Map<String, String> translations, List<List<String>> dependencies) {
    public boolean isTranslationSpecified() {
        return this.translations() != null && !this.translations().isEmpty();
    }
}
