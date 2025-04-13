package cool.muyucloud.croparia.kubejs;

import cool.muyucloud.croparia.CropariaIf;
import cool.muyucloud.croparia.api.crop.Crop;
import cool.muyucloud.croparia.api.crop.CropType;
import cool.muyucloud.croparia.registry.Crops;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Map;
import java.util.Optional;

@SuppressWarnings("unused")
public class CropModifier {
    public static boolean modify(@NotNull String name, @Nullable String material, @Nullable Integer color, @Nullable Integer tier, @Nullable String type, @Nullable Map<String, String> translations, @Nullable String translationKey) {
        Crop old = Crops.forName(name);
        if (old == null) {
            CropariaIf.LOGGER.error("Crop \"{}\" not found", name);
            return false;
        }
        translations = translations == null ? Map.of() : translations;
        Optional<Crop> modified = old.forModified(material, color, tier, CropType.valueOf(type), translations, translationKey);
        return modified.map(crop -> !Crops.recordCustom(crop)).isPresent();
    }
}