package cool.muyucloud.croparia.kubejs;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Map;

@SuppressWarnings("unused")
public class CropModifier {
    public static boolean modify(@NotNull String name, @Nullable String material, @Nullable Integer color, @Nullable Integer tier, @Nullable String type, @Nullable Map<String, String> translations, @Nullable String translationKey) {
//        Crop old = CropRegistry.CROPS.forName(name);
//        if (old == null) {
//            CropariaIf.LOGGER.error("Crop \"{}\" not found", name);
//            return false;
//        }
//        translations = translations == null ? Map.of() : translations;
//        Optional<Crop> modified = old.forModified(material, color, tier, type, translations, translationKey);
//        return modified.map(crop -> !Crops.recordCustom(crop)).isPresent();
        return false;
    }
}