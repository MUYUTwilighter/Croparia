package cool.muyucloud.data;

import dev.architectury.platform.Platform;

import java.util.Map;
import java.util.Set;

/**
 * Data entity for crops<br/>
 * <p>
 * name: crop name<br/>
 * materialId: material id which the crop grows, could be item ID or item tag<br/>
 * color: int value of color<br/>
 * [Optional] type: crop type that specifies the textures. See also {@link CropType}<br/>
 * [Optional] translationKey: translation key, used for compatibility with other mods<br/>
 * [Optional] translations: translations, default translation for en_us will be generated with crop name<br/>
 * [Optional] tier: tier<br/>
 * [Optional] dependencies: mod dependencies<br/>
 */
public record RawCrop(
    String name,
    String materialId,
    String type,
    String translationKey,
    int color,
    int tier,
    Map<String, String> translations,
    Set<String> dependencies
) {
}
