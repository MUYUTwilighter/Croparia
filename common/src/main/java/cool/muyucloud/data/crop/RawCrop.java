package cool.muyucloud.data.crop;

import java.util.List;
import java.util.Map;

/**
 * Data entity for crops<br/>
 * <p>
 * name: crop name<br/>
 * material: material which the crop grows, could be item ID or item tag<br/>
 * color: int value of color<br/>
 * [Suggested] tier: tier, default {@code 0}<br/>
 * [Suggested] type: crop type that specifies the textures, default {@link CropType#CROP}. See also {@link CropType}<br/>
 * [Optional] translationKey: translation key, default {@code "crop.croparia.{name}"},
 * used for convenience to format item & block names<br/>
 * [Optional] translations: custom translations,
 * default translation {@link Crop#parseDefaultTranslation(String name)} for {@code en_us}<br/>
 * [Optional] dependencies: mod dependencies. See also {@link cool.muyucloud.registry.Crops#shouldLoad}
 *
 * @see Crop
 */
public record RawCrop(String name, String material, String type, String translationKey, int color, int tier,
                      Map<String, String> translations, List<List<String>> dependencies) {
}
