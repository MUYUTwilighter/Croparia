package cool.muyucloud.registry;

import cool.muyucloud.data.crop.Crop;
import cool.muyucloud.data.crop.CropFileReader;
import cool.muyucloud.data.crop.CropType;
import cool.muyucloud.data.crop.RawCrop;
import dev.architectury.platform.Platform;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public class Crops {
    public static final Set<Crop> CROPS = new HashSet<>();

    public static final Crop APPLE = registerCrop("apple", "minecraft:apple", 0x7F7F00, 0, CropType.FOOD, "item.minecraft.apple");
    public static final Crop ELEMATILIUS = registerCrop("elematilius", "croparia:elematilius", 0x7F7F00, 3, CropType.ELEMENTAL);

    /**
     * Add a simple crop. Mainly used for croparia crops.
     *
     * @param name       crop name
     * @param materialId material id which the crop grows, could be item ID or item tag
     * @param color      int value of color
     * @param tier       tier
     * @param type       crop type that specifies the textures. See also {@link CropType}
     */
    private static Crop registerCrop(@NotNull String name, @NotNull String materialId, int color, int tier, @NotNull CropType type) {
        Crop crop = Crop.create(name, materialId, color, tier, type);
        CROPS.add(crop);
        return crop;
    }

    /**
     * Add a simple crop with specified translation key. Mainly used for vanilla crops.
     *
     * @param name           crop name
     * @param materialId     material id which the crop grows, could be item ID or item tag
     * @param color          int value of color
     * @param tier           tier
     * @param type           crop type that specifies the textures. See also {@link CropType}
     * @param translationKey translation key for the crop, used for formatting item & block names.
     */
    private static Crop registerCrop(@NotNull String name, @NotNull String materialId, int color, int tier, @NotNull CropType type, @NotNull String translationKey) {
        Crop crop = Crop.create(name, materialId, color, tier, type, translationKey, Map.of());
        CROPS.add(crop);
        return crop;
    }

    /**
     * Add a crop from other mods which should be loaded. Mainly used for modded crops.
     *
     * @param name         crop name
     * @param materialId   material id which the crop grows, could be item ID or item tag
     * @param color        int value of color
     * @param tier         tier
     * @param type         crop type that specifies the textures. See also {@link CropType}
     * @param dependencies mod dependencies
     */
    private static Optional<Crop> registerCrop(
        @NotNull String name, @NotNull String materialId, int color, int tier, @NotNull CropType type,
        @NotNull String translationKey, @NotNull List<String>... dependencies
    ) {
        if (shouldLoad(dependencies)) {
            return Optional.of(registerCrop(name, materialId, color, tier, type));
        } else {
            return Optional.empty();
        }
    }

    public static boolean shouldLoad(List<List<String>> dependencies) {
        return dependencies.stream().allMatch(list -> list.stream().anyMatch(Platform::isModLoaded));
    }

    public static boolean shouldLoad(List<String>... dependencies) {
        return Arrays.stream(dependencies).allMatch(list -> list.stream().anyMatch(Platform::isModLoaded));
    }

    public static void register() {
        CropFileReader.readCrops().forEach(Crops::registerFileCrop);
        for (Crop crop : CROPS) {
            CropariaItems.registerCrop(crop);
            CropariaBlocks.registerCrop(crop);
        }
    }

    private static void registerFileCrop(RawCrop raw) {
        if (raw.dependencies() == null || shouldLoad(raw.dependencies())) {
            Crop crop = Crop.of(raw);
            CROPS.add(crop);
        }
    }
}
